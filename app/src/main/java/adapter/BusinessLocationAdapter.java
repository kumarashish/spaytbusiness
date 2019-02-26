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

import interfaces.OnListItemSelected;
import models.Business_locations;
import models.UserProfile;

public class BusinessLocationAdapter extends BaseAdapter {
    ArrayList<Business_locations> list;
    Activity act;
    LayoutInflater inflater;
    OnListItemSelected callback;

    public BusinessLocationAdapter(ArrayList<Business_locations> list, Activity act,OnListItemSelected callback) {
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
        final Business_locations model = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.business_user_row, parent, false);
            holder.address = (TextView) convertView.findViewById(R.id.email);
            holder.locationname = (TextView) convertView.findViewById(R.id.name);
            holder.description = (TextView) convertView.findViewById(R.id.role);
            holder.next = (View) convertView.findViewById(R.id.next);
            holder.delete=(ImageView) convertView.findViewById(R.id.delete);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.locationname.setText(model.getLocation_name());
        holder.address.setText(model.getStreet_name() + "," + model.getCity() + "," + model.getDoor_no());
        holder.description.setText(model.getDescription());
        holder.delete.setVisibility(View.VISIBLE);
        try{
            BusinessProductDetails activity=(BusinessProductDetails)act;
            holder.delete.setVisibility(View.GONE);
        }catch (Exception ex)
        {
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
             callback.onBusinessLocationSlected(model);
            }
        });
        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder {
        TextView locationname, address, description;
        ImageView delete;
        View next;
    }
}