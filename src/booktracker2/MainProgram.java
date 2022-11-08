package booktracker2;

/*
 * In the whole program, there are three classes: MainProgram, MyWindow, and Utilities.
 * 
 * The Utilities class provides back end functionality for connecting with the database.
 * 
 * The MyWindow class provides functionality for the GUI. It allows the GUI 
 * to interact with the database by calling methods from the Utilities class.
 * 
 * The MainProgram class then provides the main method,
 * and this creates a new instance of MyWindow
 * 
 */

import java.awt.Window;

//create TestUtilities Object
//invoke getConnection() <can always be run>
//invoke loadTables() <can always be run>
//invoke populateTables()
//invoke addBorrower() and removeBorrower()
//invoke addBook() and removeBook()
//invoke addBorrow() and completeBorrow()
//invoke viewBorrowers(), viewBooks(), and viewBorrows()

import java.sql.*;
import javax.swing.*;

public class MainProgram extends JFrame{
	public static void main(String args[]) throws SQLException {
		MyWindow wd = new MyWindow();
		wd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wd.setSize(5000,1000);
		wd.setVisible(true);
	}
}
