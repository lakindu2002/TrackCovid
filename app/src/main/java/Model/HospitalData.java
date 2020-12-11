package Model;

import java.util.Comparator;

public class HospitalData {
    private int id;
    private int hospitalId;
    private int cumulativeLocal; //Total number of Sri Lankans who have been treated /observed for COVID-19 in a given hospital
    private int cumulativeForeign; //Total number of foreigners who have been treated /observed for COVID-19 in a given hospital
    private int treatmentLocal; //Total number of Sri Lankans who are currently on treatment/observation for COVID-19 in a given hospital
    private int treatmentForeign; //Total number of foreigners who are currently on treatment/observation for COVID-19 in a given hospital
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private int cumulativeTotal;
    private int treatmentTotal;
    private Hospital theHospital;

    public HospitalData(int id, int hospitalId, int cumulativeLocal, int cumulativeForeign, int treatmentLocal, int treatmentForeign, String createdAt, String updatedAt, String deletedAt, int cumulativeTotal, int treatmentTotal, Hospital theHospital) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.cumulativeLocal = cumulativeLocal;
        this.cumulativeForeign = cumulativeForeign;
        this.treatmentLocal = treatmentLocal;
        this.treatmentForeign = treatmentForeign;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.cumulativeTotal = cumulativeTotal;
        this.treatmentTotal = treatmentTotal;
        this.theHospital = theHospital;
    }

    public HospitalData(int treatmentLocal, int treatmentForeign, int treatmentTotal) {
        this.treatmentLocal = treatmentLocal;
        this.treatmentForeign = treatmentForeign;
        this.treatmentTotal = treatmentTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Comparator<HospitalData> hospitalDataComparator = new Comparator<HospitalData>() {
        @Override
        public int compare(HospitalData hospitalData, HospitalData t1) {
            String hospitalNameOne = hospitalData.getTheHospital().getName();
            String hospitalNameTwo = t1.getTheHospital().getName();

            return hospitalNameOne.compareTo(hospitalNameTwo);
        }
    };

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getCumulativeLocal() {
        return cumulativeLocal;
    }

    public void setCumulativeLocal(int cumulativeLocal) {
        this.cumulativeLocal = cumulativeLocal;
    }

    public int getCumulativeForeign() {
        return cumulativeForeign;
    }

    public void setCumulativeForeign(int cumulativeForeign) {
        this.cumulativeForeign = cumulativeForeign;
    }

    public int getTreatmentLocal() {
        return treatmentLocal;
    }

    public void setTreatmentLocal(int treatmentLocal) {
        this.treatmentLocal = treatmentLocal;
    }

    public int getTreatmentForeign() {
        return treatmentForeign;
    }

    public void setTreatmentForeign(int treatmentForeign) {
        this.treatmentForeign = treatmentForeign;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getCumulativeTotal() {
        return cumulativeTotal;
    }

    public void setCumulativeTotal(int cumulativeTotal) {
        this.cumulativeTotal = cumulativeTotal;
    }

    public int getTreatmentTotal() {
        return treatmentTotal;
    }

    public void setTreatmentTotal(int treatmentTotal) {
        this.treatmentTotal = treatmentTotal;
    }

    public Hospital getTheHospital() {
        return theHospital;
    }

    public void setTheHospital(Hospital theHospital) {
        this.theHospital = theHospital;
    }

}
