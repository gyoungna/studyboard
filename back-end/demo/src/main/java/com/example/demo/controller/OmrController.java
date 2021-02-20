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

import com.example.demo.mapper.OmrMapper;
import com.example.demo.vo.OmrVO;


@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/omr")
public class OmrController {

		@Autowired
		OmrMapper omrMapper;
		

		
		
		
		
		@GetMapping("/cod/{cod}/ban/{ban}")
		@ResponseBody//user가져오기
		public List<OmrVO> fetchUserById(@PathVariable int cod, @PathVariable int ban){
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("cod",cod);
			map.put("ban", ban);
			

			List<OmrVO> temp= omrMapper.Omrlist(map);
			for(int i=0;i<temp.size();i++) {
				temp.get(i).setCo();
				temp.get(i).setCount();
			}
			return temp;
		}
		
		@DeleteMapping("/cod/{cod}/ban/{ban}/num/{num}")
		public void deleteOmr(@PathVariable int cod, @PathVariable int ban,@PathVariable int num) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("cod",cod);
			map.put("ban", ban);
			map.put("num",num);
			
			omrMapper.deleteOmr(map);
			
		}
		
		@GetMapping("/cod/{cod}/ban/{ban}/num/{num}")
		public OmrVO getOmr(@PathVariable int cod, @PathVariable int ban,@PathVariable int num) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("cod",cod);
			map.put("ban", ban);
			map.put("num",num);
			
			OmrVO omr= omrMapper.getOmr(map);
			omr.setCo();
			return omr;
			
		}
		
}
