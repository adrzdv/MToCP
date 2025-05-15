package com.adrzdv.mtocp.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.ui.model.ViolationDto;

import java.util.List;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {

    private List<ViolationDto> violations;

    public ViolationAdapter(List<ViolationDto> violations) {
        this.violations = violations;
    }

    public static class ViolationViewHolder extends RecyclerView.ViewHolder {
        TextView violationCode, violationName;

        public ViolationViewHolder(@NonNull View itemView) {
            super(itemView);
            violationCode = itemView.findViewById(R.id.violation_code);
            violationName = itemView.findViewById(R.id.violation_name);
        }
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.violation_item_without_button, parent, false);
        return new ViolationAdapter.ViolationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        ViolationDto violation = violations.get(position);
        holder.violationCode.setText(String.valueOf(violation.getCode()));
        holder.violationName.setText(violation.getName());
    }

    @Override
    public int getItemCount() {
        return violations.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ViolationDto> newViolationList) {
        this.violations = newViolationList;
        notifyDataSetChanged();
    }
}
