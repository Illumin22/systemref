package com.btp.wmsscanner.STR;

import com.google.gson.annotations.SerializedName;

public class strPostMaterialStock {

    @SerializedName("stockTransactionID")
    private String stockTransactionID;

    @SerializedName("sessionID")
    private String sessionID;

    @SerializedName("documentDate")
    private String documentDate;

    @SerializedName("documentNo")
    private String documentNo;

    @SerializedName("modelIDFK")
    private String modelIDFK;

    @SerializedName("materialIDFK")
    private String materialIDFK;

    @SerializedName("matCategoryIDFK")
    private String matCategoryIDFK;

    @SerializedName("supplierIDFK")
    private String supplierIDFK;

    @SerializedName("secIDFK")
    private String secIDFK;

    @SerializedName("iqc_status")
    private String iqc_status;

    @SerializedName("iMthIDFK")
    private String iMthIDFK;

    @SerializedName("iYrIDFK")
    private String iYrIDFK;

    @SerializedName("addedDate")
    private String addedDate;

    @SerializedName("addedBy")
    private String addedBy;

    @SerializedName("stockTransactionIDFK")
    private String stockTransactionIDFK;

    @SerializedName("sessionIDFK")
    private String sessionIDFK;

    @SerializedName("documentNo_in")
    private String documentNo_in;

    @SerializedName("qty_in")
    private String qty_in;

    @SerializedName("documentNo_out")
    private String documentNo_out;

    @SerializedName("qty_out")
    private String qty_out;

    @SerializedName("legend_in_IDFK")
    private String legend_in_IDFK;

    @SerializedName("legend_out_IDFK")
     private String legend_out_IDFK;

    @SerializedName("transacDetailsID")
    private String transacDetailsID;

    @SerializedName("matTypeIDFK")
    private String matTypeIDFK;

    //----Header
    public strPostMaterialStock(String sessionID, String documentDate,String documentNo,
                                String modelIDFK,
                                String materialIDFK, String matCategoryIDFK,
                                String matTypeIDFK, String supplierIDFK,
                                String secIDFK, String iqc_status, String iMthIDFK,
                                String iYrIDFK, String addedDate, String addedBy, String dummy){
        this.sessionID=sessionID;
        this.documentDate=documentDate;
        this.documentNo=documentNo;
        this.modelIDFK=modelIDFK;
        this.materialIDFK=materialIDFK;
        this.matCategoryIDFK=matCategoryIDFK;
        this.matTypeIDFK=matTypeIDFK;
        this.supplierIDFK=supplierIDFK;
        this.secIDFK=secIDFK;
        this.iqc_status=iqc_status;
        this.iMthIDFK=iMthIDFK;
        this.iYrIDFK=iYrIDFK;
        this.addedDate=addedDate;
        this.addedBy=addedBy;
    }

    //----Header Update
    public strPostMaterialStock(String stockTransactionID,String sessionID, String documentDate,String documentNo,
                                String modelIDFK,
                                String materialIDFK, String matCategoryIDFK, String supplierIDFK,
                                String secIDFK, String iqc_status, String iMthIDFK,
                                String iYrIDFK, String addedDate, String addedBy){
        this.stockTransactionID=stockTransactionID;
        this.sessionID=sessionID;
        this.documentDate=documentDate;
        this.documentNo=documentNo;
        this.modelIDFK=modelIDFK;
        this.materialIDFK=materialIDFK;
        this.matCategoryIDFK=matCategoryIDFK;
        this.supplierIDFK=supplierIDFK;
        this.secIDFK=secIDFK;
        this.iqc_status=iqc_status;
        this.iMthIDFK=iMthIDFK;
        this.iYrIDFK=iYrIDFK;
        this.addedDate=addedDate;
        this.addedBy=addedBy;
    }

