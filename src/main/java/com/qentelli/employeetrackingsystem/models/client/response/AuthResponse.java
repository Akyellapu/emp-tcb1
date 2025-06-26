package com.qentelli.employeetrackingsystem.models.client.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class AuthResponse<T> {

   String code;
   RequestProcessStatus statusType;
   private String message;
   private T data;
   LocalDateTime timestamp;
   HttpStatusCode errorCode;
   String errorDescription;
   
   public AuthResponse(Integer code,RequestProcessStatus statusType,LocalDateTime timestamp,String message, T data) {
	   this.code=code.toString();
	   this.statusType= statusType;
	   this.timestamp = timestamp;
	   this.message = message;
	   this.data = data;
   }
}
