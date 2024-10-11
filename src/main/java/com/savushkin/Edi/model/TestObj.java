package com.savushkin.Edi.model;

import lombok.ToString;

@ToString
public class TestObj {

    int F_ID;
    String USERNAME;

    public TestObj(int f_ID, String USERNAME) {
        F_ID = f_ID;
        this.USERNAME = USERNAME;
    }
}
