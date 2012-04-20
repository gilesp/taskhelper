package uk.co.vurt.hakken.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.security.HashUtils;

@Controller
@RequestMapping("/submissions")
public class SubmissionController {

	@RequestMapping(value = "job/{username}/{hmac}", method = RequestMethod.POST)
	@ResponseBody
	public boolean addEmployee(@PathVariable String username, @PathVariable String hmac, @RequestBody Submission submission) {
		Map<String, String>parameterMap = new HashMap<String, String>();
		parameterMap.put("username", username);
		
		boolean validRequest = HashUtils.validate(parameterMap, hmac);
		if(validRequest){
			//TODO: handle submissions
		}else {
			//TODO: send error message
		}
		return false;
	}

}
