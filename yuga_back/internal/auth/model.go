package auth

type User struct {
	ID       string `json:"id"`
	FullName string `json:"fullName"`
	Email    string `json:"email"`
	Phone    string `json:"phone"`
}

type CreateUserDTO struct {
	FullName string `json:"fullName"`
	Email    string `json:"email"`
	Phone    string `json:"phone"`
	Password string `json:"password"`
}

type LoginUserDTO struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}
type RestorePasswordDTO struct {
	Email string `json:"email"`
}
type ValidateCodeDTO struct {
	Code string `json:"code"`
}
type UpdatePasswordDTO struct {
	Password string `json:"password"`
}
