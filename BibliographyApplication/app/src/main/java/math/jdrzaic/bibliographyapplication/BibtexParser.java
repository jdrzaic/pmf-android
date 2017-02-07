package math.jdrzaic.bibliographyapplication;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jelenadrzaic on 06/02/2017.
 */
public class BibtexParser {

    public static final String PARSER_TAG = "parser_tag";

    public static Entity parseBibtex(String bibtex) {
        Entity entity = new Entity();
        String type = "(@[a-zA-Z]+)";
        Pattern p = Pattern.compile(type);
        Matcher m = p.matcher(bibtex);
        if (m.find()) {
            entity.type = m.group();
        }
        String title = "title\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(title);
        m = p.matcher(bibtex);
        if (m.find()) {
            entity.title = m.group(1).trim();
        }

        String journal = "journal\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(journal);
        m = p.matcher(bibtex);
        if (m.find()) {
            entity.journal = m.group(1);
        }
        String number = "number\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(number);
        m = p.matcher(bibtex);
        try {
            entity.number = Integer.valueOf(m.group(1));
        } catch (Exception ex) {}
        String pages = "pages\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(pages);
        m = p.matcher(bibtex);
        if (m.find()) {
            entity.pages = m.group(1);
        }
        String year = "year\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(year);
        m = p.matcher(bibtex);
        try {
            if (m.find()) {
                entity.year = Integer.valueOf(m.group(1));
            }
        } catch (Exception ex) {}

        String link = "url\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(link);
        m = p.matcher(bibtex);
        if (m.find()) {
            entity.link = m.group(1);
        }
        String authors = "author\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(authors);
        m = p.matcher(bibtex);
        if (m.find()) {
            entity.authorsString = m.group(1);
        }
        String keywords = "keywords\\s*=\\s*[{]([^}]+)[}]";
        p = Pattern.compile(keywords);
        m = p.matcher(bibtex);
        if (m.find()) {
            entity.keywordsString = m.group(1);
        }
        return entity;
    }

    public static String exportToBibtex(Cursor c) {
        ArrayList<String> aKeywords = new ArrayList<>();
        ArrayList<String> aAuthors = new ArrayList<>();
        MainActivity.db.open();
        Cursor cutil = MainActivity.db.getKeywordsForEntity(c.getInt(0));
        if (cutil.moveToFirst()) {
            do {
                aKeywords.add(cutil.getString(0));
            } while (cutil.moveToNext());
        }
        cutil = MainActivity.db.getAuthorsForEntity(c.getInt(0));
        if (cutil.moveToFirst()) {
            do {
                aAuthors.add(cutil.getString(0));
            } while (cutil.moveToNext());
        }
        MainActivity.db.close();
        Entity entity = new Entity(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
                c.getInt(4), c.getString(5), c.getInt(6), c.getString(7), c.getString(8), aKeywords, aAuthors);
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(entity.type);
        sb.append("{\n");
        sb.append("\ttitle={");
        sb.append(entity.title);
        sb.append("},\n");

        sb.append("\tauthor={");
        sb.append(entity.getAuthors());
        sb.append("},\n");

        sb.append("\tkeywords={");
        sb.append(entity.getKeywords());
        sb.append("},\n");

        sb.append("\tjournal={");
        sb.append(entity.journal);
        sb.append("},\n");

        sb.append("\tnumber={");
        sb.append(entity.number);
        sb.append("},\n");

        sb.append("\tpages={");
        sb.append(entity.pages);
        sb.append("},\n");

        sb.append("\tyear={");
        sb.append(entity.year);
        sb.append("},\n");

        sb.append("\turl={");
        sb.append(entity.link);
        sb.append("},\n");

        sb.append("}");

        return sb.toString();
    }

    public static List<Entity> filter(String key, String value, List<Entity> entities) {
        List<Entity> filtered = new ArrayList<>();
        if (key.compareTo("title") == 0) {
            for (Entity entity : entities) {
                if(entity.title.toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("author") == 0) {
            for (Entity entity : entities) {
                if(entity.getAuthors().toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("keywords") == 0) {
            for (Entity entity : entities) {
                if(entity.getKeywords().toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("journal") == 0) {
            for (Entity entity : entities) {
                if(entity.journal.toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("number") == 0) {
            for (Entity entity : entities) {
                if(String.valueOf(entity.number).contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("pages") == 0) {
            for (Entity entity : entities) {
                if(entity.pages.toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("year") == 0) {
            for (Entity entity : entities) {
                if(String.valueOf(entity.year).toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("url") == 0) {
            for (Entity entity : entities) {
                if(entity.link.toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        if (key.compareTo("file") == 0) {
            for (Entity entity : entities) {
                if(entity.file.toLowerCase().contains(value.toLowerCase())) {
                    filtered.add(entity);
                }
            }
        }
        return filtered;
    }
}
