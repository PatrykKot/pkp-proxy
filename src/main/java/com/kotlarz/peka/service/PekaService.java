package com.kotlarz.peka.service;

import com.kotlarz.config.container.Bean;
import com.kotlarz.dto.Bollard;
import com.kotlarz.dto.Stop;
import com.kotlarz.dto.Time;
import com.kotlarz.peka.adapter.dto.PekaStopPoint;
import com.kotlarz.peka.adapter.service.PekaAdapterService;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Bean
public class PekaService {
    private PekaAdapterService pekaAdapterService;

    @Inject
    public PekaService(PekaAdapterService pekaAdapterService) {
        this.pekaAdapterService = pekaAdapterService;
    }

    public List<Stop> getStops(String namePattern) {
        return pekaAdapterService.getStopPointsByName(namePattern).stream()
                .map(Stop::from)
                .collect(Collectors.toList());
    }

    public List<Bollard> getBollards(String stopName) {
        Optional<PekaStopPoint> optionalPekaStopPoint = pekaAdapterService.getStopPointsByName(stopName).stream()
                .min(Comparator.comparing(o -> Levenshtein.calculate(o.getName(), stopName)));

        if (optionalPekaStopPoint.isPresent()) {
            PekaStopPoint pekaStopPoint = optionalPekaStopPoint.get();
            return pekaAdapterService.getBollardsByStopPoint(pekaStopPoint.getName()).stream()
                    .map(Bollard::from)
                    .collect(Collectors.toList());
        } else {
            return new LinkedList<>();
        }
    }

    public List<Time> getTimes(String bollardTag) {
        return Time.from(pekaAdapterService.getTimesByBollard(bollardTag));
    }

}
