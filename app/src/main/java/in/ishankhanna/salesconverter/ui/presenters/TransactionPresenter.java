package in.ishankhanna.salesconverter.ui.presenters;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.List;

import in.ishankhanna.salesconverter.data.DataManager;
import in.ishankhanna.salesconverter.data.model.Transaction;
import in.ishankhanna.salesconverter.ui.views.TransactionMvpView;

/**
 * Created by ishan on 03/04/16.
 */
public class TransactionPresenter extends BasePresenter<TransactionMvpView> {

    DataManager dataManager;

    public TransactionPresenter(Context context) {
        dataManager = DataManager.getInstance(context);
    }

    public void presentTransactionsForProduct(String productSku) {

        List<Transaction> transactionList =
                dataManager
                    .getFilteredListOfTransactionsForProductWithSku(productSku);
        getMvpView().showTransactions(transactionList);
        DecimalFormat df = new DecimalFormat("#.##");
        getMvpView().showTotalAmountInGBP(df.format(dataManager.getTotalAmountInGBPForAProduct()));
    }
}
