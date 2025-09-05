package com.book_micro.identity_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.book_micro.identity_service.validator.DobConstraint;

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
public class UserCreationRequest {
    @Size(min = 3, message = "INVALID_REQUEST_USERNAME")
    String username;

    @Size(min = 8, message = "INVALID_REQUEST_PASSWORD")
    String password;

    @Email(message = "INVALID_EMAIL")
    @NotBlank(message = "EMAIL_IS_REQUIRED")
    String email;

    @Size(min = 2, message = "INVALID_REQUEST_FIRSTNAME")
    String firstName;

    @Size(min = 2, message = "INVALID_REQUEST_LASTNAME")
    String lastName;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

    @Size(min = 3, message = "INVALID_REQUEST_CITY")
    String city;
}
