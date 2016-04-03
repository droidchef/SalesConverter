package in.ishankhanna.salesconverter.ui.presenters;

import in.ishankhanna.salesconverter.ui.views.MvpView;

/**
 * Created by ishan on 03/04/16.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
