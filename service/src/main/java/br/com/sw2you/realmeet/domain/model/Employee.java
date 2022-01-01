package br.com.sw2you.realmeet.domain.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Employee {
    @Column(name = "employee_name")
    private String name;

    @Column(name = "employee_email")
    private String email;

    private Employee(Builder builder) {
        name = builder.name;
        email = builder.email;
    }

    public Employee() {}

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.name, employee.name) && Objects.equals(this.email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.email);
    }

    @Override
    public String toString() {
        return "Employee{" + "name='" + this.name + '\'' + ", email='" + this.email + '\'' + '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String email;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}
