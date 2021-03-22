package com.sysdo.controller;

import com.sysdo.model.Device;
import com.sysdo.model.User;
import com.sysdo.service.DeviceService;
import com.sysdo.service.UserService;
import com.sysdo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProfilController {

    private DeviceService deviceService;
    private UserService userService;
    private ImageService imageService;

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setStorageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/profile")
    public String profil(Model model, Authentication authentication){

        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("authUser", user);

        List<Device> devices = deviceService.findByUserId(user.getId());
        model.addAttribute("devicesOfAuthUser", devices);
        int size = devices.size();
        model.addAttribute("size",size);

        return "profile";
    }

    @RequestMapping("profile/deletedevice/{id}")
    public String del(Model model, @PathVariable(value = "id") Long id, Authentication authentication, RedirectAttributes redirectAttributes) throws Exception {

        if (authentication.getName() != null) {

            User user = userService.findByUsername(authentication.getName());
            List<Device> devices = deviceService.findByUserId(user.getId());

            for(int i = 0; i < devices.size(); i++) {
                if (devices.get(i).getId().equals(id)) {
                    imageService.deleteBlob(devices.get(i).getImagename());
                    deviceService.deleteDevice(id);
                }
            }

            redirectAttributes.addAttribute("message","DeleteWasSuccessfully");
        }

        return "redirect:/profile";
    }
}
