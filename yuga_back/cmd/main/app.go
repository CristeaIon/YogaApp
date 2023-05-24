package main

import (
	"yuga_back/internal/auth"
	"yuga_back/pkg/logger"

	"github.com/gin-gonic/gin"
)

func main() {
	router := gin.Default()
	logger := logger.GetLogger()

	userHandler := auth.NewHandler(logger)
	userHandler.Register(router)

	router.Run()
}
