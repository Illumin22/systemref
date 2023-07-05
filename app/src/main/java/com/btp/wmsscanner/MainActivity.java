package com.btp.wmsscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.btp.wmsscanner.API.ApiClientInterface;
import com.btp.wmsscanner.API.ApiInterface;
import com.btp.wmsscanner.DB.dbHelper;
import com.btp.wmsscanner.MOD.AlertDialogModule;
import com.btp.wmsscanner.MOD.CodeDefinitionModule;
import com.btp.wmsscanner.MOD.GenerateSessionIDModule;
import com.btp.wmsscanner.MOD.GetDeviceNameModule;
import com.btp.wmsscanner.STR.PojoDataSource;
import com.btp.wmsscanner.STR.dataCollector;
import com.btp.wmsscanner.STR.strHandler;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DecoratedBarcodeView barcodeView;
    private DecoratedBarcodeView barcodeView2;
    private ViewfinderView viewfinderView;
    private ViewfinderView viewfinderView2;
    private CompoundBarcodeView initView;
    private BeepManager beepManager;
    private String lastText;
    TextView strip, fetchStat, matType, iMth, iYr, scannerTitle;
    TextView mainTitle, tv1Mc, tv2, tv3, tv4, tv5, tv6, tv7,  stat, stat2, resultText,
    resultText2, checkerResult;
    ImageButton codeCheck, poScan, invScan;
    ImageView g, s, t, g2, s2;
    ProgressBar fetchLoadStat;
    EditText docDate, getLocalHost, docno, empNo, rack, poNo, reMarks;
    FloatingActionButton startScan;
    BottomAppBar appBar;
    Button commitSetting;
    ArrayList<String> arrList;
    ArrayAdapter<String> listAdapter;
    Spinner supplier, material, materialClass, transferType, section, sMatType, sRack, sModel,
            sMatCat, invMth, invYr;
    SwipeRefreshLayout refreshLayout;
    dataCollector dtCollector =  new dataCollector();
    final Calendar docCalendar = Calendar.getInstance();
    final dbHelper dataHelper = new dbHelper(MainActivity.this);
    final AlertDialogModule alertMod = new AlertDialogModule(MainActivity.this);
    final CodeDefinitionModule codeDefinition = new CodeDefinitionModule(MainActivity.this);
    Dialog dialog1 = null;
    Dialog dialog2 = null;
    int checker = 0;
    Handler handler = new Handler();
    int delay = 1000;

    private void activeMonthCountChecker(String checker){
        if("2".equals(checker)){
            iMth.setVisibility(View.VISIBLE);
            iYr.setVisibility(View.VISIBLE);
            invMth.setVisibility(View.VISIBLE);
            invYr.setVisibility(View.VISIBLE);
        }
        else{
            iMth.setVisibility(View.GONE);
            iYr.setVisibility(View.GONE);
            invMth.setVisibility(View.GONE);
            invYr.setVisibility(View.GONE);
        }
    }

   //Method calls for getting data from server
    public void dataFetchers(){
        try{
            fetchInfoBoard();
            fetchMaterialData();
            fetchSupplierData();
            fetchClassTypeData();
            fetchSectionData();
            fetchTransferTypeData();
            fetchMaterialTypeData();
            fetchRackData();
            fetchModelData();
            fetchSectionTransferData();
            fetchMaterialCategoryData();
            fetchMonthData();
            fetchYearData();
            fetchActiveMonthYearData();
            fetchActiveMonthNumberData();
            fetchCodeDefinitionData();
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "No localhost set! " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit BTP-WMS Mobile Scanner");
        builder.setMessage("Are you sure you want to quit?")
                .setCancelable(false)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })

                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }

    //-- Method set for initialization of controls.
    private void initializeControl(){
        strip = findViewById(R.id.lbl_infoStrip);
        startScan = findViewById(R.id.startScanner);
        appBar = findViewById(R.id.bAB);
        fetchStat = findViewById(R.id.tv_fetchStatus);
        fetchLoadStat = findViewById(R.id.pb_fetchStatus);
        supplier = findViewById(R.id.sp_supplier);
        material = findViewById(R.id.sp_material);
        transferType = findViewById(R.id.sp_transferType);
        section = findViewById(R.id.sp_section);
        matType = findViewById(R.id.tv_matType);
        sMatType = findViewById(R.id.sp_matType);
        sRack = findViewById(R.id.sp_rack);
        docno = findViewById(R.id.tb_docNo);
        poNo = findViewById(R.id.tb_PONo);
        empNo = findViewById(R.id.tb_empNo);
        rack = findViewById(R.id.tb_rackNo);
        sModel = findViewById(R.id.sp_model);
        sMatCat =findViewById(R.id.sp_materialCat);
        refreshLayout = findViewById(R.id.swiperefresh);
        iMth = findViewById(R.id.textView12);
        iYr = findViewById(R.id.textView17);
        invMth = findViewById(R.id.sp_month);
        invYr = findViewById(R.id.sp_year);

        mainTitle = findViewById(R.id.mainTitle);
        tv1Mc =findViewById(R.id.tvMC);
        tv2 =findViewById(R.id.textView5);
        tv3 =findViewById(R.id.tv1);
        tv4 =findViewById(R.id.tvPo);
        tv5 =findViewById(R.id.tvClass);
        tv6 =findViewById(R.id.tvModel);
        tv7 =findViewById(R.id.tvRem);
        reMarks=findViewById(R.id.tb_remarks);

        tv7.setVisibility(View.GONE);
        reMarks.setVisibility(View.GONE);

        mainCard=findViewById(R.id.materialCardView2);

        codeCheck = findViewById(R.id.bt_scanToIn);

        poScan = findViewById(R.id.bt_scanToPO);
        invScan=findViewById(R.id.bt_scanToDocNo);
    }

    //--Permission method for required functions.
    private void permissionControl(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        Toast.makeText(MainActivity.this, "Network Allowed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        Toast.makeText(MainActivity.this, "Network Denied", Toast.LENGTH_LONG).show();
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

    public String id, localhost, activeNumber, activeEmpNo, q_transType;
    MaterialCardView mainCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Initializes controls
        initializeControl();
        permissionControl();
        permissionControlCam();
        beepManager = new BeepManager(this);
        strip.setHorizontallyScrolling(true);
        strip.setSelected(true);

        //--Control Functions

        //--Listener for menu items
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_history:
                        intentCallHistory();
                        return true;
                    case R.id.action_settings:
                        //Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        popSettings();
                        return true;
                    case R.id.action_scan:
                        popBarcodeChecker();
                        return true;
                    default:
                        return false;
                }
            }
        });

        //--Start scan activity
        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSettings();
            }
        });

        //--Search employee from live server
        empNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    fetchActiveUserEmployee(empNo.getText().toString());
                }
                else{
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //--Sync local data from live server
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataFetchers();
                refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
                refreshLayout.setRefreshing(false);
            }
        });

        //--Start PO Scanner
        poScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    popPOPopulate();
                }
                catch (Exception e){alertMod.warningGeneral("PO No. detection is not set in " +
                                                                    "selected " +
                                                                 "material & model.");}
            }
        });

        //--Start Document No Scanner
        invScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    popDocNoPopulate();
                }
                catch (Exception e){alertMod.warningGeneral("Invoice No. detection is not set in " +
                                                                 "selected material & model.");}
            }
        });
        //--Method Call
        calendarPop();
        dataFetchers();

        //loadSettings();
    }

    private void validateSettings(){

        boolean cri1 = false, cri2 = false, cri3 = false, cri4 = false, cri5=false, cri6=
                false;
        if(docno.getText().toString().equals("")){
            cri1 = false;
            docno.setError("Required Field!");
        }else{
            cri1 = true;
        }

        if(poNo.getText().toString().equals("")){
            cri2 = false;
            poNo.setError("Required Field!");
        }else{
            cri2 = true;
        }

        if(empNo.getText().toString().equals("")){
            cri3 = false;
            empNo.setError("Required Field!");
        }else{
            cri3 = true;
        }

        if(rack.getText().toString().equals("")){
            cri4 = false;
            rack.setError("Required Field!");
        }else{
            cri4 = true;
        }

        if(docDate.getText().toString().equals("")){
            cri5 = false;
            docDate.setError("Required Field!");
        }else{
            cri5 = true;
        }




        try {
            if(activeEmpNo.equals("")){
                cri6 = false;
                alertMod.errorGeneral("Unregistered/Invalid User!");
            }
            else if(activeEmpNo.equals("a")){
                cri6 = false;
                alertMod.errorGeneral("Unregistered/Invalid User!");
            }
            else if(activeEmpNo.equals("b")){
                cri6 = false;
                alertMod.errorGeneral("System Error! \n (Data Retrieval)");
            }
            else if(activeEmpNo.equals("d")){
                cri6 = false;
                alertMod.errorGeneral("System Error!");
            }
            else if(activeEmpNo.equals("c")){
                cri6 = false;
                alertMod.errorGeneral("Network Error!");
            }
            else{
                cri6 = true;
            }
        }
        catch (Exception e){
            activeEmpNo = "d";
        }


        if(q_transType.equals("402")){
            if(cri1 == true  && cri3 == true && cri4 == true && cri5 == true && cri6 == true){
                intentCallScanner();
            }
            else if(cri1 == true  && cri3 == true && cri4 == true && cri5 == true && cri6 == false){

            }
            else{
                //Toast.makeText(MainActivity.this, R.string.error_req_fields, Toast.LENGTH_SHORT).show();
                alertMod.incompleteData();
            }
        }
        else {
            if(cri1 == true && cri2 == true && cri3 == true && cri4 == true && cri5 == true && cri6 == true){
                intentCallScanner();
            }
            else if(cri1 == true && cri2 == true && cri3 == true && cri4 == true && cri5 == true && cri6 == false){

            }
            else{
                //Toast.makeText(MainActivity.this, R.string.error_req_fields, Toast.LENGTH_SHORT).show();
                alertMod.incompleteData();
            }
        }

    }

    private void permissionControlCam(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        Toast.makeText(MainActivity.this, "Camera Allowed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        Toast.makeText(MainActivity.this, "Camera Denied", Toast.LENGTH_LONG).show();
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

    private void transferDataType(String transTypeSelected){
        strHandler transType = dataHelper.getTransTypeIndex(transTypeSelected);
        q_transType = transType.getTransTypeIndex();
    }

    //--One-stop method for card layout height detection and setter.
    private void controlLayoutParam(int height){
        ViewGroup.LayoutParams layoutParams = mainCard.getLayoutParams();
        if (null == layoutParams)
            return;
        //set height
        layoutParams.height = height;
        //set changes to control
        mainCard.setLayoutParams(layoutParams);
    }

    //======================================================================
    /**/
    //===========================INTENT CALLS===============================

    //--Intent call for history activity.
    private void intentCallHistory(){
        Intent i = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(i);
    }

    //--Intent call for scanner activity.
    private void intentCallScanner(){
        String sessionID =  GenerateSessionIDModule.getCurrentSession();
        String device = GetDeviceNameModule.getDeviceName();
        String materialTypeStat = sMatType.getSelectedItem().toString();
        Intent initScanner = new Intent(MainActivity.this, ScannerActivity.class);
        String typeVal = transferType.getSelectedItem().toString();
        transferDataType(typeVal);
        if(q_transType.equals("402")){ //= current value of transfer from
            // other section
            initScanner.putExtra("DOCUMENT_NO",docno.getText().toString().replace("'","`"));
            initScanner.putExtra("DOCUMENT_DATE",docDate.getText().toString());
            initScanner.putExtra("EMPLOYEE_NO",empNo.getText().toString());

            if(invMth.getVisibility() == View.VISIBLE){
                initScanner.putExtra("INVENTORY_MONTH",invMth.getSelectedItem().toString());
            }
            else{
                strHandler mth = dataHelper.monthSingleData();
                String mthData = mth.getInvMth();
                initScanner.putExtra("INVENTORY_MONTH", mthData);
            }

            if(invYr.getVisibility() == View.VISIBLE){
                initScanner.putExtra("INVENTORY_YEAR",invYr.getSelectedItem().toString());
            }
            else{
                strHandler yr = dataHelper.yearSingleData();
                String yrData = yr.getInvYear();
                initScanner.putExtra("INVENTORY_YEAR", yrData);
            }

            initScanner.putExtra("TRANSFER_TYPE",transferType.getSelectedItem().toString());
            initScanner.putExtra("TRANSFER_SECTION",section.getSelectedItem().toString());
            initScanner.putExtra("RACK",sRack.getSelectedItem().toString());
            initScanner.putExtra("RACK_SECTION_NO",rack.getText().toString());
            initScanner.putExtra("DEVICE_NAME", device);
            initScanner.putExtra("REMARKS",reMarks.getText().toString());

            startActivity(initScanner);
            finish();
        }
        else{
            initScanner.putExtra("DOCUMENT_NO",docno.getText().toString().replace("'","`"));
            initScanner.putExtra("DOCUMENT_DATE",docDate.getText().toString());
            initScanner.putExtra("PO_NO",poNo.getText().toString());
            initScanner.putExtra("EMPLOYEE_NO",empNo.getText().toString());
            initScanner.putExtra("MATERIAL_CATEGORY", sMatCat.getSelectedItem().toString());
            initScanner.putExtra("MATERIAL",material.getSelectedItem().toString());

            if(materialTypeStat.equals("")){
                initScanner.putExtra("MATERIAL_TYPE", "NO TYPE");
            }
            else{
                initScanner.putExtra("MATERIAL_TYPE",sMatType.getSelectedItem().toString());
            }

            if(invMth.getVisibility() == View.VISIBLE){
                initScanner.putExtra("INVENTORY_MONTH",invMth.getSelectedItem().toString());
            }
            else{
                strHandler mth = dataHelper.monthSingleData();
                String mthData = mth.getInvMth();
                initScanner.putExtra("INVENTORY_MONTH", mthData);
            }

            if(invYr.getVisibility() == View.VISIBLE){
                initScanner.putExtra("INVENTORY_YEAR",invYr.getSelectedItem().toString());
            }
            else{
                strHandler yr = dataHelper.yearSingleData();
                String yrData = yr.getInvYear();
                initScanner.putExtra("INVENTORY_YEAR", yrData);
            }

            initScanner.putExtra("MATERIAL_CLASS",materialClass.getSelectedItem().toString());
            initScanner.putExtra("SUPPLIER",supplier.getSelectedItem().toString());
            initScanner.putExtra("TRANSFER_TYPE",transferType.getSelectedItem().toString());
            initScanner.putExtra("TRANSFER_SECTION",section.getSelectedItem().toString());
            initScanner.putExtra("RACK",sRack.getSelectedItem().toString());
            initScanner.putExtra("RACK_SECTION_NO",rack.getText().toString());
            initScanner.putExtra("MODEL", sModel.getSelectedItem().toString());
            initScanner.putExtra("SESSION_ID", sessionID);
            initScanner.putExtra("DEVICE_NAME", device);

            startActivity(initScanner);
            finish();
        }



    }

    //======================================================================
    /**/
    //=========================== QR CHECKER ===============================

    //--Display barcode checker dialog
    private void popBarcodeChecker(){
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.scanner_checker);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                barcodeView = dialog.findViewById(R.id.barcode_view);
                g = dialog.findViewById(R.id.stat_green);
                s = dialog.findViewById(R.id.stat_red);
                viewfinderView2 = dialog.findViewById(R.id.zxing_viewfinder_view);
                resultText2 = dialog.findViewById(R.id.resultText);
                checkerResult = dialog.findViewById(R.id.tv_resultChecker);
                stat = dialog.findViewById(R.id.scannerStat);

                barcodeView.decodeContinuous(callback);
                barcodeView.resume();
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        barcodeView.pause();
                    }
                });
            }

    String delimitStat, invoiceStat, poStat, h_material, h_model, h_supplier, autoDismiss="O";
    //--Get material, model & supplier
    public void getTrio(){
        h_material=material.getSelectedItem().toString();
        h_model = sModel.getSelectedItem().toString();
        h_supplier=supplier.getSelectedItem().toString();
    }

    //--Display PO Scanner dialog
    private void popPOPopulate(){
        getTrio();
        poNo.setText("");
        poStat = codeDefinition.getPOStatus(h_material, h_model, h_supplier);
        if(poStat.equals("I")){
            autoDismiss="O";
            delimitStat = codeDefinition.getDelimiterStatus(h_material, h_model, h_supplier);
            dialog1 = new Dialog(MainActivity.this);
            final Dialog dialog = new Dialog(MainActivity.this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.scanner_checker);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            scannerTitle = dialog.findViewById(R.id.tv_title);
            scannerTitle.setText("Scan PO No from QR/Barcode");
            scannerTitle.setTextSize(20.0f);
            barcodeView = dialog.findViewById(R.id.barcode_view);
            g = dialog.findViewById(R.id.stat_green);
            s = dialog.findViewById(R.id.stat_red);
            viewfinderView2 = dialog.findViewById(R.id.zxing_viewfinder_view);
            resultText2 = dialog.findViewById(R.id.resultText);
            checkerResult = dialog.findViewById(R.id.tv_resultChecker);
            stat = dialog.findViewById(R.id.scannerStat);

            handler.postDelayed(new Runnable(){
                public void run(){
                    if(autoDismiss.equals("I")){
                        autoDismiss="O";
                        barcodeView.pause();
                        dialog.dismiss();
                        dialog.cancel();

                    }
                    handler.postDelayed(this, delay);
                }
            }, delay);


            barcodeView.decodeContinuous(callbackPO);
            barcodeView.resume();
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    barcodeView.pause();
                }
            });
        }
        else if(poStat.equals("O")){
            alertMod.warningGeneral("PO No is not available in selected material & model.");
        }
        else {
            alertMod.infoGeneral("PO No detection is not set in this material & model.");
        }

    }

    //--Display Document No Scanner dialog
    private void popDocNoPopulate(){
        getTrio();
        invoiceStat = codeDefinition.getInvoiceStatus(h_material, h_model, h_supplier);
        if(invoiceStat.equals("I")){
            autoDismiss="O";
            delimitStat = codeDefinition.getDelimiterStatus(h_material, h_model, h_supplier);
            dialog2 = new Dialog(MainActivity.this);
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.scanner_checker);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            scannerTitle = dialog.findViewById(R.id.tv_title);
            scannerTitle.setText("Scan Document No from QR/Barcode");
            scannerTitle.setTextSize(20.0f);
            barcodeView = dialog.findViewById(R.id.barcode_view);
            g = dialog.findViewById(R.id.stat_green);
            s = dialog.findViewById(R.id.stat_red);
            viewfinderView2 = dialog.findViewById(R.id.zxing_viewfinder_view);
            resultText2 = dialog.findViewById(R.id.resultText);
            checkerResult = dialog.findViewById(R.id.tv_resultChecker);
            stat = dialog.findViewById(R.id.scannerStat);

            handler.postDelayed(new Runnable(){
                public void run(){
                    if(autoDismiss.equals("I")){
                        autoDismiss="O";
                        barcodeView.pause();
                        dialog.dismiss();
                        dialog.cancel();
                    }
                    handler.postDelayed(this, delay);
                }
            }, delay);

            barcodeView.decodeContinuous(callbackDocNo);
            barcodeView.resume();
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    barcodeView.pause();
                }
            });
        }
        else if(invoiceStat.equals("O")){
            alertMod.warningGeneral("Invoice No is not available in this material & model.");
        }
        else {
            alertMod.infoGeneral("Invoice No detection is not set in this material & model.");
        }

    }

    //--Zxing barcode decoder(Checker).
    private BarcodeCallback callback = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {
           /* if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }*/

            lastText = result.getText();
            barcodeView.setStatusText(result.getText());
            //resultText2.setText("Latest Result \n" + lastText );
            checkerResult.setText(lastText + "\n \n" +"Code Count: "+ lastText.length());
            beepManager.playBeepSoundAndVibrate();

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
            }, 300);
            barcodeView.pause();
            barcodeView.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    //--Zxing barcode decoder.(Settings)
    private BarcodeCallback callbackSettings = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {

            lastText = result.getText();
            getLocalHost.setText(lastText);
            beepManager.playBeepSoundAndVibrate();
            initView.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 300);
            barcodeView2.pause();
            barcodeView2.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    //--Zxing barcode decoder(PO no).
    private BarcodeCallback callbackPO = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {

            lastText = result.getText();
            barcodeView.setStatusText(result.getText());
            //resultText2.setText("Latest Result \n" + lastText );
            checkerResult.setText(lastText +"\n \n"+ "Tap to exit scanner");
            beepManager.playBeepSoundAndVibrate();

            String[] codeRaw;
            if(delimitStat.equals("I")){
                autoDismiss="I";
                String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                  h_supplier);
                Integer poPosFin = Integer.parseInt(codeDefinition.getPoPos(h_material, h_model,
                                                                            h_supplier));
                codeRaw = lastText.split(Pattern.quote(delimiterChar));
                poNo.setText(codeRaw[poPosFin]);
            }
            else{
                autoDismiss="I";
                Integer poBegFin = Integer.parseInt(codeDefinition.getPoPos(h_material, h_model,
                                                                            h_supplier));
                Integer poEndFin = Integer.parseInt(codeDefinition.getPoLen(h_material, h_model,
                                                                            h_supplier));
                poNo.setText(lastText.substring(poBegFin,poEndFin));
            }

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
            }, 300);
            barcodeView.pause();
            barcodeView.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    //--Zxing barcode decoder(Document no).
    private BarcodeCallback callbackDocNo = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {

            lastText = result.getText();
            barcodeView.setStatusText(result.getText());
            //resultText2.setText("Latest Result \n" + lastText );
            checkerResult.setText(lastText +"\n \n"+ "Tap to exit scanner");
            beepManager.playBeepSoundAndVibrate();

            String[] codeRaw;
            if(delimitStat.equals("I")){
                autoDismiss="I";
                String delimiterChar = codeDefinition.getDeliChar(h_material, h_model,
                                                                  h_supplier);
                Integer invPosFin = Integer.parseInt(codeDefinition.getInvPos(h_material, h_model,
                                                                            h_supplier));
                codeRaw = lastText.split(Pattern.quote(delimiterChar));
                docno.setText(codeRaw[invPosFin]);
            }
            else{
                autoDismiss="I";
                Integer invBegFin = Integer.parseInt(codeDefinition.getInvPos(h_material, h_model,
                                                                            h_supplier));
                Integer invEndFin = Integer.parseInt(codeDefinition.getInvLen(h_material, h_model,
                                                                            h_supplier));
                docno.setText(lastText.substring(invBegFin,invEndFin));
            }

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
            }, 300);
            barcodeView.pause();
            barcodeView.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    //======================================================================
    /**/
    //===========================DATE METHODS===============================

    //--Display android datepicker
    private void calendarPop(){
        docDate = (EditText)findViewById(R.id.tb_docDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                docCalendar.set(Calendar.YEAR, year);
                docCalendar.set(Calendar.MONTH, month);
                docCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateSetter();
            }
        };
        docDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, docCalendar.get(Calendar.YEAR), docCalendar.get(Calendar.MONTH), docCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //--Set picked date to editText.
    private void dateSetter(){
        String docDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(docDateFormat, Locale.US);
        docDate.setText(sdf.format(docCalendar.getTime()));
    }

    //======================================================================
    /**/
    //===========================SETTING METHODS============================

    //--Displays setting dialog
    private void popSettings(){
        loadSettings();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_setting);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
                dialog.dismiss();
                //Toast.makeText(MainActivity.this, "Setting's dismissed", Toast.LENGTH_SHORT)
                // .show();
            }
        });

        commitSetting = dialog.findViewById(R.id.bt_settingSave);
        getLocalHost = dialog.findViewById(R.id.tb_localHost);

        String retrieveHost = loadSettings(); //retrieve host from loadSetting method
        getLocalHost.setText(retrieveHost); //display fetched settings

        barcodeView2 = dialog.findViewById(R.id.barcode_view);
        g2 = dialog.findViewById(R.id.stat_green);
        s2 = dialog.findViewById(R.id.stat_red);

        codeCheck = dialog.findViewById(R.id.bt_scanToIn);

        codeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView = dialog.findViewById(R.id.barcode_view);
                viewfinderView2 = dialog.findViewById(R.id.zxing_viewfinder_view);
                resultText2 = dialog.findViewById(R.id.resultText);
                checkerResult = dialog.findViewById(R.id.tv_resultChecker);
                stat2 = dialog.findViewById(R.id.scannerStat);

                getLocalHost.setText("");
                initView.setVisibility(View.VISIBLE);
                barcodeView2.decodeContinuous(callbackSettings);
                barcodeView2.resume();
            }
        });


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                barcodeView2.pause();
            }
        });

        commitSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String strLocalHost = getLocalHost.getText().toString();
                    if(retrieveHost == null){
                        //calls localhost save method
                        saveSettings(strLocalHost);
                    }
                    else{
                        //calls localhost update method
                        updateSetting(id, strLocalHost);
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "No localhost was set!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, "No localhost was set!" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return methodStrHost;
    }

    //--Save & sets localhost setting.
    private void saveSettings(String host){
        String localhostVal = host;
        dtCollector.rawLocalHost = localhostVal;
        boolean createSetting = new dbHelper(MainActivity.this).createSettingDt(dtCollector);

        if (createSetting == true){
            alertMod.localhostSet("is saved");
            fetchInfoBoard();
        }
        else {
            alertMod.localhostSetError("is not saved");
        }
    }

    //--Update localhost settings.
    private void updateSetting(String id, String host){
        String strLocalHost = host;
        boolean updateSetting = new dbHelper(MainActivity.this).updateSetting(strLocalHost, id);
        if(updateSetting == true){
            alertMod.localhostSet("updated");
            fetchInfoBoard();
        }else{
           alertMod.localhostSetError("not updated");
        }

    }

    //======================================================================
    /**/
    //=====================NETWORK DATA FETCH METHODS=======================

    //--Fetch information board for mobile.
    private void fetchInfoBoard(){
        String host = loadSettings();
        ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
        Call<List<PojoDataSource>> call = clientInterface.getInfo("info_mob","s");
        //Call<List<PojoDataSource>> call = clientInterface.getInfo1();
        fetchLoadStat.setVisibility(View.VISIBLE);
        fetchStat.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<PojoDataSource>>() {
            @Override
            public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                fetchStat.setText(R.string.on_success_main_activity);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchLoadStat.setVisibility(View.GONE);
                    }
                }, 500);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchStat.setVisibility(View.GONE);
                    }
                }, 5000);

                List<PojoDataSource> infoList = response.body();
                try{
                    String infoSub = infoList.get(0).getInfoSubject();
                    //String infoSub = response.body().getInfoSubject();
                    strip.setText(infoSub);
                    strip.setSelected(true);

                }
                catch (Exception e){
                    fetchStat.setText(R.string.http_200 + " " + e.getMessage());
                }
                //fetchMaterialData();
                spinnerLoadMaterialCategory();
            }
            @Override
            public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                strip.setText(R.string.on_failure_infoSub);
                strip.setSelected(true);
                fetchLoadStat.setVisibility(View.GONE);
                fetchStat.setVisibility(View.GONE);
                spinnerLoadMaterialCategory();
                spinnerLoadTransferTypes();
                spinnerLoadRacks();
            }
        });

    }

    //--Get material data for spinner
    private void fetchMaterialData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getMaterialList();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> materialList = response.body();

                            try{
                                    items = dataHelper.materialData();

                                    //---Check if table has data.
                                    //---If table is not empty, delete and replace data.
                                    //---If empty, encode the retrieve data from server
                                    if(items != null && items.length > 0){
                                        dataHelper.deleteMaterialAll(); //syntax for deletion of local table

                                        for(int i = 0; i < materialList.size(); i++){
                                            itemDataCollector.materialDataName = materialList.get(i).getMaterialName();
                                            itemDataCollector.materialDataID = materialList.get(i).getMaterialID();
                                            itemDataCollector.materialDataClassCode = materialList.get(i).getMaterialClassCode();
                                            itemDataCollector.materialMatCatIDFK =
                                                    materialList.get(i).getMatCategoryIDFK();

                                            boolean createMaterialData = new dbHelper(MainActivity.this).insertMaterial(itemDataCollector);
                                        }
                                    }
                                    else{

                                        for(int i = 0; i < materialList.size(); i++){
                                            itemDataCollector.materialDataName =
                                                    materialList.get(i).getMaterialName();
                                            itemDataCollector.materialDataID = materialList.get(i).getMaterialID();
                                            itemDataCollector.materialDataClassCode = materialList.get(i).getMaterialClassCode();
                                            itemDataCollector.materialMatCatIDFK =
                                                    materialList.get(i).getMatCategoryIDFK();

                                            boolean createMaterialData = new dbHelper(MainActivity.this).insertMaterial(itemDataCollector);
                                        }
                                    }
                                //spinnerLoadMaterial();
                               // fetchSupplierData();
                              //  fetchClassTypeData();
                               // fetchSectionData();
                                //fetchTransferTypeData();
                                //fetchProcessFlowData();
                            }
                            catch (Exception e){
                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadMaterialCategory();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(MainActivity.this, "Mat Gen: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }

    //--Get material data for spinner
    private void fetchMaterialCategoryData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getMaterialCategory();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> materialList = response.body();

                            try{
                                items = dataHelper.materialData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteMatCatAll(); //syntax for deletion of local table

                                    for(int i = 0; i < materialList.size(); i++){
                                        itemDataCollector.catDataID =
                                                materialList.get(i).getMatCategoryID();
                                        itemDataCollector.catDataName =
                                                materialList.get(i).getMatCategoryName();
                                        itemDataCollector.catDataMatIDFK =
                                                materialList.get(i).getMaterialIDFK();


                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertMaterialCategory(itemDataCollector);
                                    }
                                }
                                else{

                                    for(int i = 0; i < materialList.size(); i++){
                                        itemDataCollector.catDataID =
                                                materialList.get(i).getMatCategoryID();
                                        itemDataCollector.catDataName =
                                                materialList.get(i).getMatCategoryName();
                                        itemDataCollector.catDataMatIDFK =
                                                materialList.get(i).getMaterialIDFK();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertMaterialCategory(itemDataCollector);
                                    }
                                }
                                //spinnerLoadMaterial();
                                // fetchSupplierData();
                                //  fetchClassTypeData();
                                // fetchSectionData();
                                //fetchTransferTypeData();
                                //fetchProcessFlowData();
                            }
                            catch (Exception e){
                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadMaterialCategory();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(MainActivity.this, "Mat Gen: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }

    //--Get material type data for spinner
    private void fetchMaterialTypeData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getMaterialType();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> materialTypeList = response.body();

                            try{
                                items = dataHelper.materialTypeData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteMaterialTypeAll(); //syntax for deletion of local table

                                    for(int i = 0; i < materialTypeList.size(); i++){
                                        itemDataCollector.materialDataIDFK = materialTypeList.get(i).getMaterialIDFK();
                                        itemDataCollector.materialTypeID = materialTypeList.get(i).getMatTypeID();
                                        itemDataCollector.materialDataName = materialTypeList.get(i).getMaterialName();
                                        itemDataCollector.materialTypeName = materialTypeList.get(i).getMatTypeName();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertMaterialType(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < materialTypeList.size(); i++){
                                        itemDataCollector.materialDataIDFK = materialTypeList.get(i).getMaterialIDFK();
                                        itemDataCollector.materialTypeID = materialTypeList.get(i).getMatTypeID();
                                        itemDataCollector.materialDataName = materialTypeList.get(i).getMaterialName();
                                        itemDataCollector.materialTypeName = materialTypeList.get(i).getMatTypeName();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertMaterialType(itemDataCollector);
                                    }
                                }
                                // spinnerLoadMaterial();
                                // fetchSupplierData();
                                // fetchClassTypeData();
                                // fetchSectionData();
                                // fetchTransferTypeData();
                                // fetchProcessFlowData();
                            }
                            catch (Exception e){
                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadMaterialCategory();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(MainActivity.this, "Mat Gen: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    //--Get supplier data for spinner
    private void fetchSupplierData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getSupplierList();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> supplierList = response.body();

                            try{
                                items = dataHelper.supplierData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteSupplierAll(); //syntax for deletion of local table

                                    for(int i = 0; i < supplierList.size(); i++){
                                        itemDataCollector.supplierDataName = supplierList.get(i).getSupplierName();
                                        itemDataCollector.supplierDataID = supplierList.get(i).getSupplierID();
                                        itemDataCollector.supplierDataCode = supplierList.get(i).getSupplierCode();
                                        itemDataCollector.supplierDataMaterialID = supplierList.get(i).getMaterialIDFK();
                                        itemDataCollector.supplierDataMaterialTypeID =
                                                supplierList.get(i).getMatTypeIDFK();

                                        boolean createSupplierData = new dbHelper(MainActivity.this).insertSupplier(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < supplierList.size(); i++){
                                        itemDataCollector.supplierDataName = supplierList.get(i).getSupplierName();
                                        itemDataCollector.supplierDataID = supplierList.get(i).getSupplierID();
                                        itemDataCollector.supplierDataCode = supplierList.get(i).getSupplierCode();
                                        itemDataCollector.supplierDataMaterialID = supplierList.get(i).getMaterialIDFK();
                                        itemDataCollector.supplierDataMaterialTypeID =
                                                supplierList.get(i).getMatTypeIDFK();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertSupplier(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadSupplier();
                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get class type data for spinner
    private void fetchClassTypeData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getClassType();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> classList = response.body();

                            try {
                                items = dataHelper.classTypeData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if (items != null && items.length > 0) {
                                    dataHelper.deleteClassTypeAll(); //syntax for deletion of local table

                                    for (int i = 0; i < classList.size(); i++) {
                                        itemDataCollector.classDataName = classList.get(i).getClassName();
                                        itemDataCollector.classDataID = classList.get(i).getClassID();
                                        itemDataCollector.classDataCode = classList.get(i).getClassCode();
                                        itemDataCollector.classDataMaterialID = classList.get(i).getMaterialID();

                                        boolean createSupplierData = new dbHelper(MainActivity.this).insertClassType(itemDataCollector);
                                    }
                                } else {
                                    for (int i = 0; i < classList.size(); i++) {
                                        itemDataCollector.classDataName = classList.get(i).getClassName();
                                        itemDataCollector.classDataID = classList.get(i).getClassID();
                                        itemDataCollector.classDataCode = classList.get(i).getClassCode();
                                        itemDataCollector.classDataMaterialID = classList.get(i).getMaterialID();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertClassType(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadSupplier();
                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get transfer type data for spinner
    private void fetchTransferTypeData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getTransferType();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> transferList = response.body();

                            try{
                                items = dataHelper.transferTypeData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteTransferTypeAll(); //syntax for deletion of local table

                                    for(int i = 0; i < transferList.size(); i++){
                                        itemDataCollector.transferDataID = transferList.get(i).getTransferID();
                                        itemDataCollector.transferDataCode = transferList.get(i).getTransferCode();
                                        itemDataCollector.transferDataName = transferList.get(i).getTransferName();

                                        boolean createSupplierData = new dbHelper(MainActivity.this).insertTransferType(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < transferList.size(); i++){
                                        itemDataCollector.transferDataID = transferList.get(i).getTransferID();
                                        itemDataCollector.transferDataCode = transferList.get(i).getTransferCode();
                                        itemDataCollector.transferDataName = transferList.get(i).getTransferName();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertTransferType(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    spinnerLoadTransferTypes();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadTransferTypes();
                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get section data for spinner
    private void fetchSectionData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getSections();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> sectionList = response.body();

                            try{
                                items = dataHelper.sectionData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteSectionAll(); //syntax for deletion of local table

                                    for(int i = 0; i < sectionList.size(); i++){
                                        itemDataCollector.sectionDataID = sectionList.get(i).getSectionID();
                                        itemDataCollector.sectionDataCode = sectionList.get(i).getSectionCode();
                                        itemDataCollector.sectionDataName = sectionList.get(i).getSectionName();
                                        itemDataCollector.sectionDataClassID = sectionList.get(i).getClassIDFK();

                                        boolean createSupplierData = new dbHelper(MainActivity.this).insertSection(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < sectionList.size(); i++){
                                        itemDataCollector.sectionDataID = sectionList.get(i).getSectionID();
                                        itemDataCollector.sectionDataCode = sectionList.get(i).getSectionCode();
                                        itemDataCollector.sectionDataName = sectionList.get(i).getSectionName();
                                        itemDataCollector.sectionDataClassID = sectionList.get(i).getClassIDFK();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertSection(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadSupplier();
                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get section data for spinner
    private void fetchRackData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getRacks();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> rackList = response.body();

                            try{
                                items = dataHelper.rackData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteRackAll(); //syntax for deletion of local table

                                    for(int i = 0; i < rackList.size(); i++){
                                        itemDataCollector.rackDataID = rackList.get(i).getRackID();
                                        itemDataCollector.rackDataCode = rackList.get(i).getRackCode();
                                        itemDataCollector.rackDataName = rackList.get(i).getRackName();

                                        boolean createRackData = new dbHelper(MainActivity.this).insertRacks(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < rackList.size(); i++){
                                        itemDataCollector.rackDataID = rackList.get(i).getRackID();
                                        itemDataCollector.rackDataCode = rackList.get(i).getRackCode();
                                        itemDataCollector.rackDataName = rackList.get(i).getRackName();

                                        boolean createRackData = new dbHelper(MainActivity.this).insertRacks(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    spinnerLoadRacks();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {

                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get section data for spinner
    private void fetchModelData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getModel();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> modelList = response.body();

                            try{
                                items = dataHelper.modelData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteModelAll(); //syntax for deletion of local table

                                    for(int i = 0; i < modelList.size(); i++){
                                        itemDataCollector.modelDataID = modelList.get(i).getModelID();
                                        itemDataCollector.modelDataCode = modelList.get(i).getModelCode();
                                        itemDataCollector.modelDataName = modelList.get(i).getModelName();
                                        itemDataCollector.modelDataMaterialID = modelList.get(i).getMaterialIDFK();
                                        itemDataCollector.modelDataSupplierID =
                                                modelList.get(i).getSupplierID();
                                        itemDataCollector.modelDataMcID =
                                                modelList.get(i).getMcID();
                                        boolean createRackData = new dbHelper(MainActivity.this).insertModel(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < modelList.size(); i++){
                                        itemDataCollector.modelDataID = modelList.get(i).getModelID();
                                        itemDataCollector.modelDataCode = modelList.get(i).getModelCode();
                                        itemDataCollector.modelDataName = modelList.get(i).getModelName();
                                        itemDataCollector.modelDataMaterialID = modelList.get(i).getMaterialIDFK();
                                        itemDataCollector.modelDataSupplierID =
                                                modelList.get(i).getSupplierID();
                                        itemDataCollector.modelDataMcID =
                                                modelList.get(i).getMcID();
                                        boolean createModelData = new dbHelper(MainActivity.this).insertModel(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    //
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {

                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get section data for spinner
    private void fetchSectionTransferData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getSectionTransfer();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> sectionTransferList = response.body();

                            try{
                                items = dataHelper.modelData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteStAll(); //syntax for deletion of local table

                                    for(int i = 0; i < sectionTransferList.size(); i++){
                                        itemDataCollector.stSecID =
                                                sectionTransferList.get(i).getSecID();
                                        itemDataCollector.stClassIDFK =
                                                sectionTransferList.get(i).getClassIDFK();
                                        itemDataCollector.stSectionIDFK =
                                                sectionTransferList.get(i).getSectionIDFK();
                                        itemDataCollector.stTransferIDFK =
                                                sectionTransferList.get(i).getTransferIDFK();
                                        itemDataCollector.stDirection =
                                                sectionTransferList.get(i).getDirectionIDFK();
                                        itemDataCollector.stSequence =
                                                sectionTransferList.get(i).getSequence();

                                        boolean createRackData =
                                                new dbHelper(MainActivity.this).insertSectionTransfer(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < sectionTransferList.size(); i++){
                                        itemDataCollector.stSecID =
                                                sectionTransferList.get(i).getSecID();
                                        itemDataCollector.stClassIDFK =
                                                sectionTransferList.get(i).getClassIDFK();
                                        itemDataCollector.stSectionIDFK =
                                                sectionTransferList.get(i).getSectionIDFK();
                                        itemDataCollector.stTransferIDFK =
                                                sectionTransferList.get(i).getTransferIDFK();
                                        itemDataCollector.stDirection =
                                                sectionTransferList.get(i).getDirectionIDFK();
                                        itemDataCollector.stSequence =
                                                sectionTransferList.get(i).getSequence();

                                        boolean createModelData = new dbHelper(MainActivity.this).insertSectionTransfer(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    //
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {

                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get month data
    private void fetchMonthData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getMonth();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> month = response.body();

                            try{
                                items = dataHelper.modelData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteMonthAll(); //syntax for deletion of local table

                                    for(int i = 0; i < month.size(); i++){
                                        itemDataCollector.dataIMthID =
                                                month.get(i).getiMthID();
                                        itemDataCollector.dataIMth =
                                                month.get(i).getiMth();

                                        boolean createRackData =
                                                new dbHelper(MainActivity.this).insertMonth(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < month.size(); i++){
                                        itemDataCollector.dataIMthID =
                                                month.get(i).getiMthID();
                                        itemDataCollector.dataIMth =
                                                month.get(i).getiMth();

                                        boolean createModelData = new dbHelper(MainActivity.this).insertMonth(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    //
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {

                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get year data
    private void fetchYearData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getYear();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> sectionTransferList = response.body();

                            try{
                                items = dataHelper.modelData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteYearAll(); //syntax for deletion of local table

                                    for(int i = 0; i < sectionTransferList.size(); i++){
                                        itemDataCollector.dataIYrID =
                                                sectionTransferList.get(i).getiYrID();
                                        itemDataCollector.dataIYr =
                                                sectionTransferList.get(i).getiYr();

                                        boolean createRackData =
                                                new dbHelper(MainActivity.this).insertYear(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < sectionTransferList.size(); i++){
                                        itemDataCollector.dataIYrID =
                                                sectionTransferList.get(i).getiYrID();
                                        itemDataCollector.dataIYr =
                                                sectionTransferList.get(i).getiYr();

                                        boolean createModelData = new dbHelper(MainActivity.this).insertYear(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    //
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {

                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get month/year data for spinner
    private void fetchActiveMonthYearData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getActiveMonthYear();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> activeMthYr = response.body();

                            try{
                                items = dataHelper.modelData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteActiveMonthYearAll(); //syntax for deletion of local table

                                    for(int i = 0; i < activeMthYr.size(); i++){
                                        itemDataCollector.dataActMth =
                                                activeMthYr.get(i).getActiveMth();
                                        itemDataCollector.dataActYr =
                                                activeMthYr.get(i).getActiveYr();
                                        itemDataCollector.dataActStat =
                                                activeMthYr.get(i).getIsActive();

                                        boolean createRackData =
                                                new dbHelper(MainActivity.this).insertActiveMonthYear(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < activeMthYr.size(); i++){
                                        itemDataCollector.dataActMth =
                                                activeMthYr.get(i).getActiveMth();
                                        itemDataCollector.dataActYr =
                                                activeMthYr.get(i).getActiveYr();
                                        itemDataCollector.dataActStat =
                                                activeMthYr.get(i).getIsActive();

                                        boolean createModelData = new dbHelper(MainActivity.this).insertActiveMonthYear(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    //
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {

                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get month/year number data
    private void fetchActiveMonthNumberData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<PojoDataSource> call = clientInterface.getActiveMonthNumber();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<PojoDataSource>() {
                @Override
                public void onResponse(Call<PojoDataSource> call, Response<PojoDataSource> response) {
                    try{
                        activeNumber = response.body().getActiveNumber();
                        activeMonthCountChecker(activeNumber);
                    }
                    catch(Exception e){}

                }
                @Override
                public void onFailure(Call<PojoDataSource> call, Throwable t) {

                }
            });
        }
        catch(Exception e){
            activeNumber = "2";
        }
    }

    //--Get Employee Validation
    private void fetchActiveUserEmployee(String empNo){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<PojoDataSource> call = clientInterface.getEmpStat(empNo, "user_stat");
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<PojoDataSource>() {
                @Override
                public void onResponse(Call<PojoDataSource> call, Response<PojoDataSource> response) {

                    try {
                        activeEmpNo = response.body().getMessage();
                        if(activeEmpNo.equals(null))
                        {
                            activeNumber = "a";
                        }
                    }
                    catch (Exception e){
                        activeEmpNo = "a";
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);

                }
                @Override
                public void onFailure(Call<PojoDataSource> call, Throwable t) {
                    activeEmpNo = "c";
                }
            });
        }
        catch(Exception e){
            activeEmpNo = "d";
        }
    }

    //--Get Code Definition
    private void fetchCodeDefinitionData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getTable("codeDef","m");
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> codeDef = response.body();

                            try{
                                items = dataHelper.modelData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteCodeDefinitionAll(); //syntax for deletion of local table

                                    for(int i = 0; i < codeDef.size(); i++){
                                        itemDataCollector.dataBcID =
                                                codeDef.get(i).getBcID();
                                        itemDataCollector.dataDelimiter =
                                                codeDef.get(i).getDelimiter_char();
                                        itemDataCollector.dataMatID =
                                                codeDef.get(i).getMaterialID();
                                        itemDataCollector.dataMcID =
                                                codeDef.get(i).getMcID();
                                        itemDataCollector.dataModPos =
                                                codeDef.get(i).getModel_pos();
                                        itemDataCollector.dataModLen =
                                                codeDef.get(i).getModel_len();
                                        itemDataCollector.dataQtyPos =
                                                codeDef.get(i).getQty_pos();
                                        itemDataCollector.dataQtyLen =
                                                codeDef.get(i).getQty_len();
                                        itemDataCollector.dataPoPos =
                                                codeDef.get(i).getPo_no_pos();
                                        itemDataCollector.dataPoLen =
                                                codeDef.get(i).getPo_no_len();
                                        itemDataCollector.dataInvPos =
                                                codeDef.get(i).getInvoice_no_pos();
                                        itemDataCollector.dataInvLen =
                                                codeDef.get(i).getInvoice_no_len();

                                        itemDataCollector.dataPartPos =
                                                codeDef.get(i).getPart_no_pos();
                                        itemDataCollector.dataPartLen =
                                                codeDef.get(i).getPart_no_len();
                                        itemDataCollector.dataLotPos =
                                                codeDef.get(i).getLot_no_pos();
                                        itemDataCollector.dataLotLen =
                                                codeDef.get(i).getLot_no_len();

                                        itemDataCollector.dataTotalLen =
                                                codeDef.get(i).getTotal_len();

                                        itemDataCollector.dataReqDelimiter =
                                                codeDef.get(i).getReq_delimiter();
                                        itemDataCollector.dataReqModel =
                                                codeDef.get(i).getReq_model();
                                        itemDataCollector.dataReqQty =
                                                codeDef.get(i).getReq_qty();
                                        itemDataCollector.dataReqPo =
                                                codeDef.get(i).getReq_po_No();
                                        itemDataCollector.dataReqInvoice =
                                                codeDef.get(i).getReq_invoice_no();
                                        itemDataCollector.dataReqPart =
                                                codeDef.get(i).getReq_part_no();
                                        itemDataCollector.dataReqLot =
                                                codeDef.get(i).getReq_lot_no();

                                        boolean createRackData =
                                                new dbHelper(MainActivity.this).insertCodeDefinition(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < codeDef.size(); i++){
                                        itemDataCollector.dataBcID =
                                                codeDef.get(i).getBcID();
                                        itemDataCollector.dataDelimiter =
                                                codeDef.get(i).getDelimiter_char();
                                        itemDataCollector.dataMatID =
                                                codeDef.get(i).getMaterialID();
                                        itemDataCollector.dataMcID =
                                                codeDef.get(i).getMcID();
                                        itemDataCollector.dataModPos =
                                                codeDef.get(i).getModel_pos();
                                        itemDataCollector.dataQtyPos =
                                                codeDef.get(i).getQty_pos();
                                        itemDataCollector.dataPoPos =
                                                codeDef.get(i).getPo_no_pos();
                                        itemDataCollector.dataPoLen =
                                                codeDef.get(i).getPo_no_len();
                                        itemDataCollector.dataInvPos =
                                                codeDef.get(i).getInvoice_no_pos();
                                        itemDataCollector.dataInvLen =
                                                codeDef.get(i).getInvoice_no_len();

                                        itemDataCollector.dataPartPos =
                                                codeDef.get(i).getPart_no_pos();
                                        itemDataCollector.dataPartLen =
                                                codeDef.get(i).getPart_no_len();
                                        itemDataCollector.dataLotPos =
                                                codeDef.get(i).getLot_no_pos();
                                        itemDataCollector.dataLotLen =
                                                codeDef.get(i).getLot_no_len();

                                        itemDataCollector.dataTotalLen =
                                                codeDef.get(i).getTotal_len();

                                        itemDataCollector.dataReqDelimiter =
                                                codeDef.get(i).getReq_delimiter();
                                        itemDataCollector.dataReqModel =
                                                codeDef.get(i).getReq_model();
                                        itemDataCollector.dataReqQty =
                                                codeDef.get(i).getReq_qty();
                                        itemDataCollector.dataReqPo =
                                                codeDef.get(i).getReq_po_No();
                                        itemDataCollector.dataReqInvoice =
                                                codeDef.get(i).getReq_invoice_no();
                                        itemDataCollector.dataReqPart =
                                                codeDef.get(i).getReq_part_no();
                                        itemDataCollector.dataReqLot =
                                                codeDef.get(i).getReq_lot_no();

                                        boolean createModelData =
                                                new dbHelper(MainActivity.this).insertCodeDefinition(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                    //
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {

                }
            });
        }
        catch(Exception e){

        }
    }

    //--Get process flow data
    private void fetchProcessFlowData(){
        String host = loadSettings();
        final dbHelper dataHelper = new dbHelper(MainActivity.this);
        dataCollector itemDataCollector = new dataCollector();
        try {
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getProcessFlow();
            fetchLoadStat.setVisibility(View.VISIBLE);
            fetchStat.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    fetchStat.setText(R.string.on_success_main_activity);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchLoadStat.setVisibility(View.GONE);
                        }
                    }, 500);

                    final String items[];

                    if(response.isSuccessful()){
                        if(response.body() != null){
                            List<PojoDataSource> pfData = response.body();

                            try{
                                items = dataHelper.sectionData();

                                //---Check if table has data.
                                //---If table is not empty, delete and replace data.
                                //---If empty, encode the retrieve data from server
                                if(items != null && items.length > 0){
                                    dataHelper.deleteProcessFlowAll(); //syntax for deletion of local table

                                    for(int i = 0; i < pfData.size(); i++){
                                        itemDataCollector.processDataCode = pfData.get(i).getProcessCode();
                                        itemDataCollector.processDataIsLastProcess = pfData.get(i).getIsLastProcess();
                                        itemDataCollector.processDataSectionID = pfData.get(i).getSectionIDFK();
                                        itemDataCollector.processDataMaterialID = pfData.get(i).getMaterialIDFK();
                                        itemDataCollector.processDataClassID = pfData.get(i).getClassIDFK();

                                        boolean createSupplierData = new dbHelper(MainActivity.this).insertProcessFlow(itemDataCollector);
                                    }
                                }
                                else{
                                    for(int i = 0; i < pfData.size(); i++){
                                        itemDataCollector.processDataCode = pfData.get(i).getProcessCode();
                                        itemDataCollector.processDataIsLastProcess = pfData.get(i).getIsLastProcess();
                                        itemDataCollector.processDataSectionID = pfData.get(i).getSectionIDFK();
                                        itemDataCollector.processDataMaterialID = pfData.get(i).getMaterialIDFK();
                                        itemDataCollector.processDataClassID = pfData.get(i).getClassIDFK();

                                        boolean createMaterialData = new dbHelper(MainActivity.this).insertProcessFlow(itemDataCollector);
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchStat.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    spinnerLoadSupplier();
                }
            });
        }
        catch(Exception e){

        }
    }

    //======================================================================
    /**/
    //=======================SPINNER LOADER METHODS=========================

    //--Load material data to spinner
    private void spinnerLoadMaterialCategory(){
        try{
            final String [] items = dataHelper.materialCategoryData();
            ArrayList<String> materialArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            materialArr.removeAll(Collections.singleton(null));

            sMatCat.setAdapter(listAdapter);
            sMatCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    {
                        checker = 5;
                        if(checker > 1){

                            String mat = parent.getItemAtPosition(position).toString();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    spinnerLoadMaterial(mat);
                                }
                            }, 500);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //matType.setVisibility(View.VISIBLE);
                    // sMatType.setVisibility(View.VISIBLE);
                    spinnerLoadSupplier();
                }
            });
        }
        catch(Exception e){
            Log.d(" MaterialSpinner", "Error: " + e.toString() );
        }
        fetchLoadStat.setVisibility(View.GONE);
        fetchStat.setVisibility(View.GONE);
    }

    //--Load material data to spinner
    public String matVal = "";
    private void spinnerLoadMaterial(String materialCat){
        try{
            final String [] items = dataHelper.MaterialWMaterialCat(materialCat);
            ArrayList<String> materialArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            materialArr.removeAll(Collections.singleton(null));

            material.setAdapter(listAdapter);
            material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            {
                checker = 5;
                    if(checker > 1){

                        String acb = parent.getItemAtPosition(position).toString();
                        matVal = acb;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                         public void run() {
                           if(acb.equals("AC-Block")){
                               spinnerLoadMaterialType(acb);
                                matType.setVisibility(View.VISIBLE);
                                sMatType.setVisibility(View.VISIBLE);
                            }
                            else{
                               spinnerLoadMaterialType(acb);
                                matType.setVisibility(View.VISIBLE);
                                sMatType.setVisibility(View.VISIBLE);
                            }

                        spinnerLoadClassTypes(acb);
                        spinnerLoadSupplierWMat(acb);

                            spinnerLoadActiveMonth();
                            spinnerLoadActiveYear();
                        }
                        }, 500);
                    }
            }
}

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //matType.setVisibility(View.VISIBLE);
                   // sMatType.setVisibility(View.VISIBLE);
                    spinnerLoadActiveMonth();
                    spinnerLoadActiveYear();
                    spinnerLoadSupplier();
                }
            });
        }
        catch(Exception e){
            Log.d(" MaterialSpinner", "Error: " + e.toString() );
        }
        fetchLoadStat.setVisibility(View.GONE);
        fetchStat.setVisibility(View.GONE);
    }

    //--Load rack data to spinner
    private void spinnerLoadRacks(){
        try{
            final String [] items = dataHelper.rackData();
            ArrayList<String> rackArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            rackArr.removeAll(Collections.singleton(null));

            sRack.setAdapter(listAdapter);
            sRack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    {

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch(Exception e){
        }
        fetchLoadStat.setVisibility(View.GONE);
        fetchStat.setVisibility(View.GONE);
    }

    //--Load material type data to spinner
    private void spinnerLoadMaterialType(String strMat){
        try{
            listAdapter.notifyDataSetChanged();
            sMatType = findViewById(R.id.sp_matType);
            final String [] items = dataHelper.materialTypeDataWMaterial(strMat);
            ArrayList<String> supplierArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            supplierArr.removeAll(Collections.singleton(null));
            listAdapter.notifyDataSetChanged();

            sMatType.setAdapter(listAdapter);
            sMatType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
            Log.d(" SupplierSpinner", "Error: " + e.toString() );
        }
    }

    //--Load supplier data to spinner
    private void spinnerLoadSupplier(){
        try{
            listAdapter.notifyDataSetChanged();
            final String [] items = dataHelper.supplierData();
            ArrayList<String> supplierArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            supplierArr.removeAll(Collections.singleton(null));
            listAdapter.notifyDataSetChanged();

            supplier.setAdapter(listAdapter);
            supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
            Log.d(" SupplierSpinner", "Error: " + e.toString() );
        }
    }

    //--Load supplier data to spinner with variable
    private void spinnerLoadSupplierWMat(String strMaterial){
        try{
            listAdapter.notifyDataSetChanged();
            supplier = findViewById(R.id.sp_supplier);
            final String [] items = dataHelper.supplierDataWMaterial(strMaterial);
            ArrayList<String> supplierArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            supplierArr.removeAll(Collections.singleton(null));

            supplier.setAdapter(listAdapter);
            supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String supp = parent.getItemAtPosition(position).toString();
                    strHandler supplier = dataHelper.getSupplierIndex(supp);
                    String q_supplier = supplier.getSupplierIndex();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            spinnerLoadModel(strMaterial, q_supplier);
                        }
                    }, 500);

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
            Log.d(" SupplierSpinner", "Error: " + e.toString() );
        }
    }

    //--Load supplier data to spinner with variable
    private void spinnerLoadSupplierWMatType(String strMaterial, String strMatType){
        try{
            listAdapter.notifyDataSetChanged();
            supplier = findViewById(R.id.sp_supplier);
            final String [] items = dataHelper.supplierDataWMaterial(strMaterial);
            ArrayList<String> supplierArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            supplierArr.removeAll(Collections.singleton(null));

            supplier.setAdapter(listAdapter);
            supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String supp = parent.getItemAtPosition(position).toString();
                    strHandler supplier = dataHelper.getSupplierIndex(supp);
                    String q_supplier = supplier.getSupplierIndex();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            spinnerLoadModel(strMaterial, q_supplier);
                        }
                    }, 500);

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
            Log.d(" SupplierSpinner", "Error: " + e.toString() );
        }
    }

    //--Load Class type data to spinner
    private void spinnerLoadClassTypes(String strMaterial){
        try{
            listAdapter.notifyDataSetChanged();
            materialClass = findViewById(R.id.sp_class);
            Log.d("exception_load", "Class Type Function: "+strMaterial);
            final String [] items = dataHelper.classTypeDataWMaterial(strMaterial);
            ArrayList<String> classArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            classArr.removeAll(Collections.singleton(null));

            materialClass.setAdapter(listAdapter);
            materialClass.setSelection(listAdapter.NO_SELECTION);
            materialClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    checker = 5;
                    if(checker > 1){
                        String str = parent.getItemAtPosition(position).toString();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                                spinnerLoadSection(str);
                            }
                        }, 500);

                    }

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
            Log.d("ClassSpinner", "Error: " + e.toString() );
        }
    }

    //--Load Model data to spinner
    private void spinnerLoadModel(String strMaterial, String strSupplier){
        try{
            listAdapter.notifyDataSetChanged();
            sModel = findViewById(R.id.sp_model);
            final String [] items = dataHelper.modelWMaterial(strMaterial, strSupplier);
            ArrayList<String> classArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            classArr.removeAll(Collections.singleton(null));

            sModel.setAdapter(listAdapter);
            sModel.setSelection(listAdapter.NO_SELECTION);
            sModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    checker = 5;
                    if(checker > 1){
                        String str = parent.getItemAtPosition(position).toString();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                            }
                        }, 500);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
            Log.d("ClassSpinner", "Error: " + e.toString() );
        }
    }

    //--Load Transfer type data to spinner
    private void spinnerLoadTransferTypes(){
        try{
            final String [] items = dataHelper.transferTypeData();
            ArrayList<String> transferArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            transferArr.removeAll(Collections.singleton(null));

            transferType.setAdapter(listAdapter);
            transferType.setSelection(listAdapter.NO_SELECTION);
            transferType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    checker = 5;
                    if(checker > 1){

                        String val = parent.getItemAtPosition(position).toString();
                        transferDataType(val);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(q_transType.equals("402")){ //= current value of transfer from
                                    // other section
                                    transferModeHide();

                                }
                                else{
                                    transferModeShow();

                                }
                            }
                        }, 500);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
        }
    }

    private void transferModeHide(){
        mainTitle.setText("Receive From Section Mode");
        matType.setVisibility(View.GONE);
        sMatType.setVisibility(View.GONE);
        tv1Mc.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        tv5.setVisibility(View.GONE);
        tv6.setVisibility(View.GONE);
        controlLayoutParam(100);

        material.setVisibility(View.GONE);
        supplier.setVisibility(View.GONE);
        poNo.setVisibility(View.GONE);
        materialClass.setVisibility(View.GONE);
        sModel.setVisibility(View.GONE);
        sMatCat.setVisibility(View.GONE);
        invScan.setVisibility(View.GONE);

        tv7.setVisibility(View.VISIBLE);
        reMarks.setVisibility(View.VISIBLE);
    }

    private void transferModeShow(){
        mainTitle.setText(R.string.doc_info);
        matType.setVisibility(View.VISIBLE);
        sMatType.setVisibility(View.VISIBLE);
        tv1Mc.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
        tv3.setVisibility(View.VISIBLE);
        tv4.setVisibility(View.VISIBLE);
        tv5.setVisibility(View.VISIBLE);
        tv6.setVisibility(View.VISIBLE);
        //controlLayoutParam(445);
        controlLayoutParam(345);

        material.setVisibility(View.VISIBLE);
        supplier.setVisibility(View.VISIBLE);
        poNo.setVisibility(View.VISIBLE);
        sMatCat.setVisibility(View.VISIBLE);
        invScan.setVisibility(View.VISIBLE);
        try {
            materialClass.setVisibility(View.VISIBLE);
        }catch (Exception e){

        }

        sModel.setVisibility(View.VISIBLE);

        tv7.setVisibility(View.GONE);
        reMarks.setVisibility(View.GONE);
    }

    //--Load section data to spinner
    private void spinnerLoadSection(String strClass){
        try{
            listAdapter.notifyDataSetChanged();
            section = findViewById(R.id.sp_section);
            Log.d("ClassDB", "Section Initial: " + strClass );
            final String [] items = dataHelper.sectionDataWClass(strClass);
            ArrayList<String> classArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            classArr.removeAll(Collections.singleton(null));


            section.setAdapter(listAdapter);
            section.setSelection(listAdapter.NO_SELECTION, true);
            section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fetchLoadStat.setVisibility(View.GONE);
            fetchStat.setVisibility(View.GONE);
        }
        catch(Exception e){
            Log.d("SectionSpinner", "Error: " + e.toString() );
        }
    }

    //--Load active month
    private void spinnerLoadActiveMonth(){
        try{
            final String [] items = dataHelper.monthData();
            ArrayList<String> mthArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            mthArr.removeAll(Collections.singleton(null));

            invMth.setAdapter(listAdapter);
            invMth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    {

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch(Exception e){
        }
        fetchLoadStat.setVisibility(View.GONE);
        fetchStat.setVisibility(View.GONE);
    }

    //--Load active month
    private void spinnerLoadActiveYear(){
        try{
            final String [] items = dataHelper.yearData();
            ArrayList<String> yrArr = new ArrayList<>(Arrays.asList(items));
            listAdapter= new ArrayAdapter<String>(this, R.layout.listview_singleitem, items);
            yrArr.removeAll(Collections.singleton(null));

            invYr.setAdapter(listAdapter);
            invYr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    {

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch(Exception e){
        }
        fetchLoadStat.setVisibility(View.GONE);
        fetchStat.setVisibility(View.GONE);
    }

    //======================================================================
    /**/
}