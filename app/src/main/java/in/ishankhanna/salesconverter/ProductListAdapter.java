package in.ishankhanna.salesconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ishan on 03/04/16.
 */
public class ProductListAdapter extends BaseAdapter {
    private List<String> productNames;
    private Map<String,Integer> productTransactionCountMap;
    private LayoutInflater layoutInflater;
    public ProductListAdapter(Context context, Set<String> productNames, Map<String,Integer> productTransactionCountMap) {
        layoutInflater = LayoutInflater.from(context);
        this.productNames = new ArrayList<>(productNames);
        this.productTransactionCountMap = productTransactionCountMap;
    }

    @Override
    public int getCount() {
        return productNames.size();
    }

    @Override
    public String getItem(int position) {
        return productNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null ){
            convertView = layoutInflater.inflate(R.layout.item_product, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvProductName = (TextView) convertView.findViewById(R.id.tv_productName);
            viewHolder.tvTransactions = (TextView) convertView.findViewById(R.id.tv_number_of_transactions);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String productName = getItem(position);
        if (productName != null) {
            int countOfTransactions = productTransactionCountMap.get(productName);
            viewHolder.tvProductName.setText(productName);
            viewHolder.tvTransactions.setText(countOfTransactions + " transactions");
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvProductName, tvTransactions;
    }
}
