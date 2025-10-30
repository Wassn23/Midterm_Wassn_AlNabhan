package com.wassn.midterm;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // declare ui componets
    EditText edtNumber;
    Button btnGenerate, btnHistory;
    ListView listView;

    ArrayList<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link java with elements
        edtNumber = findViewById(R.id.edtNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnHistory = findViewById(R.id.btnHistory);
        listView = findViewById(R.id.listView);

        //make multiplaction table
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtNumber.getText().toString();
                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                int number = Integer.parseInt(input);
                ArrayList<String> tableList = new ArrayList<>();
                for (int i = 1; i <= 12; i++) {
                    tableList.add(number + " x " + i + " = " + (number * i));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, tableList);
                listView.setAdapter(adapter);

                historyList.add("Table of " + number);
            }
        });
        // delete item
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);

            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Item")
                    .setMessage("Do you want to delete \"" + selectedItem + "\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ((ArrayAdapter) parent.getAdapter()).remove(selectedItem);
                        ((ArrayAdapter) parent.getAdapter()).notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Deleted: " + selectedItem, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (historyList.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No history yet", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, historyList);
                listView.setAdapter(adapter);
            }
        });
    }
}
