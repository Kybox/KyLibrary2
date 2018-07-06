package fr.kybox.batch.servlets;

import fr.kybox.batch.Reminder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Kybox
 * @version 1.0
 */

public class WebBatch extends HttpServlet {

    public WebBatch(){ super(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-batch-job.xml")) {
            Reminder reminder = context.getBean(Reminder.class);
            Map<String, Object> resultMap = reminder.run(email, password);
            request.setAttribute("result", resultMap);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
}
