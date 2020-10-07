package com.company.weathervietnamongooglemap.data.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.company.weathervietnamongooglemap.R;
import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;
import com.company.weathervietnamongooglemap.utls.Methods;

import java.util.ArrayList;
import java.util.List;
/**https://github.com/googlemaps/android-maps-utils/blob/f84cf5ee80f662b072ddee03601ab050afc2b1e8/demo/src/main/java/com/google/maps/android/utils/demo/CustomMarkerClusteringDemoActivity.java#L57*/
public class WeatherRenderer extends DefaultClusterRenderer<WeatherForecastResponse> {

    private final IconGenerator mIconGenerator;
    private final IconGenerator mIconWeather;
    private final IconGenerator mClusterIconGenerator;
    private final ImageView mImageView;
    private final ImageView mClusterImageView;
    private final int mDimension;
    private Context mContext;
    private GoogleMap mMap;
    private TextView mTempText;
    /**https://stackoverflow.com/questions/39699348/google-map-clustering-in-android*/
    public WeatherRenderer(Context context, GoogleMap googleMap, ClusterManager clusterManager) {
        super(context, googleMap, clusterManager);
        mContext = context;
        mMap = googleMap;
        mIconGenerator = new IconGenerator(mContext);
        mClusterIconGenerator = new IconGenerator(mContext);
        mIconWeather = new IconGenerator(mContext);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View multiProfile = inflater.inflate(R.layout.multi_profile, null);
        mClusterIconGenerator.setContentView(multiProfile);
        mClusterIconGenerator.setBackground(null);
        mIconGenerator.setBackground(null);
        mClusterImageView = multiProfile.findViewById(R.id.image);
        mTempText = multiProfile.findViewById(R.id.temp_marker);
        mImageView = new ImageView(mContext);
        mDimension = (int) mContext.getResources().getDimension(R.dimen.custom_profile_image);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = (int) mContext.getResources().getDimension(R.dimen.custom_profile_padding);
      //  mImageView.setPadding(padding, padding, padding, padding);
        ((ViewGroup)mTempText.getParent()).removeView(mTempText);
        mIconGenerator.setContentView(mImageView);
//        mIconWeather.setContentView(mImageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull WeatherForecastResponse weatherForecastResponse, MarkerOptions markerOptions) {
        // Draw a single person - show their profile photo and set the info window to show their name
        markerOptions
                .icon(getItemIcon(weatherForecastResponse));
    }

    @Override
    protected void onClusterItemUpdated(@NonNull WeatherForecastResponse weatherForecastResponse, Marker marker) {
        // Same implementation as onBeforeClusterItemRendered() (to update cached markers)
        marker.setIcon(getItemIcon(weatherForecastResponse));
    }

    /**
     * Get a descriptor for a single person (i.e., a marker outside a cluster) from their
     * profile photo to be used for a marker icon
     *
     * @return the person's profile photo as a BitmapDescriptor
     */
    @SuppressLint("SetTextI18n")
    private BitmapDescriptor getItemIcon(WeatherForecastResponse weatherForecastResponse) {
        mImageView.setImageResource(Methods.getDrawable(weatherForecastResponse.getCurrentWeather().getWeathers().get(0).getIcon(),
                                                        mContext));
        mTempText.setText(Double.toString((weatherForecastResponse.getCurrentWeather().getTemp())));
        Bitmap icon = mIconGenerator.makeIcon();
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    @Override
    protected void onBeforeClusterRendered(@NonNull Cluster<WeatherForecastResponse> cluster, MarkerOptions markerOptions) {
        // Draw multiple people.
        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
        markerOptions.icon(getClusterIcon(cluster));
    }

    @Override
    protected void onClusterItemRendered(@NonNull WeatherForecastResponse clusterItem, @NonNull Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
        getMarker(clusterItem).setSnippet(clusterItem.getAddress());
        marker.setTitle(clusterItem.getTitle());
        marker.setSnippet(clusterItem.getAddress());
        getMarker(clusterItem).showInfoWindow();
    }

    @Override
    protected void onClusterUpdated(@NonNull Cluster<WeatherForecastResponse> cluster, Marker marker) {
        // Same implementation as onBeforeClusterRendered() (to update cached markers)
        marker.setIcon(getClusterIcon(cluster));
    }

    /**
     * Get a descriptor for multiple people (a cluster) to be used for a marker icon. Note: this
     * method runs on the UI thread. Don't spend too much time in here (like in this example).
     *
     * @param cluster cluster to draw a BitmapDescriptor for
     * @return a BitmapDescriptor representing a cluster
     */
    private BitmapDescriptor getClusterIcon(Cluster<WeatherForecastResponse> cluster) {
        List<Drawable> profilePhotos = new ArrayList<>(Math.min(4, cluster.getSize()));
        int width = mDimension;
        int height = mDimension;
        System.out.println(cluster.getSize());
        for (WeatherForecastResponse p : cluster.getItems()) {
            // Draw 4 at most.
            if (profilePhotos.size() == 4) break;
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_01d);
            drawable.setBounds(0, 0, width, height);
            profilePhotos.add(drawable);
        }
        MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
        multiDrawable.setBounds(0, 0, width, height);

        mClusterImageView.setImageDrawable(multiDrawable);
        System.out.println(cluster.getItems().iterator().next().getCurrentWeather().getTemp());
        mTempText.setText(Double.toString(cluster.getItems().iterator().next().getCurrentWeather().getTemp()));
        Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        // Always render clusters.
        return cluster.getSize() > 1;
    }

}
