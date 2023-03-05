package com.example.CLP_Case_Study.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

    @Mock
    private Logger logger;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;
    @Mock
    private TestController testController;
    @Mock
    private TestService testService;

    @InjectMocks
    private LoggingAspect loggingAspect;

    //@Test
    public void testLogControllerMethods() throws Exception {
        // Setup
        given(joinPoint.getTarget()).willReturn(testController);
        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.toString()).willReturn("testMethod");

        // Execute
        loggingAspect.logControllerMethods(joinPoint);

        // Verify
        verify(logger).info("-=CONTROLLER=-");
        verify(logger).info("com.example.CLP_Case_Study.advice.com.example.CLP_Case_Study.advice.TestController invoked testMethod");
    }

    //@Test
    public void testLogServiceMethods() throws Exception {
        // Setup
        given(joinPoint.getTarget()).willReturn(testService);
        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.toString()).willReturn("testMethod");

        // Execute
        loggingAspect.logServiceMethods(joinPoint);

        // Verify
        verify(logger).info("-=SERVICE=-");
        verify(logger).info("com.example.CLP_Case_Study.advice.com.example.CLP_Case_Study.advice.TestService invoked testMethod");
    }
}
