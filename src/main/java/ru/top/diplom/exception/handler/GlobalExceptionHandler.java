package ru.top.diplom.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.top.diplom.exception.balance.BalanceTooLowException;
import ru.top.diplom.exception.city.CityAlreadyExistsException;
import ru.top.diplom.exception.city.CityNotFoundException;
import ru.top.diplom.exception.club_pricing.ClubPricingNotFoundException;
import ru.top.diplom.exception.computer.ComputerNotFoundException;
import ru.top.diplom.exception.computer_club.ComputerClubAllreadyExistsException;
import ru.top.diplom.exception.computer_club.ComputerClubNotFoundException;
import ru.top.diplom.exception.computer_specification.ComputerSpecificationNotFoundException;
import ru.top.diplom.exception.reservation.ReservationAlreadyException;
import ru.top.diplom.exception.user.UserAllreadyExistsException;
import ru.top.diplom.exception.user.UserNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAllreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAllreadyExistsException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ComputerNotFoundException.class)
    ResponseEntity<ErrorResponse> handleComputerNotFoundException(ComputerNotFoundException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ComputerClubNotFoundException.class)
    ResponseEntity<ErrorResponse> handleComputerClubNotFoundException(ComputerClubNotFoundException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ComputerClubAllreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(ComputerClubAllreadyExistsException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ComputerSpecificationNotFoundException.class)
    ResponseEntity<ErrorResponse> handleComputerClubNotFoundException(ComputerSpecificationNotFoundException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handDateTimeParseException(DateTimeParseException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handValidationException(MethodArgumentNotValidException ex){

        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errorMessage)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handeBadCredentialsException(){

        String message = "Неверный пароль";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(message)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handeCityNotFoundException(CityNotFoundException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handeCityAlreadyExistsException(CityAlreadyExistsException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReservationAlreadyException.class)
    public ResponseEntity<ErrorResponse> handeReservationAlreadyExistsException(ReservationAlreadyException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BalanceTooLowException.class)
    public ResponseEntity<ErrorResponse> handeBalanceTooLowException(BalanceTooLowException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClubPricingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handeBalanceTooLowException(ClubPricingNotFoundException ex){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
