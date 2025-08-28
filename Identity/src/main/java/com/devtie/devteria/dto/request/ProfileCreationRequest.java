package com.devtie.devteria.dto.request;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    String userId;
    // @Size(min = 2, message = "INVALID_REQUEST_FIRSTNAME")
    String firstName;

    // @Size(min = 2, message = "INVALID_REQUEST_LASTNAME")
    String lastName;

    // @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

    String city;
}
