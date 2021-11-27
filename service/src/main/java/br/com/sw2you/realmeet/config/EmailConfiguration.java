package br.com.sw2you.realmeet.config;

import static br.com.sw2you.realmeet.config.properties.EmailConfigProperties.*;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
    private final EmailConfigProperties emailConfigProperties;

    public EmailConfiguration(EmailConfigProperties emailConfigProperties) {
        this.emailConfigProperties = emailConfigProperties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfigProperties.getHost());
        mailSender.setPort(Integer.parseInt(this.emailConfigProperties.getProperty(PROPERTY_SMTP_PORT)));
        mailSender.setUsername(this.emailConfigProperties.getUsername());
        mailSender.setPassword(this.emailConfigProperties.getPassword());

        var properties = mailSender.getJavaMailProperties();
        properties.put(
            PROPERTY_TRANSPORT_PROTOCOL,
            this.emailConfigProperties.getProperty(PROPERTY_TRANSPORT_PROTOCOL)
        );
        properties.put(PROPERTY_SMTP_AUTH, this.emailConfigProperties.getProperty(PROPERTY_SMTP_AUTH));
        properties.put(
            PROPERTY_SMTP_STARTTLS_ENABLE,
            this.emailConfigProperties.getProperty(PROPERTY_SMTP_STARTTLS_ENABLE)
        );

        return mailSender;
    }
}
