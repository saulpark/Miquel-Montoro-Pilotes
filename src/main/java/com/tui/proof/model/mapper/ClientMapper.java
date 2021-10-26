package com.tui.proof.model.mapper;

import com.tui.proof.model.Client;
import com.tui.proof.model.dto.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client ClientDtoToClient(ClientDTO clientDTO);
}
