package br.ufpb.dcx.dsc.apiYuGiOh.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TipoTrapExistenteValidator.class)
@Documented
public @interface TipoTrapExistente {

    Class<? extends Enum<?>> enumClass();
    String message() default "Tipo de armadilha inválida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
