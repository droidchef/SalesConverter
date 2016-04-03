package in.ishankhanna.salesconverter.ui.views;

import java.util.List;

import in.ishankhanna.salesconverter.data.model.Transaction;

/**
 * Created by ishan on 03/04/16.
 */
public interface TransactionMvpView extends MvpView {

    void showTransactions(List<Transaction> transactions);
    void showTotalAmountInGBP(String totalAmountInGBP);
}
