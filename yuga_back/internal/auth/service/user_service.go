package service

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"time"
	"yuga_back/internal/auth/model"
	"yuga_back/internal/auth/storage"
	"yuga_back/pkg/logger"
	"yuga_back/pkg/password"
	"yuga_back/pkg/token"
)

type Service struct {
	repository storage.AuthRepository
	jwtCreator token.Creator
	log        *logger.Logger
}

func NewService(repository storage.AuthRepository, jwtCreator token.Creator, log *logger.Logger) *Service {
	return &Service{repository: repository, jwtCreator: jwtCreator, log: log}
}

func (s Service) CreateUser(ctx *gin.Context, userDTO *model.CreateUserDTO) (model.UserResponse, error) {
	newToken, err := s.jwtCreator.CreateToken(userDTO.FullName, userDTO.Email, 600*time.Second)

	if err != nil {
		err = fmt.Errorf("error to create token:%v", err)
		s.log.Error(err)
		return model.UserResponse{}, err
	}

	refreshToken, err := s.jwtCreator.CreateToken(userDTO.FullName, userDTO.Email, 600*time.Minute)

	if err != nil {
		err = fmt.Errorf("error to create refresh token:%v", err)
		s.log.Error(err)
		return model.UserResponse{}, err
	}
	hashPassword, err := password.HashPassword(userDTO.Password)
	if err != nil {
		err = fmt.Errorf("error to hash password:%v", err)
		s.log.Error(err)
		return model.UserResponse{}, err
	}
	var newUser = model.User{
		FullName:     userDTO.FullName,
		Email:        userDTO.Email,
		Phone:        userDTO.Phone,
		PasswordHash: hashPassword,
	}

	u, err := s.repository.Create(ctx, newUser)

	if err != nil {
		err = fmt.Errorf("error to create user:%v", err)
		s.log.Error(err)
		return model.UserResponse{}, err
	}

	user := model.UserResponse{
		FullName:     u.FullName,
		Email:        u.Email,
		Phone:        u.Phone,
		CreatedAt:    u.CreatedAt,
		UpdatedAt:    u.UpdatedAt,
		Token:        newToken,
		RefreshToken: refreshToken,
	}
	return user, nil
}

func (s Service) LoginUser(ctx *gin.Context, userDTO *model.LoginUserDTO) (model.UserResponse, error) {
	user, err := s.repository.FindOne(ctx, userDTO.Email)

	if err != nil {
		return model.UserResponse{}, err
	}

	err = password.CheckPassword(userDTO.Password, user.PasswordHash)
	if err != nil {
		return model.UserResponse{}, err
	}
	newToken, err := s.jwtCreator.CreateToken(user.FullName, user.Email, 600*time.Second)

	if err != nil {
		err = fmt.Errorf("error to create token:%v", err)
		s.log.Error(err)
		return model.UserResponse{}, err
	}

	refreshToken, err := s.jwtCreator.CreateToken(user.FullName, user.Email, 600*time.Minute)

	if err != nil {
		err = fmt.Errorf("error to create refresh token:%v", err)
		s.log.Error(err)
		return model.UserResponse{}, err
	}
	resp := model.UserResponse{
		FullName:     user.FullName,
		Email:        user.Email,
		Phone:        user.Phone,
		CreatedAt:    user.CreatedAt,
		UpdatedAt:    user.UpdatedAt,
		Token:        newToken,
		RefreshToken: refreshToken,
	}
	return resp, nil
}

func (s Service) DeleteUser(ctx *gin.Context, id string) (model.DeleteUserResponse, error) {
	user, err := s.repository.Delete(ctx, id)
	if err != nil {
		return model.DeleteUserResponse{}, err
	}

	u := model.DeleteUserResponse{
		FullName:  user.FullName,
		Email:     user.Email,
		Phone:     user.Phone,
		CreatedAt: user.CreatedAt,
		UpdatedAt: user.UpdatedAt,
	}
	return u, nil
}
