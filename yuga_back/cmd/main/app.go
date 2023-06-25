package main

import (
	"context"
	"fmt"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
	"yuga_back/internal/auth"
	users "yuga_back/internal/auth/db/postgres"
	"yuga_back/internal/auth/service"
	"yuga_back/internal/config"
	"yuga_back/internal/restore"
	"yuga_back/internal/restore/db"
	restoreService "yuga_back/internal/restore/service"
	_ "yuga_back/openapi"
	"yuga_back/pkg/client/postgres"
	"yuga_back/pkg/logger"
	"yuga_back/pkg/mail_service"
	"yuga_back/pkg/token"

	"github.com/gin-gonic/gin"
)

//	@title			Gin Swagger Yoga app API
//	@version		1.0
//	@description	This is a sample server.
//	@termsOfService	http://swagger.io/terms/

//	@contact.name	API Support
//	@contact.url	http://www.swagger.io/support
//	@contact.email	support@swagger.io

//	@license.name	Apache 2.0
//	@license.url	http://www.apache.org/licenses/LICENSE-2.0.html

// @host		localhost:9090
// @BasePath	/api/v1
// @schemes	http https
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
	rs := restoreService.NewService(restorePasswordRepository, jwtCreator, repository, mailService, log)
	restoreHandler := restore.NewHandler(rs, log)
	restoreHandler.Register(router)

	//swagUrl := ginSwagger.URL("http://localhost:9090/swagger/doc.json")
	router.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	url := fmt.Sprintf("%s:%s", cfg.Listen.BindIP, cfg.Listen.Port)

	err = router.Run(url)
	if err != nil {
		return
	}
}
