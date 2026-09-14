-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 11, 2026 at 08:55 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `patel`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `name`, `email`, `password`, `mobile`) VALUES
(3, 'patel dhruv', 'dhruv2007@gmail.com', '3333', '9988556677');

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `attendanceId` int(11) NOT NULL,
  `attendanceDate` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `studentName` varchar(100) DEFAULT NULL,
  `studentEmail` varchar(100) NOT NULL,
  `studentMobile` varchar(100) NOT NULL,
  `courseName` varchar(100) DEFAULT NULL,
  `teacherName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`attendanceId`, `attendanceDate`, `status`, `studentName`, `studentEmail`, `studentMobile`, `courseName`, `teacherName`) VALUES
(1, '2026-05-06', 'Present', 'dhyey patel', 'dhyey@gmail.com', '9876543210', 'Java II', 'Milan Patel'),
(2, '2026-08-01', 'Present', 'Yash Patel', 'yash@gmail.com', '9876543211', 'DBMS', ''),
(3, '2026-08-01', 'Present', 'Meet Shah', '', '', 'Data Structure', ''),
(4, '2026-08-02', 'Present', 'Raj Patel', '', '', 'Operating System', ''),
(5, '2026-08-02', 'Absent', 'Priya Shah', '', '', 'Java II', ''),
(6, '2026-08-02', '1', 'Rahul Mehta', '', '', 'Python Programming', ''),
(7, '2026-08-02', 'Present', 'Esha Patel', 'esha@gmail.com', '9876543220', 'c++', 'Vismay Shah');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `courseId` int(11) NOT NULL,
  `courseName` varchar(100) DEFAULT NULL,
  `duration` varchar(50) DEFAULT NULL,
  `fees` double DEFAULT NULL,
  `teacherId` int(11) DEFAULT NULL,
  `teacherName` varchar(500) DEFAULT NULL,
  `totalChapters` int(50) NOT NULL,
  `notes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`courseId`, `courseName`, `duration`, `fees`, `teacherId`, `teacherName`, `totalChapters`, `notes`) VALUES
(1, 'java I', '6 Months', 18000, 2, 'Milan Patel', 10, 'JDBC-I,JDBC-II,Collection Part-1,Collection Part-II'),
(2, 'DBMS', '4 Months', 12000, 4, 'Neha Patel', 10, 'ER Diagram, SQL, MySQL, Normalization'),
(3, 'Data Structure', '5 Months', 14000, 5, 'Rahul Verma', 10, 'Array, Linked List, Stack, Queue, Tree, Graph'),
(5, 'Java II', '6 Months', 18000, 2, 'Milan Patel', 10, 'Collections, JDBC, Multithreading, File Handling, Networking'),
(6, 'c++', '6 Months', 25000, 8, 'Vismay Shah', 10, 'Object-Oriented Programming\r\nEncapsulation\r\nInheritance\r\nPolymorphism\r\nAbstraction\r\nFunction overloading\r\nOperator overloading\r\nException handling\r\nTemplates\r\nDynamic memory allocation'),
(7, 'Web Development', '6 Months', 20000, 13, 'Amit Shah', 10, 'HTML, CSS, JavaScript and Web Development'),
(8, 'Python Programming', '6 Months', 18000, 14, 'Hetal Patel', 10, 'Python programming fundamentals and advanced concepts'),
(9, 'Artificial Intelligence', '8 Months', 30000, 15, 'Tarak Mehta', 12, 'Artificial Intelligence and Machine Learning concepts'),
(10, 'Cloud Computing', '6 Months', 25000, 16, 'Riya Desai', 10, 'Cloud Computing, AWS, Azure and cloud technologies');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(10) NOT NULL,
  `password` int(11) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `password`, `role`) VALUES
