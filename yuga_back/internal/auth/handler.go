package auth

import (
	"net/http"
	"yuga_back/internal/handlers"
	"yuga_back/pkg/logger"

	"github.com/gin-gonic/gin"
)

type handler struct {
	logger *logger.Logger
}

func NewHandler(logger *logger.Logger) handlers.Handler {
	return &handler{logger: logger}
}

func (h *handler) Register(router *gin.Engine) {
	router.POST("auth/signup", h.CreateUser)
	router.POST("auth/login", h.LoginUser)
	router.POST("auth/restore", h.RestorePassword)
	router.POST("auth/validate-code", h.ValidateCode)
	router.POST("auth/update", h.UpdatePassword)
}

func (h *handler) CreateUser(ctx *gin.Context) {
	var newUser CreateUserDTO
	h.logger.Info("create new user")
	ctx.BindJSON(&newUser)
	ctx.IndentedJSON(http.StatusCreated, newUser)
}

func (h *handler) LoginUser(ctx *gin.Context) {
	var newUser LoginUserDTO
	h.logger.Info("login user")
	ctx.BindJSON(&newUser)
	ctx.IndentedJSON(http.StatusCreated, newUser)
}
func (h *handler) RestorePassword(ctx *gin.Context) {
	var restoreDTO RestorePasswordDTO
	h.logger.Info("restore password")
	ctx.BindJSON(&restoreDTO)
	ctx.IndentedJSON(http.StatusCreated, restoreDTO)
}
func (h *handler) ValidateCode(ctx *gin.Context) {
	var code ValidateCodeDTO
	h.logger.Info("validate code")
	ctx.BindJSON(&code)
	ctx.IndentedJSON(http.StatusCreated, code)
}
func (h *handler) UpdatePassword(ctx *gin.Context) {
	var newPassword UpdatePasswordDTO
	h.logger.Info("update password")
	ctx.BindJSON(&newPassword)
	ctx.IndentedJSON(http.StatusCreated, newPassword)
}
