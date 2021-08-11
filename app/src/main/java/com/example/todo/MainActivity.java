package com.example.todo;

import static org.apache.commons.io.FileUtils.readLines;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<String> items;

    //add a member variable from each view
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //sets layout as content of activity?

        //define what all of the member variables are; connects the object to the variable
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        //instantiate the model
        loadItems();

        //ItemsAdapter: gives rv data + decides ViewHolders
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //delete item from model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item removed!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String todoItem = etItem.getText().toString();
                //add item to the model
                items.add(todoItem);
                //notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item added!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //read data file - load items by reading every line of data file
    private void loadItems() {
        try {
            items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items.", e);
            items = new ArrayList<>();
        }
    }

    //writing data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items.", e);
        }
    }
}