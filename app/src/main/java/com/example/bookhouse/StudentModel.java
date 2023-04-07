package com.example.bookhouse;

public class StudentModel {
    private String studentname;
    private String studentdepartment;
    private String studentphone;
    private String studentemail;
    private String studentpassword;
    private String uid;
    private String studentclassroll;
    private int numofbook;
    private String studentregistration;
    private String studentbatch;
    private String session;

    public StudentModel(String studentname, String studentdepartment, String studentphone, String studentemail, String studentpassword, String uid, String studentclassroll, int numofbook, String studentregistration, String studentbatch, String session) {
        this.studentname = studentname;
        this.studentdepartment = studentdepartment;
        this.studentphone = studentphone;
        this.studentemail = studentemail;
        this.studentpassword = studentpassword;
        this.uid = uid;
        this.studentclassroll = studentclassroll;
        this.numofbook = numofbook;
        this.studentregistration = studentregistration;
        this.studentbatch = studentbatch;
        this.session = session;
    }

    public StudentModel(String studentname, String studentdepartment, String studentphone, String studentemail, String studentpassword, String uid, int numofbook) {
        this.studentname = studentname;
        this.studentdepartment = studentdepartment;
        this.studentphone = studentphone;
        this.studentemail = studentemail;
        this.studentpassword = studentpassword;
        this.uid = uid;
        this.numofbook = numofbook;
    }

    public StudentModel(String studentname, String studentdepartment, String studentphone, String uid, int numofbook, String studentclassroll) {
        this.studentname = studentname;
        this.studentdepartment = studentdepartment;
        this.studentphone = studentphone;
        this.uid = uid;
        this.numofbook = numofbook;
        this.studentclassroll = studentclassroll;
    }

    public StudentModel() {
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getStudentdepartment() {
        return studentdepartment;
    }

    public void setStudentdepartment(String studentdepartment) {
        this.studentdepartment = studentdepartment;
    }

    public String getStudentphone() {
        return studentphone;
    }

    public void setStudentphone(String studentphone) {
        this.studentphone = studentphone;
    }

    public String getStudentemail() {
        return studentemail;
    }

    public void setStudentemail(String studentemail) {
        this.studentemail = studentemail;
    }

    public String getStudentpassword() {
        return studentpassword;
    }

    public void setStudentpassword(String studentpassword) {
        this.studentpassword = studentpassword;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getNumofbook() {
        return numofbook;
    }

    public void setNumofbook(int numofbook) {
        this.numofbook = numofbook;
    }

    public String getStudentclassroll() {
        return studentclassroll;
    }

    public void setStudentclassroll(String studentclassroll) {
        this.studentclassroll = studentclassroll;
    }

    public String getStudentregistration() {
        return studentregistration;
    }

    public void setStudentregistration(String studentregistration) {
        this.studentregistration = studentregistration;
    }

    public String getStudentbatch() {
        return studentbatch;
    }

    public void setStudentbatch(String studentbatch) {
        this.studentbatch = studentbatch;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
