package com.sysdo.controller;

import com.sysdo.service.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * It controls the homepage of the website and displays ads according to the selected device type.
 * */
@Controller
public class HomeController {

    private DeviceService deviceService;
    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @RequestMapping("/")
    public String home(Model model, @RequestParam(value = "page",required = false) Integer page){

        int sumOfAllDevices = deviceService.countDevices();

        model.addAttribute("sumOfPages", deviceService.getPagination(sumOfAllDevices));
        model.addAttribute("device", deviceService.getDevices(page));

        return "layouts/index";
    }

    @RequestMapping("/device/{devicetype}")
    public String searchForUser(@PathVariable(value = "devicetype") String devicetype, Model model, @RequestParam(value = "page",required = false) Integer page){

        if (devicetype == null)
            throw new NullPointerException("No such device!");

        model.addAttribute("sumOfPages", deviceService.getPagination(deviceService.countDevicesByType(devicetype)));
        model.addAttribute("devicetype", deviceService.getDevicesByType(devicetype,page));
        model.addAttribute("dvn",devicetype);

        return "nav/devices";
    }
}
