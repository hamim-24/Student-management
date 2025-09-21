package launcher;

import model.*;
import ui.*;
import util.Utils;

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

        for (int i = 1; i <= 1000; i++) {
            String studentId = String.format("s%d", i);
            String gender = (i % 2 == 0) ? "Male" : "Female";
            String dob = String.format("2000-%02d-%02d", (i % 12) + 1, (i % 28) + 1);
            String year = Utils.YEARS[(int)(Math.random() * (Utils.YEARS.length - 2)) + 1];
            String department = Utils.DEPARTMENTS[(int)(Math.random() * (Utils.DEPARTMENTS.length - 1)) + 1];
            double cgpa = Math.random() * 4.0;
            String[] sessions = {"2021-2022", "2022-2023", "2023-2024", "2024-2025"};
            String session = sessions[(int)(Math.random() * sessions.length)];
            StudentAccount student = new StudentAccount(studentId, "pass" + i, "student" + i + "@university.edu", "Student" + i, "Last" + i,
                                                     gender, dob, year, i, department, "Student", cgpa, session);
            accounts.put(studentId, student);

            Result result = new Result(student, Math.random() * 100, (int) (Math.random() * 100), (int) (Math.random() * 100), "100", null, cgpa);
            resultsList.add(result);
        }

        for (int i = 1; i <= 100; i++) {
            String teacherId = String.format("t%d", i);
            String gender = (i % 2 == 0) ? "Male" : "Female";
            String dob = String.format("1980-%02d-%02d", (i % 12) + 1, (i % 28) + 1);
            String department = Utils.DEPARTMENTS[(int)(Math.random() * (Utils.DEPARTMENTS.length - 1)) + 1];

            TeacherAccount teacher = new TeacherAccount(teacherId, "pass" + i, "teacher" + i + "@university.edu",
                    "Teacher" + i, "Last" + i,
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

        new LoginForm();

    }

}