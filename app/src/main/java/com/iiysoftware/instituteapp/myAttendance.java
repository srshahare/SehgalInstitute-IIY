package com.iiysoftware.instituteapp;

public class myAttendance {
    private String email;
    private String id;
    private boolean isChecked;
    public myAttendance( String email, String id) {

        this.email = email;
        this.id = id;

    }
    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
