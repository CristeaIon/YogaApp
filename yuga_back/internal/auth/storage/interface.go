package storage

import (
	"context"
	"yuga_back/internal/auth/model"
)

type AuthRepository interface {
	Create(ctx context.Context, user model.UserDAO) (model.UserDAO, error)
	Update(ctx context.Context, user model.UserDAO) error
	FindOne(ctx context.Context, email string) (model.UserDAO, error)
	Delete(ctx context.Context, id string) (model.UserDAO, error)
}
