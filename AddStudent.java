import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String number = req.getParameter("number");

        if (number == null || name == null) {
            throw new ServletException("please input all parameters");
        }

        addStudent(name, number);

        PrintWriter out = resp.getWriter();
        out.printf("[add]ok, 当前时间: %s", new Date().toString());
	out.close();

    }

    public void addStudent(String name, String number) throws ServletException {
        Connection conn = null;
    	PreparedStatement stmt = null;
        try {
	    Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("[sql:插入t_student表]");
            stmt.setString(1, number);
            stmt.setString(2, name);

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
