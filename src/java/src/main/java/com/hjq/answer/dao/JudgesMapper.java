package com.hjq.answer.dao;

import com.hjq.answer.entity.Judges;

public interface JudgesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Judges record);

    int insertSelective(Judges record);

    Judges selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Judges record);

    int updateByPrimaryKey(Judges record);
}