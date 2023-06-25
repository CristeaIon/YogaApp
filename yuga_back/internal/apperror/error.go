package apperror

import (
	"fmt"
	"net/http"
)

const name = "YA"

var (
	ErrInternalSystem = NewAppError(http.StatusInternalServerError, "00100", "internal system error")
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
