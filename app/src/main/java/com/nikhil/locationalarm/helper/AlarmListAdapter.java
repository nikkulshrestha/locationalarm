package com.nikhil.locationalarm.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.nikhil.locationalarm.R;
import com.nikhil.locationalarm.model.AlarmItem;
import com.nikhil.locationalarm.model.LocationModel;

import java.util.List;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder> {

    private List<AlarmItem> mAlarmItems;
    private IAlarmStateChangeListener mAlarmStateChangeListener;

    public interface IAlarmStateChangeListener {
        void onAlarmStateChange(int position, boolean isActive);
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView mAlarmNameView;
        TextView mAlarmLocationView;
        Switch mIsActiveAlarmSwitch;
        AlarmViewHolder(View view) {
            super(view);
            mAlarmNameView = view.findViewById(R.id.alarmName);
            mAlarmLocationView = view.findViewById(R.id.alarmLocation);
            mIsActiveAlarmSwitch = view.findViewById(R.id.switchIsActive);
        }
    }

    public AlarmListAdapter(List<AlarmItem> alarmItems, IAlarmStateChangeListener alarmStateChangeListener) {
        mAlarmItems = alarmItems;
        mAlarmStateChangeListener = alarmStateChangeListener;
    }

    @Override
    public AlarmListAdapter.AlarmViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_list_item, parent, false);

        AlarmViewHolder viewHolder = new AlarmViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {

        AlarmItem alarmItem = mAlarmItems.get(position);
        holder.mAlarmNameView.setText(alarmItem.getAlarmName());

        LocationModel triggerLocation = alarmItem.getTriggerLocation();
        holder.mAlarmLocationView.setText(triggerLocation.getLatitude() + ", " + triggerLocation.getLongitude());

        holder.mIsActiveAlarmSwitch.setChecked(alarmItem.isActive());
        holder.mIsActiveAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarmStateChangeListener.onAlarmStateChange(position, isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAlarmItems.size();
    }
}
