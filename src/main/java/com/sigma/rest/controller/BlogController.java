package com.sigma.rest.controller;

import com.sigma.rest.pojo.BlogPojo;
import com.sigma.rest.services.BlogServices;
import com.sigma.rest.utility.MessageModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogServices blogServices;

    @GetMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header") })
    public ResponseEntity<MessageModel> findAll() {

        return blogServices.all();
    }

    @GetMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header") })
    public ResponseEntity<MessageModel> findById(@PathVariable Long id) {

        return blogServices.findById(id);
    }

    @PostMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header") })
    public ResponseEntity<MessageModel> addMovie(@RequestBody BlogPojo blogPojo) {

        return blogServices.add(blogPojo);
    }

    @PatchMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header") })
    public ResponseEntity<MessageModel> update(@PathVariable Long id, @RequestBody BlogPojo blogPojo) {
        return blogServices.update(id,blogPojo);
    }

    @DeleteMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header") })
    public ResponseEntity<MessageModel> update(@PathVariable Long id) {
        return blogServices.delete(id);
    }



}
