package booktracker2;

import java.sql.*;
import javax.swing.RowFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.text.ParseException;

/*This class creates a new instance of the utilities class, 
 * and creates the components of the visible GUI window
 * 
 * */

public class MyWindow extends JFrame {
	//declaring components
	private Utilities ut;
	
	private JTabbedPane tabbedPane;
	
	private JPanel booksPanel;
	private JPanel studentsPanel;
	private JPanel borrowsPanel;
	
	private JTextField bookIsbnEntry;
	private JTextField bookNameEntry;
	private JLabel bookIsbnLabel;
	private JLabel bookNameLabel;
	private JButton enterBookIntoDatabase;
	private JPanel entryBookPanel;
	private JButton deleteBook;
	private JPanel deleteBookPanel;
	private BookTableModel bookTableModel;
	private JTable booktable;
	private JLabel bookMessageBox;
	private JTextField searchBookField;
	
	private JTextField studentIDEntry;
	private JTextField studentNameEntry;
	private JTextField studentGradeEntry;
	private JLabel studentIDLabel;
	private JLabel studentNameLabel;
	private JLabel studentGradeLabel;
	private JButton enterStudentIntoDatabase;
	private JPanel entryStudentPanel;
	private JButton deleteStudent;
	private JPanel deleteStudentPanel;
	private JTable studenttable;
	private StudentTableModel studentTableModel;
	private JLabel studentMessageBox;
	private JTextField searchStudentField;
	
	private JButton borrowStudentButton;
	private JTextField borrowStudentIDEntry;
	private JButton borrowBookButton;
	private JTextField borrowISBNEntry;
	private JLabel borrowDaysLabel;
	private JTextField borrowDaysEntry;
	private JButton enterBorrowIntoDatabase;
	private JPanel entryBorrowPanel;
	private JButton completeBorrow;
	private JPanel completeBorrowPanel;
	private BorrowTableModel borrowTableModel;
	private JTable borrowtable;
	private JLabel borrowMessageBox;
	private JTextField searchBorrowField;
	
