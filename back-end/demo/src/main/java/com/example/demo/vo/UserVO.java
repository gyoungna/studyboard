package com.example.demo.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserVO {
		String id;
		 String pw;
	     String name;
	     String email;
	     String auth;
	     int ban;
}