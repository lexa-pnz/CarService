package com.example.carservice;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;

import static com.example.carservice.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SimpleAdapter adapter;
    DBList dbList;

    private DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listView);
        dbList = new DBList(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDataActivity();
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeDataActivity(position);
            }
        });

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        dbListM();
    }

    public void newDataActivity(){
        Intent intent = new Intent(this, NewDataActivity.class);
        startActivity(intent);
    }

    public void changeDataActivity(int position){
        Intent intent = new Intent(this, ChangeDataActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_ClearDB) {
            mDb.execSQL("DELETE FROM '"+ TABLE_NAME +"' ");
            dbListM();
        }
        else if (id == R.id.action_Exit){
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void dbListM(){
        adapter = dbList.dbList();
        listView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        dbListM();
    }
}
