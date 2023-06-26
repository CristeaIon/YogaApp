package apperror

import (
	"fmt"
	"net/http"
)

const name = "YA"

var (
	ErrInternalSystem = NewAppError(http.StatusInternalServerError, "00100", "internal system error")
	ErrBadRequest     = NewAppError(http.StatusBadRequest, "00101", "bad request")
	ErrValidation     = NewAppError(http.StatusBadRequest, "00102", "validation error")
	ErrNotFound       = NewAppError(http.StatusNotFound, "00103", " not found")
	ErrUnauthorized   = NewAppError(http.StatusUnauthorized, "00104", "unauthorized")
	ErrForbidden      = NewAppError(http.StatusForbidden, "00105", " access forbidden")
)

type AppError struct {
	Err           error  `json:"-"`
	Message       string `json:"message,omitempty"`
	Code          string `json:"code,omitempty"`
	TransportCode int    `json:"-"`
}

func NewAppError(transportCode int, code, message string) *AppError {
	return &AppError{
		Err:           fmt.Errorf(message),
		Code:          name + "-" + code,
		TransportCode: transportCode,
		Message:       message,
	}
}
