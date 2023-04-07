package com.example.bookhouse;

public class RequestModel {
    private String studentid, bookid, requestid, boookimg, writername, bookname, catagory, studentname, phone, description, registration;

    public RequestModel(String studentid, String bookid, String requestid, String boookimg, String writername, String bookname, String catagory, String studentname, String phone, String description, String registration) {
        this.studentid = studentid;
        this.bookid = bookid;
        this.requestid = requestid;
        this.boookimg = boookimg;
        this.writername = writername;
        this.bookname = bookname;
        this.catagory = catagory;
        this.studentname = studentname;
        this.phone = phone;
        this.description = description;
        this.registration = registration;
    }

    public RequestModel() {
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getBoookimg() {
        return boookimg;
    }

    public void setBoookimg(String boookimg) {
        this.boookimg = boookimg;
    }

    public String getWritername() {
        return writername;
    }

    public void setWritername(String writername) {
        this.writername = writername;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
