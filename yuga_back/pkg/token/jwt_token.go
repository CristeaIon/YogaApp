package token

import (
	"errors"
	"fmt"
	"github.com/golang-jwt/jwt/v4"
	"time"
)

const minSecretSize = 32

type JWTCreator struct {
	secretKey string
}

func NewJWTCreator(secretKey string) (Creator, error) {
	if len(secretKey) < minSecretSize {
		return nil, fmt.Errorf("invalid key size: ,ust be at least %d characters", minSecretSize)
	}
	return &JWTCreator{secretKey: secretKey}, nil
}

func (c *JWTCreator) CreateToken(username string, email string, duration time.Duration) (string, error) {
	payload, err := NewPayload(username, email, duration)
	if err != nil {
		return "", err
	}

	jwtToken := jwt.NewWithClaims(jwt.SigningMethodHS256, payload)
	token, err := jwtToken.SignedString([]byte(c.secretKey))

	return token, nil
}

func (c *JWTCreator) VerifyToken(token string) (*Payload, error) {
	keyFunc := func(token *jwt.Token) (interface{}, error) {
		_, ok := token.Method.(*jwt.SigningMethodHMAC)
		if !ok {
			return nil, errors.New("token is invalid")
		}

		return []byte(c.secretKey), nil
	}
	jwtToken, err := jwt.ParseWithClaims(token, &Payload{}, keyFunc)

	if err != nil {
		verr, ok := err.(*jwt.ValidationError)
		if ok && errors.Is(verr.Inner, errors.New("token is expired")) {
			return nil, errors.New("token is expired")
		}
		return nil, errors.New("token is not valid")
	}

	payload, ok := jwtToken.Claims.(*Payload)

	if !ok {
		return nil, errors.New("token is not valid")
	}
	return payload, nil
}
