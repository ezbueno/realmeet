package br.com.sw2you.realmeet.domain.repository;

import br.com.sw2you.realmeet.domain.entity.Allocation;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        value = "UPDATE Allocation a SET a.subject = :subject, a.startAt = :startAt, a.endAt = :endAt WHERE a.id = :allocationId"
    )
    void updateAllocation(
        @Param(value = "allocationId") Long allocationId,
        @Param(value = "subject") String subject,
        @Param(value = "startAt") OffsetDateTime startAt,
        @Param(value = "endAt") OffsetDateTime endAt
    );

    @Query(
        value = "SELECT a FROM Allocation a WHERE " +
        "(:employeeEmail IS NULL OR a.employee.email = :employeeEmail) AND " +
        "(:roomId IS NULL OR a.room.id = :roomId) AND " +
        "(:startAt IS NULL OR a.startAt >= :startAt) AND " +
        "(:endAt IS NULL OR a.endAt <= :endAt)"
    )
    Page<Allocation> findAllWithFilters(
        @Param(value = "employeeEmail") String employeeEmail,
        @Param(value = "roomId") Long roomId,
        @Param(value = "startAt") OffsetDateTime startAt,
        @Param(value = "endAt") OffsetDateTime endAt,
        Pageable pageable
    );

    @Query(
        value = "SELECT a FROM Allocation a WHERE " +
        "(:employeeEmail IS NULL OR a.employee.email = :employeeEmail) AND " +
        "(:roomId IS NULL OR a.room.id = :roomId) AND " +
        "(:startAt IS NULL OR a.startAt >= :startAt) AND " +
        "(:endAt IS NULL OR a.endAt <= :endAt)"
    )
    List<Allocation> findAllWithFilters(
        @Param(value = "employeeEmail") String employeeEmail,
        @Param(value = "roomId") Long roomId,
        @Param(value = "startAt") OffsetDateTime startAt,
        @Param(value = "endAt") OffsetDateTime endAt
    );
}
