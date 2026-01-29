import java.time.LocalDateTime;

public class Order {
    private Integer id;
    private String reference;           
    private LocalDateTime orderDate;    
    private Double totalPrice;         
    private OrderTypeEnum type;         
    private OrderStatusEnum status;
    public Order(Integer id, String reference, LocalDateTime orderDate, Double totalPrice, OrderTypeEnum type,
            OrderStatusEnum status) {
        this.id = id;
        this.reference = reference;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.type = type;
        this.status = status;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public OrderTypeEnum getType() {
        return type;
    }
    public void setType(OrderTypeEnum type) {
        this.type = type;
    }
    public OrderStatusEnum getStatus() {
        return status;
    }
    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "Order [id=" + id + ", reference=" + reference + ", orderDate=" + orderDate + ", totalPrice="
                + totalPrice + ", type=" + type + ", status=" + status + "]";
    }    

}
