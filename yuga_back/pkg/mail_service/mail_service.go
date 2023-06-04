package mail_service

import (
	"github.com/sendgrid/sendgrid-go"
	"github.com/sendgrid/sendgrid-go/helpers/mail"
	"yuga_back/internal/config"
	"yuga_back/pkg/logger"
)

type MailService interface {
	CreateMail(mailReq *Mail) []byte
	SendMail(mailReq *Mail) error
	NewMail(from string, to []string, subject string, data *MailData) *Mail
}

type MailData struct {
	Name string
	Code string
}

type Mail struct {
	from    string
	to      []string
	subject string
	data    *MailData
}

type sgMailService struct {
	cfg *config.Config
	log *logger.Logger
}

func (s *sgMailService) CreateMail(mailReq *Mail) []byte {
	m := mail.NewV3Mail()

	from := mail.NewEmail("yoga_app", mailReq.from)
	m.SetFrom(from)
	m.SetTemplateID(s.cfg.SMTPConf.TemplateId)
	p := mail.NewPersonalization()

	tos := make([]*mail.Email, 0)

	for _, to := range mailReq.to {
		tos = append(tos, mail.NewEmail("user", to))
	}
	p.AddTos(tos...)
	p.SetDynamicTemplateData("Name", mailReq.data.Name)
	p.SetDynamicTemplateData("Code", mailReq.data.Code)

	m.AddPersonalizations(p)
	return mail.GetRequestBody(m)
}

func (s *sgMailService) SendMail(mailReq *Mail) error {
	request := sendgrid.GetRequest(s.cfg.SMTPConf.SendGridApiKey, "/v3/mail/send", "https://api.sendgrid.com")
	request.Method = "POST"
	var Body = s.CreateMail(mailReq)

	request.Body = Body

	response, err := sendgrid.API(request) //{"errors":[{"message":"Permission denied, wrong credentials","field":null,"help":null}]}
	if err != nil {
		s.log.Errorf("unable to send email, error: %v", err)
		return err
	}
	s.log.Infof(" mail send successfully status code: %d", response.StatusCode)
	return nil
}

func (s *sgMailService) NewMail(from string, to []string, subject string, data *MailData) *Mail {
	return &Mail{
		from:    from,
		to:      to,
		subject: subject,
		data:    data,
	}
}

func NewMailService(cfg *config.Config, log *logger.Logger) MailService {
	return &sgMailService{cfg: cfg, log: log}
}