	public MyWindow() throws SQLException {
		super("Book Manager");
		setLayout(new BorderLayout());
		
		//new instance of utilities class, so methods from it can be called.
		ut = new Utilities();
		
		//Window with tabs
		
		tabbedPane = new JTabbedPane();
		
		//Setting up panels
		
		booksPanel = new JPanel(new BorderLayout());
		
		entryBookPanel = new JPanel(new GridLayout(12,1));
		deleteBookPanel = new JPanel(new GridLayout(12,1)); 
		booksPanel.add(entryBookPanel, BorderLayout.WEST);
		booksPanel.add(deleteBookPanel, BorderLayout.EAST);
		
		studentsPanel = new JPanel(new BorderLayout());
		
		entryStudentPanel = new JPanel(new GridLayout(12,1));
		deleteStudentPanel = new JPanel(new GridLayout(12,1));
		studentsPanel.add(entryStudentPanel, BorderLayout.WEST);
		studentsPanel.add(deleteStudentPanel, BorderLayout.EAST);
		
		borrowsPanel = new JPanel(new BorderLayout());
		
		entryBorrowPanel = new JPanel(new GridLayout(12,1));
		completeBorrowPanel = new JPanel(new GridLayout(12,1));
		borrowsPanel.add(entryBorrowPanel, BorderLayout.WEST);
		borrowsPanel.add(completeBorrowPanel, BorderLayout.EAST);
		
		//Book panel components
		
		bookIsbnLabel = new JLabel("ISBN number:");
		entryBookPanel.add(bookIsbnLabel);
		bookIsbnEntry = new JTextField(10);
		entryBookPanel.add(bookIsbnEntry);
		bookNameLabel = new JLabel("Book name:");
		entryBookPanel.add(bookNameLabel);
		bookNameEntry = new JTextField(10);
		entryBookPanel.add(bookNameEntry);
		
		enterBookIntoDatabase = new JButton("Input book");
		enterBookIntoDatabase.setBackground(Color.GREEN);
		enterBookIntoDatabase.setOpaque(true); 
		EnterBookListener enterBookListener = new EnterBookListener();
		enterBookIntoDatabase.addActionListener(enterBookListener);
		entryBookPanel.add(enterBookIntoDatabase);
		
		bookTableModel = new BookTableModel();
		booktable = new JTable(bookTableModel);
		booksPanel.add(booktable);
		JScrollPane bookScrollPane = new JScrollPane(booktable);
		booksPanel.add(bookScrollPane, BorderLayout.CENTER);

		deleteBook = new JButton("delete book");
		deleteBook.setBackground(Color.RED);
		deleteBook.setOpaque(true);
		DeleteBookListener deleteBookListener = new DeleteBookListener();
		deleteBook.addActionListener(deleteBookListener);
		deleteBookPanel.add(deleteBook);
		
		bookMessageBox = new JLabel(ut.getMessage());
		booksPanel.add(bookMessageBox, BorderLayout.SOUTH);
		
		JPanel searchBookPanel = new JPanel(new BorderLayout());
		searchBookField = new JTextField(10);
		searchBookField.addKeyListener(new KeyBookEventListener());
		searchBookPanel.add(searchBookField, BorderLayout.CENTER);
		searchBookPanel.add(new JLabel("filter: "), BorderLayout.WEST);
		booksPanel.add(searchBookPanel, BorderLayout.NORTH);
		
		//Student panel components
		
		studentIDLabel = new JLabel("Student ID:");
		entryStudentPanel.add(studentIDLabel);
		
		studentIDEntry = new JTextField(10);
		entryStudentPanel.add(studentIDEntry);
		
		studentNameLabel = new JLabel("Name:");
		entryStudentPanel.add(studentNameLabel);
		
		studentNameEntry = new JTextField(10);
		entryStudentPanel.add(studentNameEntry);
		
		studentGradeLabel = new JLabel("Grade:");
		entryStudentPanel.add(studentGradeLabel);
		
		studentGradeEntry = new JTextField(10);
		entryStudentPanel.add(studentGradeEntry);
		
		enterStudentIntoDatabase = new JButton("Input student");
		enterStudentIntoDatabase.setBackground(Color.GREEN);
		enterStudentIntoDatabase.setOpaque(true);
		EnterStudentListener enterStudentListener = new EnterStudentListener();
		enterStudentIntoDatabase.addActionListener(enterStudentListener);
		entryStudentPanel.add(enterStudentIntoDatabase, BorderLayout.WEST);
		 
		studentTableModel = new StudentTableModel();
		studenttable = new JTable(studentTableModel);
		studentsPanel.add(studenttable);
		JScrollPane studentScrollPane = new JScrollPane(studenttable);
		studentsPanel.add(studentScrollPane, BorderLayout.CENTER);
		
		deleteStudent = new JButton("delete student");
		deleteStudent.setBackground(Color.RED);
		deleteStudent.setOpaque(true);
		DeleteStudentListener deleteStudentListener = new DeleteStudentListener();
		deleteStudent.addActionListener(deleteStudentListener);
		deleteStudentPanel.add(deleteStudent);
		
		studentMessageBox = new JLabel(ut.getMessage());
		studentsPanel.add(studentMessageBox, BorderLayout.SOUTH);
		
		JPanel searchStudentPanel = new JPanel(new BorderLayout());
		searchStudentField = new JTextField(10);
		searchStudentField.addKeyListener(new KeyStudentEventListener());
		searchStudentPanel.add(searchStudentField, BorderLayout.CENTER);
		searchStudentPanel.add(new JLabel("filter: "), BorderLayout.WEST);
		studentsPanel.add(searchStudentPanel, BorderLayout.NORTH);
		 
		//Borrow panel components
		 
		borrowStudentIDEntry = new JTextField(10);
		entryBorrowPanel.add(borrowStudentIDEntry);
		
		borrowStudentButton = new JButton("Choose Student");
		BorrowStudentButtonListener borrowStudentButtonListener = new BorrowStudentButtonListener();
		borrowStudentButton.addActionListener(borrowStudentButtonListener);
		entryBorrowPanel.add(borrowStudentButton);
		
		borrowISBNEntry = new JTextField(10);
		entryBorrowPanel.add(borrowISBNEntry);
		
		borrowBookButton = new JButton("Choose book");
		BorrowBookButtonListener borrowBookButtonListener = new BorrowBookButtonListener();
		borrowBookButton.addActionListener(borrowBookButtonListener);
		entryBorrowPanel.add(borrowBookButton);
		
		borrowDaysLabel = new JLabel("Days to lend:");
		entryBorrowPanel.add(borrowDaysLabel);
		
		borrowDaysEntry = new JTextField(10);
		entryBorrowPanel.add(borrowDaysEntry);
		
		enterBorrowIntoDatabase = new JButton("Input borrow");
		enterBorrowIntoDatabase.setBackground(Color.GREEN);
		enterBorrowIntoDatabase.setOpaque(true);
		EnterBorrowListener enterBorrowListener = new EnterBorrowListener();
		enterBorrowIntoDatabase.addActionListener(enterBorrowListener);
		entryBorrowPanel.add(enterBorrowIntoDatabase);
		
		borrowTableModel = new BorrowTableModel();
		borrowtable = new JTable(borrowTableModel);
		JScrollPane borrowScrollPane = new JScrollPane(borrowtable);
		borrowsPanel.add(borrowScrollPane, BorderLayout.CENTER);
		
		completeBorrow = new JButton("complete borrow");
		completeBorrow.setBackground(Color.RED);
		completeBorrow.setOpaque(true);
		CompleteBorrowListener completeBorrowListener = new CompleteBorrowListener();
		completeBorrow.addActionListener(completeBorrowListener);
		completeBorrowPanel.add(completeBorrow);
		
		borrowMessageBox = new JLabel(ut.getMessage());
		borrowsPanel.add(borrowMessageBox, BorderLayout.SOUTH);
		
		JPanel searchBorrowPanel = new JPanel(new BorderLayout());
		searchBorrowField = new JTextField(10);
		searchBorrowField.addKeyListener(new KeyBorrowEventListener());
		searchBorrowPanel.add(searchBorrowField, BorderLayout.CENTER);
		searchBorrowPanel.add(new JLabel("filter: "), BorderLayout.WEST);
		borrowsPanel.add(searchBorrowPanel, BorderLayout.NORTH);
		
		//Adding panels to tabs
		tabbedPane.addTab("books", booksPanel);
		tabbedPane.addTab("students", studentsPanel);
		tabbedPane.addTab("borrows", borrowsPanel);
		
		//Adding parent panel to JFrame
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	//Button action listeners
	
	class EnterBookListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(bookIsbnEntry.getText().isEmpty() || bookNameEntry.getText().isEmpty()) {
				JOptionPane.showMessageDialog(tabbedPane, "One or more required fields is empty");
			} else {
				if(bookIsbnEntry.getText().matches("[0-9]+")) {
					if(!existsInTable(booktable, bookIsbnEntry.getText())) {
						ut.addBook(bookIsbnEntry.getText(), bookNameEntry.getText());
						((AbstractTableModel) booktable.getModel()).fireTableDataChanged();
						bookMessageBox.setText(ut.getMessage());
						bookIsbnEntry.setText("");
						bookNameEntry.setText("");
					} else {
						JOptionPane.showMessageDialog(tabbedPane, "Book ISBN alrady exists");
					}
				} else {
					JOptionPane.showMessageDialog(tabbedPane, "BookISBN must only contain numbers");
				}
			}
		}
	}
	
	class DeleteBookListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(booktable.getSelectionModel().isSelectionEmpty()) {
				JOptionPane.showMessageDialog(tabbedPane, "No book is selected");
			} else {
				ut.removeBook((String) booktable.getValueAt(booktable.getSelectedRow(), 0));
				((AbstractTableModel) booktable.getModel()).fireTableDataChanged();
				((AbstractTableModel) borrowtable.getModel()).fireTableDataChanged();
				bookMessageBox.setText(ut.getMessage());
			}
		}
	}
	
	class EnterStudentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(studentIDEntry.getText().isEmpty() || studentNameEntry.getText().isEmpty() || studentGradeEntry.getText().isEmpty()) {
				JOptionPane.showMessageDialog(tabbedPane, "One or more required fields is empty");
			} else {
				if(studentIDEntry.getText().matches("[0-9]+")) {
					if(!existsInTable(studenttable, studentIDEntry.getText())) {
						ut.addStudent(Integer.parseInt(studentIDEntry.getText()), studentNameEntry.getText(), studentGradeEntry.getText());
						((AbstractTableModel) studenttable.getModel()).fireTableDataChanged();
						studentMessageBox.setText(ut.getMessage());
						studentIDEntry.setText("");
						studentNameEntry.setText("");
						studentGradeEntry.setText("");
					} else {
						JOptionPane.showMessageDialog(tabbedPane, "student ID already exists");
					}
					
				} else {
					JOptionPane.showMessageDialog(tabbedPane, "ID must be numbers only");
				}
				
			}
		}
	}
	
	class DeleteStudentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(studenttable.getSelectionModel().isSelectionEmpty()) {
				JOptionPane.showMessageDialog(tabbedPane, "No student is selected");
			} else {
				ut.removeStudent((int) studenttable.getValueAt(studenttable.getSelectedRow(), 0));
				((AbstractTableModel) studenttable.getModel()).fireTableDataChanged();
				((AbstractTableModel) borrowtable.getModel()).fireTableDataChanged();
				studentMessageBox.setText(ut.getMessage());
			}
		}
	}
	
	class EnterBorrowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(borrowStudentIDEntry.getText().isEmpty() || borrowISBNEntry.getText().isEmpty() 
						|| borrowDaysEntry.getText().isEmpty()) {
					JOptionPane.showMessageDialog(tabbedPane, "One or more required fields is empty");
				} else {
					if(existsInTable(studenttable, borrowStudentIDEntry.getText()) 
							&& existsInTable(booktable, borrowISBNEntry.getText())) {
						if(borrowDaysEntry.getText().matches("[0-9]+")) {
							ut.addBorrow(Integer.parseInt(borrowStudentIDEntry.getText()), borrowISBNEntry.getText(), 
									ut.getDueDate(Integer.parseInt(borrowDaysEntry.getText())));
							if(ut.bookBorrowed) {
								JOptionPane.showMessageDialog(tabbedPane, "Book is already borrowed");
								borrowMessageBox.setText(ut.getMessage());
							} else {
								((AbstractTableModel) borrowtable.getModel()).fireTableDataChanged();
								((AbstractTableModel) booktable.getModel()).fireTableDataChanged();
								borrowMessageBox.setText(ut.getMessage());
								borrowStudentIDEntry.setText("");
								borrowISBNEntry.setText("");
								borrowDaysEntry.setText("");
							}
						} else {
							JOptionPane.showMessageDialog(tabbedPane, "Days must be a positive number");
						}
					}
					else {
						JOptionPane.showMessageDialog(tabbedPane, "student ID or ISBN does not exist");
					}		
				} 
			} catch (SQLException e1) {
				borrowMessageBox.setText(e1.toString());
			} catch (NumberFormatException e1) {
				borrowMessageBox.setText(e1.toString());
			} catch (ParseException e1) {
				borrowMessageBox.setText(e1.toString());
			}
		}
	}
	
	class CompleteBorrowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(borrowtable.getSelectionModel().isSelectionEmpty()) {
				JOptionPane.showMessageDialog(tabbedPane, "No borrow is selected");
			} else {
				ut.completeBorrow((String) borrowtable.getValueAt(borrowtable.getSelectedRow(), 1)); 
				((AbstractTableModel) borrowtable.getModel()).fireTableDataChanged();
				((AbstractTableModel) booktable.getModel()).fireTableDataChanged();
				borrowMessageBox.setText(ut.getMessage());
			}
		}
	}
	
	class BorrowStudentButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JScrollPane(new JTable(new StudentTableModel())), 
					BorderLayout.CENTER);
			panel.add(new JLabel("Enter ID"), BorderLayout.SOUTH);
			String input = JOptionPane.showInputDialog(panel);
			borrowStudentIDEntry.setText(input);
		}
	}
	
	class BorrowBookButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JScrollPane(new JTable(new AvailableBookTableModel())), 
					BorderLayout.CENTER);
			panel.add(new JLabel("Enter ISBN"), BorderLayout.SOUTH);
			String input = JOptionPane.showInputDialog(panel);
			borrowISBNEntry.setText(input);
		}
	}
	
	//Table models
	
	class BookTableModel extends AbstractTableModel {
		private String[] columnNames = {"ISBN", "Title", "Available"};
		 
		public String getColumnName(int col) {
			return columnNames[col].toString();
		}
		
		public int getRowCount() {
			try {
				return ut.viewBooks().length;
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return 0;
				}
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				return ut.viewBooks()[rowIndex][columnIndex];
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return null;
			}
		}
	}
	
	class AvailableBookTableModel extends AbstractTableModel {
		private String[] columnNames = {"ISBN", "Title"};
		 
		public String getColumnName(int col) {
			return columnNames[col].toString();
		}
		
		public int getRowCount() {
			try {
				return ut.viewAvailableBooks().length;
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return 0;
				}
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				return ut.viewAvailableBooks()[rowIndex][columnIndex];
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return null;
			}
		}
	}
	
	
	class StudentTableModel extends AbstractTableModel {
		private String[] columnNames = {"ID", "Name", "Grade"};
		
		public String getColumnName(int col) {
			return columnNames[col].toString();
		}
		
		public int getRowCount() {
			try {
				return ut.viewStudents().length;
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return 0;
			}
		}
		
		public int getColumnCount() {
			return columnNames.length;
		}
		
		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				return ut.viewStudents()[rowIndex][columnIndex];
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return null;
			}
		}
	}
	
	class BorrowTableModel extends AbstractTableModel {
		private String[] columnNames = {"Student ID", "Book ISBN", "Begin Date", "Due Date"};
		
		public String getColumnName(int col) {
			return columnNames[col].toString();
		}
		
		public int getRowCount() {
			try {
				return ut.viewIncompleteBorrows().length;
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return 0;
			}
		}
		
		public int getColumnCount() {
			return columnNames.length;
		}
		
		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				return ut.viewIncompleteBorrows()[rowIndex][columnIndex];
			} catch(SQLException e) {
				JOptionPane.showMessageDialog(tabbedPane, e);
				return null;
			}
		}
	}
	
	
	//Search filter methods
	
	void bookFilter(String query) {
		TableRowSorter<BookTableModel> btr = new 
				TableRowSorter<BookTableModel>(bookTableModel);
		booktable.setRowSorter(btr);
		
		btr.setRowFilter(RowFilter.regexFilter(query));
	}
	
	void studentFilter(String query) {
		TableRowSorter<StudentTableModel> btr = new TableRowSorter<StudentTableModel>(studentTableModel);
		studenttable.setRowSorter(btr);
		
		btr.setRowFilter(RowFilter.regexFilter(query));
	}
	
	void borrowFilter(String query) {
		TableRowSorter<BorrowTableModel> btr = new TableRowSorter<BorrowTableModel>(borrowTableModel);
		borrowtable.setRowSorter(btr);
		
		btr.setRowFilter(RowFilter.regexFilter(query));
	}
	
	//Keyboard event listeners
	
	public class KeyBookEventListener implements KeyListener {


		@Override
		public void keyReleased(KeyEvent e) {
			String query = searchBookField.getText();
			bookFilter(query);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
		}
		
	}
	
	public class KeyStudentEventListener implements KeyListener {


		@Override
		public void keyReleased(KeyEvent e) {
			String query = searchStudentField.getText();
			studentFilter(query);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
		}
		
	}
	
	public class KeyBorrowEventListener implements KeyListener {


		@Override
		public void keyReleased(KeyEvent e) {
			String query = searchBorrowField.getText();
			borrowFilter(query);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
		}
	}
	
	//Method for checking if item exists in a table
	
	public boolean existsInTable(JTable table, String entry) {

	    int rowCount = table.getRowCount();
	    
	    String item;
	    for (int i = 0; i < rowCount; i++) {
	        	item = table.getValueAt(i, 0).toString();
	        	if (Integer.parseInt(item) == Integer.parseInt(entry)) {
		            return true;
	        	}
	    }
	    return false;
	}
	
}  

