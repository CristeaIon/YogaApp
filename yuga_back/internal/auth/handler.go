package auth

import (
	"github.com/lib/pq"
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
	router.PATCH("auth/update", h.UpdatePassword)
	router.DELETE("auth/delete/:id", h.DeleteUser)
}

func (h *handler) CreateUser(ctx *gin.Context) {
	var newUser model.CreateUserDTO
	h.logger.Info("create new user")
	if err := ctx.ShouldBindJSON(&newUser); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	var user model.UserResponse
	user, err := h.service.CreateUser(ctx, &newUser)

	if err != nil {
		if pqErr, ok := err.(*pq.Error); ok {
			switch pqErr.Code.Name() {
			case "foreign_key_violation", "unique_violation":
				ctx.JSON(http.StatusForbidden, errorResponse(err))
				return
			}
		}
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}
	ctx.IndentedJSON(http.StatusCreated, user)
}

func (h *handler) LoginUser(ctx *gin.Context) {
	var newUser model.LoginUserDTO
	h.logger.Info("login user")

	if err := ctx.ShouldBindJSON(&newUser); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}
	user, err := h.service.LoginUser(ctx, &newUser)
	if err != nil {
		if pqErr, ok := err.(*pq.Error); ok {
			switch pqErr.Code.Name() {
			case "unique_violation", "no_data_found":
				ctx.JSON(http.StatusForbidden, errorResponse(err))
				return
			}
		}
		if err.Error() == "no rows in result set" {
			ctx.JSON(http.StatusNotFound, errorResponse(err))
			return
		}

		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	ctx.IndentedJSON(http.StatusOK, user)
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
func (h *handler) DeleteUser(ctx *gin.Context) {
	var newPassword model.UpdatePasswordDTO
	h.logger.Info("update password")
	err := ctx.BindJSON(&newPassword)
	if err != nil {
		h.logger.Error(err)
	}
	ctx.IndentedJSON(http.StatusCreated, newPassword)
}

func errorResponse(err error) gin.H {
	return gin.H{"error": err.Error()}
}
