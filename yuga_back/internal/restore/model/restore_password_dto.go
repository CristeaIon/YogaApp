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
	Code string `json:"code"`
}
