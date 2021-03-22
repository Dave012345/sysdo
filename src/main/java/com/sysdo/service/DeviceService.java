package com.sysdo.service;

import com.sysdo.repository.DeviceRepository;
import com.sysdo.repository.UserRepository;
import com.sysdo.model.Device;
import com.sysdo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private DeviceRepository deviceRepo;
    private UserRepository userRepo;
    @Autowired
    public void setDeviceRepo(DeviceRepository deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    /**
     * specifies the max size of the device lists based on the application.properties (external configuration file).
     */
    @Value("${DeviceService.limit}")
    private int limit;

    /**
     * This divides the total size of the found devices by the limit, and returns by an array for the pagination.
     *
     * @param querySize : total size of found devices.
     * @return : int array for pagination.
     */
    public int[] getPagination(int querySize) {
        int pages = (int) Math.ceil(querySize / (double) limit);

        int[] pagination = new int[pages];
        for (int i = 0; i < pagination.length; i++)
            pagination[i] = i;
        return pagination;
    }

    /**
     * It gets a page number from the pagination and multiplies it by the limit
     */
    public int getOffset(Integer page){
        int offset = 0;
        if (page != null)
            offset = page * limit;
        return offset;
    }


    public int countDevices()
        {return deviceRepo.countAll();}

    public int countDevicesByType(String devicetype)
        {return deviceRepo.countAllByDevicename(devicetype);}

    public int countSearchedDevices(String devicename, String keywords){
        if (devicename.compareTo("all") == 0)
            return deviceRepo.countSearchedAllDevices(keywords);
        else return deviceRepo.countSearchedDevices(devicename,keywords);
    }

    public List<Device> getDevices(Integer page) { return deviceRepo.findAll(limit, getOffset(page)); }

    public List<Device> getDevicesByType(String devicetype,Integer page) {
        return deviceRepo.findAllByDevicename(devicetype, limit, getOffset(page));
    }

    public List<Device> getSearchedDevices(String devicename, String keywords, String orderby, Integer page){
        int offset = getOffset(page);

        if (devicename.compareTo("all") == 0)
            if (orderby.compareTo("ASC") == 0)
                return deviceRepo.findSearchedDevicesOrderAsc(keywords, limit,offset);
            else return deviceRepo.findSearchedDevicesOrderDesc(keywords, limit,offset);
        else {
            if (orderby.compareTo("ASC") == 0)
                return deviceRepo.findSearchedDeviceOrderAsc(devicename, keywords, limit,offset);
            else return deviceRepo.findSearchedDeviceOrderDesc(devicename,keywords, limit,offset);
        }
    }

    public List<Device> findByUserId(Long id) {
        return deviceRepo.findAllByUserIdOrderByPostedDesc(id);
    }


    public void uploaderDevice(Device device, User user){
        device.setUser(user);
        deviceRepo.save(device);
    }

    public void deleteDevice(Long id) {
        deviceRepo.delete(id);
    }
}
