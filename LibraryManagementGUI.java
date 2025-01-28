# JavaAssignmentimport javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Book {
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issue() {
        isIssued = true;
    }

    public void returnBook() {
        isIssued = false;
    }
}

public class LibraryManagementGUI extends JFrame {
    private ArrayList<Book> books = new ArrayList<>();
    private DefaultTableModel tableModel;

    public LibraryManagementGUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout());

        // Table to display books
        String[] columns = {"Title", "Author", "Issued"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBookButton = new JButton("Add Book");
        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton viewBooksButton = new JButton("View Books");

        buttonPanel.add(addBookButton);
        buttonPanel.add(issueBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(viewBooksButton);

        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addBookButton.addActionListener(e -> addBook());
        issueBookButton.addActionListener(e -> issueBook());
        returnBookButton.addActionListener(e -> returnBook());
        viewBooksButton.addActionListener(e -> viewBooks());

        setVisible(true);
    }

    private void addBook() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();

        Object[] message = {
                "Title:", titleField,
                "Author:", authorField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty()) {
                books.add(new Book(title, author));
                JOptionPane.showMessageDialog(this, "Book added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both title and author.");
            }
        }
    }

    private void issueBook() {
        String title = JOptionPane.showInputDialog(this, "Enter the title of the book to issue:");
        if (title != null && !title.trim().isEmpty()) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title.trim())) {
                    if (!book.isIssued()) {
                        book.issue();
                        JOptionPane.showMessageDialog(this, "Book issued successfully!");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "This book is already issued.");
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Book not found.");
        }
    }

    private void returnBook() {
        String title = JOptionPane.showInputDialog(this, "Enter the title of the book to return:");
        if (title != null && !title.trim().isEmpty()) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title.trim())) {
                    if (book.isIssued()) {
                        book.returnBook();
                        JOptionPane.showMessageDialog(this, "Book returned successfully!");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "This book was not issued.");
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Book not found.");
        }
    }

    private void viewBooks() {
        tableModel.setRowCount(0); // Clear the table
        for (Book book : books) {
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.isIssued() ? "Yes" : "No"});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryManagementGUI::new);
    }
}
