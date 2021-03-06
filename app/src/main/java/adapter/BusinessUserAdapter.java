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

import interfaces.BusinessUserClicked;
import models.UserProfile;

public class BusinessUserAdapter extends BaseAdapter {
    ArrayList<UserProfile>list;
    Activity act;
    LayoutInflater inflater;
    BusinessUserClicked callback;
    public BusinessUserAdapter(ArrayList<UserProfile>list, Activity act,BusinessUserClicked callback)
    {
        this.list=list;
        this.act=act;
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
        final UserProfile model=list.get(position);
        if(convertView==null)
        {
            holder=new ViewHolder();

            convertView = inflater.inflate(R.layout.business_user_row, parent, false);
            holder.email=(TextView)convertView.findViewById(R.id.email);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.role=(TextView)convertView.findViewById(R.id.role);
            holder.next=(View) convertView.findViewById(R.id.next);
            holder.delete=(ImageView) convertView.findViewById(R.id.delete);
        }else{holder=(ViewHolder) convertView.getTag();
        }
        holder.name.setText(model.getFirst_name()+" "+model.getLast_name());
        holder.email.setText(model.getEmail());
        holder.role.setText(model.getRole());
        holder.delete.setVisibility(View.GONE);
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         callback.onUserSelected(model);
            }
        });
        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder{
        TextView name,email,role;
        ImageView delete;
        View next;
    }
}
