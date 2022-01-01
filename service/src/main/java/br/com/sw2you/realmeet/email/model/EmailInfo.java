package br.com.sw2you.realmeet.email.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmailInfo {
    private final String from;
    private final List<String> to;
    private final List<String> cc;
    private final List<String> bcc;
    private final String subject;
    private final List<Attachment> attachments;
    private final String template;
    private final Map<String, Object> templateData;

    private EmailInfo(Builder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
        this.subject = builder.subject;
        this.attachments = builder.attachments;
        this.template = builder.template;
        this.templateData = builder.templateData;
    }

    public String getFrom() {
        return this.from;
    }

    public List<String> getTo() {
        return this.to;
    }

    public List<String> getCc() {
        return this.cc;
    }

    public List<String> getBcc() {
        return this.bcc;
    }

    public String getSubject() {
        return this.subject;
    }

    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    public String getTemplate() {
        return this.template;
    }

    public Map<String, Object> getTemplateData() {
        return this.templateData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailInfo emailInfo = (EmailInfo) o;
        return (
            Objects.equals(this.from, emailInfo.from) &&
            Objects.equals(this.to, emailInfo.to) &&
            Objects.equals(this.cc, emailInfo.cc) &&
            Objects.equals(this.bcc, emailInfo.bcc) &&
            Objects.equals(this.subject, emailInfo.subject) &&
            Objects.equals(this.attachments, emailInfo.attachments) &&
            Objects.equals(this.template, emailInfo.template) &&
            Objects.equals(this.templateData, emailInfo.templateData)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.from,
            this.to,
            this.cc,
            this.bcc,
            this.subject,
            this.attachments,
            this.template,
            this.templateData
        );
    }

    @Override
    public String toString() {
        return (
            "EmailInfo{" +
            "from='" +
            this.from +
            '\'' +
            ", to=" +
            this.to +
            ", cc=" +
            this.cc +
            ", bcc=" +
            this.bcc +
            ", subject='" +
            this.subject +
            '\'' +
            ", attachments=" +
            this.attachments +
            ", template='" +
            this.template +
            '\'' +
            ", templateData=" +
            this.templateData +
            '}'
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String from;
        private List<String> to;
        private List<String> cc;
        private List<String> bcc;
        private String subject;
        private List<Attachment> attachments;
        private String template;
        private Map<String, Object> templateData;

        private Builder() {}

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(List<String> to) {
            this.to = to;
            return this;
        }

        public Builder cc(List<String> cc) {
            this.cc = cc;
            return this;
        }

        public Builder bcc(List<String> bcc) {
            this.bcc = bcc;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder attachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Builder template(String template) {
            this.template = template;
            return this;
        }

        public Builder templateData(Map<String, Object> templateData) {
            this.templateData = templateData;
            return this;
        }

        public EmailInfo build() {
            return new EmailInfo(this);
        }
    }
}
