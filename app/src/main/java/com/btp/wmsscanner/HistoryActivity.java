package com.btp.wmsscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.btp.wmsscanner.ADAPTERS.historyBarcodeAdapter;
import com.btp.wmsscanner.ADAPTERS.historyDataAdapter;
import com.btp.wmsscanner.ADAPTERS.scannerDataAdapter;
import com.btp.wmsscanner.API.ApiClientInterface;
import com.btp.wmsscanner.API.ApiInterface;
import com.btp.wmsscanner.DB.dbHelper;
import com.btp.wmsscanner.MOD.AlertDialogModule;
import com.btp.wmsscanner.MOD.RecyclerClickListener;
import com.btp.wmsscanner.STR.PojoDataSource;
import com.btp.wmsscanner.STR.strBarcodeCollector;
import com.btp.wmsscanner.STR.strCustomDuplicateList;
import com.btp.wmsscanner.STR.strHandler;
import com.btp.wmsscanner.STR.strHistoryCollector;
import com.btp.wmsscanner.STR.strPostMaterialDetail;
import com.btp.wmsscanner.STR.strViewerCollector;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    private List<strHistoryCollector> dataList = new ArrayList<>();
    private List<strViewerCollector> modelList = new ArrayList<>();
    private List<strViewerCollector> barcodeList = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    public Spinner modelSelect;
    private historyDataAdapter adapter;
    private historyBarcodeAdapter barcodeAdapter;
    public String localhost, id,
            v_docNo, v_docDate, v_po, v_material, v_class, v_supplier, v_transType, v_section,
            v_rack, v_quantity, v_modelName;
    int checker = 0;
    public SwipeRefreshLayout refreshLayout;
    public EditText searchQuery;
    public TextView r_docNo, r_docDate, r_po, r_material, r_class, r_supplier, r_transType,
            r_section, r_rack, r_quantity;
    public Button r_dismiss;
    final dbHelper dataHelper = new dbHelper(HistoryActivity.this);
    final AlertDialogModule alertMod = new AlertDialogModule(HistoryActivity.this);
    EditText dataSearch;
    RecyclerView listHistoryData, listBarcodeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initializeControl();
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        getHistoryData();
        recyclerClickListeners();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
                listHistoryData.setLayoutManager(null);
                listHistoryData.setAdapter(null);
                getHistoryData();
            }
        });

        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    searchData(searchQuery.getText().toString());
                }
                else{
                    getHistoryData();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //loadData();
    }

    //--Method set for initialization of controls.
    private void initializeControl(){
        listHistoryData = findViewById(R.id.history_data_list);
        dataSearch = findViewById(R.id.history_data_search);
        refreshLayout = findViewById(R.id.swiperefresh);
        searchQuery = findViewById(R.id.history_data_search);

    }

    private void popItemViewer(String docNo, String docDate){
        DecimalFormat formatNum = new DecimalFormat("#,###.##");
        final Dialog dialog = new Dialog(HistoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_history_item);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();

        r_docNo = dialog.findViewById(R.id.hr_documentNo);
        r_docDate = dialog.findViewById(R.id.hr_documentDate);
        r_dismiss = dialog.findViewById(R.id.bt_resultDismiss);
        r_po = dialog.findViewById(R.id.hr_po);
        r_material = dialog.findViewById(R.id.hr_material);
        r_class = dialog.findViewById(R.id.hr_matClass);
        r_supplier = dialog.findViewById(R.id.hr_supplier);
        r_transType = dialog.findViewById(R.id.hr_transType);
        r_section = dialog.findViewById(R.id.hr_section);
        r_rack = dialog.findViewById(R.id.hr_rack);
        r_quantity = dialog.findViewById(R.id.hr_totalQty);
        modelSelect = dialog.findViewById(R.id.sp_model);
        listBarcodeData = dialog.findViewById(R.id.rv_resultItems);

        r_docNo.setText(docNo);
        r_docDate.setText(docDate);
        r_po.setText(v_po);
        r_material.setText(v_material);
        r_class.setText(v_class);
        r_supplier.setText(v_supplier);
        r_transType.setText(v_transType);
        r_section.setText(v_section);
        r_rack.setText(v_rack);
        Float totalQty = Float.parseFloat(v_quantity);
        r_quantity.setText(formatNum.format(totalQty));

        r_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                if(searchQuery.getText().toString().equals("")){
                    getHistoryData();
                }
                else {
                    searchData(searchQuery.getText().toString());
                }
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                dialog.cancel();
                if(searchQuery.getText().toString().equals("")){
                    getHistoryData();
                }
                else {
                    searchData(searchQuery.getText().toString());
                }
            }
        });
    }

    //--Initialize recyclerView Click Listener in first run (Important!)
    private void recyclerClickListeners(){
        listHistoryData.setItemAnimator(new DefaultItemAnimator());
        listHistoryData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        listHistoryData.addOnItemTouchListener(new RecyclerClickListener(getApplicationContext(), listHistoryData, new RecyclerClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                strHistoryCollector data = dataList.get(position);
                /*Toast.makeText(HistoryActivity.this,
                                data.getHistoDocNo() + " " + data.getHistoDocDate(),
                               Toast.LENGTH_LONG).show();*/
                v_quantity = data.getHistoTotalQty();
                getViewerHeader(data.getHistoDocNo(), data.getHistoDocDate());
                //popItemViewer(data.getHistoDocNo(), data.getHistoDocDate());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
                Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(HistoryActivity.this, "No localhost was set!" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return methodStrHost;
    }

    //-- Load Data from network
    private void loadData() {
        //dummyData();
        dataList.removeAll(Collections.singleton(null));
        adapter = new historyDataAdapter(dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listHistoryData.removeAllViews();
        listHistoryData.removeAllViewsInLayout();
        listHistoryData.setLayoutManager(layoutManager);
        listHistoryData.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    //-- Load barcode data base on selected item
    private void loadBarcodeData(){
        listBarcodeData.setItemAnimator(new DefaultItemAnimator());
        listBarcodeData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        barcodeList.removeAll(Collections.singleton(null));
        barcodeAdapter = new historyBarcodeAdapter(barcodeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listBarcodeData.removeAllViews();
        listBarcodeData.removeAllViewsInLayout();
        listBarcodeData.setLayoutManager(layoutManager);
        listBarcodeData.setAdapter(barcodeAdapter);
        barcodeAdapter.notifyDataSetChanged();
    }

    //-- Search data from network
    private void searchData(String query){
        String host = loadSettings();
        try{
            refreshLayout.setRefreshing(true);
            ApiClientInterface clientInterface = ApiInterface
                    .getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getHistoryQuery(query, query,
                                                                              "search");
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    if(response.body() != null){
                        List<PojoDataSource> historyList =  response.body();
                        dataList.clear();
                        for(int i = 0; i<historyList.size(); i++){
                            String getDocNo, getMaterial, getDocDate, getItemCount, getQty;
                            getDocNo = historyList.get(i).getDocumentNo();
                            getDocDate = historyList.get(i).getDocumentDate();
                            getMaterial = historyList.get(i).getMaterialName();
                            getItemCount = historyList.get(i).getTotalCount();
                            getQty = historyList.get(i).getQty();

                            strHistoryCollector data = new strHistoryCollector(getDocNo,
                                                                               getDocDate,
                                                                               getMaterial,getItemCount,
                                                                               getQty);
                            dataList.add(data);
                        }
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    refreshLayout.setRefreshing(false);
                    alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //--Get History Details
    private void getHistoryData(){
        String host = loadSettings();
        try{
            refreshLayout.setRefreshing(true);
            ApiClientInterface clientInterface = ApiInterface
                    .getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getHistory();
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    if(response.body() != null){
                        List<PojoDataSource> historyList =  response.body();
                        dataList.clear();
                        for(int i = 0; i<historyList.size(); i++){
                            String getDocNo, getMaterial, getDocDate, getItemCount, getQty;
                            getDocNo = historyList.get(i).getDocumentNo();
                            getDocDate = historyList.get(i).getDocumentDate();
                            getMaterial = historyList.get(i).getMaterialName();
                            getItemCount = historyList.get(i).getTotalCount();
                            getQty = historyList.get(i).getQty();

                            strHistoryCollector data = new strHistoryCollector(getDocNo,
                                                                               getDocDate,
                                                                               getMaterial,getItemCount,
                                                                               getQty);
                            dataList.add(data);
                        }
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    refreshLayout.setRefreshing(false);
                    alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //--Get History Viewer header
    private void getViewerHeader(String docNo, String docDate){
        String host = loadSettings();
        String newDate = docDate.replace("/", "-");
        final dbHelper dataHelper = new dbHelper(HistoryActivity.this);
        try{
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strPostMaterialDetail> call = clientInterface.getViewerHeader(docNo, newDate, "qHeader");
            call.enqueue(new Callback<strPostMaterialDetail>() {
                @Override
                public void onResponse(Call<strPostMaterialDetail> call, Response<strPostMaterialDetail> response) {
                    //alertMod.infoGeneral(response.body().getMessage());
                    v_docNo = response.body().getDocumentNo();
                    v_docDate = response.body().getDocumentDate();
                    v_po = response.body().getPo_no();
                    v_material = response.body().getMaterialName();
                    v_class = response.body().getClassName();
                    v_supplier = response.body().getSupplierName();
                    v_transType = response.body().getTransferName();
                    v_section = response.body().getSectionName();
                    v_rack = response.body().getRack();
                    getViewerModel(v_docNo,v_docDate);
                    popItemViewer(v_docNo,v_docDate);
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

    //--Get History Viewer Model
    private void getViewerModel(String docNo, String docDate){
        String host = loadSettings();
        String newDate = docDate.replace("/", "-");
        try{
            refreshLayout.setRefreshing(true);
            ApiClientInterface clientInterface = ApiInterface
                    .getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getViewerModel(docNo, newDate,
                                                                             "qModel");
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    modelList.clear();
                    if(response.body() != null){
                        List<PojoDataSource> historyModelList =  response.body();
                        dataList.clear();
                        for(int i = 0; i<historyModelList.size(); i++){
                            String getModel;
                            getModel = historyModelList.get(i).getModelName();

                            strViewerCollector data = new strViewerCollector(getModel);
                            modelList.add(data);
                        }
                        loadModelSpinner();
                    }
                }

                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    refreshLayout.setRefreshing(false);
                    alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //--Get History Viewer Barcode List
    private void getViewerBarcode(String docNo, String docDate, String docModel){
        String host = loadSettings();
        String newDate = docDate.replace("/", "-");
        try{
            refreshLayout.setRefreshing(true);
            ApiClientInterface clientInterface = ApiInterface
                    .getClient(host).create(ApiClientInterface.class);
            Call<List<PojoDataSource>> call = clientInterface.getViewerBarcode(docNo, newDate,
                                                                               docModel,
                                                                             "qBarcode");
            call.enqueue(new Callback<List<PojoDataSource>>() {
                @Override
                public void onResponse(Call<List<PojoDataSource>> call, Response<List<PojoDataSource>> response) {
                    barcodeList.clear();
                    if(response.body() != null){
                        List<PojoDataSource> historyBarcodeList =  response.body();
                        dataList.clear();
                        for(int i = 0; i<historyBarcodeList.size(); i++){
                            String getBarcode, getQty, getScanDate, getDevice;
                            getBarcode = historyBarcodeList.get(i).getBarcode();
                            getQty = historyBarcodeList.get(i).getQty();
                            getScanDate = historyBarcodeList.get(i).getScanDate();
                            getDevice = historyBarcodeList.get(i).getDevice();

                            strViewerCollector data = new strViewerCollector(getBarcode, getQty,
                                                                             getScanDate, getDevice);
                            barcodeList.add(data);
                            //popItemViewer(v_docNo, v_docDate);
                        }
                        loadBarcodeData();
                        //loadData();
                    }
                }

                @Override
                public void onFailure(Call<List<PojoDataSource>> call, Throwable t) {
                    refreshLayout.setRefreshing(false);
                    alertMod.errorGeneral(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    //--Load Model Spinner
    private void loadModelSpinner(){
        ArrayAdapter<strViewerCollector> modelAdapter = new ArrayAdapter<strViewerCollector>(this,
                                                                      R.layout.listview_singleitem_nopadding, modelList);
        modelSelect.setAdapter(modelAdapter);
        modelSelect.setSelection(modelAdapter.NO_SELECTION);
        modelSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checker = 5;
                if(checker > 1){
                    String str = parent.getItemAtPosition(position).toString();
                    v_modelName = str;
                    getViewerBarcode(v_docNo, v_docDate, v_modelName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //--To query indexes
    public String q_material, q_materialCat, q_matType, q_matClass, q_supplier, q_transType,
            q_section, q_model,
            q_upload_status = "N", q_secID, q_invMth, q_invYr;

    //--Query index of each details
    private void queryKeys(String materialSelected,  String matTypeSelected,
                           String matClassSelected, String supplierSelected,
                           String transTypeSelected, String sectionSelected,
                           String modelSelected, String inventoryMth, String inventoryYr,
                           String matCatSelected){
        try{
            strHandler mat = dataHelper.getMaterialIndex(materialSelected);
            q_material = mat.getMaterialIndex();
            strHandler matType = dataHelper.getMatTypeIndex(matTypeSelected);
            q_matType = matType.getMatTypeIndex();
            strHandler matClass = dataHelper.getMatClassIndex(matClassSelected);
            q_matClass = matClass.getMatClassIndex();
            strHandler supplier = dataHelper.getSupplierIndex(supplierSelected);
            q_supplier = supplier.getSupplierIndex();
            strHandler transType = dataHelper.getTransTypeIndex(transTypeSelected);
            q_transType = transType.getTransTypeIndex();
            strHandler section = dataHelper.getSectionIndex(sectionSelected);
            q_section = section.getSectionIndex();
            strHandler model = dataHelper.getModelIndex(modelSelected, q_supplier);
            q_model = model.getModelIndex();
            strHandler secID = dataHelper.getSectionTransferIndex(matClassSelected,
                                                                  sectionSelected,
                                                                  transTypeSelected);
            q_secID = secID.getSecID();
            strHandler mthId = dataHelper.getMonthIndex(inventoryMth);
            q_invMth = mthId.getInvMth();
            strHandler yrID = dataHelper.getYearIndex(inventoryYr);
            q_invYr = yrID.getInvYear();
            strHandler matCatID = dataHelper.getMaterialCategoryIndex(matCatSelected);
            q_materialCat = matCatID.getMaterialCategoryIndex();
        }
        catch (Exception e){
            alertMod.errorGeneral(e.getMessage());
        }
    }

    private void dummyData(){
        strHistoryCollector data = new strHistoryCollector("BIT-BTP-1912002","11/10/2020", "ACB", "75", "7800");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);
        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1912015","11/10/2020", "Latch Pin", "75", "28000");
        dataList.add(data);

        data = new strHistoryCollector("BIT-BTP-1911009","11/10/2020", "Magnet Wire", "10", "4632");
        dataList.add(data);


    }
}