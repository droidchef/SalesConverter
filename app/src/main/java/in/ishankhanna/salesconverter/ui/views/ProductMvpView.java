package in.ishankhanna.salesconverter.ui.views;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ishan on 03/04/16.
 */
public interface ProductMvpView extends MvpView {

    void showProducts(List<String> productNames,
                      HashMap<String, Integer> productCountMap);

    void showError(String message);
}
