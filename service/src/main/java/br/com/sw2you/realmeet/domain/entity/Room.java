package br.com.sw2you.realmeet.domain.entity;

import static java.util.Objects.isNull;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public Room() {}

    private Room(Long id, String name, Integer seats, Boolean active) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        if (isNull(this.active)) {
            this.active = true;
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getSeats() {
        return this.seats;
    }

    public Boolean getActive() {
        return this.active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return (
            Objects.equals(this.id, room.id) &&
            Objects.equals(this.name, room.name) &&
            Objects.equals(this.seats, room.seats) &&
            Objects.equals(this.active, room.active)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.seats, this.active);
    }

    @Override
    public String toString() {
        return (
            "Room{" +
            "id=" +
            this.id +
            ", name='" +
            this.name +
            '\'' +
            ", seats=" +
            this.seats +
            ", active=" +
            this.active +
            '}'
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Integer seats;
        private Boolean active;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder seats(Integer seats) {
            this.seats = seats;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Room build() {
            return new Room(this.id, this.name, this.seats, this.active);
        }
    }
}
