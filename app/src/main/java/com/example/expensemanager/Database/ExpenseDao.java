package com.example.expensemanager.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemanager.Models.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Query("select * from Expense")
    List<Expense> getAllExpenses();

    @Insert
    void addExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);
}
