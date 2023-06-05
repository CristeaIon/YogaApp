package storage

import (
	"context"
	m "yuga_back/internal/auth/model"
	"yuga_back/internal/restore/model"
)

type RestorePasswordRepository interface {
	RestorePassword(ctx context.Context, dto model.ValidationData) (model.ValidationData, error)
	ValidateCode(ctx context.Context, code model.ValidateCodeDTO) (model.ValidationData, error)
	UpdatePassword(ctx context.Context, email string, password string) (m.User, error)
}
