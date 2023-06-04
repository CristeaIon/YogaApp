package main

import (
	"context"
	"fmt"
	"yuga_back/internal/auth"
	users "yuga_back/internal/auth/db/postgres"
	"yuga_back/internal/auth/service"
	"yuga_back/internal/config"
	"yuga_back/internal/restore"
	"yuga_back/internal/restore/db"
	restoreService "yuga_back/internal/restore/service"
	"yuga_back/pkg/client/postgres"
	"yuga_back/pkg/logger"
	"yuga_back/pkg/mail_service"
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
	mailService := mail_service.NewMailService(cfg, log)
	// auth service
	repository := users.NewRepository(psqlClient, log)
	ser := service.NewService(repository, jwtCreator, log)
	userHandler := auth.NewHandler(ser, log)
	userHandler.Register(router)

	//restore service
	restorePasswordRepository := db.NewRepository(psqlClient, log)
	rs := restoreService.NewService(restorePasswordRepository, repository, mailService, log)
	restoreHandler := restore.NewHandler(rs, log)
	restoreHandler.Register(router)

	url := fmt.Sprintf("%s:%s", cfg.Listen.BindIP, cfg.Listen.Port)

	err = router.Run(url)
	if err != nil {
		return
	}
}
