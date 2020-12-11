package com.example.trackcovid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import Model.DailyData;
import Model.Hospital;
import Model.HospitalAdapter;
import Model.HospitalData;


public class HospitalInformationSriLanka extends Fragment {

    private ArrayList<HospitalData> theHospitalData = new ArrayList<HospitalData>();
    private RecyclerView theRecyclerView;
    private RecyclerView.LayoutManager recyclerLayout;
    private HospitalAdapter theAdapter;

    private SwipeRefreshLayout theSwipe;
    private ProgressBar theProgress;
    private TextView updatedTimeField;
    private SearchView theSearch;
    private MaterialButton theChartViewToggler;

    private String updatedTime = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View theView = inflater.inflate(R.layout.fragment_hospital_information_sri_lanka, container, false);

        theRecyclerView = theView.findViewById(R.id.hospital_reycler);
        theSwipe = theView.findViewById(R.id.hospital_list_swipe);
        theProgress = theView.findViewById(R.id.loading_list);
        updatedTimeField = theView.findViewById(R.id.last_updated_list);
        theSearch = theView.findViewById(R.id.search_view);
        theChartViewToggler = theView.findViewById(R.id.toggle_chart);

        recyclerLayout = new LinearLayoutManager(theView.getContext());
        theRecyclerView.setHasFixedSize(true);
        theRecyclerView.setLayoutManager(recyclerLayout);

        theSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDailyHospitalStatistics(theView);
            }
        });
        getDailyHospitalStatistics(theView);

        theSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                theAdapter = null;
                theRecyclerView.setAdapter(null);
                try {
                    searchData(theView, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                theAdapter = null;
                theRecyclerView.setAdapter(null);
                try {
                    searchData(theView, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


        theChartViewToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(HospitalInformationSriLanka.this.getActivity(),HospitalChartView.class));
            }
        });

        return theView;
    }

    private void searchData(final View theView, String searchQuery) throws Exception {
        ArrayList<HospitalData> filteredList = new ArrayList<>();
        if (theHospitalData.isEmpty()) {
            Snackbar.make(theView, "No Hospital Data Available.", Snackbar.LENGTH_LONG).setAction("Load Data", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDailyHospitalStatistics(theView);
                }
            }).show();
        } else {
            for (HospitalData data : theHospitalData) {
                Hospital theHospital = data.getTheHospital();
                if (theHospital.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredList.add(data);
                }
            }
            if (filteredList.isEmpty()) {
                Snackbar.make(theView, "Search Result Empty.", Snackbar.LENGTH_LONG).show();
            } else {
                Collections.sort(filteredList, HospitalData.hospitalDataComparator);
                theAdapter = new HospitalAdapter(filteredList, theView.getContext());
                theRecyclerView.setAdapter(theAdapter);
            }
        }
    }

    private void getDailyHospitalStatistics(final View theView) {
        theHospitalData.clear();
        theProgress.setVisibility(View.VISIBLE);
        RequestQueue theRequestQueue = Volley.newRequestQueue(theView.getContext());
        JsonObjectRequest theRequest = new JsonObjectRequest(Request.Method.GET, DailyData.getAPIURL(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    JSONArray hospitalList = dataObject.getJSONArray("hospital_data");
                    updatedTime = dataObject.getString("update_date_time");

                    for (int i = 0; i < hospitalList.length(); i++) {
                        JSONObject theHospital = hospitalList.getJSONObject(i);

                        int treatmentLocal = theHospital.getInt("treatment_local");
                        int treatmentForeign = theHospital.getInt("treatment_foreign");
                        int totalTreated = theHospital.getInt("treatment_total");

                        if (totalTreated != 0) {
                            JSONObject theHospitalInformation = theHospital.getJSONObject("hospital");
                            int hospitalId = theHospitalInformation.getInt("id");
                            String hospitalName = theHospitalInformation.getString("name");

                            Hospital theHospitalObject = new Hospital(hospitalId, hospitalName);
                            HospitalData theData = new HospitalData(treatmentLocal, treatmentForeign, totalTreated);
                            theData.setTheHospital(theHospitalObject);

                            theHospitalData.add(theData);
                        }
                    }

                    showInReycler(theHospitalData, theView, updatedTime);
                } catch (Exception ex) {
                    Log.d("Error", ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
                theSwipe.setRefreshing(false);
                theProgress.setVisibility(View.GONE);

                Snackbar.make(theView, "No Hospital Data Available.", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDailyHospitalStatistics(theView);
                    }
                }).show();
            }
        });

        theRequestQueue.add(theRequest);
    }

    private void showInReycler(ArrayList<HospitalData> data, final View theView, String updatedTime) throws Exception {

        if (data.isEmpty()) {
            Snackbar.make(theView, "No Hospital Data Available.", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDailyHospitalStatistics(theView);
                }
            }).show();
        } else {
            if (updatedTime != null) {
                SimpleDateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = theDateFormat.parse(updatedTime);

                SimpleDateFormat newFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                updatedTimeField.setText("Last Updated: " + newFormatter.format(date));
            }

            Collections.sort(data, HospitalData.hospitalDataComparator);
            theAdapter = new HospitalAdapter(data, theView.getContext());
            theRecyclerView.setAdapter(theAdapter);
        }
        theSwipe.setRefreshing(false);
        theProgress.setVisibility(View.GONE);
    }
}