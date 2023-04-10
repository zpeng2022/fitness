package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 市区内市民的基本信息
 * 一般不会改变的信息, 或者改变的次数很少.
 */
public class Customer implements Serializable {
    private String customer_id;
    private String customer_identity_card;
    private String customer_name;
    private String customer_nickname;
    private Double customer_weight;
    private Double customer_height;
    private Date customer_birthday;
    private String customer_location;
    private String customer_hobby;
    private String customer_disease;
    private String customer_picture;
    private Integer customer_gender;
    private String customer_phone;
    private String customer_fitness_QRCode;
    /**
     * 存储 用户的 常用联系人表的id
     */
    private String customer_contacts_id;
    private String customer_info_id;
    private Date customer_create_time;
    private Date customer_update_time;
    private Integer customer_deleted;

    public String getCustomer_contacts_id() {
        return customer_contacts_id;
    }

    public void setCustomer_contacts_id(String customer_contacts_id) {
        this.customer_contacts_id = customer_contacts_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id == null ? null : customer_id.trim();
    }

    public String getCustomer_identity_card() {
        return customer_identity_card;
    }

    public void setCustomer_identity_card(String customer_identity_card) {
        this.customer_identity_card = customer_identity_card == null ? null : customer_identity_card.trim();
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name == null ? null : customer_name.trim();
    }

    public String getCustomer_nickname() {
        return customer_nickname;
    }

    public void setCustomer_nickname(String customer_nickname) {
        this.customer_nickname = customer_nickname == null ? null : customer_nickname.trim();
    }

    public Double getCustomer_weight() {
        return customer_weight;
    }

    public void setCustomer_weight(Double customer_weight) {
        this.customer_weight = customer_weight;
    }

    public Double getCustomer_height() {
        return customer_height;
    }

    public void setCustomer_height(Double customer_height) {
        this.customer_height = customer_height;
    }

    public Date getCustomer_birthday() {
        return customer_birthday;
    }

    public void setCustomer_birthday(Date customer_birthday) {
        this.customer_birthday = customer_birthday;
    }

    public String getCustomer_location() {
        return customer_location;
    }

    public void setCustomer_location(String customer_location) {
        this.customer_location = customer_location;
    }

    public String getCustomer_hobby() {
        return customer_hobby;
    }

    public void setCustomer_hobby(String customer_hobby) {
        this.customer_hobby = customer_hobby;
    }

    public String getCustomer_disease() {
        return customer_disease;
    }

    public void setCustomer_disease(String customer_disease) {
        this.customer_disease = customer_disease;
    }

    public String getCustomer_picture() {
        return customer_picture;
    }

    public void setCustomer_picture(String customer_picture) {
        this.customer_picture = customer_picture;
    }

    public Integer getCustomer_gender() {
        return customer_gender;
    }

    public void setCustomer_gender(Integer customer_gender) {
        this.customer_gender = customer_gender;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone == null ? null : customer_phone.trim();
    }

    public String getCustomer_fitness_QRCode() {
        return customer_fitness_QRCode;
    }

    public void setCustomer_fitness_QRCode(String customer_fitness_QRCode) {
        this.customer_fitness_QRCode = customer_fitness_QRCode;
    }

    public String getCustomer_contactId() {
        return customer_contacts_id;
    }

    public void setCustomer_contactId(String customer_contactId) {
        this.customer_contacts_id = customer_contactId == null ? null : customer_contactId.trim();
    }

    public String getCustomer_info_id() {
        return customer_info_id;
    }

    public void setCustomer_info_id(String customer_info_id) {
        this.customer_info_id = customer_info_id == null ? null : customer_info_id.trim();
    }

    public Date getCustomer_create_time() {
        return customer_create_time;
    }

    public void setCustomer_create_time(Date customer_create_time) {
        this.customer_create_time = customer_create_time;
    }

    public Date getCustomer_update_time() {
        return customer_update_time;
    }

    public void setCustomer_update_time(Date customer_update_time) {
        this.customer_update_time = customer_update_time;
    }

    public Integer getCustomer_deleted() {
        return customer_deleted;
    }

    public void setCustomer_deleted(Integer customer_deleted) {
        this.customer_deleted = customer_deleted;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id='" + customer_id + '\'' +
                ", customer_identity_card='" + customer_identity_card + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_nickname='" + customer_nickname + '\'' +
                ", customer_weight=" + customer_weight +
                ", customer_height=" + customer_height +
                ", customer_birthday=" + customer_birthday +
                ", customer_location='" + customer_location + '\'' +
                ", customer_hobby='" + customer_hobby + '\'' +
                ", customer_disease='" + customer_disease + '\'' +
                ", customer_picture='" + customer_picture + '\'' +
                ", customer_gender=" + customer_gender +
                ", customer_phone='" + customer_phone + '\'' +
                ", customer_fitness_QRCode='" + customer_fitness_QRCode + '\'' +
                ", customer_contactId='" + customer_contacts_id + '\'' +
                ", customer_info_id='" + customer_info_id + '\'' +
                ", customer_create_time=" + customer_create_time +
                ", customer_update_time=" + customer_update_time +
                ", customer_deleted=" + customer_deleted +
                '}';
    }
}
