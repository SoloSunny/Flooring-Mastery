/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author solomon
 */
public interface UserIo {

    void print(String message);

    String readString(String prompt);

    String readNonEmptyString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    int readIntPossiblyEmpty(String prompt, int min, int max);

    BigDecimal readBigDecimal(String prompt, BigDecimal min);

    BigDecimal readBigDecimalPossiblyEmpty(String prompt, BigDecimal min);

    LocalDate readLocalDate();

    LocalDate readLocalDateInFuture();

}