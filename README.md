# Student Management System

## Overview
The Student Management System is a comprehensive Java-based desktop application designed to streamline the management of academic records, exams, and user accounts within an educational institution. Built using Java Swing for the graphical user interface (GUI), the system supports multiple user roles—administrators, teachers, and students—each with tailored access and functionality. The application enables efficient handling of student and teacher data, exam creation and participation, automated grading, and result management, all within an intuitive and user-friendly interface.

## Key Features

### 🔐 User Authentication & Registration
- **Multi-role Login System**: Secure login for students, teachers, and administrators with role-based access control
- **User Registration**: New users can register via a dedicated sign-up form with comprehensive validation
- **Password Security**: Encrypted password storage and validation
- **Session Management**: Secure session handling for all user types

### 👥 Account Management
- **Administrator Controls**: 
  - View, search, update, and delete student and teacher accounts
  - Bulk operations for user management
  - Advanced filtering and search capabilities
- **Self-Service**: Students and teachers can view and update their own profiles
- **Profile Management**: Comprehensive user profile management with validation

### 📚 Course Management
- **Course Catalog**: 20 pre-configured courses across 4 departments (CSE, EEE, BBA, Civil)
- **Course Details**: Each course includes ID, name, credits, and maximum enrollment capacity
- **Enrollment Tracking**: Real-time tracking of enrolled students vs. capacity
- **Department-wise Organization**: Courses organized by academic departments

### 📝 Exam Management
- **MCQ Exam Creation**: Teachers can create, edit, and publish multiple-choice question exams
- **Exam Configuration**: 
  - Associate exams with specific departments and academic years
  - Set exam duration (30 seconds per question)
  - Configure question codes for easy access
- **Real-time Publishing**: Exams can be published and made available to students instantly
- **Question Management**: Support for multiple questions per exam with 4 options each
- **Retake system**: Those who have fail, didn't got promotion, can seat for retake

### 🎯 Exam Participation
- **Exam Discovery**: Students can search for available exams using unique codes
- **Real-time Timer**: Countdown timer with automatic submission when time expires
- **Immediate Feedback**: Instant grading and score calculation upon submission
- **Auto-grading**: Automated scoring system with detailed performance analysis

### 📊 Result Management
- **Comprehensive Results**: Teachers and administrators can view, filter, and export exam results
- **Student Performance**: Students can view their own results and performance history
- **Detailed Analytics**: Results include breakdowns of correct/incorrect answers and calculated GPA
- **Performance Tracking**: Historical performance data and trend analysis

### 🔔 Notification System
- **System Notifications**: Inform users of important events, such as new exams or results
- **Real-time Updates**: Instant notification delivery for critical system events
- **Notification Center**: Centralized location for all system notifications

### 🛠️ Administration Panel
- **Centralized Dashboard**: Comprehensive admin interface for managing users, exams, and results
- **Bulk Operations**: Efficient management of large datasets
- **Advanced Search**: Powerful filtering and search capabilities
- **Data Export**: Export functionality for reports and analysis

## Technical Architecture

### Project Structure
```
Student-management/
├── src/
│   ├── launcher/
│   │   └── Main.java                # Application entry point with sample data initialization
│   ├── model/                       # Data models and business logic
│   │   ├── Account.java             # Abstract base class for all user accounts
│   │   ├── StudentAccount.java      # Student-specific account with academic details
│   │   ├── TeacherAccount.java      # Teacher-specific account with department info
│   │   ├── Course.java              # Course management with enrollment tracking
│   │   ├── Question.java            # Exam container with metadata and questions
│   │   ├── SingleQuestion.java      # Individual MCQ with options and correct answer
│   │   ├── Result.java              # Exam results with scoring and GPA calculation
│   │   ├── Notification.java        # System notification management
│   │   └── QuestionManager.java     # Persistence layer for exam data
│   ├── ui/                          # User interface components (Java Swing)
│   │   ├── LoginForm.java           # Multi-role login interface
│   │   ├── SignInFrame.java         # User registration form
│   │   ├── AdministrationForm.java  # Admin dashboard and controls
│   │   ├── StudentPanel.java        # Student dashboard and exam access
│   │   ├── TeacherPanel.java        # Teacher dashboard and exam management
│   │   ├── MCQQuestionCreator.java  # Exam creation interface for teachers
│   │   ├── StudentExamFrame.java    # Exam-taking interface with timer
│   │   ├── ResultList.java          # Results viewing and filtering
│   │   ├── StudentList.java         # Student account management
│   │   ├── TeacherList.java         # Teacher account management
│   │   └── CourseList.java          # Course management interface
│   └── util/
│       └── utils.java               # Utility functions and constants
├── Project-Proposal.pdf             # Detailed project documentation
├── Project-Details                  # Detailed project
└── Student-management.iml           # IntelliJ IDEA project configuration
```

