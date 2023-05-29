package token

import (
	"github.com/gofrs/uuid"
	"github.com/pkg/errors"
	"time"
)

type Payload struct {
	ID        uuid.UUID `json:"id"`
	UserName  string    `json:"user_name"`
	Email     string    `json:"email"`
	IssuedAt  time.Time `json:"issued_at"`
	ExpiredAt time.Time `json:"expired_at"`
}

func NewPayload(username string, email string, duration time.Duration) (*Payload, error) {
	ID, err := uuid.NewV4()

	if err != nil {
		return nil, err
	}

	payload := &Payload{
		ID:        ID,
		UserName:  username,
		Email:     email,
		IssuedAt:  time.Now(),
		ExpiredAt: time.Now().Add(duration),
	}
	return payload, err
}

func (p *Payload) Valid() error {
	if time.Now().After(p.ExpiredAt) {
		return errors.New("token is expired")
	}
	return nil
}
