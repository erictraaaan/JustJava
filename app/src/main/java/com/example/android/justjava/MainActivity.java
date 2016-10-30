/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p/>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

//import com.example.android.justjava.R;


/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int quantity = 1;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayMessage(createOrderSummary(calculatePrice()));

    }

    public int calculatePrice() {
        int basePrice = 5;

        // Add 1 if user wants whipped cream
        if (hasWhippedCream())
            basePrice += 1;

        // Add 2 if user wants chocolate
        if (hasChocolate())
            basePrice += 2;

        return basePrice * quantity;
    }

    public String createOrderSummary(int price) {
        String order =  "Name: " + getName()
                + "\nAdd whipped cream? " + hasWhippedCream()
                + "\nAdd chocolate? " + hasChocolate()
                + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";
        String subject = "Just Java order for " + getName();

        sendOrder(order,subject);
        return order;
    }

    public void increment(View view) {
        quantity++;
        if (quantity > 100) {
            quantity = 100;
            Toast tooMany = tooManyCups();
            tooMany.show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity--;
        if (quantity < 1) {
            quantity = 1;
            Toast tooLittle = tooLittleCups();
            tooLittle.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderTextView.setText(message);
    }

    private boolean hasWhippedCream() {
        CheckBox check = (CheckBox) findViewById(R.id.whipped_cream);
        return check.isChecked();
    }

    private boolean hasChocolate() {
        CheckBox check = (CheckBox) findViewById(R.id.chocolate);
        return check.isChecked();
    }

    private String getName() {
        EditText editText = (EditText) findViewById(R.id.name_text);
        return editText.getText().toString();
    }

    public Toast tooLittleCups() {
        Context context = getApplicationContext();
        CharSequence text = "You cannot order less than 1 cup of coffee";
        int duration = Toast.LENGTH_SHORT;
        return (Toast.makeText(context, text, duration));
    }

    public Toast tooManyCups(){
        Context context = getApplicationContext();
        CharSequence text = "You cannot order more than 100 cups of coffee";
        int duration = Toast.LENGTH_SHORT;
        return (Toast.makeText(context, text, duration));
    }

    public void sendOrder (String text, String subject){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

}