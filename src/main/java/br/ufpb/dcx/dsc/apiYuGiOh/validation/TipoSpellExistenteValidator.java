package br.ufpb.dcx.dsc.apiYuGiOh.validation;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoSpell;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TipoSpellExistenteValidator implements ConstraintValidator<TipoSpellExistente, TipoSpell> {
    private List<String> listaTipoSpellStr;

    @Override
    public void initialize(TipoSpellExistente annotation) {
        listaTipoSpellStr = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(TipoSpell tipoSpell, ConstraintValidatorContext constraintValidatorContext) {
        if(tipoSpell == null || tipoSpell.name().isEmpty()) {
            return false;
        }

        return listaTipoSpellStr.contains(tipoSpell.name());
    }

}
