package br.ufpb.dcx.dsc.apiYuGiOh.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailExistenteValidator.class)
@Documented
public @interface EmailExistente {
    String message() default "Email inválido. Por favor, fornece um " +
            "email que termine com @gmail.com, @outlook.com, @yahoo.com, " +
            "@protomail.com, @zohomail.com, @aol.com, @icloud.com, " +
            "@mail.com, @gmx.com ou @yandex.com";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
