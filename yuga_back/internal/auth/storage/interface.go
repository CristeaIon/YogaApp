package storage

import (
	"context"
	"yuga_back/internal/auth/model"
)

type AuthRepository interface {
	Create(ctx context.Context, user model.User) (model.User, error)
	Update(ctx context.Context, user model.User) error
	FindOne(ctx context.Context, email string) (model.User, error)
	Delete(ctx context.Context, id string) (model.User, error)
}
