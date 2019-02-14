import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.logging.Logger;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;

public class FileSearch {

    private static Pattern pattern;
    private static final String HOME_DIR = System.getProperty("user.home");
    private static Logger logger = Logger.getLogger("FileSearch log");

    /**
     * Finds the file name matching with the regular expression and 
     * displays its absolute path
     * 
     * @param regularExpression is the regular expression which is entered by the user
     * @param currentDirectory directory to be searched
     */
    public static void findFile(String regularExpression, File currentDirectory) {
        File[] listOfFiles = currentDirectory.listFiles();
        //if directory is empty
        if(listOfFiles == null) 
            return;
        for(File file : listOfFiles) {
            if (file.isDirectory()) {
                findFile(regularExpression, file);
            } else {
                if(pattern.matcher(file.getName()).matches()){
                    logger.info(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Takes regular expression as input from user and finds the file
     * matching the regular expression
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        logger.info("Enter regular expression");
        
        while (scanner.hasNext()) {
            String regularExpression = scanner.next();
            try {
                pattern = Pattern.compile(regularExpression);
                findFile(regularExpression, (new File(HOME_DIR)) );
            } catch (PatternSyntaxException patternSyntaxException) {
                logger.info("Please enter valid regular expression");
            }
            logger.info("Search another file or cntl D to exit");
        }
    }
}
