package com.btp.wmsscanner.MOD;

import android.content.Context;
import android.content.ContextWrapper;

import com.btp.wmsscanner.DB.dbHelper;
import com.btp.wmsscanner.STR.strHandler;
import com.btp.wmsscanner.ScannerActivity;

public class CodeDefinitionModule extends ContextWrapper {
    //-----------------------------------------
    /*Get Code definition references.*/
    //-----------------------------------------
    public CodeDefinitionModule(Context context){
        super(context);
    }

    final dbHelper dataHelper = new dbHelper(this);
    final AlertDialogModule alertMod = new AlertDialogModule(this);

    public String q_material="", q_supplier="", q_modelOrig="";

    //-- Query Indexes
    public void queryKeyCodeDef(String materialSelected,
                                String modelSelected,
                                String supplierSelected){
        try{
            strHandler mat = dataHelper.getMaterialIndex(materialSelected);
            q_material = mat.getMaterialIndex();
            strHandler supplier = dataHelper.getSupplierIndex(supplierSelected);
            q_supplier = supplier.getSupplierIndex();
            strHandler model2 = dataHelper.getModelIndexOrig(modelSelected, q_supplier);
            q_modelOrig = model2.getModelIndex();
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //-- Get Delimiter Character from local DB
    public String getDeliChar(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String delimiterChar = null;
        strHandler deliChar = dataHelper.getDelimeterChar(q_material, q_modelOrig, q_supplier);
        delimiterChar = deliChar.getDeliChar();
        return delimiterChar;
    }

    //-- Get Model Position from local DB
    public String getModelPos(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String modelPosVal = null;
        strHandler modelPos = dataHelper.getModelPos(q_material, q_modelOrig, q_supplier);
        modelPosVal = modelPos.getModelPos();
        return modelPosVal;
    }

    //-- Get Model End Position from local DB
    public String getModelLen(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String modelEndPosVal = null;
        strHandler modelEndPos = dataHelper.getModelLen(q_material, q_modelOrig, q_supplier);
        modelEndPosVal = modelEndPos.getModelLen();
        return modelEndPosVal;
    }

    //-- Get Qty Position from local DB
    public String getQtyPos(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String qtyPosVal = null;
        strHandler qtyPos = dataHelper.getQtyPos(q_material, q_modelOrig, q_supplier);
        qtyPosVal = qtyPos.getQtyPos();
        return qtyPosVal;
    }

    //-- Get Qty End Position from local DB
    public String getQtyLen(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String qtyEndPosVal = null;
        strHandler qtyEndPos = dataHelper.getQtyLen(q_material, q_modelOrig, q_supplier);
        qtyEndPosVal = qtyEndPos.getQtyLen();
        return qtyEndPosVal;
    }

    //-- Get PO No Position from local DB
    public String getPoPos(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String poPosVal = null;
        strHandler poPos = dataHelper.getPoPos(q_material, q_modelOrig, q_supplier);
        poPosVal = poPos.getPoPos();
        return poPosVal;
    }

    //-- Get PO No length from local DB
    public String getPoLen(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String poLenVal = null;
        strHandler poLen = dataHelper.getPoLen(q_material, q_modelOrig, q_supplier);
        poLenVal = poLen.getPoLen();
        return poLenVal;
    }

    //-- Get Invoice No Position from local DB
    public String getInvPos(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String invPosVal = null;
        strHandler invPos = dataHelper.getInvPos(q_material, q_modelOrig, q_supplier);
        invPosVal = invPos.getInvPos();
        return invPosVal;
    }

    //-- Get Invoice No length from local DB
    public String getInvLen(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String invLenVal = null;
        strHandler invLen = dataHelper.getInvLen(q_material, q_modelOrig, q_supplier);
        invLenVal = invLen.getInvLen();
        return invLenVal;
    }

    //-- Get Part No Position from local DB
    public String getPartPos(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String partPosVal = null;
        strHandler partPos = dataHelper.getPartPos(q_material, q_modelOrig, q_supplier);
        partPosVal = partPos.getPartPos();
        return partPosVal;
    }

    //-- Get Part No length from local DB
    public String getPartLen(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String partLenVal = null;
        strHandler partLen = dataHelper.getPartLen(q_material, q_modelOrig, q_supplier);
        partLenVal = partLen.getPartLen();
        return partLenVal;
    }

    //-- Get Lot No Position from local DB
    public String getLotPos(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String lotPosVal = null;
        strHandler invPos = dataHelper.getLotPos(q_material, q_modelOrig, q_supplier);
        lotPosVal = invPos.getLotPos();
        return lotPosVal;
    }

    //-- Get Lot No length from local DB
    public String getLotLen(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String lotLenVal = null;
        strHandler lotLen = dataHelper.getLotLen(q_material, q_modelOrig, q_supplier);
        lotLenVal = lotLen.getLotLen();
        return lotLenVal;
    }

    //-- Get Total character length from local DB
    public String getTotalLen(String materialSelect, String modelSelect, String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String totalLenVal = null;
        strHandler totalLen = dataHelper.getTotalLen(q_material, q_modelOrig, q_supplier);
        totalLenVal = totalLen.getTotalLen();
        return totalLenVal;
    }

    //-- Get Delimiter status from local DB
    public String getDelimiterStatus(String materialSelect, String modelSelect,
                                   String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String delStat = null;
        strHandler delimiter = dataHelper.getDelimiterStats(q_material, q_modelOrig, q_supplier);
        delStat = delimiter.getReqDelimit();
        return delStat;
    }

    //-- Get Mpdel status from local DB
    public String getModelStatus(String materialSelect, String modelSelect,
                                     String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String modelStat = null;
        strHandler model = dataHelper.getModelStats(q_material, q_modelOrig, q_supplier);
        modelStat = model.getReqModel();
        return modelStat;
    }

    //-- Get Qty status from local DB
    public String getQtyStatus(String materialSelect, String modelSelect,
                                     String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String qtyStat = null;
        strHandler qty = dataHelper.getQtyStats(q_material, q_modelOrig, q_supplier);
        qtyStat = qty.getReqQty();
        return qtyStat;
    }

    //-- Get PO status from local DB
    public String getPOStatus(String materialSelect, String modelSelect,
                                     String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String poStat = null;
        strHandler po = dataHelper.getPoStats(q_material, q_modelOrig, q_supplier);
        poStat = po.getReqPO();
        return poStat;
    }

    //-- Get Invoice status from local DB
    public String getInvoiceStatus(String materialSelect, String modelSelect,
                                     String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String invoiceStat = null;
        strHandler invoice = dataHelper.getInvoiceStats(q_material, q_modelOrig, q_supplier);
        invoiceStat = invoice.getReqInvoice();
        return invoiceStat;
    }

    //-- Get Part status from local DB
    public String getPartStatus(String materialSelect, String modelSelect,
                                     String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String partStat = null;
        strHandler part = dataHelper.getPartStats(q_material, q_modelOrig, q_supplier);
        partStat = part.getReqPart();
        return partStat;
    }

    //-- Get Lot status from local DB
    public String getLotStatus(String materialSelect, String modelSelect,
                                     String supplierSelect){
        queryKeyCodeDef(materialSelect, modelSelect, supplierSelect);
        String lotStat = null;
        strHandler lot = dataHelper.getPartStats(q_material, q_modelOrig, q_supplier);
        lotStat = lot.getReqPart();
        return lotStat;
    }

}
