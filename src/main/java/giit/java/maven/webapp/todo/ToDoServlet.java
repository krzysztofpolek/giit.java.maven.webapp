package giit.java.maven.webapp.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ToDo", urlPatterns = {"/api/todos/*"})
public class ToDoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ToDoServlet.class);

    private ToDoReporsitory reporsitory;
    private ObjectMapper mapper;

    /**
     * Servlet container needs it.
     */
    @SuppressWarnings("unused")
    public ToDoServlet() { this(new ToDoReporsitory(), new ObjectMapper()); }

    ToDoServlet(ToDoReporsitory reporsitory, ObjectMapper mapper) {
        this.reporsitory = reporsitory;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with parameter: " + req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), reporsitory.findAll());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            Integer todoId = Integer.valueOf(pathInfo.substring(1));
            ToDo todo = reporsitory.toggleTodo(todoId);
            resp.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(resp.getOutputStream(), todo);
        }
        catch (NumberFormatException e) {
            logger.warn("Wrong path used: " + pathInfo);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ToDo newToDo =  mapper.readValue(req.getInputStream(), ToDo.class);
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), reporsitory.addTodo(newToDo));
    }
}
