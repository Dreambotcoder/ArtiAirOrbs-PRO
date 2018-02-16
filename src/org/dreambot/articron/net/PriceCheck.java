package org.dreambot.articron.net;

import org.dreambot.articron.net.json.Json;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class PriceCheck {

    private static HashMap<Integer, Integer> priceMap;
    private static HashMap<String, Integer> conversionMap;

    private static final String CONVERSION_URL = "https://dinhware.org/media/items.csv";
    private static final String PRICE_URL = "http://api.rsbuddy.com/grandExchange?a=guidePrice&i=";

    static {
        priceMap = new HashMap<>();
        conversionMap = new HashMap<>();
        loadConversions();
    }

    public static String formatLikeExchange(int price) {
        String priceString = Integer.toString(price);
        int size = priceString.length();
        StringBuilder builder = new StringBuilder(priceString);
        if (size < 4)
            return priceString;
        for (int i = size - 1; i >= 0; i -= 4) {
            builder.insert(size - i, ",");
        }
        return builder.toString();
    }

    private static void loadConversions() {
        try {
            URL url = new URL(CONVERSION_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String in;
            while ((in = reader.readLine()) != null) {
                String[] subs = in.split(",");
                if (subs.length >= 2) {
                    String itemName = subs[1];
                    int itemId = Integer.parseInt(subs[0]);
                    if (!conversionMap.containsKey(itemName)) {
                        conversionMap.put(itemName, itemId);
                        //System.out.println("Loaded " + itemName + ": " + subs[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
    }

    public static int getFreshPrice(int id) {
        return readWebPrice(id);
    }

    public static int getFreshPrice(String itemName) {
        return readWebPrice(getId(itemName));
    }

    public static int getPrice(int id) {
        if (priceMap.containsKey(id)) {
            return priceMap.get(id);
        }
        return readWebPrice(id);
    }

    public static int getPrice(String itemName) {
        return getPrice(getId(itemName));
    }

    private static int readWebPrice(int id) {
        try {
            URL url = new URL(PRICE_URL + id);
            String json = readQuick(url.openConnection().getInputStream());
            int price = Json.parse(json).asObject().get("selling").asInt();
            priceMap.put(id, price);
            return price;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static int getId(String itemName) {
        return conversionMap.get(itemName);
    }

    private static String readQuick(InputStream stream) throws IOException {
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }
    }
}
