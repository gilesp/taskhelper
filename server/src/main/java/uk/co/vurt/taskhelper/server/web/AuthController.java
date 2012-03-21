package uk.co.vurt.taskhelper.server.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.taskhelper.server.domain.user.Person;

@Controller
public class AuthController {

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ResponseBody
	public Object authenticate(	@RequestParam(value = "username", required = true) String username, 
								@RequestParam(value = "password", required = true) String password){
		if(username != null && password != null){
			Person person = Person.findPeopleByUsernameLike(username).getSingleResult();
			if(person != null){
				if(person.getPassword().equals(password)){
					//we're authenticated.
					return password; //TODO: implement proper login and auth tokens. For now just return the password as the auth token
				} else {
					return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
				}
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
}
