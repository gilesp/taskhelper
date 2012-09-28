package uk.co.vurt.hakken.server.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import uk.co.vurt.hakken.domain.ErrorMessage;
import uk.co.vurt.hakken.server.exception.HakkenException;

/**
 * Parent class for the controllers that implement the REST Api.
 * 
 * Serves as a single place to put the error handling methods.
 * 
 * @author giles.paterson
 *
 */
public abstract class RESTController {

	@ExceptionHandler(HakkenException.class)
    public @ResponseBody ErrorMessage handleUncaughtException(Exception ex, WebRequest request, HttpServletResponse response) throws IOException {
            response.setHeader("Content-Type", "application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorMessage("Unknown error occurred.", ex.getMessage());
    }
}
