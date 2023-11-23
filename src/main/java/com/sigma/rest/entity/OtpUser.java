package com.sigma.rest.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp_user", schema = "db_sigma")
public class OtpUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_otp", nullable = false)
    private Long idOtp;

    @Column(name = "otp", nullable = true )
    private String otp;

    @Column(name = "email", nullable = true )
    private String email;

    @Column(name = "tgl_berlaku", nullable = true )
    private Date tglBerlaku;

    @Column(name = "is_valid", nullable = false )
    private boolean isValid;

}
