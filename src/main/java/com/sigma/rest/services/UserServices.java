package com.sigma.rest.services;


import com.sigma.rest.entity.OtpUser;
import com.sigma.rest.entity.User;
import com.sigma.rest.pojo.LoginPojo;
import com.sigma.rest.pojo.UserPojo;
import com.sigma.rest.respository.OtpUserRepository;
import com.sigma.rest.respository.UserRepository;
import com.sigma.rest.utility.CreditCardInfoEncryptor;
import com.sigma.rest.utility.JwtUtil;
import com.sigma.rest.utility.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpUserRepository otpUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    CreditCardInfoEncryptor encryptor;

    public ResponseEntity register(UserPojo dataUser) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            List<User> userExist = userRepository.findByEmailList(dataUser.getEmail());

            if(userExist.size() > 0) {
                msg.setStatus(false);
                msg.setMessage("User dengan email " + dataUser.getEmail() + " sudah terdaftar");
                msg.setData(null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
            }

            if(!dataUser.getPassword().equals(dataUser.getPasswordConfirm())) {
                msg.setStatus(false);
                msg.setMessage("Konfirmasi password anda tidak sesuai!");
                msg.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            }


            String password = dataUser.getPassword();
            String encryptedPassword = encoder.encode(password);

//          Set Data
            User user = new User();
            user.setEmail(dataUser.getEmail());
            user.setPassword(encryptedPassword);
            user.setNama(dataUser.getNama());
            user.setTelepon(dataUser.getTelepon());
            user.setValidated(false);

            String otp = generateOTP();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Menambah 1 hari

            OtpUser otpUser = new OtpUser();
            otpUser.setEmail(user.getEmail());
            otpUser.setOtp(otp);
            otpUser.setTglBerlaku(calendar.getTime());
            otpUser.setValid(true);

            emailService.sendEmail(otpUser.getEmail(), "OTP", "Kode OTP : "+otp+" " +
                    "dapat digunakan dalam 24 jam");

            userRepository.save(user);
            otpUserRepository.save(otpUser);

            msg.setStatus(true);
            msg.setMessage("Berhasil Register, Mohon lakukan verifikasi Email!");
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public ResponseEntity login (LoginPojo dataLogin) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            User user = userRepository.findByEmail(dataLogin.getEmail());

            if(user == null) {
                msg.setStatus(false);
                msg.setMessage("User dengan email " + dataLogin.getEmail() + " Tidak ditemukan");
                msg.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }

//            Cek password
            boolean isPasswordMatch = encoder.matches(dataLogin.getPassword(), user.getPassword());
            if(!isPasswordMatch){
                msg.setStatus(false);
                msg.setMessage("Password anda salah");
                msg.setData(null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
            }

            String token = jwtUtil.generateToken(user);

            msg.setStatus(true);
            msg.setMessage("Berhasil Login");
            msg.setData(token);

            return ResponseEntity.ok().body(msg);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public String generateOTP() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public ResponseEntity validate (String email, String otp) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            OtpUser otpUser = otpUserRepository.findOtp(email, otp, new Date());

            if (otpUser != null) {
                User user = userRepository.findByEmail(email);
                user.setValidated(true);
                userRepository.save(user);

                otpUser.setValid(false);
                otpUserRepository.save(otpUser);

                msg.setStatus(true);
                msg.setMessage("Otentikasi Berhasil");
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

    public ResponseEntity status (String email) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                msg.setStatus(true);
                msg.setMessage("Status: TIDAK TERDAFTAR");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);
            }else if (user.isValidated()) {
                msg.setStatus(true);
                msg.setMessage("Status: TERVALIDASI");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);
            }else {
                msg.setStatus(true);
                msg.setMessage("BELUM TERVALIDASI");
                msg.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public ResponseEntity logout (String tokenHeader) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                tokenHeader.substring(7);
            }

            jwtUtil.invalidateToken(tokenHeader);


            msg.setStatus(true);
            msg.setMessage("Logout Berhasil");
            msg.setData(null);
            return ResponseEntity.ok().body(msg);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }




}
