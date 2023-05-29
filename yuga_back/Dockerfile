#Build Stage
FROM golang:1.20.4-alpine AS builder
LABEL authors="icristea"

WORKDIR /app

COPY . .
RUN go build -o build cmd/main/app.go

# Run Stage
FROM alpine:3.18.0
WORKDIR /app
COPY --from=builder /app/build .

EXPOSE 8080
CMD ["/app/build"]

#ENTRYPOINT ["top", "-b"]