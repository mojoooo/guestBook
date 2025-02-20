package guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/guest")

public class GuestBookServlet extends HttpServlet
{
    private List<GuestBook> entries;

    public List<GuestBook> getAllEntries()
    {
        return entries;
    }

    public void setEntry(GuestBook entry)
    {
        this.entries.add(entry);
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        this.entries = new ArrayList<GuestBook>();
        
        GuestBook entry1 = new GuestBook("Ich", new Date(), "war hier am 20.02.25");
        this.setEntry(entry1);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {        
        String name = req.getParameter("name");
        String dateString = req.getParameter("date");
        String content = req.getParameter("content");
        String login = req.getParameter("login");
        
        if ("login".equals(login))
        {
            req.getSession().setAttribute("login", "true");
        }
        else if ("logout".equals(login))
        {
            req.getSession().setAttribute("login", "false");
        }

        if (name != null && content != null && dateString != null && !name.isEmpty() && !dateString.isEmpty() && !content.isEmpty())
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try
            {
                date = dateFormat.parse(dateString);
            }
            catch (Exception e)
            {    
            }

            GuestBook newEntry = new GuestBook(name, date, content);
            this.setEntry(newEntry);
        }
        
        this.showHTML(req, resp);
    }
    
    public void showHTML(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Guestbook</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Guestbook</h1>");
        
        for (GuestBook entry : this.getAllEntries())
        {
            out.println("<div style='border: 2px solid black; padding: 15px; margin: 10px 0; width: 400px; border-radius: 8px; background-color: #f9f9f9;'>");
            out.println("<p><strong>Name:</strong> " + entry.getName() + "</p>");
            out.println("<p><strong>Reisedatum:</strong> " + entry.getDate() + "</p>");
            out.println("<p><strong>Nachricht:</strong><br>" + entry.getContent() + "</p>");
            out.println("</div>");
        }
        
        String login = (String) req.getSession().getAttribute("login");
        if ("true".equals(login))
        {
            out.println("<div style='position: absolute; top: 10px; right: 10px;'>");
            out.println("<form action='' method='post' style='margin: 0;'>");
            out.println("<button type='submit' name='login' value='logout' style='background-color: #ff2a00; color: white; border: none; padding: 10px 20px; font-size: 16px; border-radius: 5px; cursor: pointer;'>Logout</button>");
            out.println("</form>");
            out.println("</div>");
            
            this.showNewEntryForm(req, resp);
        }
        else
        {
            out.println("<div style='position: absolute; top: 10px; right: 10px;'>");
            out.println("<form action='' method='post' style='margin: 0;'>");
            out.println("<button type='submit' name='login' value='login' style='background-color: #007bff; color: white; border: none; padding: 10px 20px; font-size: 16px; border-radius: 5px; cursor: pointer;'>Login</button>");
            out.println("</form>");
            out.println("</div>");
        }
        
        out.println("</body>");
        out.println("</html>");
    }
    
    public void showNewEntryForm(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        PrintWriter out = resp.getWriter();
        
        out.println("<form action='' method='post' style='border: 2px solid black; padding: 15px; width: 400px; border-radius: 8px; background-color: #f9f9f9;'>");
        out.println("<label for='name' style='font-weight: bold;'>Name:</label><br>");
        out.println("<input type='text' id='name' name='name' required style='width: 100%; padding: 8px; margin: 5px 0; border: 1px solid #ccc; border-radius: 4px;'><br>");
        out.println("<label for='date' style='font-weight: bold;'>Datum der Reise:</label><br>");
        out.println("<input type='date' id='date' name='date' required style='width: 100%; padding: 8px; margin: 5px 0; border: 1px solid #ccc; border-radius: 4px;'><br>");
        out.println("<label for='content' style='font-weight: bold;'>Nachricht:</label><br>");
        out.println("<textarea id='content' name='content' rows='4' required style='width: 100%; padding: 8px; margin: 5px 0; border: 1px solid #ccc; border-radius: 4px;'></textarea><br>");
        out.println("<button type='submit' style='background-color: #28a745; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer;'>Neuen Eintrag erstellen</button>");
        out.println("</form>");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        this.showHTML(req, resp);
    }
}
