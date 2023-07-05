package com.btp.wmsscanner.ADAPTERS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btp.wmsscanner.R;
import com.btp.wmsscanner.STR.strViewerCollector;

import java.text.DecimalFormat;
import java.util.List;

public class historyBarcodeAdapter extends RecyclerView.Adapter<historyBarcodeAdapter.DataViewHolder>{
    private List<strViewerCollector> barcodeDataList;

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView viewerBarcode, viewerQty, viewerScanDate, viewerDevice;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            viewerBarcode = itemView.findViewById(R.id.barcode_data);
            viewerQty = itemView.findViewById(R.id.barcode_qty);
            viewerScanDate = itemView.findViewById(R.id.barcode_date);
            viewerDevice = itemView.findViewById(R.id.barcode_device);
        }
    }

    public historyBarcodeAdapter(List<strViewerCollector> barcodeDataList){
        this.barcodeDataList=barcodeDataList;
    }

    @NonNull
    @Override
    public historyBarcodeAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_history_barcode, parent, false);
        return new DataViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull historyBarcodeAdapter.DataViewHolder holder,
                                 int position) {
        DecimalFormat formatNum = new DecimalFormat("#,###.##");
        strViewerCollector collector = barcodeDataList.get(position);
        holder.viewerBarcode.setText(collector.getBarcode());
        Float newQty = Float.parseFloat(collector.getQty());
        holder.viewerQty.setText("Qty: "+ formatNum.format(newQty));
        holder.viewerDevice.setText("Scanner: "+ collector.getDevice());
        holder.viewerScanDate.setText("Scan Date: "+ collector.getScanDate());

    }

    @Override
    public int getItemCount() {
        return barcodeDataList.size();
    }


}