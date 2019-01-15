import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingHost {

  public static final int NO_OF_PINGS = 10;
  public static final Pattern pingTimePattern = Pattern.compile("(time=)(\\d+\\.?\\d*)");
  public static Double[] pingTime = new Double[NO_OF_PINGS];

  public static void main(String[] args) throws IOException, NullPointerException {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter url without http(s)");
    String url = scanner.next();

    Process process = Runtime.getRuntime().exec("ping -c " + NO_OF_PINGS + " " + url);
    BufferedReader pingResponse = new BufferedReader(new InputStreamReader(process.getInputStream()));

    pingResponse.readLine();

    for(int i=0; i<NO_OF_PINGS; i++){
      String pingLine = pingResponse.readLine();
      System.out.println(pingLine);
      Matcher matcher = pingTimePattern.matcher(pingLine);
      if(matcher.find()){
        pingTime[i] = Double.parseDouble(matcher.group(2));
      }
    }
    Arrays.sort(pingTime);
    Double median = (pingTime[4] + pingTime[5])/2;
    System.out.println("Median time: "+ median + " ms");
  }
}