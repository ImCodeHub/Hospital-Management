package com.Hospital.Hospital.Management.Service.Implement;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.Appointment;
import com.Hospital.Hospital.Management.Entity.AppointmentStatus;
import com.Hospital.Hospital.Management.Entity.Profile;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotBookedException;
import com.Hospital.Hospital.Management.Exception.CustomException.AppoinmentNotFoundException;
import com.Hospital.Hospital.Management.Model.AppointmentModel;
import com.Hospital.Hospital.Management.Repository.AppointmentRepository;
import com.Hospital.Hospital.Management.Repository.ProfileRepository;
import com.Hospital.Hospital.Management.Service.AppointmentService;
import com.Hospital.Hospital.Management.Service.Utility.AgeCalculator;
import com.Hospital.Hospital.Management.Service.Utility.Validator;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Validator validator;

    public String generateAppoinmentId() {
        String appoinmentId = validator.generateRandomNumber(5);
        Optional<Appointment> optional = appointmentRepository.findByAppointmentId(appoinmentId);
        if (optional.isPresent()) {
            generateAppoinmentId();
        } else {
            return appoinmentId;
        }
        throw new RuntimeException("Id not generated");
    }

    @Override
    public String bookAppointment(Appointment appointment,User user) {
        try {
            String appointmentId = generateAppoinmentId();

            appointment.setAppointmentId(appointmentId);
            appointment.setUserName(user.getFirstName()+" "+user.getLastName());
            appointment.setReason(appointment.getReason());
            appointment.setDateOfBirth(appointment.getDateOfBirth());
            appointment.setAppointmentDate(appointment.getAppointmentDate());
            appointment.setAppointmentTime(appointment.getAppointmentTime());
            appointment.setLocation(appointment.getLocation());
            appointment.setPatient(user);
            appointment.setStatus(AppointmentStatus.PENDING);
            // to save in db
            appointmentRepository.save(appointment);
            return "your appoinment has been booked.";
        } catch (AppoinmentNotBookedException e) {
            throw new AppoinmentNotBookedException("appoinment not booked please try again.");
        }

    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = appointmentRepository.findAll();
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

        List<Appointment> appointments = appointmentRepository.findByYearAndMonth(currentYear, currentMonth, userId);
        
        for (Appointment appointment : appointments) {
            AppointmentModel appointmentModel = new AppointmentModel();
            appointmentModel.setAppointmentId(appointment.getAppointmentId());
            appointmentModel
                    .setPatient(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
            appointmentModel.setReason(appointment.getReason());

            List<Profile> profiles = profileRepository.findByType(appointment.getReason());
            
            if(!profiles.isEmpty()){
                Profile profile = profiles.get(0);
                appointmentModel.setDoctor(profile.getUser().getFirstName()+" "+profile.getUser().getLastName());
                appointmentModel.setDoctorId(profile.getUser().getUserId());
            }else{
                appointmentModel.setDoctor("N/A");
                appointmentModel.setDoctorId("N/A");
            }
            
            int age = AgeCalculator.calculateAge(appointment.getDateOfBirth());
            appointmentModel.setAge(age);
            appointmentModel.setAppointmentDate(appointment.getAppointmentDate());
            appointmentModel.setDateOfBooking(appointment.getDateOfBooking());
            appointmentModel.setLocation(appointment.getLocation());
            appointmentModel.setStatus(appointment.getStatus());
            appointmentModel.setAppointmentTime(appointment.getAppointmentTime());

            list.add(appointmentModel);
        }
        return list;

    }

    @Override
    public String cancelAppointment(String appointmentId) {
        Optional<Appointment> optional = appointmentRepository.findByAppointmentId(appointmentId);
        if (optional.isPresent()) {
            Appointment appointment = optional.get();

            appointment.setStatus(AppointmentStatus.CANCEL);
            appointmentRepository.save(appointment);
            return "Your appointment has been canceled.";
        }
        throw new AppoinmentNotFoundException("Appointment not found by this Id: " + appointmentId);
    }

    @Override
    public List<String> getReasonTypes() {
        List<String> types = profileRepository.findTypes();
        return types;
    }
}