### Data Models

#### Account System
- **Account.java**: Base class with common user attributes (ID, password, email, name, gender, DOB, department, status)
- **StudentAccount.java**: Extends Account with academic fields (year, roll number, GPA, session)
- **TeacherAccount.java**: Extends Account with teaching-specific data

#### Academic Management
- **Course.java**: Course management with enrollment tracking, capacity limits, and validation
- **Question.java**: Exam container with metadata (session, exam name, code, year, department, course ID)
- **SingleQuestion.java**: Individual MCQ with question text, options array, and correct answer
- **Result.java**: Exam results with scoring, GPA calculation, and performance metrics

#### System Components
- **Notification.java**: System-wide notification management
- **QuestionManager.java**: File-based persistence for exam data using Java serialization

### UI Architecture
- **Java Swing Framework**: Modern GUI with responsive design
- **Component-based Architecture**: Modular UI components for maintainability
- **Event-driven Programming**: Efficient event handling for user interactions
- **Custom Styling**: Consistent visual design with custom button styling and color schemes

## Technologies Used

### Core Technologies
- **Java 8+**: Core programming language with modern features
- **Java Swing**: GUI framework for building desktop applications
- **JDK Serialization**: For saving/loading exam data to/from files

### Development Tools
- **IntelliJ IDEA**: Recommended IDE with project configuration
- **Maven/Gradle**: Optional dependency management for project extensions

### Design Patterns
- **Model-View-Controller (MVC)**: Separation of data, presentation, and business logic
- **Factory Pattern**: UI component creation through utility classes
- **Observer Pattern**: Event handling for user interactions

## Installation & Setup

### Prerequisites
- **Java Development Kit (JDK) 8 or higher**
- **IntelliJ IDEA** (recommended) or Eclipse
- **Minimum 4GB RAM** for optimal performance
- **Windows/macOS/Linux** operating system

### Build & Run Instructions

1. **Clone/Download the Project**
   ```bash
   git clone <repository-url>
   cd Student-management
   ```

2. **Open in IDE**
   - Open IntelliJ IDEA
   - Select "Open" and choose the project directory
   - Wait for project indexing to complete

3. **Configure Main Class**
   - Set `src/launcher/Main.java` as the main class
   - Ensure Java 8+ is selected as the project SDK

4. **Run the Application**
   - Click the "Run" button or press Shift+F10
   - The login screen will appear automatically

### Sample Data
The application comes pre-loaded with sample data:
- **10,000 Student Accounts**: S001 to S10000 with realistic data
- **100 Teacher Accounts**: T001 to T100 across 4 departments
- **20 Courses**: Across CSE, EEE, BBA, and Civil departments
- **Sample Exam**: Pre-configured MCQ exam with 6 questions
- **100 Sample Results**: Random exam results for demonstration

## Usage Guide

### 🔑 Default Login Credentials

#### Administrator Access
- **ID**: `11111`
- **Password**: `111111`
- **Access**: Full system administration

#### Student Access
- **Sample ID**: `S001` to `S10000`
- **Password**: `pass1` to `pass10000`
- **Example**: `S001` / `pass1`

#### Teacher Access
- **Sample ID**: `T001` to `T100`
- **Password**: `pass1` to `pass100`
- **Example**: `T001` / `pass1`


