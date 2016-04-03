package in.ishankhanna.salesconverter.ui.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import in.ishankhanna.salesconverter.R;
import in.ishankhanna.salesconverter.data.model.Transaction;
import in.ishankhanna.salesconverter.ui.adapters.TransactionListAdapter;
import in.ishankhanna.salesconverter.ui.presenters.TransactionPresenter;
import in.ishankhanna.salesconverter.ui.views.TransactionMvpView;

public class TransactionsActivity extends BaseActivity implements TransactionMvpView {

    public static final String PRODUCT_SKU = "PRODUCT_SKU";

    private ListView lvTransactions;
    private TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        String productSku = getIntent().getStringExtra(PRODUCT_SKU);
        setTitle(getString(R.string.title_transactions, productSku));
        lvTransactions = (ListView) findViewById(R.id.lv_transactions);
        tvTotalAmount = (TextView) findViewById(R.id.tv_total_value_in_gbp);
        TransactionPresenter transactionPresenter = new TransactionPresenter(this);
        transactionPresenter.attachView(this);
        transactionPresenter.presentTransactionsForProduct(productSku);
    }

    @Override
    public void showTransactions(List<Transaction> transactions) {
        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(this, transactions);
        lvTransactions.setAdapter(transactionListAdapter);
    }

    @Override
    public void showTotalAmountInGBP(String totalAmountInGBP) {
        tvTotalAmount.setText(getString(R.string.total_value_in_gbp, totalAmountInGBP));
    }
}
