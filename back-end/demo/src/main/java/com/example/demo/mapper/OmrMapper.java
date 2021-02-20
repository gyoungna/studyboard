package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.OmrVO;

@Mapper
public interface OmrMapper {
	
	public List<OmrVO> Omrlist(Map<String, Object> map);
	public void deleteOmr(Map<String, Object> map);
	public OmrVO getOmr(Map<String, Object> map);
}
