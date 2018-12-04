package com.kotlarz.api.pkp.train.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor( access = AccessLevel.PRIVATE )
public enum Carrier
{
    ARRIVA( "Arriva", "AR" ),
    PKP_CARGO( "PKP Cargo", "CAROG" ),
    PKP_INTERCITY( "PKP Intercity", "IC" ),
    KOLEJE_DOLNOSLASKIE( "Koleje dolnośląskie", "KD" ),
    KOLEJE_MALOPOLSKIE( "Koleje małopolskie", "KM" ),
    KOLEJE_SLASKIE( "Koleje śląskie", "KS" ),
    KOLEJE_WIELKOPOLSKIE( "Koleje wielkopolskie", "KW" ),
    LODZKA_KOLEJ_ALGOMERACYJNA( "Łódzka kolej aglomeracyjna", "ŁKA" ),
    OSTEDEUTSCHE_EISENBAHN( "Ostedeutsche Eisenbahn", "ODEG" ),
    PRZEWOZY_REGIONALNE( "Przewozy regionalne", "PR" ),
    SZYBKA_KOLEJ_MIEJSKA( "Szybka kolej miejska", "SKM" ),
    SZYBKA_KOLEJ_MIEJSKA_W_TROJMIESCIE( "Szybka kolej miejska w Trójmieście", "SKMT" ),
    SKPL_CARGO( "SKPL Cargo", "SKPL" ),
    LEO_EXPRESS( "LEO Express", "LEO" );

    private String translation;

    private String id;

    public static Carrier fromId( String id )
    {
        return Stream.of( values() )
                        .filter( carrier -> carrier.getId().equals( id ) )
                        .findFirst()
                        .orElseThrow( IllegalArgumentException::new );
    }
}
