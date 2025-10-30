<<<<<<< HEAD
🎓 Student Attendance and Performance Tracker
🧭 Objective

The Student Attendance and Performance Tracker is a web-based platform designed to streamline the management of student attendance, academic performance, and faculty feedback.
It ensures transparency, accuracy, and data-driven insights for both faculty and students through a centralized system.

🧩 Core Features / Use Cases
🔐 1. Login & Authentication

Secure login for students, faculty, and admins.

Role-based access control ensures users access only relevant modules.

🗓️ 2. Attendance Management

Faculty can mark daily attendance for students directly from the system.

Records attendance history by date and student ID.

Enables updating, deleting, and viewing attendance records.

Attendance reports can be generated for review.

📊 3. Performance Tracking

Faculty can record and update exam scores and grades for students.

Supports bulk entry with shared subject and exam fields.

Auto-calculates grades (A+, A, B+, B, C, F).

Allows viewing and editing student marks.

(Upcoming) Export mark lists to Excel for administrative use.

💬 4. Feedback System

Faculty can provide qualitative feedback and performance remarks for each student.

Stores comments in the database for review by admin or faculty.

📈 5. Report Generation

Generates detailed reports combining attendance, grades, and feedback.

Future enhancement: graphical visualizations using charts for performance trends.

🧠 System Modules
Module	Description
User Management	Manages users (Admin, Faculty, Student) and authentication.
Attendance Module	Handles student attendance entry, viewing, and modification.
Performance Module	Manages grades, marks, and performance analysis.
Feedback Module	Allows faculty to submit qualitative assessments.
Report Module	Integrates data from all modules for student progress insights.
⚙️ Expected Design Components
🧩 UML Diagrams

Use Case Diagram – Depicts the interaction between Admin, Faculty, and Student.

Activity Diagram (Attendance & Performance) – Represents workflow of marking attendance and entering grades.

🧬 ER Diagram

Models relationships between Student, Faculty, Attendance, Performance, and Feedback entities.

🗄️ Database Design

Tables:

users – Stores user details (ID, username, password, role).

attendance – Stores daily attendance with date and student reference.

performance – Contains exam name, subject, marks, grade, and linked student.

feedback – Stores qualitative remarks and evaluation comments.

report – (optional) aggregates attendance and performance data for summary.

🧰 Technical Stack
Layer	Technology
Frontend	HTML5, CSS3, Thymeleaf
Backend	Java, Spring Boot, Spring MVC
Database	MySQL
ORM	Spring Data JPA
Security	Spring Security (Role-based Authentication)
Build Tool	Maven
IDE	Visual Studio Code / IntelliJ IDEA
Version Control	Git & GitHub
🧱 Database Schema
🧑‍🎓 users
Field	Type	Description
id	BIGINT	Primary Key
username	VARCHAR(255)	User name
password	VARCHAR(255)	Encrypted password
role	VARCHAR(50)	(Admin / Faculty / Student)
🗓️ attendance
Field	Type	Description
id	BIGINT	Primary Key
student_id	BIGINT	Foreign key (User)
date	DATE	Date of attendance
present	VARCHAR(10)	Present / Absent
notes	VARCHAR(255)	Optional
📊 performance
Field	Type	Description
id	BIGINT	Primary Key
student_id	BIGINT	Foreign key (User)
subject	VARCHAR(100)	Subject name
exam	VARCHAR(100)	Exam name
marks	INT	Marks obtained
grade	VARCHAR(10)	Grade (A+, A, B+, B, C, F)
💬 feedback
Field	Type	Description
id	BIGINT	Primary Key
student_id	BIGINT	Foreign key (User)
feedback_text	VARCHAR(255)	Feedback content
date	DATE	Feedback date
🔗 RESTful API Endpoints
Endpoint	Method	Description
/attendance	GET	View attendance list
/attendance/add	POST	Add new attendance record
/attendance/delete/{id}	POST	Delete attendance entry
/performance	GET	View performance list
/performance/saveAll	POST	Save all marks for a test
/performance/delete/{id}	POST	Delete performance record
/feedback	GET/POST	Manage feedback entries
🧮 Role-Based Access Control
Role	Permissions
Admin	Full control over all modules
Faculty	Add/edit attendance, performance, feedback
Student	View attendance and grades
🧾 How to Run

Clone Repository

git clone https://github.com/your-username/Student-Attendance-Performance-Tracker.git


Configure Database

spring.datasource.url=jdbc:mysql://localhost:3306/student_tracker
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


Run the Application

mvn spring-boot:run


Access in Browser

http://localhost:8080

📈 Future Enhancements

📤 Export reports to Excel or PDF

📊 Add visual dashboards using charts (attendance % and marks trend)

🧾 Monthly attendance summary per student

🧑‍🏫 Faculty feedback analytics

📱 Responsive UI for mobile access

👨‍💻 Developer

Developed by: MIDHULA V
Course: B.Tech in Artificial Intelligence and Data Science
Year: 2025
Project Title: Student Attendance and Performance Tracker
=======
# Student_Performance_Tracker
Semester End Project - Java
>>>>>>> 849682de14a87b6cb8b15f363ffdae60a537e752
