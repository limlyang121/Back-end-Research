package com.myapp.restapi.researchconference.entity.Admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles", schema = "public")
public class Role {
    @Id
    @Column(name = "roles")
    private String role;
    @Column(name = "Description")
    private String desc;

}