(1, 1111, 'student'),
(2, 2222, 'Teacher'),
(3, 3333, 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

CREATE TABLE `quiz` (
  `quizId` int(11) NOT NULL,
  `courseName` varchar(100) NOT NULL,
  `question` text DEFAULT NULL,
  `optionA` varchar(200) DEFAULT NULL,
  `optionB` varchar(200) DEFAULT NULL,
  `optionC` varchar(200) DEFAULT NULL,
  `optionD` varchar(200) DEFAULT NULL,
  `correctAnswer` varchar(10) DEFAULT NULL,
  `marks` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `quiz`
--

INSERT INTO `quiz` (`quizId`, `courseName`, `question`, `optionA`, `optionB`, `optionC`, `optionD`, `correctAnswer`, `marks`) VALUES
(1, 'java I', 'Who developed Java?', 'Microsoft', 'Sun Microsystems', 'Google', 'Apple', 'B', 2),
(2, 'DBMS', 'SQL stands for?', 'Structured Query Language', 'Simple Query Language', 'System Query Language', 'Standard Question Language', 'A', 2),
(3, 'Data Structure', 'Which data structure works on FIFO?', 'Stack', 'Queue', 'Tree', 'Graph', 'B', 2),
(5, 'Java II', 'Java source file extension is?', '.java', '.class', '.jar', '.exe', 'A', 2),
(9, 'c++', 'Which keyword is used to inherit a class in C++?', 'extends', 'implements', ':', 'inherits', 'C', 2),
(10, 'Web Development', 'Which language is mainly used to structure a web page?', 'Python', 'HTML', 'Java', 'C++', 'B', 2),
(11, 'Web Development', 'Which language is used to style web pages?', 'HTML', 'CSS', 'SQL', 'Python', 'B', 2),
(12, 'Web Development', 'Which language is commonly used to add interactivity to web pages?', 'JavaScript', 'HTML', 'SQL', 'C', 'A', 2),
(13, 'Web Development', 'What does HTML stand for?', 'Hyper Text Markup Language', 'High Text Machine Language', 'Hyperlink Text Management Language', 'Home Tool Markup Language', 'A', 2),
(14, 'Web Development', 'Which CSS property is used to change the text color?', 'font-size', 'background', 'color', 'text-style', 'C', 2),
(15, 'Python Programming', 'Which keyword is used to define a function in Python?', 'function', 'def', 'fun', 'define', 'B', 2),
(16, 'Python Programming', 'Which symbol is used for a single-line comment in Python?', '//', '#', '/*', '--', 'B', 2),
(17, 'Python Programming', 'Which of the following is a Python list?', '(1, 2, 3)', '[1, 2, 3]', '{1, 2, 3}', '<1, 2, 3>', 'B', 2),
(18, 'Python Programming', 'Which function is used to display output in Python?', 'display()', 'echo()', 'print()', 'show()', 'C', 2),
(19, 'Python Programming', 'Which keyword is used to create a class in Python?', 'object', 'class', 'struct', 'define', 'B', 2),
(20, 'Artificial Intelligence', 'What does AI stand for?', 'Automated Information', 'Artificial Intelligence', 'Advanced Internet', 'Artificial Integration', 'B', 2),
(21, 'Artificial Intelligence', 'Which field is a subset of Artificial Intelligence?', 'Machine Learning', 'Web Design', 'Database Management', 'Networking', 'A', 2),
(22, 'Artificial Intelligence', 'Which technique allows computers to learn from data?', 'Machine Learning', 'Web Development', 'File Handling', 'Operating System', 'A', 2),
(23, 'Artificial Intelligence', 'What is the main goal of Artificial Intelligence?', 'To make computers intelligent enough to perform tasks', 'To create websites only', 'To store files only', 'To design computer hardware', 'A', 2),
(24, 'Artificial Intelligence', 'Which of the following is an example of AI?', 'Voice Assistant', 'Calculator only', 'Text Editor', 'File Explorer', 'A', 2),
(25, 'Cloud Computing', 'What is Cloud Computing?', 'Using remote computing resources over a network', 'Only using a local computer', 'Creating desktop applications', 'Programming without a computer', 'A', 2),
(26, 'Cloud Computing', 'Which of the following is a cloud service provider?', 'AWS', 'HTML', 'Java', 'MySQL', 'A', 2),
(27, 'Cloud Computing', 'What does AWS stand for?', 'Amazon Web Services', 'Advanced Web System', 'Amazon Web Software', 'Application Web Services', 'A', 2),
(28, 'Cloud Computing', 'Which cloud service model provides virtual machines and storage?', 'IaaS', 'SaaS', 'PaaS', 'DBaaS', 'A', 2),
(29, 'Cloud Computing', 'Which cloud model is available for general public use?', 'Private Cloud', 'Public Cloud', 'Personal Cloud', 'Local Cloud', 'B', 2),
(30, 'java I', 'Which keyword is used to create a class in Java?', 'class', 'struct', 'object', 'define', 'A', 2),
(31, 'java I', 'Which method is the entry point of a Java program?', 'start()', 'main()', 'run()', 'execute()', 'B', 2),
(32, 'java I', 'Which data type is used to store whole numbers in Java?', 'float', 'double', 'int', 'char', 'C', 2),
(33, 'java I', 'Which symbol is used to end a statement in Java?', '.', ',', ':', ';', 'D', 2),
(34, 'java I', 'Which keyword is used to create an object in Java?', 'class', 'new', 'this', 'object', 'B', 2),
(35, 'Java II', 'Which package contains the ArrayList class?', 'java.io', 'java.util', 'java.sql', 'java.lang', 'B', 2),
(36, 'Java II', 'Which interface is used to create a thread in Java?', 'Runnable', 'Threadable', 'Executable', 'Run', 'A', 2),
(37, 'Java II', 'Which API is used to work with dates and times in modern Java?', 'Java Date-Time API', 'Java Calendar API', 'Java Time API only', 'Java Clock API', 'A', 2),
(38, 'Java II', 'Which technology is used to connect Java applications with databases?', 'JDBC', 'JDK', 'JVM', 'JRE', 'A', 2),
(39, 'Java II', 'Which Java feature allows handling runtime errors?', 'Inheritance', 'Exception Handling', 'Encapsulation', 'Polymorphism', 'B', 2),
(40, 'DBMS', 'What does DBMS stand for?', 'Database Management System', 'Data Backup Management System', 'Database Machine System', 'Data Management Software', 'A', 2),
(41, 'DBMS', 'Which SQL command is used to retrieve data?', 'INSERT', 'UPDATE', 'SELECT', 'DELETE', 'C', 2),
(42, 'DBMS', 'Which key uniquely identifies each record in a table?', 'Foreign Key', 'Primary Key', 'Candidate Key', 'Composite Key', 'B', 2),
(43, 'DBMS', 'Which SQL command is used to add a new record?', 'INSERT', 'ADD', 'CREATE', 'APPEND', 'A', 2),
(44, 'DBMS', 'Which SQL command is used to remove records from a table?', 'REMOVE', 'DROP', 'DELETE', 'CLEAR', 'C', 2),
(45, 'Data Structure', 'Which data structure follows FIFO?', 'Stack', 'Queue', 'Tree', 'Graph', 'B', 2),
(46, 'Data Structure', 'Which data structure follows LIFO?', 'Queue', 'Stack', 'Linked List', 'Tree', 'B', 2),
(47, 'Data Structure', 'Which data structure consists of nodes connected by links?', 'Array', 'Linked List', 'Stack', 'Queue', 'B', 2),
(48, 'Data Structure', 'Which data structure is commonly used for hierarchical data?', 'Array', 'Stack', 'Tree', 'Queue', 'C', 2),
(49, 'Data Structure', 'Which traversal visits the root node first in a binary tree?', 'Inorder', 'Postorder', 'Preorder', 'Levelorder', 'C', 2),
(50, 'c++', 'Which extension is commonly used for a C++ source file?', '.java', '.py', '.cpp', '.html', 'C', 2),
(51, 'c++', 'Which function is the entry point of a C++ program?', 'start()', 'main()', 'run()', 'begin()', 'B', 2),
(52, 'c++', 'Which operator is used with cout for output?', '>>', '<<', '==', '&&', 'B', 2),
(53, 'c++', 'Which keyword is used to define a class in C++?', 'object', 'class', 'structs', 'define', 'B', 2),
(54, 'c++', 'Which keyword is used for dynamic memory allocation in C++?', 'malloc', 'new', 'create', 'alloc', 'B', 2);

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
  `resultId` int(11) NOT NULL,
  `studentName` varchar(100) NOT NULL,
  `studentEmail` varchar(100) NOT NULL,
  `studentMobile` varchar(100) NOT NULL,
  `courseName` varchar(100) NOT NULL,
  `totalQuestions` int(11) DEFAULT NULL,
  `correctAnswers` int(11) DEFAULT NULL,
  `wrongAnswers` int(11) DEFAULT NULL,
  `marks` int(11) DEFAULT NULL,
  `totalMarks` int(11) DEFAULT NULL,
  `percentage` double DEFAULT NULL,
  `result` varchar(20) DEFAULT NULL,
  `resultDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `result`
--

INSERT INTO `result` (`resultId`, `studentName`, `studentEmail`, `studentMobile`, `courseName`, `totalQuestions`, `correctAnswers`, `wrongAnswers`, `marks`, `totalMarks`, `percentage`, `result`, `resultDate`) VALUES
(1, 'dhyey n patel', 'dhyey1@gmail.com', '9988776655', 'Java II', 5, 4, 1, 8, 10, 80, 'PASS', '2026-08-01'),
(2, 'dhyey Patel', 'dhyey@gmail.com', '9876543210', 'DBMS', 5, 5, 0, 10, 10, 100, 'PASS', '2026-08-01'),
(3, 'Meet Shah', 'meet@gmail.com', '9876543212', 'Data Structure', 5, 3, 2, 6, 10, 60, 'PASS', '2026-08-01'),
(4, 'Priya Patel', 'priya@gmail.com', '9876543213', 'Java I', 5, 2, 3, 4, 10, 40, 'PASS', '2026-08-01'),
(5, 'Yash Patel', 'yash@gmail.com', '9876543211', 'Data Structure', 5, 1, 4, 2, 10, 20, 'FAIL', '2026-08-01'),
(9, 'Esha Patel', 'esha@gmail.com', '9876543220', 'c++', 0, 0, 0, 2, 2, 100, 'PASS', '2026-08-11'),
(10, 'Esha Patel', 'esha@gmail.com', '9876543220', 'Java II', 0, 0, 0, 0, 2, 0, 'FAIL', '2026-08-11'),
(11, 'Esha Patel', 'esha@gmail.com', '9876543220', 'c++', 0, 0, 0, 2, 2, 100, 'PASS', '2026-08-11'),
(12, 'Esha Patel', 'esha@gmail.com', '9876543220', 'c++', 1, 1, 0, 2, 2, 100, 'PASS', '2026-08-11'),
(13, 'Esha Patel', 'esha@gmail.com', '9876543220', 'Java II', 1, 0, 1, 0, 2, 0, 'FAIL', '2026-08-11'),
(14, 'Esha Patel', 'esha@gmail.com', '9876543220', 'Data Structure', 1, 1, 0, 2, 2, 100, 'PASS', '2026-08-11'),
(15, 'Harsh Patel', 'harsh@gmail.com', '9876543219', 'c++', 1, 1, 0, 2, 2, 100, 'PASS', '2026-08-11'),
(16, 'Harsh Patel', 'harsh@gmail.com', '9876543219', 'Java II', 1, 0, 1, 0, 2, 0, 'FAIL', '2026-08-11'),
(17, 'Harsh Patel', 'harsh@gmail.com', '9876543219', 'Data Structure', 1, 1, 0, 2, 2, 100, 'PASS', '2026-08-11'),
(18, 'Harsh Patel', 'harsh@gmail.com', '9876543219', 'java I', 1, 0, 1, 0, 2, 0, 'FAIL', '2026-08-11');

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

CREATE TABLE `signup` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `studentId` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `course` varchar(100) DEFAULT NULL,
  `courseFees` double NOT NULL,
  `paymentStatus` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentId`, `name`, `email`, `password`, `mobile`, `course`, `courseFees`, `paymentStatus`) VALUES
(1, 'dhyey n patel', 'dhyey1@gmail.com', '1111', '9988776655', 'DBMS', 12000, 'PAID'),
(2, 'Dhyey Patel', 'dhyey@gmail.com', '4321', '9876543210', 'Java I', 18000, 'PAID'),
(3, 'Yash Patel', 'yash@gmail.com', '1112', '9876543211', 'Data Structures', 14000, 'PAID'),
(4, 'Meet Shah', 'meet@gmail.com', '2222', '9876543212', 'DBMS', 12000, 'Pending'),
(5, 'Priya Patel', 'priya@gmail.com', '3333', '9876543213', 'Web Development', 20000, 'PAID'),
(6, 'Rahul Mehta', 'rahul@gmail.com', '4444', '9876543214', 'Python Programming', 18000, 'Pending'),
(7, 'Krishna Patel', 'krishna@gmail.com', '5555', '9876543215', 'Java I', 18000, 'PAID'),
(8, 'Neha Shah', 'neha@gmail.com', '6666', '9876543216', 'Artificial Intelligence', 300000, 'PAID'),
(9, 'Aarav Desai', 'aarav@gmail.com', '7777', '9876543217', 'c++', 25000, 'Pending'),
(11, 'Harsh Patel', 'harsh@gmail.com', '9999', '9876543219', 'Cloud Computing', 25000, 'PAID'),
(12, 'Esha Patel', 'esha@gmail.com', '9876', '9876543220', 'Java II', 18000, 'PAID'),
(13, 'Nimisha Patel', 'nimisha.patel@yahoo.com', '8855', '9876543221', 'c++', 25000, 'Pending'),
(14, 'Jhanvi Patel', 'jhanvi@outlook.com', '4567', '9876543222', 'Python Programming', 18000, 'Pending'),
(15, 'Yashvi Patel', 'yashvi@zmail.com', '4521', '9876543223', 'Web Development', 20000, 'Paid');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `teacherId` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `salary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`teacherId`, `name`, `email`, `password`, `mobile`, `subject`, `salary`) VALUES
(2, 'Milan Patel', 'Milan@gmail.com', '2222', '9988556677', 'Java II', 50000),
(3, 'Amit Sharma', 'amit@gmail.com', '1222', '9876543210', 'Java', 25000),
(4, 'Neha Patel', 'neha@gmail.com', '1234', '9876543211', 'DBMS', 40000),
(5, 'Rahul Mehta', 'rahul@gmail.com', '1211', '9876543212', 'Data Structure', 45000),
(6, 'Priya Shah', 'priya@gmail.com', '1311', '9876543213', 'Operating System', 43000),
(8, 'Vismay Shah', 'vismay@yahoo.com', '3333', '9632587410', 'C++', 45000),
(13, 'Amit Shah', 'amit.shah@gmail.com', '1111', '9876543214', 'Web Development', 40000),
(14, 'Hetal Patel', 'hetal.patel@gmail.com', '1243', '9876543215', 'Python Programming', 50000),
(15, 'Tarak Mehta', 'tarak.mehta@gmail.com', '1212', '9876543216', 'Artificial Intelligence', 25000),
(16, 'Riya Desai', 'riya.desai@gmail.com', '1212', '9876543217', 'Cloud Computing', 26000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`attendanceId`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`courseId`),
  ADD KEY `teacherId` (`teacherId`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`quizId`);

--
-- Indexes for table `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`resultId`);

--
-- Indexes for table `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentId`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `password` (`password`),
  ADD UNIQUE KEY `mobile` (`mobile`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`teacherId`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `attendanceId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `courseId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `quizId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `result`
--
ALTER TABLE `result`
  MODIFY `resultId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `signup`
--
ALTER TABLE `signup`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `studentId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `teacherId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
