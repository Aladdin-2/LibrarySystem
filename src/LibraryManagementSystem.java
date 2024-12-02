import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final String FILE_NAME = "BookList.txt";
    private static final Scanner scan = new Scanner(System.in);


    public void addBook() {
        System.out.println("Write the book Id! ");
        int id = scan.nextInt();
        scan.nextLine();

        System.out.println("Write the title! ");
        String title = scan.nextLine();

        System.out.println("Write the author! ");
        String author = scan.nextLine();

        System.out.println("Write the genre! ");
        String genre = scan.nextLine();

        System.out.println("Write the availability! ");
        boolean availability = scan.nextBoolean();
        scan.nextLine();

        addBookToFile(new Book(id, title, author, genre, availability));
        System.out.println(new Book(id, title, author, genre, availability));

    }


    public void addBookToFile(Book book) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bufferedWriter.write(book.toString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file:" + e.getMessage());
        }
    }

    public List<Book> readFromFileAllBook() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                int id = Integer.parseInt(bookDetails[0].trim());
                String title = bookDetails[1].trim();
                String author = bookDetails[2].trim();
                String genre = bookDetails[3].trim();
                boolean availability = Boolean.parseBoolean(bookDetails[4].trim());
                books.add(new Book(id, title, author, genre, availability));

            }
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file:" + e.getMessage());
        }
        return books;
    }

    public List<Book> readFromFileById(int findingID) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                int id = Integer.parseInt(bookDetails[0].trim());
                if (id == findingID) {
                    String title = bookDetails[1].trim();
                    String author = bookDetails[2].trim();
                    String genre = bookDetails[3].trim();
                    boolean availability = Boolean.parseBoolean(bookDetails[4].trim());
                    scan.nextLine();
                    books.add(new Book(id, title, author, genre, availability));
                    System.out.println(books);
                    return books;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }
        System.out.println("There is no book with this ID: " + findingID);
        return null;
    }

    public void deleteBookFromFileById() {
        System.out.println("Write the book id for deleting! ");
        int id = scan.nextInt();
        scan.nextLine();

        List<Book> books = readFromFileAllBook();
        boolean bookFound = books.removeIf(book -> book.getId() == id);

        if (!bookFound) {
            System.out.println("No book found with ID: " + id);
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                bufferedWriter.write(book.toString());
                bufferedWriter.newLine();
            }
            System.out.println("Book with ID " + id + " has been deleted.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public void showBookById() {
        System.out.println("Write the book id to search! ");
        int id = scan.nextInt();
        scan.nextLine();
        readFromFileById(id);
    }

    public void findBookByTitle() {
        System.out.println("Write the book id to clarify!");
        int findingID = scan.nextInt();
        scan.nextLine();
        System.out.println("Write the book title to search!");
        String findingTitle = scan.nextLine();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                int id = Integer.parseInt(bookDetails[0].trim());
                String title = bookDetails[1].trim();
                String author = bookDetails[2].trim();
                String genre = bookDetails[3].trim();
                boolean availability = Boolean.parseBoolean(bookDetails[4].trim());
                if (id == findingID && title.equalsIgnoreCase(findingTitle)) {
                    System.out.println(new Book(id, title, author, genre, availability));
                }
            }
            System.out.println("There is no book with this ID and title: " + findingID + " -- " + findingTitle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void mainMenu() {
        while (true) {
            System.out.println("     Choice!");
            System.out.println("1.Add book.");
            System.out.println("2.Show all books.");
            System.out.println("3.Search for a book by title.");
            System.out.println("4.Delete book by Id.");
            System.out.println("5.Exit ...");
            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    readFromFileAllBook();
                    break;
                case 3:
                    findBookByTitle();
                    break;
                case 4:
                    deleteBookFromFileById();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice! ");

            }
        }
    }


}
