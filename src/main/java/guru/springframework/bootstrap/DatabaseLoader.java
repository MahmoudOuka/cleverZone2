package guru.springframework.bootstrap;

import guru.springframework.domain.Course;
import guru.springframework.domain.MCQ_Game;
import guru.springframework.domain.MCQ_Question;
import guru.springframework.domain.TF_Game;
import guru.springframework.domain.TF_Question;
import guru.springframework.domain.User;
import guru.springframework.repositories.CourseRepository;
import guru.springframework.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userService;
    private CourseRepository CourseService;

    
  //  private Logger log = Logger.getLogger(DatabaseLoader.class);


    @Autowired
    public void setProductRepository(UserRepository userService,CourseRepository courseService ) {
        this.userService = userService;
        this.CourseService = courseService;
    }
    
    
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    	ArrayList<User> UsersData = new ArrayList<User>(); 
    	User MCQTeacher = new User("supernour","nour" , "ahmed" , "pssword" ,new String[] {"ROLE_TEACHER","ROLE_ADMIN"} );
    	User TFTeacher = new User("supernour1","nour1" , "ahmed1" , "pssword1" ,new String[] {"ROLE_TEACHER"} );
		
				
		
		ArrayList<Course> CoursesData = new ArrayList<Course>(); 
		Course first = new Course("Arabic","the aradbic","thea arcbi");
		Course second = new Course("Arabidfghfdc","the arabgfdhfgic","tsdghea arcbi");
		Course third = new Course("Arabhdfghic","the arabfghdhic","thea ardsdfbcbi");
		
        
		
		
        MCQ_Game theFirstGame = new MCQ_Game("thetest", "the desc", "imageSrc1");
        TF_Game theSecondGame = new TF_Game("thetessdfst", "thesdv desc", "imageSrc2");
        MCQ_Game theThirdGame = new MCQ_Game("thetsdsest", "the desdvsc", "isdmageSrc3");
       
        
        TF_Question firstQuestion = new TF_Question("Question1", "answer1");
        String choices [] = new String [4];
        choices[0]="1";
        choices[1]="2";
        choices[2]="3";
        choices[3]="4";
        MCQ_Question secondQuestion = new MCQ_Question("Question2", "Answer",choices);
        
        theFirstGame.addQuestion(secondQuestion);
        theSecondGame.addQuestion(firstQuestion);
       
  	
        
        first.addContents(theFirstGame);
        second.addContents(theSecondGame);
        third.addContents(theThirdGame);
        
        System.out.println(theFirstGame.getName());
        CoursesData.add(first);
        CoursesData.add(second);
        CoursesData.add(third);
        
        MCQTeacher.addCourses(first);
        MCQTeacher.addCourses(third);
        TFTeacher.addCourses(second);
        UsersData.add(TFTeacher);
        UsersData.add(MCQTeacher);
        userService.save(UsersData);

    }
}
