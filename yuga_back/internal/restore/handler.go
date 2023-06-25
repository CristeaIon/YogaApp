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
	v1 := router.Group("api/v1")
	{
		password := v1.Group("password")
		{
			password.POST("restore", h.RestorePassword)
			password.POST("validate-code", h.ValidateCode)
			password.PATCH("update", h.UpdatePassword)
		}
	}
}

// RestorePassword godoc

// @Summary		Restore user password
// @Description	Restore user password and obtain a validation code to create a new one
// @Tag			password
// @Accept			json
// @Produce		json
// @Param			json	body		model.RestorePasswordRequest	true	"Request a password restore"
// @Success		200		{object}	model.RestorePasswordResponse
// @Failure		400		{object}	apperror.AppError
// @Failure		404		{object}	apperror.AppError
// @Failure		500		{object}	apperror.AppError
// @Router			/password/restore [post]
func (h *handler) RestorePassword(ctx *gin.Context) {
	var restoreDTO model.RestorePasswordRequest
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

// ValidateCode godoc

// @Summary		Validate restore password code
// @Description	Validate restore password code
// @Tag			password
// @Accept			json
// @Produce		json
// @Param			json	body		model.ValidateCodeRequest	true	"Validate restore password code object"
// @Success		200		{object}	model.ValidateCodeResponse
// @Failure		400		{object}	apperror.AppError
// @Failure		404		{object}	apperror.AppError
// @Failure		500		{object}	apperror.AppError
// @Router			/password/validate-code [post]
func (h *handler) ValidateCode(ctx *gin.Context) {
	var code model.ValidateCodeRequest
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

// UpdatePassword godoc

// @Summary		Update user password
// @Description	Update user password and obtain new login info
// @Tag			password
// @Accept			json
// @Produce		json
// @Param			json	body		model.UpdatePasswordRequest	true	"Update user password object"
// @Success		200		{object}	model.UserResponse
// @Failure		400		{object}	apperror.AppError
// @Failure		404		{object}	apperror.AppError
// @Failure		500		{object}	apperror.AppError
// @Router			/password/update [patch]
func (h *handler) UpdatePassword(ctx *gin.Context) {
	var passwordDTO model.UpdatePasswordRequest
	h.logger.Info("update password")
	err := ctx.ShouldBindJSON(&passwordDTO)
	if err != nil {
		h.logger.Error(err)
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}
	response, err := h.service.UpdatePassword(ctx, passwordDTO)
	if err != nil {
		if isNotFoundError(err) {
			h.logger.Error(err)
			ctx.JSON(http.StatusNotFound, errorResponse(err))
		}
		h.logger.Error(err)
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}
	ctx.IndentedJSON(http.StatusOK, response)
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
