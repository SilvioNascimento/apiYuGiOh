package br.ufpb.dcx.dsc.apiYuGiOh.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class EmailExistenteValidator implements ConstraintValidator<EmailExistente, String> {
    private static final List<String> listaExtensaoEmails = Arrays.asList(
            "@gmail.com", "@outlook.com", "@yahoo.com", "@protomail.com",
            "@zohomail.com", "@aol.com", "@icloud.com", "@mail.com",
            "@gmx.com", "@yandex.com"
    );

    @Override
    public void initialize(EmailExistente constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.trim().isEmpty()) {
            return false; // Pode ajustar conforme a sua necessidade
        }

        return listaExtensaoEmails.stream()
                .anyMatch(email::endsWith);
    }

}
