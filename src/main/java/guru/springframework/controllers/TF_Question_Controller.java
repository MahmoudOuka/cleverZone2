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

import guru.springframework.domain.MCQ_Question;
import guru.springframework.domain.TF_Game;
import guru.springframework.domain.TF_Question;

import guru.springframework.repositories.TFGameRepository;
import guru.springframework.repositories.TFQuestionRepository;

@RestController
public class TF_Question_Controller {
	TFQuestionRepository TFQuestionService;
	TFGameRepository TFGameService;

	
	@Autowired
	public TF_Question_Controller(TFQuestionRepository tFQuestionService, TFGameRepository tFGameService) {
		super();
		TFQuestionService = tFQuestionService;
		TFGameService = tFGameService;
	}
	   //-------------------Retrieve All TF Questions --------------------------------------------------------
    
	   @RequestMapping(value = "/tfquestion/", method = RequestMethod.GET)
	   public ResponseEntity<List<TF_Question>> listAllCourses() {
	       List<TF_Question> Questions = TFQuestionService.findAll();
	       if(Questions.isEmpty()){
	           return new ResponseEntity<List<TF_Question>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
	       }
	       return new ResponseEntity<List<TF_Question>>(Questions, HttpStatus.OK);
	   }
   //------------------- Update a Question -------------------------
   
   @PreAuthorize("hasRole('ROLE_TEACHER')")
   @RequestMapping(value = "/tfquestion/{id}", method = RequestMethod.PUT)
   public ResponseEntity<TF_Question> updateCourse(@PathVariable("id") long id, @RequestBody TF_Question Q) {
       System.out.println("Updating TF_Question " + id);
       TF_Question currentQuestion = TFQuestionService.findOne(id);
       UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       if (currentQuestion == null) {
           System.out.println("Question with ID " + id + " not found");
           return new ResponseEntity<TF_Question>(HttpStatus.NOT_FOUND);
           
       }else if(!currentQuestion.getTF_TheGame().getCourse().getTeacher().getUserName().equals(userDetails.getUsername())){
    	   System.out.println("tring to update Course not belonging to the user ");
    	   return new ResponseEntity<TF_Question>(HttpStatus.NOT_ACCEPTABLE);
    	   
       }
       
       currentQuestion.setAnswer(Q.getAnswer());
       currentQuestion.setQuestion(Q.getQuestion());
       
       TFQuestionService.save(currentQuestion);
       return new ResponseEntity<TF_Question>(currentQuestion, HttpStatus.OK);
   }

//------------------- Delete a T/F Question --------------------------------------------------------
   
   @PreAuthorize("hasRole('ROLE_TEACHER')")
   @RequestMapping(value = "/tfquestion/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<TF_Question> deleteCourse(@PathVariable("id") long id) {
	   
       System.out.println("Fetching & Deleting Course with ID " + id);
       UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       TF_Question currentQuestion = TFQuestionService.findOne(id);
       if (currentQuestion == null) {
           System.out.println("Unable to delete. Question with ID " + id + " not found");
           return new ResponseEntity<TF_Question>(HttpStatus.NOT_FOUND);
       }else if(!currentQuestion.getTF_TheGame().getCourse().getTeacher().getUserName().equals(userDetails.getUsername())){
    	   System.out.println("tring to update Question not belonging to the user ");
    	   return new ResponseEntity<TF_Question>(HttpStatus.NOT_ACCEPTABLE);
    	   
       }

       TFQuestionService.delete(id);
       return new ResponseEntity<TF_Question>(HttpStatus.NO_CONTENT);
   }
   
   //-------------------Create a T/F Question--------------------------------------------------------
   
   @PreAuthorize("hasRole('ROLE_TEACHER')")
   @RequestMapping(value = "/tfquestion/{gameId}", method = RequestMethod.POST)
   public ResponseEntity<Void> createUser(@PathVariable("gameId") long gameId,@RequestBody TF_Question Q,    UriComponentsBuilder ucBuilder) {
       System.out.println("Creating T/F Question ");
       if ( TFQuestionService.findByQuestion(Q.getQuestion()) != null  ) {
           System.out.println("The same Question already exist");
           return new ResponseEntity<Void>(HttpStatus.CONFLICT);
       }
       TF_Game Game = TFGameService.findOne(gameId);
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
