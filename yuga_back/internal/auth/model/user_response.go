package model

import "time"

type UserResponse struct {
	FullName     string    `json:"fullName"`
	Email        string    `json:"email"`
	Phone        string    `json:"phone"`
	Token        string    `json:"token"`
	RefreshToken string    `json:"refreshToken"`
	CreatedAt    time.Time `json:"createdAt"`
	UpdatedAt    time.Time `json:"updatedAt"`
}

type DeleteUserRequest struct {
	ID string `uri:"id" binding:"required"`
}

type DeleteUserResponse struct {
	FullName  string    `json:"fullName"`
	Email     string    `json:"email"`
	Phone     string    `json:"phone"`
	CreatedAt time.Time `json:"createdAt"`
	UpdatedAt time.Time `json:"updatedAt"`
}
