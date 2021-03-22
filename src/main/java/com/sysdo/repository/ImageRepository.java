package com.sysdo.repository;

import com.sysdo.model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ImageRepository extends CrudRepository<Image, Long> {

    @Query(value = "select * from images where filename = ? ;", nativeQuery = true)
    Image findImageByFilename(String filename);


    @Query(value = "select blob from images where filename = ? ;", nativeQuery = true)
    byte[] getBlob(String filename);

    @Query(value = "select mimetype from images where filename = ? ;", nativeQuery = true)
    String findMimeTypeByFilename(String filename);
}
