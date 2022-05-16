package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.StateDao;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class StateDaoStub implements StateDao {

    @Override
    public State getStateByAbbreviation(String abbreviation) {
        if (abbreviation.equals("TX")) {
            return new State("TX", "Texas", new BigDecimal("4.45"));
        } else {
            return null;
        }
    }

}