package com.iiysoftware.instituteapp;

public class modes {
  private  boolean admin=false;
   private  boolean student=false;
    public boolean isAdmin()
    {
        return  admin;
    }
    public void setAdmin(boolean bool)
    {
        this.admin=bool;
    }
    public boolean isStudent()
    {
        return  student;
    }
    public void setStudent(boolean bool)
    {
        this.student=bool;
    }
}
