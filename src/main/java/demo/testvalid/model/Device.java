package demo.testvalid.model;

public class Device {
    private Long id;
    private String name;
    private String image;
    private Integer quantity;
    private String type;

    public Device() {
    }

    public Device(Long id, String name, String image, Integer quantity, String type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.type = type;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
