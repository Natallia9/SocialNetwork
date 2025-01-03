package com.example.socialnetwork.validations;

import com.example.socialnetwork.annotations.PasswordMatches;
import com.example.socialnetwork.payload.request.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignupRequest> {

    @Override
    public boolean isValid(SignupRequest signupRequest, ConstraintValidatorContext context) {
        if (signupRequest == null) {
            return true;
        }
        return signupRequest.getPassword().equals(signupRequest.getConfirmPassword());
    }
}
