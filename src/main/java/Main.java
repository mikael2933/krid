import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by multimikael on 17-01-2017.
 */
public class Main{
    public static void main(String[] args) {
        while (true) {
            String cookie = getNewCookie();
            System.out.println("Cookie : " + cookie);
            voteFor("625", cookie);
        }
    }

    private static String getNewCookie() {
        try {
            Connection.Response response = Jsoup.connect("http://kridellerkran.dk/boys")
                    .method(Connection.Method.GET)
                    .followRedirects(false)
                    .execute();
            if (response.cookies().size() == 1) {
                return response.cookies().get("PHPSESSID");
            } else {
                return "BLOCKED?";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static void voteFor(String id, String cookie) {
        try {
            Connection.Response response = Jsoup.connect("http://kridellerkran.dk/vote.php?id=" + id)
                    .method(Connection.Method.GET)
                    .followRedirects(false)
                    .cookie("PHPSESSID", cookie)
                    .execute();
            System.out.println(response.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
