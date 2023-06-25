package service

import (
	"errors"
	"github.com/gin-gonic/gin"
	"time"
	m "yuga_back/internal/auth/model"
	userStorage "yuga_back/internal/auth/storage"
	"yuga_back/internal/restore/model"
	"yuga_back/internal/restore/storage"
	"yuga_back/pkg/logger"
	"yuga_back/pkg/mail_service"
	"yuga_back/pkg/password"
	"yuga_back/pkg/token"
	"yuga_back/pkg/util"
)

type RestoreService struct {
	repository     storage.RestorePasswordRepository
	jwtCreator     token.Creator
	authRepository userStorage.AuthRepository
	mailService    mail_service.MailService
	log            *logger.Logger
}

func NewService(
	repository storage.RestorePasswordRepository,
	jwtCreator token.Creator,
	authRepository userStorage.AuthRepository,
	mailService mail_service.MailService,
	log *logger.Logger,
) *RestoreService {
	return &RestoreService{repository: repository, jwtCreator: jwtCreator, authRepository: authRepository, mailService: mailService, log: log}
}

func (s RestoreService) RestorePassword(ctx *gin.Context, dto model.RestorePasswordRequest) (model.RestorePasswordResponse, error) {

	user, err := s.authRepository.FindOne(ctx, dto.Email)
	if err != nil {
		return model.RestorePasswordResponse{}, err
	}
	code := util.GenerateRandomCode(4)
	from := "cristeaion36@gmail.com"
	to := []string{dto.Email}
	subject := "Password reset for Yoga app"
	mailData := mail_service.MailData{Code: code, Name: user.FullName}
	mailReq := s.mailService.NewMail(from, to, subject, &mailData)
	err = s.mailService.SendMail(mailReq)
	if err != nil {
		s.log.Error("unable to send mail")
		return model.RestorePasswordResponse{}, err
	}
	data := model.ValidationData{
		Contact:    dto.Email,
		Token:      code,
		SendStatus: "SUCCESS",
		SendTime:   time.Now(),
	}
	restoreValidationData, err := s.repository.RestorePassword(ctx, data)
	if err != nil {
		s.log.Error("unable to store Validation Data")
		return model.RestorePasswordResponse{}, err
	}

	response := model.RestorePasswordResponse{
		ID:         restoreValidationData.ID,
		Contact:    restoreValidationData.Contact,
		SendStatus: restoreValidationData.SendStatus,
		SendTime:   restoreValidationData.SendTime,
	}

	return response, nil
}
func (s RestoreService) ValidateCode(ctx *gin.Context, code model.ValidateCodeRequest) (model.ValidateCodeResponse, error) {
	data, err := s.repository.ValidateCode(ctx, code)
	if err != nil {
		return model.ValidateCodeResponse{}, err
	}
	if data.Token != code.Code {
		err = errors.New("invalid code")
		return model.ValidateCodeResponse{}, err
	}
	return model.ValidateCodeResponse{
		Status: "SUCCESS",
	}, nil
}
func (s RestoreService) UpdatePassword(ctx *gin.Context, updatePasswordDto model.UpdatePasswordRequest) (m.UserResponse, error) {

	updatedPassword, err := password.HashPassword(updatePasswordDto.Password)
	if err != nil {
		return m.UserResponse{}, err
	}

	user, err := s.repository.UpdatePassword(ctx, updatePasswordDto.Email, updatedPassword)
	if err != nil {
		return m.UserResponse{}, err
	}

	newToken, err := s.jwtCreator.CreateToken(user.FullName, user.Email, time.Second*600)
	if err != nil {
		return m.UserResponse{}, err
	}

	refreshToken, err := s.jwtCreator.CreateToken(user.FullName, user.Email, time.Minute*600)
	if err != nil {
		return m.UserResponse{}, err
	}

	u := m.UserResponse{FullName: user.FullName, Email: user.Email, Phone: user.Phone, Token: newToken, RefreshToken: refreshToken}
	return u, nil
}
