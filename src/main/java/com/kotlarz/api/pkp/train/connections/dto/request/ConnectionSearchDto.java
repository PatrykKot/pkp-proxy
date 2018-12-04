package com.kotlarz.api.pkp.train.connections.dto.request;

import com.kotlarz.api.pkp.train.connections.adapter.dto.request.ConnectionSearchAdapterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConnectionSearchDto
{
    private String from;

    private String to;

    public ConnectionSearchAdapterDto toAdapterDto()
    {
        ConnectionSearchAdapterDto dto = ConnectionSearchAdapterDto.buildDefault();
        dto.setStartingStop( from );
        dto.setTargetStop( to );
        return dto;
    }
}
