package com.jobseekers.mc1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Name : Account
 * <p>
 * Description : Testing Kafka - Account Class
 * <p>
 * Date : 15/03/2022
 * <p>
 * Create by : Abdulrazak A. Osman
 * <p>
 * Mail : a.osman@comptechco.com
 */

public class Account {

    private String holder;
    private String funds;

    public Account(
        @JsonProperty String holder,
        @JsonProperty String funds) {
        this.holder = holder;
        this.funds = funds;
    }

    public Account() {
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getFunds() {
        return funds;
    }

    public void setFunds(String funds) {
        this.funds = funds;
    }
}
