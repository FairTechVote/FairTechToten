package com.example.fairtechtoten.data.remote.dto;

import java.util.List;

public class CoordinatorInstituteLisResponseDto {

    private List<SummaryInstituteDto> institutes;

    public List<SummaryInstituteDto> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(List<SummaryInstituteDto> institutes) {
        this.institutes = institutes;
    }

}
