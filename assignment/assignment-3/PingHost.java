import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

public class PingHost {

  public static final int NO_OF_PINGS = 10;
  public static final Pattern pingTimePattern = Pattern.compile("(time=)(\\d+\\.?\\d*)");
  public static Double[] pingTime = new Double[NO_OF_PINGS];
  private static Logger logger = Logger.getLogger("PingHost log");

  /**
   * pings the host for NO_OF_PINGS times
   * 
   * @param   url of host to be pinged
   *
   * @throws  IOException if an I/O error occurs while reading a line
   * @throws  NullPointerException if url is empty
   * 
   * @return median of the time taken to ping
   */
  private static Double pingTheHost(String url) throws IOException, NullPointerException {
    Process process = Runtime.getRuntime().exec("ping -c " + NO_OF_PINGS + " " + url);
    BufferedReader pingResponse = 
                      new BufferedReader(new InputStreamReader(process.getInputStream()));

    pingResponse.readLine();

    for(int i = 0; i<NO_OF_PINGS; i++){
      String pingLine = pingResponse.readLine();
      logger.info(pingLine);
      Matcher matcher = pingTimePattern.matcher(pingLine);
      if(matcher.find()){
        pingTime[i] = Double.parseDouble(matcher.group(2));
      }
    }
    Arrays.sort(pingTime);
    Double median = 0.0;
    if(NO_OF_PINGS%2 == 0) {
      median = (pingTime[NO_OF_PINGS/2 - 1] + pingTime[NO_OF_PINGS/2])/2;
    } else {
      median = pingTime[NO_OF_PINGS/2];
    }
    return median;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    logger.info("Enter url without http(s)");
    String url = scanner.next();
    try { 
      Double median = pingTheHost(url);
      logger.info("Median time: "+ median + " ms");
    } catch(IOException ioException) {
      logger.info(ioException.getMessage());
    } catch(NullPointerException nullPointerException) {
      logger.info("no response from " + url);
    }
  }
}
