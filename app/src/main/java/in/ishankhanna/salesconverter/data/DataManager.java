package in.ishankhanna.salesconverter.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.ishankhanna.salesconverter.data.model.CurrencyRateGraph;
import in.ishankhanna.salesconverter.data.model.Rate;
import in.ishankhanna.salesconverter.data.model.RateEdge;
import in.ishankhanna.salesconverter.data.model.Transaction;

/**
 * Created by ishan on 03/04/16.
 */
public class DataManager {

    private static DataManager instance = null;

    private Context context;

    /**
     * This set holds the name of the products.
     */
    List<String> productNames;
    /**
     * This HashMap holds a mapping of Product names to the count of its
     * transactions.
     */
    HashMap<String,Integer> productTransactionCountMap;
    /**
     * This list contains all the transactions parsed from the data source.
     */
    List<Transaction> transactions;

    /**
     * This list contains all the rates.
     */
    List<Rate> rates;

    /**
     * Graph between the exchange rates.
     */
    CurrencyRateGraph graph;

    /**
     * A Map to quickly get exchange rate between two currencies,
     * even if they don't have a direct exchange rate.
     */
    HashMap<String, Double> rateCache;

    /**
     * Total value of transactions in GBP for a particular SKU.
     * It is updated when the method
     * #getFilteredListOfTransactionsForProductWithSku is called.
     */
    private double totalInGBP = 0;

    public static DataManager getInstance(Context context) {

        if (instance == null) {
            instance = new DataManager(context);
        }

        return instance;
    }

    public DataManager(Context context) {
        this.context = context;
    }

    private String readJsonFile(String fileName) {

        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
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

    public boolean requestDataFromSource() {

        Gson gson = new Gson();

        transactions = new ArrayList<>();
        rates = new ArrayList<>();
        productTransactionCountMap = new HashMap<>();

        Type collectionTypeTransaction = new TypeToken<Collection<Transaction>>(){}.getType();
        transactions = gson.fromJson(readJsonFile("transactions.json"), collectionTypeTransaction);
        Type collectionTypeRate = new TypeToken<Collection<Rate>>(){}.getType();
        rates = gson.fromJson(readJsonFile("rates.json"), collectionTypeRate);

        if (transactions != null && rates != null ) {
            Set<String> productNameSet = new HashSet<>();
            for(Transaction t : transactions) {
                String productName = t.getSku();
                productNameSet.add(t.getSku());
                Integer count = productTransactionCountMap.get(productName);
                if (count == null) {
                    productTransactionCountMap.put(productName, 1);
                } else {
                    productTransactionCountMap.put(productName, count + 1);
                }
            }
            productNames = new ArrayList<>(productNameSet);

            Set<String> currencyNames = new HashSet<>();

            for (Rate r : rates) {
                currencyNames.add(r.getFrom());
                currencyNames.add(r.getTo());
            }

            graph = new CurrencyRateGraph();

            for (String cur : currencyNames) {
                graph.addVertex(cur);
            }

            for (Rate r : rates) {
                graph.addEdge(r.getFrom(), r.getTo(), r.getRate());
            }

            GraphIterator<String, RateEdge> iterator = new DepthFirstIterator<>(graph.getGraph());
            MyListener ml = new MyListener(graph);
            iterator.addTraversalListener(ml);
            while (iterator.hasNext()) {
                iterator.next();
            }
            rateCache = ml.getRateCache();

            return true;

        } else {
            return false;
        }

    }


    public List<String> getProductNames() {
        return productNames;
    }

    public HashMap<String, Integer> getProductTransactionCountMap() {
        return productTransactionCountMap;
    }

    public List<Transaction> getFilteredListOfTransactionsForProductWithSku(String productSku) {

        totalInGBP = 0;

        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (t.getSku().equals(productSku)) {

                if (!t.getCurrency().equals("GBP")) {
                    String pair = t.getCurrency()+ "->" + "GBP";
                    t.setAmountInGBP(t.getAmount() * rateCache.get(pair));
                } else {
                    t.setAmountInGBP(t.getAmount());
                }

                totalInGBP += t.getAmountInGBP();

                filteredTransactions.add(t);
            }
        }

        return filteredTransactions;
    }


     static class MyListener extends TraversalListenerAdapter<String, RateEdge> {

        CurrencyRateGraph g;
        private boolean newComponent;
        private String reference;
        private HashMap<String,Double> rateCache;

        public MyListener(CurrencyRateGraph g) {
            this.g = g;
            rateCache = new HashMap<String,Double>();

        }

        @Override
        public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
            newComponent = true;
        }

        @Override
        public void vertexTraversed(VertexTraversalEvent<String> e) {
            String vertex = e.getVertex();

            if (newComponent) {
                reference = vertex;
                newComponent = false;
            }
            List<RateEdge> edges = DijkstraShortestPath.findPathBetween(g.getGraph(), reference, vertex);
            double finalRate = 1;
            for (int i=0; i<edges.size(); i++) {
                finalRate *= edges.get(i).getWeight();
            }
            System.out.println( reference + "->" + vertex + ":" + finalRate);
            rateCache.put(reference + "->" + vertex, finalRate);
            rateCache.put(vertex + "->" + reference, 1/finalRate);
        }

        public HashMap<String, Double> getRateCache() {
            return rateCache;
        }
    }

    public double getTotalAmountInGBPForAProduct() {
        if (totalInGBP != 0) {
            return totalInGBP;
        } else {
            throw new RuntimeException("Must call #getFilteredListOfTransactionsForProductWithSku first");
        }
    }
}
