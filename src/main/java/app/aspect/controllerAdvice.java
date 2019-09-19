package app.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class controllerAdvice {
    @AfterReturning(pointcut = "execution(* app.controller.SearchController.search(..))", returning = "result")
    public void afterHomePage(String result) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(result);
    }
}
