package demo.testvalid.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DeviceDto {
    private Long id;
    @NotBlank(message = "Tên thiết bị không được để trống đâu nhé!")
    @Size(min = 3, max = 100, message = "Tên thiết bị phải từ 3 đến 100 ký tự")
    private String name;

    @NotBlank(message = "Vui lòng dán link ảnh vào đây")
    private String image;

    @NotNull(message = "Số lượng không được bỏ trống")
    @Min(value = 0, message = "Số lượng tồn kho không thể nhỏ hơn 0")
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
