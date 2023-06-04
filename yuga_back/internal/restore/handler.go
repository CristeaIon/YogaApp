package restore

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"yuga_back/internal/handlers"
	"yuga_back/internal/restore/model"
	restoreService "yuga_back/internal/restore/service"
	"yuga_back/pkg/logger"
)

type handler struct {
	service *restoreService.RestoreService
	logger  *logger.Logger
}

func NewHandler(service *restoreService.RestoreService, logger *logger.Logger) handlers.Handler {
	return &handler{service: service, logger: logger}
}

func (h *handler) Register(router *gin.Engine) {
	router.POST("password/restore", h.RestorePassword)
	router.POST("password/validate-code", h.ValidateCode)
	router.PATCH("password/update", h.UpdatePassword)
}

func (h *handler) RestorePassword(ctx *gin.Context) {
	var restoreDTO model.RestorePasswordDTO
	h.logger.Info("restore password")
	err := ctx.ShouldBindJSON(&restoreDTO)
	if err != nil {
		h.logger.Error(err)
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}
	passwordResponse, err := h.service.RestorePassword(ctx, restoreDTO)
	if err != nil {
		if isNotFoundError(err) {
			h.logger.Error(err)
			ctx.JSON(http.StatusNotFound, errorResponse(err))
		}
		h.logger.Error(err)
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}
	ctx.IndentedJSON(http.StatusOK, passwordResponse)
}
func (h *handler) ValidateCode(ctx *gin.Context) {
	var code model.ValidateCodeDTO
	h.logger.Info("validate code")
	err := ctx.ShouldBindJSON(&code)
	if err != nil {
		h.logger.Error(err)
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}
	validationResponse, err := h.service.ValidateCode(ctx, code)
	if err != nil {
		if isNotFoundError(err) {
			h.logger.Error(err)
			ctx.JSON(http.StatusNotFound, errorResponse(err))
		}
		h.logger.Error(err)
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}
	ctx.IndentedJSON(http.StatusOK, validationResponse)
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
func errorResponse(err error) gin.H {
	return gin.H{"error": err.Error()}
}

func isNotFoundError(err error) bool {
	if err.Error() == "no rows in result set" {
		return true
	}
	return false
}
