package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.*;

import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.checkBoxWhippedCream);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.checkBoxChoco);
        boolean hasChoco = chocolate.isChecked();
        EditText hasName = (EditText) findViewById(R.id.nameField);
        String customName = hasName.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChoco);
        //Log.v("MainActivity", "The price is " + price); //you can delete this after testing it works

        String priceMessage = createOrderSummary(customName, price, quantity, hasWhippedCream, hasChoco);
        //calculatePrice(quantity, 5);
        //latter is the price of one coffee
        //if (!(price == 0)) {
        //    priceMessage = priceMessage + "\nThank you!";
            //displayMessage(priceMessage);
            //displayMessage(createOrderSummary(customName, price, quantity, hasWhippedCream, hasChoco));
        //}
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + customName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
      //  displayMessage(priceMessage);

    }

    public String createOrderSummary(String ordererName, int price, int quantity, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage= "Name: " + ordererName;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nAdd whipped cream? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nTotal: $" + price;

        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public int calculatePrice(boolean addWhippedCream, boolean addChoco) {
        int basePrice = 5;

        if (addWhippedCream){
            basePrice=basePrice+1;
        }
        if (addChoco){
            basePrice=basePrice+1;
        }

        int price = quantity * basePrice;
        return price;
    }

    /**
     * This method displays the given text on the screen.
     */
   /* private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    } */

    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 0) {
            Toast.makeText(this, "You can't order less than 0 coffees", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity--;
            displayQuantity(quantity);
        }
    }

    public void reset(View view) {
        quantity = 0;
        displayQuantity(quantity);

    }
}