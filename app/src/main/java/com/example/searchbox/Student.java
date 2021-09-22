package com.example.searchbox;

public class Student {
    private String name;
    private String CNIC;
    private String rollNo;
    private int age;
    private int id;

    public Student(String name,int age, String CNIC, String rollNo,int id) {
        this.name = name;
        this.CNIC = CNIC;
        this.rollNo = rollNo;
        this.age = age;
        this.id=id;
    }
    public int getID() {
        return id;
    }


    public String getName() {
        return name;
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
    @Override
    public String toString() {
        return  "Name: " + name  +
                ", Age: " + age +
                ", CNIC: " + CNIC+
                ", RollNo: "+rollNo;
    }
}
