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
	     String ban; 
	     int banList[];
	     int cod;
	 
	public void setBanList() {
		String[] splited=ban.split(";");
		banList=new int[splited.length];
		for(int i=0;i<splited.length;i++) {
			banList[i]=Integer.parseInt(splited[i]);
			
		}
	}
	public int[] getBanList() {
		return this.banList;
	}
}
