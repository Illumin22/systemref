package com.btp.wmsscanner.ADAPTERS;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btp.wmsscanner.R;
import com.btp.wmsscanner.STR.strBarcodeCollector;
import com.btp.wmsscanner.STR.strCustomScannerList;

import java.util.List;

public class scannerDataAdapter extends RecyclerView.Adapter<scannerDataAdapter.DataViewHolder> {
    private List<strCustomScannerList> barcodeDataList;

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView barcodeData, totalQty;
        public DataViewHolder(View itemView) {
            super(itemView);
            barcodeData = itemView.findViewById(R.id.barcode_data);
            totalQty = itemView.findViewById(R.id.barcode_qty);
        }
    }

    public scannerDataAdapter(List<strCustomScannerList> barcodeDataList){
        this.barcodeDataList = barcodeDataList;
    }

    @NonNull
    @Override
    public scannerDataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_scanner_item, parent, false);

        return new DataViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull scannerDataAdapter.DataViewHolder holder, int position) {
        strCustomScannerList collector = barcodeDataList.get(position);
        holder.barcodeData.setText(collector.getCode());
        int val = Integer.parseInt(collector.getQty().toString());
        holder.totalQty.setText("Qty: "+ collector.getQty().toString());
    }

    @Override
    public int getItemCount() {
        return barcodeDataList.size();
    }
}
