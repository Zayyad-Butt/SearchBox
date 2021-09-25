package com.example.searchbox;

import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

public class Student {
    private String name;
    private String CNIC;
    private String rollNo;
    private String img;
    private int age;
    private int id;

    public Student(String name,int age, String CNIC, String rollNo,String img) {
        this.name = name;
        this.CNIC = CNIC;
        this.rollNo = rollNo;
        this.age = age;
        this.img=img;
    }
    public int getID() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
