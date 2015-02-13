package com.test.dao.impl;

import com.test.dao.RegistrationDao;
import com.test.dao.mapper.RegistrationMapper;
import com.test.domain.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


/**
 * Created by Kahfi on 2/13/2015.
 */
@Repository
public class RegistrationDaoImpl extends GenericDaoImpl<Registration, Long> implements RegistrationDao {

    @Autowired
    public RegistrationDaoImpl(DataSource dataSource, RegistrationMapper registrationMapper) {
        super("registration", dataSource);
        defaultMapper = registrationMapper;
    }

    @Override
    public Registration save(Registration object) {
        String sql = "insert into registration values (?,?,?,?) ";
        getSimpleJdbcTemplate().update(sql, new Object[]{
                object.getId(), object.getName(), object.getEmail(), object.getAddress()
        });

        return object;
    }
}