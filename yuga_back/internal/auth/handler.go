package auth

import (
	"net/http"
	"yuga_back/internal/auth/model"
	"yuga_back/internal/auth/service"
	"yuga_back/internal/handlers"
	"yuga_back/pkg/logger"

	"github.com/gin-gonic/gin"
)

type handler struct {
	service *service.Service
	logger  *logger.Logger
}

func NewHandler(service *service.Service, logger *logger.Logger) handlers.Handler {
	return &handler{service: service, logger: logger}
}

func (h *handler) Register(router *gin.Engine) {
	router.POST("auth/signup", h.CreateUser)
	router.POST("auth/login", h.LoginUser)
	router.POST("auth/restore", h.RestorePassword)
	router.POST("auth/validate-code", h.ValidateCode)
	router.POST("auth/update", h.UpdatePassword)
}

func (h *handler) CreateUser(ctx *gin.Context) {
	var newUser model.CreateUserDTO
	h.logger.Info("create new user")
	err := ctx.BindJSON(&newUser)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, gin.H{"error": err})
	}

	var user model.UserResponse
	user, err = h.service.CreateUser(ctx, &newUser)

	if err != nil {
		h.logger.Error(err)
	}
	ctx.IndentedJSON(http.StatusCreated, user)
}

func (h *handler) LoginUser(ctx *gin.Context) {
	var newUser model.LoginUserDTO
	h.logger.Info("login user")
	err := ctx.BindJSON(&newUser)
	if err != nil {
		h.logger.Error(err)
	}
	ctx.IndentedJSON(http.StatusCreated, newUser)
}
func (h *handler) RestorePassword(ctx *gin.Context) {
	var restoreDTO model.RestorePasswordDTO
	h.logger.Info("restore password")
	err := ctx.BindJSON(&restoreDTO)
	if err != nil {
		h.logger.Error(err)
	}
	ctx.IndentedJSON(http.StatusCreated, restoreDTO)
}
func (h *handler) ValidateCode(ctx *gin.Context) {
	var code model.ValidateCodeDTO
	h.logger.Info("validate code")
	err := ctx.BindJSON(&code)
	if err != nil {
		h.logger.Error(err)
	}
	ctx.IndentedJSON(http.StatusCreated, code)
}
func (h *handler) UpdatePassword(ctx *gin.Context) {
	var newPassword model.UpdatePasswordDTO
	h.logger.Info("update password")
	err := ctx.BindJSON(&newPassword)
	if err != nil {
		h.logger.Error(err)
	}
	ctx.IndentedJSON(http.StatusCreated, newPassword)
}
