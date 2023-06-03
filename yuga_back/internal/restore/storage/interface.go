package storage

import (
	"context"
	"yuga_back/internal/restore/model"
)

type RestorePasswordRepository interface {
	RestorePassword(ctx context.Context, dto model.RestorePasswordDTO)
	ValidateCode(ctx context.Context, code model.ValidateCodeDTO)
	UpdatePassword(ctx context.Context, dto model.UpdatePasswordDTO)
}
