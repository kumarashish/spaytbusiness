package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.spaytbusiness.R;

import java.util.List;

import interfaces.OnDeleteOrderClickListner;
import interfaces.PayoutClickListners;
import models.PayoutModel;
import utils.Utils;

public class PayoutOrderAdapter  extends BaseAdapter {
    Activity activity;
    List<PayoutModel.PayoutConsumerOrder> orderData;
    LayoutInflater inflater;
    OnDeleteOrderClickListner callback;

    public PayoutOrderAdapter(Activity activity, List<PayoutModel.PayoutConsumerOrder> orderData) {
        this.activity = activity;
        this.orderData = orderData;
        inflater = LayoutInflater.from(activity);
        callback=( OnDeleteOrderClickListner)activity;

    }

    @Override
    public int getCount() {
        return orderData.size();
    }

    @Override
    public Object getItem(int position) {
        return orderData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final PayoutModel.PayoutConsumerOrder model = orderData.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.payout_orders_row, parent, false);
            holder.orderId = (TextView) convertView.findViewById(R.id.order_id);
            holder.totalAmount = (TextView) convertView.findViewById(R.id.total_amount);
            holder.commision = (TextView) convertView.findViewById(R.id.commision);
            holder.paypalfess = (TextView) convertView.findViewById(R.id.paypal_fess);
            holder.date = (TextView) convertView.findViewById(R.id.payout_date);
            holder.delete_btn = (Button) convertView.findViewById(R.id.delete_btn);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(holder);
        holder.orderId.setText("Consumer Order Id :" + model.getConsumerOrderId());
        holder.totalAmount.setText("Total Amount : " + Utils.getFormattedAmount( model.getTotalAmount() )+ " €");
        holder.commision.setText("Commission Fee : " +Utils.getFormattedAmount( model.getCommissionFee() )+ " €");
        holder.date.setText("Created_on : " + model.getCreatedOn());
        holder.paypalfess.setText("Paypal Fess : " + Utils.getFormattedAmount(model.getPaypalFee()));
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onDeleteClick(model);
            }
        });


        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView orderId, totalAmount, commision, paypalfess, date;
        Button delete_btn;

    }
}