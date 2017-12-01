package com.hjq.answer.dao;

import com.hjq.answer.entity.Qtypes;

public interface QtypesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Qtypes record);

    int insertSelective(Qtypes record);

    Qtypes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Qtypes record);

    int updateByPrimaryKey(Qtypes record);
}