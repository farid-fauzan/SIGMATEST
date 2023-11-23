package com.sigma.rest.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter
@Getter
public class BlogPojo {

    @NotBlank(message = "title harus diisi!")
    private String title;

    private String description;

    private String author;

    private Date createdt;

    private Date updatedAt;
}
