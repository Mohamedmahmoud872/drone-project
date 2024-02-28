package com.example.dronetaskv1.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.dto.RegisterMedicationsDTO;
import com.example.dronetaskv1.model.Medication;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    MedicationResDTO medicationRestoDTO(Medication medication);

    List<MedicationResDTO> medicationRestoDTO(List<Medication> medications);

    Medication dtoToMedication(RegisterMedicationsDTO medication);

    List<Medication> resDtoToMedications(List<MedicationResDTO> medications);

    List<Medication> dtosToMedications(List<RegisterMedicationsDTO> medications);

}
