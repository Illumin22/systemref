package com.btp.wmsscanner.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    protected static final String DATABASE_NAME = "dbLocal";

    //table
    private static final String TABLE_SETTINGS = "tbl_Settings";
    private static final String TABLE_SUPPLIER = "tbl_Supplier";
    private static final String TABLE_MATERIAL = "tbl_Material";
    private static final String TABLE_CLASS_TYPE = "tbl_ClassType";
    private static final String TABLE_TRANSFER_TYPE = "tbl_TransferType";
    private static final String TABLE_SECTION = "tbl_Section";
    private static final String TABLE_PROCESS_FLOW = "tbl_ProcessFlow";
    private static final String TABLE_MATERIAL_TYPE = "tbl_MaterialType";
    private static final String TABLE_RACKS = "tbl_Racks";
    private static final String TABLE_MODEL = "tbl_Model";
    private static final String TABLE_MATERIAL_CATEGORY = "tbl_MaterialCategory";
    private static final String TABLE_SECTION_TRANSFER = "tbl_sectionTransfer";
    private static final String TABLE_MONTH = "tbl_Month";
    private static final String TABLE_YEAR = "tbl_Year";
    private static final String TABLE_ACTIVE_MY = "tbl_ActiveMonthYear";
    private static final String TABLE_CODE_DEFINITION = "tbl_CodeDefinition";
    private static final String TABLE_FINAL_SCANNED_CODE = "tbl_scannedCodeFinal";

    //common columns
    private static final String KEY_ID = "_id";

    //column_settings
    private static final String KEY_SETTINGS = "settings_config";

    //column supplier
    private static final String KEY_SUPPLIER_ID = "supplierID";
    private static final String KEY_SUPPLIER_NAME = "supplierName";
    private static final String KEY_SUPPLIER_CODE = "supplierCode";
    private static final String KEY_SUPPLIER_MATERIAL_IDFK = "materialIDFK";
    private static final String KEY_SUPPLIER_MATERIAL_TYPE_IDFK = "matTypeIDFK";

    //column material
    private static final String KEY_MATERIAL_ID = "materialID";
    private static final String KEY_MATERIAL_NAME = "materialName";
    private static final String KEY_MATERIAL_CLASS_CODE = "classIDFK";
    private static final String KEY_MATERIAL_MATCAT_IDFK = "matCategoryIDFK";

    //column rack
    private static final String KEY_RACK_ID = "rackID";
    private static final String KEY_RACK_CODE = "rackCode";
    private static final String KEY_RACK_NAME = "rackName";

    //column material type
    private static final String KEY_MATERIAL_TYPE_ID = "materialIDFK";
    private static final String KEY_MATERIAL_TYPE_MATID = "matTypeID";
    private static final String KEY_MATERIAL_TYPE_NAME = "materialName";
    private static final String KEY_MATERIAL_TYPE_MATNAME = "matTypeName";

    //column class
    private static final String KEY_CLASS_ID = "classID";
    private static final String KEY_CLASS_NAME = "className";
    private static final String KEY_CLASS_CODE = "classCode";
    private static final String KEY_CLASS_MATERIAL_IDFK = "materialIDFK";

    //column transfer type
    private static final String  KEY_TRANSFER_ID ="transferID";
    private static final String  KEY_TRANSFER_NAME ="transferName";
    private static final String  KEY_TRANSFER_CODE ="transferCode";

    //column Section
    private static final String KEY_SECTION_ID = "sectionID";
    private static final String KEY_SECTION_NAME = "sectionName";
    private static final String KEY_SECTION_CODE = "sectionCode";
    private static final String KEY_SECTION_CLASS_IDFK = "classIDFK";

    //column Process Flow
    private static final String KEY_PF_ID = "processCode";
    private static final String KEY_PF_IS_LP = "isLastProcess";
    private static final String KEY_PF_SECTION_IDFK = "sectionIDFK";
    private static final String KEY_PF_MATERIAL_IDFK = "materialIDFK";
    private static final String KEY_PF_CLASS_IDFK = "classIDFK";

    //column Model
    private static final String KEY_MODEL_ID = "modelID";
    private static final String KEY_MODEL_NAME = "modelCode";
    private static final String KEY_MODEL_CODE = "modelName";
    private static final String KEY_MODEL_MATERIAL_IDFK = "materialIDFK";
    private static final String KEY_MODEL_SUPPLIER_IDFK = "supplierIDFK";
    private static final String KEY_MODEL_MC_ID = "mcID";


    //column Section Transfer
    private static final String KEY_ST_SECID = "secID";
    private static final String KEY_ST_TRANS_IDFK = "classIDFK";
    private static final String KEY_ST_SECTION_IDFK = "sectionIDFK";
    private static final String KEY_ST_TRANSFER_IDFK = "transferIDFK";
    private static final String KEY_ST_DIRECTION_IDFK = "directionIDFK";
    private static final String KEY_ST_SEQUENCE = "sequence";

    //column material category
    private static final String KEY_MATERIAL_CAT_ID = "matCategoryID";
    private static final String KEY_MATERIAL_CAT_NAME = "matCategoryName";
    private static final String KEY_MATERIAL_CAT_MATIDFK = "materialIDFK";

    //column month
    private static final String KEY_I_MONTH_ID = "iMthID";
    private static final String KEY_I_MONTH_NAME = "iMth";

    //column year
    private static final String KEY_I_YEAR_ID = "iYrID";
    private static final String KEY_I_YEAR_NAME = "iYr";

    //column active month/year
    private static final String KEY_ACTIVE_MY_ID = "activeMth";
    private static final String KEY_ACTIVE_MY_NAME = "activeYr";
    private static final String KEY_ACTIVE_MY_ACTIVESTAT = "isActive";

    //column code definition
    private static final String KEY_CD_BCID = "bcID";
    private static final String KEY_CD_DELI_CHAR = "delimeter_char";
    private static final String KEY_CD_MATID = "materialID";
    private static final String KEY_CD_MCID = "mcID";
    private static final String KEY_CD_MOD_POS = "model_pos";
    private static final String KEY_CD_MOD_LEN = "model_len";
    private static final String KEY_CD_QTY_POS = "qty_pos";
    private static final String KEY_CD_QTY_LEN = "qty_len";
    private static final String KEY_CD_PO_POS = "po_no_pos";
    private static final String KEY_CD_PO_LEN = "po_no_len";
    private static final String KEY_CD_INV_POS = "invoice_no_pos";
    private static final String KEY_CD_INV_LEN = "invoice_no_len";

    private static final String KEY_CD_PART_POS = "part_no_pos";
    private static final String KEY_CD_PART_LEN = "part_no_len";
    private static final String KEY_CD_LOT_POS = "lot_no_pos";
    private static final String KEY_CD_LOT_LEN = "lot_no_len";

    private static final String KEY_CD_TOTAL_LEN = "total_len";

    private static final String KEY_CD_REQ_DELIMITER = "req_delimiter";
    private static final String KEY_CD_REQ_MODEL = "req_model";
    private static final String KEY_CD_REQ_QTY = "req_qty";
    private static final String KEY_CD_REQ_PO = "req_po_No";
    private static final String KEY_CD_REQ_INVOICE = "req_invoice_no";
    private static final String KEY_CD_REQ_PART = "req_part_no";
    private static final String KEY_CD_REQ_LOT = "req_lot_no";



    //sql_settings
    private static final String CREATE_TABLE_SETTINGS =
            "CREATE TABLE " + TABLE_SETTINGS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_SETTINGS + " TEXT " + ")";
    //------------------
    private static final String CREATE_SUPPLIER =
            "CREATE TABLE " + TABLE_SUPPLIER + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, "  + KEY_SUPPLIER_ID + " INTEGER, " +  KEY_SUPPLIER_NAME + " " +
                    "TEXT,  " + KEY_SUPPLIER_CODE + " TEXT, " + KEY_SUPPLIER_MATERIAL_TYPE_IDFK + " INTEGER, " + KEY_SUPPLIER_MATERIAL_IDFK + " INTEGER " + ")";
    //------------------
    private static final String CREATE_MATERIAL =
            "CREATE TABLE " + TABLE_MATERIAL + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, "  + KEY_MATERIAL_ID + " INTEGER, " +  KEY_MATERIAL_CLASS_CODE +
                    " TEXT, " +  KEY_MATERIAL_NAME + " TEXT, " +  KEY_MATERIAL_MATCAT_IDFK + " TEXT " + ")";
    //------------------
    private static final String CREATE_RACK =  "CREATE TABLE " + TABLE_RACKS + "(" + KEY_ID + " " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_RACK_ID + " INTEGER, " +  KEY_RACK_CODE + " " +
            "TEXT, " +  KEY_RACK_NAME + " TEXT " + ")";
    //------------------
    private static final String CREATE_MATERIAL_TYPE =  "CREATE TABLE " + TABLE_MATERIAL_TYPE +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MATERIAL_TYPE_ID + " " +
            "INTEGER,  " +  KEY_MATERIAL_TYPE_MATID + " INTEGER, " +  KEY_MATERIAL_TYPE_NAME + " " +
            " TEXT," +  KEY_MATERIAL_TYPE_MATNAME + " TEXT " + ")";
    //------------------
    private static final String CREATE_CLASS =
            "CREATE TABLE " + TABLE_CLASS_TYPE + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT,  " + KEY_CLASS_ID + " INTEGER, " +  KEY_CLASS_CODE + " TEXT, "+
                    KEY_CLASS_NAME + " TEXT, " + KEY_CLASS_MATERIAL_IDFK + " INTEGER " +")";
    //------------------
    private static final String CREATE_TRANSFER =
            "CREATE TABLE " + TABLE_TRANSFER_TYPE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_TRANSFER_ID + " INTEGER, " +  KEY_TRANSFER_CODE + " TEXT, " +
                    KEY_TRANSFER_NAME + " TEXT " + ")";
    //------------------
    private static final String CREATE_SECTION =
            "CREATE TABLE " + TABLE_SECTION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_SECTION_ID + " INTEGER, " +  KEY_SECTION_CODE + " TEXT, " +  KEY_SECTION_NAME + "" +
                    " TEXT, " +   KEY_SECTION_CLASS_IDFK + " INTEGER " + ")";
    //------------------
    private static final String CREATE_PROCESS_FLOW =
            "CREATE TABLE " + TABLE_PROCESS_FLOW + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "" + KEY_PF_ID + " INTEGER, " +  KEY_PF_IS_LP + " TEXT, " +  KEY_PF_SECTION_IDFK
                    + " INTEGER, " +   KEY_PF_MATERIAL_IDFK + " INTEGER, " + KEY_PF_CLASS_IDFK + " " +
                    "INTEGER " + ")";
    //------------------
    private static final String CREATE_MODEL =  "CREATE TABLE " + TABLE_MODEL + "(" + KEY_ID + " " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MODEL_ID + " INTEGER, " +  KEY_MODEL_CODE + "" +
            " TEXT, " +  KEY_MODEL_NAME + " TEXT, " +   KEY_MODEL_MATERIAL_IDFK + " INTEGER, " +
            KEY_MODEL_SUPPLIER_IDFK + " INTEGER, " +
            KEY_MODEL_MC_ID + " INTEGER " + ")";
    //------------------
    private static final String CREATE_SECTION_TRANSFER =
            "CREATE TABLE " + TABLE_SECTION_TRANSFER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_ST_SECID + " INTEGER, " +  KEY_ST_TRANS_IDFK + " INTEGER, " +
                    KEY_ST_SECTION_IDFK + " INTEGER, " +   KEY_ST_TRANSFER_IDFK + " INTEGER, " +
                    KEY_ST_DIRECTION_IDFK + " INTEGER, " +   KEY_ST_SEQUENCE + " INTEGER " + ")";
    //------------------
    private static final String CREATE_MATERIAL_CATEGORY =
            "CREATE TABLE " + TABLE_MATERIAL_CATEGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, "
                    + KEY_MATERIAL_CAT_ID + " INTEGER, " +  KEY_MATERIAL_CAT_MATIDFK + " TEXT, " +
                    KEY_MATERIAL_CAT_NAME + " TEXT " + ")";
    //------------------
    private static final String CREATE_MONTH =
            "CREATE TABLE " + TABLE_MONTH + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, " +  KEY_I_MONTH_ID + " TEXT, " +
                    KEY_I_MONTH_NAME + " TEXT " + ")";
    //------------------
    private static final String CREATE_YEAR =
            "CREATE TABLE " + TABLE_YEAR + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, " +  KEY_I_YEAR_ID + " TEXT, " +
                    KEY_I_YEAR_NAME + " TEXT " + ")";
    //------------------
    private static final String CREATE_ACTIVE_MY =
            "CREATE TABLE " + TABLE_ACTIVE_MY + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, " +  KEY_ACTIVE_MY_ID + " TEXT, " +
                    KEY_ACTIVE_MY_NAME + " TEXT, " +
                    KEY_ACTIVE_MY_ACTIVESTAT + " TEXT " + ")";
    //------------------
    private static final String CREATE_CODE_DEFINITION =
            "CREATE TABLE " + TABLE_CODE_DEFINITION + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, " +  KEY_CD_BCID + " TEXT, " +
                    KEY_CD_DELI_CHAR + " TEXT, " +
                    KEY_CD_MATID + " TEXT, " +
                    KEY_CD_MCID + " TEXT, " +
                    KEY_CD_MOD_POS + " TEXT, " +
                    KEY_CD_MOD_LEN + " TEXT, " +
                    KEY_CD_QTY_POS + " TEXT, " +
                    KEY_CD_QTY_LEN + " TEXT, " +
                    KEY_CD_PO_POS + " TEXT, " +
                    KEY_CD_PO_LEN + " TEXT, " +
                    KEY_CD_INV_POS + " TEXT, " +
                    KEY_CD_INV_LEN + " TEXT, " +

                    KEY_CD_PART_POS + " TEXT, " +
                    KEY_CD_PART_LEN + " TEXT, " +
                    KEY_CD_LOT_POS + " TEXT, " +
                    KEY_CD_LOT_LEN + " TEXT, " +

                    KEY_CD_REQ_DELIMITER + " TEXT, " +
                    KEY_CD_REQ_MODEL + " TEXT, " +
                    KEY_CD_REQ_QTY + " TEXT, " +
                    KEY_CD_REQ_PO + " TEXT, " +
                    KEY_CD_REQ_INVOICE + " TEXT, " +
                    KEY_CD_REQ_PART + " TEXT, " +
                    KEY_CD_REQ_LOT + " TEXT, " +

                    KEY_CD_TOTAL_LEN + " TEXT " + ")";

    // private static final String CREATE_FINAL_SCAN = "CREATE TABLE " + TABLE_FINAL_SCANNED_CODE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SCAN_DATA + " TEXT, " +  KEY_SCAN_DATE + " TEXT, " +  KEY_SCAN_DOC_NO + " TEXT, " +  KEY_SCAN_TRANSFER + " TEXT, " +   KEY_SCAN_MATERIAL + " TEXT, " +  KEY_SCAN_SUPPLIER + " TEXT, " +  KEY_SCAN_QTY + " INTEGER " + ")";

    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SETTINGS);
        db.execSQL(CREATE_SUPPLIER);
        db.execSQL(CREATE_MATERIAL);
        db.execSQL(CREATE_CLASS);
        db.execSQL(CREATE_TRANSFER);
        db.execSQL(CREATE_SECTION);
        db.execSQL(CREATE_PROCESS_FLOW);
        db.execSQL(CREATE_MATERIAL_TYPE);
        db.execSQL(CREATE_RACK);
        db.execSQL(CREATE_MODEL);
        db.execSQL(CREATE_SECTION_TRANSFER);
        db.execSQL(CREATE_MATERIAL_CATEGORY);
        db.execSQL(CREATE_MONTH);
        db.execSQL(CREATE_YEAR);
        db.execSQL(CREATE_ACTIVE_MY);
        db.execSQL(CREATE_CODE_DEFINITION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSFER_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROCESS_FLOW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RACKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECTION_TRANSFER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVE_MY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODE_DEFINITION);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    public void dropRacks(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RACKS);
        db.execSQL(CREATE_RACK);
    }

    /*public void dropModel(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODEL);
        db.execSQL(CREATE_MODEL);
    }*/
}
