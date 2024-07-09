package com.Hospital.Hospital.Management.Service;

import java.util.List;

import com.Hospital.Hospital.Management.Entity.Appointment;
import com.Hospital.Hospital.Management.Entity.User;
import com.Hospital.Hospital.Management.Model.AppointmentModel;

public interface AppointmentService {

    public String bookAppointment(User user);

    public List<Appointment> getAllAppointments();

    public List<AppointmentModel> getRecentAppointment(String userId);

    public String cancelAppointment(String appoinmentId);
} 

