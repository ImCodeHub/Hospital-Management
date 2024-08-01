package com.Hospital.Hospital.Management.Service.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email, String subject, String text ) throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true);

        javaMailSender.send(message);
    }

    public String greetingMail(String userName){
        return "<p>Thank you, <b>" + userName + "</b>, for registering with us.</p>"
                + "<p>You can book appointments with our doctors and surgeons. For more details, explore our website.</p>"
                + "<p>Stay healthy.</p>"
                + "<p><b>Regards,</b><br>Hospital Name</p>";
    }

    public String otpMail(String otp){
        return "<p>This is your OTP (one time password) <b style='color:black;'>" + otp + "</b> to reset your password.</p>"
            + "<p style='color:red;'>This OTP is valid for 2 minutes only.</p>"
            + "<p>If the OTP has expired, click on the Resend button.</p>";
    }
}
