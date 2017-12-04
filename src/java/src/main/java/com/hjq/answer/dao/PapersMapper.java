package com.hjq.answer.dao;

import com.hjq.answer.entity.Papers;

import java.util.List;

public interface PapersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Papers record);

    int insertSelective(Papers record);

    Papers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Papers record);

    int updateByPrimaryKey(Papers record);

    List<Papers> getAllPapers();
}