    //----Details
    public strPostMaterialStock(String stockTransactionIDFK, String sessionIDFK,
                                String document_in, String documentNo_out, String qty_in,
                                String qty_out, String legend_in_IDFK, String legend_out_IDFK){

        this.stockTransactionIDFK=stockTransactionIDFK;
        this.sessionIDFK=sessionIDFK;
        this.documentNo_in=document_in;
        this.documentNo_out=documentNo_out;
        this.qty_in=qty_in;
        this.qty_out=qty_out;
        this.legend_in_IDFK=legend_in_IDFK;
        this.legend_out_IDFK=legend_out_IDFK;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getModelIDFK() {
        return modelIDFK;
    }

    public void setModelIDFK(String modelIDFK) {
        this.modelIDFK = modelIDFK;
    }

    public String getMaterialIDFK() {
        return materialIDFK;
    }

    public void setMaterialIDFK(String materialIDFK) {
        this.materialIDFK = materialIDFK;
    }

    public String getMatCategoryIDFK() {
        return matCategoryIDFK;
    }

    public void setMatCategoryIDFK(String matCategoryIDFK) {
        this.matCategoryIDFK = matCategoryIDFK;
    }

    public String getSupplierIDFK() {
        return supplierIDFK;
    }

    public void setSupplierIDFK(String supplierIDFK) {
        this.supplierIDFK = supplierIDFK;
    }

    public String getSecIDFK() {
        return secIDFK;
    }

    public void setSecIDFK(String secIDFK) {
        this.secIDFK = secIDFK;
    }

    public String getIqc_status() {
        return iqc_status;
    }

    public void setIqc_status(String iqc_status) {
        this.iqc_status = iqc_status;
    }

    public String getiMthIDFK() {
        return iMthIDFK;
    }

    public void setiMthIDFK(String iMthIDFK) {
        this.iMthIDFK = iMthIDFK;
    }

    public String getiYrIDFK() {
        return iYrIDFK;
    }

    public void setiYrIDFK(String iYrIDFK) {
        this.iYrIDFK = iYrIDFK;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getStockTransactionIDFK() {
        return stockTransactionIDFK;
    }

    public void setStockTransactionIDFK(String stockTransactionIDFK) {
        this.stockTransactionIDFK = stockTransactionIDFK;
    }

    public String getSessionIDFK() {
        return sessionIDFK;
    }

    public void setSessionIDFK(String sessionIDFK) {
        this.sessionIDFK = sessionIDFK;
    }

    public String getDocumentNo_in() {
        return documentNo_in;
    }

    public void setDocumentNo_in(String documentNo_in) {
        this.documentNo_in = documentNo_in;
    }

    public String getQty_in() {
        return qty_in;
    }

    public void setQty_in(String qty_in) {
        this.qty_in = qty_in;
    }

    public String getDocumentNo_out() {
        return documentNo_out;
    }

    public void setDocumentNo_out(String documentNo_out) {
        this.documentNo_out = documentNo_out;
    }

    public String getQty_out() {
        return qty_out;
    }

    public void setQty_out(String qty_out) {
        this.qty_out = qty_out;
    }

    public String getLegend_in_IDFK() {
        return legend_in_IDFK;
    }

    public void setLegend_in_IDFK(String legend_in_IDFK) {
        this.legend_in_IDFK = legend_in_IDFK;
    }

    public String getLegend_out_IDFK() {
        return legend_out_IDFK;
    }

    public void setLegend_out_IDFK(String legend_out_IDFK) {
        this.legend_out_IDFK = legend_out_IDFK;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getStockTransactionID() {
        return stockTransactionID;
    }

    public void setStockTransactionID(String stockTransactionID) {
        this.stockTransactionID = stockTransactionID;
    }

    public String getTransacDetailsID() {
        return transacDetailsID;
    }

    public void setTransacDetailsID(String transacDetailsID) {
        this.transacDetailsID = transacDetailsID;
    }

    public String getMatTypeIDFK() {
        return matTypeIDFK;
    }

    public void setMatTypeIDFK(String matTypeIDFK) {
        this.matTypeIDFK = matTypeIDFK;
    }
}
