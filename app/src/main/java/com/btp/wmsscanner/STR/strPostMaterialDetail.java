package com.btp.wmsscanner.STR;

import com.google.gson.annotations.SerializedName;

public class strPostMaterialDetail {

    @SerializedName("sessionID")
    private String sessionID;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("modelIDFK")
    private String modelIDFK;

    @SerializedName("materialIDFK")
    private String materialIDFK;

    @SerializedName("matTypeIDFK")
    private String matTypeIDFK;

    @SerializedName("matCategoryID")
    private String matCategoryID;

    @SerializedName("matCategoryIDFK")
    private String matCategoryIDFK;

    @SerializedName("qty")
    private String qty;

    @SerializedName("scanDate")
    private String scanDate;

    @SerializedName("documentNo")
    private String documentNo;

    @SerializedName("documentDate")
    private String documentDate;

    @SerializedName("supplierIDFK")
    private String supplierIDFK;

    @SerializedName("po_no")
    private String po_no;

    @SerializedName("classIDFK")
    private String classIDFK;

    @SerializedName("rack")
    private String rack;

    @SerializedName("empNo")
    private String empNo;

    @SerializedName("scanDevice")
    private String scanDevice;

    @SerializedName("transactionIDFK")
    private String transactionIDFK;

    @SerializedName("sessionIDFK")
    private String sessionIDFK;

    @SerializedName("sectionIDFK")
    private String sectionIDFK;

    @SerializedName("transactionDate")
    private String transactionDate;

    @SerializedName("transactionQty")
    private String transactionQty;

    @SerializedName("movement_qty")
    private String movement_qty;

    @SerializedName("ng_qty")
    private String ng_qty;

    @SerializedName("transferIDFK")
    private String transferIDFK;

    @SerializedName("addedDate")
    private String addedDate;

    @SerializedName("addedBy")
    private String addedBy;

    @SerializedName("sequence")
    private String sequence;

    @SerializedName("Message")
    private String Message;

    @SerializedName("secIDFK")
    private String secIDFK;

    @SerializedName("secStatus")
    private String secStatus;

    @SerializedName("secDate")
    private String secDate;

    @SerializedName("secRemarks")
    private String secRemarks;

    @SerializedName("secID")
    private String secID;

    @SerializedName("transactionID")
    private String transactionID;

    @SerializedName("transacDetailsID")
    private String transacDetailsID;

    @SerializedName("stdID")
    private String stdID;

    @SerializedName("stdIDFK")
    private String stdIDFK;

    @SerializedName("iMthIDFK")
    private String iMthIDFK;

    @SerializedName("iYrIDFK")
    private String iYrIDFK;

    @SerializedName("stockTransactionID")
    private String stockTransactionID;

    @SerializedName("secControlNo")
    private String secControlNo;

    @SerializedName("materialName")
    private String materialName;

    @SerializedName("className")
    private String className;

    @SerializedName("supplierName")
    private String supplierName;

    @SerializedName("transferName")
    private String transferName;

    @SerializedName("sectionName")
    private String sectionName;

    @SerializedName("empID")
    public String empID;

    @SerializedName("duplicate_status")
    public String duplicate_status;

    @SerializedName("action")
    public String action;

    @SerializedName("modelRaw")
    public String modelRaw;

    @SerializedName("poNoRaw")
    public String poNoRaw;

    @SerializedName("invoiceNoRaw")
    public String invoiceNoRaw;

    @SerializedName("partNoRaw")
    public String partNoRaw;

    @SerializedName("lotNoRaw")
    public String lotNoRaw;

    @SerializedName("modelName")
    public String modelName;

    public strPostMaterialDetail(String sessionID, String barcode, String modelIDFK,
                                 String materialIDFK, String matCategoryIDFK, String qty,
                                 String scanDate, String documentNo, String documentDate,
                                 String supplierIDFK, String po_no, String classIDFK,
                                 String matTypeIDFK) {
        this.sessionID=sessionID;
        this.barcode=barcode;
        this.materialIDFK=materialIDFK;
        this.modelIDFK=modelIDFK;
        this.qty=qty;
        this.scanDate=scanDate;
        this.documentNo=documentNo;
        this.documentDate=documentDate;
        this.supplierIDFK=supplierIDFK;
        this.po_no=po_no;
        this.classIDFK=classIDFK;
        this.matCategoryIDFK=matCategoryIDFK;
        this.matTypeIDFK=matTypeIDFK;
    }

