package util

import (
	"math/rand"
	"strings"
)

const digitsBytes = "0123456789"

func GenerateRandomCode(n int) string {
	sb := strings.Builder{}
	sb.Grow(n)
	for i := 0; i < n; i++ {
		idx := rand.Int63() % int64(len(digitsBytes))
		sb.WriteByte(digitsBytes[idx])
	}
	return sb.String()
}
