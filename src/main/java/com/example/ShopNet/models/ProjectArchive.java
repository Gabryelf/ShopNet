package com.example.ShopNet.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_archives")
public class ProjectArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name ="project_name")
    private String projectName;

    @Lob
    @Column(name="bytes", columnDefinition="LONGBLOB")
    private byte[] archiveData;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public byte[] getArchiveData() {
        return archiveData;
    }

    public void setArchiveData(byte[] archiveData) {
        this.archiveData = archiveData;
    }
}

