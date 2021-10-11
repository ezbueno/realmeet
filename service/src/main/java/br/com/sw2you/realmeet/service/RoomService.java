package br.com.sw2you.realmeet.service;

import static java.util.Objects.requireNonNull;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import br.com.sw2you.realmeet.validator.RoomValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomValidator roomValidator;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomValidator roomValidator, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomValidator = roomValidator;
        this.roomMapper = roomMapper;
    }

    public RoomDTO getRoom(Long id) {
        requireNonNull(id);
        Room room = this.getActiveRoomOrThrow(id);
        return this.roomMapper.fromEntityToDTO(room);
    }

    public RoomDTO createRoom(CreateRoomDTO createRoomDTO) {
        this.roomValidator.validate(createRoomDTO);
        var room = this.roomMapper.fromCreateRoomDTOToEntity(createRoomDTO);
        this.roomRepository.save(room);
        return this.roomMapper.fromEntityToDTO(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        this.getActiveRoomOrThrow(roomId);
        this.roomRepository.deactivate(roomId);
    }

    @Transactional
    public void updateRoom(Long roomId, UpdateRoomDTO updateRoomDTO) {
        this.getActiveRoomOrThrow(roomId);
        this.roomValidator.validate(roomId, updateRoomDTO);
        this.roomRepository.updateRoom(roomId, updateRoomDTO.getName(), updateRoomDTO.getSeats());
    }

    private Room getActiveRoomOrThrow(Long id) {
        requireNonNull(id);
        return this.roomRepository.findByIdAndActive(id, true)
            .orElseThrow(() -> new RoomNotFoundException("Room not found! ID: " + id));
    }
}
