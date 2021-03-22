package com.sysdo.controller;

import com.sysdo.model.Device;
import com.sysdo.model.User;
import com.sysdo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UploadController {

    private ImageService imageService;
    private UserService userService;
    private DeviceService deviceService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }



    @RequestMapping("/upload")
    public String upload(Model model){
        Device device = new Device();
        model.addAttribute("newDevice", device);

        return "uploader";
    }

    @PostMapping("/uploading")
    public String uploading(@ModelAttribute Device device, Authentication authentication, @RequestParam(name = "file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception{

        if (!file.isEmpty()) {
            try {
                String filename = imageService.uploadImage(file);
                device.setImagename(filename);
            } catch (Exception e) {
                redirectAttributes.addAttribute("error", e.getMessage());
                return "redirect:/upload";
            }
        } else device.setImagename("");

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        deviceService.uploaderDevice(device, user);

        return "redirect:/";
    }
}
