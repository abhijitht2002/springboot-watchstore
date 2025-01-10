package com.abhijith.dream_books.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int payment_id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_id")
    private String transaction_id;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Payment(){}

    public Payment(Orders orders, PaymentMode paymentMode, PaymentStatus paymentStatus, BigDecimal amount, String transaction_id) {
        this.orders = orders;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.transaction_id = transaction_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
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

    @Override
    public String toString() {
        return "Payment{" +
                "payment_id=" + payment_id +
                ", orders=" + orders +
                ", paymentMode=" + paymentMode +
                ", paymentStatus=" + paymentStatus +
                ", amount=" + amount +
                ", transaction_id='" + transaction_id + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
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
        updated_at = LocalDateTime.now();
    }
}
