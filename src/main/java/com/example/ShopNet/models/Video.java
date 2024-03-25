package com.example.ShopNet.models;

import jakarta.persistence.*;
import lombok.*;
/**
 * Сущность, представляющая видео материалы пользователя в базе данных.
 */

@Entity
@Table(name = "videos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Lob
    @Column(name="bytes", columnDefinition="LONGBLOB")
    private byte[] videoData;

    @Column(name = "product_name")
    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getVideoData() {
        return videoData;
    }

    public void setVideoData(byte[] videoData) {
        this.videoData = videoData;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}



