package com.example.dronetaskv1.seviceImpl;

import java.util.List;
// import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.dronetaskv1.constant.Message;
import com.example.dronetaskv1.dto.MedicationResDTO;
import com.example.dronetaskv1.exceptionHandler.DroneNotLoadingException;
import com.example.dronetaskv1.exceptionHandler.MedicationExistsException;
import com.example.dronetaskv1.mapper.MedicationMapper;
import com.example.dronetaskv1.model.Drone;
import com.example.dronetaskv1.model.Medication;
import com.example.dronetaskv1.repository.MedicationRepository;
import com.example.dronetaskv1.service.MedicationService;

@Service
public class MedicationServiceImpl implements MedicationService {

    private MedicationRepository medicationRepository;
    private MedicationMapper medicationMapper;

    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    @Override
    public void registerMedications(List<Medication> medications) {
        // for (Medication medication : medications) {
        //     Optional<Medication> savedMedication = medicationRepository.findById(medication.getMedicationCode());
        //     if(savedMedication.isPresent()) {
        //         Medication med = savedMedication.get();
        //         med.setNumberOfPackages(med.getNumberOfPackages() + medication.getNumberOfPackages());
        //         medicationRepository.save(med);
        //     }else {
        //         medicationRepository.save(medication);
        //     }
        // }
        medicationRepository.saveAll(medications);
    }

    @Override
    public double calculateMedicationsWeight(List<Medication> medications) {
        double total = 0;
        for (Medication medication : medications) {
            total += (medication.getMedicationWeight() * medication.getNumberOfPackages());
        }
        return total;
    }

    @Override
    public List<MedicationResDTO> findMedications(Drone drone) {
        List<Medication> medications = medicationRepository.findByDrone(drone);
        if(medications.size() == 0 || medications == null) {
            throw new DroneNotLoadingException(Message.DRONE_EMPTY);
        } else {
            return medicationMapper.medicationRestoDTO(medications);
        }
    }

    @Override
    public void throwExceptionIfMedicationExists(List<Medication> medications) {
        for (Medication medication : medications) {
            if(medicationRepository.existsById(medication.getMedicationCode())) {
                throw new MedicationExistsException(Message.MEDICATION_EXISTS);
            }
        }
    }

}
