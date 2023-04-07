package com.example.bookhouse;

public class Modelforpdf {
    private String pdfname, writername, url;

    public Modelforpdf(String pdfname, String writername, String url) {
        this.pdfname = pdfname;
        this.writername = writername;
        this.url = url;
    }

    public Modelforpdf() {
    }

    public String getPdfname() {
        return pdfname;
    }

    public void setPdfname(String pdfname) {
        this.pdfname = pdfname;
    }

    public String getWritername() {
        return writername;
    }

    public void setWritername(String writername) {
        this.writername = writername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
