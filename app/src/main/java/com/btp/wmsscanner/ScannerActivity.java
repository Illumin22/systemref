package com.btp.wmsscanner;

//Dev Notes:
/*--------------------------------------
* --Possible feature
* > Detect if barcode is scanned in wrong model indicated in main window.
* --------------------------------------
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.btp.wmsscanner.ADAPTERS.duplicateCheckAdapter;
import com.btp.wmsscanner.API.ApiClientInterface;
import com.btp.wmsscanner.API.ApiInterface;
import com.btp.wmsscanner.DB.dbHelper;
import com.btp.wmsscanner.DB.dbHelperBarcode;
import com.btp.wmsscanner.MOD.AlertDialogModule;
import com.btp.wmsscanner.MOD.CodeDefinitionModule;
import com.btp.wmsscanner.MOD.EncryptionModule;
import com.btp.wmsscanner.ADAPTERS.scannerDataAdapter;
import com.btp.wmsscanner.MOD.GenerateDateTimeModule;
import com.btp.wmsscanner.MOD.RecyclerClickListener;
import com.btp.wmsscanner.STR.PojoDataSource;
import com.btp.wmsscanner.STR.barcodeDataCollector;
import com.btp.wmsscanner.STR.strBarcodeCollector;
import com.btp.wmsscanner.STR.strCustomDuplicateList;
import com.btp.wmsscanner.STR.strCustomScannerList;
import com.btp.wmsscanner.STR.strHandler;
import com.btp.wmsscanner.STR.strPostMaterialDetail;
import com.btp.wmsscanner.STR.strPostMaterialStock;
import com.google.android.material.card.MaterialCardView;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;

public class ScannerActivity extends AppCompatActivity {
    EncryptionModule encrypt = new EncryptionModule();
    private DecoratedBarcodeView barcodeView;
    private DecoratedBarcodeView barcodeView2;
    private ViewfinderView viewfinderView;
    private ViewfinderView viewfinderView2;
    private BeepManager beepManager;
    private String lastText;
    private CaptureManager capture;
    private List<strBarcodeCollector> dataList = new ArrayList<>();
    private List<strCustomScannerList> arrList = new ArrayList<>();
    private List<strCustomDuplicateList> arrDupList = new ArrayList<>();
    private scannerDataAdapter adapter;
    private duplicateCheckAdapter adapterDuplicate;
    MaterialCardView details;
    TextView resultText, resultText2, scanCount, totalQtyAll, empNo, matModel, checkerResult;
    TextView docNo, docDate, poNo, material, matType, matClass, transType, transSection,
            supplier, rack;
    TextView itemCount, qtyCount, itemName;
    TextView stat, stat2;
    TextView tv1Mc, tv2, tv3, tv4, tv5, tv6, tv7;
    ImageView g, s, t, g2, s2;
    EditText itemQty, itemCode;
    Button bt, codeCheck, saveProgress, continueScanning, manualInput, manualSave, itemQtySave,
            duplicateEncode, duplicateNEncode;
    ImageButton drop;
    RecyclerView listDisplay, duplicateList;
    Handler handler = new Handler();
    ViewCompat ctrlView;
    int delay = 60000, initial = 0, test;
    final dbHelper dataHelper = new dbHelper(ScannerActivity.this);
    final dbHelperBarcode dataHelperBarcode = new dbHelperBarcode(ScannerActivity.this);
    final AlertDialogModule alertMod = new AlertDialogModule(ScannerActivity.this);
    final CodeDefinitionModule codeDefinition = new CodeDefinitionModule(ScannerActivity.this);
    public String h_docNo="", h_docDate="", h_poNo="",
    h_empNo="", h_material="", h_materialCat="", h_matType="",
            h_matClass="", h_supplier="", h_transType="", h_section="", h_rack="", h_rackNo="",
            finalRack="", h_model="",
            id, localhost, h_sessionID="", h_deviceName="", h_invMth="", h_invYr="",h_remarks="";
    public String g_transactionID, g_transactionDetailsID, g_sessionID, g_secID, g_stdID;
    public String g_stockcardID, r_stockcardID;
    public String c_barcode, c_documentNo, c_scanDate;
    public Integer totalInitial = 0;
    public Integer initialCount = 0, qty = 0;
    public String barcodeFinal;
    public MediaPlayer error_beeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        permissionControl();
        initializeControl();
        tickers();
        popBarcodeChecker();
        popManualInput();
        //screenShot();

        //Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE);
        //barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.decodeContinuous(callback);
        try {
            //barcodeView2.decodeContinuous(callbackChecker);
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }

        beepManager = new BeepManager(this);
        error_beeper= MediaPlayer.create(this, R.raw.error_beep);

        dropDownDetail();
        //loadData();
        collectData();
        //alertMod.infoGeneral(h_invMth + " " + h_invYr);
        recyclerClickListeners();
        //alertMod.infoGeneral("Month: "+ h_invMth+"\n"+"Year: "+h_invYr);
    }

    //======================================================================
    /**/
    //=========================OVERRIDE CALLS===============================
    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
        //barcodeView2.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        final Dialog dialog = new Dialog(ScannerActivity.this);
        dialog.setContentView(R.layout.activity_change_qty);
        dialog.dismiss();
        dialog.setContentView(R.layout.scanner_checker);
        dialog.dismiss();
        dialog.setContentView(R.layout.activity_manual_input);
        dialog.dismiss();
        dialog.setContentView(R.layout.activity_confirmation);
        dialog.dismiss();

        barcodeView.pause();
        //barcodeView2.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {

        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed(){
        popOptions();
    }

    //======================================================================
    /**/
    //========================INITIALIZATION METHODS========================

    //--Method set for initialization of controls.
    private void initializeControl(){
        g = findViewById(R.id.stat_green);
        s = findViewById(R.id.stat_red);
        //t = findViewById(R.id.imageView2);
        stat = findViewById(R.id.scannerStat);
        resultText =findViewById(R.id.resultText);
        barcodeView = findViewById(R.id.barcode_view);
        viewfinderView = findViewById(R.id.zxing_viewfinder_view);
        drop = findViewById(R.id.detailDrop);
        details = findViewById(R.id.detailView);
        listDisplay = findViewById(R.id.rv_dataList);
        //-- textviews
        docNo = findViewById(R.id.tvs_docNo);
        docDate = findViewById(R.id.tvs_docDate);
        poNo = findViewById(R.id.tvs_poNo);
        material = findViewById(R.id.tvs_material);
        matType = findViewById(R.id.tvs_matType);
        matClass = findViewById(R.id.tvs_matClass);
        transType = findViewById(R.id.tvs_transType);
        transSection = findViewById(R.id.tvs_section);
        supplier = findViewById(R.id.tvs_vendor);
        rack = findViewById(R.id.tvs_rack);
        scanCount = findViewById(R.id.tvs_scanItem);
        totalQtyAll = findViewById(R.id.tvs_totalQty);
        empNo = findViewById(R.id.tvs_empNo);
        matModel = findViewById(R.id.tvs_model);
        codeCheck = findViewById(R.id.bt_checkQR);
        manualInput = findViewById(R.id.bt_manualInput);
        itemCount = findViewById(R.id.tvs_scanItem);
        qtyCount = findViewById(R.id.tvs_totalQty);

        tv1Mc = findViewById(R.id.textView14);
        tv2 = findViewById(R.id.textView6);
        tv3 = findViewById(R.id.textView8);
        tv4 = findViewById(R.id.textView10);
        tv5 = findViewById(R.id.textView19);
        tv6 = findViewById(R.id.textView15);
    }

    //--Permission method for required functions.
    private void permissionControl(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        Toast.makeText(ScannerActivity.this, "Camera Allowed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        Toast.makeText(ScannerActivity.this, "Camera Denied", Toast.LENGTH_LONG).show();
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    //--Fetch current setting
    private String loadSettings(){
        String methodStrHost = null;
        try{
            strHandler handler = dataHelper.checkSettings();
            id = handler.getSettingId().toString();
            localhost = handler.getSettingLocal().toString();
            try{
                methodStrHost = localhost;
            }
            catch (Exception e){
                Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
//            Toast.makeText(ScannerActivity.this, "No localhost was set!" + e.toString(),
                          //  Toast.LENGTH_LONG).show();
        }
        return methodStrHost;
    }

    //--Initialize recyclerView Click Listener in first run (Important!)
    private void recyclerClickListeners(){
        listDisplay.setItemAnimator(new DefaultItemAnimator());
        listDisplay.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        listDisplay.addOnItemTouchListener(new RecyclerClickListener(getApplicationContext(), listDisplay, new RecyclerClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                strCustomScannerList data = arrList.get(position);
                strHandler dbData = dataHelperBarcode.getBarcodeQty(data.getCode());
                String qty = String.valueOf(dbData.getBarcodeQty());
                popChangeQty(data.getCode(), qty);
                //Toast.makeText(ScannerActivity.this, data.getCode() + " QTY =:" + qty,
                //                Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    //======================================================================
    /**/
    //========================SUPPLICATION METHODS==========================

    //--Expand view control for detail card.
    private void dropDownDetail(){

        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(initial == 0){
                initial = initial + 1;
                drop.setImageResource(R.drawable.ic_arrow_down);
                    //controlLayoutParam(110);
                    controlLayoutParam(110);
                }
                else{
                    initial = initial - 1;
                    drop.setImageResource(R.drawable.ic_arrow_up);
                    //controlLayoutParam(350);
                    controlLayoutParam(250);
                }
            }
        });
    }

    //--One-stop method for card layout height detection and setter.
    private void controlLayoutParam(int height){
        ViewGroup.LayoutParams layoutParams = details.getLayoutParams();
        if (null == layoutParams)
            return;
        //set height
        layoutParams.height = height;
        //set changes to control
        details.setLayoutParams(layoutParams);
    }

    //--Take screenshot of this activity.
    private void screenShot(){
        /*  bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = ScreenshotModule.takeScRootView(t);
                t.setImageBitmap(b);
            }
        });*/
    }

    //--Method for functions that needs tickers threads.
    private void tickers(){

        //Clear result text every 1 min
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, delay);
                resultText.setText("");
            }
        }, delay);
    }

    //======================================================================
    /**/
    //========================DIALOG BOX METHODS============================
    /**/
    //--Display option dialog
    private void popOptions(){
        final Dialog dialog = new Dialog(ScannerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_confirmation);
        dialog.setCancelable(false);
        //dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        barcodeView.pause();

        /*dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                barcodeView.resume();
            }
        });*/

        saveProgress = dialog.findViewById(R.id.bt_finalSave);
        continueScanning = dialog.findViewById(R.id.bt_cancel);

        saveProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHelperBarcode.deleteTempAll();
                dialog.dismiss();
                Intent i = new Intent(ScannerActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        continueScanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeView.pause();
                barcodeView.resume();
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }

    //--Display barcode checker dialog
    private void popBarcodeChecker(){
        codeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeView.pause();
                //barcodeView2.decodeContinuous(callbackChecker);

                /*Intent i = new Intent(ScannerActivity.this, CheckerActivity.class);
                startActivity(i);*/
                final Dialog dialog = new Dialog(ScannerActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.scanner_checker);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                barcodeView2 = dialog.findViewById(R.id.barcode_view);
                g2 = dialog.findViewById(R.id.stat_green);
                s2 = dialog.findViewById(R.id.stat_red);
                viewfinderView2 = dialog.findViewById(R.id.zxing_viewfinder_view);
                resultText2 = dialog.findViewById(R.id.resultText);
                checkerResult = dialog.findViewById(R.id.tv_resultChecker);
                stat2 = dialog.findViewById(R.id.scannerStat);

                barcodeView2.decodeContinuous(callbackChecker);
                barcodeView2.resume();
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        barcodeView2.pause();
                        barcodeView.resume();
                    }
                });

            }
        });

    }

    //-- Display manual item input
    private void popManualInput(){
        manualInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeView.pause();
                final Dialog dialog = new Dialog(ScannerActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_manual_input);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                itemCode = dialog.findViewById(R.id.tb_itemCode);
                manualSave = dialog.findViewById(R.id.bt_saveChanges);

                manualSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String initialData = itemCode.getText().toString();
                        if(initialData.equals("")){
                            alertMod.emptyQrCode();
                        }
                        else{
                            manualDataEntry(initialData);
                            dialog.cancel();
                            barcodeView.pause();
                            barcodeView.resume();
                        }
                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        barcodeView.pause();
                        barcodeView.resume();
                    }
                });
            }
        });
    }

    //-- Dipslay change qty upon clicking on item
    public String oldQty;
    private void popChangeQty(String dataItem, String dataQty){
        barcodeView.pause();
        fetchTransactionID(dataItem);
        final Dialog dialog = new Dialog(ScannerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_change_qty);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        itemName = dialog.findViewById(R.id.tvs_itemCode);
        itemQty = dialog.findViewById(R.id.tb_itemCode);
        itemQtySave = dialog.findViewById(R.id.bt_saveChanges);
        itemName.setText(dataItem);
        itemQty.setText(dataQty);
        itemQtySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQTy = itemQty.getText().toString();
                //Update Qty when status is deliver
                if(q_transType.equals("402")){
                    oldQty=dataQty;
                    updateBarcodeQty(dataItem,newQTy,g_transactionID, "receive");
                }
                else{
                    oldQty=dataQty;
                    updateBarcodeQty(dataItem,newQTy,g_transactionID, "deliver");
                }
                barcodeView.pause();
                barcodeView.resume();
                dialog.cancel();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                barcodeView.resume();
            }
        });
    }

    //-- Display duplicate detection window
    private void popDuplicateCheck(String itemCode){
        barcodeView.pause();
        final Dialog dialog = new Dialog(ScannerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_duplicate_detect);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                barcodeView.resume();
            }
        });
        duplicateEncode = dialog.findViewById(R.id.bt_encode);
        duplicateNEncode = dialog.findViewById(R.id.bt_nEncode);
        duplicateList = dialog.findViewById(R.id.rv_duplicateList);
        duplicateEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                barcodeView.pause();
                barcodeView.resume();
                //String[] codeRaw;
                //codeRaw = itemCode.split("-");
                //int qty = Integer.parseInt(codeRaw[1]);
                //itemCounters(qty, "n");
                //postMaterialDetail(itemCode); //Add transfer detection
                alertMod.duplicateCodeEncoded(itemCode);
                arrDupList.clear();
            }
        });

        duplicateNEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                barcodeView.pause();
                barcodeView.resume();
                alertMod.duplicateCodeNotEncoded();
                arrDupList.clear();
            }
        });

        arrDupList.removeAll(Collections.singleton(null));
        adapterDuplicate = new duplicateCheckAdapter(arrDupList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        duplicateList.setLayoutManager(layoutManager);
        duplicateList.setItemAnimator(new DefaultItemAnimator());
        duplicateList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapterDuplicate.notifyDataSetChanged();
        duplicateList.setAdapter(adapterDuplicate);

    }

    //=======================================================================
    /**/
    //========================BARCODE FETCH METHODS==========================

    //--Zxing barcode decoder.
    private BarcodeCallback callback = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {
           /* if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }*/

            lastText = result.getText();
            Log.d("QREntry: ", result.getText());
            barcodeView.setStatusText(result.getText());
            resultText.setText("Latest Result \n" + lastText);
            beepManager.playBeepSoundAndVibrate();

            try{
                strHandler barcodeInitial = dataHelperBarcode.getBarcodeForDuplicate(lastText, h_docNo);
                barcodeFinal = barcodeInitial.getBarcode();
            }
            catch(Exception e){

            }

            String rawModel="", modelValRaw="";
            strHandler rawModelVal;
            try{
                 rawModel = lastText.substring(0,5);
                 rawModelVal = dataHelper.getModelCode(h_model);
                 modelValRaw = rawModelVal.getModelCode();
            }
            catch(Exception e){}

          /*  if(rawModel.equals(modelValRaw)){
                alertMod.infoGeneral("Barcode: " +  rawModel + "\n" + "Query: " + h_model + " " +
                                             "DB:" + " " + modelValRaw);
            }
            else{
                error_beeper.start();
                alertMod.warningGeneral("Selected model is not match to scanned lot model!");
            }*/

         if(h_transType.equals("Transfer From")){
                fetchTransferParam(lastText);
            }
            else{
                if(rawModel.equals(modelValRaw)){
                     if(h_transType.equals("Transfer From")){
                         fetchTransferParam(lastText);
                        }
                     else{
                         codeParseMethod();
                     }
                }
                else{
                    error_beeper.start();
                    alertMod.warningGeneral("Scanned lot model mismatched on selected model!");
                }
            }

            //Added preview of scanned barcode
           /* ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));*/

            g.setVisibility(View.INVISIBLE);
            s.setVisibility(View.VISIBLE);
            stat.setText(R.string.stat_wait);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    g.setVisibility(View.VISIBLE);
                    s.setVisibility(View.INVISIBLE);
                    stat.setText(R.string.stat_ready_scan);
                }
            }, 3000);
            barcodeView.pause();
            barcodeView.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    //--Zxing barcode decoder (checker module).
    private BarcodeCallback callbackChecker = new BarcodeCallback() {

        @Override
        public void barcodeResult(BarcodeResult result) {
           /* if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }*/
           lastText = result.getText();
            //resultText2.setText("Latest Result \n" + lastText);
            checkerResult.setText(lastText);
            beepManager.playBeepSoundAndVibrate();

            //Added preview of scanned barcode
           /* ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));*/

            g2.setVisibility(View.INVISIBLE);
            s2.setVisibility(View.VISIBLE);
            stat2.setText(R.string.stat_wait);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    g2.setVisibility(View.VISIBLE);
                    s2.setVisibility(View.INVISIBLE);
                    stat2.setText(R.string.stat_ready_scan);
                }
            }, 300);
            barcodeView2.pause();
            barcodeView2.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    //--Manual data input for item code
    private void manualDataEntry(String result){
        lastText = result;
        try{
            strHandler barcodeInitial = dataHelperBarcode.getBarcodeForDuplicate(lastText, h_docNo);
            barcodeFinal = barcodeInitial.getBarcode();
        }
        catch(Exception e){

        }
        //codeChop();
        codeParseMethod();

    }


    //=========================================================================
    /**/
    //==========================DATA PARSING METHODS===========================
    /**/
    String delimitStat, modelStat, qtyStat, poStat, invoiceStat, partNoStat, lotNoStat;
    private void codeParseMethod(){

        try{
            delimitStat = codeDefinition.getDelimiterStatus(h_material, h_model, h_supplier);
        modelStat = codeDefinition.getModelStatus(h_material, h_model, h_supplier);
        qtyStat = codeDefinition.getQtyStatus(h_material, h_model, h_supplier);
        poStat = codeDefinition.getPOStatus(h_material, h_model, h_supplier);
        invoiceStat = codeDefinition.getInvoiceStatus(h_material, h_model, h_supplier);
        partNoStat = codeDefinition.getPartStatus(h_material, h_model, h_supplier);
        lotNoStat = codeDefinition.getLotStatus(h_material, h_model, h_supplier);

        if(delimitStat.equals("I")){
            delimiterMode();
        }
        else{
            characterChopMode();
        }
        }
        catch(Exception e){
            alertMod.errorGeneral("Error occured in delimiter method.");
        }

    }

    //-- Chop barcode qty then run to duplicate checkers
    /*private void codeChop(){
        try {
            if(lastText.equals("")){
                alertMod.emptyQrCode();
            }
            else{
                String[] codeRaw;
                codeRaw = lastText.split("-");
                totalInitial = Integer.parseInt(codeRaw[1]);
                if(q_transType.equals("402")){
                    fetchBarcodeDataR2(lastText, barcodeFinal);
                }
                else{
                    /*String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                      h_supplier);*/
                   /* alertMod.infoGeneral("Delimiter:" + delimitStat + "\n" +
                                                 "Model:" + modelStat + "\n" +
                                                 "Qty:" + qtyStat + "\n" +
                                                 "PO No:" + poStat + "\n" +
                                                 "Invoice:" + invoiceStat + "\n" +
                                                 "Part No:" + partNoStat + "\n" +
                                                 "Lot No:" + lotNoStat + "\n");
                    //fetchBarcodeData(lastText, barcodeFinal, totalInitial);
                }
            }
        }
        catch (Exception e){
            alertMod.invalidQrCode();
        }
    }*/
    //--Parse data using delimiter character
    private void delimiterMode(){
        try {
            if(lastText.equals("")){
                alertMod.emptyQrCode();
            }
            else{
                String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                      h_supplier);
                String qtyPos = codeDefinition.getQtyPos(h_material, h_model,
                                                         h_supplier);

                Integer qtyPosFin = Integer.parseInt(qtyPos);
                String[] codeRaw;
                codeRaw = lastText.split(Pattern.quote(delimiterChar));
                totalInitial = Integer.parseInt(codeRaw[qtyPosFin]);

                if(q_transType.equals("402")){
                    fetchBarcodeDataR2(lastText, barcodeFinal);
                }
                else{
                    //alertMod.infoGeneral(totalInitial.toString());
                    fetchBarcodeData(lastText, barcodeFinal, totalInitial);

                    //alertMod.infoGeneral("MAIN: "+ lastText +"\n"+"QTY: "+ totalInitial);
                }
            }
        }
        catch (Exception e){
            alertMod.invalidQrCode();
        }
    }

    //--Parse data using character index count
    private void characterChopMode(){
        try {
            if(lastText.equals("")){
                alertMod.emptyQrCode();
            }
            else{
                String qtyBeg = codeDefinition.getQtyPos(h_material, h_model,
                                                                  h_supplier);
                String qtyEnd = codeDefinition.getQtyLen(h_material, h_model,
                                                         h_supplier);
                Integer qtyBegFin = Integer.parseInt(qtyBeg);
                Integer qtyEndFin = Integer.parseInt(qtyEnd);

               String qty = lastText.substring(qtyBegFin,qtyEndFin);
               totalInitial = Integer.parseInt(qty);

                if(q_transType.equals("402")){
                    fetchBarcodeDataR2(lastText, barcodeFinal);
                }
                else{
                    //alertMod.infoGeneral(totalInitial.toString());
                    fetchBarcodeData(lastText, barcodeFinal, totalInitial);
                }
            }
        }
        catch (Exception e){
            alertMod.invalidQrCode();
        }
    }
    //=========================================================================
    private void modelChecker(String modelName){

    }

    //--Get transaction Barcode
    private void fetchBarcodeData(String barcode, String dbBarcode, Integer qty){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(ScannerActivity.this);
        try{
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = null;
            call = clientInterface.getBarcodee(barcode, "s", "get_def");
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    //alertMod.infoGeneral(response.body().getMessage());
                    c_barcode = response.body().getMessage();
                    duplicateChecker(barcode, c_barcode, dbBarcode, qty);
                }

                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    //alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //Get unique barcode and session base on transfer and section status
    private void fetchBarcodeDataR2(String barcode, String dbBarcode){
        String host = loadSettings();
        sectionDataType(h_section);
        final dbHelper dataHelper = new dbHelper(ScannerActivity.this);
        try{
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = null;
            call = clientInterface.getReceiveDetails(barcode, q_section, q_transType, "rcvfrto",
                                                     "rcv_fr");
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    //alertMod.infoGeneral(response.body().getMessage());
                    try {
                        c_barcode = response.body().getBarcode();
                        h_sessionID = response.body().getSessionID();
                        q_matClass = response.body().getClassIDFK();
                        qn_section = response.body().getSectionIDFK();
                        qn_transType = response.body().getTransferIDFK();
                        qn_qty = response.body().getTransactionQty();
                        q_model = response.body().getModelIDFK();
                        q_material = response.body().getMaterialIDFK();
                        q_materialCat = response.body().getMatCategoryIDFK();
                        q_supplier = response.body().getSupplierIDFK();
                        qn_docNo = response.body().getDocumentNo();
                        g_transactionID = response.body().getTransactionID();
                        q_matType = response.body().getMatTypeIDFK();
                        qn_dupStat = response.body().getDuplicate_status();
                        duplicateChecker(barcode, c_barcode, dbBarcode, Integer.parseInt(qn_qty));
                    }
                    catch (Exception e){
                        alertMod.warningGeneral("This item is not yet issued in selected section.");
                    }

                }

                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //Get barcode parameters before code parsing
    private void fetchTransferParam(String barcode){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(ScannerActivity.this);
        try{
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = null;
            call = clientInterface.getBarcodeParam(barcode,"rcv_params");
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    //alertMod.infoGeneral(response.body().getMessage());
                    try {
                        h_model = response.body().getModelName();
                        h_material = response.body().getMaterialName();
                        h_supplier = response.body().getSupplierName();
                        //Call Parse Method
                        codeParseMethod();
                    }
                    catch (Exception e){
                        alertMod.warningGeneral("Can't find associating parameters of this " +
                                                        "barcode");
                    }
                }

                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    alertMod.errorGeneral(t.getMessage());
                }
            });

        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    private void infoPop(){
        alertMod.infoGeneral(c_barcode+" | "+h_sessionID+" | "+q_matClass+" | "+qn_section+" | "+qn_transType);
    }

    //-- Check barcode from local db and server for duplicate
    private void duplicateChecker(String barcode, String serverBarcode, String dbBarcode,
                                  Integer counterQty){

        if(barcode.equals(dbBarcode)){
            alertMod.duplicateQRCode("Local DB");
        }
        else {
            if(barcode.equals(serverBarcode)){
                if(q_transType.equals("402")){
                    if(barcode.equals(serverBarcode) && h_docNo.equals(qn_docNo)){
                        alertMod.duplicateQRCode("\n"+ barcode);
                    }
                    else if(barcode.equals(serverBarcode) && qn_dupStat.equals("Y")){
                        alertMod.duplicateQRCode("\n"+ barcode);
                    }
                    else{
                        itemCounters(counterQty, "n");
                        postMaterialDetailsR2(barcode, "scanner");
                    }
                }
                else{
                    getDuplicateDetails(serverBarcode);
                }
                //alertMod.duplicateQRCode("Server DB");
            }
            else{
                if(q_transType.equals("402")){
                    itemCounters(counterQty, "n");
                    postMaterialDetailsR2(barcode, "scanner");
                }
                else{
                    itemCounters(counterQty, "n");
                    if(h_transType.equals("Delivery")){
                        postMaterialDetail(barcode);
                    }
                    else{
                        postMaterialDetailUpdate(barcode);
                    }
                }
            }
        }
    }

    //--Update barcode qty
    private void updateBarcodeQty(String barcode, String qty, String dataID, String dataMethod){
        String host = loadSettings();

        try{
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = null;
            if(dataMethod.equals("deliver")){
                call = clientInterface.updateBarcodeQty(h_sessionID,
                                                        barcode, qty,
                                                        h_docNo, q_section , q_transType,"m");
            }
            else if(dataMethod.equals("receive")){
                call = clientInterface.updateBarcodeQty(h_sessionID,
                                                        barcode, qty,
                                                        h_docNo, q_section , q_transType,"f");
            }
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    tempUpdateTransHeaderQty(barcode, qty, h_sessionID);
                    tempUpdateTransDetailsQty(barcode, dataID, h_sessionID);
                    itemCounters(Integer.parseInt(qty), "r");
                    alertMod.itemQtyChanged();
                    //alertMod.infoGeneral(response.body().getMessage());
                    // c_barcode = response.body().getMessage();
                }

                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    alertMod.itemQtyNotChanged(t.getMessage());
                    //alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //--Set qty and item counters
    private void itemCounters(Integer codeQty, String method){
        DecimalFormat formatNum = new DecimalFormat("#,###.##");
        if(method.equals("n")){
            initialCount = initialCount + 1;
            qty = qty + codeQty;
        }
        else if(method.equals("r")){
            Integer initVal = Integer.parseInt(oldQty);
            Integer minusVal = 0;
            minusVal = initVal-codeQty;
            qty = qty-minusVal;
        }

        String initialQty = String.valueOf(qty);
        Float finalQty = Float.parseFloat(initialQty);
        itemCount.setText(String.valueOf(initialCount));
        qtyCount.setText(formatNum.format(finalQty));
    }

    //--Get current session quantity
    private String getCurrentSessionQty(String sessionID, String docNo){
        String currQty = null;
        strHandler totalQty = dataHelperBarcode.calculateTotalQty(sessionID, docNo);
        currQty = totalQty.getTotalQty();
        return currQty;
    }

    //--Query duplicate barcode details
    private void getDuplicateDetails(String barcode){
        String host = loadSettings();

        try{
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getBarcode(barcode);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                   if(response.body() != null){
                       List<PojoDataSource> duplicateList =  response.body();

                       for(int i = 0; i<duplicateList.size(); i++){
                           String getCode, getDocNo, getDocDate, getSection;
                           getCode = duplicateList.get(i).getBarcode();
                           getDocNo = duplicateList.get(i).getDocumentNo();
                           getDocDate = duplicateList.get(i).getDocumentDate();
                           getSection = duplicateList.get(i).getSectionName();

                           strCustomDuplicateList data = new strCustomDuplicateList(getCode,
                                                                                    getDocNo,getDocDate,
                                                                                    getSection);
                           arrDupList.add(data);
                       }
                       popDuplicateCheck(barcode);
                   }
                }

                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //=========================================================================
    /**/
    //======================DATA LOADER/COLLECTOR METHODS======================
    /**/
    //--Collect data from main activity
    private void collectData(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String typeVal = bundle.getString("TRANSFER_TYPE");
        transferDataType(typeVal);
        if(q_transType.equals("402")){
            h_docNo = bundle.getString("DOCUMENT_NO");
            h_docDate = bundle.getString("DOCUMENT_DATE");
            h_empNo = bundle.getString("EMPLOYEE_NO");
            h_transType = bundle.getString("TRANSFER_TYPE");
            h_section = bundle.getString("TRANSFER_SECTION");
            h_rack = bundle.getString("RACK");
            h_deviceName = bundle.getString("DEVICE_NAME");
            h_invMth = bundle.getString("INVENTORY_MONTH");
            h_invYr = bundle.getString("INVENTORY_YEAR");

            try {
                h_rackNo = bundle.getString("RACK_SECTION_NO");
                if (h_rackNo.equals("")){
                    h_rackNo = "";
                }
            }
            catch (Exception e){
                h_rackNo = "";
            }
            finalRack = h_rack + h_rackNo;

            h_remarks = bundle.getString("REMARKS");
            loadDataFromMainR2();
        }
        else{
            h_docNo = bundle.getString("DOCUMENT_NO");
            h_docDate = bundle.getString("DOCUMENT_DATE");
            h_poNo = bundle.getString("PO_NO");
            h_empNo = bundle.getString("EMPLOYEE_NO");
            h_materialCat = bundle.getString("MATERIAL_CATEGORY");
            h_material = bundle.getString("MATERIAL");
            h_matType = bundle.getString("MATERIAL_TYPE");
            h_matClass = bundle.getString("MATERIAL_CLASS");
            h_supplier = bundle.getString("SUPPLIER");
            h_transType = bundle.getString("TRANSFER_TYPE");
            h_section = bundle.getString("TRANSFER_SECTION");
            h_rack = bundle.getString("RACK");
            h_model = bundle.getString("MODEL");
            h_sessionID = bundle.getString("SESSION_ID");
            h_deviceName = bundle.getString("DEVICE_NAME");
            h_invMth = bundle.getString("INVENTORY_MONTH");
            h_invYr = bundle.getString("INVENTORY_YEAR");

            try {
                h_rackNo = bundle.getString("RACK_SECTION_NO");
                if (h_rackNo.equals("")){
                    h_rackNo = "";
                }
            }
            catch (Exception e){
                h_rackNo = "";
            }
            finalRack = h_rack + h_rackNo;
            loadDataFromMain();
        }


    }

    //Get transfer type ID
    private void transferDataType(String transTypeSelected){
        strHandler transType = dataHelper.getTransTypeIndex(transTypeSelected);
        q_transType = transType.getTransTypeIndex();
    }

    //Get section ID
    private void sectionDataType(String sectionSelected){
        strHandler sect = dataHelper.getSectionIndex(sectionSelected);
        q_section = sect.getTransTypeIndex();
    }

    //--Load data to list (recyclerView)
    private void loadData() {
        //dummyData();
        try{
            arrList =  dataHelperBarcode.itemData(); //note: dunno if mafefetch na agad into arraylist. Please check
            arrList.removeAll(Collections.singleton(null));
            adapter = new scannerDataAdapter(arrList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            listDisplay.setLayoutManager(layoutManager);
            adapter.notifyDataSetChanged();
            listDisplay.setAdapter(adapter);

        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
            Log.d("Load Data Error: ",e.getMessage());
        }
    }

    //--Load data from Main (Delivery)
    private void loadDataFromMain() {
        docNo.setText(h_docNo);
        docDate.setText(h_docDate);
        poNo.setText(h_poNo);
        empNo.setText(h_empNo);
        material.setText(h_material);
        matType.setText(h_matType);
        matClass.setText(h_matClass);
        transType.setText(h_transType);
        transSection.setText(h_section);
        supplier.setText(h_supplier);
        rack.setText(finalRack);
        matModel.setText(h_model);
        scanCount.setText("0");
        totalQtyAll.setText("0");
        tv1Mc.setVisibility(View.VISIBLE);
        tv2.setText(R.string.lbl_PO);
        tv3.setVisibility(View.VISIBLE);
        tv4.setVisibility(View.VISIBLE);
        tv5.setVisibility(View.VISIBLE);
        tv6.setVisibility(View.VISIBLE);
    }

    //--Load data from Main (Transfer)
    private void loadDataFromMainR2() {
        docNo.setText(h_docNo);
        docDate.setText(h_docDate);
        poNo.setText(h_remarks);
        empNo.setText(h_empNo);
        material.setVisibility(GONE);
        matType.setVisibility(GONE);
        matClass.setVisibility(GONE);
        transType.setText(h_transType);
        transSection.setText(h_section);
        supplier.setVisibility(GONE);
        rack.setText(finalRack);
        matModel.setVisibility(GONE);
        scanCount.setText("0");
        totalQtyAll.setText("0");
        tv1Mc.setVisibility(View.GONE);
        tv2.setText("Remarks: ");
        tv3.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        tv5.setVisibility(View.GONE);
        tv6.setVisibility(View.GONE);

    }

    //=========================================================================
    /**/
    //======================DATA HANDLING & UPLOADING==========================

    public String q_material="", q_materialCat="", q_matType="", q_matClass="", q_supplier="", q_transType="",
            q_section="", q_model="", q_modelOrig="",
            q_upload_status = "N", q_secID="", q_invMth="", q_invYr="", qn_transType="",
            qn_section="", qn_qty="", qn_docNo="", qn_dupStat="", q_modelMc="";

    //--Query index of each details
    private void queryKeys(String materialSelected,  String matTypeSelected,
                           String matClassSelected, String supplierSelected,
                           String transTypeSelected, String sectionSelected,
                           String modelSelected, String inventoryMth, String inventoryYr,
                           String matCatSelected){
        try{

            try{strHandler mat = dataHelper.getMaterialIndex(materialSelected);
                q_material = mat.getMaterialIndex();}
            catch (Exception e){}

            try{strHandler matClass = dataHelper.getMatClassIndex(matClassSelected);
                q_matClass = matClass.getMatClassIndex();}
            catch (Exception e){}

            try{strHandler supplier = dataHelper.getSupplierIndex(supplierSelected);
                q_supplier = supplier.getSupplierIndex();}
            catch (Exception e){}

            try{strHandler transType = dataHelper.getTransTypeIndex(transTypeSelected);
                q_transType = transType.getTransTypeIndex();}
            catch (Exception e){}

            try{strHandler section = dataHelper.getSectionIndex(sectionSelected);
                q_section = section.getSectionIndex();}
            catch (Exception e){}

            try{strHandler model = dataHelper.getModelIndex(modelSelected, q_supplier);
                q_model = model.getModelIndex();}
            catch (Exception e){}

            try{strHandler matType = dataHelper.getMatTypeIndex(matTypeSelected);
                q_matType = matType.getMatTypeIndex();}
            catch (Exception e){}

            try{strHandler model3 = dataHelper.getModelIndexMc(modelSelected, q_supplier);
                q_modelMc = model3.getModelIndex();}
            catch(Exception e){}
            try{strHandler model2 = dataHelper.getModelIndexOrig(modelSelected, q_supplier);
                q_modelOrig = model2.getModelIndex();}
            catch(Exception e){}

            try{strHandler secID = dataHelper.getSectionTransferIndex(matClassSelected,
                                                                      sectionSelected,
                                                                      transTypeSelected);
                q_secID = secID.getSecID();}
            catch(Exception e){}


            try{strHandler mthId = dataHelper.getMonthIndex(inventoryMth);
                q_invMth = mthId.getInvMth();}
            catch (Exception e){}

            try{strHandler yrID = dataHelper.getYearIndex(inventoryYr);
                q_invYr = yrID.getInvYear();}
            catch (Exception e){}

            try{strHandler matCatID = dataHelper.getMaterialCategoryIndex(matCatSelected);
                q_materialCat = matCatID.getMaterialCategoryIndex();}
            catch (Exception e){}




        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //-----------------------------------------------------
    /*Transaction Section*/
    //-----------------------------------------------------
    /**/
    //--Upload material header
    private void postMaterialDetail(String barcodeData){
        String [] codeDataRaw;
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                  h_invMth, h_invYr, h_materialCat);
        try{
            strPostMaterialDetail post = null;
            if(delimitStat.equals("I")){
                //codeDataRaw = barcodeData.split("-");
                //String codeQty = codeDataRaw[1];
                String[] codeRaw;
                String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                  h_supplier);
                Integer qtyPosFin = Integer.parseInt(codeDefinition.getQtyPos(h_material, h_model,
                                                                              h_supplier));
                Integer modelPosFin = Integer.parseInt(codeDefinition.getModelPos(h_material, h_model,
                                                                              h_supplier));
                Integer poPosFin = Integer.parseInt(codeDefinition.getPoPos(h_material, h_model,
                                                                              h_supplier));
                Integer invPosFin = Integer.parseInt(codeDefinition.getInvPos(h_material, h_model,
                                                                              h_supplier));
                Integer partPosFin = Integer.parseInt(codeDefinition.getPartPos(h_material, h_model,
                                                                              h_supplier));
                Integer lotPosFin = Integer.parseInt(codeDefinition.getLotPos(h_material, h_model,
                                                                              h_supplier));

                codeRaw = barcodeData.split(Pattern.quote(delimiterChar));
                String codeQty=null, modelVal=null, poNoVal=null, invNoVal=null, partNoVal=null,
                        lotNoVal=null;
                try{
                    codeQty= codeRaw[qtyPosFin];
                    modelVal=codeRaw[modelPosFin];
                    poNoVal=codeRaw[poPosFin];
                    invNoVal=codeRaw[invPosFin];
                    partNoVal=codeRaw[partPosFin];
                    lotNoVal=codeRaw[lotPosFin];
                }
                catch(Exception e){
                }

                 post = new strPostMaterialDetail(h_sessionID,  barcodeData,
                                                                       q_model, q_material,
                                                                       q_materialCat,  codeQty,
                                                                       //change codeQty to totalInitial
                                                                       dateTime, h_docNo,  h_docDate,
                                                                       q_supplier, h_poNo,
                                                  q_matClass, q_matType, modelVal, poNoVal,
                                                  invNoVal, partNoVal, lotNoVal);
            }
            else{

                Integer qtyBegFin = Integer.parseInt(codeDefinition.getQtyPos(h_material, h_model,
                                                                              h_supplier));
                Integer qtyEndFin = Integer.parseInt(codeDefinition.getQtyLen(h_material, h_model,
                                                                              h_supplier));

                Integer modelBegFin = Integer.parseInt(codeDefinition.getModelPos(h_material, h_model,
                                                                              h_supplier));
                Integer modelEndFin = Integer.parseInt(codeDefinition.getModelLen(h_material, h_model,
                                                                              h_supplier));

                Integer poBegFin = Integer.parseInt(codeDefinition.getPoPos(h_material, h_model,
                                                                              h_supplier));
                Integer poEndFin = Integer.parseInt(codeDefinition.getPoLen(h_material, h_model,
                                                                              h_supplier));

                Integer invBegFin = Integer.parseInt(codeDefinition.getInvPos(h_material, h_model,
                                                                              h_supplier));
                Integer invEndFin = Integer.parseInt(codeDefinition.getInvLen(h_material, h_model,
                                                                              h_supplier));

                Integer partBegFin = Integer.parseInt(codeDefinition.getPartPos(h_material, h_model,
                                                                              h_supplier));
                Integer partEndFin = Integer.parseInt(codeDefinition.getPartLen(h_material, h_model,
                                                                              h_supplier));

                Integer lotBegFin = Integer.parseInt(codeDefinition.getLotPos(h_material, h_model,
                                                                              h_supplier));
                Integer lotEndFin = Integer.parseInt(codeDefinition.getLotLen(h_material, h_model,
                                                                              h_supplier));

                String codeQty=null, modelVal=null, poNoVal=null, invNoVal=null, partNoVal=null,
                        lotNoVal=null;
                try{
                    codeQty = barcodeData.substring(qtyBegFin,qtyEndFin);
                    modelVal = barcodeData.substring(modelBegFin,modelEndFin);
                    poNoVal = barcodeData.substring(poBegFin,poEndFin);
                    invNoVal = barcodeData.substring(invBegFin,invEndFin);
                    partNoVal = barcodeData.substring(partBegFin,partEndFin);
                    lotNoVal = barcodeData.substring(lotBegFin,lotEndFin);
                }
                catch (Exception e){
                }

                 post = new strPostMaterialDetail(h_sessionID,  barcodeData,
                                                                       q_model, q_material,
                                                                       q_materialCat,  codeQty,
                                                                       //change codeQty to totalInitial
                                                                       dateTime, h_docNo,  h_docDate,
                                                                       q_supplier, h_poNo,
                                                  q_matClass , q_matType, modelVal, poNoVal,
                                                  invNoVal, partNoVal, lotNoVal);
            }
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.sendDetailHeader(post);

            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    strPostMaterialDetail postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    g_transactionID = response.body().getTransactionID();
                    try {
                        r_stockcardID = response.body().getStockTransactionID();
                    }catch (Exception e){

                    }
                    postMaterialDetails(barcodeData);
                    tempMaterialDetails(barcodeData, dateTime);//call temporary detail database
                    // method for stock calculation and later upload when failed to connect in network.

                    try {
                        if(r_stockcardID.equals("")){
                            postStockHeader();
                        }else {
                            postUpdateStockHeader();
                        }
                    }catch (Exception e){
                        postStockHeader();
                    }

                    //Toast.makeText(ScannerActivity.this, String.valueOf(postResponse), Toast.LENGTH_SHORT).show();
                    //alertMod.errorGeneral(String.valueOf(postResponse));
                }

                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    q_upload_status = "N";
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                    tempMaterialDetails(barcodeData, dateTime);
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
            //Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //--Upload material details
    private void postMaterialDetails(String barcodeData){
        String [] codeDataRaw;
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                  h_invMth, h_invYr, h_materialCat);
        try{
            strPostMaterialDetail post = null;
            if(delimitStat.equals("I")){
                //codeDataRaw = barcodeData.split("-");
                //String codeQty = codeDataRaw[1];
                String[] codeRaw;
                String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                  h_supplier);
                String qtyPos = codeDefinition.getQtyPos(h_material, h_model,
                                                         h_supplier);

                Integer qtyPosFin = Integer.parseInt(qtyPos);

                codeRaw = barcodeData.split(Pattern.quote(delimiterChar));
                String codeQty = codeRaw[qtyPosFin];

                post = new strPostMaterialDetail(g_transactionID, h_sessionID,
                                                 q_section, dateTime, codeQty,
                                                 "0", "0", q_transType,
                                                 finalRack, h_empNo,
                                                 h_deviceName, q_invMth,
                                                 q_invYr, dateTime,
                                                 h_empNo, "", "mobile");
            }
            else{
                String qtyBeg = codeDefinition.getQtyPos(h_material, h_model,
                                                         h_supplier);
                String qtyEnd = codeDefinition.getQtyLen(h_material, h_model,
                                                         h_supplier);
                Integer qtyBegFin = Integer.parseInt(qtyBeg);
                Integer qtyEndFin = Integer.parseInt(qtyEnd);

                String codeQty = barcodeData.substring(qtyBegFin,qtyEndFin);
                post = new strPostMaterialDetail(g_transactionID, h_sessionID,
                                                 q_section, dateTime, codeQty,
                                                 "0", "0", q_transType,
                                                 finalRack, h_empNo,
                                                 h_deviceName, q_invMth,
                                                 q_invYr, dateTime,
                                                 h_empNo, "", "mobile");
            }


            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.sendDataDetails(post);
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    strPostMaterialDetail postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    try {
                        g_transactionDetailsID = response.body().getTransacDetailsID();
                        postMaterialMarkers(g_transactionDetailsID);
                        tempMaterialDetailsExtend(barcodeData, dateTime);

                    }catch(Exception e){
                        alertMod.errorGeneral("Can't get transaction ID");
                        Log.d("postMaterialDetails",e.getMessage());
                    }

                    //tempMaterialDetailsExtend(barcodeData, dateTime); //call temporary detail database method for stock calculation and later upload when failed to connect in network.
                    //Toast.makeText(ScannerActivity.this, String.valueOf(postResponse), Toast.LENGTH_SHORT).show();
                    //alertMod.errorGeneral(String.valueOf(postResponse));
                }
                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    q_upload_status = "N";
                    tempMaterialDetailsExtend(barcodeData, dateTime);
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //--Upload material details R2
    private void postMaterialDetailsR2(String barcodeData, String dataMethod){
        String [] codeDataRaw;
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        /*queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                  h_invMth, h_invYr, h_materialCat);*/
        strHandler section = dataHelper.getSectionIndex(h_section);
        q_section = section.getSectionIndex();
        strHandler mthId = dataHelper.getMonthIndex(h_invMth);
        q_invMth = mthId.getInvMth();
        strHandler yrID = dataHelper.getYearIndex(h_invYr);
        q_invYr = yrID.getInvYear();
        strPostMaterialDetail post = null;
        try{
            /*codeDataRaw = barcodeData.split("-");
            String codeQty = codeDataRaw[1];*/

            if(delimitStat.equals("I")){
                //codeDataRaw = barcodeData.split("-");
                //String codeQty = codeDataRaw[1];
                String[] codeRaw;
                String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                  h_supplier);
                String qtyPos = codeDefinition.getQtyPos(h_material, h_model,
                                                         h_supplier);

                Integer qtyPosFin = Integer.parseInt(qtyPos);

                codeRaw = barcodeData.split(Pattern.quote(delimiterChar));
                String codeQty = codeRaw[qtyPosFin];
                if(dataMethod.equals("scanner")){
                    post = new strPostMaterialDetail(g_transactionID, h_sessionID,
                                                     q_section, dateTime, qn_qty,
                                                     "0", "0", q_transType,
                                                     finalRack, h_empNo,
                                                     h_deviceName, q_invMth,
                                                     q_invYr, dateTime,
                                                     h_empNo, h_docNo, "mobile");
                }
            }
            else{
                String qtyBeg = codeDefinition.getQtyPos(h_material, h_model,
                                                         h_supplier);
                String qtyEnd = codeDefinition.getQtyLen(h_material, h_model,
                                                         h_supplier);
                Integer qtyBegFin = Integer.parseInt(qtyBeg);
                Integer qtyEndFin = Integer.parseInt(qtyEnd);

                String codeQty = barcodeData.substring(qtyBegFin,qtyEndFin);
                if(dataMethod.equals("scanner")){
                    post = new strPostMaterialDetail(g_transactionID, h_sessionID,
                                                     q_section, dateTime, qn_qty,
                                                     "0", "0", q_transType,
                                                     finalRack, h_empNo,
                                                     h_deviceName, q_invMth,
                                                     q_invYr, dateTime,
                                                     h_empNo, h_docNo, "mobile");
                }
            }


            /*else if (dataMethod.equals("update")){
                post = new strPostMaterialDetail(g_transactionID, h_sessionID,
                                                 qn_section, dateTime, qn_qty,
                                                 "0", "0", q_transType,
                                                 finalRack, h_empNo,
                                                 h_deviceName, q_invMth,
                                                 q_invYr, dateTime,
                                                 h_empNo);
            }*/

            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.sendDataDetails(post);
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    strPostMaterialDetail postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    try {
                        g_transactionDetailsID = response.body().getTransacDetailsID();
                        postMaterialMarkers(g_transactionDetailsID);
                        tempMaterialDetailsExtend(barcodeData, dateTime);
                        tempMaterialDetails(barcodeData, dateTime);
                        if(q_transType.equals("402")){
                            try {
                                r_stockcardID = response.body().getStockTransactionID();
                            }catch (Exception e){

                            }
                            try {
                                if(r_stockcardID.equals("")){
                                    postStockHeader();
                                }else {
                                    postUpdateStockHeader();
                                }
                            }catch (Exception e){
                                postStockHeader();
                            }
                        }
                    }catch(Exception e){
                        alertMod.errorGeneral("Can't get transaction ID");
                        Log.d("postMaterialDetails",e.getMessage());
                    }

                    //tempMaterialDetailsExtend(barcodeData, dateTime); //call temporary detail database method for stock calculation and later upload when failed to connect in network.
                    //Toast.makeText(ScannerActivity.this, String.valueOf(postResponse), Toast.LENGTH_SHORT).show();
                    //alertMod.errorGeneral(String.valueOf(postResponse));
                }
                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    q_upload_status = "N";
                    tempMaterialDetailsExtend(barcodeData, dateTime);
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //--Upload material markers
    private void postMaterialMarkers(String transacID){
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        if(q_transType.equals("402")){
            try{strHandler secID = dataHelper.getSectionTransferIndexDirect(q_matClass,
                                                                            q_section,
                                                                            q_transType);
                q_secID = secID.getSecID();}
            catch (Exception e){}

        }
        else{
            queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                      h_invMth, h_invYr, h_materialCat);
        }

        try{
            strPostMaterialDetail post = new strPostMaterialDetail(transacID, q_secID, h_docNo,"Y",
                                                                   dateTime, h_remarks /*change
                                                                   listener
             */);
            ApiClientInterface clientInterface =
                    ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.sendDataMarkers(post);
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call,
                                       Response<strPostMaterialDetail> response) {
                    strPostMaterialDetail postResponse = response.body(); //post data to server
                    //g_stdID = response.body().getStdID();
                    //postUpdateMaterialStd(transacID);

                    //call temporary detail database method for stock calculation and later upload when failed to connect in network.
                    //Toast.makeText(ScannerActivity.this, String.valueOf(postResponse), Toast.LENGTH_SHORT).show();
                    //alertMod.errorGeneral(String.valueOf(postResponse));
                }

                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    q_upload_status = "N";
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //--Upload update on material details
    private void postMaterialDetailUpdate(String barcodeData){
        String [] codeDataRaw;
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                  h_invMth, h_invYr, h_materialCat);

        try{
            strPostMaterialDetail post = null;
            if(delimitStat.equals("I")){
                //codeDataRaw = barcodeData.split("-");
                //String codeQty = codeDataRaw[1];
                String[] codeRaw;
                String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                  h_supplier);
                String qtyPos = codeDefinition.getQtyPos(h_material, h_model,
                                                         h_supplier);

                Integer qtyPosFin = Integer.parseInt(qtyPos);

                codeRaw = barcodeData.split(Pattern.quote(delimiterChar));
                String codeQty = codeRaw[qtyPosFin];

                post = new strPostMaterialDetail(g_transactionID, h_sessionID,
                                                 q_section, dateTime, codeQty,
                                                 "0", "0", q_transType,
                                                 finalRack, h_empNo,
                                                 h_deviceName, "","", dateTime,
                                                 h_empNo,"", "mobile");
            }
            else{
                String qtyBeg = codeDefinition.getQtyPos(h_material, h_model,
                                                         h_supplier);
                String qtyEnd = codeDefinition.getQtyLen(h_material, h_model,
                                                         h_supplier);
                Integer qtyBegFin = Integer.parseInt(qtyBeg);
                Integer qtyEndFin = Integer.parseInt(qtyEnd);

                String codeQty = barcodeData.substring(qtyBegFin,qtyEndFin);
                post = new strPostMaterialDetail(g_transactionID, h_sessionID,
                                                 q_section, dateTime, codeQty,
                                                 "0", "0", q_transType,
                                                 finalRack, h_empNo,
                                                 h_deviceName, "","", dateTime,
                                                 h_empNo,"", "mobile");
            }



            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.sendDataUpdateDetails(post);
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    strPostMaterialDetail postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    g_transactionID = response.body().getTransactionID();
                    postMaterialDetails(barcodeData);
                    tempMaterialDetails(barcodeData, dateTime); //call temporary detail database method for stock calculation and later upload when failed to connect in network.
                    //Toast.makeText(ScannerActivity.this, String.valueOf(postResponse), Toast.LENGTH_SHORT).show();
                    //alertMod.errorGeneral(String.valueOf(postResponse));
                }
                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    q_upload_status = "N";
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                    tempMaterialDetails(barcodeData, dateTime);
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //-----------------------------------------------------
    /*Stockcard Section*/
    //-----------------------------------------------------
    /**/
    //--Upload stockcard header
    private void postStockHeader(){
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        if(q_transType.equals("402")){
            try{strHandler secID = dataHelper.getSectionTransferIndexDirect(q_matClass,
                                                                            q_section,
                                                                            q_transType);
                q_secID = secID.getSecID();}
            catch (Exception e){}

            try{strHandler model = dataHelper.getModelIndex(h_model, q_supplier);
                q_modelMc = model.getModelIndex();}
            catch(Exception e){}

            if(q_section.equals("306")){
                q_matType = "12"; // Temporary value for plating section
            }
        }
        else{
            queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                      h_invMth, h_invYr, h_materialCat);
        }
        try{
            strPostMaterialStock post=null;
            if(h_transType.equals("Transfer From")){
                post = new strPostMaterialStock(h_sessionID, h_docDate, h_docNo,
                                                q_modelMc, q_material,
                                                q_materialCat, q_matType, q_supplier,
                                                q_secID, "Y", q_invMth, q_invYr
                        , dateTime, h_empNo, "");
            }
            else{
                post = new strPostMaterialStock(h_sessionID, h_docDate, h_docNo,
                                                q_model, q_material,
                                                q_materialCat, q_matType, q_supplier,
                                                q_secID, "Y", q_invMth, q_invYr
                        , dateTime, h_empNo, "");
            }


            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialStock> call = clientInterface.sendDataStockCardHead(post);
            call.enqueue(new Callback<strPostMaterialStock>() {
                @Override
                public void onResponse(Call<strPostMaterialStock> call, Response<strPostMaterialStock> response) {
                    strPostMaterialStock postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    g_stockcardID = response.body().getStockTransactionID();
                    postStockDetails();
                    tempStockHeader(dateTime);
                }
                @Override
                public void onFailure(Call<strPostMaterialStock> call, Throwable t) {
                    q_upload_status = "N";
                    tempStockHeader(dateTime);
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //--Upload stockcard details
    private void postStockDetails(){
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        String currQty = getCurrentSessionQty(h_sessionID, h_docNo);
        /*queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                  h_invMth, h_invYr, h_materialCat);*/
        try{
            strPostMaterialStock post = new strPostMaterialStock(g_stockcardID, h_sessionID,
                                                                 h_docNo,"-", currQty, "0", "0",
                                                                 "0");
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialStock> call = clientInterface.sendDataStockCardDetail(post);
            call.enqueue(new Callback<strPostMaterialStock>() {
                @Override
                public void onResponse(Call<strPostMaterialStock> call, Response<strPostMaterialStock> response) {
                    strPostMaterialStock postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    //g_stockcardID = response.body().getStockTransactionID();
                }
                @Override
                public void onFailure(Call<strPostMaterialStock> call, Throwable t) {
                    q_upload_status = "N";
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //--Upload stockcard header update
    private void postUpdateStockHeader(){
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                  h_invMth, h_invYr, h_materialCat);
        try{
            strPostMaterialStock post = new strPostMaterialStock(g_stockcardID, h_sessionID,
                                                                 h_docDate, h_docNo,
                                                                 q_model, q_material,
                                                                 q_materialCat, q_supplier,
                                                                 q_secID, "Y", q_invMth, q_invMth
                    , dateTime, h_empNo);
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialStock> call = clientInterface.sendDataStockCardHeadUpdate(post);
            call.enqueue(new Callback<strPostMaterialStock>() {
                @Override
                public void onResponse(Call<strPostMaterialStock> call, Response<strPostMaterialStock> response) {
                    strPostMaterialStock postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    try{
                        g_stockcardID = response.body().getStockTransactionID();
                    }
                    catch (Exception e){

                    }

                    postUpdateStockDetails();
                }
                @Override
                public void onFailure(Call<strPostMaterialStock> call, Throwable t) {
                    q_upload_status = "N";
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //--Upload stockcard details update
    private void postUpdateStockDetails(){
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();
        String currQty = getCurrentSessionQty(h_sessionID, h_docNo);
        queryKeys(h_material, h_matType, h_matClass, h_supplier, h_transType, h_section, h_model,
                  h_invMth, h_invYr, h_materialCat);
        try{
            strPostMaterialStock post = new strPostMaterialStock(g_stockcardID, h_sessionID,
                                                                 h_docNo,"-", currQty, "0", "0",
                                                                 "0");
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialStock> call = clientInterface.sendDataStockCardDetailUpdate(post);
            call.enqueue(new Callback<strPostMaterialStock>() {
                @Override
                public void onResponse(Call<strPostMaterialStock> call, Response<strPostMaterialStock> response) {
                    strPostMaterialStock postResponse = response.body(); //post data to server
                    q_upload_status = "Y";
                    //g_stockcardID = response.body().getStockTransactionID();
                }
                @Override
                public void onFailure(Call<strPostMaterialStock> call, Throwable t) {
                    q_upload_status = "N";
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //=========================================================================
    /**/
    //======================LOCAL DB METHODS===================================

    //--Save material header to database(temp)
    private void tempMaterialDetails(String barcodeData, String dateTime){
        String [] codeDataRaw;
        String codeQtyFin="";
        barcodeDataCollector itemDataCollector = new barcodeDataCollector();
        if(delimitStat.equals("I")){
            //codeDataRaw = barcodeData.split("-");
            //String codeQty = codeDataRaw[1];
            String[] codeRaw;
            String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                              h_supplier);
            String qtyPos = codeDefinition.getQtyPos(h_material, h_model,
                                                     h_supplier);

            Integer qtyPosFin = Integer.parseInt(qtyPos);

            codeRaw = barcodeData.split(Pattern.quote(delimiterChar));
            codeQtyFin = codeRaw[qtyPosFin];

        }
        else{
            String qtyBeg = codeDefinition.getQtyPos(h_material, h_model,
                                                     h_supplier);
            String qtyEnd = codeDefinition.getQtyLen(h_material, h_model,
                                                     h_supplier);
            Integer qtyBegFin = Integer.parseInt(qtyBeg);
            Integer qtyEndFin = Integer.parseInt(qtyEnd);
            codeQtyFin = barcodeData.substring(qtyBegFin,qtyEndFin);

        }
        //Detect receive/deliver for qty
        String codeQty="";
        if(q_transType.equals("402")){
            codeQty = qn_qty;
        }
        else{
            codeQty = codeQtyFin;
        }

        try{
            itemDataCollector.dataDocumentNo = h_docNo;
            itemDataCollector.dataDocumentDate = h_docDate;
            itemDataCollector.dataBarcode = barcodeData;
            itemDataCollector.dataBarcodeQty = codeQty; //Note: DB Data type is integer.
            itemDataCollector.dataScanDate = dateTime;
            itemDataCollector.dataPoNo = h_poNo;
            itemDataCollector.dataMaterial = q_material;
            itemDataCollector.dataMaterialCat = q_materialCat;
            itemDataCollector.dataMatType = q_matType;
            itemDataCollector.dataMatClass = q_matClass;
            itemDataCollector.dataSupplier = q_supplier;
            itemDataCollector.dataTransType = q_transType;
            itemDataCollector.dataSection = q_section;
            itemDataCollector.dataModel = q_model;
            itemDataCollector.dataSessionID = h_sessionID;
            itemDataCollector.dataUploadStatus = q_upload_status;

            boolean createItemDetail = new dbHelperBarcode(ScannerActivity.this).insertData(itemDataCollector);
            loadData();
        }

        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }

    }

    //--Save material details to database(temp)
    private void tempMaterialDetailsExtend(String barcodeData, String dateTime){
        String [] codeDataRaw;
        String codeQtyFin="";
        barcodeDataCollector itemDataCollector = new barcodeDataCollector();
        if(delimitStat.equals("I")){
            //codeDataRaw = barcodeData.split("-");
            //String codeQty = codeDataRaw[1];
            String[] codeRaw;
            String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                              h_supplier);
            String qtyPos = codeDefinition.getQtyPos(h_material, h_model,
                                                     h_supplier);

            Integer qtyPosFin = Integer.parseInt(qtyPos);

            codeRaw = barcodeData.split(Pattern.quote(delimiterChar));
            codeQtyFin = codeRaw[qtyPosFin];

        }
        else{
            String qtyBeg = codeDefinition.getQtyPos(h_material, h_model,
                                                     h_supplier);
            String qtyEnd = codeDefinition.getQtyLen(h_material, h_model,
                                                     h_supplier);
            Integer qtyBegFin = Integer.parseInt(qtyBeg);
            Integer qtyEndFin = Integer.parseInt(qtyEnd);
            codeQtyFin = barcodeData.substring(qtyBegFin,qtyEndFin);

        }
        //Detect receive/deliver for qty
        String codeQty="";
        if(q_transType.equals("402")){
            codeQty = qn_qty;
        }
        else{
            codeQty = codeQtyFin;
        }

        try{
            itemDataCollector.dataTransactionIDFK = g_transactionID;
            itemDataCollector.dataSessionIDFK = h_sessionID;
            itemDataCollector.dataSectionIDFK = q_section;
            itemDataCollector.dataTransactionDate = dateTime;
            itemDataCollector.dataTransactionQty = codeQty;
            itemDataCollector.dataMovementQty = "0";
            itemDataCollector.dataNgQty = "0";
            itemDataCollector.dataTransferIDFK = q_transType;
            itemDataCollector.dataStIDFK = ""; // For changing after query in markers
            itemDataCollector.dataRack = h_rack;
            itemDataCollector.dataEmpNo = h_empNo;
            itemDataCollector.dataDevice = h_deviceName;
            itemDataCollector.dataInvMth = q_invMth;
            itemDataCollector.dataInvYr = q_invYr;
            itemDataCollector.dataAddedDate = dateTime;
            itemDataCollector.dataAddedBy = h_empNo;

            boolean createItemDetail = new dbHelperBarcode(ScannerActivity.this).insertDetailsData(itemDataCollector);
            //loadData();
        }

        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }

    }

    //--Save material details to database(temp)
    private void tempStockHeader(String dateTime){
        barcodeDataCollector itemDataCollector = new barcodeDataCollector();

        try{
            itemDataCollector.dataSessionID = h_sessionID;
            itemDataCollector.dataDocumentDate = h_docDate;
            itemDataCollector.dataDocumentNo = h_docNo ;
            itemDataCollector.dataModel = q_model;
            itemDataCollector.dataMaterial = q_material;
            itemDataCollector.dataMaterialCat = q_materialCat;
            itemDataCollector.dataSupplier = q_supplier;
            itemDataCollector.dataMSecIDFK = q_secID;
            itemDataCollector.dataIQC = "Y";
            itemDataCollector.dataInvMth = q_invMth;
            itemDataCollector.dataInvYr = q_invYr;
            itemDataCollector.dataLegendIDFK = "0";
            itemDataCollector.dataAddedDate = dateTime;
            itemDataCollector.dataAddedBy = h_empNo;
            itemDataCollector.dataUploadStatus = q_upload_status;

            boolean createItemDetail = new dbHelperBarcode(ScannerActivity.this).insertStockHeaderData(itemDataCollector);
            //loadData();
        }

        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }

    }

    //--Update material header qty to database(temp)
    private void tempUpdateTransHeaderQty(String code, String qty, String session){
        try{
            boolean createItemDetail =
                    new dbHelperBarcode(ScannerActivity.this).updateTransHeaderQty(qty, code, session);
            adapter.notifyDataSetChanged();
            loadData();
        }

        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }

    }

    //--Update material details qty to database(temp)
    private void tempUpdateTransDetailsQty(String id, String qty, String session){
        try{
            boolean createItemDetail =
                    new dbHelperBarcode(ScannerActivity.this).updateTransDetailsQty(qty, id, session);
            //loadData();
        }

        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }

    }

    //--Update section transfer index in material detail
   /* private void postUpdateMaterialStd(String transacID){
        String host = loadSettings();
        String dateTime = GenerateDateTimeModule.getCurrentDateTime();

        try{
            strPostMaterialDetail post = new strPostMaterialDetail(transacID);
            ApiClientInterface clientInterface =
                    ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.sendDataUpdateDetails(post);
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call,
                                       Response<strPostMaterialDetail> response) {
                    strPostMaterialDetail postResponse = response.body(); //post data to server

                    //call temporary detail database method for stock calculation and later upload when failed to connect in network.
                    //Toast.makeText(ScannerActivity.this, String.valueOf(postResponse), Toast.LENGTH_SHORT).show();
                    //alertMod.errorGeneral(String.valueOf(postResponse));
                }
                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    q_upload_status = "N";
                    //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //alertMod.errorGeneral(t.getMessage());
                    Log.d("Failure_in_post: ", t.getMessage());
                }
            });
            //fetchTransactionID(barcodeData);
        }
        catch (Exception e){
            Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }*/
    //--Get transaction ID Only
    private void fetchTransactionID(String barcode){
        String host = loadSettings();
        String sessionVal = h_sessionID;
        final dbHelper dataHelper = new dbHelper(ScannerActivity.this);
        try{
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.getTransactionID(sessionVal, barcode, h_docNo);
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    //alertMod.infoGeneral(response.body().getMessage());
                    g_transactionID = response.body().getMessage();
                }

                @Override
                public void onFailure(Call<strPostMaterialDetail> call, Throwable t) {
                    //alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }

    }

    //=================================================================================

    //--Dummy Data(Don't forget to delete)
    private void dummyData(){
        strBarcodeCollector data = new strBarcodeCollector("TYJ3P2010U00225_B-265","265");
        dataList.add(data);

         data = new strBarcodeCollector("TYJ3P2010F00153_B-327","327");
        dataList.add(data);

         data = new strBarcodeCollector("TYJ3P2010AB0148_B-333","333");
        dataList.add(data);

         data = new strBarcodeCollector("TYJ3P2010E00126_A-309","309");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010U00204_B-220","220");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
       dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010F00153_B-327","327");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AB0148_B-333","333");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010E00126_A-309","309");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010U00204_B-220","220");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

        data = new strBarcodeCollector("TYJ3P2010AD0143_B-305","305");
        dataList.add(data);

    }
}
