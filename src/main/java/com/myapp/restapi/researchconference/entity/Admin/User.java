package com.myapp.restapi.researchconference.entity.Admin;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "user",schema = "public")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;

    @ManyToOne (cascade =
            {CascadeType.DETACH,CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REFRESH})
    @JoinColumn(name = "roles", referencedColumnName = "roles")
    private Role role;
    @Column(name = "active")
    private int active;

    @OneToOne(cascade = {
            CascadeType.ALL
    })
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private Userdetails userdetails;


}
