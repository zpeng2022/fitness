package com.yiie.entity;

import java.io.Serializable;

public class CustomerContact implements Serializable {
    private String contact_id;
    private String customer_contacts_id;
    private String contacter_display_name;
    private String contacter_identity_card;
    private String contacter_phone;
    private Integer contacter_deleted;

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getCustomer_contactId() {
        return customer_contacts_id;
    }

    public void setCustomer_contactId(String customer_contactId) {
        this.customer_contacts_id = customer_contactId;
    }

    public String getContacter_display_name() {
        return contacter_display_name;
    }

    public void setContacter_display_name(String contacter_display_name) {
        this.contacter_display_name = contacter_display_name;
    }

    public String getContacter_identity_card() {
        return contacter_identity_card;
    }

    public void setContacter_identity_card(String contacter_identity_card) {
        this.contacter_identity_card = contacter_identity_card;
    }

    public String getContacter_phone() {
        return contacter_phone;
    }

    public void setContacter_phone(String contacter_phone) {
        this.contacter_phone = contacter_phone;
    }

    public Integer getContacter_deleted() {
        return contacter_deleted;
    }

    public void setContacter_deleted(Integer contacter_deleted) {
        this.contacter_deleted = contacter_deleted;
    }

    @Override
    public String toString() {
        return "CustomerContact{" +
                "contact_id='" + contact_id + '\'' +
                ", customer_contactId='" + customer_contacts_id + '\'' +
                ", contacter_display_name='" + contacter_display_name + '\'' +
                ", contacter_identity_card='" + contacter_identity_card + '\'' +
                ", contacter_phone='" + contacter_phone + '\'' +
                ", contacter_deleted=" + contacter_deleted +
                '}';
    }
}
