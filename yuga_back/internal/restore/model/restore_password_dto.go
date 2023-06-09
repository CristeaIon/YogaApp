package model

import "time"

type RestorePasswordRequest struct {
	Email string `json:"email" binding:"required,email"`
}

type RestorePasswordResponse struct {
	ID         string    `json:"id"`
	Contact    string    `json:"contact"`
	SendStatus string    `json:"sendStatus"`
	SendTime   time.Time `json:"sendTime"`
}

type UpdatePasswordRequest struct {
	Email    string `json:"email" binding:"required,email"`
	Password string `json:"password" binding:"required,min=6"`
}
type ValidateCodeRequest struct {
	ID      string `json:"id"`
	Contact string `json:"contact"`
	Code    string `json:"code"`
}
type ValidateCodeResponse struct {
	Status string `json:"status"`
}
type ValidationData struct {
	ID               string    `json:"id"`
	Contact          string    `json:"contact"`
	Token            string    `json:"token"`
	SendStatus       string    `json:"send_status"`
	SendTime         time.Time `json:"send_time"`
	ValidationStatus string    `json:"validation_status"`
}
