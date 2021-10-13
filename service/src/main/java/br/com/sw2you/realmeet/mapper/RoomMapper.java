package br.com.sw2you.realmeet.mapper;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO fromEntityToDTO(Room room);

    Room fromCreateRoomDTOToEntity(CreateRoomDTO createRoomDTO);
}
