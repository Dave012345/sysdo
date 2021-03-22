package com.sysdo.controller;

import com.sysdo.service.DeviceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class SearchController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DeviceService deviceService;

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }



    @GetMapping("/search")
    public String getDataFromSearchForm(HttpSession session, @RequestParam(value = "devicename") String devicename, @RequestParam(value = "keywords") String keywords, @RequestParam(value = "ob") String ob){

        session.setAttribute("devicename",devicename);
        session.setAttribute("keywords",keywords);
        session.setAttribute("ob",ob);

        return "redirect:/searching";
    }

    @GetMapping("/searching")
    public String searching(Model model, @RequestParam(name = "page", required = false) Integer page, HttpSession session){

        String devicename = session.getAttribute("devicename").toString();
        String keywords = session.getAttribute("keywords").toString();
        String ob = session.getAttribute("ob").toString();

        int searchResults = deviceService.countSearchedDevices(devicename,keywords);

        model.addAttribute("sumOfFoundDevices", searchResults);
        model.addAttribute("sumOfPages", deviceService.getPagination(searchResults));
        model.addAttribute("devicetype", deviceService.getSearchedDevices(devicename, keywords, ob, page));

        return "searchresults";
    }

}
