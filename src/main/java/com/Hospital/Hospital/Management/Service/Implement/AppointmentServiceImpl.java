package com.Hospital.Hospital.Management.Service.Implement;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.Appointment;
import com.Hospital.Hospital.Management.Entity.AppointmentStatus;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotBookedException;
import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotFoundException;
import com.Hospital.Hospital.Management.Model.AppointmentModel;
import com.Hospital.Hospital.Management.Repository.AppoinmentRepository;
import com.Hospital.Hospital.Management.Service.AppointmentService;
import com.Hospital.Hospital.Management.Service.Utility.Validator;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppoinmentRepository appoinmentRepository;

    @Autowired
    private Validator validator;

    public String generateAppoinmentId() {
        String appoinmentId = validator.generateRandomNumber(5);
        Optional<Appointment> optional = appoinmentRepository.findByAppoinmentId(appoinmentId);
        if (optional.isPresent()) {
            generateAppoinmentId();
        } else {
            return appoinmentId;
        }
        throw new RuntimeException("Id not generated");
    }

    @Override
    public String bookAppointment(User user) {
        try {
            String appoinmentId = generateAppoinmentId();
            Appointment appoinment = new Appointment();
            appoinment.setAppoinmentId(appoinmentId);
            appoinment.setPatient(user);
            appoinment.setStatus(AppointmentStatus.PENDING);
            // to save in db
            appoinmentRepository.save(appoinment);
            return "your appoinment has been booked.";
        } catch (AppoinmentNotBookedException e) {
            throw new AppoinmentNotBookedException("appoinment not booked please try again.");
        }

    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = appoinmentRepository.findAll();
        if (list.isEmpty()) {
            throw new AppoinmentNotFoundException("Appoinment not found.");
        } else {
            return list;
        }
    }

    @Override
    public List<AppointmentModel> getRecentAppointment(String userId) {
        List<AppointmentModel> list = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        List<Appointment> appoinments = appoinmentRepository.findByYearAndMonth(currentYear, currentMonth, userId);

        for (Appointment appoinment : appoinments) {
            AppointmentModel appoinmentModel = new AppointmentModel();
            appoinmentModel.setAppoinmentId(appoinment.getAppoinmentId());
            appoinmentModel
                    .setPatient(appoinment.getPatient().getFirstName() + " " + appoinment.getPatient().getLastName());
            appoinmentModel.setReason(appoinment.getReason());

            // Null check for doctor
            if (appoinment.getDoctor() != null) {
                appoinmentModel.setDoctor(appoinment.getDoctor().getFirstName() + " " + appoinment.getDoctor().getLastName());
            } else {
                appoinmentModel.setDoctor("No doctor assigned");
            }
            appoinmentModel.setAppoinmentDate(appoinment.getAppoinmentDate());
            appoinmentModel.setDateOfBooking(appoinment.getDateOfBooking());
            appoinmentModel.setLocation(appoinment.getPatient().getAddress());
            appoinmentModel.setStatus(appoinment.getStatus());

            list.add(appoinmentModel);
        }
        return list;

    }

    @Override
    public String cancelAppointment(String appoinmentId) {
        Optional<Appointment> optional = appoinmentRepository.findByAppoinmentId(appoinmentId);
        if(optional.isPresent()){
            Appointment appoinment = optional.get();

            appoinment.setStatus(AppointmentStatus.CANCEL);
            appoinmentRepository.save(appoinment);
            return "Your appoinment has been canceled.";
        }
        throw new AppoinmentNotFoundException("Appoinment not found by this Id :"+ appoinmentId);
    }
}
