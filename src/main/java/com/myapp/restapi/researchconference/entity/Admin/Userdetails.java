package com.myapp.restapi.researchconference.entity.Admin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_details", schema = "public")
public class Userdetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_seq")
    @SequenceGenerator(name = "user_details_seq", sequenceName = "user_details_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @Size(min = 2)
    private String firstName;
    @Column(name = "last_name")
    @Size(min = 1)
    private String lastName;

    @Column(name = "email")
    @Email(message = "Please input correct email address")
    private String email;

    @Positive
    private int height;

    @Positive
    private int weight;

    @OneToOne (mappedBy = "userdetails", cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE
    })
    @JsonBackReference
    private User user;

    public Userdetails() {
    }

    public Userdetails(String firstName, String lastName, int height, int weight, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Userdetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
