package launcher;

import com.sun.source.tree.UsesTree;
import model.*;
import ui.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final Map<String, Question> questionMap = new HashMap<>();
    private static final List<Notification> notifications = new ArrayList<>();
    private static final Map<String, Course> courseMap = new HashMap<>();
    private static final List<Result> resultsList = new ArrayList<>();

    public static List<Result> getResultList() {
        return resultsList;
    }

    public static List<Notification> getNotifications() {
        return notifications;
    }

    public static Map<String, Question> getQuestionMap() {
        return questionMap;
    }

    public static Map<String, Account> getAccounts() {
        return accounts;
    }

    public static Map<String, Course> getCourseMap() {
        return courseMap;
    }

    public static void main(String[] args) {
        String[] departments = {"CSE", "EEE", "BBA", "Civil"};
        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        for (int i = 1; i <= 10000; i++) {
            String studentId = String.format("S%03d", i);
            String firstName = "Student" + i;
            String lastName = "Last" + i;
            String email = "student" + i + "@university.edu";
            String gender = (i % 2 == 0) ? "Male" : "Female";
            String dob = String.format("2000-%02d-%02d", (i % 12) + 1, (i % 28) + 1);
            String year = years[(int)(Math.random() * years.length)];
            int roll = i;
            String department = departments[(int)(Math.random() * departments.length)];
            double cgpa = 1.0 + (Math.random() * 2.0);
            String session = "";
            if (year.equals(years[3])) {
                session = "2021-2022";
            } else if (year.equals(years[2])) {
                session = "2022-2023";
            } else if (year.equals(years[1])) {
                session = "2023-2024";
            } else if (year.equals(years[0])) {
                session = "2024-2025";
            }
            StudentAccount student = new StudentAccount(studentId, "pass" + i, email, firstName, lastName, 
                                                     gender, dob, year, roll, department, "Student", cgpa, session);
            accounts.put(studentId, student);
        }

        for (int i = 1; i <= 100; i++) {
            String teacherId = String.format("T%03d", i);
            String firstName = "Teacher" + i;
            String lastName = "Last" + i;
            String email = "teacher" + i + "@university.edu";
            String gender = (i % 2 == 0) ? "Male" : "Female";
            String dob = String.format("1980-%02d-%02d", (i % 12) + 1, (i % 28) + 1);
            String department = getDepartmentByIndex(i);
            
            TeacherAccount teacher = new TeacherAccount(teacherId, "pass" + i, email, firstName, lastName, 
                                                     gender, dob, department, "Teacher");
            accounts.put(teacherId, teacher);
        }

        String[] courseIds = {"CSE101", "CSE102", "CSE201", "CSE202", "CSE301", "CSE302", "CSE401", "CSE402",
                             "EEE101", "EEE102", "EEE201", "EEE202", "EEE301", "EEE302",
                             "BBA101", "BBA102", "BBA201", "BBA202", "BBA301", "BBA302"};
        
        String[] courseNames = {"Introduction to Programming", "Data Structures", "Object Oriented Programming", 
                               "Database Systems", "Software Engineering", "Computer Networks", 
                               "Artificial Intelligence", "Machine Learning",
                               "Circuit Analysis", "Digital Electronics", "Electromagnetic Theory", 
                               "Power Systems", "Control Systems", "Communication Systems",
                               "Business Management", "Marketing Principles", "Financial Accounting", 
                               "Business Law", "Strategic Management", "International Business"};
        
        int[] credits = {3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4};
        int[] maxStudents = {30, 25, 30, 25, 30, 25, 30, 25, 30, 25, 30, 25, 30, 25, 30, 25, 30, 25, 30, 25};
        
        for (int i = 0; i < 20; i++) {
            Course course = new Course(courseIds[i], courseNames[i], credits[i], maxStudents[i]);
            courseMap.put(courseIds[i], course);
        }

        List<SingleQuestion> singleQuestionList1 = new ArrayList<>();
        singleQuestionList1.add(new SingleQuestion("1+1=?", new String[]{"1", "2", "3", "4"}, "2"));
        singleQuestionList1.add(new SingleQuestion("1-1=?", new String[]{"0", "2", "3", "4"}, "0"));
        singleQuestionList1.add(new SingleQuestion("1+2=?", new String[]{"1", "2", "3", "4"}, "3"));
        singleQuestionList1.add(new SingleQuestion("1*1=?", new String[]{"1", "2", "3", "4"}, "1"));
        singleQuestionList1.add(new SingleQuestion("1+3=?", new String[]{"1", "2", "3", "4"}, "4"));
        singleQuestionList1.add(new SingleQuestion("4-1=?", new String[]{"1", "2", "3", "4"}, "3"));
        Question q1 = new Question(singleQuestionList1, "100", null, null, null, null, null);

        questionMap.put(q1.getQuestionCode(), q1);

        for (int i = 0; i < 100; i++) {
            String studentId = String.format("S%03d", 1 + (int)(Math.random() * 10000));
            Account acc = accounts.get(studentId);
            if (!(acc instanceof StudentAccount)) continue;
            StudentAccount student = (StudentAccount) acc;
            List<String> qCodes = new ArrayList<>(questionMap.keySet());
            if (qCodes.isEmpty()) break;
            String questionCode = qCodes.get((int)(Math.random() * qCodes.size()));
            int correct = (int)(Math.random() * 10);
            int incorrect = (int)(Math.random() * 10);
            double mark = correct * 2.5;
            double cgpa = 1.0 + (Math.random() * 2.0);
            Result result = new Result(student, mark, correct, incorrect, questionCode, null, cgpa);
            resultsList.add(result);
        }

        //new LoginForm();
        new ResultList();
    }
    
    private static String getDepartmentByIndex(int index) {
        String[] departments = {"CSE", "EEE", "BBA", "Civil"};
        return departments[index % departments.length];
    }
    
    private static String getYearByIndex(int index) {
        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        return years[index % years.length];
    }
}