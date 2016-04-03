package in.ishankhanna.salesconverter.ui.presenters;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import in.ishankhanna.salesconverter.data.DataManager;
import in.ishankhanna.salesconverter.ui.views.ProductMvpView;

/**
 * Created by ishan on 03/04/16.
 */
public class ProductPresenter extends BasePresenter<ProductMvpView>{

    DataManager dataManager;

    public ProductPresenter(Context context) {
        dataManager = DataManager.getInstance(context);
    }

    public void presentProducts() {

        if (dataManager.requestDataFromSource()) {
            List<String> productNames = dataManager.getProductNames();
            HashMap<String, Integer> productTransactionCountMap = dataManager.getProductTransactionCountMap();
            getMvpView().showProducts(productNames, productTransactionCountMap);
        } else {
            getMvpView().showError("There was some problem fetching/parsing the data!");
        }

    }

    public String getProductName(int position) {
        return dataManager.getProductNames().get(position);
    }
}
