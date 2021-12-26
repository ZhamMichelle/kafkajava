package zhami.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyServletV2 extends HttpServlet {
//    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;

    public MyServletV2(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI().equals("/")) {
            resp.setContentType("text/html");
            resp.getWriter().print("<html><head></head><body><h1>Welcome!</h1><p>This is a very cool page!</p></body></html>");
        } else if (req.getRequestURI().startsWith("/msg/")) {

            Integer prettyFragileUserId = Integer.valueOf(req.getRequestURI().lastIndexOf("/") + 1);

            resp.setContentType("application/json");


            kafkaTemplate.send("msg", 15L, "msg1");

            // User user = dao.findUser(prettyFragileUserId)
            // actually: jsonLibrary.toString(user)
            resp.getWriter().print("{\n" +
                    "  \"id\":" + prettyFragileUserId + ",\n" +
                    "  \"age\": 55,\n" +
                    "  \"name\" : \"John Doe\"\n" +
                    "}");
        } else {
            throw new IllegalStateException("Help, I don't know what to do with this url");
        }
    }
}
