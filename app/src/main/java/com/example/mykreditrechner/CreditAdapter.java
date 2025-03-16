package com.example.mykreditrechner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewHolder> {

    private Context context;
    private List<String> creditList;
    private DatabaseHelper dbHelper;

    public CreditAdapter(Context context, List<String> creditList) {
        this.context = context;
        this.creditList = creditList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_item, parent, false);
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder holder, int position) {
        String creditDetails = creditList.get(position);
        holder.tvCreditTitle.setText("Kredit #" + (position + 1)); // Titel mit Index
        holder.tvCreditDetails.setText(creditDetails); // Details anzeigen

        // Löschen-Button
        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteCredit(creditDetails); // Kredit aus der Datenbank löschen
            creditList.remove(position); // Kredit aus der Liste entfernen
            notifyItemRemoved(position); // RecyclerView aktualisieren
        });
    }

    @Override
    public int getItemCount() {
        return creditList.size();
    }

    public static class CreditViewHolder extends RecyclerView.ViewHolder {
        TextView tvCreditTitle, tvCreditDetails;
        Button btnDelete;

        public CreditViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreditTitle = itemView.findViewById(R.id.tvCreditTitle);
            tvCreditDetails = itemView.findViewById(R.id.tvCreditDetails);
            btnDelete = itemView.findViewById(R.id.btnDelete); // Löschen-Button
        }
    }
}
