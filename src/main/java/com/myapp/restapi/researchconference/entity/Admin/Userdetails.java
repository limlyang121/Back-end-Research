package com.myapp.restapi.researchconference.entity.Admin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;
    private int height;
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
