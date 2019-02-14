package main.com.company;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class KycFormFillingDateRange {

    private static Logger logger = Logger.getLogger("KYC log");
    private static final String LOG_FILE_NAME = "Kyc.log";
    private static FileHandler loggerFile = null;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter
                                                        .ofPattern("dd-MM-uuuu")
                                                        .withResolverStyle(ResolverStyle.STRICT);
    /**
     *
     * This function gives previous years form filling range. Previous years range is
     * used because there is no valid range of form filling in the current year
     *
     * @param startDateOfFormFilling is current years start date of form filling
     * @param endDateOfFormFilling   is current years end date of form filling
     *
     * @return previous years form filling range in String
     */
    private static String getPreviousYearsRange(LocalDate startDateOfFormFilling, LocalDate endDateOfFormFilling) {
        return (DATE_FORMAT.format(startDateOfFormFilling.minusYears(1)) + " " +
                DATE_FORMAT.format(endDateOfFormFilling.minusYears(1)));
    }

    /**
     *
     * This function is used to give current years range which is in between start date
     * of form filling and present date. This range is used because end date of form filling
     * is in the future and according the KYC problem statement range is start date of form
     * filling and present date
     *
     * @param startDateOfFormFilling is the current years start date of form filling
     * @param endDateOfFormFilling   is today's date or end date of form filling which is past
     *                               to present date
     * @return range which is in between start date of form filling and present date in String
     */
    private static String getCurrentYearsRange(LocalDate startDateOfFormFilling, LocalDate endDateOfFormFilling) {
        return (DATE_FORMAT.format(startDateOfFormFilling) + " " + DATE_FORMAT.format(endDateOfFormFilling));
    }

    /**
     *
     * @param startDateOfFormFilling is the current years start date of form filling
     * @param endDateOfFormFilling  is end date of form filling which is past to present date
     * @param presentDate is today's date
     *
     * @return valid range of KYC form filling date in String
     */
    private static String getValidFormFillingRange(LocalDate signUpDate, LocalDate presentDate) {
        // if sign up date is in the future or sign up date is today then range doesn't exist
        if (signUpDate.isAfter(presentDate) || signUpDate.isEqual(presentDate)) {
            return "No Range";
        }

        LocalDate currentYearAnniversary = LocalDate.of(presentDate.getYear(),
                signUpDate.getMonth(), signUpDate.getDayOfMonth());

        LocalDate startDateOfFormFilling = currentYearAnniversary.minusDays(30);
        LocalDate endDateOfFormFilling = currentYearAnniversary.plusDays(30);

        /*
         * if start date of form filling is in future or it is equal to present date
         * then previous years range is the nearest form filling range as per the
         * KYC problem statement.
         */
        if (startDateOfFormFilling.isAfter(presentDate) || startDateOfFormFilling.isEqual(presentDate)) {
            return getPreviousYearsRange(startDateOfFormFilling, endDateOfFormFilling);
        } else {
            //current year has valid form filling range which is the nearest.
            if (endDateOfFormFilling.isAfter(presentDate)) {
                return getCurrentYearsRange(startDateOfFormFilling, presentDate);
            } else {
                return getCurrentYearsRange(startDateOfFormFilling, endDateOfFormFilling);
            }
        }
    }

    /**
     *
     * This function will add file handler to logger to store all the log messages
     * into a file
     */
    private static void setLogger() {
        try {
            loggerFile = new FileHandler(LOG_FILE_NAME, true);
            logger.addHandler(loggerFile);
            SimpleFormatter formatter = new SimpleFormatter();
            loggerFile.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (IOException ioException) {
            logger.info(ioException.getMessage());
        }
    }

    /**
     *
     * Gives range of KYC form filling date
     *
     * @param signUpDateInString  singup date of user in string
     * @param currentDateInString present date in string
     *
     * @return valid range of KYC form filling date in String
     */
    public static String getFormFillingRange(String signUpDateInString, String currentDateInString) {
        try {

            setLogger();

            LocalDate signUpDate = LocalDate.parse(signUpDateInString, DATE_FORMAT);
            LocalDate presentDate = LocalDate.parse(currentDateInString, DATE_FORMAT);

            return getValidFormFillingRange(signUpDate, presentDate);

        } catch (DateTimeException dateTimeException) {
            logger.info(dateTimeException.getMessage());
        } finally {
            if(loggerFile != null) {
                loggerFile.close();
            }
        }
        return null;
    }

    /**
     * KycFormFillingDateRange function takes dates as input from users and prints range of form filling
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int noOfInputs = scanner.nextInt();

        for (int i = 0; i < noOfInputs; i++) {
            String signUpDateInString = scanner.next();
            String currentDateInString = scanner.next();

            String range = getFormFillingRange(signUpDateInString, currentDateInString);
            logger.info(range);
        }
    }
}
