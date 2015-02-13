package com.test.dao.mapper;

import com.test.domain.Registration;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kahfi on 2/13/2015.
 */
@Component("registrationMapper")
public class RegistrationMapper extends ChainedRowMapper<Registration> implements ParameterizedRowMapper<Registration> {

    @Override
    public Registration chainRow(ResultSet rs, int index) throws SQLException {
        Registration r = new Registration();

        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setAddress(rs.getString("address"));

        return r;
    }
}
