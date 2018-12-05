package com.kotlarz.api.pkp.train.connections.dto.response;

import com.kotlarz.api.pkp.train.connections.adapter.dto.response.TrainRouteAdapterDto;
import com.kotlarz.api.pkp.train.enums.Carrier;
import com.kotlarz.api.pkp.train.enums.TrainType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TrainRouteDto
{
    private String trainName;

    private Double kilometers;

    private String trainId;

    private Carrier carrier;

    private TrainType trainType;

    public static TrainRouteDto from( TrainRouteAdapterDto adapterDto )
    {
        return TrainRouteDto.builder()
                        .trainName( adapterDto.getTrainName() )
                        .kilometers( Double.parseDouble( adapterDto.getKilometers() ) )
                        .trainId( adapterDto.getTrainId() )
                        .carrier( Carrier.fromId( adapterDto.getCarrierId() ) )
                        .trainType( TrainType.fromId( adapterDto.getTrainTypeId() ) )
                        .build();
    }
}
