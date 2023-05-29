package main

import (
	"context"
	"yuga_back/internal/auth"
	users "yuga_back/internal/auth/db/postgres"
	service "yuga_back/internal/auth/service"
	"yuga_back/internal/config"
	"yuga_back/pkg/client/postgres"
	"yuga_back/pkg/logger"
	"yuga_back/pkg/token"

	"github.com/gin-gonic/gin"
)

func main() {
	log := logger.GetLogger()
	log.Info("create gin router")
	router := gin.Default()

	cfg := config.GetConfig()

	psqlClient, err := postgres.NewClient(context.TODO(), cfg.Storage)
	if err != nil {
		log.Fatalf("%v", err)
	}
	jwtCreator, err := token.NewJWTCreator(cfg.JWTSecret)
	if err != nil {
		log.Fatal(err)
	}

	repository := users.NewRepository(psqlClient, log)
	ser := service.NewService(repository, jwtCreator, log)

	userHandler := auth.NewHandler(ser, log)
	userHandler.Register(router)

	err = router.Run("0.0.0.0:8080")
	if err != nil {
		return
	}
}
