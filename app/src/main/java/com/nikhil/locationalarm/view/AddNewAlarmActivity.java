package com.nikhil.locationalarm.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nikhil.locationalarm.R;
import com.nikhil.locationalarm.utils.Constants;

public class AddNewAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_SELECT_LOCATION = 1004;
    private EditText mEditAlarmName, mEditAlarmLocation;
    private Button mBtnSelectLocation, mBtnAddAlarm;
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_alarm);

        initialiseViews();
    }

    private void initialiseViews() {
        mEditAlarmName = findViewById(R.id.editAlarmName);
        mEditAlarmLocation = findViewById(R.id.editAlarmLocation);
        mBtnSelectLocation = findViewById(R.id.btnSelectLocation);
        mBtnSelectLocation.setOnClickListener(this);
        mBtnAddAlarm = findViewById(R.id.btnAddAlarm);
        mBtnAddAlarm.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSelectLocation: {
                startActivityForResult(new Intent(this, MapsActivity.class), REQUEST_CODE_SELECT_LOCATION);
            }
            break;
            case R.id.btnAddAlarm: {
                String alarmName = mEditAlarmName.getText().toString();
                String alarmLocation = mEditAlarmLocation.getText().toString();
                if (TextUtils.isEmpty(alarmName)) {
                    Toast.makeText(this, "Please enter Alarm Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(alarmLocation)) {
                    Toast.makeText(this, "Please enter Alarm Location", Toast.LENGTH_SHORT).show();
                    return;
                }
                setResultForAlarmListActivity(alarmName);
            }
            break;
        }
    }

    private void setResultForAlarmListActivity(String alarmName) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.INTENT_KEY_ALARM_NAME, alarmName);
        resultIntent.putExtra(Constants.INTENT_KEY_LATITUDE, mLatitude);
        resultIntent.putExtra(Constants.INTENT_KEY_LONGITUDE, mLongitude);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_LOCATION && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(Constants.INTENT_KEY_LATITUDE) && data.hasExtra(Constants.INTENT_KEY_LONGITUDE)) {
                mLatitude = data.getDoubleExtra(Constants.INTENT_KEY_LATITUDE, 0);
                mLongitude = data.getDoubleExtra(Constants.INTENT_KEY_LONGITUDE, 0);
                mEditAlarmLocation.setText(mLatitude + ", " + mLongitude);
            }
        }
    }
}
