package storage

import (
	"context"
	"yuga_back/internal/restore/model"
)

type RestorePasswordRepository interface {
	RestorePassword(ctx context.Context, dto model.ValidationData) (model.ValidationData, error)
	ValidateCode(ctx context.Context, code model.ValidateCodeDTO) (model.ValidationData, error)
	UpdatePassword(ctx context.Context, dto model.UpdatePasswordDTO)
}
