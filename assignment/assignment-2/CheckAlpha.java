import java.util.Scanner;
import java.util.logging.Logger;

class CheckAlpha{
	
	private static Logger logger = Logger.getLogger("CheckAlpha log");
	
	/**
	 * Checks if the string contains all the english alphabets (case-insensitive)
	 * @param str string to be checked
	 * @return true if all the english alphabets are present in str
	 *		   false otherwise
	 * Time Complexity is O(n) where n is the length of string
	 * Space Complexity is O(1)
	 */
	public static boolean containsAllAlphabets(String str){
		long distinctCharCount = str.toUpperCase().chars()
								   .filter(c -> c >= 'A' && c <= 'Z')
								   .distinct()
								   .count();

		return  (distinctCharCount == 26) ? true : false;
	}

	/**
	 * Takes string as input from users and checks if that string contains all the 
	 * alphabets in it using containsAllAlphabets() method
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		logger.info("Enter string");
		String stringToBeChecked = scanner.nextLine();
		if (containsAllAlphabets(stringToBeChecked)) {
			logger.info("Input string has all the alphabets");
		} else {
			logger.info("Input string doesn't have all the alphabets");
		}
	}
}
