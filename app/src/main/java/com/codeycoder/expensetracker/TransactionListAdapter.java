package com.codeycoder.expensetracker;

import static com.codeycoder.expensetracker.Utilities.dp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeycoder.expensetracker.views.Button;

import java.util.ArrayList;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_CUSTOM = 1;
    private List<Transaction> data = new ArrayList<>();

    public void setData(List<Transaction> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        switch(viewType) {
            case TYPE_CUSTOM:
                View v = new View(parent.getContext());
                v.setLayoutParams(new RecyclerView.LayoutParams(-1, dp(70)));
                root = v;
                break;
            case TYPE_ITEM:
            default:
                root = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction, parent, false);
                break;
        }
        return new RecyclerView.ViewHolder(root){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            LinearLayout v = (LinearLayout) holder.itemView;
            Button b = v.findViewById(R.id.t_icon);
            b.setPadding(dp(0), dp(0), dp(0), dp(0));
            b.setIcon(v.getContext().getDrawable(R.drawable.coins_line));
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) return TYPE_CUSTOM;

        return TYPE_ITEM;
    }

    public class TransListViewHolder extends RecyclerView.ViewHolder {
        public TransListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
