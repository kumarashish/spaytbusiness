package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.spaytbusiness.R;
import com.spaytbusiness.TransactionDetails;

import java.util.List;

import interfaces.PayoutClickListners;
import models.OutstandingOrder;
import models.PayoutModel;
import utils.Utils;

public class PayoutAdapter  extends BaseAdapter {
    Activity activity;
    List<PayoutModel.PayoutsDetail> orderData;
    LayoutInflater inflater;
    PayoutClickListners callback;

    public PayoutAdapter(Activity activity, List<PayoutModel.PayoutsDetail> orderData) {
        this.activity = activity;
        this.orderData = orderData;
        inflater = LayoutInflater.from(activity);
        callback=(PayoutClickListners)activity;
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
        final PayoutModel.PayoutsDetail model = orderData.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.payout_row, parent, false);
            holder.orderId = (TextView) convertView.findViewById(R.id.order_id);
            holder.totalAmount = (TextView) convertView.findViewById(R.id.total_amount);
            holder.commision = (TextView) convertView.findViewById(R.id.commision);
            holder.payoutstatus = (TextView) convertView.findViewById(R.id.payout_status);
            holder.date = (TextView) convertView.findViewById(R.id.payout_date);
            holder.details = (View) convertView.findViewById(R.id.details);
            holder.details_btn = (Button) convertView.findViewById(R.id.details_btn);
            holder.delete_btn= (Button) convertView.findViewById(R.id.delete_btn);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(holder);
        holder.orderId.setText("Payout Id :" + model.getId());
        holder.totalAmount.setText("Total Amount : " + Utils.getFormattedAmount( model.getTotalAmount() )+ " €");
        holder.commision.setText("Commission Fee : " + Utils.getFormattedAmount(model.getCommissionFee()) + " €");
        holder.date.setText("Created_on : " + model.getCreatedOn());
        holder.payoutstatus.setText("Payout status : " + model.getPayoutStatus());

        if(model.getPayoutStatus().equalsIgnoreCase("Requested"))
        {
            holder.delete_btn.setVisibility(View.VISIBLE);
        }
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onDetailsClick(model.getPayoutConsumerOrders(),model.getId());
            }
        });
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            callback.onDeleteClick(model.getId());
            }
        });
        holder.details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           callback.onDetailsClick(model.getPayoutConsumerOrders(),model.getId());
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView orderId, totalAmount, commision, payoutstatus, date;
        Button delete_btn,details_btn;
        View details;
    }
}
