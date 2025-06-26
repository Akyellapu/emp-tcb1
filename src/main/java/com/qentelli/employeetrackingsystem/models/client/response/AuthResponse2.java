package com.qentelli.employeetrackingsystem.models.client.response;

//import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class AuthResponse2<T> {

   String code;
   RequestProcessStatus statusType;
   private String message;
   HttpStatusCode errorCode;
   String errorDescription;
   
   public AuthResponse2(Integer code,RequestProcessStatus statusType,String message) {
	   this.code=code.toString();
	   this.statusType= statusType;
	   this.message = message;
   }
}
