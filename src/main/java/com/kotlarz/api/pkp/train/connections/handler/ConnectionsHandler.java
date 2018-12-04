package com.kotlarz.api.pkp.train.connections.handler;

import com.kotlarz.api.pkp.train.connections.dto.request.ConnectionSearchDto;
import com.kotlarz.api.pkp.train.connections.service.ConnectionsService;
import com.kotlarz.config.container.Bean;
import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.util.HandlerUtils;
import spark.Service;
import spark.utils.Assert;

import javax.inject.Inject;

@Bean
public class ConnectionsHandler
                implements RequestHandler
{
    private ConnectionsService connectionsService;

    @Inject
    public ConnectionsHandler( ConnectionsService connectionsService )
    {
        this.connectionsService = connectionsService;
    }

    @Override
    public void register( Service service )
    {
        registerGetTrainConnections( service );
    }

    private void registerGetTrainConnections( Service service )
    {
        service.get( "pkp/search", ( request, response ) -> {
            String from = request.queryParams( "from" );
            String to = request.queryParams( "to" );

            Assert.hasLength( from, "From station cannot be empty" );
            Assert.hasLength( to, "To station cannot be empty" );

            ConnectionSearchDto searchDto = ConnectionSearchDto.builder()
                            .from( from )
                            .to( to )
                            .build();

            HandlerUtils.asJson( response );
            return HandlerUtils.toJson( connectionsService.find( searchDto ) );
        } );
    }
}
