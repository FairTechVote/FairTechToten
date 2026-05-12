package com.example.fairtechtoten.features.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fairtechtoten.R;
import com.example.fairtechtoten.domain.model.Institute;

import java.util.ArrayList;
import java.util.List;

public class InstituteAdapter extends RecyclerView.Adapter<InstituteAdapter.InstituteViewHolder> {

    private List<Institute> institutes = new ArrayList<>();
    private OnInstituteClickListener listener;

    public interface OnInstituteClickListener {
        void onInstituteClick(Institute institute);
    }

    public void setOnInstituteClickListener(OnInstituteClickListener listener) {
        this.listener = listener;
    }

    public void setInstitutes(List<Institute> institutes) {
        this.institutes = institutes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InstituteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_institute, parent, false);

        return new InstituteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstituteViewHolder holder, int position) {
        Institute institute = institutes.get(position);
        holder.bind(institute, listener);
    }

    @Override
    public int getItemCount() {
        return institutes.size();
    }

    static class InstituteViewHolder extends RecyclerView.ViewHolder {

        private final TextView instituteName;
        private final TextView instituteCnpj;

        public InstituteViewHolder(@NonNull View itemView) {
            super(itemView);

            instituteName = itemView.findViewById(R.id.instituteName);
            instituteCnpj = itemView.findViewById(R.id.instituteCnpj);
        }

        public void bind(Institute institute, OnInstituteClickListener listener) {

            instituteName.setText(institute.getName());
            instituteCnpj.setText("CNPJ: " + formatCnpj(institute.getCnpj()));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onInstituteClick(institute);
                }
            });
        }

        private String formatCnpj(String cnpj) {
            cnpj = cnpj.replaceAll("[^0-9]", "");

            if (cnpj.length() == 14) {
                return cnpj.substring(0, 2) + "." +
                        cnpj.substring(2, 5) + "." +
                        cnpj.substring(5, 8) + "/" +
                        cnpj.substring(8, 12) + "-" +
                        cnpj.substring(12, 14);
            }

            return cnpj;
        }
    }

}
