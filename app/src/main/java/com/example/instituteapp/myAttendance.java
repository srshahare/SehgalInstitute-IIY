package com.example.instituteapp;

public class myAttendance {
    private String email;
    private boolean isChecked;
    public myAttendance( String email) {

        this.email = email;

    }
    public String getEmail() {
        return email;
    }
    public boolean getChecked() {
        return isChecked;
    }

    public void set(boolean checked) {
        isChecked = checked;
    }

    public void setSelected(boolean b) {
        isChecked = b;
    }



    public boolean isSelected() {
        return isChecked;
    }
}
