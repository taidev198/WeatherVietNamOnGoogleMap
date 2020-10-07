package com.company.weathervietnamongooglemap.ui.addressspinner;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;
import com.company.weathervietnamongooglemap.R;

import java.util.List;
import java.util.Map;

public class SpinnerProvinceListener implements AdapterView.OnItemSelectedListener {
    private Map<String, Map<String, List<LatLng>>> mProvince;
    private Activity mContext;
    private Spinner mSpinnerDistrict;

    public SpinnerProvinceListener(Activity context, Map<String, Map<String, List<LatLng>>> province) {
        mProvince = province;
        mContext = context;
        mSpinnerDistrict = context.findViewById(R.id.spinner_district);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
    //create spinner district here?
        System.out.println(mProvince.get(adapterView.getItemAtPosition(pos)).keySet().toString());
        ArrayAdapter aa = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,
                mProvince.get(adapterView.getItemAtPosition(pos)).keySet()
                .toArray(new String[mProvince.get(adapterView.getItemAtPosition(pos)).keySet().size()]));
        mSpinnerDistrict.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) mContext);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        mSpinnerDistrict.setAdapter(aa);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
