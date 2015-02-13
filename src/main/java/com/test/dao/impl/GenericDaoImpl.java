package com.test.dao.impl;

import com.test.dao.GenericDao;
import com.test.domain.BaseObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * Created by Kahfi on 1/16/2015.
 */
public class GenericDaoImpl<T extends BaseObject, PK> extends SimpleJdbcDaoSupport
        implements GenericDao<T, PK> {
    //-- sql related components
    protected final String tableName;

    protected StringBuilder insertSql;
    protected StringBuilder updateSql;
    private final String deleteSql;

    private final String getSql;
    private final String getAllSql;

    //-- default resultset to bean mapper
    protected RowMapper<T> defaultMapper;

    public GenericDaoImpl(String tableName, DataSource dataSource) {
        this.tableName = tableName;
        getAllSql = "select * from " + tableName;
        getSql = getAllSql + " where id = ? ";
        deleteSql = "delete from " + tableName + " where id = ? ";

        setDataSource(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return getSimpleJdbcTemplate().query(getAllSql, defaultMapper);
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        T object = null;

        try {
            object = getSimpleJdbcTemplate().queryForObject(getSql, defaultMapper, id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * {@inheritDoc}
     */
    public Boolean exists(PK id) {
        Boolean result = true;
        String sql = "select id from " + tableName + " where id = ? ";
        try {
            getSimpleJdbcTemplate().queryForInt(sql, id);
        } catch (EmptyResultDataAccessException e) {
            result = false;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        if (object.getId() == null) {
            //-- insert
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(object);
            new NamedParameterJdbcTemplate(getDataSource()).update(insertSql.toString(), parameters, keyHolder);
            object.setId((Long) keyHolder.getKeyList().get(0).get("id"));
        }
        else {
            //-- update
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(object);
            new NamedParameterJdbcTemplate(getDataSource()).update(updateSql.toString(), parameters);
        }

        return object;
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        getSimpleJdbcTemplate().update(deleteSql, id);
    }

    /**
     * Get current page rows after sort and restriction being applied.
     *
     * @param startIndex   starting index from resultset
     * @param pageSize     how many rows to be retrieved
     * @param restrictions valid sql predicate
     * @param orders       valid order by column
     * @param params       bind variable value
     */
    public List<T> getCurrentPageRows(int startIndex, int pageSize, List<String> restrictions,
                                      List<String> orders, Object... params) {
        String sql = getAllSql + " ";

        if (restrictions != null && restrictions.size() != 0) {
            sql += "where ";
            for (String restriction : restrictions) {
                sql += restriction;
                sql += " and ";
            }

            sql = sql.substring(0, sql.length()-4);
        }

        if (orders != null && orders.size() != 0) {
            sql += "order by ";
            for (String order : orders) {
                sql += order;
                sql += ", ";
            }

            sql = sql.substring(0, sql.length()-2);
        }

        sql += " limit ? offset ?";
        int pageNo = startIndex / pageSize + (startIndex % pageSize == 0 ? 0 : 1) + 1;

        Object[] newParams;
        int index;

        if (params != null) {
            newParams = new Object[params.length + 2];
            index = params.length;

            System.arraycopy(params, 0, newParams, 0, params.length);
        }
        else {
            newParams = new Object[2];
            index = 0;
        }

        newParams[index++] = pageSize;
        newParams[index] = (pageNo - 1) * pageSize;

        return getSimpleJdbcTemplate().query(sql, defaultMapper, newParams);
    }

    /**
     * {@inheritDoc}
     */
    public int getRowCount(List<String> restrictions, Object... params) {
        String sql = "select count(*) from " + tableName + " ";

        if (restrictions != null && restrictions.size() != 0) {
            sql += "where ";
            for (String restriction : restrictions) {
                sql += restriction;
                sql += " and ";
            }

            sql = sql.substring(0, sql.length()-4);
        }

        return getSimpleJdbcTemplate().queryForInt(sql, params);
    }

    @Override
    public boolean isExist(Long id, Long updatedby, Date updated) {
        String sqlCount = "select count(*) from " + tableName + " "
                + " where id=? and updatedby=? and updated=?";
        int count = getJdbcTemplate().queryForInt(sqlCount, new Object[] {id, updatedby, updated});
        return count > 0;
    }


}
