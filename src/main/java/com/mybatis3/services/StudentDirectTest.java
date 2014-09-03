package com.mybatis3.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mybatis3.domain.Student;

public class StudentDirectTest {

    /**
     * java直连测试
     * 最好先清理数据再测试，因为id为自增长，可能存在冲突
     */
    public static void main(String[] args) {
	StudentDirectTest ss = new StudentDirectTest();
	for (int i = 1; i < 10; i++) {
	    Student s = new Student();
	    s.setStudId(i);
	    s.setName("name" + i);
	    s.setEmail("email" + i);
	    s.setDob(new Date());
	    ss.createStudent(s);
	}

	Student stu = ss.findStudentById(1);
	System.out.println(stu.getName());
    }

    public Student findStudentById(int studId) {
	Student student = null;
	Connection conn = null;
	try {
	    //获得数据库连接
	    conn = getDatabaseConnection();
	    String sql = "SELECT * FROM STUDENTS WHERE STUD_ID=?";
	    //创建 PreparedStatement
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    //设置输入参数
	    pstmt.setInt(1, studId);
	    ResultSet rs = pstmt.executeQuery();
	    //从数据库中取出结果并生成Java对象
	    if (rs.next()) {
		student = new Student();
		student.setStudId(rs.getInt("stud_id"));
		student.setName(rs.getString("name"));
		student.setEmail(rs.getString("email"));
		student.setDob(rs.getDate("dob"));
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} finally {
	    //关闭连接
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException e) {
		}
	    }
	}

	return student;
    }

    public void createStudent(Student student) {
	Connection conn = null;
	try {
	    //获得数据库连接
	    conn = getDatabaseConnection();
	    String sql = "INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL,DOB ) VALUES(?,?,?,?)";
	    // 创建 PreparedStatement
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    //设置输入参数
	    pstmt.setInt(1, student.getStudId()); // 自增长 可能存在冲突
	    pstmt.setString(2, student.getName());
	    pstmt.setString(3, student.getEmail());
	    pstmt.setDate(4, new java.sql.Date(student.getDob().getTime()));
	    pstmt.executeUpdate();

	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} finally {
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException e) {
		}
	    }
	}
    }

    protected Connection getDatabaseConnection() throws SQLException {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");
	} catch (SQLException e) {
	    throw e;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
