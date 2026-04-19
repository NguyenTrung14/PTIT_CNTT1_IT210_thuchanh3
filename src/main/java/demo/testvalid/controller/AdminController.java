package demo.testvalid.controller;

import demo.testvalid.dto.DeviceDto;
import demo.testvalid.model.BorrowRequest;
import demo.testvalid.model.Device;
import demo.testvalid.service.DeviceService;
import demo.testvalid.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RequestService requestService;@GetMapping("/devices")
    public String showDeviceList(Model model) {
        model.addAttribute("devices", deviceService.getAllDevices());
        if (!model.containsAttribute("deviceDTO")) {
            model.addAttribute("deviceDTO", new DeviceDto());
        }
        return "admin-device";
    }
    @GetMapping("/requests")
    public String showRequestList(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        return "admin-request";
    }
    @GetMapping("/requests/approve")
    public String approveRequest(@RequestParam("index") int index) {
        List<BorrowRequest> requests = requestService.getAllRequests();

        if (index >= 0 && index < requests.size()) {
            BorrowRequest req = requests.get(index);
            if ("PENDING".equals(req.getStatus())) {
                req.setStatus("APPROVED");
                deviceService.decreaseQuantity(req.getDeviceId(), req.getQuantity());
            }
        }
        return "redirect:/admin/requests";
    }
    @GetMapping("/requests/reject")
    public String rejectRequest(@RequestParam("index") int index) {
        List<BorrowRequest> requests = requestService.getAllRequests();
        if (index >= 0 && index < requests.size()) {
            requests.get(index).setStatus("REJECTED");
        }
        return "redirect:/admin/requests";
    }
    @PostMapping("/devices/add")
    public String addDevice(@Valid @ModelAttribute("deviceDTO") DeviceDto deviceDTO,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("devices", deviceService.getAllDevices());
            model.addAttribute("showAddModal", true);
            return "admin-device";
        }
        Device device = new Device();
        device.setName(deviceDTO.getName());
        device.setQuantity(deviceDTO.getQuantity());
        device.setImage(deviceDTO.getImage());
        deviceService.addDevice(device);
        return "redirect:/admin/devices";
    }
    @PostMapping("/devices/update")
    public String updateDevice(@Valid @ModelAttribute("deviceDTO") DeviceDto deviceDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("devices", deviceService.getAllDevices());
            model.addAttribute("errorId", deviceDTO.getId());
            model.addAttribute("showEditModal", true);
            return "admin-device";
        }
        Device device = new Device();
        device.setId(deviceDTO.getId());
        device.setName(deviceDTO.getName());
        device.setQuantity(deviceDTO.getQuantity());
        device.setImage(deviceDTO.getImage());
        deviceService.updateDevice(device);
        return "redirect:/admin/devices";
    }
    @GetMapping("/devices/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        deviceService.deleteDevice(id);
        return "redirect:/admin/devices";
    }
}
