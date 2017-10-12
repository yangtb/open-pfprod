package com.sm.pfprod.service.system.email.impl;

import com.sm.pfprod.service.system.email.PfEmailService;
import org.springframework.stereotype.Service;

@Service("pfEmailService")
public class PfEmailServiceImpl implements PfEmailService {

    @Override
    public boolean sendEmail() {
        return false;
    }
}
