package com.company.weathervietnamongooglemap.ui.addressspinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.company.weathervietnamongooglemap.R;

import java.util.List;
import java.util.Map;

public class SpinnerDistrictAdapter extends ArrayAdapter<Map<String, List<LatLng>>> {

    private Map<String, List<LatLng>> mDistrict;
    private Context mContext;
    public SpinnerDistrictAdapter(@NonNull Context context, int resource, Map<String, List<LatLng>> district) {
        super(context, resource);

        mDistrict = district;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
//        View row = inflater.inflate(R.layout.iten_district, parent,
//                false);
//        TextView make = row.findViewById(R.id.text_district);
//        make.setText("tai");
//        System.out.println(mDistrict.toString());
//        System.out.println(mDistrict.keySet().toArray(new String[0])[position]);
        return rowView(convertView, position);
    }

    private class ViewHolderDistrict extends RecyclerView.ViewHolder{

        TextView textView;
        public ViewHolderDistrict(@NonNull View itemView) {
            super(itemView);
        }
    }

    private View rowView(View convertView, int position) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.iten_district,null);
        }
        ViewHolderDistrict viewHolder = new ViewHolderDistrict(convertView);
        viewHolder.textView = convertView.findViewById(R.id.text_district);
        viewHolder.textView.setText("hihi");
        System.out.println("hihi");
        //Setup Values
        return convertView;
    }
}
