package com.example.carservice;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.carservice.DatabaseHelper.TABLE_NAME;

public class NewDataActivity extends AppCompatActivity {

    Button btnSave, btnClear;
    EditText txtBrand, txtYear, txtNumber, txtVIN, txtOwner;
    String brand, year, number, vin, owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnClear = (Button)findViewById(R.id.btnClear);

        txtBrand = (EditText) findViewById(R.id.txtBrand);
        txtYear = (EditText) findViewById(R.id.txtYear);
        txtNumber = (EditText) findViewById(R.id.txtNumber);
        txtVIN = (EditText) findViewById(R.id.txtVIN);
        txtOwner = (EditText) findViewById(R.id.txtOwner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void buttonClick(View view) {

        switch (view.getId()){
            case R.id.btnSave:

                brand = txtBrand.getText().toString();
                year = txtYear.getText().toString();
                number = txtNumber.getText().toString();
                vin = txtVIN.getText().toString();
                owner = txtOwner.getText().toString();

                if (brand.equals("") || year.equals("") || number.equals("") || vin.equals("") || owner.equals("")){
                    Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        MainActivity.mDb.execSQL("INSERT INTO '"+ TABLE_NAME + "' (Brand, Year, Number, VIN, Owner) " +
                                "VALUES ('" + brand + "', '" + year + "', '" + number + "', '" + vin + "', '" + owner + "')");

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(this, "Повторите ввод", Toast.LENGTH_SHORT).show();
                        Log.d("mLog", String.valueOf(e));
                    }
                }
                break;

            case R.id.btnClear:
                txtBrand.setText("");
                txtYear.setText("");
                txtNumber.setText("");
                txtVIN.setText("");
                txtOwner.setText("");
                break;
        }
    }
}
