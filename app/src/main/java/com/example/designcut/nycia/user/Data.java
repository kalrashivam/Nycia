package com.example.designcut.nycia.user;

/**
 * Created by hp on 03-01-2018.
 */

public class Data {

private String Heading;
private String Subheading;

   public Data(String head, String Sub){
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

