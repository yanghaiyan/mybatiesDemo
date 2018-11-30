package com.mybatis.demo.dao;

import java.util.List;

public interface BaseMapper<T> {

    Integer save(T t);

    void delete(Integer id);

    T findByUserName(String userName);

    void update(T t);

    List<T> list();

}
