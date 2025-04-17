package com.zoommeeting.mindconnectzoommeeting.services;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import com.zoommeeting.mindconnectzoommeeting.entitites.Quiz1;

import com.zoommeeting.mindconnectzoommeeting.repository.MeetingRepository;

@Service
public class MailServices {
	
	private JavaMailSender javaMailSender;
    private MeetingRepository meetingRepository;
    
	public MailServices(JavaMailSender javaMailSender, MeetingRepository meetingRepository) {
		this.javaMailSender = javaMailSender;
		this.meetingRepository = meetingRepository;
	}
    
	public Quiz1 saveMail(Quiz1 quiz1) {
		return meetingRepository.save(quiz1);
	}
	
	public List<Quiz1> getAllQuiz(){
		return meetingRepository.findAll();
	}
	
	public void sendMail(String name, String email, String date, String time) {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(email);
	    message.setSubject("Your Upcoming Consultation – Google Meet Link Inside");

	    message.setText("Dear " + name + ",\n\n"
	            + "Thank you for booking your consultation with MindConnect.\n\n"
	            + "Here are your session details:\n\n"
	            + "📅 Date: " + date + "\n"
	            + "⏰ Time: " + time + "\n"
	            + "🔗 Google Meet Link: https://meet.google.com/yyo-bbvh-mmi"+"\n\n"
	            + "Please try to join a few minutes early. If you need any assistance, feel free to reach out.\n\n"
	            + "Looking forward to speaking with you!\n\n"
	            + "Warm regards,\n"
	            + "MindConnect");

	    try {
	        javaMailSender.send(message);
	        System.out.println("Email sent successfully to " + email);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

		
		//javaMailSender.send(message);}
}
