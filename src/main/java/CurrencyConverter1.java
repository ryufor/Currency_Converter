import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter1 {
    public static void main(String[] args) throws IOException {
        HashMap<Integer, String> currencyCodes = new HashMap<>();

        // Add currency codes
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "JPY");
        currencyCodes.put(3, "RUB");
        currencyCodes.put(4, "EUR");
        currencyCodes.put(5, "CAD");
        currencyCodes.put(6, "HKD");
        currencyCodes.put(7, "INR");

        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to currency converter!");

        System.out.println("Currency converting FROM");
        System.out.println("1:USD \t 2:JPY \t 3:RUB \t 4:EUR \t 5:CAD \t 6:HKD \t 7:INR");
        fromCode = currencyCodes.get(sc.nextInt());

        System.out.println("Currency converting TO");
        System.out.println("1:USD \t 2:JPY \t 3:RUB \t 4:EUR \t 5:CAD \t 6:HKD \t 7:INR");
        toCode = currencyCodes.get(sc.nextInt());

        System.out.println("Amount you wish to convert?");
        amount = sc.nextDouble();

        sendHttpGETRequest(fromCode, toCode, amount);

        System.out.println("Thank you for using the currency converter");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        String GET_URL = "https://api.currencyapi.com/v3/latest?apikey=cur_live_bwphj48gEDGfnSZl5N3jL5smnjNWzCTQDpzZlWIn&base_currency=" + fromCode + "&currencies=" + toCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());

            Double exchangeRate = obj.getJSONObject("data").getJSONObject(toCode).getDouble("value");

            System.out.printf("%.2f %s = %.2f %s%n", amount, fromCode, amount * exchangeRate, toCode);
        } else {
            System.out.println("GET request failed");
        }
    }
}