package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/add")
public class addstudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String course = req.getParameter("course");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/displayrec",
                    "root",
                    "jahe2506"
            );

            String sql = "INSERT INTO studentss(name, age, course) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(age));
            ps.setString(3, course);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<h2>Student Added Successfully!</h2>");
                out.println("<p>Name: " + name + "</p>");
                out.println("<p>Age: " + age + "</p>");
                out.println("<p>Course: " + course + "</p>");
            } else {
                out.println("<h2>Student Not Added</h2>");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<h2>Database Error</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}