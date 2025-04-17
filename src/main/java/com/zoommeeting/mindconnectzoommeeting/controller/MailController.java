package com.zoommeeting.mindconnectzoommeeting.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zoommeeting.mindconnectzoommeeting.entitites.Quiz1;
import com.zoommeeting.mindconnectzoommeeting.entitites.User;

import com.zoommeeting.mindconnectzoommeeting.repository.MeetingRepository;
import com.zoommeeting.mindconnectzoommeeting.services.MailServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class MailController {
	
	@Autowired
	private MailServices mailServices;
   
	@Autowired
	private MeetingRepository meetingRepository;
		
	public MailController(MailServices mailServices) {
		this.mailServices = mailServices;
		
	}
	
	@GetMapping("/index")
	public String index(HttpSession session, Model model) {
	    model.addAttribute("quiz1", new Quiz1());

	    // Check if user is logged in
	    User loggedInUser = (User) session.getAttribute("loggedInUser");
	    if (loggedInUser != null) {
	        model.addAttribute("username", loggedInUser.getUsername());
	    }

	    return "index";
	}


    @GetMapping("/mental")
    public String mental() {
        return "mental"; // Corresponds to mental.html
    }


    @GetMapping("/aboutus")
    public String aboutUs() {
        return "aboutus"; // Corresponds to aboutus.html
    }

    @GetMapping("/contactus")
    public String contactUs() {
        return "contactus"; // Corresponds to contactus.html
    }

    @GetMapping("/testimonials")
    public String testimonials() {
        return "testimonials"; // Corresponds to testimonials.html
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq"; // Corresponds to faq.html
    }

    @GetMapping("/learn")
    public String learn() {
        return "learn"; // Corresponds to learn.html
    }

    @GetMapping("/review")
    public String review() {
        return "review"; // Corresponds to review.html
    }
    
    @GetMapping("/quiz1")
    public String getQuiz1(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("quiz1", new Quiz1()); 
            return "quiz1";
        } else {
            return "redirect:/login";
        }
    }
    
    @GetMapping("/result")
    public String handleInvalidGetToResult() {
        return "result"; 
    }

    @PostMapping("/result")
    public String submitQuiz(@ModelAttribute("quiz1") Quiz1 quiz, Model model) {
    	System.out.println("Quiz submitted: " + quiz);  // Debugging line
        meetingRepository.save(quiz); // save to DB

        int totalScore = calculateScore(quiz);
        String message = (totalScore >= 10)
                ? "You may need to talk to a professional. Please book a meeting below."
                : "Your mental health seems okay. Stay positive and reach out if needed.";

        model.addAttribute("name", quiz.getPatientName());
        model.addAttribute("score", totalScore);
        model.addAttribute("message", message);

        return "result"; 
    }

    private int calculateScore(Quiz1 quiz) {
        return score(quiz.getQuestion1()) +
               score(quiz.getQuestion2()) +
               score(quiz.getQuestion3()) +
               score(quiz.getQuestion4()) +
               score(quiz.getQuestion5()) +
               score(quiz.getQuestion6()) +
               score(quiz.getQuestion7());
    }

    private int score(String answer) {
        if (answer == null) return 0;
        switch (answer.trim().toLowerCase()) {
            case "not at all": return 0;
            case "several days": return 1;
            case "more than half the days": return 2;
            case "nearly everyday": return 3;
            default: return 0;
        }
    }


    @GetMapping("/meeting")
	public String meetingPage() {
	    return "meeting"; 
	}


@PostMapping("/submit")
public String getMail(@RequestParam("patient-name") String name,
                      @RequestParam("patient-email") String email,
                      @RequestParam("patient-phone") String phone,
                      @RequestParam("meeting-date") String dateTimeStr,
                      Model model) {

    // Parse datetime-local input (format: "2025-04-10T14:00")
    LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
    LocalDate date = dateTime.toLocalDate();
    LocalTime time = dateTime.toLocalTime();

    // Create and populate Quiz object
    Quiz1 quiz = new Quiz1();
    quiz.setPatientName(name);
    quiz.setEmail(email);
    quiz.setPhone(phone);
    quiz.setDate(date);
    quiz.setTime(time);

    mailServices.saveMail(quiz);
    mailServices.sendMail(name, email, date.toString(), time.toString());

    return "submit";
}

	@GetMapping("/list")
	public String getList(Model model) {
		model.addAttribute("quizes",mailServices.getAllQuiz());
		return "quiz2";
	}
	
	
	

}