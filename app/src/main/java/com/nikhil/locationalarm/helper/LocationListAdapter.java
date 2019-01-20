package com.nikhil.locationalarm.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nikhil.locationalarm.R;
import com.nikhil.locationalarm.model.AlarmItem;
import com.nikhil.locationalarm.model.LocationHistoryItem;
import com.nikhil.locationalarm.model.LocationModel;

import java.util.ArrayList;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationViewHolder> {

    private ArrayList<LocationHistoryItem> mLocationHistoryItems;

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView mLocationIdView;
        TextView mLatitudeView;
        TextView mLongitudeView;
        LocationViewHolder(View view) {
            super(view);
            mLocationIdView = view.findViewById(R.id.tvLocationId);
            mLatitudeView = view.findViewById(R.id.tvLatitude);
            mLongitudeView = view.findViewById(R.id.tvLongitude);
        }
    }

    public LocationListAdapter(ArrayList<LocationHistoryItem> locationHistoryItems) {
        this.mLocationHistoryItems = locationHistoryItems;
    }

    @Override
    public LocationListAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_history_item, parent, false);

        LocationViewHolder viewHolder = new LocationViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {

        LocationHistoryItem historyItem = mLocationHistoryItems.get(position);
        holder.mLocationIdView.setText("" + historyItem.getLocationId());
        holder.mLatitudeView.setText(historyItem.getLatitude());
        holder.mLongitudeView.setText(historyItem.getLongitude());

    }

    @Override
    public int getItemCount() {
        return mLocationHistoryItems.size();
    }
}
