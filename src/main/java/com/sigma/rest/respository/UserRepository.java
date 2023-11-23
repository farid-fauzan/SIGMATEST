package com.sigma.rest.respository;

import com.sigma.rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    @Query(value = "SELECT * FROM user WHERE email =:email" , nativeQuery = true)
    List<User> findByEmailList(@Param("email") String email);

    @Query(value = "SELECT * FROM user WHERE email =:email" , nativeQuery = true)
    User findByEmail(@Param("email") String email);

}
