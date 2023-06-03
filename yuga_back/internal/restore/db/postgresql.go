package db

import (
	"context"
	"yuga_back/internal/restore/model"
	"yuga_back/internal/restore/storage"
	"yuga_back/pkg/client/postgres"
	"yuga_back/pkg/logger"
)

type repository struct {
	client postgres.Client
	log    *logger.Logger
}

func (r repository) RestorePassword(ctx context.Context, dto model.RestorePasswordDTO) {
	//TODO implement me
	panic("implement me")
}

func (r repository) ValidateCode(ctx context.Context, code model.ValidateCodeDTO) {
	//TODO implement me
	panic("implement me")
}

func (r repository) UpdatePassword(ctx context.Context, dto model.UpdatePasswordDTO) {
	//TODO implement me
	panic("implement me")
}

func NewRepository(client postgres.Client, log *logger.Logger) storage.RestorePasswordRepository {
	return &repository{client: client, log: log}
}
