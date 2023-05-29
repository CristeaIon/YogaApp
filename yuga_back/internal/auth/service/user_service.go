package service

import (
	"context"
	"yuga_back/internal/auth/model"
	"yuga_back/internal/auth/storage"
	"yuga_back/pkg/logger"
)

type Service struct {
	repository storage.AuthRepository
	log        *logger.Logger
}

func NewService(repository storage.AuthRepository, log *logger.Logger) *Service {
	return &Service{repository: repository, log: log}
}

func (s *Service) CreateUser(ctx context.Context, userDTO *model.CreateUserDTO) (user model.User, err error) {
	var newUser = model.User{
		FullName:     userDTO.FullName,
		Email:        userDTO.Email,
		Phone:        userDTO.Phone,
		PasswordHash: userDTO.Password,
	}

	err = s.repository.Create(ctx, newUser)
	return user, nil
}
