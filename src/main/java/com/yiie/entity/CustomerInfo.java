package com.yiie.entity;

import java.io.Serializable;

/**
 * 市民一些经常改变的信息
 */
public class CustomerInfo implements Serializable {
    private String customer_info_id;
    private Integer customer_fitness_point;
    private Integer customer_credit_point;
    private Integer customer_step_per_day;
    private Integer customer_info_deleted;

    public String getCustomer_id() {
        return customer_info_id;
    }

    public void setCustomer_id(String customer_id) {
        customer_info_id = customer_id;
    }

    public Integer getCustomer_fitness_point() {
        return customer_fitness_point;
    }

    public void setCustomer_fitness_point(Integer customer_fitness_point) {
        this.customer_fitness_point = customer_fitness_point;
    }

    public Integer getCustomer_credit_point() {
        return customer_credit_point;
    }

    public void setCustomer_credit_point(Integer customer_credit_point) {
        this.customer_credit_point = customer_credit_point;
    }

    public Integer getCustomer_step_per_day() {
        return customer_step_per_day;
    }

    public void setCustomer_step_per_day(Integer customer_step_per_day) {
        this.customer_step_per_day = customer_step_per_day;
    }

    public String getCustomer_info_id() {
        return customer_info_id;
    }

    public void setCustomer_info_id(String customer_info_id) {
        this.customer_info_id = customer_info_id;
    }

    public Integer getCustomer_info_deleted() {
        return customer_info_deleted;
    }

    public void setCustomer_info_deleted(Integer customer_info_deleted) {
        this.customer_info_deleted = customer_info_deleted;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customer_info_id='" + customer_info_id + '\'' +
                ", customer_fitness_point=" + customer_fitness_point +
                ", customer_credit_point=" + customer_credit_point +
                ", customer_step_per_day=" + customer_step_per_day +
                ", customer_info_deleted=" + customer_info_deleted +
                '}';
    }
}
