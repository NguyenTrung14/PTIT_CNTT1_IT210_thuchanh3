package demo.testvalid.controller;

import demo.testvalid.dto.BorrowRequestdto;
import demo.testvalid.model.BorrowRequest;
import demo.testvalid.model.Device;
import demo.testvalid.service.DeviceService;
import demo.testvalid.service.RequestService;
import demo.testvalid.validator.BorrowRequestAdvancedValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BorrowController {
    private final DeviceService deviceService;
    private final BorrowRequestAdvancedValidator validator;
    private final RequestService requestService;
    public BorrowController(DeviceService deviceService,
                            BorrowRequestAdvancedValidator validator,
                            RequestService requestService) {
        this.deviceService = deviceService;
        this.validator = validator;
        this.requestService = requestService;
    }
    @InitBinder("borrowRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
    @GetMapping("/borrow")
    public String showForm(@RequestParam(value = "deviceId", required = false) Long id, Model model) {
        BorrowRequestdto request = new BorrowRequestdto();
        if (id != null) {
            request.setDeviceId(id);
        }
        model.addAttribute("borrowRequest", request);
        return "form";
    }
    @PostMapping("/borrow")
    public String submitForm(@Valid @ModelAttribute("borrowRequest") BorrowRequestdto dto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            return "form";
        }
        BorrowRequest newRequest = new BorrowRequest();
        newRequest.setFullName(dto.getFullName());
        newRequest.setStudentCode(dto.getStudentCode());
        newRequest.setEmail(dto.getEmail());
        newRequest.setQuantity(dto.getQuantity());
        newRequest.setDeviceId(dto.getDeviceId());
        newRequest.setStartDate(dto.getStartDate());
        newRequest.setEndDate(dto.getEndDate());
        newRequest.setReason(dto.getReason());
        newRequest.setStatus("PENDING");
        newRequest.setCreatedAt(LocalDateTime.now());
        requestService.addRequest(newRequest);
        model.addAttribute("studentName", dto.getFullName());
        return "waiting";
    }
    @GetMapping("/borrow/success")
    public String showSuccess(Model model) {
        model.addAttribute("message", "Đơn mượn của bạn đã được phê duyệt thành công!");
        return "success";
    }
    @GetMapping("/borrow/status")
    @ResponseBody
    public String checkStatus() {
        List<BorrowRequest> requests = requestService.getAllRequests();
        if (requests.isEmpty()) return "NONE";
        BorrowRequest latest = requests.get(requests.size() - 1);
        return latest.getStatus();
    }
    @GetMapping("/borrow/reject")
    public String showReject(Model model) {
        model.addAttribute("message", "Đăng ký mượn thất bại! Yêu cầu của bạn đã bị Admin từ chối.");
        return "reject";
    }
}