package com.Hospital.Hospital.Management.Service.Implement;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.Patient;
import com.Hospital.Hospital.Management.Repository.PatientRepository;
import com.Hospital.Hospital.Management.Service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    @Override
    public String savePatient(Patient patient) {
        if(!Patient.isEmpty){
            String uuid = UUID.randomUUID().toString().replace("-","" ).substring(0, 8);
            String patientId = "PAT"+uuid;
            patient.setPatientId(patientId);
            patientRepository.save(patient);
            return "Patient has been saved successfully.";
        }
        throw new UnsupportedOperationException("Unimplemented method 'savePatient'");
    }
    
}
