package com.sigma.rest.services;


import com.sigma.rest.entity.*;
import com.sigma.rest.pojo.BlogPojo;
import com.sigma.rest.respository.*;
import com.sigma.rest.utility.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServices {

    @Autowired
    private BlogRepository blogRepository;

    public ResponseEntity all() {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {

            List<Blog> list = blogRepository.findAll();
            if (list.size()>0){
                msg.setStatus(true);
                msg.setMessage("Berhasil Get Data!");
                msg.setData(list);
                return ResponseEntity.ok().body(msg);
            }else {
                msg.setStatus(true);
                msg.setMessage("Data Tidak Ditemukan");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);
            }


        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public ResponseEntity findById(Long id) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {

            Blog blog = blogRepository.findByid(id);
            if (blog!=null){
                msg.setStatus(true);
                msg.setMessage("Berhasil Get Data!");
                msg.setData(blog);
                return ResponseEntity.ok().body(msg);
            }else {
                msg.setStatus(true);
                msg.setMessage("Data Tidak Ditemukan");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);
            }


        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public ResponseEntity add(BlogPojo blogPojo) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {

            Blog blog = new Blog();
            blog.setTitle(blogPojo.getTitle());
            blog.setDescription(blogPojo.getDescription());
            blog.setAuthor(blogPojo.getAuthor());
            blog.setCreatedt(blogPojo.getCreatedt());
            blog.setUpdatedAt(blogPojo.getUpdatedAt());
            blogRepository.save(blog);

            msg.setStatus(true);
            msg.setMessage("Berhasil Save Data!");
            msg.setData(blog);
            return ResponseEntity.ok().body(msg);


        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public ResponseEntity update(Long id, BlogPojo blogPojo) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {

            Blog blog = new Blog();
            blog.setId(id);
            blog.setTitle(blogPojo.getTitle());
            blog.setDescription(blogPojo.getDescription());
            blog.setAuthor(blogPojo.getAuthor());
            blog.setCreatedt(blogPojo.getCreatedt());
            blog.setUpdatedAt(blogPojo.getUpdatedAt());
            blogRepository.save(blog);

            msg.setStatus(true);
            msg.setMessage("Berhasil Update Data!");
            msg.setData(blog);
            return ResponseEntity.ok().body(msg);


        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    public ResponseEntity delete(Long id) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {

            blogRepository.deleteById(id);

            msg.setStatus(true);
            msg.setMessage("Berhasil Delete Data!");
            msg.setData(null);
            return ResponseEntity.ok().body(msg);


        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

}
