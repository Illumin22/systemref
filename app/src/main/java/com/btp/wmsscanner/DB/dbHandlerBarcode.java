package com.btp.wmsscanner.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHandlerBarcode extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    protected static final String DATABASE_NAME = "dbLocalBarcode";

    //table
    private static final String TABLE_TEMP = "t_tempBarcode";
    private static final String TABLE_TEMP_DETAILS = "t_tempBarcodeDetails";
    private static final String TABLE_TEMP_MARKERS = "t_tempBarcodeMarkers";
    private static final String TABLE_TEMP_STOCK_HEADER = "t_tempStockcardHeader";
    private static final String TABLE_TEMP_STOCK_DETAILS = "t_tempStockcardDetails";
    private static final String TABLE_MAIN = "t_MainBarcode";

    //common columns
    private static final String KEY_ID = "_id";

    //column_temp_header
    private static final String KEY_DOCNO = "docno";
    private static final String KEY_DOCDATE = "docDate";
    private static final String KEY_BARCODE = "barcode";
    private static final String KEY_BARCODE_QTY = "barcode_qty";
    private static final String KEY_SCANDATE = "scan_date";
    private static final String KEY_PONO = "po_no";
    private static final String KEY_MAT = "material";
    private static final String KEY_MATCAT = "matCategoryIDFK";
    private static final String KEY_MATTYPE = "material_Type";
    private static final String KEY_MATCLASS = "material_Class";
    private static final String KEY_SUPPLIER = "supplier";
    private static final String KEY_TRANSTYPE = "transfer_type";
    private static final String KEY_SECTION = "section";
    private static final String KEY_MODEL = "model";
    private static final String KEY_SESSIONID = "sessionID";
    private static final String KEY_UPSTATUS = "upload_status";

    //column_temp_details
    private static final String KEY_D_TRANSIDFK = "transactionIDFK";
    private static final String KEY_D_SESSID = "sessionIDFK";
    private static final String KEY_D_SECTIONIDFK = "sectionIDFK";
    private static final String KEY_D_TRANSDATE = "transactionDate";
    private static final String KEY_D_TRANSQTY = "transactionQty";
    private static final String KEY_D_MOVEMENTQTY = "movement_qty";
    private static final String KEY_D_NGQTY = "ng_qty";
    private static final String KEY_D_TRANSFERIDFK = "transferIDFK";
    private static final String KEY_D_STIDFK = "stdIDFK";
    private static final String KEY_D_RACK = "rack";
    private static final String KEY_D_EMPNO = "empNo";
    private static final String KEY_D_DEVICENAME = "device";
    private static final String KEY_D_INVMTH = "iMthIDFK";
    private static final String KEY_D_INVYR = "iYrIDFK";
    private static final String KEY_D_ADDEDATE = "addedDate";
    private static final String KEY_D_ADDEDBY = "addedBy";

    //column_temp_markers
    private static final String KEY_ST_ID = "stdID";
    private static final String KEY_ST_DETAILID = "transacDetailsID";
    private static final String KEY_ST_SECIDFK = "secIDFK";
    private static final String KEY_ST_SECSTATUS = "secStatus";
    private static final String KEY_ST_SECDATE = "secDate";
    private static final String KEY_ST_SECREMARKS = "secRemarks";

    //column_temp_stockcard_header
    private static final String KEY_SC_H_SESSIONID = "sessionID";
    private static final String KEY_SC_H_DOCDATE = "documentDate";
    private static final String KEY_SC_H_DOCNO = "documentNo";
    private static final String KEY_SC_H_MODEL = "modelIDFK";
    private static final String KEY_SC_H_MATERIAL = "materialIDFK";
    private static final String KEY_SC_H_MATCAT = "matCategoryIDFK";
    private static final String KEY_SC_H_SUPPLIER = "supplierIDFK";
    private static final String KEY_SC_H_SECID = "secIDFK";
    private static final String KEY_SC_H_IQC = "iqc_status";//
    private static final String KEY_SC_H_INVMTH = "iMthIDFK";
    private static final String KEY_SC_H_INVYR = "iYrIDFK";
    private static final String KEY_SC_H_LEGEND_IDFK = "legendIDFK";//
    private static final String KEY_SC_H_ADDED_DATE = "addedDate";
    private static final String KEY_SC_H_ADDED_BY = "addedBy";
    private static final String KEY_SC_H_UPLOAD_STAT = "upload_status";

    //private static final String ;

    //sql_settings
  /*  private static final String CREATE_TABLE_TEMP_BARCODE =
            "CREATE TABLE " + TABLE_TEMP + "("  + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,
             " +
                    KEY_DOCNO + " TEXT, " +  KEY_DOCDATE + " TEXT, " +  KEY_BARCODE + " TEXT, " +
                    KEY_BARCODE_QTY + " TEXT, " +  KEY_SCANDATE + " TEXT, " +   KEY_PONO + " TEXT, "
                    +  KEY_EMPNO + " TEXT, " +  KEY_MAT + " TEXT, " +  KEY_MATTYPE + " TEXT, " +
                    KEY_MATCLASS + " TEXT, " +  KEY_SUPPLIER + " TEXT, " +  KEY_TRANSTYPE + " TEXT, "
                    +  KEY_SECTION + " TEXT, " +  KEY_RACK + " TEXT, " +  KEY_MODEL + " TEXT, " +
                    KEY_SESSIONID + " TEXT, " +  KEY_DEVICENAME + " TEXT, " +  KEY_UPSTATUS + " TEXT "
                    + ")";*/

   /* private static final String CREATE_TABLE_TEMP_BARCODE_DETAILS =
            "CREATE TABLE " + TABLE_TEMP_DETAILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_D_TRANSIDFK + " TEXT, " +  KEY_D_SESSID + " TEXT, " +  KEY_D_SECTIONIDFK +
                    " TEXT, " +  KEY_D_TRANSDATE + " TEXT, " +  KEY_D_TRANSQTY + " TEXT, " +
                    KEY_D_MOVEMENTQTY + " TEXT, " +  KEY_D_NGQTY + " TEXT, " +  KEY_D_TRANSFERIDFK +
                    " TEXT, " +  KEY_D_STIDFK + " TEXT, " +  KEY_D_ADDEDATE + " TEXT, " +
                    KEY_D_ADDEDBY + " TEXT " + ")";*/

    private static final String CREATE_TABLE_TEMP_BARCODE =
            "CREATE TABLE " + TABLE_TEMP + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_DOCNO + " TEXT, " +  KEY_DOCDATE + " TEXT, " +  KEY_BARCODE +
            " TEXT, " +  KEY_BARCODE_QTY + " INTEGER, " +  KEY_SCANDATE + " TEXT, " +
                    KEY_PONO + " TEXT, " + KEY_MAT + " TEXT, " + KEY_MATCAT + " TEXT, " +  KEY_MATTYPE + " TEXT, " +
                    KEY_MATCLASS + " TEXT, " + KEY_SUPPLIER + " TEXT, " + KEY_TRANSTYPE + " TEXT," +
                    " " + KEY_SECTION + " TEXT, " + KEY_MODEL + " TEXT, " + KEY_SESSIONID + " " +
                    "TEXT, " + KEY_UPSTATUS + " TEXT " + ")";

    private static final String CREATE_TABLE_TEMP_BARCODE_DETAILS =
            "CREATE TABLE " + TABLE_TEMP_DETAILS +  "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_D_TRANSIDFK + " TEXT, " +  KEY_D_SESSID + " TEXT, " +  KEY_D_SECTIONIDFK +
            " TEXT, " +  KEY_D_TRANSDATE + " TEXT, " +  KEY_D_TRANSQTY + " TEXT, " +
                    KEY_D_MOVEMENTQTY + " TEXT, " + KEY_D_NGQTY + " TEXT, " + KEY_D_TRANSFERIDFK + " TEXT, " +
                    KEY_D_STIDFK + " TEXT, " + KEY_D_RACK + " TEXT, " + KEY_D_EMPNO + " TEXT," +
            " " + KEY_D_DEVICENAME + " TEXT, " + KEY_D_INVMTH + " TEXT, " + KEY_D_INVYR + " " +
            "TEXT, " + KEY_D_ADDEDATE + " TEXT, " + KEY_D_ADDEDBY + " TEXT " + ")";

    private static final String CREATE_TABLE_TEMP_STOCK_HEADER =
            "CREATE TABLE " + TABLE_TEMP_STOCK_HEADER +  "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_SC_H_SESSIONID + " TEXT, " +  KEY_SC_H_DOCDATE + " TEXT, " +  KEY_SC_H_DOCNO +
                    " TEXT, " +  KEY_SC_H_MODEL + " TEXT, " +  KEY_SC_H_MATERIAL + " TEXT, " +
                    KEY_SC_H_MATCAT + " TEXT, " + KEY_SC_H_SUPPLIER + " TEXT, " + KEY_SC_H_SECID + " TEXT, " +
                    KEY_SC_H_IQC + " TEXT, " + KEY_SC_H_INVMTH + " TEXT, " + KEY_SC_H_INVYR + " TEXT," +
                    " " + KEY_SC_H_LEGEND_IDFK + " TEXT, " + KEY_SC_H_ADDED_DATE + " TEXT, " + KEY_SC_H_ADDED_BY + " " +
                    "TEXT, " + KEY_SC_H_UPLOAD_STAT + " TEXT " + ")";

    private static final String CREATE_TABLE_TEMP_BARCODE_MARKERS =
            "CREATE TABLE " + TABLE_TEMP_MARKERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_ST_ID + " TEXT, " +  KEY_ST_DETAILID + " TEXT, " +  KEY_ST_SECIDFK +
                    " TEXT, " +  KEY_ST_SECSTATUS + " TEXT, " +  KEY_ST_SECDATE + " TEXT, " +
                    KEY_ST_SECREMARKS + " TEXT " + ")";

    public dbHandlerBarcode(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEMP_BARCODE);
        db.execSQL(CREATE_TABLE_TEMP_BARCODE_DETAILS);
        db.execSQL(CREATE_TABLE_TEMP_BARCODE_MARKERS);
        db.execSQL(CREATE_TABLE_TEMP_STOCK_HEADER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_MARKERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_STOCK_HEADER);
        onCreate(db);
    }
}