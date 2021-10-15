package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.RoomsApi;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController implements RoomsApi {
    private final Executor controllersExecutor;
    private final RoomService roomService;

    public RoomController(Executor controllersExecutor, RoomService roomService) {
        this.controllersExecutor = controllersExecutor;
        this.roomService = roomService;
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> getRoom(String apiKey, Long id) {
        return supplyAsync(() -> this.roomService.getRoom(id), this.controllersExecutor)
            .thenApply(ResponseEntityUtils::ok);
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> createRoom(
        String apiKey,
        @RequestBody CreateRoomDTO createRoomDTO
    ) {
        return supplyAsync(() -> this.roomService.createRoom(createRoomDTO), this.controllersExecutor)
            .thenApply(ResponseEntityUtils::created);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteRoom(String apiKey, Long id) {
        return runAsync(() -> this.roomService.deleteRoom(id), this.controllersExecutor)
            .thenApply(ResponseEntityUtils::noContent);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updateRoom(String apiKey, Long id, UpdateRoomDTO updateRoomDTO) {
        return runAsync(() -> this.roomService.updateRoom(id, updateRoomDTO), this.controllersExecutor)
            .thenApply(ResponseEntityUtils::noContent);
    }
}
