import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {

        Account t1 = new TeacherAccount("T001", "teach123", "alice@mail.com", "Alice", "Brown", "Female", "1980-05-12", "CSE", "Teacher");
        Account t2 = new TeacherAccount("T002", "pass234", "bob@mail.com", "Bob", "Smith", "Male", "1975-08-20", "EEE", "Teacher");
        Account t3 = new TeacherAccount("T003", "pwd321", "carol@mail.com", "Carol", "Johnson", "Female", "1983-03-15", "BBA", "Teacher");
        Account t4 = new TeacherAccount("T004", "secure444", "dan@mail.com", "Dan", "Williams", "Male", "1988-12-01", "Civil", "Teacher");
        Account t5 = new TeacherAccount("T005", "tpass555", "eve@mail.com", "Eve", "Davis", "Female", "1979-10-28", "CSE", "Teacher");
        Account t6 = new TeacherAccount("T006", "login666", "frank@mail.com", "Frank", "Garcia", "Male", "1982-07-07", "EEE", "Teacher");
        Account t7 = new TeacherAccount("T007", "safe777", "grace@mail.com", "Grace", "Martinez", "Female", "1985-11-14", "BBA", "Teacher");
        Account t8 = new TeacherAccount("T008", "pass888", "henry@mail.com", "Henry", "Rodriguez", "Male", "1981-04-23", "Civil", "Teacher");
        Account t9 = new TeacherAccount("T009", "alpha999", "irene@mail.com", "Irene", "Lopez", "Female", "1978-01-30", "CSE", "Teacher");
        Account t10 = new TeacherAccount("T010", "omega000", "jack@mail.com", "Jack", "Gonzalez", "Male", "1986-06-19", "EEE", "Teacher");
        Account t11 = new TeacherAccount("T011", "teach111", "karen@mail.com", "Karen", "Wilson", "Female", "1976-02-12", "BBA", "Teacher");
        Account t12 = new TeacherAccount("T012", "word222", "leo@mail.com", "Leo", "Anderson", "Male", "1977-09-01", "Civil", "Teacher");
        Account t13 = new TeacherAccount("T013", "secure333", "mia@mail.com", "Mia", "Thomas", "Female", "1984-08-18", "CSE", "Teacher");
        Account t14 = new TeacherAccount("T014", "code444", "nick@mail.com", "Nick", "Taylor", "Male", "1980-10-06", "EEE", "Teacher");
        Account t15 = new TeacherAccount("T015", "admin555", "olivia@mail.com", "Olivia", "Moore", "Female", "1989-03-27", "BBA", "Teacher");
        Account t16 = new TeacherAccount("T016", "login666", "peter@mail.com", "Peter", "Jackson", "Male", "1974-12-15", "Civil", "Teacher");
        Account t17 = new TeacherAccount("T017", "qwerty777", "queen@mail.com", "Queen", "Martin", "Female", "1979-06-11", "CSE", "Teacher");
        Account t18 = new TeacherAccount("T018", "zzpass888", "rob@mail.com", "Rob", "Lee", "Male", "1986-01-09", "EEE", "Teacher");
        Account t19 = new TeacherAccount("T019", "strong999", "sara@mail.com", "Sara", "Perez", "Female", "1982-05-04", "BBA", "Teacher");
        Account t20 = new TeacherAccount("T020", "login000", "tom@mail.com", "Tom", "White", "Male", "1975-11-22", "Civil", "Teacher");
        accounts.put(t1.getID(), t1);
        accounts.put(t2.getID(), t2);
        accounts.put(t3.getID(), t3);
        accounts.put(t4.getID(), t4);
        accounts.put(t5.getID(), t5);
        accounts.put(t6.getID(), t6);
        accounts.put(t7.getID(), t7);
        accounts.put(t8.getID(), t8);
        accounts.put(t9.getID(), t9);
        accounts.put(t10.getID(), t10);
        accounts.put(t11.getID(), t11);
        accounts.put(t12.getID(), t12);
        accounts.put(t13.getID(), t13);
        accounts.put(t14.getID(), t14);
        accounts.put(t15.getID(), t15);
        accounts.put(t16.getID(), t16);
        accounts.put(t17.getID(), t17);
        accounts.put(t18.getID(), t18);
        accounts.put(t19.getID(), t19);
        accounts.put(t20.getID(), t20);


        Account s1 = new StudentAccount("S001", "pass123", "john1@mail.com", "John", "Doe", "Male", "2002-01-01", "1st Year", 1, "CSE", "Student", .8);
        Account s2 = new StudentAccount("S002", "pass456", "jane2@mail.com", "Jane", "Smith", "Female", "2001-05-12", "2nd Year", 2, "EEE", "Student", 3.6);
        Account s3 = new StudentAccount("S003", "pass789", "alex3@mail.com", "Alex", "Johnson", "Male", "2000-11-23", "3rd Year", 3, "BBA", "Student", 3.2);
        Account s4 = new StudentAccount("S004", "pass321", "emma4@mail.com", "Emma", "Williams", "Female", "2002-07-30", "1st Year", 1, "CSE", "Student", 3.9);
        Account s5 = new StudentAccount("S005", "pass654", "liam5@mail.com", "Liam", "Brown", "Male", "2001-03-15", "2nd Year", 2, "EEE", "Student", 3.4);
        Account s6 = new StudentAccount("S006", "pass987", "olivia6@mail.com", "Olivia", "Jones", "Female", "2000-09-18", "3rd Year", 3, "BBA", "Student", 3.7);
        Account s7 = new StudentAccount("S007", "pass147", "noah7@mail.com", "Noah", "Garcia", "Male", "2002-02-25", "1st Year", 1, "CSE", "Student", 3.5);
        Account s8 = new StudentAccount("S008", "pass258", "ava8@mail.com", "Ava", "Martinez", "Female", "2001-06-11", "2nd Year", 2, "EEE", "Student", 3.3);
        Account s9 = new StudentAccount("S009", "pass369", "will9@mail.com", "William", "Rodriguez", "Male", "2000-12-19", "3rd Year", 3, "BBA", "Student", 3.1);
        Account s10 = new StudentAccount("S010", "pass159", "mia10@mail.com", "Mia", "Hernandez", "Female", "2002-04-03", "1st Year", 1, "CSE", "Student", 3.85);
        Account s11 = new StudentAccount("S011", "pass753", "lucas11@mail.com", "Lucas", "Lopez", "Male", "2001-08-27", "2nd Year", 2, "EEE", "Student", 3.67);
        Account s12 = new StudentAccount("S012", "pass852", "isabella12@mail.com", "Isabella", "Gonzalez", "Female", "2000-10-14", "3rd Year", 3, "BBA", "Student", 3.21);
        Account s13 = new StudentAccount("S013", "pass951", "mason13@mail.com", "Mason", "Wilson", "Male", "2002-06-21", "1st Year", 1, "CSE", "Student", 3.95);
        Account s14 = new StudentAccount("S014", "pass357", "sophia14@mail.com", "Sophia", "Anderson", "Female", "2001-01-17", "2nd Year", 2, "EEE", "Student", 3.43);
        Account s15 = new StudentAccount("S015", "pass456", "logan15@mail.com", "Logan", "Thomas", "Male", "2000-05-29", "3rd Year", 3, "BBA", "Student", 3.29);
        Account s16 = new StudentAccount("S016", "pass654", "charlotte16@mail.com", "Charlotte", "Taylor", "Female", "2002-09-13", "1st Year", 1, "CSE", "Student", 3.77);
        Account s17 = new StudentAccount("S017", "pass852", "elijah17@mail.com", "Elijah", "Moore", "Male", "2001-12-07", "2nd Year", 2, "EEE", "Student", 3.62);
        Account s18 = new StudentAccount("S018", "pass159", "amelia18@mail.com", "Amelia", "Jackson", "Female", "2000-03-22", "3rd Year", 3, "BBA", "Student", 3.12);
        Account s19 = new StudentAccount("S019", "pass357", "james19@mail.com", "James", "Martin", "Male", "2002-11-05", "1st Year", 1, "CSE", "Student", 3.68);
        Account s20 = new StudentAccount("S020", "pass753", "harper20@mail.com", "Harper", "Lee", "Female", "2001-02-16", "2nd Year", 2, "EEE", "Student", 3.58);

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

        new LoginForm();
    }


    public static Map<String, Account> getAccounts() {
        return accounts;
    }

}