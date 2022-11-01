package dualKey.dual.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> loginException(CustomException e) {
        System.out.println(e.getMessage());
        System.out.println("커스텀 예외 처리");
        HttpHeaders httpHeaders = new HttpHeaders();
        return ResponseEntity.ok(e.getMessage());
//        return new ResponseEntity<>(httpHeaders, HttpStatus.NOT_ACCEPTABLE);
    }

}
