package demo.testvalid.controller;

import demo.testvalid.model.Device;
import demo.testvalid.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    private final DeviceService deviceService;

    public StudentController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @GetMapping("/devices")
    public String showDeviceList(Model model) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        return "list";
    }
}
