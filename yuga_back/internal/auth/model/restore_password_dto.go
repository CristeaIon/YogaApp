package model

type RestorePasswordDTO struct {
	Email string `json:"email" binding:"required,email"`
}
