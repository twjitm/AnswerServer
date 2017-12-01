package com.hjq.answer.dao;

import com.hjq.answer.entity.Explain;

import java.util.List;

public interface ExplainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Explain record);

    int insertSelective(Explain record);

    Explain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Explain record);

    int updateByPrimaryKey(Explain record);

    List<Explain> getAllExceptionBytype(int type);

    Explain getExceptionById(int id, int type);
}