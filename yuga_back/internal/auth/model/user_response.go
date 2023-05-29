package model

import "time"

type UserResponse struct {
	FullName     string    `json:"fullName"`
	Email        string    `json:"email"`
	Phone        string    `json:"phone"`
	Token        string    `json:"token"`
	RefreshToken string    `json:"refresh_token"`
	CreatedAt    time.Time `json:"created_at"`
	UpdatedAt    time.Time `json:"updated_at"`
}
