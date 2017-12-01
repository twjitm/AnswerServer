package com.hjq.answer.dao;

import com.hjq.answer.entity.Choices;

import java.util.List;

public interface ChoicesMapper {

    List<Choices> getAllChose();


    int deleteByPrimaryKey(Integer id);

    int insert(Choices record);

    int insertSelective(Choices record);

    Choices selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Choices record);

    int updateByPrimaryKey(Choices record);
}