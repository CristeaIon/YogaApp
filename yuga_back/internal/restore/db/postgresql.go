package db

import (
	"context"
	"errors"
	"fmt"
	"github.com/jackc/pgconn"
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

func (r repository) ValidateCode(ctx context.Context, code model.ValidateCodeDTO) (model.ValidationData, error) {
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

func (r repository) UpdatePassword(ctx context.Context, dto model.UpdatePasswordDTO) {
	//TODO implement me
	panic("implement me")
}

func NewRepository(client postgres.Client, log *logger.Logger) storage.RestorePasswordRepository {
	return &repository{client: client, log: log}
}
