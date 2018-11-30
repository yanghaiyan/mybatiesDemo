package com.mybatis.demo.service;

import java.util.List;

/**
 * @author as
 */
public interface IBaseService<T> {

    /**
     * 保存
     */
    Integer save(T t);

    /**
     * 删除
     */
    void delete(Integer id);

    /**
     * 通过id查询
     */
    T findByUserName(String userName);

    /**
     * 更新
     */
    void update(T t);

    /**
     * 返回所有信息
     */
    List<T> list();
}

