package com.sysdo.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String filename;
    private String mimetype;

    @Type(type="org.hibernate.type.BinaryType")
    private byte[] blob;

    public Image() {
    }

    public Image(byte[] blob) {
        this.blob = blob;
    }

    public Image(String filename, String mimetype, byte[] blob) {
        this.filename = filename;
        this.mimetype = mimetype;
        this.blob = blob;
    }

    public Long getId() {
        return id;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    @Override
    public String toString() {
        return "Image{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
