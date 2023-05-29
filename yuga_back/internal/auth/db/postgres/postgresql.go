package postgres

import (
	"context"
	"errors"
	"fmt"
	"github.com/jackc/pgconn"
	"yuga_back/internal/auth/model"
	"yuga_back/internal/auth/storage"
	"yuga_back/pkg/client/postgres"
	"yuga_back/pkg/logger"
)

type repository struct {
	client postgres.Client
	log    *logger.Logger
}

func NewRepository(client postgres.Client, log *logger.Logger) storage.AuthRepository {
	return &repository{client: client, log: log}
}

func (r *repository) Create(ctx context.Context, user model.User) error {
	query := `INSERT INTO users 
				(full_name, email, phone, password_hash)
			  VALUES ($1,$2,$3,$4)
			  RETURNING id 
`
	if err := r.client.QueryRow(ctx, query).Scan(&user); err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) {
			pgErr = err.(*pgconn.PgError)
			newErr := fmt.Errorf(fmt.Sprintf("SQL Error: %s, Detail: %s, Where: %s, Code: %s, SQLState: %s", pgErr.Message, pgErr.Detail, pgErr.Where, pgErr.Code, pgErr.SQLState()))
			r.log.Error(newErr)
			return newErr
		}
		return err
	}

	return nil
}
func (r *repository) Update(ctx context.Context, user model.User) error {
	query := `INSERT INTO users 
				(full_name, email, phone, password_hash)
			  VALUES ($1,$2,$3,$4)
			  RETURNING id 
`
	if err := r.client.QueryRow(ctx, query).Scan(&user); err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) {
			pgErr = err.(*pgconn.PgError)
			newErr := fmt.Errorf(fmt.Sprintf("SQL Error: %s, Detail: %s, Where: %s, Code: %s, SQLState: %s", pgErr.Message, pgErr.Detail, pgErr.Where, pgErr.Code, pgErr.SQLState()))
			r.log.Error(newErr)
			return newErr
		}
		return err
	}

	return nil
}
func (r *repository) FindOne(ctx context.Context, id string) (model.User, error) {
	var user model.User
	return user, nil
}
func (r *repository) Delete(ctx context.Context, id string) error {

	return nil
}