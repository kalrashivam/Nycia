package com.example.designcut.nycia.Salon;

/**
 * Created by hp on 15-01-2018.
 */

public class Bookings {

    private String user_phone;
    private String user_email;
    private String status;
    private String service;
    private String amount;
    private String date;

    Bookings(String email, String phone, String status, String service, String amount, String date){
        user_email =email;
        user_phone =phone;
        this.status=status;
        this.service =service;
        this.amount=amount;
        this.date=date;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getStatus() {
        return status;
    }

    public String getService() {
        return service;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
