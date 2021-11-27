package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.config.properties.EmailConfigProperties.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class EmailConfigPropertiesIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private EmailConfigProperties emailConfigProperties;

    @Value(value = "${spring.mail.host}")
    private String host;

    @Value(value = "${spring.mail.username}")
    private String username;

    @Value(value = "${spring.mail.password}")
    private String password;

    @Value(value = "${spring.mail.from}")
    private String from;

    @Value(value = "${spring.mail.properties.mail.transport.protocol}")
    private String protocol;

    @Value(value = "${spring.mail.properties.mail.smtp.port}")
    private String port;

    @Value(value = "${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value(value = "${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttlsEnabled;

    @Test
    void testLoadConfigProperties() {
        assertNotNull(this.host);
        assertEquals(this.host, this.emailConfigProperties.getHost());

        assertNotNull(this.username);
        assertEquals(this.username, this.emailConfigProperties.getUsername());

        assertNotNull(this.password);
        assertEquals(this.password, this.emailConfigProperties.getPassword());

        assertNotNull(this.from);
        assertEquals(this.from, this.emailConfigProperties.getFrom());

        assertNotNull(this.protocol);
        assertEquals(this.protocol, this.emailConfigProperties.getProperty(PROPERTY_TRANSPORT_PROTOCOL));

        assertNotNull(this.port);
        assertEquals(this.port, this.emailConfigProperties.getProperty(PROPERTY_SMTP_PORT));

        assertNotNull(this.auth);
        assertEquals(this.auth, this.emailConfigProperties.getProperty(PROPERTY_SMTP_AUTH));

        assertNotNull(this.starttlsEnabled);
        assertEquals(this.starttlsEnabled, this.emailConfigProperties.getProperty(PROPERTY_SMTP_STARTTLS_ENABLE));
    }
}
