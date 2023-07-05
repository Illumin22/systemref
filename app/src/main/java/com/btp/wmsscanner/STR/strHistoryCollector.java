package com.btp.wmsscanner.STR;

public class strHistoryCollector {
    private String histoDocNo, histoDocDate, histoMaterial, histoItemCount, histoTotalQty;

    public strHistoryCollector(String histoDocNo, String histoDocDate, String histoMaterial, String histoItemCount, String histoTotalQty){
        this.histoDocNo=histoDocNo;
        this.histoDocDate=histoDocDate;
        this.histoMaterial=histoMaterial;
        this.histoItemCount=histoItemCount;
        this.histoTotalQty=histoTotalQty;
    }

    public String getHistoDocNo() {
        return histoDocNo;
    }

    public void setHistoDocNo(String histoDocNo) {
        this.histoDocNo = histoDocNo;
    }

    public String getHistoDocDate() {
        return histoDocDate;
    }

    public void setHistoDocDate(String histoDocDate) {
        this.histoDocDate = histoDocDate;
    }

    public String getHistoMaterial() {
        return histoMaterial;
    }

    public void setHistoMaterial(String histoMaterial) {
        this.histoMaterial = histoMaterial;
    }

    public String getHistoItemCount() {
        return histoItemCount;
    }

    public void setHistoItemCount(String histoItemCount) {
        this.histoItemCount = histoItemCount;
    }

    public String getHistoTotalQty() {
        return histoTotalQty;
    }

    public void setHistoTotalQty(String histoTotalQty) {
        this.histoTotalQty = histoTotalQty;
    }
}
