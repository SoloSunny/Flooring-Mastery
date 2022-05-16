/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class UserIoConsoleImpl implements UserIo {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    @Override
    public String readNonEmptyString(String prompt) {
        System.out.println(prompt);
        String input = "";
        boolean isValid = false;
        while (!isValid) {
            input = sc.nextLine();
            if (input.trim().length() == 0) {
                System.out.println("Your input cannot be blank.");
            } else {
                isValid = true;
            }
        }
        return input;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        String stringInput = "";
        int input = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                stringInput = sc.nextLine();
                input = Integer.parseInt(stringInput);
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please to enter an integer!");
            }
        }
        return input;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        String stringInput = "";
        int input = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                stringInput = sc.nextLine();
                input = Integer.parseInt(stringInput);
                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println("Please enter an intiger"
                            + "is between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an intiger!");
            }
        }
        return input;
    }
    
    @Override
    public int readIntPossiblyEmpty(String prompt, int min, int max) {
        System.out.println(prompt);
        String stringInput = "";
        int input = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                stringInput = sc.nextLine();
                if (stringInput.isEmpty()) {
                    return min - 1;
                }
                input = Integer.parseInt(stringInput);
                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println("Please enter an intiger"
                            + "is between " + min + " and " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an intiger!");
            }
        }
        return input;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        System.out.println(prompt);
        BigDecimal input = BigDecimal.ZERO;
        boolean isValid = false;
        while (!isValid) {
            try {
                input = new BigDecimal(sc.nextLine());
                if (input.compareTo(min) == -1) {
                    System.out.println("Please ensure the number you entered is:"
                            + " at least " + min + ".");
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ensure you enter a number!S\n");
            }
        }
        return input;
    }
    
    @Override
    public BigDecimal readBigDecimalPossiblyEmpty(String prompt, BigDecimal min) {
        System.out.println(prompt);
        String stringInput = "";
        BigDecimal input = BigDecimal.ZERO;
        boolean isValid = false;
        while (!isValid) {
            try {
                stringInput = sc.nextLine();
                if (stringInput.isEmpty()) {
                    return min.subtract(BigDecimal.ONE);
                }
                input = new BigDecimal(stringInput);
                if (input.compareTo(min) == -1) {
                    System.out.println("Please enter the number"
                            + " at least " + min + ".");
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ensure you enter a number!\n");
            }
        }
        return input;
    }

    @Override
    public LocalDate readLocalDate() {
        System.out.println("Please enter a quote-date in the format MM-dd-yyyy: ");
        String dateAsString = "";
        LocalDate ld = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                dateAsString = sc.nextLine();
                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                ld = LocalDate.parse(dateAsString, f);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a quote-date in the format MM-dd-yyyy:"
                        + " MM-DD-YYYY.");
            }
        }
        return ld;
    }

    @Override
    public LocalDate readLocalDateInFuture() {
        System.out.println("Please enter a quote-date in the format MM-dd-yyyy: ");
        String dateAsString = "";
        LocalDate ld = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                dateAsString = sc.nextLine();
                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                ld = LocalDate.parse(dateAsString, f);
                if (ld.isBefore(LocalDate.now())) {
                    System.out.println("Please enter the quote-date for "
                            + "today or after: ");
                } else {
                    isValid = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a quote-date in the format"
                        + " MM-dd-yyyy.");
            }
        }
        return ld;
    }

}