    public strPostMaterialDetail(String sessionID, String barcode, String modelIDFK,
                                 String materialIDFK, String matCategoryIDFK, String qty,
                                 String scanDate, String documentNo, String documentDate,
                                 String supplierIDFK, String po_no, String classIDFK,
                                 String matTypeIDFK, String modelRaw, String poNoRaw,
                                 String invoiceNoRaw, String partNoRaw, String lotNoRaw) {
        this.sessionID=sessionID;
        this.barcode=barcode;
        this.materialIDFK=materialIDFK;
        this.modelIDFK=modelIDFK;
        this.qty=qty;
        this.scanDate=scanDate;
        this.documentNo=documentNo;
        this.documentDate=documentDate;
        this.supplierIDFK=supplierIDFK;
        this.po_no=po_no;
        this.classIDFK=classIDFK;
        this.matCategoryIDFK=matCategoryIDFK;
        this.matTypeIDFK=matTypeIDFK;
        this.modelRaw=modelRaw;
        this.poNoRaw=poNoRaw;
        this.invoiceNoRaw=invoiceNoRaw;
        this.partNoRaw=partNoRaw;
        this.lotNoRaw=lotNoRaw;
    }

    public strPostMaterialDetail(String transactionIDFK, String sessionIDFK, String sectionIDFK,
                                 String transactionDate, String transactionQty,
                                 String movement_qty, String ng_qty, String transferIDFK,
                                 String rack, String empNo, String scanDevice, String iMthIDFK,
                                 String iYrIDFK, String addedDate, String addedBy,
                                 String documentNo, String action){
        this.transactionIDFK=transactionIDFK;
        this.sessionIDFK=sessionIDFK;
        this.sectionIDFK=sectionIDFK;
        this.transactionDate=transactionDate;
        this.transactionQty=transactionQty;
        this.movement_qty=movement_qty;
        this.ng_qty=ng_qty;
        this.transferIDFK=transferIDFK;
        this.rack=rack;
        this.empNo=empNo;
        this.scanDevice=scanDevice;
        this.iMthIDFK=iMthIDFK;
        this.iYrIDFK=iYrIDFK;
        this.addedDate=addedDate;
        this.addedBy=addedBy;
        this.documentNo=documentNo;
        this.action=action;
    }

    public strPostMaterialDetail(String transacDetailsID, String secIDFK, String secControlNo,
                                 String secStatus,
                                 String secDate, String secRemarks){
        this.transacDetailsID=transacDetailsID;
        this.secIDFK=secIDFK;
        this.secControlNo=secControlNo;
        this.secStatus=secStatus;
        this.secDate=secDate;
        this.secRemarks=secRemarks;
    }

