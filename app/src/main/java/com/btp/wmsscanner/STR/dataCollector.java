package com.btp.wmsscanner.STR;

public class dataCollector {

    public int id;
    public int idSettings;
    public String rawLocalHost;
    public String supplierDataList;
    public String materialDataList;

    //Material raw data
    public String materialDataID;
    public String materialDataName;
    public String materialDataClassCode;
    public String materialMatCatIDFK;
    public String materialDataIDFK;//FK

    //Material Type raw data
    public String materialTypeID;
    public String materialTypeName;

    //Supplier raw data
    public String supplierDataID;
    public String supplierDataName;
    public String supplierDataCode;
    public String supplierDataMaterialID; //FK
    public String supplierDataMaterialTypeID;

    //Class Type raw data
    public String classDataID;
    public String classDataName;
    public String classDataCode;
    public String classDataMaterialID; //FK

    //Transfer type raw data
    public String transferDataID;
    public String transferDataName;
    public String transferDataCode;

    //Section raw data
    public String sectionDataID;
    public String sectionDataName;
    public String sectionDataCode;
    public String sectionDataClassID;//FK

    //Rack raw data
    public String rackDataID;
    public String rackDataCode;
    public String rackDataName;

    //Process flow raw data
    public String processDataCode;
    public String processDataIsLastProcess;
    public String processDataSectionID; //FK
    public String processDataMaterialID; //FK
    public String processDataClassID; //FK

    //Model raw data
    public String modelDataID;
    public String modelDataName;
    public String modelDataCode;
    public String modelDataMaterialID;//FK
    public String modelDataSupplierID;//FK
    public String modelDataMcID;

    //Section Transfer raw data
    public String stSecID;
    public String stClassIDFK;
    public String stSectionIDFK;
    public String stTransferIDFK;
    public String stDirection;
    public String stSequence;

    //Material category raw data
    public String catDataID;
    public String catDataName;
    public String catDataMatIDFK;

    //Month raw data
    public String dataIMthID;
    public String dataIMth;

    //Year raw Data
    public String dataIYrID;
    public String dataIYr;

    //Active Month/Year raw data
    public String dataActMth;
    public String dataActYr;
    public String dataActStat;

    //Employee Status
    public String dataEmpID;

    //Code Definition
    public String dataBcID;
    public String dataDelimiter;
    public String dataMatID;
    public String dataMcID;
    public String dataModPos;
    public String dataModLen;
    public String dataQtyPos;
    public String dataQtyLen;
    public String dataPoPos;
    public String dataPoLen;
    public String dataInvPos;
    public String dataInvLen;
    public String dataTotalLen;

    public String dataPartPos;
    public String dataPartLen;
    public String dataLotPos;
    public String dataLotLen;

    public String dataReqDelimiter;
    public String dataReqModel;
    public String dataReqQty;
    public String dataReqPo;
    public String dataReqInvoice;
    public String dataReqPart;
    public String dataReqLot;

    public dataCollector(){

    }

}