package com.sigma.rest.respository;

import com.sigma.rest.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository <Blog, Long> {
    @Query(value = "SELECT * FROM blog WHERE id =:id" , nativeQuery = true)
    Blog findByid(@Param("id") Long id);
}
