import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getstudent")

public class GetStudent extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://[ip]:[port]/[database]";
    static final String USER = "xxxxxx";
    static final String PASS = "xxxxxxxx";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        out.printf("%s, 当前时间: %s", getStudent(), new Date().toString());
        out.close();
    }

    public String getStudent() throws ServletException {
        String number = "";
        String name = "";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "[sql:查询id=1的学生信息]";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                number = rs.getString("number");
                name = rs.getString("name");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return String.format("[%s]%s", number, name);
    }
}
