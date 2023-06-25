package auth

import (
	"errors"
	"github.com/lib/pq"
	"net/http"
	"yuga_back/internal/apperror"
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
	v1 := router.Group("api/v1")
	{
		user := v1.Group("user")
		{
			user.POST("signup", h.CreateUser)
			user.POST("login", h.LoginUser)
			user.DELETE("delete/:id", h.DeleteUser)
		}
	}
}

// CreateUser godoc

// @Summary		Create a new user
// @Description	Signup a new user
// @Tag			user
// @Accept			json
// @Produce		json
// @Param			json	body		model.CreateUserRequest	true	"Create user request structure"
// @Success		200		{object}	model.UserResponse
// @Failure		400		{object}	apperror.AppError
// @Failure		404		{object}	apperror.AppError
// @Failure		500		{object}	apperror.AppError
// @Router			/user/signup [post]
func (h *handler) CreateUser(ctx *gin.Context) {
	var newUser model.CreateUserRequest
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
				ctx.IndentedJSON(http.StatusForbidden, errorResponse(err))
				return
			}
		}
		ctx.JSON(http.StatusInternalServerError, apperror.ErrInternalSystem)
		return
	}
	ctx.IndentedJSON(http.StatusCreated, user)
}

// LoginUser godoc

// @Summary		Login user
// @Description	Login a user
// @Tag			user
// @Accept			json
// @Produce		json
// @Param			json	body		model.LoginUserRequest	true	"User login DTO"
// @Success		200		{object}	model.UserResponse
// @Failure		400		{object}	apperror.AppError
// @Failure		404		{object}	apperror.AppError
// @Failure		500		{object}	apperror.AppError
// @Router			/user/login [post]
func (h *handler) LoginUser(ctx *gin.Context) {
	var newUser model.LoginUserRequest
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
		if isNotFoundError(err) {
			ctx.JSON(http.StatusNotFound, errorResponse(errors.New("user not found")))
			return
		}

		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}

	ctx.IndentedJSON(http.StatusOK, user)
}

// DeleteUser godoc

// @Summary		Login user
// @Description	Login a user
// @Tag			user
// @Accept			json
// @Produce		json
// @Param			id	path		string	true	"User id"
// @Success		200	{object}	model.UserResponse
// @Failure		400	{object}	apperror.AppError
// @Failure		404	{object}	apperror.AppError
// @Failure		500	{object}	apperror.AppError
// @Router			/user/delete/{id} [delete]
func (h *handler) DeleteUser(ctx *gin.Context) {
	var req model.DeleteUserRequest
	h.logger.Info("delete users")
	if err := ctx.ShouldBindUri(&req); err != nil {
		h.logger.Error(err)
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}
	user, err := h.service.DeleteUser(ctx, req.ID)
	if err != nil {
		h.logger.Error(err)
		if isNotFoundError(err) {
			ctx.JSON(http.StatusNotFound, errorResponse(errors.New("user not found")))
			return
		}
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}
	ctx.IndentedJSON(http.StatusOK, user)
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
