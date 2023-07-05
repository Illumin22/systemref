package com.btp.wmsscanner.ADAPTERS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btp.wmsscanner.R;
import com.btp.wmsscanner.STR.strHistoryCollector;

import java.text.DecimalFormat;
import java.util.List;

public class historyDataAdapter extends RecyclerView.Adapter<historyDataAdapter.DataViewHolder> {
    private List<strHistoryCollector> historyDataList;

    public void refreshView(int position){
        notifyItemChanged(position);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView historyDocno, historyDocDate, historyMaterial, historyTotalQty, historyItemCount;
        public DataViewHolder(View itemView) {
            super(itemView);
            historyDocno = itemView.findViewById(R.id.duplicate_barcode);
            historyDocDate = itemView.findViewById(R.id.duplicate_docDate);
            historyMaterial = itemView.findViewById(R.id.duplicate_docNo);
            historyTotalQty = itemView.findViewById(R.id.duplicate_location);
            historyItemCount = itemView.findViewById(R.id.history_items);
        }
    }

    public historyDataAdapter(List<strHistoryCollector> historyDataList){
        this.historyDataList=historyDataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_history_item, parent, false);

        return new DataViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DecimalFormat formatNum = new DecimalFormat("#,###.##");
        strHistoryCollector collector = historyDataList.get(position);
        holder.historyDocno.setText(collector.getHistoDocNo());
        holder.historyDocDate.setText(collector.getHistoDocDate());
        holder.historyMaterial.setText(collector.getHistoMaterial());
        Float itemCount = Float.parseFloat(collector.getHistoItemCount());
        Float totalQty = Float.parseFloat(collector.getHistoTotalQty());
        holder.historyItemCount.setText("Items: "+formatNum.format(itemCount));
        holder.historyTotalQty.setText("Total Qty: "+formatNum.format(totalQty));
    }

    @Override
    public int getItemCount() {
        return historyDataList.size();
    }
}
