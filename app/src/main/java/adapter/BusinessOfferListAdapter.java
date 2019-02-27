package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spaytbusiness.BusinessProductDetails;
import com.spaytbusiness.R;

import java.util.ArrayList;

import interfaces.BusinessOfferClicked;
import interfaces.OnListItemSelected;
import models.Business_Offers;
import models.Business_locations;

public class BusinessOfferListAdapter  extends BaseAdapter {
    ArrayList<Business_Offers> list;
    Activity act;
    LayoutInflater inflater;
    BusinessOfferClicked callback;

    public BusinessOfferListAdapter(ArrayList<Business_Offers> list, Activity act, BusinessOfferClicked callback) {
        this.list = list;
        this.act = act;
        inflater = LayoutInflater.from(act);
        this.callback = callback;
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
        final Business_Offers model = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.business_user_row, parent, false);
            holder.from_to = (TextView) convertView.findViewById(R.id.email);
            holder.offerName = (TextView) convertView.findViewById(R.id.name);
            holder.offerDescription = (TextView) convertView.findViewById(R.id.role);
            holder.next = (View) convertView.findViewById(R.id.next);
            holder.delete = (ImageView) convertView.findViewById(R.id.delete);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.offerName.setText(model.getOffer_name());
        holder.offerDescription.setText(model.getOffer_description());
        holder.from_to.setText(model.getFrom_date()+" - "+model.getTo_date());
        holder.delete.setVisibility(View.VISIBLE);
        try {
            BusinessProductDetails activity = (BusinessProductDetails) act;

        } catch (Exception ex) {
            ex.fillInStackTrace();
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
                callback.onOfferSelected(model);
            }
        });
        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder {
        TextView offerName, from_to, offerDescription;
        ImageView delete;
        View next;
    }
}