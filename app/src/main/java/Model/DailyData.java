package Model;

import java.util.ArrayList;

public class DailyData {
    private static String URL = "https://hpb.health.gov.lk/api/get-current-statistical";

    private String updateDateTime;
    private int localNewCases; //Confirmed COVID-19 cases reported during the day
    private int localTotalCases; //Cumulative count of confirmed COVID-19 cases in Sri Lanka
    private int localTotalNumberOfIndividualsInHospitals; //Total suspected COVID-19 cases currently under investigations in hospitals
    private int localDeaths; //Total deaths due to COVID-19 reported in Sri Lanka
    private int localRecovered; //Total COVID-19 cases recovered and discharged in Sri Lanka
    private int globalNewCases; //Global confirmed COVID-19 cases reported during last 24 hours
    private int globalTotalCases; //Total global confirmed COVID-19 cases
    private int globalDeaths; //Total global deaths due to COVID-19
    private int globalRecovered;//Total Global COVID-19 cases who recovered
    private int globalNewDeaths; //total new deaths globally
    private int localActive;
    private ArrayList<HospitalData> hospitalData;

    public static String getAPIURL(){
        return URL;
    }

    public DailyData(String updateDateTime, int globalNewCases, int globalTotalCases, int globalDeaths, int globalRecovered,int globalNewDeaths) {
        this.updateDateTime = updateDateTime;
        this.globalNewCases = globalNewCases;
        this.globalTotalCases = globalTotalCases;
        this.globalDeaths = globalDeaths;
        this.globalRecovered = globalRecovered;
        this.globalNewDeaths = globalNewDeaths;
    }

    public DailyData(String updateDateTime, int localNewCases, int localTotalCases, int localTotalNumberOfIndividualsInHospitals, int localDeaths, int localRecovered, int localActive) {
        this.updateDateTime = updateDateTime;
        this.localNewCases = localNewCases;
        this.localTotalCases = localTotalCases;
        this.localTotalNumberOfIndividualsInHospitals = localTotalNumberOfIndividualsInHospitals;
        this.localDeaths = localDeaths;
        this.localRecovered = localRecovered;
        this.localActive = localActive;
    }

    public DailyData(String updateDateTime, int localNewCases, int localTotalCases, int localTotalNumberOfIndividualsInHospitals, int localDeaths, int localRecovered, int globalNewCases, int globalTotalCases, int globalDeaths, int globalRecovered, ArrayList<HospitalData> hospitalData) {
        this.updateDateTime = updateDateTime;
        this.localNewCases = localNewCases;
        this.localTotalCases = localTotalCases;
        this.localTotalNumberOfIndividualsInHospitals = localTotalNumberOfIndividualsInHospitals;
        this.localDeaths = localDeaths;
        this.localRecovered = localRecovered;
        this.globalNewCases = globalNewCases;
        this.globalTotalCases = globalTotalCases;
        this.globalDeaths = globalDeaths;
        this.globalRecovered = globalRecovered;
        this.hospitalData = hospitalData;
    }

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        DailyData.URL = URL;
    }

    public int getGlobalNewDeaths() {
        return globalNewDeaths;
    }

    public void setGlobalNewDeaths(int globalNewDeaths) {
        this.globalNewDeaths = globalNewDeaths;
    }

    public int getLocalActive() {
        return localActive;
    }

    public void setLocalActive(int localActive) {
        this.localActive = localActive;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public int getLocalNewCases() {
        return localNewCases;
    }

    public void setLocalNewCases(int localNewCases) {
        this.localNewCases = localNewCases;
    }

    public int getLocalTotalCases() {
        return localTotalCases;
    }

    public void setLocalTotalCases(int localTotalCases) {
        this.localTotalCases = localTotalCases;
    }

    public int getLocalTotalNumberOfIndividualsInHospitals() {
        return localTotalNumberOfIndividualsInHospitals;
    }

    public void setLocalTotalNumberOfIndividualsInHospitals(int localTotalNumberOfIndividualsInHospitals) {
        this.localTotalNumberOfIndividualsInHospitals = localTotalNumberOfIndividualsInHospitals;
    }

    public int getLocalDeaths() {
        return localDeaths;
    }

    public void setLocalDeaths(int localDeaths) {
        this.localDeaths = localDeaths;
    }

    public int getLocalRecovered() {
        return localRecovered;
    }

    public void setLocalRecovered(int localRecovered) {
        this.localRecovered = localRecovered;
    }

    public int getGlobalNewCases() {
        return globalNewCases;
    }

    public void setGlobalNewCases(int globalNewCases) {
        this.globalNewCases = globalNewCases;
    }

    public int getGlobalTotalCases() {
        return globalTotalCases;
    }

    public void setGlobalTotalCases(int globalTotalCases) {
        this.globalTotalCases = globalTotalCases;
    }

    public int getGlobalDeaths() {
        return globalDeaths;
    }

    public void setGlobalDeaths(int globalDeaths) {
        this.globalDeaths = globalDeaths;
    }

    public int getGlobalRecovered() {
        return globalRecovered;
    }

    public void setGlobalRecovered(int globalRecovered) {
        this.globalRecovered = globalRecovered;
    }

    public ArrayList<HospitalData> getHospitalData() {
        return hospitalData;
    }

    public void setHospitalData(ArrayList<HospitalData> hospitalData) {
        this.hospitalData = hospitalData;
    }
}
