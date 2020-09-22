//package com.taimar198.weatherongooglemap.data.model;
//
//import android.app.Person;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.BitmapDescriptor;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.maps.android.clustering.Cluster;
//import com.google.maps.android.clustering.ClusterManager;
//import com.google.maps.android.clustering.view.DefaultClusterRenderer;
//import com.google.maps.android.ui.IconGenerator;
//import com.taimar198.weatherongooglemap.R;
//import com.taimar198.weatherongooglemap.data.api.response.WeatherResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WeatherRenderer extends DefaultClusterRenderer<WeatherResponse> {
//
//    private final IconGenerator mIconGenerator;
//    private final IconGenerator mClusterIconGenerator;
//    private final ImageView mImageView;
//    private final ImageView mClusterImageView;
//    private final int mDimension;
//    private Context mContext;
//
//    public WeatherRenderer(Context context, GoogleMap googleMap, ClusterManager clusterManager) {
//        super(context, googleMap, clusterManager);
//        mContext = context;
//        mIconGenerator = new IconGenerator(mContext);
//        mClusterIconGenerator = new IconGenerator(mContext);
//
//        View multiProfile = mContext.getLayoutInflater().inflate(R.layout.multi_profile, null);
//        mClusterIconGenerator.setContentView(multiProfile);
//        mClusterImageView = multiProfile.findViewById(R.id.image);
//
//        mImageView = new ImageView(mContext);
//        mDimension = (int) mContext.getResources().getDimension(R.dimen.custom_profile_image);
//        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
//        int padding = (int) mContext.getResources().getDimension(R.dimen.custom_profile_padding);
//        mImageView.setPadding(padding, padding, padding, padding);
//        mIconGenerator.setContentView(mImageView);
//    }
//
//    @Override
//    protected void onBeforeClusterItemRendered(@NonNull Person person, MarkerOptions markerOptions) {
//        // Draw a single person - show their profile photo and set the info window to show their name
//        markerOptions
//                .icon(getItemIcon(person))
//                .title(person.name);
//    }
//
//    @Override
//    protected void onClusterItemUpdated(@NonNull Person person, Marker marker) {
//        // Same implementation as onBeforeClusterItemRendered() (to update cached markers)
//        marker.setIcon(getItemIcon(person));
//        marker.setTitle(person.name);
//    }
//
//    /**
//     * Get a descriptor for a single person (i.e., a marker outside a cluster) from their
//     * profile photo to be used for a marker icon
//     *
//     * @param person person to return an BitmapDescriptor for
//     * @return the person's profile photo as a BitmapDescriptor
//     */
//    private BitmapDescriptor getItemIcon(Person person) {
//        mImageView.setImageResource(person.profilePhoto);
//        Bitmap icon = mIconGenerator.makeIcon();
//        return BitmapDescriptorFactory.fromBitmap(icon);
//    }
//
//    @Override
//    protected void onBeforeClusterRendered(@NonNull Cluster<WeatherResponse> cluster, MarkerOptions markerOptions) {
//        // Draw multiple people.
//        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
//        markerOptions.icon(getClusterIcon(cluster));
//    }
//
//    @Override
//    protected void onClusterUpdated(@NonNull Cluster<WeatherResponse> cluster, Marker marker) {
//        // Same implementation as onBeforeClusterRendered() (to update cached markers)
//        marker.setIcon(getClusterIcon(cluster));
//    }
//
//    /**
//     * Get a descriptor for multiple people (a cluster) to be used for a marker icon. Note: this
//     * method runs on the UI thread. Don't spend too much time in here (like in this example).
//     *
//     * @param cluster cluster to draw a BitmapDescriptor for
//     * @return a BitmapDescriptor representing a cluster
//     */
//    private BitmapDescriptor getClusterIcon(Cluster<Person> cluster) {
//        List<Drawable> profilePhotos = new ArrayList<>(Math.min(4, cluster.getSize()));
//        int width = mDimension;
//        int height = mDimension;
//
//        for (Person p : cluster.getItems()) {
//            // Draw 4 at most.
//            if (profilePhotos.size() == 4) break;
//            Drawable drawable = mContext.getResources().getDrawable(p.profilePhoto);
//            drawable.setBounds(0, 0, width, height);
//            profilePhotos.add(drawable);
//        }
//        MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
//        multiDrawable.setBounds(0, 0, width, height);
//
//        mClusterImageView.setImageDrawable(multiDrawable);
//        Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
//        return BitmapDescriptorFactory.fromBitmap(icon);
//    }
//
//    @Override
//    protected boolean shouldRenderAsCluster(Cluster cluster) {
//        // Always render clusters.
//        return cluster.getSize() > 1;
//    }
//}
