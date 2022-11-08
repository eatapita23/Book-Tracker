package booktracker2;

import java.sql.*;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utilities {
	
	//SQLite connection objects
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement prep;
	PreparedStatement updateprep;
	
	//Date objects
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String thedate = df.format(Calendar.getInstance().getTime());
	
	//Message that will be shown in status box
	public String msg;
	
	//Boolean for checking if book is already borrowed
	boolean bookBorrowed;
	
	//Constructor establishes connection, loads all tables
	public Utilities() throws SQLException {
		this.getConnection();
		this.loadTables();;
	};
		
	//Connects to SQLite database, returns this connection
	public Connection getConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
		msg = "connected to database";
		stmt = conn.createStatement();
		return conn;
	}
	
	//Returns message for error box
	public String getMessage() {
		return msg;
	}
	
	//Loads tables with SQLite commands. Creates tables only if they do not exist already.
	public void loadTables() throws SQLException {
		String createString	= "CREATE TABLE IF NOT EXISTS STUDENTS (\n" + 
				"	StudentID	INTEGER		PRIMARY KEY,\n" + 
				"	StudentName	CHAR (30),\n" + 
				"	Grade		CHAR (2)\n" + 
				");\n" + 
				"\n" + 
				"CREATE TABLE IF NOT EXISTS BOOKS (\n" + 
				"	ISBNNumber	CHAR (13) 	PRIMARY KEY,\n" + 
				"	BookTitle	CHAR (30) 	NOT NULL,	\n" + 
				"	Borrowed	BOOLEAN		NOT NULL\n" + 
				");\n" + 
				"\n" + 
				"CREATE TABLE IF NOT EXISTS BORROWS (\n" + 
				"	BorrowID	INTEGER		PRIMARY KEY	AUTOINCREMENT,\n" + 
				"	StudentID	INTEGER,	\n" + 
				"	BookNumber	CHAR (13),\n" + 
				"	BeginDate	DATE,\n" + 
				"	DueDate		DATE,\n" + 
				"	Completed	BOOLEAN,\n" + 
				"	FOREIGN KEY (StudentID) REFERENCES STUDENTS (StudentID)\n" + 
				"		ON DELETE CASCADE,\n" + 
				"	FOREIGN KEY (BookNumber) REFERENCES BOOKS (ISBNNumber)\n" + 
				"		ON DELETE CASCADE\n" + 
				");\n";
		
		stmt.executeUpdate(createString);
		stmt.execute("PRAGMA foreign_keys = ON"); //needed this for cascade to work
		msg = "successfully loaded tables";
	}
	
	//Reads all books from database. Returns values in 2D array.
	public Object[][] viewBooks() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM BOOKS");
		int size = 0;
		PreparedStatement prep = conn.prepareStatement("SELECT * FROM BOOKS");
		ResultSet rs2 = prep.executeQuery();
		while (rs2.next()) {
			size++;
		}
		Object[][] newList = new Object[size][3];
		int counter = 0;
		while (rs.next()) {
			String isbn = rs.getString("ISBNNumber");
			String bookTitle = rs.getString("BookTitle");
			boolean borrowed = rs.getBoolean("Borrowed");
			String availablestring;
			if(borrowed) 
				availablestring = "No";
			else 
				availablestring = "Yes";
			newList[counter][0] = isbn;
			newList[counter][1] = bookTitle;
			newList[counter][2] = availablestring;
			counter++;
		}
		return newList;
	}
	
	//Reads all available books from database. Returns values in 2D array.
	public Object[][] viewAvailableBooks() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM BOOKS WHERE Borrowed = false;");
		int size = 0;
		PreparedStatement prep = conn.prepareStatement("SELECT * FROM BOOKS WHERE Borrowed = false;");
		ResultSet rs2 = prep.executeQuery();
		while (rs2.next()) {
			size++;
		}
		Object[][] newList = new Object[size][2];
		int counter = 0;
		while (rs.next()) {
			String isbn = rs.getString("ISBNNumber");
			String bookTitle = rs.getString("BookTitle");
			newList[counter][0] = isbn;
			newList[counter][1] = bookTitle;
			counter++;
		}
		return newList;
	}
	
	//Inserts book into database
	public void addBook(String isbn, String name) {
		try {
		prep = conn.prepareStatement("INSERT INTO BOOKS VALUES (?, ?, false);");
		prep.setString(1, isbn);
		prep.setString(2, name);
		prep.executeUpdate();
		msg = "Book added";
		} catch(SQLException e) {
			msg = e.getMessage();
		}
	}
	
	//Removes book from database
	public void removeBook(String num) {
		try {
			prep = conn.prepareStatement("DELETE FROM BOOKS WHERE ISBNNumber = ?;");
			prep.setString(1, num);
			prep.executeUpdate();
			msg = "Book deleted";
			} catch(SQLException e) {
				msg = e.getMessage();
			}
	}
	
	//Reads all students from database. Returns values in 2D array.
	public Object[][] viewStudents() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM STUDENTS");
		int size = 0;
		PreparedStatement prep = conn.prepareStatement("SELECT * FROM STUDENTS");
		ResultSet rs2 = prep.executeQuery();
		while (rs2.next()) {
			size++;
		}
		Object [][] newList = new Object[size][3];
		int counter = 0;
		while (rs.next()) {
			int studentID = rs.getInt("StudentID");
			String studentName = rs.getString("StudentName");
			String grade = rs.getString("Grade");
			newList[counter][0] = studentID;
			newList[counter][1] = studentName;
			newList[counter][2] = grade;
			counter++;
		}
		return newList;
	}
	
	//Inserts student into database
	public void addStudent(int id, String name, String grade) {
		try {
		prep = conn.prepareStatement("INSERT INTO STUDENTS VALUES (?, ?, ?);");
		prep.setInt(1, id);
		prep.setString(2, name);
		prep.setString(3, grade);
		prep.executeUpdate();
		msg = "Student added";
		} catch(SQLException e) {
			msg = e.getMessage();
		}
	}
	
	//Removes student from database
	public void removeStudent(int id) {
		try {
			prep = conn.prepareStatement("DELETE FROM STUDENTS WHERE StudentID = ?;");
			prep.setInt(1, id);
			prep.executeUpdate();
			msg = "Student deleted";
			} catch(SQLException e) {
				msg = e.getMessage();
			}
	}
	
	//Inserts borrow into database
	public void addBorrow(int studentid, String bookisbn, String duedate) throws SQLException {
		rs = stmt.executeQuery("SELECT Borrowed FROM BOOKS WHERE ISBNNumber = '" + bookisbn + "';");
		if(!rs.getBoolean(1)) { //Checks if book is already borrowed
			bookBorrowed = false; //Since book is not borrowed
		try {
			prep = conn.prepareStatement("INSERT INTO BORROWS VALUES (null, ?, ?, ?, ?, false);");
			prep.setInt(1, studentid);
			prep.setString(2, bookisbn);
			prep.setString(3, thedate);
			prep.setString(4, duedate);
			prep.executeUpdate();
			msg = "Borrow added";
			
			updateprep = conn.prepareStatement("UPDATE BOOKS SET Borrowed = true WHERE ISBNnumber = '" + bookisbn + "';");
			updateprep.executeUpdate();
			msg = "book with isbn number " + bookisbn + " borrowed changed to true";
		} catch(SQLException e) {
			msg = e.getMessage();
		}
		} else {msg = "Book is already borrowed"; bookBorrowed = true;} //Since book is borrowed
	}
	
	//Reads incomplete borrows from database. Returns 2D array.
	public Object[][] viewIncompleteBorrows() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM BORROWS WHERE Completed = FALSE;");
		int size = 0;
		PreparedStatement prep = conn.prepareStatement("SELECT * FROM BORROWS WHERE Completed = FALSE;");
		ResultSet rs2 = prep.executeQuery();
		while (rs2.next()) {
			size++;
		}
		Object[][] newList = new Object[size][4];
		int counter = 0;
		while (rs.next()) {
			int studentid = rs.getInt("StudentID");
			String booknumber = rs.getString("BookNumber");
			String begindate = rs.getString("BeginDate");
			String duedate = rs.getString("DueDate");
			newList[counter][0] = studentid;
			newList[counter][1] = booknumber;
			newList[counter][2] = begindate;
			newList[counter][3] = duedate;
			counter++;
		}
		return newList;
	}
	
	//Changes borrow to completed, changes book to not borrowed
	public void completeBorrow(String isbn) {
		try {
		prep = conn.prepareStatement("UPDATE BOOKS SET Borrowed = false "
				+ "WHERE ISBNnumber = '" + isbn + "';");
		msg = "Book with number " + isbn + " borrowed set to false";
		prep.executeUpdate();
		updateprep = conn.prepareStatement("UPDATE BORROWS SET Completed = true "
				+ "WHERE BookNumber = '" + isbn + "' AND Completed = false;");
		msg += "\n Uncompleted borrow with this ISBN number set to completed";
		updateprep.executeUpdate();
		msg += "\n Borrow fully completed";
		} catch (SQLException e) {
			msg = e.getMessage();
		}
	}
	
	//Reads today's date. Adds number of days to this date. Returns new date.
	public String getDueDate (int days) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dt = thedate;
		
		Calendar Date = Calendar.getInstance();
		Date.setTime(sdf.parse(dt));
		Date.add(Calendar.DATE, days);
		dt = sdf.format(Date.getTime());
		return dt;
	}
}
