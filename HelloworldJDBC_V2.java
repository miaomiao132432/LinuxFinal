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
import redis.clients.jedis.Jedis;

@WebServlet("/hellojdbc_v2")

public class HelloworldJDBC_V2 extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3337/linux_final";
    static final String USER = "root";
    static final String PASS = "miaomiao1";
    
    static Connection conn = null;
    static Jedis jedis = null;
   public void init() {
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         jedis = new Jedis("127.0.0.1"); 
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void destroy() {
      try {
         conn.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        String studentInfo = jedis.get("1");
        
        if (studentInfo == null) {
            studentInfo = getStudent();
            jedis.set("1", studentInfo);
            out.printf("%s, 当前时间: %s", studentInfo, new Date().toString());
 
        } else {
            out.printf("%s, 当前时间: %s", studentInfo, new Date().toString());
        }

        out.close();
    }

    public String getStudent() throws ServletException {
        String number = "";
        String name = "";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM t_student WHERE id=1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                number = rs.getString("number");
                name = rs.getString("name");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        return String.format("[%s]%s", number, name);
    }

}
