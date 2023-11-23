package com.sigma.rest.services;

import com.sigma.rest.entity.OtpUser;
import com.sigma.rest.entity.User;
import com.sigma.rest.respository.OtpUserRepository;
import com.sigma.rest.respository.UserRepository;
import com.sigma.rest.utility.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServices userServices;

    @Autowired
    OtpUserRepository otpUserRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public ResponseEntity resetPassword (String email) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            User user = userRepository.findByEmail(email);

            if (user != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH, 1); // Menambah 1 hari

                String otp = userServices.generateOTP();

                OtpUser otpUser = new OtpUser();
                otpUser.setEmail(user.getEmail());
                otpUser.setOtp(otp);
                otpUser.setTglBerlaku(calendar.getTime());
                otpUser.setValid(true);


                emailService.sendEmail(otpUser.getEmail(), "ResetPassword", "Kode OTP untuk reset password : "+otp+" " +
                        "dapat digunakan dalam 24 jam");
                otpUserRepository.save(otpUser);

                msg.setStatus(true);
                msg.setMessage("Cek Email untuk mendapatkan OTP");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);
            }else {
                msg.setStatus(false);
                msg.setMessage("Request Reset Password Gagal");
                msg.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public ResponseEntity confirmResetPassword (String email, String otp, String newPassword) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            OtpUser otpUser = otpUserRepository.findOtp(email, otp, new Date());

            if (otpUser != null) {
                User user = userRepository.findByEmail(email);
                String encryptedPassword = encoder.encode(newPassword);

                user.setPassword(encryptedPassword);
                userRepository.save(user);

                otpUser.setValid(false);
                otpUserRepository.save(otpUser);

                msg.setStatus(true);
                msg.setMessage("Berhasil Ubah Password, Silahkan Login");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);
            }else {
                msg.setStatus(false);
                msg.setMessage("Otentikasi Gagal");
                msg.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

}
