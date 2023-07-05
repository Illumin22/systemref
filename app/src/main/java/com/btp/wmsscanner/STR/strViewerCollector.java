package com.btp.wmsscanner.STR;

public class strViewerCollector {

    private String modelName;
    private String barcode;
    private String qty;
    private String scanDate;
    private String device;

    public strViewerCollector(String modelName){
        this.modelName=modelName;
    }

    public strViewerCollector(String barcode, String qty, String scanDate, String device){
        this.barcode=barcode;
        this.qty=qty;
        this.scanDate=scanDate;
        this.device=device;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
