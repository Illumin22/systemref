package com.btp.wmsscanner.STR;

public class strCustomDuplicateList {

    private String barcode;
    private String documentNo;
    private String documentDate;
    private String currentSection;

    public strCustomDuplicateList(String barcode, String documentNo, String documentDate,
                                  String currentSection){
        this.barcode=barcode;
        this.documentNo=documentNo;
        this.documentDate=documentDate;
        this.currentSection=currentSection;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(String currentSection) {
        this.currentSection = currentSection;
    }
}
