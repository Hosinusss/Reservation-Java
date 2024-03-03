package models;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Payment {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51OqHSwLgc390JJd8ROSmj5KdACOWpmxPfmOcdVZlZExwJv0QpN3uRS1sIIZ5ZVaTQnxkuU07xAA0SPMxbl5sVGF300HIaP2hRe";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}