package com.abhijith.dream_books.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int order_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "other_charges")
    private BigDecimal other_charges;

    @Column(name = "total")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItems> items = new ArrayList<>();

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Payment payment;

    public Orders(){}

    public Orders(User user, BigDecimal subtotal, BigDecimal other_charges, BigDecimal total, OrderStatus orderStatus) {
        this.user = user;
        this.subtotal = subtotal;
        this.other_charges = other_charges;
        this.total = total;
        this.orderStatus = orderStatus;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getOther_charges() {
        return other_charges;
    }

    public void setOther_charges(BigDecimal other_charges) {
        this.other_charges = other_charges;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    //  getter and setter for referenced address, orderitem, payment
    public List<OrderItems> getItems() {
        return items;
    }

    public void setItems(List<OrderItems> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", user=" + user +
                ", subtotal=" + subtotal +
                ", other_charges=" + other_charges +
                ", total=" + total +
                ", orderStatus=" + orderStatus +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", address=" + address +
                ", items=" + items +
                ", payment=" + payment +
                '}';
    }

    //  the prepersist methods to add the current time just before persist happens i.e., no manual date insertion needed
    @PrePersist
    protected void onCreate(){
        created_at = LocalDateTime.now();
        updated_at = created_at;
    }

    //  the preupdate methods to add the current time just before update happens i.e., no manual date insertion needed
    @PreUpdate
    protected void onUpdate(){
        this.updated_at = LocalDateTime.now();
    }

    //  helper methods
    public void addItem(OrderItems item){
        items.add(item);
        item.setOrders(this);
    }

    public void removeItem(OrderItems item){
        items.remove(item);
        item.setOrders(null);
    }

    public void addAddress(Address address){
        this.address = address;
        address.setOrder(this);
    }

    public void removeAddress(Address address){
        this.address.setOrder(null);
        this.address=null;
    }
}
