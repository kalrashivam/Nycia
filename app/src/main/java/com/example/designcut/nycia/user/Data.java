package com.example.designcut.nycia.user;

import java.lang.reflect.Array;

/**
 * Created by hp on 03-01-2018.
 */

public class Data {

private String Name;
private String Address;
private String Logo;
private String email;

   public Data(String head, String Sub, String Logo, String email){
     Name= head;
     Address= Sub;
     this.Logo=Logo;
     this.email=email;
  }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getLogo() {
        return Logo;
    }

    public String getEmail() {
        return email;
    }
}

