package com.btp.wmsscanner.STR;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class strCustomScannerList extends ArrayList<strCustomScannerList> {

    public String code;
    public Integer qty;
    private ArrayList<strCustomScannerList> dataList;

    public strCustomScannerList(String code, Integer qty){
        this.code=code;
        this.qty=qty;
    }

    /*@NonNull
    @Override
    public Stream<strCustomScannerList> stream(){
        return null;
    }*/

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
