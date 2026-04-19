package demo.testvalid.service;

import demo.testvalid.model.Device;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    private List<Device> deviceList = new ArrayList<>();

    public DeviceService() {
        deviceList.add(new Device(1L,"Màn hình Dell 24 inch","/images/monitor.png",10,"Thiết bị IT"));
        deviceList.add(new Device(2L,"Cáp chuyển HDMI","/images/hdmi.png",25,"Phụ kiện"));
        deviceList.add(new Device(3L,"Board mạch Arduino Uno","/images/arduino.png",5,"Linh kiện điện tử"));
        deviceList.add(new Device(4L,"Phòng Lab 402","/images/lab.png",1,"Phòng Lab"));
    }

    public List<Device> getAllDevices() {
        return deviceList;
    }

    public Device findDeviceById(Long id) {
        return deviceList.stream()
                .filter(device -> device.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public void decreaseQuantity(Long deviceId, int amount) {
        for (Device dev : deviceList) {
            if (dev.getId().equals(deviceId)) {
                int newQty = dev.getQuantity() - amount;
                dev.setQuantity(Math.max(newQty, 0));
                break;
            }
        }
    }
    public void addDevice(Device device) {
        Long nextId = deviceList.isEmpty() ? 1L : deviceList.get(deviceList.size() - 1).getId() + 1;
        device.setId(nextId);
        deviceList.add(device);
    }
    public void deleteDevice(Long id) {
        deviceList.removeIf(d -> d.getId().equals(id));
    }
    public void updateDevice(Device updatedDev) {
        for (int i = 0; i < deviceList.size(); i++) {
            if (deviceList.get(i).getId().equals(updatedDev.getId())) {
                deviceList.set(i, updatedDev);
                return;
            }
        }
    }
}