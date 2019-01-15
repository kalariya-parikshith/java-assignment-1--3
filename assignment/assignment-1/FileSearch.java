import java.util.Scanner;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import java.util.NoSuchElementException;

public class FileSearch {

    private static String regEx;
    private static Pattern pattern;
    private static Matcher matcher;
    private static final String HOME_DIR = System.getProperty("user.home");


    /**
     * Finds the file name matching with the regular expression and displays its absolute path
     * @param regEx is the regular expression which is entered by the user
     * @param currentDirectory directory to be searched
     */
    public static void findFile(String regEx, File currentDirectory) {
        File[] listOfFiles = currentDirectory.listFiles();
        for(File file : listOfFiles) {
            if (file.isDirectory()) {
                findFile(regEx, file);
            } else {
                matcher = pattern.matcher(file.getName());
                if(matcher.matches()){
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Takes regular expression as input from user and findes the file
     * matching the regular expression
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter regular expression");
        
        while (scanner.hasNext()) {
            regEx = scanner.next();
            try {
                pattern = Pattern.compile(regEx);
                findFile(regEx, (new File(HOME_DIR)) );
            } catch (PatternSyntaxException patternSyntaxException) {
                System.out.println("Please enter valid regular expression");
            }
            
            System.out.println("Search another file or cntl D to exit");
        }
    }
}


