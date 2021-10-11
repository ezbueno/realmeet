package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.util.DateUtils.now;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import java.time.Duration;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class AllocationValidator {
    private final AllocationRepository allocationRepository;

    public AllocationValidator(AllocationRepository allocationRepository) {
        this.allocationRepository = allocationRepository;
    }

    public void validate(CreateAllocationDTO createAllocationDTO) {
        var validationErrors = new ValidationErrors();

        this.validateSubject(createAllocationDTO.getSubject(), validationErrors);
        this.validateEmployeeName(createAllocationDTO.getEmployeeName(), validationErrors);
        this.validateEmployeeEmail(createAllocationDTO.getEmployeeEmail(), validationErrors);
        this.validateDates(createAllocationDTO.getStartAt(), createAllocationDTO.getEndAt(), validationErrors);

        thrownOnError(validationErrors);
    }

    public void validate(Long allocationId, UpdateAllocationDTO updateAllocationDTO) {
        var validationErrors = new ValidationErrors();

        validateRequired(allocationId, ALLOCATION_ID, validationErrors);
        this.validateSubject(updateAllocationDTO.getSubject(), validationErrors);
        this.validateDates(updateAllocationDTO.getStartAt(), updateAllocationDTO.getEndAt(), validationErrors);

        thrownOnError(validationErrors);
    }

    private void validateSubject(String subject, ValidationErrors validationErrors) {
        validateRequired(subject, ALLOCATION_SUBJECT, validationErrors);
        validateMaxLength(subject, ALLOCATION_SUBJECT, ALLOCATION_SUBJECT_MAX_LENGTH, validationErrors);
    }

    private void validateEmployeeName(String employeeName, ValidationErrors validationErrors) {
        validateRequired(employeeName, ALLOCATION_EMPLOYEE_NAME, validationErrors);
        validateMaxLength(
            employeeName,
            ALLOCATION_EMPLOYEE_NAME,
            ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH,
            validationErrors
        );
    }

    private void validateEmployeeEmail(String employeeEmail, ValidationErrors validationErrors) {
        validateRequired(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, validationErrors);
        validateMaxLength(
            employeeEmail,
            ALLOCATION_EMPLOYEE_EMAIL,
            ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH,
            validationErrors
        );
    }

    private void validateDates(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if (this.validateDatesPresent(startAt, endAt, validationErrors)) {
            this.validateDateOrdering(startAt, endAt, validationErrors);
            this.validateDateInThePast(startAt, validationErrors);
            this.validateDuration(startAt, endAt, validationErrors);
            this.validateIfTimeAvailable(startAt, endAt, validationErrors);
        }
    }

    private boolean validateDatesPresent(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        return (
            validateRequired(startAt, ALLOCATION_START_AT, validationErrors) &&
            validateRequired(endAt, ALLOCATION_END_AT, validationErrors)
        );
    }

    private void validateDateOrdering(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT + INCONSISTENT);
        }
    }

    private void validateDateInThePast(OffsetDateTime date, ValidationErrors validationErrors) {
        if (date.isBefore(now())) {
            validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT + IN_THE_PAST);
        }
    }

    private void validateDuration(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErrors validationErrors) {
        if (Duration.between(startAt, endAt).getSeconds() > ALLOCATION_MAX_DURATION_SECONDS) {
            validationErrors.add(ALLOCATION_END_AT, ALLOCATION_END_AT + EXCEEDS_DURATION);
        }
    }

    private void validateIfTimeAvailable(
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        ValidationErrors validationErrors
    ) {
        //TODO
    }
}
