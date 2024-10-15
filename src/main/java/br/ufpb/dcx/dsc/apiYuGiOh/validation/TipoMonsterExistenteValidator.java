package br.ufpb.dcx.dsc.apiYuGiOh.validation;

import br.ufpb.dcx.dsc.apiYuGiOh.ENUM.TipoMonster;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TipoMonsterExistenteValidator implements ConstraintValidator<TipoMonsterExistente, List<TipoMonster>> {
    private List<String> listaTipoMonsterStr;

    @Override
    public void initialize(TipoMonsterExistente annotation) {
        listaTipoMonsterStr = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(List<TipoMonster> tipoMonsters, ConstraintValidatorContext constraintValidatorContext) {
        if(tipoMonsters == null || tipoMonsters.isEmpty()) {
            return false;
        }

        return tipoMonsters.stream()
                .map(Enum::name)
                .allMatch(listaTipoMonsterStr::contains);
    }
}
