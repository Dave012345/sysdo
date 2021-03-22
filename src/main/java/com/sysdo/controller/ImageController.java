package com.sysdo.controller;

import com.sysdo.service.GlobalThrowableExcaption;
import com.sysdo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 *By using this endpoint -> "/image/filename", uploaded image files by the users are accessible from the database.
 */

@Controller
public class ImageController {

    private ImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/image/**")
    void getImage(@RequestParam(value = "src") String filename, HttpServletResponse response) throws Exception{

        try {

            response.setContentType(imageService.getMimeTypeByFilename(filename));
            response.setBufferSize(2048);
            response.getOutputStream().write(imageService.getBlobByFilename(filename));
            response.getOutputStream().close();

        }catch (GlobalThrowableExcaption e){
            response.sendError(404, e.getMessage());
        }catch (NullPointerException npe){
            response.sendError(404, npe.getMessage());
        }

    }
}
