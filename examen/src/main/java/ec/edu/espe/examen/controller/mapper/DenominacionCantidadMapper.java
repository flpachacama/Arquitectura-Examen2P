package ec.edu.espe.examen.controller.mapper;

import ec.edu.espe.examen.controller.dto.DenominacionCantidadDTO;
import ec.edu.espe.examen.model.DenominacionCantidad;

import java.util.List;
import java.util.stream.Collectors;

public class DenominacionCantidadMapper {

    public static DenominacionCantidad toEntity(DenominacionCantidadDTO dto) {
        return DenominacionCantidad.builder()
                .denominacion(dto.getDenominacion())
                .cantidad(dto.getCantidad())
                .build();
    }

    public static DenominacionCantidadDTO toDTO(DenominacionCantidad entity) {
        return new DenominacionCantidadDTO(
                entity.getDenominacion(),
                entity.getCantidad()
        );
    }

    public static List<DenominacionCantidad> toEntityList(List<DenominacionCantidadDTO> dtoList) {
        return dtoList.stream().map(DenominacionCantidadMapper::toEntity).collect(Collectors.toList());
    }

    public static List<DenominacionCantidadDTO> toDTOList(List<DenominacionCantidad> entityList) {
        return entityList.stream().map(DenominacionCantidadMapper::toDTO).collect(Collectors.toList());
    }
}