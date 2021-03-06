package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.spaytbusiness.R;
import com.spaytbusiness.TransactionDetails;

import org.w3c.dom.Text;

import java.util.List;

import fragments.Transactions;
import models.OutstandingOrder;
import utils.Utils;

public class TransactionAdapter extends BaseAdapter  {
    Activity activity;
    List<OutstandingOrder.OrderDatum> orderData;
    LayoutInflater inflater;
    public TransactionAdapter(Activity activity, List<OutstandingOrder.OrderDatum> orderData) {
        this.activity=activity;
        this.orderData=orderData;
        inflater = LayoutInflater.from(activity);
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
        final OutstandingOrder.OrderDatum model=orderData.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.order_row, parent, false);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.amount=(TextView)convertView.findViewById(R.id.net_amount);
            holder.paymentstatus=(TextView)convertView.findViewById(R.id.payment_status);
            holder.orderstatus=(TextView)convertView.findViewById(R.id.order_status);
            holder.date=(TextView)convertView.findViewById(R.id.order_date);
            holder.details=(View)convertView.findViewById(R.id.next);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(holder);
        holder.name.setText(model.getConsumerFullname()+"\nOrderId: "+model.getId());
        holder.amount.setText("Net Amount : "+model.getNetAmount()+" €");
        holder.date.setText("Order Date : "+model.getUpdatedOn());
        holder.orderstatus.setText("Order status : "+model.getStatus());
        if(model.getPaypalTransactionId()!=null)
        {
            holder.paymentstatus.setText("Your Order Id : "+model.getId()+" has been paid.\nPaypal Id : "+model.getPaypalTransactionId());
        }else{
            holder.paymentstatus.setText("Payment : pending");

        }
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(activity, TransactionDetails.class);
                in.putExtra("orderId",model.getId());
                activity.startActivity(in);
            }
        });

        return convertView;
    }
    public class ViewHolder{
TextView name,amount,orderstatus,date,paymentstatus;
View details;
    }
}
