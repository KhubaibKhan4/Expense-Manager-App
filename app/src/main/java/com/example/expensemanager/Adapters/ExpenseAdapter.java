package com.example.expensemanager.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Database.DatabaseHelper;
import com.example.expensemanager.DetailedExpensesActivity;
import com.example.expensemanager.Models.Expense;
import com.example.expensemanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Expense> expenseList;
    List<Expense> backup;

    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
        backup = new ArrayList<>(expenseList);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_expense, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(expenseList.get(position).getTitle());
        holder.title.setTextColor(Color.WHITE);
        holder.amount.setText(expenseList.get(position).getValue());
        holder.amount.setTextColor(Color.WHITE);
        holder.date.setText(expenseList.get(position).getDate());
        holder.date.setTextColor(Color.WHITE);

        int colorCode = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(colorCode, null));
        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedExpensesActivity.class);
                intent.putExtra("title", expenseList.get(position).getTitle());
                intent.putExtra("value", expenseList.get(position).getValue());
                intent.putExtra("date", expenseList.get(position).getDate());
                intent.putExtra("id", expenseList.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                        .setTitle("Are Your Sure!")
                        .setMessage("Do You want to Delete Expenses Data that is Available in the following Data.?\n" + expenseList.get(position).getTitle() + "\n" + expenseList.get(position).getValue())
                        .setIcon(R.drawable.baseline_delete_forever_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
                                databaseHelper.expenseDao().deleteExpense(new Expense(expenseList.get(position).getId(), expenseList.get(position).getTitle(), expenseList.get(position).getValue(), expenseList.get(position).getDate()));
                                expenseList.remove(position);
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                dialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Expense> filteredData = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredData.addAll(backup);
            } else {
                for (Expense expense : backup) {
                    if (expense.getTitle().toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredData.add(expense);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            expenseList.clear();
            expenseList.addAll((List<Expense>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, date;
        CardView notes_container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_txt);
            amount = itemView.findViewById(R.id.desc_txt);
            date = itemView.findViewById(R.id.textView_date);
            notes_container = itemView.findViewById(R.id.notes_container);
        }
    }
}
