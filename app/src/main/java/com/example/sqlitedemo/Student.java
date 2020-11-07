package com.example.sqlitedemo;

public class Student {
    String name ;
    int usn ;

    public Student() {
    }

    public Student(String name, int usn) {
        this.name = name;
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsn() {
        return usn;
    }

    public void setUsn(int usn) {
        this.usn = usn;
    }
}
