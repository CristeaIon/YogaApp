package config

import (
	"github.com/ilyakaznacheev/cleanenv"
	"sync"
	"yuga_back/pkg/logger"
)

type Config struct {
	ISDebug *bool `yaml:"is_debug" env-required:"true"`
	Listen  struct {
		Type   string `yaml:"type" env-default:"port"`
		BindIP string `yaml:"bind_ip" env-default:"127.0.0.1"`
		Port   string `yaml:"port" env-default:"8080"`
	} `yaml:"listen"`
	JWTSecret string        `yaml:"jwt_secret"`
	Storage   StorageConfig `yaml:"storage"`
	SMTPConf  struct {
		SendGridApiKey string `yaml:"sendgrid_api_key"`
		TemplateId     string `yaml:"template_id"`
	} `yaml:"smtp_conf"`
}

type StorageConfig struct {
	Host     string `yaml:"host"`
	Port     string `yaml:"port"`
	Database string `yaml:"database"`
	Username string `yaml:"username"`
	Password string `yaml:"password"`
}

var instance *Config
var once sync.Once

func GetConfig() *Config {
	once.Do(func() {
		log := logger.GetLogger()
		log.Info("read app configuration")
		instance = &Config{}
		if err := cleanenv.ReadConfig("config.yaml", instance); err != nil {
			help, _ := cleanenv.GetDescription(instance, nil)
			log.Info(help)
			log.Fatal(err)
		}
	})
	return instance
}
