package com.KameHouse.ecom.entity;

import com.KameHouse.ecom.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="users")

public class User {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String pasword;

    private String name;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")

    private byte[] img;



}
