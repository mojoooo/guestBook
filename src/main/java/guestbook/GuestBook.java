package guestbook;

import java.util.Date;
import java.text.SimpleDateFormat;

public class GuestBook
{
    private String name;
    private Date date;
    private String content;

    public GuestBook(String name, Date date, String content)
    {
        this.setName(name);
        this.setDate(date);
        this.setContent(content);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
