import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<String, Account> accounts = new HashMap<>();
    public static void main(String[] args) {
        // Add all StudentAccount objects directly to the shared accounts map

        StudentAccount s1 = new StudentAccount("S001", "pass123", "john1@mail.com", "John", "Doe", "Male", "2002-01-01", "1st Year", 1, "CSE", "Student");
        StudentAccount s2 = new StudentAccount("S002", "pass234", "jane2@mail.com", "Jane", "Smith", "Female", "2001-05-12", "1st Year", 2, "EEE", "Student");
        StudentAccount s3 = new StudentAccount("S003", "abc321", "ali@mail.com", "Ali", "Khan", "Male", "2000-03-15", "2nd Year", 3, "CSE", "Student");
        StudentAccount s4 = new StudentAccount("S004", "qwerty", "tina@mail.com", "Tina", "Roy", "Female", "1999-09-30", "2nd Year", 4, "BBA", "Student");
        StudentAccount s5 = new StudentAccount("S005", "123456", "leo@mail.com", "Leo", "Miller", "Male", "2002-07-17", "3rd Year", 5, "EEE", "Student");
        StudentAccount s6 = new StudentAccount("S006", "xyz789", "emma@mail.com", "Emma", "Watts", "Female", "2003-11-11", "3rd Year", 6, "CSE", "Student");
        StudentAccount s7 = new StudentAccount("S007", "hello123", "rahim@mail.com", "Rahim", "Uddin", "Male", "2001-02-20", "1st Year", 7, "Civil", "Student");
        StudentAccount s8 = new StudentAccount("S008", "admin321", "nina@mail.com", "Nina", "Paul", "Female", "2000-12-05", "2nd Year", 8, "CSE", "Student");
        StudentAccount s9 = new StudentAccount("S009", "pass007", "raj@mail.com", "Raj", "Das", "Male", "2002-04-10", "4th Year", 9, "EEE", "Student");
        StudentAccount s10 = new StudentAccount("S010", "securepass", "lisa@mail.com", "Lisa", "Ahmed", "Female", "2001-08-23", "4th Year", 10, "CSE", "Student");

        StudentAccount s11 = new StudentAccount("S011", "alpha1", "sam@mail.com", "Sam", "Wick", "Male", "1998-03-03", "3rd Year", 11, "BBA", "Student");
        StudentAccount s12 = new StudentAccount("S012", "beta2", "sana@mail.com", "Sana", "Ali", "Female", "2000-06-26", "2nd Year", 12, "EEE", "Student");
        StudentAccount s13 = new StudentAccount("S013", "gamma3", "farhan@mail.com", "Farhan", "Iqbal", "Male", "2003-01-15", "1st Year", 13, "CSE", "Student");
        StudentAccount s14 = new StudentAccount("S014", "delta4", "megha@mail.com", "Megha", "Singh", "Female", "2001-05-19", "3rd Year", 14, "Civil", "Student");
        StudentAccount s15 = new StudentAccount("S015", "omega5", "nabil@mail.com", "Nabil", "Reza", "Male", "2002-09-12", "2nd Year", 15, "CSE", "Student");
        StudentAccount s16 = new StudentAccount("S016", "theta6", "rani@mail.com", "Rani", "Kumari", "Female", "1999-10-28", "4th Year", 16, "EEE", "Student");
        StudentAccount s17 = new StudentAccount("S017", "lambda7", "elon@mail.com", "Elon", "Musk", "Male", "1981-06-28", "1st Year", 17, "BBA", "Student");
        StudentAccount s18 = new StudentAccount("S018", "mu8", "grace@mail.com", "Grace", "Hopper", "Female", "1995-12-09", "3rd Year", 18, "CSE", "Student");
        StudentAccount s19 = new StudentAccount("S019", "nu9", "alan@mail.com", "Alan", "Turing", "Male", "1998-01-01", "2nd Year", 19, "EEE", "Student");
        StudentAccount s20 = new StudentAccount("S020", "pi10", "ada@mail.com", "Ada", "Lovelace", "Female", "1997-07-20", "4th Year", 20, "CSE", "Student");

        accounts.put("S001", s1);
        accounts.put("S002", s2);
        accounts.put("S003", s3);
        accounts.put("S004", s4);
        accounts.put("S005", s5);
        accounts.put("S006", s6);
        accounts.put("S007", s7);
        accounts.put("S008", s8);
        accounts.put("S009", s9);
        accounts.put("S010", s10);
        accounts.put("S011", s11);
        accounts.put("S012", s12);
        accounts.put("S013", s13);
        accounts.put("S014", s14);
        accounts.put("S015", s15);
        accounts.put("S016", s16);
        accounts.put("S017", s17);
        accounts.put("S018", s18);
        accounts.put("S019", s19);
        accounts.put("S020", s20);

        // Now launch the UI
        new AdministrationForm(); // or new StudentList();
    }


    public static Map<String, Account> getAccounts() {
        return accounts;
    }
}