package Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackcovid.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {
    private ArrayList<HospitalData> theHospitals;
    private Context currentContext;

    public HospitalAdapter(ArrayList<HospitalData> theHospitals, Context currentContext) {
        this.theHospitals = theHospitals;
        this.currentContext = currentContext;
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {

        private TextView hospitalNameField;
        private TextView treatmentLocalField;
        private TextView treatmentForeignField;
        private TextView totalTreatment;
        private MaterialButton moreInfoButton;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            getReferences(itemView);
            currentContext = itemView.getContext();

        }

        private void getReferences(View theView) {
            hospitalNameField = theView.findViewById(R.id.hospital_name);
            treatmentLocalField = theView.findViewById(R.id.treatment_local);
            treatmentForeignField = theView.findViewById(R.id.treatment_foreign);
            totalTreatment = theView.findViewById(R.id.total_treatment);
            moreInfoButton = theView.findViewById(R.id.more_info_button);
        }

        public MaterialButton getMoreInfoButton() {
            return moreInfoButton;
        }

        public void bindHospital(HospitalData theHospitalData){
            hospitalNameField.setText(theHospitalData.getTheHospital().getName());
            treatmentLocalField.setText("Locals Treated: "+theHospitalData.getTreatmentLocal());
            treatmentForeignField.setText("Foreigners Treated: "+theHospitalData.getTreatmentForeign());
            totalTreatment.setText("Total Treated: "+theHospitalData.getTreatmentTotal());
        }
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater theInflator = LayoutInflater.from(parent.getContext());
        View theView = theInflator.inflate(R.layout.hospital_card,parent,false);

        return new HospitalViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        holder.bindHospital(theHospitals.get(position));
        holder.getMoreInfoButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Functionality Not Yet Implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return theHospitals.size();
    }

}
