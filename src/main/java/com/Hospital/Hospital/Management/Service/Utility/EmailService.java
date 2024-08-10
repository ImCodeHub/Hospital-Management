package com.Hospital.Hospital.Management.Service.Utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Hospital.Hospital.Management.Entity.Profile;
import com.Hospital.Hospital.Management.Repository.ProfileRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ProfileRepository profileRepository;

    public void sendEmail(String email, String subject, String text) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true);

        javaMailSender.send(message);
    }

    public String greetingMail(String userName) {
        return "<div style='font-family: Arial, sans-serif; padding: 20px; background: linear-gradient(135deg, #ff9400 0%, #ff0084 100%); max-width: 600px; margin: 0 auto; border-radius: 10px;'>" +
        "<h2 style='text-align: center; color: #333;'>Welcome to Our Healthcare System</h2>" +
        "<div style='background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>" +
            "<p style='font-size: 16px;'><strong>Dear " + userName + ",</strong></p>" +
            "<p style='font-size: 16px;'>Thank you for registering with us. Your account has been successfully created.</p>" +
            "<p style='font-size: 16px;'>We're excited to have you on board! Our healthcare system offers a wide range of services to ensure you get the best care possible.</p>" +
            "<p style='font-size: 16px; margin-top: 20px;'>To get started, simply log in to your account and explore the features available to you.</p>" +
            "<p style='font-size: 16px; margin-top: 20px;'>If you have any questions, feel free to contact our support team.</p>" +
            "<p style='font-size: 16px;'>Best regards,<br>Your Healthcare Team</p>" +
        "</div>" +
        "<p style='text-align: center; font-size: 14px; color: #999; margin-top: 20px;'>This is an automated message, please do not reply.</p>" +
        "</div>";
    }

    public String otpMail(String otp) {
        return "<div style='font-family:Arial,sans-serif;'>"
            + "<h2 style='color:#333;'>Password Reset OTP</h2>"
            + "<p style='font-size:16px;color:#555;'>Dear User,</p>"
            + "<p style='font-size:16px;color:#555;'>Here is your One-Time Password (OTP) to reset your password:</p>"
            + "<p style='font-size:24px;color:#000;font-weight:bold;'>" + otp + "</p>"
            + "<p style='font-size:16px;color:#555;'>This OTP is <span style='color:#ff0000;'>valid for 2 minutes</span> only. If it has expired, please click on the Resend button.</p>"
            + "<p style='font-size:16px;color:#555;'>If you did not request a password reset, please ignore this email.</p>"
            + "<br><p style='font-size:16px;color:#555;'>Best regards,<br>Your Hospital Management Team</p>"
            + "</div>";
    }

    public String appointmentBookedEmail(String appointmentId, String patientName, String dateAndTime, String reason, String status) {
            
            String doctorName="N/A";
            List<Profile> profiles = profileRepository.findByType(reason);
            if (!profiles.isEmpty()) {
                Profile profile = profiles.get(0);
                doctorName=profile.getUser().getFirstName()+" "+profile.getUser().getLastName();
            }

            return "<div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4; max-width: 600px; margin: 0 auto; border-radius: 10px;'>" +
                "<h2 style='text-align: center; color: #333;'>Appointment Confirmation</h2>" +
                "<div style='background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>" +
                    "<p style='font-size: 16px;'><strong>Dear " + patientName + ",</strong></p>" +
                    "<p style='font-size: 16px;'>Your appointment has been successfully booked. Below are the details:</p>" +
                    "<table style='width: 100%; font-size: 16px; margin-top: 20px;'>" +
                        "<tr>" +
                            "<td style='padding: 10px 0;'><strong>Appointment ID:</strong></td>" +
                            "<td style='padding: 10px 0;'>" + appointmentId + "</td>" +
                        "</tr>" +
                        "<tr>" +
                            "<td style='padding: 10px 0;'><strong>Doctor's Name:</strong></td>" +
                            "<td style='padding: 10px 0;'> Dr."+ doctorName + "</td>" +
                        "</tr>" +
                        "<tr>" +
                            "<td style='padding: 10px 0;'><strong>Date and Time:</strong></td>" +
                            "<td style='padding: 10px 0;'>" + dateAndTime + "</td>" +
                        "</tr>" +
                        "<tr>" +
                            "<td style='padding: 10px 0;'><strong>Status:</strong></td>" +
                            "<td style='padding: 10px 0;'>" + status + "</td>" +
                        "</tr>" +
                    "</table>" +
                    "<p style='font-size: 16px; margin-top: 20px;'>Thank you for choosing our service. We look forward to seeing you at your appointment.</p>" +
                    "<p style='font-size: 16px;'>Best regards,<br>Your Healthcare Team</p>" +
                "</div>" +
                "<p style='text-align: center; font-size: 14px; color: #999; margin-top: 20px;'>This is an automated message, please do not reply.</p>" +
                "</div>";
        }

        public String appointmentCancellationEmail(String patientName, String reason, String dateAndTime){

            String doctorName="N/A";
            List<Profile> profiles = profileRepository.findByType(reason);
            if (!profiles.isEmpty()) {
                Profile profile = profiles.get(0);
                doctorName=profile.getUser().getFirstName()+" "+profile.getUser().getLastName();
            }

            return "<div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4; max-width: 600px; margin: 0 auto; border-radius: 10px;'>" +
            "<h2 style='text-align: center; color: #e74c3c;'>Appointment Cancellation Notice</h2>" +
            "<div style='background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>" +
                "<p style='font-size: 16px;'><strong>Dear " + patientName + ",</strong></p>" +
                "<p style='font-size: 16px;'>We regret to inform you that your scheduled appointment has been canceled. Below are the details of the canceled appointment:</p>" +
                "<table style='width: 100%; font-size: 16px; margin-top: 20px;'>" +
                    "<tr>" +
                        "<td style='padding: 10px 0;'><strong>Doctor's Name:</strong></td>" +
                        "<td style='padding: 10px 0;'>Dr." + doctorName + "</td>" +
                    "</tr>" +
                    "<tr>" +
                        "<td style='padding: 10px 0;'><strong>Date and Time:</strong></td>" +
                        "<td style='padding: 10px 0;'>" + dateAndTime + "</td>" +
                    "</tr>" +
                "</table>" +
                "<p style='font-size: 16px; margin-top: 20px;'>We apologize for any inconvenience this may cause. If you would like to reschedule, please contact us at your earliest convenience.</p>" +
                "<p style='font-size: 16px;'>Best regards,<br>Your Healthcare Team</p>" +
            "</div>" +
            "<p style='text-align: center; font-size: 14px; color: #999; margin-top: 20px;'>This is an automated message, please do not reply.</p>" +
            "</div>";
        }
}
