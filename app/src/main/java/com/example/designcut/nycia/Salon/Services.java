package com.example.designcut.nycia.Salon;

/**
 * Created by hp on 18-01-2018.
 */

public class Services {
    private String Heading;
    private String Subheading;

    public Services(String head, String Sub){
        Heading= head;
        Subheading= Sub;
    }

    public String getHeading()
    {
        return Heading;
    }
    public  String getSubheading()
    {
        return Subheading;
    }
}
