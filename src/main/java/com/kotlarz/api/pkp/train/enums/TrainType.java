package com.kotlarz.api.pkp.train.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor( access = AccessLevel.PRIVATE )
public enum TrainType
{
    FAST( "Szybki", "SZ" ),
    LONG_DISTANCE( "Dalekobieżny", "DA" ),
    LOCAL( "Lokalny", "LO" ),
    AGGLOMERATION( "Aglomeracyjny", "AG" );

    private String translation;

    private String id;

    public static TrainType fromId( String id )
    {
        return Stream.of( values() )
                        .filter( trainType -> trainType.getId().equals( id ) )
                        .findFirst()
                        .orElseThrow( IllegalArgumentException::new );
    }
}
