# Student Management System

## Overview
The Student Management System is a comprehensive Java-based desktop application designed to streamline the management of academic records, exams, and user accounts within an educational institution. Built using Java Swing for the graphical user interface (GUI), the system supports multiple user roles—administrators, teachers, and students—each with tailored access and functionality. The application enables efficient handling of student and teacher data, exam creation and participation, automated grading, and result management, all within an intuitive and user-friendly interface.

## Features
- **User Authentication & Registration:**
  - Secure login for students, teachers, and administrators.
  - New users can register via a dedicated sign-up form with validation.
- **Account Management:**
  - Administrators can view, search, update, and delete student and teacher accounts.
  - Students and teachers can view and update their own profiles.
- **Exam Management:**
  - Teachers can create, edit, and publish multiple-choice question (MCQ) exams.
  - Exams are associated with specific departments and academic years.
  - Exams can be published and made available to students in real time.
- **Exam Participation:**
  - Students can search for available exams using unique codes.
  - Exams feature a real-time countdown timer and immediate feedback upon submission.
  - Automated grading calculates scores and updates student records.
- **Result Management:**
  - Teachers and administrators can view, filter, and export exam results.
  - Students can view their own results and performance history.
  - Results include detailed breakdowns of correct/incorrect answers and calculated GPA.
- **Notifications:**
  - System notifications inform users of important events, such as new exams or results.
- **Administration Panel:**
  - Centralized dashboard for administrators to manage users, exams, and results.
  - Bulk operations and advanced search/filtering capabilities.

## Project Structure
```
Student-management/
├── src/
│   ├── launcher/
│   │   └── Main.java                # Application entry point, initializes sample data and launches the UI
│   ├── model/                       # Data models for users, exams, results, and notifications
│   │   ├── Account.java             # Abstract base class for all user accounts
│   │   ├── StudentAccount.java      # Extends Account with student-specific fields (year, roll, GPA, etc.)
│   │   ├── TeacherAccount.java      # Extends Account for teacher-specific data
│   │   ├── Question.java            # Represents an exam, including metadata and a list of questions
│   │   ├── SingleQuestion.java      # Represents a single MCQ with options and answer
│   │   ├── Result.java              # Stores and processes exam results for students
│   │   ├── Notification.java        # Represents system notifications
│   │   └── QuestionManager.java     # Handles persistence (save/load) of questions
│   ├── ui/                          # User interface components (Java Swing)
│   │   ├── LoginForm.java           # Login screen for all user types
│   │   ├── SignInFrame.java         # Registration form for new users
│   │   ├── AdministrationForm.java  # Admin dashboard for managing users and results
│   │   ├── StudentPanel.java        # Student dashboard for exams and results
│   │   ├── TeacherPanel.java        # Teacher dashboard for exam management
│   │   ├── MCQQuestionCreator.java  # UI for teachers to create MCQ exams
│   │   ├── StudentExamFrame.java    # UI for students to take exams with timer and auto-grading
│   │   ├── ResultList.java          # View and filter exam results
│   │   ├── StudentList.java         # View, filter, and manage student accounts
│   │   └── TeacherList.java         # View, filter, and manage teacher accounts
│   └── util/
│       └── utils.java               # Utility functions, constants, and UI helpers
├── Project-Proposal.pdf             # Project proposal and documentation
└── Student-management.iml           # IntelliJ IDEA project file
```

## Technologies Used
- **Java 8+**: Core programming language
- **Java Swing**: GUI framework for building desktop applications
- **JDK Serialization**: For saving/loading exam data
- **Maven/Gradle (optional)**: For dependency management (if you wish to extend the project)

## How to Run
1. **Requirements:**
   - Java Development Kit (JDK) 8 or higher
   - An IDE such as IntelliJ IDEA or Eclipse (recommended)

2. **Build & Run:**
   - Open the project in your IDE.
   - Set `src/launcher/Main.java` as the main class.
   - Build and run the application. The login screen will appear.

3. **Usage Guide:**
   - **Admin:**
     - Log in with the default admin credentials (see `LoginForm.java`).
     - Access the administration dashboard to manage students, teachers, and results.
   - **Register:**
     - New users can register via the Sign In form, providing all required details.
   - **Teachers:**
     - After logging in, teachers can create new exams, publish them, and view student results.
   - **Students:**
     - Students can log in, search for available exams, participate in exams, and view their results and GPA.

## Main Classes and Their Roles
- **Main.java:** Initializes sample data (students, teachers, exams) and launches the main application window.
- **Account, StudentAccount, TeacherAccount:** Represent user accounts and encapsulate user-specific data and methods.
- **Question, SingleQuestion:** Model the structure of exams and individual MCQs, including options and correct answers.
- **Result:** Collects and processes exam results, calculates scores, and stores performance data.
- **Notification:** Used to display system-wide messages and alerts to users.
- **QuestionManager:** Provides static methods for saving and loading exam data to/from files.
- **utils.java:** Contains utility methods for UI styling, validation, and shared constants.
- **UI Classes:** Each class in `src/ui/` implements a specific window or panel, such as login, registration, dashboards, exam creation, and result viewing. The UI is designed for clarity, ease of use, and accessibility.

## Extensibility
- The project is modular and can be extended with new features, such as:
  - Additional question types (e.g., short answer, essay)
  - Enhanced reporting and analytics
  - Integration with databases for persistent storage
  - Web-based front-end using JavaFX or a web framework
  - Role-based access control and permissions

## Authors
- [Your Name Here]

---
*This project is for educational purposes and demonstrates a full-featured Java Swing application for academic management. Contributions and suggestions are welcome!* 