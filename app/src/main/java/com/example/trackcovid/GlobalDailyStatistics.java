package com.example.trackcovid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.DailyData;


public class GlobalDailyStatistics extends Fragment {

    private TextView globalNewCases;
    private TextView globalNewDeaths;
    private TextView globalTotalCases;
    private TextView globalTotalDeath;
    private TextView globalTotalRecovered;
    private TextView lastUpdatedField;

    private DailyData globalData;

    private SwipeRefreshLayout theSwiper;
    private ProgressBar theProgress;

    public GlobalDailyStatistics() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View theView = inflater.inflate(R.layout.fragment_global_daily_statistics, container, false);

        getReferences(theView);

        theSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGlobalDailyData(theView);
            }
        });

        getGlobalDailyData(theView);

        return theView;
    }

    private void getReferences(View theView) {
        globalNewCases = (TextView) theView.findViewById(R.id.new_cases_field_global);
        globalNewDeaths = (TextView) theView.findViewById(R.id.newDeaths_field_global);
        globalTotalCases = (TextView) theView.findViewById(R.id.total_cases_field_global);
        globalTotalDeath = (TextView) theView.findViewById(R.id.total_deaths_cases_field_global);
        globalTotalRecovered = (TextView) theView.findViewById(R.id.total_recovered_field_global);
        lastUpdatedField = (TextView) theView.findViewById(R.id.last_updated_global);

        theSwiper = (SwipeRefreshLayout) theView.findViewById(R.id.swipe_refresh_global);
        theProgress = (ProgressBar) theView.findViewById(R.id.daily_stat_progressbar_global);
    }

    private void getGlobalDailyData(final View theView) {
        theProgress.setVisibility(View.VISIBLE);

        RequestQueue theRequests = Volley.newRequestQueue(theView.getContext());

        JsonObjectRequest theRequest = new JsonObjectRequest(Request.Method.GET, DailyData.getAPIURL(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");

                    int newCasesGlobal = data.getInt("global_new_cases");
                    int totalGlobal = data.getInt("global_total_cases");
                    int totalDeaths = data.getInt("global_deaths");
                    int newDeaths = data.getInt("global_new_deaths");
                    int totalRecovered = data.getInt("global_recovered");

                    String updatedTime = data.getString("update_date_time");

                    globalData = new DailyData(updatedTime, newCasesGlobal, totalGlobal, totalDeaths, totalRecovered, newDeaths);

                    if (globalData != null) {
                        showInUI(globalData, theView);
                    }
                } catch (Exception ex) {
                    Log.d("Error: ", ex.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(theView, "No Global Data Available.", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getGlobalDailyData(theView);
                    }
                }).show();
                theProgress.setVisibility(View.GONE);
                theSwiper.setRefreshing(false);
            }
        });

        theRequests.add(theRequest);
    }

    private void showInUI(DailyData globalData, View theView) {
        //when the data is loaded from the internet
        theSwiper.setRefreshing(false);

        try {
            SimpleDateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = theDateFormat.parse(globalData.getUpdateDateTime());

            SimpleDateFormat newFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            globalData.setUpdateDateTime(newFormatter.format(date));
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        } finally {

            NumberFormat theFormat = NumberFormat.getInstance();

            globalNewCases.setText(theFormat.format(globalData.getGlobalNewCases()));
            globalNewDeaths.setText(theFormat.format(globalData.getGlobalNewDeaths()));
            globalTotalCases.setText(theFormat.format(globalData.getGlobalTotalCases()));
            globalTotalDeath.setText(theFormat.format(globalData.getGlobalDeaths()));
            globalTotalRecovered.setText(theFormat.format(globalData.getGlobalRecovered()));
            lastUpdatedField.setText("Last Updated: " + globalData.getUpdateDateTime());

            theProgress.setVisibility(View.GONE);
        }

    }
}