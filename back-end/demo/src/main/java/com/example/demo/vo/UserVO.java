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
	     //String banList[];
	     int cod;
	     String authkey;
	     
	/*public void setBanList() {
		String[] splited=ban.split(";");
		banList=new String[splited.length];
		for(int i=0;i<splited.length;i++) {
			banList[i]=splited[i];
			
		}
	}
	public String[] getBanList() {
		return this.banList;
	}*/
}
