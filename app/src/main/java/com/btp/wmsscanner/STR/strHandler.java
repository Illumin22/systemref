package com.btp.wmsscanner.STR;

public class strHandler {

    private String settingId;
    private String settingLocal;
    private String materialCategoryIndex;
    private String materialIndex;
    private String matTypeIndex;
    private String matClassIndex;
    private String supplierIndex;
    private String transTypeIndex;
    private String sectionIndex;
    private String modelIndex;
    private String sessionID;
    private String barcode;
    private String secID;
    private Integer barcodeQty;
    private String invMth;
    private String invYear;
    private String totalQty;

    private String codeDefIndex;

    private String deliChar;
    private String modelPos;
    private String modelLen;
    private String qtyPos;
    private String qtyLen;
    private String poPos;
    private String poLen;
    private String invPos;
    private String invLen;

    private String partPos;
    private String partLen;
    private String lotPos;
    private String lotLen;

    private String totalLen;

    private String reqDelimit;
    private String reqModel;
    private String reqQty;
    private String reqPO;
    private String reqInvoice;
    private String reqPart;
    private String reqLot;

    private String modelCode;


    //private String ;

    public strHandler(String id, String strLocalhost){
        this.settingId= id;
        this.settingLocal = strLocalhost;
    }

    public strHandler(String index){
        this.materialCategoryIndex=index;
        this.materialIndex=index;
        this.matTypeIndex=index;
        this.matClassIndex=index;
        this.supplierIndex=index;
        this.transTypeIndex=index;
        this.sectionIndex=index;
        this.modelIndex=index;
        this.sessionID=index;
        this.barcode=index;
        this.secID=index;
        this.invMth=index;
        this.invYear=index;
        this.totalQty=index;
        this.deliChar=index;
        this.modelPos=index;
        this.modelLen=index;
        this.qtyPos=index;
        this.qtyLen=index;
        this.poPos=index;
        this.poLen=index;
        this.invPos=index;
        this.invLen=index;
        this.partPos=index;
        this.partLen=index;
        this.lotPos=index;
        this.lotLen=index;
        this.totalLen=index;
        this.reqDelimit=index;
        this.reqModel=index;
        this.reqQty=index;
        this.reqPO=index;
        this.reqInvoice=index;
        this.reqPart=index;
        this.reqLot=index;
        this.modelCode=index;
    }

    public strHandler(Integer index){
        this.barcodeQty=index;
    }

    public String getSettingId(){
        return settingId;
    }

    public void setSettingId(String settingId){
        this.settingId=settingId;
    }

    public String getSettingLocal(){
        return settingLocal;
    }

    public void setSettingLocal(String settingLocal){
        this.settingLocal=settingLocal;
    }

    public String getMaterialIndex() {
        return materialIndex;
    }

    public void setMaterialIndex(String materialIndex) {
        this.materialIndex = materialIndex;
    }

    public String getMatTypeIndex() {
        return matTypeIndex;
    }

    public void setMatTypeIndex(String matTypeIndex) {
        this.matTypeIndex = matTypeIndex;
    }

    public String getMatClassIndex() {
        return matClassIndex;
    }

    public void setMatClassIndex(String matClassIndex) {
        this.matClassIndex = matClassIndex;
    }

    public String getSupplierIndex() {
        return supplierIndex;
    }

    public void setSupplierIndex(String supplierIndex) {
        this.supplierIndex = supplierIndex;
    }

    public String getTransTypeIndex() {
        return transTypeIndex;
    }

    public void setTransTypeIndex(String transTypeIndex) {
        this.transTypeIndex = transTypeIndex;
    }

    public String getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(String sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public String getModelIndex() {
        return modelIndex;
    }

    public void setModelIndex(String modelIndex) {
        this.modelIndex = modelIndex;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Integer getBarcodeQty() {
        return barcodeQty;
    }

    public void setBarcodeQty(Integer barcodeQty) {
        this.barcodeQty = barcodeQty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSecID() {
        return secID;
    }

    public void setSecID(String secID) {
        this.secID = secID;
    }

    public String getInvMth() {
        return invMth;
    }

    public void setInvMth(String invMth) {
        this.invMth = invMth;
    }

    public String getInvYear() {
        return invYear;
    }

    public void setInvYear(String invYear) {
        this.invYear = invYear;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getMaterialCategoryIndex() {
        return materialCategoryIndex;
    }

    public void setMaterialCategoryIndex(String materialCategoryIndex) {
        this.materialCategoryIndex = materialCategoryIndex;
    }

    public String getCodeDefIndex() {
        return codeDefIndex;
    }

    public void setCodeDefIndex(String codeDefIndex) {
        this.codeDefIndex = codeDefIndex;
    }

    public String getDeliChar() {
        return deliChar;
    }

    public void setDeliChar(String deliChar) {
        this.deliChar = deliChar;
    }

    public String getModelPos() {
        return modelPos;
    }

    public void setModelPos(String modelPos) {
        this.modelPos = modelPos;
    }

    public String getQtyPos() {
        return qtyPos;
    }

    public void setQtyPos(String qtyPos) {
        this.qtyPos = qtyPos;
    }

    public String getPoPos() {
        return poPos;
    }

    public void setPoPos(String poPos) {
        this.poPos = poPos;
    }

    public String getPoLen() {
        return poLen;
    }

    public void setPoLen(String poLen) {
        this.poLen = poLen;
    }

    public String getInvPos() {
        return invPos;
    }

    public void setInvPos(String invPos) {
        this.invPos = invPos;
    }

    public String getInvLen() {
        return invLen;
    }

    public void setInvLen(String invLen) {
        this.invLen = invLen;
    }

    public String getTotalLen() {
        return totalLen;
    }

    public void setTotalLen(String totalLen) {
        this.totalLen = totalLen;
    }

    public String getPartPos() {
        return partPos;
    }

    public void setPartPos(String partPos) {
        this.partPos = partPos;
    }

    public String getPartLen() {
        return partLen;
    }

    public void setPartLen(String partLen) {
        this.partLen = partLen;
    }

    public String getLotPos() {
        return lotPos;
    }

    public void setLotPos(String lotPos) {
        this.lotPos = lotPos;
    }

    public String getLotLen() {
        return lotLen;
    }

    public void setLotLen(String lotLen) {
        this.lotLen = lotLen;
    }

    public String getReqDelimit() {
        return reqDelimit;
    }

    public void setReqDelimit(String reqDelimit) {
        this.reqDelimit = reqDelimit;
    }

    public String getReqModel() {
        return reqModel;
    }

    public void setReqModel(String reqModel) {
        this.reqModel = reqModel;
    }

    public String getReqQty() {
        return reqQty;
    }

    public void setReqQty(String reqQty) {
        this.reqQty = reqQty;
    }

    public String getReqPO() {
        return reqPO;
    }

    public void setReqPO(String reqPO) {
        this.reqPO = reqPO;
    }

    public String getReqInvoice() {
        return reqInvoice;
    }

    public void setReqInvoice(String reqInvoice) {
        this.reqInvoice = reqInvoice;
    }

    public String getReqPart() {
        return reqPart;
    }

    public void setReqPart(String reqPart) {
        this.reqPart = reqPart;
    }

    public String getReqLot() {
        return reqLot;
    }

    public void setReqLot(String reqLot) {
        this.reqLot = reqLot;
    }

    public String getModelLen() {
        return modelLen;
    }

    public void setModelLen(String modelLen) {
        this.modelLen = modelLen;
    }

    public String getQtyLen() {
        return qtyLen;
    }

    public void setQtyLen(String qtyLen) {
        this.qtyLen = qtyLen;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
}
