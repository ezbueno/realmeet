package br.com.sw2you.realmeet.email.model;

import java.io.InputStream;
import java.util.Objects;

public class Attachment {
    private final InputStream inputStream;
    private final String contentType;
    private final String fileName;

    private Attachment(Builder builder) {
        this.inputStream = builder.inputStream;
        this.contentType = builder.contentType;
        this.fileName = builder.fileName;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return (
            Objects.equals(this.inputStream, that.inputStream) &&
            Objects.equals(this.contentType, that.contentType) &&
            Objects.equals(this.fileName, that.fileName)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.inputStream, this.contentType, this.fileName);
    }

    @Override
    public String toString() {
        return (
            "Attachment{" +
            "inputStream=" +
            this.inputStream +
            ", contentType='" +
            this.contentType +
            '\'' +
            ", fileName='" +
            this.fileName +
            '\'' +
            '}'
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private InputStream inputStream;
        private String contentType;
        private String fileName;

        private Builder() {}

        public Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Attachment build() {
            return new Attachment(this);
        }
    }
}
