package in.ishankhanna.salesconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import in.ishankhanna.salesconverter.data.Transaction;

public class ProductsActivity extends AppCompatActivity {

    /**
     * This set holds the name of the products.
     */
    Set<String> productNames;
    /**
     * This HashMap holds a mapping of Product names to the count of its
     * transactions.
     */
    Map<String,Integer> productTransactionCountMap;
    /**
     * This list contains all the transactions parsed from the data source.
     */
    List<Transaction> transactions;

    ListView lvProducts;
    ProductListAdapter productListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        transactions = new ArrayList<>();
        productNames = new HashSet<>();
        productTransactionCountMap = new HashMap<>();

        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Transaction>>(){}.getType();
        transactions = gson.fromJson(readJsonFile("transactions.json"), collectionType);

        if (transactions != null) {
            for(Transaction t : transactions) {
                String productName = t.getSku();
                productNames.add(t.getSku());

                Integer count = productTransactionCountMap.get(productName);
                if (count == null) {
                    productTransactionCountMap.put(productName, 1);
                } else {
                    productTransactionCountMap.put(productName, count + 1);
                }
            }
        }

        lvProducts = (ListView) findViewById(R.id.lv_products);
        productListAdapter = new ProductListAdapter(this, productNames, productTransactionCountMap);
        lvProducts.setAdapter(productListAdapter);
    }

    public String readJsonFile(String fileName) {

        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
