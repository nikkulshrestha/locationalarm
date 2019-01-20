package com.nikhil.locationalarm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.nikhil.locationalarm.R;
import com.nikhil.locationalarm.helper.AlarmListAdapter;
import com.nikhil.locationalarm.helper.ILocationPermissionSuccessListener;
import com.nikhil.locationalarm.helper.LocationHelper;
import com.nikhil.locationalarm.helper.SharedPreferenceManager;
import com.nikhil.locationalarm.model.AlarmItem;
import com.nikhil.locationalarm.model.LocationModel;
import com.nikhil.locationalarm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AlarmListActivity extends AppCompatActivity implements ILocationPermissionSuccessListener, AlarmListAdapter.IAlarmStateChangeListener {

    public static final String TAG = AlarmListActivity.class.getSimpleName();
    private static final int REQUEST_CODE_ADD_ALARM = 1002;
    private LocationHelper mLocationHelper;
    private RecyclerView mAlarmListView;
    private LinearLayoutManager mAlarmListLayoutManager;
    private AlarmListAdapter mAlarmListAdapter;
    private ArrayList<AlarmItem> mAlarmItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        mLocationHelper = new LocationHelper();
        mLocationHelper.init(this);

        initialiseAlarmList();
        initialiseAddNewAlarmButton();
        initialiseShowHistoryButton();
    }

    private void initialiseShowHistoryButton() {
        Button showLocationHistoryButton = findViewById(R.id.btnShowLocationHistory);
        showLocationHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlarmListActivity.this, LocationHistoryActivity.class));
            }
        });
    }

    private void initialiseAlarmList() {
        mAlarmListView = findViewById(R.id.alarmList);


        mAlarmListView.setHasFixedSize(true);

        mAlarmListLayoutManager = new LinearLayoutManager(this);
        mAlarmListView.setLayoutManager(mAlarmListLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mAlarmListView.getContext(),
                mAlarmListLayoutManager.getOrientation());
        mAlarmListView.addItemDecoration(dividerItemDecoration);

        mAlarmItems = SharedPreferenceManager.getAlarmList(this);

        mAlarmListAdapter = new AlarmListAdapter(mAlarmItems, this);
        mAlarmListView.setAdapter(mAlarmListAdapter);
    }

    private void initialiseAddNewAlarmButton() {
        Button addNewAlarmButton = findViewById(R.id.btnAddNewAlarm);
        addNewAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AlarmListActivity.this,
                        AddNewAlarmActivity.class), REQUEST_CODE_ADD_ALARM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ALARM && resultCode == RESULT_OK && data != null
                && data.hasExtra(Constants.INTENT_KEY_ALARM_NAME)
                && data.hasExtra(Constants.INTENT_KEY_LATITUDE)
                && data.hasExtra(Constants.INTENT_KEY_LONGITUDE)) {

            String alarmName = data.getStringExtra(Constants.INTENT_KEY_ALARM_NAME);
            double latitude = data.getDoubleExtra(Constants.INTENT_KEY_LATITUDE, 0);
            double longitude = data.getDoubleExtra(Constants.INTENT_KEY_LONGITUDE, 0);
            mAlarmItems.add(new AlarmItem(alarmName, true, new LocationModel(latitude,
                    longitude)));
            SharedPreferenceManager.saveAlarmListIntoPreference(this, mAlarmItems);
            mAlarmListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationHelper.onActivityResume(this);
    }

    @Override
    public void onLocationPermissionSuccess() {
        mLocationHelper.onLocationPermissionSuccess(this);
    }

    @Override
    public void onAlarmStateChange(int position, boolean isActive) {
        mAlarmItems.get(position).setActive(isActive);
        SharedPreferenceManager.saveAlarmListIntoPreference(this, mAlarmItems);
    }
}
