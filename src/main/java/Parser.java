// to import jsoup: File - Project Structure -  Global Libraries - (+) - maven - type org.jsoup

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    // getting the weather page
    public static Document getPage() throws IOException {

        String url = "http://pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;

    }

    // create pattern for regular expressions
    // in our case we need the dates: 20.04 =>> \d{2}.\d{2}
    // where \d{2} ==> two numbers, then . andagain \d{2}
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    // getting date from the table's cells
    private static String getDateFromString(String stringDate) throws Exception {
        // matcher for the RegEx
        Matcher matcher = pattern.matcher(stringDate);

        //now we want to get the values if matcher found anyhthin
        if (matcher.find()) {
            return matcher.group(); // матчер, группируй найденное
        }
        throw new Exception("Can't extract the date from the string");

    }

    public static void main(String[] args) throws Exception {

       // System.out.println(getPage()); // Just to check if the method returns the page

        Document page = getPage();
        // to get the table from the page, CSS query language
        // first -> первый элемент с таким идентификатором на странице
        Element tableWeather = page.select("table[class=wt]").first();

        //to get the titles (including dates) from the cells of the table
        Elements names = tableWeather.select("tr[class=wth]");

        // to get values (not just titles) from the table
        Elements values = tableWeather.select("tr[valign=top]");

        // to print the weather sections as many times as many sections we have in the table
        for (Element name : names) {
            String dateString = name.select("th[id=dt]").text();
            String date = getDateFromString(dateString);
            System.out.println(date);
        }
    }
}
