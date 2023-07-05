package com.btp.wmsscanner.STR;

public class strBarcodeCollector {
    private String barcodeData, barcodeQty;

    public strBarcodeCollector(){}

    public strBarcodeCollector(String barcodeData, String barcodeQty){
        this.barcodeData=barcodeData;
        this.barcodeQty=barcodeQty;
    }

    public String getBarcodeData() {
        return barcodeData;
    }

    public void setBarcodeData(String barcodeData) {
        this.barcodeData = barcodeData;
    }

    public String getBarcodeQty() {
        return barcodeQty;
    }

    public void setBarcodeQty(String barcodeQty) {
        this.barcodeQty = barcodeQty;
    }
}
