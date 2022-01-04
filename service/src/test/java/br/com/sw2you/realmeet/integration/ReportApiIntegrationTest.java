package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.util.DateUtils.now;
import static br.com.sw2you.realmeet.utils.TestConstants.*;
import static br.com.sw2you.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.sw2you.realmeet.api.facade.AllocationApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.email.EmailSender;
import java.net.MalformedURLException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

class AllocationApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocationApi allocationApi;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @MockBean
    private EmailSender emailSender;

    private static final String PATH = "/v1";

    @Override
    protected void setupEach() throws MalformedURLException {
        this.setLocalHostBasePath(this.allocationApi.getApiClient(), PATH);
    }

    @Test
    void testCreateAllocationSuccess() {
        var room = this.roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = newCreateAllocationDTO().roomId(room.getId());
        var allocationDTO = this.allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO);

        assertNotNull(allocationDTO.getRoomId());
        assertEquals(room.getId(), allocationDTO.getRoomId());
        assertEquals(createAllocationDTO.getSubject(), allocationDTO.getSubject());
        assertEquals(createAllocationDTO.getEmployeeName(), allocationDTO.getEmployeeName());
        assertEquals(createAllocationDTO.getEmployeeEmail(), allocationDTO.getEmployeeEmail());
        assertTrue(createAllocationDTO.getStartAt().isEqual(allocationDTO.getStartAt()));
        assertTrue(createAllocationDTO.getEndAt().isEqual(allocationDTO.getEndAt()));
    }

    @Test
    void testCreateAllocationValidationError() {
        var room = this.roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = newCreateAllocationDTO().roomId(room.getId()).subject(null);

        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> this.allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO)
        );
    }

    @Test
    void testCreateAllocationWhenRoomDoesNotExist() {
        assertThrows(
            HttpClientErrorException.NotFound.class,
            () -> this.allocationApi.createAllocation(TEST_CLIENT_API_KEY, newCreateAllocationDTO())
        );
    }

    @Test
    void testDeleteAllocationSuccess() {
        var room = this.roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation = this.allocationRepository.saveAndFlush(newAllocationBuilder(room).build());

        this.allocationApi.deleteAllocation(TEST_CLIENT_API_KEY, allocation.getId());
        assertFalse(this.allocationRepository.findById(allocation.getId()).isPresent());
    }

    @Test
    void testDeleteAllocationInThePast() {
        var room = this.roomRepository.saveAndFlush(newRoomBuilder().build());
        var allocation =
            this.allocationRepository.saveAndFlush(
                    newAllocationBuilder(room)
                        .startAt(now().minusDays(1))
                        .endAt(now().minusDays(1).plusHours(1))
                        .build()
                );

        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () -> this.allocationApi.deleteAllocation(TEST_CLIENT_API_KEY, allocation.getId())
        );
    }

    @Test
    void testDeleteAllocationDoesNotExist() {
        assertThrows(
            HttpClientErrorException.NotFound.class,
            () -> this.allocationApi.deleteAllocation(TEST_CLIENT_API_KEY, 1L)
        );
    }

    @Test
    void testUpdateAllocationSuccess() {
        var room = this.roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = newCreateAllocationDTO().roomId(room.getId());
        var allocationDTO = this.allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO);

        var updateAllocationDTO = newUpdateAllocationDTO()
            .subject(DEFAULT_ALLOCATION_SUBJECT + "_")
            .startAt(DEFAULT_ALLOCATION_START_AT.plusDays(1))
            .endAt(DEFAULT_ALLOCATION_END_AT.plusDays(1));

        this.allocationApi.updateAllocation(TEST_CLIENT_API_KEY, allocationDTO.getId(), updateAllocationDTO);
        var allocation = this.allocationRepository.findById(allocationDTO.getId()).orElseThrow();

        assertEquals(updateAllocationDTO.getSubject(), allocation.getSubject());
        assertTrue(updateAllocationDTO.getStartAt().isEqual(allocation.getStartAt()));
        assertTrue(updateAllocationDTO.getEndAt().isEqual(allocation.getEndAt()));
    }

    @Test
    void testUpdateAllocationDoesNotExist() {
        assertThrows(
            HttpClientErrorException.NotFound.class,
            () -> this.allocationApi.updateAllocation(TEST_CLIENT_API_KEY, 1L, newUpdateAllocationDTO())
        );
    }

    @Test
    void testUpdateAllocationValidationError() {
        var room = this.roomRepository.saveAndFlush(newRoomBuilder().build());
        var createAllocationDTO = newCreateAllocationDTO().roomId(room.getId());
        var allocationDTO = this.allocationApi.createAllocation(TEST_CLIENT_API_KEY, createAllocationDTO);

        assertThrows(
            HttpClientErrorException.UnprocessableEntity.class,
            () ->
                this.allocationApi.updateAllocation(
                        TEST_CLIENT_API_KEY,
                        allocationDTO.getId(),
                        newUpdateAllocationDTO().subject(null)
                    )
        );
    }
}
