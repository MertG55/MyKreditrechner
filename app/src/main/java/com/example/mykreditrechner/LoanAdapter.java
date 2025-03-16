package com.example.mykreditrechner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LoanAdapter extends ArrayAdapter<Loan> {

    private Context context;
    private List<Loan> loans;

    public LoanAdapter(Context context, List<Loan> loans) {
        super(context, R.layout.loan_item, loans);
        this.context = context;
        this.loans = loans;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.loan_item, parent, false);
        }

        Loan loan = loans.get(position);

        TextView loanAmount = convertView.findViewById(R.id.loan_amount);
        TextView loanTerm = convertView.findViewById(R.id.loan_term);
        TextView loanInterestRate = convertView.findViewById(R.id.loan_interest_rate);

        loanAmount.setText("Betrag: " + loan.getAmount() + " €");
        loanTerm.setText("Laufzeit: " + loan.getTerm() + " Jahre");
        loanInterestRate.setText("Zinsen: " + loan.getInterestRate() + " %");

        return convertView;
    }
}
