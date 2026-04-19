package demo.testvalid.validator;

import demo.testvalid.dto.BorrowRequestdto;
import demo.testvalid.model.BorrowRequest;
import demo.testvalid.model.Device;
import demo.testvalid.service.DeviceService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class BorrowRequestAdvancedValidator implements Validator {
    private final DeviceService deviceService;

    public BorrowRequestAdvancedValidator(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public boolean supports(Class<?> type) {
        return BorrowRequestdto.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BorrowRequestdto request = (BorrowRequestdto) target;

        LocalDate today = LocalDate.now();
        if (request.getStartDate() != null && !request.getStartDate().isAfter(today)) {
            errors.rejectValue("startDate", "error.startDate", "Ngày nhận phải sau ngày hiện tại");
        }

        if (request.getStartDate() != null && request.getEndDate() != null &&
                !request.getEndDate().isAfter(request.getStartDate())) {
            errors.rejectValue("endDate", "error.endDate", "Ngày trả phải sau ngày nhận");
        }
        if (request.getDeviceId() != null && request.getQuantity() != null) {
            Device device = deviceService.findDeviceById(request.getDeviceId());
            if (device != null) {
                int quantityRequested = request.getQuantity();
                int quantityInStock = device.getQuantity();
                if (quantityRequested > quantityInStock) {
                    errors.rejectValue("quantity", "error.insufficient",
                            "Số lượng mượn (" + quantityRequested + ") vượt quá tồn kho (" + quantityInStock + ")");
                }
            } else {
                errors.rejectValue("deviceId", "error.deviceNotFound", "Thiết bị không tồn tại trong hệ thống");
            }
        }
    }
}
