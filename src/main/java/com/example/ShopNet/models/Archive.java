package com.example.ShopNet.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность, представляющая архивный файл в базе данных.
 */
@Entity
@Table(name = "archives")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Lob
    @Column(name="bytes", columnDefinition="LONGBLOB")
    private byte[] archiveData;

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

    public byte[] getArchiveData() {
        return archiveData;
    }

    public void setArchiveData(byte[] archiveData) {
        this.archiveData = archiveData;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
