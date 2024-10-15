package br.ufpb.dcx.dsc.apiYuGiOh.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TipoMonsterExistenteValidator.class)
@Documented
public @interface TipoMonsterExistente {

    Class<? extends Enum<?>> enumClass();
    String message() default "Tipo de monstro inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
