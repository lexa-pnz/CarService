package com.example.carservice;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.carservice.DatabaseHelper.TABLE_NAME;
import static com.example.carservice.MainActivity.mDb;

class DBList {

    Context mContext;

    DBList(Context context){
        mContext = context;
    }

    SimpleAdapter dbList(){
        ArrayList<HashMap<String, Object>> clients = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> client;

        Cursor cursor = mDb.rawQuery("SELECT * FROM ('" + TABLE_NAME + "')", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            client = new HashMap<String, Object>();

            client.put("brand",  cursor.getString(1));
            client.put("year",  cursor.getString(2));
            client.put("number",  cursor.getString(3));
            client.put("vin",  cursor.getString(4));
            client.put("owner",  cursor.getString(5));

            clients.add(client);
            cursor.moveToNext();
        }
        cursor.close();

        String[] from = { "brand", "year", "number", "vin", "owner"};
        int[] to = { R.id.brandView, R.id.yearView, R.id.numberView, R.id.vinView, R.id.ownerView};

        SimpleAdapter adapter = new SimpleAdapter(mContext, clients, R.layout.adapter_item, from, to);

        return adapter;
    }
}
