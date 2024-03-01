package com.example.dronetaskv1.serviceImpl;

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

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    /**
     * Save medications to database by converting RegisterMedicationDTO to Medication
     *
     * @param medications List of medications needs to be saved to db
     * */
    @Override
    public List<Medication> registerMedications(List<Medication> medications) {
        medicationRepository.saveAll(medications);
        return medications;
    }

    /**
     * Calculate weight of medication that will be saved
     *
     * @param medications List of medications to calculate their weight
     * @return Total weight of medications*/
    @Override
    public double calculateMedicationsWeight(List<Medication> medications) {
        double total = 0;
        if(medications != null && medications.size() > 0) {
            for (Medication medication : medications) {
                total += (medication.getMedicationWeight() * medication.getNumberOfPackages());
            }
        }
        return total;
    }

    /**
     * Utility method to find medications loaded in specific drone
     *
     * @param drone the drone to get medications for it
     * @return List of medications for that drone
     */
    @Override
    public List<MedicationResDTO> findMedications(Drone drone) {
        List<Medication> medications = medicationRepository.findByDrone(drone);
        if(medications.size() == 0 || medications == null) {
            throw new DroneNotLoadingException(Message.DRONE_EMPTY);
        } else {
            return medicationMapper.medicationRestoDTO(medications);
        }
    }

    /**
     * Throw exception if medication already exists in db
     *
     * @param medications List of medications to check
     */
    @Override
    public void throwExceptionIfMedicationExists(List<Medication> medications) {
        for (Medication medication : medications) {
            if(medicationRepository.existsById(medication.getMedicationCode())) {
                throw new MedicationExistsException(Message.MEDICATION_EXISTS);
            }
        }
    }

}
