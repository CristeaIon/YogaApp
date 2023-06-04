package model

import "time"

type RestorePasswordDTO struct {
	Email string `json:"email" binding:"required,email"`
}

type RestorePasswordResponse struct {
	ID         string    `json:"id"`
	Contact    string    `json:"contact"`
	SendStatus string    `json:"sendStatus"`
	SendTime   time.Time `json:"sendTime"`
}

type UpdatePasswordDTO struct {
	Password string `json:"password" binding:"required,min=6"`
}
type ValidateCodeDTO struct {
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
