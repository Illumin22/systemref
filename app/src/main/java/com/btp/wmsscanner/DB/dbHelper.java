package com.btp.wmsscanner.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.btp.wmsscanner.STR.dataCollector;
import com.btp.wmsscanner.STR.strHandler;

import java.util.ArrayList;

public class dbHelper extends dbHandler {
    private Context dbHelper;

    public dbHelper(Context context) {
        super(context);
    }

    //===============================SETTINGS METHODS====================================
    //----Create new data in settings(localhost)
    public boolean createSettingDt(dataCollector dtCollection){

        ContentValues val = new ContentValues();
        val.put("settings_config", dtCollection.rawLocalHost);
        Log.d("helper_setting", dtCollection.rawLocalHost);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSettingUp = db.insertOrThrow("tbl_Settings", null, val)>0;
        db.close();
        return createSettingUp;
    }

    //----Update data in settings(localhost)
    public boolean updateSetting(String strLocalHost, String strId){

        ContentValues val = new ContentValues();
        val.put("settings_config", strLocalHost);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSettingUp = db.update("tbl_Settings",  val, strId, null)>0;
        db.close();
        return updateSettingUp;
    }

    //----Retrieve data in settings(localhost)
    public strHandler checkSettings(){
        strHandler handler = null;
        String query = "select * from tbl_Settings";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strSetting = cursor.getString(cursor.getColumnIndexOrThrow("settings_config"));
                String strId = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                handler = new strHandler(strId, strSetting);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //===================================================================================

    //================================SPINNER METHODS====================================
    /**/
    //----Retrieve Material Data
    public String[] materialData(){
        String query = "select distinct materialName from tbl_Material ORDER BY materialName asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String mat = cursor.getString(cursor.getColumnIndexOrThrow("materialName"));
                items.add(mat);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Material Data
    public String[] rackData(){
        String query = "select distinct rackName from tbl_Racks ORDER BY _id asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String rack = cursor.getString(cursor.getColumnIndexOrThrow("rackName"));
                items.add(rack);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Material Data
    public String[] materialTypeData(){
        String query = "select distinct matTypeName from tbl_MaterialType ORDER BY matTypeID asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String matType = cursor.getString(cursor.getColumnIndexOrThrow("matTypeName"));
                items.add(matType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Supplier Data
    public String[] supplierData(){
        String query = "select * from tbl_Supplier ORDER BY supplierName asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String supplier = cursor.getString(cursor.getColumnIndexOrThrow("supplierName"));
                items.add(supplier);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Class Type Data
    public String[] classTypeData(){
        String query = "select * from tbl_ClassType ORDER BY className asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String classType = cursor.getString(cursor.getColumnIndexOrThrow("className"));
                items.add(classType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Model Data
    public String[] modelData(){
        String query = "select * from tbl_Model ORDER BY _id asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String model = cursor.getString(cursor.getColumnIndexOrThrow("modelName"));
                items.add(model);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Material Category Data
    public String[] materialCategoryData(){
        String query = "select distinct matCategoryName from tbl_MaterialCategory ORDER BY _id asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String mat = cursor.getString(cursor.getColumnIndexOrThrow("matCategoryName"));
                items.add(mat);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Class Type Data with variable
    public String[] classTypeDataWMaterial(String matID){
        String query = "select distinct className from tbl_ClassType where materialIDFk in (select materialID from tbl_Material where materialName = '"+matID+"') ORDER BY className asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String classType = cursor.getString(cursor.getColumnIndexOrThrow("className"));
                Log.d("ClassDB", "Class Type: " + classType );
                items.add(classType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Material Type Data with variable
    public String[] materialTypeDataWMaterial(String matID){
        String query = "select distinct matTypeName from tbl_MaterialType where materialIDFk in (select materialID from tbl_Material where materialName = '"+matID+"') ORDER BY matTypeID asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String matType = cursor.getString(cursor.getColumnIndexOrThrow("matTypeName"));
                items.add(matType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Supplier Data with variables
    public String[] supplierDataWMaterial(String matID){
        String query = "select distinct supplierName from tbl_Supplier where materialIDFk in (select materialID from tbl_Material where materialName = '"+matID+"') ORDER BY supplierID asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String supplierType = cursor.getString(cursor.getColumnIndexOrThrow("supplierName"));
                items.add(supplierType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Supplier Data with variables
    public String[] supplierDataWMaterialType(String matID, String typeID){
        String query = "select distinct supplierName from tbl_Supplier where materialIDFk in (select materialID from tbl_Material where materialName = '"+matID+"') ORDER BY supplierID asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String supplierType = cursor.getString(cursor.getColumnIndexOrThrow("supplierName"));
                items.add(supplierType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Section with variables
    public String[] sectionDataWClass(String strClassID){
        String query = "select distinct sectionName from tbl_Section where classIDFK in (select classID from tbl_ClassType where className = '"+strClassID+"') ORDER BY sectionID asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String section = cursor.getString(cursor.getColumnIndexOrThrow("sectionName"));
                Log.d("ClassDB", "Section: " + section );
                items.add(section);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Class Type Data with variable
    public String[] modelWMaterial(String matID, String supplierID){
        String query = "select distinct modelName from tbl_Model where materialIDFk in (select " +
                "materialID from tbl_Material where materialName = '"+matID+"') and supplierIDFK" +
                "='"+supplierID+"'" +
                " ORDER BY modelName asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String classType = cursor.getString(cursor.getColumnIndexOrThrow("modelName"));
                items.add(classType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Material Category Data with variable
    public String[] MaterialWMaterialCat(String matID){
        String query = "select distinct materialName from tbl_Material where matCategoryIDFK in (select matCategoryID from tbl_MaterialCategory where matCategoryName = '"+matID+"') ORDER BY materialID asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String mat = cursor.getString(cursor.getColumnIndexOrThrow("materialName"));
                items.add(mat);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Transfer Type Data
    public String[] transferTypeData(){
        String query = "select * from tbl_TransferType ORDER BY transferName asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String transferType = cursor.getString(cursor.getColumnIndexOrThrow("transferName"));
                items.add(transferType);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Section Data
    public String[] sectionData(){
        String query = "select * from tbl_Section ORDER BY sectionName asc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String section = cursor.getString(cursor.getColumnIndexOrThrow("sectionName"));
                items.add(section);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Active Month Data
    public String[] monthData(){
        String query = "select distinct iMth from tbl_Month  inner join tbl_ActiveMonthYear on " +
                "iMthID = activeMth where isActive = 'Y' order by iMthID desc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String mth = cursor.getString(cursor.getColumnIndexOrThrow("iMth"));
                items.add(mth);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Active Year Data
    public String[] yearData(){
        String query = "select distinct iYr from tbl_Year inner join tbl_ActiveMonthYear on iYrID" +
                " = " +
                "activeYr" +
                " " +
                "where isActive = 'Y' order by iYrID desc";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<String> items = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String yr = cursor.getString(cursor.getColumnIndexOrThrow("iYr"));
                items.add(yr);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        String[] collection = new String[items.size()];
        collection = items.toArray(collection);
        return collection;
    }

    //----Retrieve Single Active Year Data
    public strHandler yearSingleData(){
        strHandler handler = null;
        String query = "select iYr from tbl_Year inner join tbl_ActiveMonthYear on iYrID = " +
                "activeYr" +
                " " +
                "where isActive = 'Y'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String yr = cursor.getString(cursor.getColumnIndexOrThrow("iYr"));
                handler = new strHandler(yr);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //----Retrieve Single Active Month Data
    public strHandler monthSingleData(){
        strHandler handler = null;
        String query = "select iMth from tbl_Month inner join tbl_ActiveMonthYear on iMthID = activeMth where isActive = 'Y'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String mth = cursor.getString(cursor.getColumnIndexOrThrow("iMth"));
                handler = new strHandler(mth);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //----Insert Material Data
    public boolean insertMaterial(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("materialID", itemCollection.materialDataID);
        values.put("materialName", itemCollection.materialDataName);
        values.put("classIDFK", itemCollection.materialDataClassCode);
        values.put("matCategoryIDFK", itemCollection.materialMatCatIDFK);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_Material", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Material Data
    public boolean insertRacks(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("rackID", itemCollection.rackDataID);
        values.put("rackCode", itemCollection.rackDataCode);
        values.put("rackName", itemCollection.rackDataName);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_Racks", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Material Type Data
    public boolean insertMaterialType(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("materialIDFK", itemCollection.materialDataIDFK);
        values.put("matTypeID", itemCollection.materialTypeID);
        values.put("materialName", itemCollection.materialDataName);
        values.put("matTypeName", itemCollection.materialTypeName);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_MaterialType", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Supplier Data
    public boolean insertSupplier(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("supplierID", itemCollection.supplierDataID);
        values.put("supplierName", itemCollection.supplierDataName);
        values.put("supplierCode", itemCollection.supplierDataCode);
        values.put("materialIDFK", itemCollection.supplierDataMaterialID);
        values.put("matTypeIDFK", itemCollection.supplierDataMaterialTypeID);


        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_Supplier", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Class Type Data
    public boolean insertClassType(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("classID", itemCollection.classDataID);
        values.put("className", itemCollection.classDataName);
        values.put("classCode", itemCollection.classDataCode);
        values.put("materialIDFK", itemCollection.classDataMaterialID);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_ClassType", null, values)> 0;
        db.close();
        return createSuccessful;
    }

    //----Insert Transfer Type Data
    public boolean insertTransferType(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("transferID", itemCollection.transferDataID);
        values.put("transferName", itemCollection.transferDataName);
        values.put("transferCode", itemCollection.transferDataCode);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_TransferType", null, values)> 0;
        db.close();
        return createSuccessful;
    }

    //----Insert Section Data
    public boolean insertSection(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("sectionID", itemCollection.sectionDataID);
        values.put("sectionName", itemCollection.sectionDataName);
        values.put("sectionCode", itemCollection.sectionDataCode);
        values.put("classIDFK", itemCollection.sectionDataClassID);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_Section", null, values)> 0;
        db.close();
        return createSuccessful;
    }

    //----Insert Process Flow Data
    public boolean insertProcessFlow(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("processCode", itemCollection.processDataCode);
        values.put("isLastProcess", itemCollection.processDataIsLastProcess);
        values.put("sectionIDFK", itemCollection.processDataSectionID);
        values.put("materialIDFK", itemCollection.processDataMaterialID);
        values.put("classIDFK", itemCollection.processDataClassID);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_ProcessFlow", null, values)> 0;
        db.close();
        return createSuccessful;
    }

    //----Insert Material Data
    public boolean insertModel(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("modelID", itemCollection.modelDataID);
        values.put("modelCode", itemCollection.modelDataCode);
        values.put("modelName", itemCollection.modelDataName);
        values.put("materialIDFK", itemCollection.modelDataMaterialID);
        values.put("supplierIDFK", itemCollection.modelDataSupplierID);
        values.put("mcID", itemCollection.modelDataMcID);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_Model", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Material Data
    public boolean insertSectionTransfer(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("secID", itemCollection.stSecID);
        values.put("classIDFK", itemCollection.stClassIDFK);
        values.put("sectionIDFK", itemCollection.stSectionIDFK);
        values.put("transferIDFK", itemCollection.stTransferIDFK);
        values.put("directionIDFK", itemCollection.stDirection);
        values.put("sequence", itemCollection.stSequence);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_sectionTransfer", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Material Category Data
    public boolean insertMaterialCategory(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("matCategoryID", itemCollection.catDataID);
        values.put("matCategoryName", itemCollection.catDataName);
        values.put("materialIDFK", itemCollection.catDataMatIDFK);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_MaterialCategory", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Month Data
    public boolean insertMonth(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("iMthID", itemCollection.dataIMthID);
        values.put("iMth", itemCollection.dataIMth);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_Month", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Year Data
    public boolean insertYear(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("iYrID", itemCollection.dataIYrID);
        values.put("iYr", itemCollection.dataIYr);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_Year", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Month/Year Data
    public boolean insertActiveMonthYear(dataCollector itemCollection){

        ContentValues values = new ContentValues();
        values.put("activeMth", itemCollection.dataActMth);
        values.put("activeYr", itemCollection.dataActYr);
        values.put("isActive", itemCollection.dataActStat);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_ActiveMonthYear", null, values)> 0;
        db.close();

        return createSuccessful;
    }

    //----Insert Month/Year Data
    public boolean insertCodeDefinition(dataCollector itemCollection){
        ContentValues values = new ContentValues();
        values.put("bcID", itemCollection.dataBcID);
        values.put("delimeter_char", itemCollection.dataDelimiter);
        values.put("materialID", itemCollection.dataMatID);
        values.put("mcID", itemCollection.dataMcID);
        values.put("model_pos", itemCollection.dataModPos);
        values.put("model_len", itemCollection.dataModLen);
        values.put("qty_pos", itemCollection.dataQtyPos);
        values.put("qty_len", itemCollection.dataQtyLen);
        values.put("po_no_pos", itemCollection.dataPoPos);
        values.put("po_no_len", itemCollection.dataPoLen);
        values.put("invoice_no_pos", itemCollection.dataInvPos);
        values.put("invoice_no_len", itemCollection.dataInvLen);
        values.put("total_len", itemCollection.dataTotalLen);

        values.put("part_no_pos", itemCollection.dataPartPos);
        values.put("part_no_len", itemCollection.dataPartLen);
        values.put("lot_no_pos", itemCollection.dataLotPos);
        values.put("lot_no_len", itemCollection.dataLotLen);

        values.put("req_delimiter", itemCollection.dataReqDelimiter);
        values.put("req_model", itemCollection.dataReqModel);
        values.put("req_qty", itemCollection.dataReqQty);
        values.put("req_po_No", itemCollection.dataReqPo);
        values.put("req_invoice_no", itemCollection.dataReqInvoice);
        values.put("req_part_no", itemCollection.dataReqPart);
        values.put("req_lot_no", itemCollection.dataReqLot);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insertOrThrow("tbl_CodeDefinition", null, values)> 0;
        db.close();

        return createSuccessful;
    }
    //----Update Material Data
   /* public boolean updateMaterial(String value, String data){

        ContentValues val = new ContentValues();
        val.put("materialName", value);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateMaterialUp = db.update("tbl_Material",  val, data, null)>0;
        db.close();
        return updateMaterialUp;
    }*/

    //----Delete Material Data
    public boolean deleteMaterialAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_Material", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Material Data
    public boolean deleteMaterialTypeAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_MaterialType", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Supplier Data
    public boolean deleteSupplierAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_Supplier", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Class Type Data
    public boolean deleteClassTypeAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_ClassType", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Transfer Type Data
    public boolean deleteTransferTypeAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_TransferType", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Section Data
    public boolean deleteSectionAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_Section", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Process Flow Data
    public boolean deleteProcessFlowAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_ProcessFlow", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Process Flow Data
    public boolean deleteRackAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_Racks", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Model Data
    public boolean deleteModelAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_Model", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Section Transfer Data
    public boolean deleteStAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_sectionTransfer", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Material Category Data
    public boolean deleteMatCatAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_MaterialCategory", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Month Data
    public boolean deleteMonthAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_Month", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Year Data
    public boolean deleteYearAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_Year", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Active Month/Year Data
    public boolean deleteActiveMonthYearAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_ActiveMonthYear", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    //----Delete Code Definition Data
    public boolean deleteCodeDefinitionAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean deleteSuccess = db.delete("tbl_CodeDefinition", null, null)>0;
        db.close();

        return deleteSuccess;
    }

    public boolean deleteRackAll1(){
        final dbHandler handler = new dbHandler(dbHelper);
        handler.dropRacks();

        return false;
    }

    /*public boolean deleteModelAll1(){
        final dbHandler handler = new dbHandler(dbHelper);
        handler.dropModel();

        return false;
    }*/
    //===================================================================================
    /**/
    //================================GET INDEX METHODS==================================

    //---- Get Material Index
    public strHandler getMaterialCategoryIndex(String selection){
        strHandler handler = null;
        String query = "select matCategoryID from tbl_MaterialCategory where matCategoryName = '"+selection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("matCategoryID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Material Index
    public strHandler getMaterialIndex(String selection){
        strHandler handler = null;
        String query = "select materialID from tbl_Material where materialName = '"+selection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("materialID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Material Type Index
    public strHandler getMatTypeIndex(String selection){
        strHandler handler = null;
        String query = "select matTypeID from tbl_MaterialType where matTypeName = '"+selection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("matTypeID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Material Class Index
    public strHandler getMatClassIndex(String selection){
        strHandler handler = null;
        String query = "select classID from tbl_ClassType where className = '"+selection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("classID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Supplier Index
    public strHandler getSupplierIndex(String selection){
        strHandler handler = null;
        String query = "select supplierID from tbl_Supplier where supplierName = '"+selection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("supplierID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Transfer Type Index
    public strHandler getTransTypeIndex(String selection){
        strHandler handler = null;
        String query = "select transferID from tbl_TransferType where transferName = '"+selection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("transferID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Section Index
    public strHandler getSectionIndex(String selection){
        strHandler handler = null;
        String query = "select sectionID from tbl_Section where sectionName = '"+selection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("sectionID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Model Index
    public strHandler getModelIndex(String selection, String supplierSelection){
        strHandler handler = null;
        String query = "select mcID from tbl_Model where modelName = '"+selection+"' and " +
                "supplierIDFK='"+supplierSelection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("mcID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Model Index mcType
    public strHandler getModelIndexMc(String selection, String supplierSelection){
        strHandler handler = null;
        String query = "select mcID from tbl_Model where modelName = '"+selection+"' and " +
                "supplierIDFK IN (select supplierID from tbl_Supplier where supplierName='"+supplierSelection+"')";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("mcID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Model Index Original
    public strHandler getModelIndexOrig(String selection, String supplierSelection){
        strHandler handler = null;
        String query = "select modelID from tbl_Model where modelName = '"+selection+"' and " +
                "supplierIDFK='"+supplierSelection+"'";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("modelID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Section Transfer Index
    public strHandler getSectionTransferIndex(String classID, String sectionID, String transferID){
        strHandler handler = null;
        String query = "select secID from tbl_sectionTransfer where classIDFK in (select classID " +
                "from tbl_ClassType where className = '"+classID+"'" + ") and sectionIDFK in " +
                "(select sectionID from tbl_Section where sectionName = '"+sectionID+"' ) and " +
                "transferIDFK in (select transferID from tbl_TransferType where transferName = '"+transferID+"');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("secID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Section Transfer Index(Direct ID)
    public strHandler getSectionTransferIndexDirect(String classID, String sectionID,
                                                String transferID){
        strHandler handler = null;
        String query = "select secID from tbl_sectionTransfer where classIDFK="+classID+" and " +
                "sectionIDFK="+sectionID+" and " +
                "transferIDFK="+transferID+";";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("secID"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Month Index
    public strHandler getMonthIndex(String selection){
        strHandler handler = null;
        String query = "select activeMth from tbl_ActiveMonthYear where activeMth in (select " +
                "iMthID from tbl_Month where iMth = '"+selection+"') order by activeMth desc;";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("activeMth"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Year Index
    public strHandler getYearIndex(String selection){
        strHandler handler = null;
        String query = "select activeYr from tbl_ActiveMonthYear where activeYr in (select iYrID " +
                "from tbl_Year where iYr = '"+selection+"') order by activeYr desc;";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("activeYr"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //===================================================================================
    /**/
    //============================GET CODE DEFINITION METHODS============================

    //---- Get Delimiter
    public strHandler getDelimeterChar(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select delimeter_char from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("delimeter_char"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Model Position
    public strHandler getModelPos(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select model_pos from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("model_pos"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Model End Position
    public strHandler getModelLen(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select model_len from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("model_len"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Qty Position
    public strHandler getQtyPos(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select qty_pos from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("qty_pos"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Qty End Position
    public strHandler getQtyLen(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select qty_len from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("qty_len"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get PO No Position
    public strHandler getPoPos(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select po_no_pos from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("po_no_pos"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get PO No Length
    public strHandler getPoLen(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select po_no_len from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("po_no_len"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Invoice Position
    public strHandler getInvPos(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select invoice_no_pos from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("invoice_no_pos"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Invoice Length
    public strHandler getInvLen(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select invoice_no_len from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("invoice_no_len"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //---- Get Part No Position
    public strHandler getPartPos(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select part_no_pos from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("part_no_pos"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Part No Length
    public strHandler getPartLen(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select part_no_len from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("part_no_len"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //---- Get Lot No Position
    public strHandler getLotPos(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select lot_no_pos from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("lot_no_pos"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Lot No Length
    public strHandler getLotLen(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select lot_no_len from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("lot_no_len"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //---- Get Total Length
    public strHandler getTotalLen(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select total_len from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("total_len"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //---- Get Delimiter Status
    public strHandler getDelimiterStats(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select req_delimiter from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("req_delimiter"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Model Status
    public strHandler getModelStats(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select req_model from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("req_model"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Qty Status
    public strHandler getQtyStats(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select req_qty from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("req_qty"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get PO No Status
    public strHandler getPoStats(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select req_po_No from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("req_po_No"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Invoice No Status
    public strHandler getInvoiceStats(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select req_invoice_no from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("req_invoice_no"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Part No Status
    public strHandler getPartStats(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select req_part_no from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("req_part_no"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }
    //---- Get Lot No Status
    public strHandler getLotStats(String materialID, String modelID, String supplierID){
        strHandler handler = null;
        String query = "select req_lot_no from tbl_CodeDefinition where materialID='"+materialID+
                "' and mcID in "+
                "(select mcID from tbl_Model where modelID = '"+modelID+"' and supplierIDFK='"+supplierID+
                "');";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("req_lot_no"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

    //---- Get Model Code
    public strHandler getModelCode(String modelName){
        strHandler handler = null;
        String query = "select distinct modelCode from tbl_Model where modelName = '"+modelName+"';";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String strIndex = cursor.getString(cursor.getColumnIndexOrThrow("modelCode"));
                handler = new strHandler(strIndex);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return handler;
    }

}
