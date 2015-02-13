package com.test.service.impl;

import com.test.dao.RegistrationDao;
import com.test.domain.Registration;
import com.test.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kahfi on 2/13/2015.
 */
@Service
public class RegistrationServiceImpl extends GenericServiceImpl<Registration, Long> implements RegistrationService {

    RegistrationDao registrationDao;

    @Autowired
    public RegistrationServiceImpl(RegistrationDao registrationDao) {
        super(registrationDao);
    }
}
