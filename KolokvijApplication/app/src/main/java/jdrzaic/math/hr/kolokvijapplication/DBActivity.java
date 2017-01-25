package jdrzaic.math.hr.kolokvijapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DBActivity extends AppCompatActivity {

    TextView cookieView;
    TextView pricesView ;
    EditText almondEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        DBAdapter db = new DBAdapter(this);
        cookieView = (TextView)findViewById(R.id.cookies);
        pricesView = (TextView)findViewById(R.id.prices);
        almondEditor = (EditText)findViewById(R.id.almond);

        //---add a contact---
        /*db.open();
        long id = db.insertCookie("Kremsnita", "torta", "cokolada");
        id = db.insertCookie("BademSnita", "pita", "badem");
        id = db.insertCookie("Madarica", "torta", "cokolada");
        id = db.insertCookie("Ledeni Vjetar", "torta", "ananas");
        id = db.insertCookie("Medenjak", "keks", "med");

        id = db.insertPrice(10, 1);
        id = db.insertPrice(20, 2);
        id = db.insertPrice(20, 1);
        id = db.insertPrice(5, 3);
        id = db.insertPrice(6, 1);
        db.close();*/



        //--get all cookies---
        String cookiesContent = "";
        db.open();
        Cursor c = db.getAllCookies();
        if (c.moveToFirst())
        {
            do {
                cookiesContent += DisplayCookie(c);
            } while (c.moveToNext());
        }
        db.close();
        cookieView.setText(cookiesContent);

        //--get all prices---
        StringBuilder cookiePrices = new StringBuilder();
        db.open();
        c = db.getAllPrices();
        if (c.moveToFirst()) {
            do {
                Log.d("TAG", "printing prize");
                cookiePrices.append(DisplayPrice(c));
            } while (c.moveToNext());
        }
        db.close();
        pricesView.setText(cookiePrices.toString());


        String almondCookie = "";
        db.open();
        Cursor cu = db.getCookiesWithAlmond();
        if (cu.moveToFirst())
            almondCookie += DisplayCookieName(cu);
        else
            Toast.makeText(this, "No cookie found", Toast.LENGTH_LONG).show();
        db.close();
        almondEditor.setText(almondCookie);
    }

    public String DisplayCookieName(Cursor c) {
        return c.getString(1) + "\n";
    }
    public String DisplayCookie(Cursor c)
    {
        return "id: " + c.getString(0) + ", " +
                        "Naziv: " + c.getString(1) + ", " +
                        "Vrsta:  " + c.getString(2) + ", " +
                        "Glavni sastojak: " + c.getString(3) + "\n";
    }


    public String DisplayPrice(Cursor c)
    {
        return "id: " + c.getString(0) + ", " +
                        "Cijena: " + c.getString(1) + ", " +
                        "Id kolača: :  " + c.getString(2) + "\n";
    }

}
