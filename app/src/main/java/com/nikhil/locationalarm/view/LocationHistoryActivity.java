package com.nikhil.locationalarm.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nikhil.locationalarm.R;
import com.nikhil.locationalarm.helper.LocationListAdapter;
import com.nikhil.locationalarm.model.LocationHistoryItem;
import com.nikhil.locationalarm.model.LocationHistoryResponse;
import com.nikhil.locationalarm.model.NetworkModel;
import com.nikhil.locationalarm.network.NetworkRequest;
import com.nikhil.locationalarm.utils.AppCache;
import com.nikhil.locationalarm.utils.AppUtility;
import com.nikhil.locationalarm.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocationHistoryActivity extends AppCompatActivity {

    private RecyclerView mLocationListView;
    private ProgressBar mLoadingProgress;
    private ArrayList<LocationHistoryItem> mLocationHistoryItems;
    private LocationListAdapter mLocationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);

        if (AppUtility.isNetworkAvailable(this)) {
            if (AppCache.getCache().getUserInfo() != null) {
                initialiseLocationList();
            } else {
                Toast.makeText(this, "Session expire, please login again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please connect to internet!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialiseLocationList() {
        mLocationListView = findViewById(R.id.locationList);


        mLocationListView.setHasFixedSize(true);

        LinearLayoutManager mLocationListLayoutManager = new LinearLayoutManager(this);
        mLocationListView.setLayoutManager(mLocationListLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mLocationListView.getContext(),
                mLocationListLayoutManager.getOrientation());
        mLocationListView.addItemDecoration(dividerItemDecoration);

        getLocationHistory();
    }

    private void getLocationHistory() {
        showProgressBar(true);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + AppCache.getCache().getUserInfo().getToken());

        NetworkRequest request = new NetworkRequest(Request.Method.GET,
                Constants.BASE_URL + Constants.ENDPOINT_LOCATION_HISTORY,
                new Response.Listener<NetworkModel>() {
                    @Override
                    public void onResponse(NetworkModel response) {
                        if (response instanceof LocationHistoryResponse) {

                            mLocationHistoryItems = ((LocationHistoryResponse) response).getResult();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLocationListAdapter = new LocationListAdapter(mLocationHistoryItems);
                                    mLocationListView.setAdapter(mLocationListAdapter);
                                }
                            });

                        }
                        showProgressBar(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgressBar(false);
                        Toast.makeText(LocationHistoryActivity.this, "Unable to fetch location history", Toast.LENGTH_SHORT).show();
                    }
                }, new LocationHistoryResponse(), headers, null);
        Volley.newRequestQueue(this).add(request);
    }

    private void showProgressBar(boolean visible) {
        if (mLoadingProgress == null) {
            mLoadingProgress = findViewById(R.id.loadingProgress);
        }

        if (visible && mLoadingProgress.getVisibility() == View.GONE) {
            mLoadingProgress.setVisibility(View.VISIBLE);
            mLocationListView.setVisibility(View.GONE);
        } else {
            mLoadingProgress.setVisibility(View.GONE);
            mLocationListView.setVisibility(View.VISIBLE);
        }
    }
}
