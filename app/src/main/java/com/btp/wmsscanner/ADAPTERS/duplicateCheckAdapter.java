package com.btp.wmsscanner.ADAPTERS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btp.wmsscanner.R;
import com.btp.wmsscanner.STR.strCustomDuplicateList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class duplicateCheckAdapter extends RecyclerView.Adapter<duplicateCheckAdapter.DataViewHolder> {
    private List<strCustomDuplicateList> dataList;

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView barcodeData, docNoData, docDateData, currentSectionData;
        public DataViewHolder(View itemView) {
            super(itemView);
            barcodeData = itemView.findViewById(R.id.duplicate_barcode);
            docNoData = itemView.findViewById(R.id.duplicate_docNo);
            docDateData = itemView.findViewById(R.id.duplicate_docDate);
            currentSectionData = itemView.findViewById(R.id.duplicate_location);
        }
    }
    public duplicateCheckAdapter(List<strCustomDuplicateList> dataList){
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public duplicateCheckAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType) {
        View viewItem =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_duplicate_item, parent, false);

        return new DataViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull duplicateCheckAdapter.DataViewHolder holder,
                                 int position) {
        strCustomDuplicateList collector = dataList.get(position);
        holder.barcodeData.setText(collector.getBarcode());
        holder.docNoData.setText("Document No: "+collector.getDocumentNo());
        holder.docDateData.setText("Document Date: "+ collector.getDocumentDate());
        holder.currentSectionData.setText("Current Location: " + collector.getCurrentSection());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
