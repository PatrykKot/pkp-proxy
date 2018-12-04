package com.kotlarz.api.pkp.train.connections.dto.response;

import com.kotlarz.api.pkp.train.connections.adapter.dto.response.TravelRouteAdapterDto;
import com.kotlarz.api.pkp.train.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class TravelRouteDto
{
    private Long travelTimeMs;

    private Double kilometers;

    private List<TrainRouteDto> trainRoutes;

    private Long travelStartDate;

    private Long travelFinishDate;

    public static TravelRouteDto from( TravelRouteAdapterDto adapterDto )
    {
        return TravelRouteDto.builder()
                        .travelTimeMs( DateUtil.getTravelTime( adapterDto.getTravelTime() ) )
                        .kilometers( adapterDto.getKilometers() )
                        .trainRoutes( adapterDto.getTrainRouteAdapterDtos().stream()
                                                      .map( TrainRouteDto::from )
                                                      .collect( Collectors.toList() ) )
                        .travelStartDate( DateUtil.parse( adapterDto.getTravelStartDate() ).getTime() )
                        .travelFinishDate( DateUtil.parse( adapterDto.getTravelFinishDate() ).getTime() )
                        .build();
    }
}