    public strPostMaterialDetail(String index, String barcode, String session, String docNo){
        this.stdID=index;
        this.barcode=barcode;
        this.sessionID=session;
        this.documentNo=docNo;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getModelIDFK() {
        return modelIDFK;
    }

    public void setModelIDFK(String modelIDFK) {
        this.modelIDFK = modelIDFK;
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

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getSupplierIDFK() {
        return supplierIDFK;
    }

    public void setSupplierIDFK(String supplierIDFK) {
        this.supplierIDFK = supplierIDFK;
    }

    public String getPo_no() {
        return po_no;
    }

    public void setPo_no(String po_no) {
        this.po_no = po_no;
    }

    public String getClassIDFK() {
        return classIDFK;
    }

    public void setClassIDFK(String classIDFK) {
        this.classIDFK = classIDFK;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getScanDevice() {
        return scanDevice;
    }

    public void setScanDevice(String scanDevice) {
        this.scanDevice = scanDevice;
    }

    public String getTransactionIDFK() {
        return transactionIDFK;
    }

    public void setTransactionIDFK(String transactionIDFK) {
        this.transactionIDFK = transactionIDFK;
    }

    public String getSessionIDFK() {
        return sessionIDFK;
    }

    public void setSessionIDFK(String sessionIDFK) {
        this.sessionIDFK = sessionIDFK;
    }

    public String getSectionIDFK() {
        return sectionIDFK;
    }

    public void setSectionIDFK(String sectionIDFK) {
        this.sectionIDFK = sectionIDFK;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionQty() {
        return transactionQty;
    }

    public void setTransactionQty(String transactionQty) {
        this.transactionQty = transactionQty;
    }

    public String getMovement_qty() {
        return movement_qty;
    }

    public void setMovement_qty(String movement_qty) {
        this.movement_qty = movement_qty;
    }

    public String getNg_qty() {
        return ng_qty;
    }

    public void setNg_qty(String ng_qty) {
        this.ng_qty = ng_qty;
    }

    public String getTransferIDFK() {
        return transferIDFK;
    }

    public void setTransferIDFK(String transferIDFK) {
        this.transferIDFK = transferIDFK;
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

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getSecIDFK() {
        return secIDFK;
    }

    public void setSecIDFK(String secIDFK) {
        this.secIDFK = secIDFK;
    }

    public String getSecStatus() {
        return secStatus;
    }

    public void setSecStatus(String secStatus) {
        this.secStatus = secStatus;
    }

    public String getSecDate() {
        return secDate;
    }

    public void setSecDate(String secDate) {
        this.secDate = secDate;
    }

    public String getSecRemarks() {
        return secRemarks;
    }

    public void setSecRemarks(String secRemarks) {
        this.secRemarks = secRemarks;
    }

    public String getSecID() {
        return secID;
    }

    public void setSecID(String secID) {
        this.secID = secID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getMaterialIDFK() {
        return materialIDFK;
    }

    public void setMaterialIDFK(String materialIDFK) {
        this.materialIDFK = materialIDFK;
    }

    public String getTransacDetailsID() {
        return transacDetailsID;
    }

    public void setTransacDetailsID(String transacDetailsID) {
        this.transacDetailsID = transacDetailsID;
    }

    public String getStdID() {
        return stdID;
    }

    public void setStdID(String stdID) {
        this.stdID = stdID;
    }

    public String getStdIDFK() {
        return stdIDFK;
    }

    public void setStdIDFK(String stdIDFK) {
        this.stdIDFK = stdIDFK;
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

    public String getMatCategoryID() {
        return matCategoryID;
    }

    public void setMatCategoryID(String matCategoryID) {
        this.matCategoryID = matCategoryID;
    }

    public String getMatCategoryIDFK() {
        return matCategoryIDFK;
    }

    public void setMatCategoryIDFK(String matCategoryIDFK) {
        this.matCategoryIDFK = matCategoryIDFK;
    }

    public String getStockTransactionID() {
        return stockTransactionID;
    }

    public void setStockTransactionID(String stockTransactionID) {
        this.stockTransactionID = stockTransactionID;
    }

    public String getSecControlNo() {
        return secControlNo;
    }

    public void setSecControlNo(String secControlNo) {
        this.secControlNo = secControlNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getMatTypeIDFK() {
        return matTypeIDFK;
    }

    public void setMatTypeIDFK(String matTypeIDFK) {
        this.matTypeIDFK = matTypeIDFK;
    }

    public String getDuplicate_status() {
        return duplicate_status;
    }

    public void setDuplicate_status(String duplicate_status) {
        this.duplicate_status = duplicate_status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModelRaw() {
        return modelRaw;
    }

    public void setModelRaw(String modelRaw) {
        this.modelRaw = modelRaw;
    }

    public String getPoNoRaw() {
        return poNoRaw;
    }

    public void setPoNoRaw(String poNoRaw) {
        this.poNoRaw = poNoRaw;
    }

    public String getInvoiceNoRaw() {
        return invoiceNoRaw;
    }

    public void setInvoiceNoRaw(String invoiceNoRaw) {
        this.invoiceNoRaw = invoiceNoRaw;
    }

    public String getPartNoRaw() {
        return partNoRaw;
    }

    public void setPartNoRaw(String partNoRaw) {
        this.partNoRaw = partNoRaw;
    }

    public String getLotNoRaw() {
        return lotNoRaw;
    }

    public void setLotNoRaw(String lotNoRaw) {
        this.lotNoRaw = lotNoRaw;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}