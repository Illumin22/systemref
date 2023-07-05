package com.btp.wmsscanner.STR;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoDataSource {

    /*This class converts JSON data into Plain Old Java Object (POJO) as data object
    to be use in entire system*/

    //-----------------------------------------
    //Declaration(ArrayList)
    //-----------------------------------------
    private ArrayList infoBoard;
    private ArrayList materialList;
    private  ArrayList supplierList;
    private  ArrayList classList;
    private  ArrayList transferList;
    private  ArrayList sectionList;

    //---- Declaration(Serialized data)
    @SerializedName("id")
    public String id;

    @SerializedName("infoSubject")
    public String infoSubject;

    @SerializedName("materialName")
    public String materialName;

    @SerializedName("materialID")
    public String materialID;

    @SerializedName("materialClassCode")
    public String materialClassCode;

    @SerializedName("supplierID")
    public String supplierID;

    @SerializedName("supplierName")
    public String supplierName;

    @SerializedName("supplierCode")
    public String supplierCode;

    @SerializedName("materialIDFK")
    public String materialIDFK;

    @SerializedName("classID")
    public String classID;

    @SerializedName("className")
    public String className;

    @SerializedName("classCode")
    public String classCode;

    @SerializedName("transferID")
    public String transferID;

    @SerializedName("transferName")
    public String transferName;

    @SerializedName("transferCode")
    public String transferCode;

    @SerializedName("sectionID")
    public String sectionID;

    @SerializedName("sectionName")
    public String sectionName;

    @SerializedName("sectionCode")
    public String sectionCode;

    @SerializedName("classIDFK")
    public String classIDFK;

    @SerializedName("processCode")
    public String processCode;

    @SerializedName("isLastProcess")
    public String isLastProcess;

    @SerializedName("sectionIDFK")
    public String sectionIDFK;

    @SerializedName("matTypeID")
    public String matTypeID;

    @SerializedName("matTypeName")
    public String matTypeName;

    @SerializedName("rackID")
    public String rackID;

    @SerializedName("rackCode")
    public String rackCode;

    @SerializedName("rackName")
    public String rackName;

    @SerializedName("modelID")
    public String modelID;

    @SerializedName("modelCode")
    public String modelCode;

    @SerializedName("modelName")
    public String modelName;

    @SerializedName("secID")
    public String secID;

    @SerializedName("transferIDFK")
    public String transferIDFK;

    @SerializedName("secTransIDFK")
    public String secTransIDFK;

    @SerializedName("directionIDFK")
    public String directionIDFK;

    @SerializedName("sequence")
    public String sequence;

    @SerializedName("matCategoryID")
    public String matCategoryID;

    @SerializedName("matCategoryName")
    public String matCategoryName;

    @SerializedName("matCategoryIDFK")
    public String matCategoryIDFK;

    @SerializedName("iMthID")
    public String iMthID;

    @SerializedName("iMth")
    public String iMth;

    @SerializedName("iYrID")
    public String iYrID;

    @SerializedName("iYr")
    public String iYr;

    @SerializedName("activeMth")
    public String activeMth;

    @SerializedName("activeYr")
    public String activeYr;

    @SerializedName("isActive")
    public String isActive;

    @SerializedName("activeNumber")
    public String activeNumber;

    @SerializedName("barcode")
    public String barcode;

    @SerializedName("documentNo")
    public String documentNo;

    @SerializedName("documentDate")
    public String documentDate;

    @SerializedName("transactionDate")
    public String transactionDate;

    @SerializedName("qty")
    public String qty;

    @SerializedName("totalCount")
    public String totalCount;

    @SerializedName("scanDate")
    public String scanDate;

    @SerializedName("device")
    public String device;

    @SerializedName("empID")
    public String empID;

    @SerializedName("Message")
    public String Message;

    @SerializedName("bcID")
    public String bcID;

    @SerializedName("delimeter_char")
    public String delimiter_char;

    @SerializedName("mcID")
    public String mcID;

    @SerializedName("model_pos")
    public String model_pos;

    @SerializedName("model_len")
    public String model_len;

    @SerializedName("qty_pos")
    public String qty_pos;

    @SerializedName("qty_len")
    public String qty_len;

    @SerializedName("po_no_pos")
    public String po_no_pos;

    @SerializedName("po_no_len")
    public String po_no_len;

    @SerializedName("invoice_no_pos")
    public String invoice_no_pos;

    @SerializedName("invoice_no_len")
    public String invoice_no_len;

    @SerializedName("total_len")
    public String total_len;

    @SerializedName("part_no_pos")
    public String part_no_pos;

    @SerializedName("part_no_len")
    public String part_no_len;

    @SerializedName("lot_no_pos")
    public String lot_no_pos;

    @SerializedName("lot_no_len")
    public String lot_no_len;

    @SerializedName("req_delimiter")
    public String req_delimiter;

    @SerializedName("req_model")
    public String req_model;

    @SerializedName("req_qty")
    public String req_qty;

    @SerializedName("req_po_No")
    public String req_po_No;

    @SerializedName("req_invoice_no")
    public String req_invoice_no;

    @SerializedName("req_part_no")
    public String req_part_no;

    @SerializedName("req_lot_no")
    public String req_lot_no;

    @SerializedName("matTypeIDFK")
    public String matTypeIDFK;

    //---- Getter & Setter

    public ArrayList getMaterialList() {
        return materialList;
    }

    public void setMaterialList(ArrayList materialList) {
        this.materialList = materialList;
    }

    public ArrayList getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(ArrayList supplierList) {
        this.supplierList = supplierList;
    }

    public String getInfoSubject() {
        return infoSubject;
    }

    public void setInfoSubject(String infoSubject) {
        this.infoSubject = infoSubject;
    }

    public ArrayList getInfoBoard() {
        return infoBoard;
    }

    public void setInfoBoard(ArrayList infoBoard) {
        this.infoBoard = infoBoard;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getMaterialClassCode() {
        return materialClassCode;
    }

    public void setMaterialClassCode(String materialClassCode) {
        this.materialClassCode = materialClassCode;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getMaterialIDFK() {
        return materialIDFK;
    }

    public void setMaterialIDFK(String materialIDFK) {
        this.materialIDFK = materialIDFK;
    }

    public ArrayList getClassList() {
        return classList;
    }

    public void setClassList(ArrayList classList) {
        this.classList = classList;
    }

    public ArrayList getTransferList() {
        return transferList;
    }

    public void setTransferList(ArrayList transferList) {
        this.transferList = transferList;
    }

    public ArrayList getSectionList() {
        return sectionList;
    }

    public void setSectionList(ArrayList sectionList) {
        this.sectionList = sectionList;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getTransferID() {
        return transferID;
    }

    public void setTransferID(String transferID) {
        this.transferID = transferID;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getTransferCode() {
        return transferCode;
    }

    public void setTransferCode(String transferCode) {
        this.transferCode = transferCode;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getClassIDFK() {
        return classIDFK;
    }

    public void setClassIDFK(String classIDFK) {
        this.classIDFK = classIDFK;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getIsLastProcess() {
        return isLastProcess;
    }

    public void setIsLastProcess(String isLastProcess) {
        this.isLastProcess = isLastProcess;
    }

    public String getSectionIDFK() {
        return sectionIDFK;
    }

    public void setSectionIDFK(String sectionIDFK) {
        this.sectionIDFK = sectionIDFK;
    }

    public String getMatTypeID() {
        return matTypeID;
    }

    public void setMatTypeID(String matTypeID) {
        this.matTypeID = matTypeID;
    }

    public String getMatTypeName() {
        return matTypeName;
    }

    public void setMatTypeName(String matTypeName) {
        this.matTypeName = matTypeName;
    }

    public String getRackID() {
        return rackID;
    }

    public void setRackID(String rackID) {
        this.rackID = rackID;
    }

    public String getRackCode() {
        return rackCode;
    }

    public void setRackCode(String rackCode) {
        this.rackCode = rackCode;
    }

    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSecID() {
        return secID;
    }

    public void setSecID(String secID) {
        this.secID = secID;
    }

    public String getTransferIDFK() {
        return transferIDFK;
    }

    public void setTransferIDFK(String transferIDFK) {
        this.transferIDFK = transferIDFK;
    }

    public String getSecTransIDFK() {
        return secTransIDFK;
    }

    public void setSecTransIDFK(String secTransIDFK) {
        this.secTransIDFK = secTransIDFK;
    }

    public String getDirectionIDFK() {
        return directionIDFK;
    }

    public void setDirectionIDFK(String directionIDFK) {
        this.directionIDFK = directionIDFK;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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

    public String getMatCategoryName() {
        return matCategoryName;
    }

    public void setMatCategoryName(String matCategoryName) {
        this.matCategoryName = matCategoryName;
    }

    public String getiMthID() {
        return iMthID;
    }

    public void setiMthID(String iMthID) {
        this.iMthID = iMthID;
    }

    public String getiMth() {
        return iMth;
    }

    public void setiMth(String iMth) {
        this.iMth = iMth;
    }

    public String getiYrID() {
        return iYrID;
    }

    public void setiYrID(String iYrID) {
        this.iYrID = iYrID;
    }

    public String getiYr() {
        return iYr;
    }

    public void setiYr(String iYr) {
        this.iYr = iYr;
    }

    public String getActiveMth() {
        return activeMth;
    }

    public void setActiveMth(String activeMth) {
        this.activeMth = activeMth;
    }

    public String getActiveYr() {
        return activeYr;
    }

    public void setActiveYr(String activeYr) {
        this.activeYr = activeYr;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActiveNumber() {
        return activeNumber;
    }

    public void setActiveNumber(String activeNumber) {
        this.activeNumber = activeNumber;
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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
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

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getBcID() {
        return bcID;
    }

    public void setBcID(String bcID) {
        this.bcID = bcID;
    }

    public String getDelimiter_char() {
        return delimiter_char;
    }

    public void setDelimiter_char(String delimiter_char) {
        this.delimiter_char = delimiter_char;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public String getModel_pos() {
        return model_pos;
    }

    public void setModel_pos(String model_pos) {
        this.model_pos = model_pos;
    }

    public String getQty_pos() {
        return qty_pos;
    }

    public void setQty_pos(String qty_pos) {
        this.qty_pos = qty_pos;
    }

    public String getPo_no_pos() {
        return po_no_pos;
    }

    public void setPo_no_pos(String po_no_pos) {
        this.po_no_pos = po_no_pos;
    }

    public String getPo_no_len() {
        return po_no_len;
    }

    public void setPo_no_len(String po_no_len) {
        this.po_no_len = po_no_len;
    }

    public String getInvoice_no_pos() {
        return invoice_no_pos;
    }

    public void setInvoice_no_pos(String invoice_no_pos) {
        this.invoice_no_pos = invoice_no_pos;
    }

    public String getInvoice_no_len() {
        return invoice_no_len;
    }

    public void setInvoice_no_len(String invoice_no_len) {
        this.invoice_no_len = invoice_no_len;
    }

    public String getTotal_len() {
        return total_len;
    }

    public void setTotal_len(String total_len) {
        this.total_len = total_len;
    }

    public String getPart_no_pos() {
        return part_no_pos;
    }

    public void setPart_no_pos(String part_no_pos) {
        this.part_no_pos = part_no_pos;
    }

    public String getPart_no_len() {
        return part_no_len;
    }

    public void setPart_no_len(String part_no_len) {
        this.part_no_len = part_no_len;
    }

    public String getLot_no_pos() {
        return lot_no_pos;
    }

    public void setLot_no_pos(String lot_no_pos) {
        this.lot_no_pos = lot_no_pos;
    }

    public String getLot_no_len() {
        return lot_no_len;
    }

    public void setLot_no_len(String lot_no_len) {
        this.lot_no_len = lot_no_len;
    }

    public String getReq_delimiter() {
        return req_delimiter;
    }

    public void setReq_delimiter(String req_delimiter) {
        this.req_delimiter = req_delimiter;
    }

    public String getReq_model() {
        return req_model;
    }

    public void setReq_model(String req_model) {
        this.req_model = req_model;
    }

    public String getReq_qty() {
        return req_qty;
    }

    public void setReq_qty(String req_qty) {
        this.req_qty = req_qty;
    }

    public String getReq_po_No() {
        return req_po_No;
    }

    public void setReq_po_No(String req_po_No) {
        this.req_po_No = req_po_No;
    }

    public String getReq_invoice_no() {
        return req_invoice_no;
    }

    public void setReq_invoice_no(String req_invoice_no) {
        this.req_invoice_no = req_invoice_no;
    }

    public String getReq_part_no() {
        return req_part_no;
    }

    public void setReq_part_no(String req_part_no) {
        this.req_part_no = req_part_no;
    }

    public String getReq_lot_no() {
        return req_lot_no;
    }

    public void setReq_lot_no(String req_lot_no) {
        this.req_lot_no = req_lot_no;
    }

    public String getModel_len() {
        return model_len;
    }

    public void setModel_len(String model_len) {
        this.model_len = model_len;
    }

    public String getQty_len() {
        return qty_len;
    }

    public void setQty_len(String qty_len) {
        this.qty_len = qty_len;
    }

    public String getMatTypeIDFK() {
        return matTypeIDFK;
    }

    public void setMatTypeIDFK(String matTypeIDFK) {
        this.matTypeIDFK = matTypeIDFK;
    }
}