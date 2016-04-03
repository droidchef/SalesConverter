package in.ishankhanna.salesconverter.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import in.ishankhanna.salesconverter.R;
import in.ishankhanna.salesconverter.data.model.Transaction;

/**
 * Created by ishan on 03/04/16.
 */
public class TransactionListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Transaction> transactions;
    private DecimalFormat df;
    public TransactionListAdapter(Context context, List<Transaction> listOfTransactions) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.transactions = listOfTransactions;
        df = new DecimalFormat("#.00");
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null ){
            convertView = layoutInflater.inflate(R.layout.item_transaction, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
            viewHolder.tvValueInGBP = (TextView) convertView.findViewById(R.id.tv_value_in_gbp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Transaction transaction = getItem(position);
        if (transaction != null) {
            viewHolder.tvValue.setText(context.getResources().getString(R.string.value, transaction.getCurrency(), df.format(transaction.getAmount())));
            viewHolder.tvValueInGBP.setText(context.getResources().getString(R.string.value_in_gbp, df.format(transaction.getAmountInGBP())));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvValue, tvValueInGBP;
    }
}
