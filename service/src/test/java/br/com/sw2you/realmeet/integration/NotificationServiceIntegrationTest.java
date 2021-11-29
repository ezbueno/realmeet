package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.email.TemplateType.*;
import static br.com.sw2you.realmeet.util.Constants.ALLOCATION;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newAllocationBuilder;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import br.com.sw2you.realmeet.config.properties.TemplateConfigProperties;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class NotificationServiceIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private NotificationService victim;

    @Autowired
    private TemplateConfigProperties templateConfigProperties;

    @Autowired
    private EmailConfigProperties emailConfigProperties;

    @MockBean
    private EmailSender emailSender;

    private Allocation allocation;

    @Override
    protected void setupEach() throws Exception {
        this.allocation = newAllocationBuilder(newRoomBuilder().build()).build();
    }

    @Test
    void testNotifyAllocationCreated() {
        this.victim.notifyAllocationCreated(this.allocation);
        this.testInteraction(ALLOCATION_CREATED);
    }

    @Test
    void testNotifyAllocationUpdated() {
        this.victim.notifyAllocationUpdated(this.allocation);
        this.testInteraction(ALLOCATION_UPDATED);
    }

    @Test
    void testNotifyAllocationDeleted() {
        this.victim.notifyAllocationDeleted(this.allocation);
        this.testInteraction(ALLOCATION_DELETED);
    }

    private void testInteraction(TemplateType templateType) {
        var emailTemplate = this.templateConfigProperties.getEmailTemplate(templateType);

        verify(this.emailSender)
            .send(
                argThat(
                    emailInfo ->
                        emailInfo.getSubject().equals(emailTemplate.getSubject()) &&
                        emailInfo.getTemplate().equals(emailTemplate.getTemplateName()) &&
                        emailInfo.getTo().get(0).equals(this.allocation.getEmployee().getEmail()) &&
                        emailInfo.getFrom().equals(this.emailConfigProperties.getFrom()) &&
                        emailInfo.getTemplateData().get(ALLOCATION).equals(this.allocation)
                )
            );
    }
}
