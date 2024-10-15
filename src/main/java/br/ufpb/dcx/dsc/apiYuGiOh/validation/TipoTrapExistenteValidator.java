package br.ufpb.dcx.dsc.apiYuGiOh.validation;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoTrap;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TipoTrapExistenteValidator implements ConstraintValidator<TipoTrapExistente, TipoTrap> {

    private List<String> listaTipoTrapStr;

    @Override
    public void initialize(TipoTrapExistente annotation) {
        listaTipoTrapStr = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(TipoTrap tipoTrap, ConstraintValidatorContext constraintValidatorContext) {
        if (tipoTrap == null || tipoTrap.name().isEmpty()) {
            return false;
        }

        return listaTipoTrapStr.contains(tipoTrap.name());
    }

}
