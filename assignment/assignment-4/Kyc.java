import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;
class Kyc {
	private static int noOfInputs;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	/**
	 * Converts localdate to dd-mm-yyyy format 
	 * @param localDate localdate to be formated
	 * @return Date as dd-MM-yyyy in String format
	 */
	public static String formatLocalDate(LocalDate localDate) throws DateTimeException {
		return DATE_FORMAT.format(localDate).toString();
	}

	/**
	 * Range of dates for KYC form in format dd-mm-yyyy dd-mm-yyyy
	 * @param signUpDate sign up date for KYC of user
	 * @param currentDate present date
	 */
	public static void rangeOfDates(LocalDate signUpDate, LocalDate currentDate) {
		
		if (signUpDate.isAfter(currentDate)) {
			System.out.println("No range");
			return ;
		}

		long yearGap = currentDate.getYear() - signUpDate.getYear();
		LocalDate closestAnniversary = signUpDate.plusYears(yearGap);
		String minus30Days = formatLocalDate(closestAnniversary.minusDays(30));
		String plus30Days = formatLocalDate(closestAnniversary.plusDays(30));

		if (closestAnniversary.plusDays(30).isAfter(currentDate)) {

			System.out.println(minus30Days + " " + formatLocalDate(currentDate));
		} else {
			System.out.println(minus30Days +" "+ plus30Days);	
		}		
	}

	public static void main(String[] args) throws DateTimeParseException{
		Scanner scanner = new Scanner(System.in);
		noOfInputs = scanner.nextInt();
		for (int i=0; i<noOfInputs; i++) {
			String signUpDate  = scanner.next();
			String currentDate = scanner.next();
			rangeOfDates(LocalDate.parse(signUpDate, DATE_FORMAT),
						 LocalDate.parse(currentDate, DATE_FORMAT));
		}
	}
}