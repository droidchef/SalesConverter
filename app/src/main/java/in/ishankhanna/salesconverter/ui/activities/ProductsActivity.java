package in.ishankhanna.salesconverter.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

import in.ishankhanna.salesconverter.R;
import in.ishankhanna.salesconverter.ui.adapters.ProductListAdapter;
import in.ishankhanna.salesconverter.ui.presenters.ProductPresenter;
import in.ishankhanna.salesconverter.ui.views.ProductMvpView;

public class ProductsActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, ProductMvpView {

    ListView lvProducts;

    ProductListAdapter productListAdapter;

    ProductPresenter productPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        productPresenter = new ProductPresenter(this);
        productPresenter.attachView(this);
        lvProducts = (ListView) findViewById(R.id.lv_products);
        productPresenter.presentProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String productSku = productPresenter.getProductName(position);
        Intent transactionActivityIntent = new Intent(ProductsActivity.this, TransactionsActivity.class);
        transactionActivityIntent.putExtra(TransactionsActivity.PRODUCT_SKU, productSku);
        startActivity(transactionActivityIntent);
    }

    @Override
    public void showProducts(List<String> productNames,
                             HashMap<String, Integer> productTransactionCountMap) {
        productListAdapter = new ProductListAdapter(this, productNames,
                productTransactionCountMap);
        lvProducts.setAdapter(productListAdapter);
        lvProducts.setOnItemClickListener(this);
    }

    @Override
    public void showError(String message) {
        toast(message);
    }
}
