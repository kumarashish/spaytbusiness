package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spaytbusiness.R;

import java.util.ArrayList;

import interfaces.BusinessProductClicked;
import models.BusinessProductModel;
import models.UserProfile;

public class BusinessProductAdapter extends BaseAdapter {
    ArrayList<BusinessProductModel> list;
    Activity act;
    LayoutInflater inflater;
    BusinessProductClicked callback;

    public BusinessProductAdapter(ArrayList<BusinessProductModel> list, Activity act,BusinessProductClicked callback) {
        this.list = list;
        this.act = act;
        inflater = LayoutInflater.from(act);
        this.callback=callback;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final BusinessProductModel model = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.business_user_row, parent, false);
            holder.description = (TextView) convertView.findViewById(R.id.email);
            holder.productname = (TextView) convertView.findViewById(R.id.name);
            holder.price = (TextView) convertView.findViewById(R.id.role);
            holder.next = (View) convertView.findViewById(R.id.next);
            holder.delete=(ImageView) convertView.findViewById(R.id.delete);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.delete.setVisibility(View.VISIBLE);
        holder.productname.setText(model.getName());
        holder.description.setText(model.getDescription());
        if (Double.parseDouble(model.getTotal_price()) > 0.0) {
            holder.price.setText(model.getTotal_price() + " € ");
        } else if (Double.parseDouble(model.getPrice_per_liter()) > 0.0) {
            holder.price.setText(model.getPrice_per_liter() + " € / liter");
        } else if (Double.parseDouble(model.getParking_fee_per_hour()) > 0.0) {
            holder.price.setText(model.getParking_fee_per_hour() + " € / hour");
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeleteCLicked(model);
            }
        });
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onProductSelected(model);
            }
        });
        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder {
        TextView productname, description, price;
        ImageView delete;
        View next;
    }
}