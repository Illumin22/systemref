package com.btp.wmsscanner.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.btp.wmsscanner.STR.barcodeDataCollector;
import com.btp.wmsscanner.STR.dataCollector;
import com.btp.wmsscanner.STR.strCustomScannerList;
import com.btp.wmsscanner.STR.strHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class dbHelperBarcode extends dbHandlerBarcode {
    private List<strCustomScannerList> arrList = new ArrayList<>();
    public dbHelperBarcode(Context context) {
        super(context);
    }

    private static final String KEY_BARCODE_QTY = "barcode_qty";
    private static final String KEY_BARCODE = "barcode";
    private static final String KEY_TRANSQTY = "transactionQty";
    private static final String KEY_TRANSIDFK = "transactionIDFK";
    private static final String KEY_SESSID = "sessionID";
    private static final String KEY_SESSIDFK = "sessionIDFK";
    //============================                ====================================
    //================================================================================

    //---- Get items to display in scanner list.
    public List<strCustomScannerList> itemData(){
        strCustomScannerList codes = null;
        String query = "select barcode, barcode_qty from t_tempBarcode ORDER BY _id desc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        arrList.clear();
        if(cursor.moveToFirst()){
            do{
                String code = cursor.getString(cursor.getColumnIndexOrThrow("barcode"));
                String qty = cursor.getString(cursor.getColumnIndexOrThrow("barcode_qty"));
                codes = new strCustomScannerList(code, Integer.valueOf(qty));
                arrList.add(codes);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        arrList.removeAll(Collections.singleton(null));
        return arrList;
    }

    //---- Get Barcode quantity
    public strHandler getBarcodeQty(String barcode){
        strHandler handler = null;
        String query = "select barcode_qty from t_tempBarcode where barcode = '"+barcode+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("barcode_qty"));
                handler = new strHandler(Integer.valueOf(strIndex));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //---- Get Barcode for duplicate compare
    public strHandler getBarcodeForDuplicate(String barcode, String docNo){
        strHandler handler = null;
        String query = "select barcode from t_tempBarcode where barcode = '"+barcode+"' and docno = '"+docNo+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("barcode_qty"));
                handler = new strHandler(Integer.valueOf(strIndex));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //---- Insert Item Header Data
    public boolean insertData(barcodeDataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("docno", itemCollection.dataDocumentNo);
        values.put("docDate", itemCollection.dataDocumentDate);
        values.put("barcode", itemCollection.dataBarcode);
        values.put("barcode_qty", itemCollection.dataBarcodeQty); //Note: DB Data type is integer.
        // Watch for error.
        values.put("scan_date", itemCollection.dataScanDate);
        values.put("po_no", itemCollection.dataPoNo);
        values.put("material", itemCollection.dataMaterial);
        values.put("matCategoryIDFK", itemCollection.dataMaterialCat);
        values.put("material_Type", itemCollection.dataMatType);
        values.put("material_Class", itemCollection.dataMatClass);
        values.put("supplier", itemCollection.dataSupplier);
        values.put("transfer_type", itemCollection.dataTransType);
        values.put("section", itemCollection.dataSection);
        values.put("model", itemCollection.dataModel);
        values.put("sessionID", itemCollection.dataSessionID);
        values.put("upload_status", itemCollection.dataUploadStatus);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("t_tempBarcode", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //---- Insert Item Details Data
    public boolean insertDetailsData(barcodeDataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("transactionIDFK", itemCollection.dataTransactionIDFK);
        values.put("sessionIDFK", itemCollection.dataSessionIDFK);
        values.put("sectionIDFK", itemCollection.dataSectionIDFK);
        values.put("transactionDate", itemCollection.dataTransactionDate);
        values.put("transactionQty", itemCollection.dataTransactionQty);
        values.put("movement_qty", itemCollection.dataMovementQty);
        values.put("ng_qty", itemCollection.dataNgQty);
        values.put("transferIDFK", itemCollection.dataTransferIDFK);
        values.put("stdIDFK", itemCollection.dataStIDFK);
        values.put("rack", itemCollection.dataRack);
        values.put("empNo", itemCollection.dataEmpNo);
        values.put("device", itemCollection.dataDevice);
        values.put("iMthIDFK", itemCollection.dataInvMth);
        values.put("iYrIDFK", itemCollection.dataInvYr);
        values.put("addedDate", itemCollection.dataAddedDate);
        values.put("addedBy", itemCollection.dataAddedBy);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("t_tempBarcodeDetails", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //---- Insert Item Details Data
    public boolean insertStockHeaderData(barcodeDataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("sessionID", itemCollection.dataSessionID);
        values.put("documentDate", itemCollection.dataDocumentDate);
        values.put("documentNo", itemCollection.dataDocumentNo);
        values.put("modelIDFK", itemCollection.dataModel);
        values.put("materialIDFK", itemCollection.dataMaterial);
        values.put("matCategoryIDFK", itemCollection.dataMaterialCat);
        values.put("supplierIDFK", itemCollection.dataSupplier);
        values.put("secIDFK", itemCollection.dataMSecIDFK);
        values.put("iqc_status", itemCollection.dataIQC);
        values.put("iMthIDFK", itemCollection.dataInvMth);
        values.put("iYrIDFK", itemCollection.dataInvYr);
        values.put("legendIDFK", itemCollection.dataLegendIDFK);
        values.put("addedDate", itemCollection.dataAddedDate);
        values.put("addedBy", itemCollection.dataAddedBy);
        values.put("upload_status", itemCollection.dataUploadStatus);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("t_tempStockcardHeader", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //---- Insert Item Details Data
    public boolean insertMarkersData(barcodeDataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("stdID", itemCollection.dataMStdID);
        values.put("transacDetailsID", itemCollection.dataMTransacDetailsID);
        values.put("secIDFK", itemCollection.dataMSecIDFK);
        values.put("secStatus", itemCollection.dataMSecStatus);
        values.put("secDate", itemCollection.dataMSecDate);
        values.put("secRemarks", itemCollection.dataMSecRemarks);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("t_tempBarcodeMarkers", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Update data transaction header
    public boolean updateTransHeaderQty(String newQty, String code, String session){

        ContentValues val = new ContentValues();
        val.put(KEY_BARCODE_QTY, newQty);

        String whereClause = KEY_BARCODE + "=? AND " + KEY_SESSID + "=?";
        final String whereArgs[] = {code, session};
        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSettingUp = db.update("t_tempBarcode", val, whereClause, whereArgs)>0;
        db.close();
        return updateSettingUp;
    }

    //----Update data transaction header
    public boolean updateTransDetailsQty(String newQty, String id, String session){

        ContentValues val = new ContentValues();
        val.put("transactionQty", newQty);

        String whereClause = KEY_TRANSIDFK + "=?";
        final String whereArgs[] = {id};
        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSettingUp = db.update("t_tempBarcodeDetails", val, whereClause, whereArgs)>0;
        db.close();
        return updateSettingUp;
    }

    //---- Get SessionID
    public strHandler getSessionID(String docNo, String model){
        strHandler handler = null;
        String query = "select sessionID from t_tempBarcode where docno = '"+docNo+"' && model = '"+model+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strSession = cursor.getString(cursor.getColumnIndexOrThrow("materialID"));
                handler = new strHandler(strSession);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //----Retrieve calculated quantity of items
    public strHandler calculateTotalQty(String sessionID, String docNo){
        strHandler handler = null;
        String query =
                "select SUM(barcode_qty) as total_qty from t_tempBarcode where docno='"+docNo+
                        "' and sessionID = '"+sessionID+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String mth = cursor.getString(cursor.getColumnIndexOrThrow("total_qty"));
                handler = new strHandler(mth);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //----Delete Material Data
    public boolean deleteTempAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("t_tempBarcode", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    public boolean deleteTempDetailsAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("t_tempBarcodeDetails", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    public boolean deleteTempMarkersAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("t_tempBarcodeMarkers", null, null)>0;
        db.close();

        return deleteSuccess;
    }
}
