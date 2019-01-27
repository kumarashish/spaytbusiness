package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spaytbusiness.Add_Location;
import com.spaytbusiness.Add_Products;
import com.spaytbusiness.R;

public class Products extends Fragment {
    ImageView addproducts;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.products, container, false);
        addproducts=(ImageView)v.findViewById(R.id.add);
        addproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Add_Products.class));
            }
        });
        return v;
    }
}