## Features in Detail

### Component-wise Functionality

#### 1. LoginForm
- **Multi-role Authentication**: Login for administration, students, and teachers
- **Smart Default Buttons**: Administration button as default, register button if admin account is null
- **Enhanced Security**: Password field visibility toggle (shows placeholder when focused, hides with '-' when focus lost)
- **Notification System**: Integrated notification display
- **Placeholder Management**: Dynamic placeholder text for better UX

#### 2. AdministrationForm
- **Centralized Dashboard**: Default search button for quick access
- **Comprehensive Lists**: Student list, teacher list, result list, and course list management
- **Advanced Search**: Search accounts (students and teachers) with filtering capabilities
- **Announcement System**: Create and manage system announcements
- **Promotion Management**: 
  - Calculate promotion statistics (promoted vs not promoted)
  - Automated promotion logic: if StudentAccount matches year, session, and department
  - GPA-based promotion: CG ≥ 2 increases year (for years 1-4) and sets result info
  - Result tracking for all promotion decisions

#### 3. CourseList
- **5-Column Display**: Course ID, Course Name, Credits, Student Capacity, Current Students
- **Full CRUD Operations**: Edit, add, delete courses with validation
- **Edit Panel Management**: Clear edit panel functionality
- **Navigation**: Back button for seamless navigation

#### 4. MCQQuestionCreator
- **Unique Question Codes**: Generate unique identifiers for each question
- **MCQ Management**: Add multiple choice questions with options
- **Validation**: Warning system for empty fields
- **Save Functionality**: Persistent storage of created questions

#### 5. ResultList
- **6-Column Results**: ID, Roll, Marks, Incorrect, Correct, CG
- **Advanced Filtering**: Filter results by year, department, session, and exam code
- **Performance Analytics**: Comprehensive result analysis and reporting

#### 6. SignInFrame
- **Comprehensive Data Collection**: Gather all required user information
- **Student-Specific Fields**: Additional text fields for student registration
- **Email Validation**: Proper email format verification
- **Unique ID System**: Ensure unique user identification
- **Password Security**: Minimum 6-character password requirement
- **Password Confirmation**: Match password validation
- **Account Creation**: Support for both Student and Teacher account types

#### 7. StudentCourseFrame
- **Course Catalog**: Display all available courses
- **Enrollment Management**: Select courses for enrollment and drop-out functionality
- **Course Persistence**: Save course selections and changes

#### 8. StudentExamFrame
- **Exam Interface**: Complete exam-taking experience
- **Result Creation**: Generate exam results automatically
- **Result Storage**: Add results to the system
- **Average CG**: Calculate and display average CG
- **Answer Analysis**: Track correct and incorrect answers
- **Timed Exams**: 30 seconds per question with automatic submission
- **Real-time Timer**: Visual countdown with color-coded warnings
- **Auto-grading**: Instant score calculation and performance analysis
- **Question Navigation**: Easy navigation between questions
- **Answer Validation**: Prevents submission with unanswered questions

#### 9. StudentList
- **8-Column Display**: Roll, Name, ID, Email, Session, Department, GPA, Year
- **Student Management**: Edit student information and delete courses
- **Edit Panel**: Clear edit panel functionality
- **Advanced Filtering**: Filter by year, department, roll, ID, and minimum CG

#### 10. StudentPanel
- **Exam Status**: Display exam running status and result publication
- **Exam Access**: Conditional exam access based on year, department, session, and courseId matching
- **Question Search**: Search questions (hidden when exam is running)
- **Information Display**: Show relevant student information
- **Result Access**: View published results
- **Course Management**: Add courses to student profile
- **Navigation**: Back button for panel navigation

#### 11. TeacherPanel
- **Exam Status**: Display exam running status and result publication
- **Exam Creation**: Create new exams with full configuration
- **Exam Search**: Search exams without student restrictions
- **Retake Exam**: Those, didn't get promotion, Year will be same can be taken exam.
- **Exam Publishing**: Publish exams for specific year, department, session, code, and name
- **Result Publishing**: Publish results for published exams
- **Navigation**: Back button for panel navigation

