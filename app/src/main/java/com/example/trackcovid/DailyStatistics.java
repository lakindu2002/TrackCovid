package com.example.trackcovid;

import android.annotation.SuppressLint;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.DailyData;

public class DailyStatistics extends Fragment {

    private TextView newCases;
    private TextView activeCases;
    private TextView totalCases;
    private TextView totalDeaths;
    private TextView totalRecovered;
    private TextView suspectedCases;
    private TextView lastUpdated;

    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar theProgress;


    private DailyData dailyLocalData;

    public DailyStatistics() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View theView = inflater.inflate(R.layout.fragment_daily_statistics, container, false);
        getReferences(theView);
        gatherDailyStats(theView);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gatherDailyStats(theView);
            }
        });

        return theView;
    }

    private void getReferences(View theView){
        newCases = (TextView) theView.findViewById(R.id.new_cases_field);
        activeCases = (TextView) theView.findViewById(R.id.active_cases_field);
        totalCases = (TextView) theView.findViewById(R.id.total_cases_field);
        totalDeaths = (TextView) theView.findViewById(R.id.total_deaths_cases_field);
        totalRecovered = (TextView) theView.findViewById(R.id.total_recovered_field);
        suspectedCases = (TextView) theView.findViewById(R.id.suspected_cases_field);
        lastUpdated = (TextView) theView.findViewById(R.id.last_updated);

        swipeRefresh = (SwipeRefreshLayout) theView.findViewById(R.id.swipe_refresh);
        theProgress = (ProgressBar) theView.findViewById(R.id.daily_stat_progressbar);
    }

    private void gatherDailyStats(final View theView) {
        theProgress.setVisibility(View.VISIBLE);
        RequestQueue theQueue = Volley.newRequestQueue(theView.getContext());

        JsonObjectRequest theRequest = new JsonObjectRequest(Request.Method.GET, DailyData.getAPIURL(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject("data");

                    String lastUpdateTime = dataObject.getString("update_date_time");
                    int localNewCases = dataObject.getInt("local_new_cases");
                    int localTotalCases = dataObject.getInt("local_total_cases");
                    int localInHospitals = dataObject.getInt("local_total_number_of_individuals_in_hospitals");
                    int localDeath = dataObject.getInt("local_deaths");
                    int localRecovered = dataObject.getInt("local_recovered");
                    int localActive = dataObject.getInt("local_active_cases");

                    dailyLocalData = new DailyData(lastUpdateTime, localNewCases, localTotalCases, localInHospitals, localDeath, localRecovered,localActive);

                    if (dailyLocalData != null) {
                        showInUI(dailyLocalData,theView);
                    }

                } catch (Exception ex) {
                    Log.d("Error: ", ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //when there is an error
                Snackbar.make(theView, "No Daily Data Available.", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gatherDailyStats(theView);
                    }
                }).show();
                theProgress.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }
        });

        theQueue.add(theRequest);
    }
    @SuppressLint("SetTextI18n")
    private void showInUI(DailyData dailyData, View theView){
        //when the data is loaded from the internet
        swipeRefresh.setRefreshing(false);

        try {
            SimpleDateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = theDateFormat.parse(dailyData.getUpdateDateTime());

            SimpleDateFormat newFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dailyData.setUpdateDateTime(newFormatter.format(date));
        }
        catch(Exception ex){
            Log.d("Error",ex.getMessage());
        }
        finally {

            NumberFormat theFormat = NumberFormat.getInstance();

            newCases.setText(theFormat.format(dailyData.getLocalNewCases()));
            activeCases.setText(theFormat.format(dailyData.getLocalActive()));
            totalCases.setText(theFormat.format(dailyData.getLocalTotalCases()));
            totalDeaths.setText(theFormat.format(dailyData.getLocalDeaths()));
            totalRecovered.setText(theFormat.format(dailyData.getLocalRecovered()));
            suspectedCases.setText(theFormat.format(dailyData.getLocalTotalNumberOfIndividualsInHospitals()));
            lastUpdated.setText("Last Updated: "+dailyData.getUpdateDateTime());

            theProgress.setVisibility(View.GONE);
        }

    }
}