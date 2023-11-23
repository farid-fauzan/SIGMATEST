package com.sigma.rest.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "db_sigma")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nama", nullable = true)
    private String nama;

    @Column(name = "telepon", nullable = true)
    private String telepon;

    @Column(name = "is_validated", nullable = false )
    private boolean isValidated;

    @Column(name = "otp", nullable = true )
    private String otp;



}
