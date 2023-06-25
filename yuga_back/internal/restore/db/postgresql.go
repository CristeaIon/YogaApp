package db

import (
	"context"
	"errors"
	"fmt"
	"github.com/jackc/pgconn"
	m "yuga_back/internal/auth/model"
	"yuga_back/internal/restore/model"
	"yuga_back/internal/restore/storage"
	"yuga_back/pkg/client/postgres"
	"yuga_back/pkg/logger"
)

type repository struct {
	client postgres.Client
	log    *logger.Logger
}

func (r repository) RestorePassword(ctx context.Context, data model.ValidationData) (model.ValidationData, error) {

	query := `INSERT INTO otp_code(contact,token, send_status, send_time)
VALUES ($1,$2, $3,$4)
RETURNING id,contact, send_status,send_time`
	var resp model.ValidationData

	if err := r.client.QueryRow(ctx, query, data.Contact, data.Token, data.SendStatus, data.SendTime).Scan(&resp.ID, &resp.Contact, &resp.SendStatus, &resp.SendTime); err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) {
			pgErr = err.(*pgconn.PgError)
			newErr := fmt.Errorf(fmt.Sprintf("SQL Error: %s, Detail: %s, Where: %s, Code: %s, SQLState: %s", pgErr.Message, pgErr.Detail, pgErr.Where, pgErr.Code, pgErr.SQLState()))
			r.log.Error(newErr)
			return model.ValidationData{}, newErr
		}
		return model.ValidationData{}, err
	}

	return resp, nil
}

func (r repository) ValidateCode(ctx context.Context, code model.ValidateCodeRequest) (model.ValidationData, error) {
	query := `SELECT id,contact,token, send_status, send_time FROM otp_code
WHERE contact=$1 AND id=$2`

	var resp model.ValidationData
	if err := r.client.QueryRow(ctx, query, code.Contact, code.ID).Scan(&resp.ID, &resp.Contact, &resp.Token, &resp.SendStatus, &resp.SendTime); err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) {
			pgErr = err.(*pgconn.PgError)
			newErr := fmt.Errorf(fmt.Sprintf("SQL Error: %s, Detail: %s, Where: %s, Code: %s, SQLState: %s", pgErr.Message, pgErr.Detail, pgErr.Where, pgErr.Code, pgErr.SQLState()))
			r.log.Error(newErr)
			return model.ValidationData{}, newErr
		}
		return model.ValidationData{}, err
	}

	return resp, nil
}

func (r repository) UpdatePassword(ctx context.Context, email string, password string) (m.UserDAO, error) {
	query := `UPDATE users
SET password_hash = $1
WHERE email = $2
RETURNING id, full_name,email,phone,created_at,updated_at`

	var u m.UserDAO

	if err := r.client.QueryRow(ctx, query, password, email).Scan(&u.ID, &u.FullName, &u.Email, &u.Phone, &u.CreatedAt, &u.UpdatedAt); err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) {
			pgErr = err.(*pgconn.PgError)
			newErr := fmt.Errorf(fmt.Sprintf("SQL Error: %s, Detail: %s, Where: %s, Code: %s, SQLState: %s", pgErr.Message, pgErr.Detail, pgErr.Where, pgErr.Code, pgErr.SQLState()))
			r.log.Error(newErr)
			return u, newErr
		}
		return u, err
	}

	return u, nil
}

func NewRepository(client postgres.Client, log *logger.Logger) storage.RestorePasswordRepository {
	return &repository{client: client, log: log}
}
