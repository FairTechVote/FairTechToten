package com.example.fairtechtoten.data.mapper;

import com.example.fairtechtoten.data.remote.dto.CoordinatorDto;
import com.example.fairtechtoten.data.remote.dto.CoordinatorUpdateRequestDto;
import com.example.fairtechtoten.domain.model.Coordinator;
import com.example.fairtechtoten.domain.model.Status;

public class CoordinatorMapper {

    private CoordinatorMapper() {}
    public static Coordinator toEntity(CoordinatorDto dto) {
        return new Coordinator(
                dto.getId(),
                dto.getName(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getStatus() != null ? dto.getStatus() : Status.ACTIVE
        );
    }
    public static CoordinatorUpdateRequestDto toUpdateRequest(Coordinator entity) {
        return new CoordinatorUpdateRequestDto(
                entity.getName(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getStatus()
        );
    }

}
