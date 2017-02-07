package math.jdrzaic.bibliographyapplication;

import java.util.ArrayList;

/**
 * Created by jelenadrzaic on 06/02/2017.
 */
public class Entity {

    public int id;
    public String type = "";
    public String title = "";
    public String journal = "";
    public int number = -1;
    public String pages = "";
    public int year = -1;
    public String link = "";
    public String file = "";
    public ArrayList<String> keywords = new ArrayList<>();
    public ArrayList<String> authors = new ArrayList<>();
    public String authorsString = "";
    public String keywordsString = "";

    public Entity() {}

    public Entity(int id, String type, String title, String journal, int number,
                  String pages, int year, String link, String file, ArrayList<String> keywords,
                  ArrayList<String> authors) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.journal = journal;
        this.number = number;
        this.pages = pages;
        this.year = year;
        this.link = link;
        this.file = file;
        this.keywords = keywords;
        this.authors = authors;
    }

    public String getAuthors() {
        StringBuilder sb = new StringBuilder();
        for (String author : authors) {
            sb.append(author);
            sb.append(", ");
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    public String getKeywords() {
        StringBuilder sb = new StringBuilder();
        for (String keyword : keywords) {
            sb.append(keyword);
            sb.append(", ");
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }
}
