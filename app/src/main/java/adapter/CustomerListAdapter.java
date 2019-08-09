package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spaytbusiness.R;

import org.w3c.dom.Text;

import java.util.List;

import models.ExistingCustomerModel;
import models.UserProfile;

public class CustomerListAdapter extends BaseAdapter {
    List<ExistingCustomerModel.ExistingConsumer> list;
    Activity act;
    LayoutInflater inflater;
    public CustomerListAdapter(Activity act, List<ExistingCustomerModel.ExistingConsumer> list)
    {
        this.list=list;
        this.act=act;
        inflater = LayoutInflater.from(act);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup viewGroup) {
        ViewHolder holder;
        final ExistingCustomerModel.ExistingConsumer model = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.customer_row, viewGroup, false);
            holder.name=(TextView)convertView.findViewById(R.id.name);
        }else{
           holder=(ViewHolder)convertView.getTag();
        }
        holder.name.setText(model.getFirstName()+" "+model.getLastName());
        convertView.setTag(holder);
            return convertView;
        }

    public class ViewHolder{
        TextView name;
    }
}
