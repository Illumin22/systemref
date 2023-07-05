package com.btp.wmsscanner.API;

import com.btp.wmsscanner.STR.PojoDataSource;
import com.btp.wmsscanner.STR.strPostMaterialDetail;
import com.btp.wmsscanner.STR.strPostMaterialStock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiClientInterface {

    @GET("/BTP-WMS_API_TEST/api/customInformationBoard")
    Call<List<PojoDataSource>> getInfo1();

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Loaders/GetTable")
    Call<List<PojoDataSource>> getInfo(
            @Field("action") String action,
            @Field("sp_mode") String sp_mode
    );

    @GET("/BTP-WMS/api/Loaders/GetMaterialList")
    Call<List<PojoDataSource>> getMaterialList();

    @GET("/BTP-WMS/api/Loaders/GetSupplierList")
    Call<List<PojoDataSource>> getSupplierList();

    @GET("/BTP-WMS/api/Loaders/GetTransferList")
    Call<List<PojoDataSource>> getTransferType();

    @GET("/BTP-WMS/api/Loaders/GetClassList")
    Call<List<PojoDataSource>> getClassType();

    @GET("/BTP-WMS/api/Loaders/GetSectionList")
    Call<List<PojoDataSource>> getSections();

    @GET("/BTP-WMS/api/Loaders/GetMaterialTypeList")
    Call<List<PojoDataSource>> getMaterialType();

    @GET("/BTP-WMS/api/Loaders/GetProcessFlow")
    Call<List<PojoDataSource>> getProcessFlow();

    @GET("/BTP-WMS/api/Loaders/GetRackList")
    Call<List<PojoDataSource>> getRacks();

    @GET("/BTP-WMS/api/Loaders/GetModelList")
    Call<List<PojoDataSource>> getModel();

    @GET("/BTP-WMS/api/Loaders/GetSectionTransfer")
    Call<List<PojoDataSource>> getSectionTransfer();

    @GET("/BTP-WMS/api/Loaders/GetMaterialCategoryList")
    Call<List<PojoDataSource>> getMaterialCategory();

    @GET("/BTP-WMS/api/Loaders/GetActiveMonthYear")
    Call<List<PojoDataSource>> getActiveMonthYear();

    @GET("/BTP-WMS/api/Loaders/GetActiveMonthNumber")
    Call<PojoDataSource> getActiveMonthNumber();

    @GET("/BTP-WMS/api/Loaders/GetMonth")
    Call<List<PojoDataSource>> getMonth();

    @GET("/BTP-WMS/api/Loaders/GetYear")
    Call<List<PojoDataSource>> getYear();

    @GET("/BTP-WMS/api/Loaders/GetHistoryList")
    Call<List<PojoDataSource>> getHistory();

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetHistorySearch")
    Call<List<PojoDataSource>> getHistoryQuery(
            @Field("documentNo") String docNo,
            @Field("material") String material,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetBarcodeData")
    Call<strPostMaterialDetail> getBarcodee(
            @Field("barcode") String barcode,
            @Field("sp_mode") String sp_mode,
            @Field("action") String action
            );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetBarcodeInDetail")
    Call<strPostMaterialDetail> getReceiveDetails(
            @Field("barcode") String barcode,
            @Field("sectionIDFK") String sectionIDFK,
            @Field("transferIDFK") String transferIDFK,
            @Field("sp_mode") String sp_mode,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetBarcodeParam")
    Call<strPostMaterialDetail> getBarcodeParam(
            @Field("barcode") String barcode,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Loaders/GetComplete")
    Call<PojoDataSource> getEmpStat(
            @Field("search") String empID,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Loaders/GetTable")
    Call<List<PojoDataSource>> getTable(
            @Field("action") String action,
            @Field("sp_mode") String sp_mode
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/getDuplicateList")
    Call<List<PojoDataSource>> getBarcode(
            @Field("barcode") String barcode
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetHistoryHeader")
    Call<strPostMaterialDetail> getViewerHeader(
            @Field("documentNo") String docNo,
            @Field("documentDate") String docDate,
            @Field("action") String action
            );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetHistoryModel")
    Call<List<PojoDataSource>> getViewerModel(
            @Field("documentNo") String docNo,
            @Field("documentDate") String docDate,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetHistoryBarcode")
    Call<List<PojoDataSource>> getViewerBarcode(
            @Field("documentNo") String docNo,
            @Field("documentDate") String docDate,
            @Field("modelName") String model,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Query/GetTransactionID")
    Call<strPostMaterialDetail> getTransactionID(
            @Field("sessionID") String sessionID,
            @Field("barcode") String barcode,
            @Field("documentNo") String docNo
    );

    @FormUrlEncoded
    @POST("/BTP-WMS/api/Encoder/PostQtyUpdate")
    Call<strPostMaterialDetail> updateBarcodeQty(
            @Field("sessionID") String sessionID,
            @Field("barcode") String barcode,
            @Field("qty") String qty,
            @Field("documentNo") String docNo,
            @Field("sectionIDFK") String section,
            @Field("transferIDFK") String transfer,
            @Field("sp_mode") String sp_mode
    );

    @POST("/BTP-WMS/api/Encoder/PostTransactionHeader")
    Call<strPostMaterialDetail> sendDetailHeader(@Body strPostMaterialDetail postMaterialDetail);

    @POST("/BTP-WMS/api/Encoder/PostTransactionDetails")
    Call<strPostMaterialDetail> sendDataDetails(@Body strPostMaterialDetail postMaterialDetail);

    @POST("/BTP-WMS/api/Encoder/PostUpdateTransactionDetails")
    Call<strPostMaterialDetail> sendDataUpdateDetails(@Body strPostMaterialDetail postMaterialDetail);

    @POST("/BTP-WMS/api/Encoder/PostTransactionMarkers")
    Call<strPostMaterialDetail> sendDataMarkers(@Body strPostMaterialDetail postMaterialDetail);

    @POST("/BTP-WMS/api/Encoder/PostStockHeader")
    Call<strPostMaterialStock> sendDataStockCardHead(@Body strPostMaterialStock postMaterialStock);

    @POST("/BTP-WMS/api/Encoder/PostStockDetails")
    Call<strPostMaterialStock> sendDataStockCardDetail(@Body strPostMaterialStock postMaterialStock);

    @POST("/BTP-WMS/api/Encoder/PostUpdateStockHeader")
    Call<strPostMaterialStock> sendDataStockCardHeadUpdate(@Body strPostMaterialStock postMaterialStock);

    @POST("/BTP-WMS/api/Encoder/PostUpdateStockDetails")
    Call<strPostMaterialStock> sendDataStockCardDetailUpdate(@Body strPostMaterialStock postMaterialStock);
}