### User Management
- **Role-based Access**: Different interfaces for different user types
- **Profile Management**: Comprehensive user profile editing
- **Search & Filter**: Advanced search capabilities for large datasets
- **Bulk Operations**: Efficient management of multiple accounts
- **Data Validation**: Input validation and error handling

## System Requirements

### Minimum Requirements
- **OS**: Windows 7+, macOS 10.12+, or Linux (Ubuntu 16.04+)
- **Java**: JDK 8 or higher
- **RAM**: 2GB minimum, 4GB recommended
- **Storage**: 100MB free space
- **Display**: 1024x768 minimum resolution

### Recommended Requirements
- **OS**: Windows 10+, macOS 11+, or Linux (Ubuntu 20.04+)
- **Java**: JDK 11 or higher
- **RAM**: 8GB or higher
- **Storage**: 500MB free space
- **Display**: 1920x1080 or higher resolution

## Extensibility & Future Enhancements

### Potential Extensions
- **Database Integration**: Replace file-based storage with MySQL/PostgreSQL
- **Web Interface**: Develop web-based frontend using Spring Boot
- **Mobile App**: Create mobile companion app for students
- **Advanced Analytics**: Implement detailed performance analytics and reporting
- **API Development**: RESTful API for third-party integrations
- **Cloud Deployment**: Deploy to cloud platforms for scalability

### Additional Features
- **Question Types**: Support for essay, short answer, and file upload questions
- **Exam Scheduling**: Advanced exam scheduling with time slots
- **Proctoring**: Online proctoring capabilities for remote exams
- **Gradebook**: Comprehensive gradebook with assignment tracking
- **Communication**: Built-in messaging system between users
- **Calendar Integration**: Academic calendar and event management

## Troubleshooting

### Common Issues

#### Application Won't Start
- **Check Java Version**: Ensure JDK 8+ is installed and configured
- **Verify Main Class**: Confirm `src/launcher/Main.java` is set as main class
- **Check Dependencies**: Ensure all required libraries are available

#### Login Issues
- **Verify Credentials**: Use correct default credentials
- **Check Case Sensitivity**: IDs and passwords are case-sensitive
- **Clear Cache**: Restart application if login state is corrupted

#### Exam Problems
- **Valid Exam Code**: Ensure exam code exists and is published
- **Time Constraints**: Check if exam is within allowed time window
- **Network Issues**: Ensure stable connection for real-time features

### Performance Optimization
- **Memory Management**: Close unused windows to free memory
- **Data Cleanup**: Regularly clear old results and notifications
- **System Resources**: Ensure adequate RAM and CPU resources

## Contributing

### Development Guidelines
- **Code Style**: Follow Java coding conventions
- **Documentation**: Add comments for complex logic
- **Testing**: Test new features thoroughly before submission
- **Version Control**: Use meaningful commit messages

### Bug Reports
- **Detailed Description**: Provide clear bug description
- **Steps to Reproduce**: Include step-by-step reproduction steps
- **System Information**: Include OS, Java version, and error logs
- **Screenshots**: Attach relevant screenshots if applicable

## License & Legal

### Educational Use
This project is designed for educational purposes and demonstrates:
- Java Swing application development
- Object-oriented programming principles
- Database design and management concepts
- User interface design and implementation
- Software engineering best practices

### Copyright
- **Author**: MD INZAMAMUL LOHANI
- **Purpose**: Educational demonstration project
- **Usage**: Free for educational and learning purposes

## Support & Contact

### Getting Help
- **Documentation**: Refer to this README and Project-Proposal.pdf
- **Code Comments**: Check inline code documentation
- **Community**: Share issues and suggestions for improvements

### Contact Information
- **Author**: MD INZAMAMUL LOHANI
- **Email**: hamimlohani@gmail.com
- **Project**: Student Management System

---

*This comprehensive Student Management System demonstrates advanced Java development concepts and provides a solid foundation for educational institution management. The modular architecture allows for easy extension and customization to meet specific institutional needs.* 