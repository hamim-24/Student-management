package launcher;

import model.*;
import ui.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final Map<String, Question> questionMap = new HashMap<>();
    private static final Map<String, Result> resultMap = new HashMap<>();
    private static final List<Notification> notifications = new ArrayList<>();

    public static List<Notification> getNotifications() {
        return notifications;
    }

    public static Map<String, Result> getResultMap() {
        return resultMap;
    }

    public static Map<String, Question> getQuestionMap() {
        return questionMap;
    }

    public static Map<String, Account> getAccounts() {
        return accounts;
    }

    public static void main(String[] args) {
        StudentAccount s1 = new StudentAccount("S001", "pass1", "s1@mail.com", "John", "Doe", "Male", "2000-01-01", "1st Year", 1, "CSE", "Student", 1.5);
        StudentAccount s2 = new StudentAccount("S002", "pass2", "s2@mail.com", "Jane", "Smith", "Female", "2000-02-02", "1st Year", 2, "CSE", "Student", 3.7);
        StudentAccount s3 = new StudentAccount("S003", "pass3", "s3@mail.com", "Mike", "Johnson", "Male", "2000-03-03", "2nd Year", 3, "EEE", "Student", 2.9);
        StudentAccount s4 = new StudentAccount("S004", "pass4", "s4@mail.com", "Emily", "Davis", "Female", "2000-04-04", "2nd Year", 4, "CSE", "Student", 3.2);
        StudentAccount s5 = new StudentAccount("S005", "pass5", "s5@mail.com", "Chris", "Brown", "Male", "2000-05-05", "3rd Year", 5, "BBA", "Student", 3.0);
        StudentAccount s6 = new StudentAccount("S006", "pass6", "s6@mail.com", "Anna", "Taylor", "Female", "2000-06-06", "3rd Year", 6, "CSE", "Student", 2.4);
        StudentAccount s7 = new StudentAccount("S007", "pass7", "s7@mail.com", "Robert", "Wilson", "Male", "2000-07-07", "3rd Year", 7, "CSE", "Student", 3.9);
        StudentAccount s8 = new StudentAccount("S008", "pass8", "s8@mail.com", "Sophia", "Martinez", "Female", "2000-08-08", "4th Year", 8, "EEE", "Student", 2.8);
        StudentAccount s9 = new StudentAccount("S009", "pass9", "s9@mail.com", "David", "Anderson", "Male", "2000-09-09", "4th Year", 9, "CSE", "Student", 3.6);
        StudentAccount s10 = new StudentAccount("S010", "pass10", "s10@mail.com", "Mia", "Thomas", "Female", "2000-10-10", "4th Year", 10, "CSE", "Student", 2.5);
        StudentAccount s11 = new StudentAccount("S011", "pass11", "s11@mail.com", "Ethan", "Jackson", "Male", "2000-11-11", "4th Year", 11, "BBA", "Student", 3.3);
        StudentAccount s12 = new StudentAccount("S012", "pass12", "s12@mail.com", "Ava", "White", "Female", "2000-12-12", "2nd Year", 12, "CSE", "Student", 3.8);
        StudentAccount s13 = new StudentAccount("S013", "pass13", "s13@mail.com", "Liam", "Harris", "Male", "2000-01-13", "2nd Year", 13, "EEE", "Student", 2.6);
        StudentAccount s14 = new StudentAccount("S014", "pass14", "s14@mail.com", "Olivia", "Martin", "Female", "2000-02-14", "2nd Year", 14, "CSE", "Student", 3.1);
        StudentAccount s15 = new StudentAccount("S015", "pass15", "s15@mail.com", "Noah", "Thompson", "Male", "2000-03-15", "3rd Year", 15, "CSE", "Student", 2.7);
        StudentAccount s16 = new StudentAccount("S016", "pass16", "s16@mail.com", "Isabella", "Garcia", "Female", "2000-04-16", "3rd Year", 16, "BBA", "Student", 3.4);
        StudentAccount s17 = new StudentAccount("S017", "pass17", "s17@mail.com", "Lucas", "Martinez", "Male", "2000-05-17", "3rd Year", 17, "CSE", "Student", 3.0);
        StudentAccount s18 = new StudentAccount("S018", "pass18", "s18@mail.com", "Charlotte", "Rodriguez", "Female", "2000-06-18", "4th Year", 18, "EEE", "Student", 2.3);
        StudentAccount s19 = new StudentAccount("S019", "pass19", "s19@mail.com", "James", "Lee", "Male", "2000-07-19", "4th Year", 19, "CSE", "Student", 3.6);
        StudentAccount s20 = new StudentAccount("S020", "pass20", "s20@mail.com", "Amelia", "Walker", "Female", "2000-08-20", "4th Year", 20, "CSE", "Student", 2.2);


        accounts.put(s1.getID(), s1);
        accounts.put(s2.getID(), s2);
        accounts.put(s3.getID(), s3);
        accounts.put(s4.getID(), s4);
        accounts.put(s5.getID(), s5);
        accounts.put(s6.getID(), s6);
        accounts.put(s7.getID(), s7);
        accounts.put(s8.getID(), s8);
        accounts.put(s9.getID(), s9);
        accounts.put(s10.getID(), s10);
        accounts.put(s11.getID(), s11);
        accounts.put(s12.getID(), s12);
        accounts.put(s13.getID(), s13);
        accounts.put(s14.getID(), s14);
        accounts.put(s15.getID(), s15);
        accounts.put(s16.getID(), s16);
        accounts.put(s17.getID(), s17);
        accounts.put(s18.getID(), s18);
        accounts.put(s19.getID(), s19);
        accounts.put(s20.getID(), s20);

        TeacherAccount t1 = new TeacherAccount("T001", "pass1", "t1@mail.com", "Alan", "Turing", "Male", "1970-01-01", "CSE", "Teacher");
        TeacherAccount t2 = new TeacherAccount("T002", "pass2", "t2@mail.com", "Ada", "Lovelace", "Female", "1975-02-02", "CSE", "Teacher");
        TeacherAccount t3 = new TeacherAccount("T003", "pass3", "t3@mail.com", "Grace", "Hopper", "Female", "1980-03-03", "EEE", "Teacher");
        TeacherAccount t4 = new TeacherAccount("T004", "pass4", "t4@mail.com", "James", "Gosling", "Male", "1978-04-04", "CSE", "Teacher");
        TeacherAccount t5 = new TeacherAccount("T005", "pass5", "t5@mail.com", "Dennis", "Ritchie", "Male", "1972-05-05", "CSE", "Teacher");
        TeacherAccount t6 = new TeacherAccount("T006", "pass6", "t6@mail.com", "Barbara", "Liskov", "Female", "1976-06-06", "BBA", "Teacher");
        TeacherAccount t7 = new TeacherAccount("T007", "pass7", "t7@mail.com", "Brian", "Kernighan", "Male", "1974-07-07", "CSE", "Teacher");
        TeacherAccount t8 = new TeacherAccount("T008", "pass8", "t8@mail.com", "Margaret", "Hamilton", "Female", "1971-08-08", "EEE", "Teacher");
        TeacherAccount t9 = new TeacherAccount("T009", "pass9", "t9@mail.com", "Ken", "Thompson", "Male", "1982-09-09", "CSE", "Teacher");
        TeacherAccount t10 = new TeacherAccount("T010", "pass10", "t10@mail.com", "Linus", "Torvalds", "Male", "1985-10-10", "CSE", "Teacher");
        TeacherAccount t11 = new TeacherAccount("T011", "pass11", "t11@mail.com", "Tim", "Berners-Lee", "Male", "1960-11-11", "BBA", "Teacher");
        TeacherAccount t12 = new TeacherAccount("T012", "pass12", "t12@mail.com", "Sheryl", "Sandberg", "Female", "1979-12-12", "CSE", "Teacher");
        TeacherAccount t13 = new TeacherAccount("T013", "pass13", "t13@mail.com", "Susan", "Wojcicki", "Female", "1981-01-13", "EEE", "Teacher");
        TeacherAccount t14 = new TeacherAccount("T014", "pass14", "t14@mail.com", "Jeff", "Dean", "Male", "1983-02-14", "CSE", "Teacher");
        TeacherAccount t15 = new TeacherAccount("T015", "pass15", "t15@mail.com", "Marissa", "Mayer", "Female", "1984-03-15", "CSE", "Teacher");
        TeacherAccount t16 = new TeacherAccount("T016", "pass16", "t16@mail.com", "Satya", "Nadella", "Male", "1986-04-16", "BBA", "Teacher");
        TeacherAccount t17 = new TeacherAccount("T017", "pass17", "t17@mail.com", "Sundar", "Pichai", "Male", "1987-05-17", "CSE", "Teacher");
        TeacherAccount t18 = new TeacherAccount("T018", "pass18", "t18@mail.com", "Ginni", "Rometty", "Female", "1973-06-18", "EEE", "Teacher");
        TeacherAccount t19 = new TeacherAccount("T019", "pass19", "t19@mail.com", "Steve", "Wozniak", "Male", "1988-07-19", "CSE", "Teacher");
        TeacherAccount t20 = new TeacherAccount("T020", "pass20", "t20@mail.com", "Mark", "Zuckerberg", "Male", "1989-08-20", "CSE", "Teacher");

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

        List<SingleQuestion> singleQuestionList1 = new ArrayList<>();
        singleQuestionList1.add(new SingleQuestion("1+1=?", new String[]{"1", "2", "3", "4"}, "2"));
        singleQuestionList1.add(new SingleQuestion("1-1=?", new String[]{"0", "2", "3", "4"}, "0"));
        singleQuestionList1.add(new SingleQuestion("1+2=?", new String[]{"1", "2", "3", "4"}, "3"));
        singleQuestionList1.add(new SingleQuestion("1*1=?", new String[]{"1", "2", "3", "4"}, "1"));
        singleQuestionList1.add(new SingleQuestion("1+3=?", new String[]{"1", "2", "3", "4"}, "4"));
        singleQuestionList1.add(new SingleQuestion("4-1=?", new String[]{"1", "2", "3", "4"}, "3"));
        Question q1 = new Question(singleQuestionList1, "100", null, null, null);

        questionMap.put(q1.getQuestionCode(), q1);

        //new TeacherPanel(null);
        new LoginForm();
        //new MCQQuestionCreator(null);
    }
}