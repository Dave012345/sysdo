package com.sysdo.service;

import com.sysdo.model.Image;
import com.sysdo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


@Service
public class ImageService {

    private ImageRepository imageRepository;

    @Autowired
    public void setImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    /**
     * specifies the max size of uploadable files based on the application.properties (external configuration file).
     */
    @Value("${storage.maxFilesize}")
    private int maxFilesize;

    /**
     * When uploadImage() gets a file, it checks its type and size by fileCheck, renames by the uniqueFilenameGenerator() and saves it to database as a blob.
     *
     * if filesize is more as the set max size, throws a GlobalThrowableExcaption with this message -> "uploader.error.filesize".
     * if everything ok with the file it returns the name of the file.
     * **/

    public String uploadImage(MultipartFile file) throws Exception{
        fileCheck(file);
        String filename = uniqueFilenameGenerator(file.getContentType().split("/")[1]);
        imageRepository.save(new Image(filename, file.getContentType(), file.getBytes()));
        return filename;
    }

    private void fileCheck(MultipartFile file) {
        if (!file.getContentType().startsWith("image/"))
            throw new GlobalThrowableExcaption("uploader.error.filetype");
        if (file.getSize() > maxFilesize)
            throw new GlobalThrowableExcaption("uploader.error.filesize");
    }

    public String uniqueFilenameGenerator(String fileType) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTime = dtf.format(now).replaceAll("[\\/\\ \\:\\-\\.]","");

        Random random = new Random();
        char[] name = new char[24];
        for (int i = 0; i < name.length; i++) name[i] = (char) ('A' + random.nextInt(26));

        return new String(name)+"_"+dateTime+"."+fileType;
    }



    /** returns the Image object that has this filename in the database */
    public Image getImageByFilename(String filename) {
        return imageRepository.findImageByFilename(filename);
    }


    /** returns the mimetype of that Image object, which one has this filename in the database */
    public String getMimeTypeByFilename(String filename){
        return imageRepository.findMimeTypeByFilename(filename);
    }

    /** returns the blob of that Image object, which one has this filename in the database */
    public byte[] getBlobByFilename(String img){
        return imageRepository.getBlob(img);
    }


    /** deletes the Image object, which one has this filename in the database */
    public void deleteBlob(String filename) {
        if (!filename.isEmpty())
            imageRepository.delete(getImageByFilename(filename));
    }
}
