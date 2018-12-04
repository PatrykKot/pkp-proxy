package com.kotlarz.api.pkp.train.connections.dto.response;

import com.kotlarz.api.pkp.train.connections.adapter.dto.response.ConnectionSearchResponseAdapterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class ConnectionSearchResponseDto
{
    private List<TravelRouteDto> routes;

    public static ConnectionSearchResponseDto from( ConnectionSearchResponseAdapterDto adapterDto )
    {
        return ConnectionSearchResponseDto.builder()
                        .routes( adapterDto.getRoutes().stream()
                                                 .map( TravelRouteDto::from )
                                                 .collect( Collectors.toList() ) )
                        .build();
    }
}
