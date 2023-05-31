package model

type UpdatePasswordDTO struct {
	Password string `json:"password" binding:"required,min=6"`
}
