package com.sysdo.repository;

import com.sysdo.model.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DeviceRepository extends CrudRepository<Device, Long> {

    @Query(value = "SELECT COUNT(id) FROM devices;", nativeQuery = true)
    int countAll();
    @Query(value = "SELECT COUNT(id) FROM devices WHERE devicename = ? ;", nativeQuery = true)
    int countAllByDevicename(String devicetype);

    @Query(value = "select * from devices order by posted desc limit ?1 offset ?2 ;", nativeQuery = true)
    List<Device> findAll(int limit,int offset);
    @Query(value = "select * from devices where devicename = ?1 order by posted desc limit ?2 offset ?3 ;", nativeQuery = true)
    List<Device> findAllByDevicename(String devicetype, int limit,int offset);

    List<Device> findAllByUserIdOrderByPostedDesc(Long id);


    @Query(value = "SELECT COUNT(id) FROM devices where devicename = ?1 and (maker || model || short_description || long_description || location) LIKE %?2% ;", nativeQuery = true)
    int countSearchedDevices(String devicename, String keywords);
    @Query(value = "SELECT COUNT(id) FROM devices where (maker || model || short_description || long_description || location) LIKE %?1% ;", nativeQuery = true)
    int countSearchedAllDevices(String keywords);

    @Query(value = "select * from devices where devicename = ?1 and (maker || model || short_description || long_description || location) LIKE %?2% order by price asc limit ?3 offset ?4 ;", nativeQuery = true)
    List<Device> findSearchedDeviceOrderAsc(String devicename, String keywords, int limit,int offset);
    @Query(value = "select * from devices where devicename = ?1 and (maker || model || short_description || long_description || location) LIKE %?2% order by price desc limit ?3 offset ?4 ;", nativeQuery = true)
    List<Device> findSearchedDeviceOrderDesc(String devicename, String keywords, int limit,int offset);
    @Query(value = "select * from devices where (maker || model || short_description || long_description || location) LIKE %?1% order by price asc limit ?2 offset ?3 ;", nativeQuery = true)
    List<Device> findSearchedDevicesOrderAsc(String keywords, int limit,int offset);
    @Query(value = "select * from devices where (maker || model || short_description || long_description || location) LIKE %?1% order by price desc limit ?2 offset ?3 ;", nativeQuery = true)
    List<Device> findSearchedDevicesOrderDesc(String keywords, int limit,int offset);
}
