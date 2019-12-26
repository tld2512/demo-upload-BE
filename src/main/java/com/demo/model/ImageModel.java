package com.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor

@ToString
public class ImageModel {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "pic")
    private byte[] pic;

    private String imgURL;

    public ImageModel() {
    }

    //Custom Constructor
    public ImageModel(String name, String type, byte[] pic, String imgURL) {
        this.name = name;
        this.type = type;
        this.pic = pic;
        this.imgURL = imgURL;
    }

    public ImageModel(String originalFilename, String contentType, byte[] bytes) {

    }
}
