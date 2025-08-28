package com.book_micro.profile_service.dto.response;

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
public class UserProfileResponse {

    String id;
    // @Size(min = 2, message = "INVALID_REQUEST_FIRSTNAME")
    String firstName;

    // @Size(min = 2, message = "INVALID_REQUEST_LASTNAME")
    String lastName;

    LocalDate dob;

    String city;
}
