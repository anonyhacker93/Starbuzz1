package com.example.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class DrinkCategoryActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        SQLiteOpenHelper starbuzzDatabaseHelper=new StarbuzzDatabaseHelper(this);
        ListView listDrinks=(ListView)findViewById(R.id.list_drinks);
        try{
            db=starbuzzDatabaseHelper.getReadableDatabase();
            cursor= db.query("DRINK",
                               new String[]{"_id","NAME"},
                                null,null,null,null,null);

            SimpleCursorAdapter listAdapter=new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listDrinks.setAdapter(listAdapter);
        }catch (SQLiteException e){
            Log.i("MyTag",""+e);
            Toast toast=Toast.makeText(this,"Database unavailable"+e,Toast.LENGTH_LONG);
            toast.show();
        }

        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listDrinks, View itemView, int position, long id)
            {
                Intent intent=new Intent(DrinkCategoryActivity.this,
                        DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID,(int)id);
                startActivity(intent);

            }
        };
        listDrinks.setOnItemClickListener(itemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
