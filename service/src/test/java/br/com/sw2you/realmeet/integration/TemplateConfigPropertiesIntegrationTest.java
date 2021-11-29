package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.email.TemplateType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.sw2you.realmeet.config.properties.TemplateConfigProperties;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class TemplateConfigPropertiesIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TemplateConfigProperties templateConfigProperties;

    @Value(value = "${realmeet.email.templates.allocationCreated.subject}")
    private String allocationCreatedSubject;

    @Value(value = "${realmeet.email.templates.allocationCreated.templateName}")
    private String allocationCreatedTemplateName;

    @Value(value = "${realmeet.email.templates.allocationUpdated.subject}")
    private String allocationUpdatedSubject;

    @Value(value = "${realmeet.email.templates.allocationUpdated.templateName}")
    private String allocationUpdatedTemplateName;

    @Value(value = "${realmeet.email.templates.allocationDeleted.subject}")
    private String allocationDeletedSubject;

    @Value(value = "${realmeet.email.templates.allocationDeleted.templateName}")
    private String allocationDeletedTemplateName;

    @Test
    void testLoadConfigProperties() {
        assertNotNull(this.allocationCreatedSubject);
        assertEquals(
            this.allocationCreatedSubject,
            this.templateConfigProperties.getEmailTemplate(ALLOCATION_CREATED).getSubject()
        );

        assertNotNull(this.allocationCreatedTemplateName);
        assertEquals(
            this.allocationCreatedTemplateName,
            this.templateConfigProperties.getEmailTemplate(ALLOCATION_CREATED).getTemplateName()
        );

        assertNotNull(this.allocationUpdatedSubject);
        assertEquals(
            this.allocationUpdatedSubject,
            this.templateConfigProperties.getEmailTemplate(ALLOCATION_UPDATED).getSubject()
        );

        assertNotNull(this.allocationUpdatedTemplateName);
        assertEquals(
            this.allocationUpdatedTemplateName,
            this.templateConfigProperties.getEmailTemplate(ALLOCATION_UPDATED).getTemplateName()
        );

        assertNotNull(this.allocationDeletedSubject);
        assertEquals(
            this.allocationDeletedSubject,
            this.templateConfigProperties.getEmailTemplate(ALLOCATION_DELETED).getSubject()
        );

        assertNotNull(this.allocationDeletedTemplateName);
        assertEquals(
            this.allocationDeletedTemplateName,
            this.templateConfigProperties.getEmailTemplate(ALLOCATION_DELETED).getTemplateName()
        );
    }
}
