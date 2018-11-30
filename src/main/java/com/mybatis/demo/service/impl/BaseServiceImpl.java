package com.mybatis.demo.service.impl;

import com.mybatis.demo.dao.BaseMapper;
import com.mybatis.demo.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;


    @Override
    public Integer save(T t) {
        return baseMapper.save(t);
    }

    @Override
    public void delete(Integer id) {
        baseMapper.delete(id);
    }

    @Override
    public T findByUserName(String userName) {
        return baseMapper.findByUserName(userName);
    }

    @Override
    public void update(T t) {
       baseMapper.update(t);
    }

    @Override
    public List<T> list() {
        return baseMapper.list();
    }
}
