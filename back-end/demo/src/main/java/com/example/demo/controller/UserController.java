package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mapper.UserMapper;
import com.example.demo.vo.UserVO;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/users")
public class UserController {

		@Autowired
		UserMapper userMapper;
		
		@Autowired
		PasswordEncoder passwordEncoder;
		
		
		@PostMapping
		@ResponseBody//회원가입
		public String insertUser(@RequestBody UserVO user) {
			String id=user.getId();
			UserVO temp=userMapper.fetchUserByID(id);
			
			if(temp!=null) {
				return "실패";
				
			}
			else {
				user.setPw(passwordEncoder.encode(user.getPw()));
				userMapper.insertUser(user);
				
				return "성공";
			}
		}
		
		@GetMapping("/auth/{auth}")
		@ResponseBody//auth별 user
		public List<UserVO> userAuthList(@PathVariable String auth){
			//System.out.println(userMapper.userAuthList(auth));
			return userMapper.userAuthList(auth);
		}
		
		@GetMapping("/{id}")
		@ResponseBody//user가져오기
		public UserVO fetchUserById(@PathVariable String id){
			
			UserVO user=userMapper.fetchUserByID(id);
			if(user!=null&&user.getBan()!=null) {
				user.setBanList();
			}
			
			return user;
		}
		
		@DeleteMapping("/{id}")
		public void deleteUser(@PathVariable String id) {
			userMapper.deleteUser(id);
			
		}
		
		@PutMapping("/{id}")
		public void updateUser(@PathVariable String id, @RequestBody UserVO user) {
			
			UserVO temp=userMapper.fetchUserByID(user.getId());
			if(!temp.getPw().contentEquals(user.getPw())) {//비밀번호 변경시 암호화..
				user.setPw(passwordEncoder.encode(user.getPw()));
			}
			
			//System.out.println(user.getBan());

			userMapper.updateUser(user);
		}
		
		
		@GetMapping("/{id}/pw/{pw}")
		@ResponseBody
		public boolean EncodePw(@PathVariable String id,@PathVariable String pw) {
			
			Map<String, Boolean> map=new HashMap<String,Boolean>();
			UserVO temp=userMapper.fetchUserByID(id);
			boolean result=passwordEncoder.matches(pw, temp.getPw());
			
			return result;
		}
		
		@GetMapping("/auth/{auth}/cod/{cod}")
		@ResponseBody
		public List<UserVO> StudentList(@PathVariable String auth,@PathVariable int cod){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("auth",auth);
			map.put("cod", cod);
			return userMapper.StudentList(map);
		}
}
