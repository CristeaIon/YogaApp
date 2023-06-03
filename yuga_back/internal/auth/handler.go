package auth

import (
	"errors"
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
	router.POST("user/signup", h.CreateUser)
	router.POST("user/login", h.LoginUser)
	router.DELETE("user/delete/:id", h.DeleteUser)
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
		if isNotFoundError(err) {
			ctx.JSON(http.StatusNotFound, errorResponse(errors.New("user not found")))
			return
		}

		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}

	ctx.IndentedJSON(http.StatusOK, user)
}
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
