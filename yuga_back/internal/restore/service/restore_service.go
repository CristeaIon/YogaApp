package service

import (
	"github.com/gin-gonic/gin"
	"time"
	"yuga_back/internal/restore/model"
	"yuga_back/internal/restore/storage"
	"yuga_back/pkg/logger"
)

type RestoreService struct {
	repository storage.RestorePasswordRepository
	log        *logger.Logger
}

func NewService(repository storage.RestorePasswordRepository, log *logger.Logger) *RestoreService {
	return &RestoreService{repository: repository, log: log}
}

func (s RestoreService) RestorePassword(ctx *gin.Context, dto model.RestorePasswordDTO) (model.RestorePasswordResponse, error) {

	return model.RestorePasswordResponse{
		ID:         "ddfghjkl",
		Contact:    "cristeaion36@gmail.com",
		SendStatus: "SUCCESS",
		SendTime:   time.Now(),
	}, nil
}
