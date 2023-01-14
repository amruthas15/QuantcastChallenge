import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MostActiveCookie {
    public static void main(String[] args) throws IOException, ParseException {

        if(args.length != 3) {
            System.out.println("Please call the program again with the correct number of arguments: ./most_active_cookie log_file_name.csv -d YYYY-MM-DD");
        } else {
            //Parse and store input arguments
            String filePath = args[0];
            String dateString = args[2];

            String[] dateComps = dateString.split("-");
            System.out.println(dateComps[0] + " " + dateComps[1] + " " + dateComps[2]);
            System.out.println(dateComps.length);
            if((dateComps.length != 3) || (dateComps[0].length() != 4) || (dateComps[1].length() != 2) || (dateComps[2].length() != 2)) {
                System.out.println("Please call the program again with the correct date format: ./most_active_cookie log_file_name.csv -d YYYY-MM-DD");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(dateString);
                File f = new File(filePath);

                if(!f.exists()) {
                    System.out.println("Your log file is currently inaccessible. Make sure that you are inputting the correct file name and path to access the cookie log file.");
                } else {
                    cookieMaxAnalysis(filePath, date);
                }
            }
        }
    }

    public static void cookieMaxAnalysis(String filePath, Date date) {
        //Parse and organize cookie accesses
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            Map<String, Integer> cookieTracker = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String cookie = parts[0];
                Date timestamp = dateFormat.parse(parts[1]);
                if (timestamp.getYear() == date.getYear() && timestamp.getMonth() == date.getMonth() && timestamp.getDate() == date.getDate()) {
                    if (cookieTracker.containsKey(cookie)) { // cookie already added to tracker
                        cookieTracker.put(cookie, cookieTracker.get(cookie) + 1);
                    } else { // cookie not yet in tracker
                        cookieTracker.put(cookie, 1);
                    }
                }
            }
            br.close();

            // Find max count of cookie accesses
            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : cookieTracker.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                }
            }

            // Print name of cookie(s) with max access count
            for (Map.Entry<String, Integer> entry : cookieTracker.entrySet()) {
                if (entry.getValue() == maxCount) {
                    System.out.println(entry.getKey());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}