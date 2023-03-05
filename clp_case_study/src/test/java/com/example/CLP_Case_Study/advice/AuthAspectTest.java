package com.example.CLP_Case_Study.advice;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.CLP_Case_Study.models.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.CLP_Case_Study.annotations.Authorized;
import com.example.CLP_Case_Study.exceptions.NotLoggedInException;

@ExtendWith(MockitoExtension.class)
public class AuthAspectTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Mock
    private Authorized authorizedAnnotation;

    @InjectMocks
    private AuthAspect authAspect;

    @BeforeEach
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
    }

    @Test
    public void testAuthenticate_UserNotLoggedIn() throws Throwable {
        when(session.getAttribute("user")).thenReturn(null);

        assertThrows(NotLoggedInException.class, () -> authAspect.authenticate(joinPoint, authorizedAnnotation));
    }

    @Test
    public void testAuthenticate_UserLoggedIn() throws Throwable {
        when(session.getAttribute("user")).thenReturn(mock(User.class));
        when(joinPoint.proceed(any())).thenReturn(null);

        authAspect.authenticate(joinPoint, authorizedAnnotation);

        verify(joinPoint).proceed(any());
    }

}
