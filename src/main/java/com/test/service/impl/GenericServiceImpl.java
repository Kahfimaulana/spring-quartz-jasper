package com.test.service.impl;

import com.test.dao.GenericDao;
import com.test.domain.BaseObject;
import com.test.service.GenericService;

import java.util.Date;
import java.util.List;

/**
 * Created by kahfi on 11/25/14.
 */
public class GenericServiceImpl<T extends BaseObject, PK> implements GenericService<T, PK> {
    protected GenericDao<T, PK> dao;

    public GenericServiceImpl(GenericDao<T, PK> dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        return dao.get(id);
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return dao.save(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id, Long userId) {
        dao.remove(id);
    }


    /**
     * {@inheritDoc}
     */
    public List<T> getCurrentPageRows(int startIndex, int pageSize, List<String> restrictions,
                                      List<String> orders, Object... params) {
        return dao.getCurrentPageRows(startIndex, pageSize, restrictions, orders, params);
    }

    /**
     * {@inheritDoc}
     */
    public int getRowCount(List<String> restrictions, Object... params) {
        return dao.getRowCount(restrictions, params);
    }

    public boolean isExist(Long id, Long updatedby, Date updated) {
        return dao.isExist(id, updatedby, updated);
    }

}
