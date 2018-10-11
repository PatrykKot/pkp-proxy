package com.kotlarz.dto;

import com.kotlarz.peka.adapter.dto.PekaBollard;
import com.kotlarz.peka.adapter.dto.PekaBollardDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Bollard {
    private List<Direction> directions;

    private String symbol;

    private String tag;

    private String name;

    private Boolean mainBollard;

    public static Bollard from(PekaBollard bollard) {
        PekaBollardDetails bollardDetails = bollard.getBollard();

        return Bollard.builder()
                .symbol(bollardDetails.getSymbol())
                .tag(bollardDetails.getTag())
                .name(bollardDetails.getName())
                .mainBollard(bollardDetails.getMainBollard())
                .directions(bollard.getDirections().stream()
                        .map(Direction::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
