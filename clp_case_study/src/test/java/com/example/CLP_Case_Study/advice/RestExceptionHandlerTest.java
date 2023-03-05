package com.example.CLP_Case_Study.advice;

import com.example.CLP_Case_Study.exceptions.IllegalArgumentException;
import com.example.CLP_Case_Study.exceptions.NotLoggedInException;
import com.example.CLP_Case_Study.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RestExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Test
    public void handleNotLoggedInException_shouldReturnUnauthorized() {
        NotLoggedInException notLoggedInException = new NotLoggedInException();
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleNotLoggedInException(request, notLoggedInException);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void handleIllegalArgumentException_shouldReturnBadRequest() {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleIllegalArgumentException(request, illegalArgumentException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void handleResourceNotFoundException_shouldReturnNotFound() {
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleResourceNotFoundException(request, resourceNotFoundException);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}