package com.example.starbuzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID="drinkId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkID=(Integer)getIntent().getExtras().get(EXTRA_DRINKID);
        SQLiteOpenHelper starbuzzDatabaseHelper=new StarbuzzDatabaseHelper(this);
        try {
            Log.i("DineshTag","DrinkActivity onCreate data query begin");
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkID)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);


                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                Log.i("DineshTag","DrinkActivity onCreate data qyery scssful");
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Log.i("DineshTag","DrinkActivity onCreate data query Database unavailable");
            Toast toast=Toast.makeText(this,
                                        "Database unavailable",
                                          Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
