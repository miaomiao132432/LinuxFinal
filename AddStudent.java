import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

@WebServlet("/addstudent")

public class AddStudent extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://[ip]:[port]/[database]";
    static final String USER = "xxxxxx";
    static final String PASS = "xxxxxxxx";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        int number = req.getParameter("number");

        if (info.number == null || info.name == null) {
            throw new ServletException("please input all parameters");
        }

        addHesuan(name, number);

        PrintWriter out = resp.getWriter();
        out.printf("[add]ok");
        out.close();

    }

    public void addHesuan(String name, int number) throws ServletException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO t_hesuan(number, name) VALUES(?, ?)");
            stmt.setString(1, number);
            [仿照上一句, 将name参数填入sql中]

            int row = stmt.executeUpdate();
            if (row == 0) {
                throw new ServletException("insert error.");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
