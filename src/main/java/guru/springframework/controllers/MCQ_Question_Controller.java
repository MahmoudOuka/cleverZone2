package guru.springframework.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.domain.Course;
import guru.springframework.domain.MCQ_Game;
import guru.springframework.domain.MCQ_Question;
import guru.springframework.repositories.MCQGameRepository;
import guru.springframework.repositories.MCQQuestionRepository;

@RestController
public class MCQ_Question_Controller {
	MCQQuestionRepository MCQQuestionService;
	MCQGameRepository MCQGameService;

	
	@Autowired
	   public MCQ_Question_Controller(MCQQuestionRepository mCQQuestionService, MCQGameRepository mCQGameService) {
			super();
			MCQQuestionService = mCQQuestionService;
			MCQGameService = mCQGameService;
		}
	
	   //-------------------Retrieve All MCQ Questions --------------------------------------------------------
    
	   @RequestMapping(value = "/MCQQuestion/", method = RequestMethod.GET)
	   public ResponseEntity<List<MCQ_Question>> listAllCourses() {
	       List<MCQ_Question> Questions = MCQQuestionService.findAll();
	       if(Questions.isEmpty()){
	           return new ResponseEntity<List<MCQ_Question>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
	       }
	       return new ResponseEntity<List<MCQ_Question>>(Questions, HttpStatus.OK);
	   }
   //------------------- Update a MCQQuestion -------------------------
   
   @PreAuthorize("hasRole('ROLE_TEACHER')")
   @RequestMapping(value = "/mcqquestion/{id}", method = RequestMethod.PUT)
   public ResponseEntity<MCQ_Question> updateCourse(@PathVariable("id") long id, @RequestBody MCQ_Question Q) {
       System.out.println("Updating MCQQuestion " + id);
       MCQ_Question currentQuestion = MCQQuestionService.findOne(id);
       UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       if (currentQuestion == null) {
           System.out.println("Question with ID " + id + " not found");
           return new ResponseEntity<MCQ_Question>(HttpStatus.NOT_FOUND);
           
       }else if(!currentQuestion.getMCQ_TheGame().getCourse().getTeacher().getUserName().equals(userDetails.getUsername())){
    	   System.out.println("tring to update Course not belonging to the user ");
    	   return new ResponseEntity<MCQ_Question>(HttpStatus.NOT_ACCEPTABLE);
    	   
       }
       
       currentQuestion.setAnswer(Q.getAnswer());
       currentQuestion.setChoices(Q.getChoices());
       currentQuestion.setQuestion(Q.getQuestion());
       
       MCQQuestionService.save(currentQuestion);
       return new ResponseEntity<MCQ_Question>(currentQuestion, HttpStatus.OK);
   }

//------------------- Delete a MCQQuestion --------------------------------------------------------
   
   @PreAuthorize("hasRole('ROLE_TEACHER')")
   @RequestMapping(value = "/mcqquestion/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<MCQ_Question> deleteCourse(@PathVariable("id") long id) {
	   
       System.out.println("Fetching & Deleting Course with ID " + id);
       UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       MCQ_Question currentQuestion = MCQQuestionService.findOne(id);
       if (currentQuestion == null) {
           System.out.println("Unable to delete. Course with ID " + id + " not found");
           return new ResponseEntity<MCQ_Question>(HttpStatus.NOT_FOUND);
       }else if(!currentQuestion.getMCQ_TheGame().getCourse().getTeacher().getUserName().equals(userDetails.getUsername())){
    	   System.out.println("tring to update Course not belonging to the user ");
    	   return new ResponseEntity<MCQ_Question>(HttpStatus.NOT_ACCEPTABLE);
    	   
       }

       MCQQuestionService.delete(id);
       return new ResponseEntity<MCQ_Question>(HttpStatus.NO_CONTENT);
   }
   
   //-------------------Create a MCQQuestion--------------------------------------------------------
   
   @PreAuthorize("hasRole('ROLE_TEACHER')")
   @RequestMapping(value = "/mcqquestion/{gameId}", method = RequestMethod.POST)
   public ResponseEntity<Void> createUser(@PathVariable("gameId") long gameId,@RequestBody MCQ_Question Q,    UriComponentsBuilder ucBuilder) {
       System.out.println("Creating MCQ Question ");
       if ( MCQQuestionService.findByQuestion(Q.getQuestion()) != null  ) {
           System.out.println("The same Question already exist");
           return new ResponseEntity<Void>(HttpStatus.CONFLICT);
       }
       MCQ_Game Game = MCQGameService.findOne(gameId);
       if(Game == null){
    	   System.out.println("A Game with id " + gameId + " was not found");
    	   return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
       }
       UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       if(!Game.getCourse().getTeacher().getUserName().equals(userDetails.getUsername())){
    	   System.out.println("tring to update Course not belonging to the user ");
    	   return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
       }
       Game.addQuestion(Q);
       HttpHeaders headers = new HttpHeaders();
       //headers.setLocation(ucBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri());
       return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
   }
}
