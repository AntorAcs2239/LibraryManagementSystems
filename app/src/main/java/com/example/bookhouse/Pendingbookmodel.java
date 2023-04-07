package com.example.bookhouse;

import com.google.firebase.Timestamp;

public class Pendingbookmodel {
    private String bookid, catagory, studentid, studentname, bookname, writername, pendingid, img;
    private Timestamp timestamp;

    public Pendingbookmodel(String bookid, String catagory, String studentid, String studentname, String bookname, String writername, String pendingid, Timestamp timestamp, String img) {
        this.bookid = bookid;
        this.catagory = catagory;
        this.studentid = studentid;
        this.studentname = studentname;
        this.bookname = bookname;
        this.writername = writername;
        this.pendingid = pendingid;
        this.timestamp = timestamp;
        this.img = img;
    }

    public Pendingbookmodel() {
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getWritername() {
        return writername;
    }

    public void setWritername(String writername) {
        this.writername = writername;
    }

    public String getPendingid() {
        return pendingid;
    }

    public void setPendingid(String pendingid) {
        this.pendingid = pendingid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
