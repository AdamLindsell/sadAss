import java.sql.*;
import java.util.*;

/*
 * A program for interacting with a school database with multiple functionalities
 */
/**
 * @author adaml
 */
public class sadAss {
    
    /**
     * Adds a student and all their relevant credentials into the Student table
     */
    public static void addStudent() {
        String fname, lname, group;
        String DOB;
        Scanner s = new Scanner(System.in);

        System.out.println("What is the student's first name?"); 
        fname = s.nextLine();

        System.out.println("What is the student's last name?");
        lname = s.nextLine();

        System.out.println("What is the students date of birth? \n*please enter DOB in the format YYYY-MM-DD*");
        DOB = s.nextLine();

        System.out.println("What group is the student in?");      
        group = s.nextLine();
        s.nextLine();

        try {                       //Statement to enter the student
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("INSERT INTO Student (fName, lName, DOB, \"Group\") VALUES ('" + fname + "', '" + lname + "', '"
                    + DOB + "', '" + group + "')");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Student " + fname + " " + lname + " has been added to the database");
    }

    /**
     * Changes the group column of a specified student in the students table
     */
    public static void updateGroup() {
        Scanner s = new Scanner(System.in);
        String fname, lname, group;

        System.out.println("What is the first name of the student you want to change?");
        fname = s.nextLine();

        System.out.println("What is the surname of the student you want to change?");
        lname = s.nextLine();

        System.out.println("What group do you want to change them to?");
        group = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("UPDATE Student SET \"Group\" = '" + group + "' WHERE fName = '" + fname + "' AND lName = '" + lname + "';");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Searches for and returns all of the credentials of a student matching the search criteria
     */
    public static void searchStudent() {
        Scanner s = new Scanner(System.in);
        int ans = 0;
        while (ans != 6) {
            System.out.println("How would you like to search for a student?"        //Gives the user a choice oni how they wish to search for the student
                    + "\n(1) First name\n(2) Last Name\n(3) Age\n(4) Group"
                    + "\n(5) Multiple criteria or \n(6) Exit");
            ans = s.nextInt();

            switch (ans) {
                case 1:
                try {
                    System.out.println("What is the student's first name?");
                    s.nextLine();
                    String fname = s.nextLine();
                    ResultSet rs = null;
                    Boolean empty = true;

                    int id, DOB;
                    String fName, lname, group;

                    Connection conn = null;
                    conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
                    Statement stat = conn.createStatement();
                    rs = stat.executeQuery("SELECT * FROM Student WHERE fName = '" + fname + "';");
                   //Displays the information of any student who matches the criteria
                    while (rs.next()) {
                        empty = false;
                        id = rs.getInt(1);
                        fName = rs.getString("fName");      
                        lname = rs.getString("lName");
                        DOB = rs.getInt(4);
                        group = rs.getString("Group");

                        System.out.println("StudentID: " + id + "\tFirst Name: " + fName
                                + "\tLast Name: " + lname + "\tDOB: " + DOB + "\tGroup: " + group);
                        calculateParts(fname, lname);

                    }
                    if (empty == true){
                        System.out.println("No student with matching credentials exist");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                break;

                case 2:
                try {
                    System.out.println("What is the student's last name?");
                    s.nextLine();
                    String lname = s.nextLine();
                    ResultSet rs = null;
                    Boolean empty = true;

                    int id, DOB;
                    String fName, lName, group;

                    Connection conn = null;
                    conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
                    Statement stat = conn.createStatement();
                    rs = stat.executeQuery("SELECT * FROM Student WHERE lName = '" + lname + "';");
                    //Displays the information of any student who matches the criteria
                    while (rs.next()) {
                        id = rs.getInt(1);
                        fName = rs.getString("fName");
                        lName = rs.getString("lName");
                        DOB = rs.getInt(4);
                        group = rs.getString("Group");

                        System.out.println("StudentID: " + id + "\tFirst Name: " + fName
                                + "\tLast Name: " + lName + "\tDOB: " + DOB + "\tGroup: " + group);
                        calculateParts(fName, lName);
                        empty = false;
                    }
                    if (empty == true){
                        System.out.println("No student with matching credentials exist");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

                case 3:
                    try {
                    System.out.println("How old is the student (in years)?");
                    int age = s.nextInt();
                    ResultSet rs = null;
                    int id, DOB;
                    String fName, lName, group;
                    Boolean empty = true;

                    Connection conn = null;
                    conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
                    Statement stat = conn.createStatement();
                    rs = stat.executeQuery("SELECT * FROM Student " //calculates the age of the student as an int
                            + "WHERE cast (strftime('%Y.%m%d', 'now') "
                            + "- strftime('%Y.%m%d', dob) as int) = " + age + ";");
                    //Displays the information of any student who matches the criteria
                    while (rs.next()) {
                        id = rs.getInt(1);
                        fName = rs.getString("fName");
                        lName = rs.getString("lName");
                        DOB = rs.getInt(4);
                        group = rs.getString("Group");

                        System.out.println("StudentID: " + id + "\tFirst Name: " + fName
                                + "\tLast Name: " + lName + "\tDOB: " + DOB + "\tGroup: " + group);
                        calculateParts(fName, lName);
                            empty = false;
                    }
                    if (empty == true){
                        System.out.println("No student with matching credentials exist");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

                case 4:
                try {
                    System.out.println("What is the student's group?");
                    s.nextLine();
                    String group = s.nextLine();
                    ResultSet rs = null;
                    Boolean empty = true;

                    int id, DOB;
                    String fName, lname, Group;

                    Connection conn = null;
                    conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
                    Statement stat = conn.createStatement();
                    rs = stat.executeQuery("SELECT * FROM Student WHERE \"Group\" = '" + group + "';");
                    //Displays the information of any student who matches the criteria
                    while (rs.next()) {
                        id = rs.getInt(1);
                        fName = rs.getString("fName");
                        lname = rs.getString("lName");
                        DOB = rs.getInt(4);
                        Group = rs.getString("Group");

                        System.out.println("StudentID: " + id + "\tFirst Name: " + fName
                                + "\tLast Name: " + lname + "\tDOB: " + DOB + "\tGroup: " + Group);
                        calculateParts(fName, lname);
                            empty = false;
                    }
                    if (empty == true){
                        System.out.println("No student with matching credentials exist");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                
                case 5:
                    System.out.println("Would you like to search for \n(1) First name and last name"
                            + "\n(2) First name and group or\n(3) Quit");
                    int ans2 = s.nextInt();
                    switch (ans2) {
                        case 1:
                            try {
                            String first, last;
                            ResultSet rs = null;
                            int id, DOB;
                            String fName, lname, group;
                            Boolean empty = true;

                            System.out.println("What is the student's first name?");
                            s.nextLine();
                            first = s.nextLine();

                            System.out.println("What is the student's last name?");
                            last = s.nextLine();
                            Connection conn = null;
                            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
                            Statement stat = conn.createStatement();
                            rs = stat.executeQuery("SELECT * FROM Student WHERE fName = '"
                                    + first + "' AND lNAme = '" + last + "';");
                           //Displays the information of any student who matches the criteria
                            while (rs.next()) {
                                id = rs.getInt(1);
                                fName = rs.getString("fName");
                                lname = rs.getString("lName");
                                DOB = rs.getInt(4);
                                group = rs.getString("Group");

                                System.out.println("StudentID: " + id + "\tFirst Name: " + fName
                                        + "\tLast Name: " + lname + "\tDOB: " + DOB + "\tGroup: " + group);
                                calculateParts(fName, lname);
                                empty = false;
                            }
                            if (empty == true){
                                System.out.println("No student with matching credentials exist");
                            }
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                        case 2:
                            try {
                            String first, Group;
                            ResultSet rs = null;
                            int id, DOB;
                            String fName, lname, group;
                            Boolean empty = true;

                            System.out.println("What is the student's first name?");
                            s.nextLine();
                            first = s.nextLine();

                            System.out.println("What is the student's group?");
                            Group = s.nextLine();
                            Connection conn = null;
                            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
                            Statement stat = conn.createStatement();
                            rs = stat.executeQuery("SELECT * FROM Student WHERE fName = '"
                                    + first + "' AND \"Group\" = '" + Group + "';");
                           //Displays the information of any student who matches the criteria
                            while (rs.next()) {
                                id = rs.getInt(1);
                                fName = rs.getString("fName");
                                lname = rs.getString("lName");
                                DOB = rs.getInt(4);
                                group = rs.getString("Group");

                                System.out.println("StudentID: " + id + "\tFirst Name: " + fName
                                        + "\tLast Name: " + lname + "\tDOB: " + DOB + "\tGroup: " + group);
                                calculateParts(fName, lname);
                                empty = false;
                            }
                            if (empty == true){
                                System.out.println("No student with matching credentials exist");
                            }
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    }
            }

        }

    }

    /**
     * Displays the parts a student has completed
     */
    public static void viewParts() {
        Scanner s = new Scanner(System.in);
        String first, last;
        ResultSet rs = null;
        String partDes;

        System.out.println("What is the student's first name?");
        first = s.nextLine();

        System.out.println("What is the student's last name?");
        last = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT PartDescription FROM Parts WHERE PartID IN \n" +
                "(SELECT PartID FROM Schedule WHERE WeekNum IN \n" +
                "(SELECT weekNum FROM StudentAttendance\n" +
                "INNER JOIN Student ON  StudentAttendance.StudentID = Student.StudentID \n" +
                "AND Student.fName = '" + first + "'\n" +
                "AND Student.lName = '" + last + "')); ");
            
            System.out.println("Parts learnt:");
            while (rs.next()){
                partDes = rs.getString(1);
                
                System.out.println(partDes);
            }           
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Displays the topics a student has completed
     */
    public static void viewCompletedTopics(){
        Scanner s = new Scanner(System.in);
        String first, last;
        ResultSet rs = null;
        String topic;

        System.out.println("What is the student's first name?");
        first = s.nextLine();

        System.out.println("What is the student's last name?");
        last = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT topicName FROM Topics " +
                    "INNER JOIN (SELECT TopicID, COUNT(*) AS numParts FROM Parts "
                    + "WHERE PartID IN (SELECT DISTINCT(PartID) FROM Schedule "
                    + "WHERE WeekNum IN (SELECT weekNum FROM StudentAttendance "
                    + "INNER JOIN Student ON  StudentAttendance.StudentID = Student.StudentID " 
                    + "AND Student.fName = '" + first + "' AND Student.lName = '" + last + "'))" 
                    + "GROUP BY TopicID) AS partCount ON Topics.TopicID = partCount.TopicID " 
                    + "AND Topics.NumParts = partCount.numParts;");
            
            System.out.println("Topics completed: ");
            while (rs.next()){
                topic = rs.getString(1);
                
                System.out.println(topic);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    
    }
    
    /**
     * Displays the tests a student has completed
     */
    public static void viewCompletedTests(){
        Scanner s = new Scanner(System.in);
        String first, last;
        ResultSet rs = null;
        String testDes;

        System.out.println("What is the student's first name?");
        first = s.nextLine();

        System.out.println("What is the student's last name?");
        last = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT TestDescription FROM Tests INNER JOIN\n" +
                "(SELECT TestID, COUNT(*) AS numTopics FROM Topics WHERE TopicID IN\n" +
                "(SELECT Topics.TopicID FROM Topics INNER JOIN \n" +
                "(SELECT TopicID, COUNT(*) AS numParts FROM Parts WHERE PartID IN \n" +
                "(SELECT DISTINCT(PartID) FROM Schedule WHERE WeekNum IN \n" +
                "(SELECT weekNum FROM StudentAttendance\n" +
                "INNER JOIN Student ON  StudentAttendance.StudentID = Student.StudentID AND Student.fName = '" + first + "' AND Student.lName = '" + last + "')) GROUP BY TopicID) \n" +
                "AS partCount ON Topics.TopicID = partCount.TopicID \n" +
                "AND Topics.NumParts = partCount.numParts) GROUP BY TestID)\n" +
                "AS topicCount ON Tests.TestID = topicCount.TestID AND Tests.numTopics = topicCount.numTopics;");
            
            System.out.println("Completed Tests: ");
            
            while (rs.next()){
                testDes = rs.getString(1);
                
                System.out.println(testDes);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * A UI for the previous three methods, to make the main UI less congested
     */
    public static void viewStuff() {
        Scanner s = new Scanner(System.in);
        int ans = 0;

        while (ans != 4) {
            System.out.println("Would you like to view: \n(1) A student's completed parts"
                    + "\n(2) A student's completed topics \n(3) A student's completed tests or"
                    + "\n(4) Quit");
            ans = s.nextInt();

            switch (ans) {
                case 1:
                    viewParts();
                    break;

                case 2:
                    viewCompletedTopics();
                    break;

                case 3:
                    viewCompletedTests();
                    break;
            }
        }
    }

    /**
     * Displays the badges a student has earned
     */
    public static void viewBadges(){
        Scanner s = new Scanner(System.in);
        String first, last;
        ResultSet rs = null;
        int badge;

        System.out.println("What is the student's first name?");
        first = s.nextLine();

        System.out.println("What is the student's last name?");
        last = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT Badges.BadgeID FROM Badges INNER JOIN\n" +
                "(SELECT BadgeID, COUNT(*) AS numTest FROM Tests WHERE TestID IN\n" +
                "(SELECT Tests.TestID FROM Tests INNER JOIN\n" +
                "(SELECT Tests.TestID, COUNT(*) AS numTopics FROM Topics WHERE TopicID IN\n" +
                "(SELECT Topics.TopicID FROM Topics INNER JOIN \n" +
                "(SELECT Topics.TopicID, COUNT(*) AS numParts FROM Parts WHERE PartID IN \n" +
                "(SELECT DISTINCT(PartID) FROM Schedule WHERE WeekNum IN \n" +
                "(SELECT weekNum FROM StudentAttendance\n" +
                "INNER JOIN Student ON  StudentAttendance.StudentID = Student.StudentID AND Student.fName = '" + first + "' AND Student.lName = '" + last + "')) GROUP BY TopicID) \n" +
                "AS partCount ON Topics.TopicID = partCount.TopicID \n" +
                "AND Topics.NumParts = partCount.numParts) GROUP BY TestID)\n" +
                "AS topicCount ON Tests.TestID = topicCount.TestID AND Tests.numTopics = topicCount.numTopics) GROUP BY BadgeID)\n" +
                "AS testCount ON Badges.BadgeID = testCount.BadgeID AND testCount.numTest = 14;");
            System.out.println("Badges acquired:");
            
            while (rs.next()){
                badge = rs.getInt(1);
                
                System.out.println(badge);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Deletes a students details from the Student table
     */
    public static void deleteStudent() {

        Scanner s = new Scanner(System.in);
        String fname, lname;

        System.out.println("What is the first name of the student you want to delete?");
        fname = s.nextLine();

        System.out.println("What is the surname of the student you want to delete?");
        lname = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();

            stat.executeUpdate("DELETE FROM Student WHERE fName = '" + fname + "' AND lName = '" + lname + "';");
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Student " + fname + " " + lname + " has been deleted from the database");

    }

    /**
     * Marks the attendance of a certain group in a week
     */
    public static void markAttendance() {
        Scanner s = new Scanner(System.in);
        int week;
        int i3 = 0;
        String group, absence;
        int i2 = 0;

        System.out.println("What week are you marking?");
        week = s.nextInt();

        System.out.println("What group are you teaching?");
        s.nextLine();
        group = s.nextLine();
        try {
            Connection conn = null;
            ResultSet rs = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT count(StudentID) FROM Student WHERE"
                    + "\"Group\" = '" + group + "';");
            rs.next();
            i3 = rs.getInt(1);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int[] ids = new int[i3];
        ArrayList<Integer> truants = new ArrayList<>();

        try {
            Connection conn = null;
            ResultSet rs = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT StudentID FROM Student WHERE \"Group\" = '"
                    + group + "';");
            while (rs.next()) {
                ids[i2] = rs.getInt(1);
                i2++;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Are there any absences?");
        absence = s.nextLine();
        if (absence.equalsIgnoreCase("Yes")) {
            System.out.println("What are the IDs of the students absent? Enter done when complete");
            try {
                for (int i = 0; i < ids.length; i++) {
                    truants.add(s.nextInt());
                }
            } catch (Exception e) {

            }
        }

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            for (int i = 0; i < ids.length; i++) {
                stat.executeUpdate("INSERT INTO StudentAttendance (StudentID, weekNum) VALUES"
                        + "(" + ids[i] + ", " + week + ");");
            }

            if (truants.size() != 0) {
                for (int i = 0; i < truants.size(); i++) {
                    stat.executeUpdate("DELETE FROM StudentAttendance WHERE weekNum = "
                            + week + " AND StudentID = " + truants.get(i) + ";");
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the schedule of the semester form a specified week onward
     */
    public static void displaySchedule() {
        Scanner s = new Scanner(System.in);
        System.out.println("What week do you wish to see the schedule from?");
        int ans = s.nextInt();
        int StaffID, WeekNum, PartID;
        String group;

        try {
            Connection conn = null;
            ResultSet rs = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * FROM Schedule WHERE WeekNum >= " + ans + " ORDER BY WeekNum ASC;");
            while (rs.next()) {
                StaffID = rs.getInt(2);
                WeekNum = rs.getInt(3);
                PartID = rs.getInt(4);
                group = rs.getString(5);

                System.out.println("Staff teaching this part: " + StaffID
                        + "\tWeek: " + WeekNum + "\tPart being taught: " + PartID 
                        + "\tGrouup being taught: " + group);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a staff members details to the Staff table
     */
    public static void addStaff() {
        Scanner s = new Scanner(System.in);
        String fname, lname;

        System.out.println("What is the staff members first name?");
        fname = s.nextLine();

        System.out.println("What is the staff members last name?");
        lname = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("INSERT INTO Staff (fName, lName) VALUES ('" + fname + "', '" + lname + "')");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Staff member " + fname + " " + lname + " has been added to the database");
    }

    /**
     * Assigns a staff member to teach a part and adds it to the schedule
     */
    public static void assignPart() {
        Scanner s = new Scanner(System.in);
        int part, staff, week;
        String group;

        System.out.println("What is the ID of the part to be taught");
        part = s.nextInt();

        System.out.println("What is the ID of the staff member who will teach it?");
        staff = s.nextInt();

        System.out.println("What week is it being taught in?");
        week = s.nextInt();

        System.out.println("What group are they teaching?");
        s.nextLine();
        group = s.nextLine();

        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("INSERT INTO Schedule (StaffID, WeekNum, PartID, \"Group\")"
                    + " VALUES (" + staff + ", " + week + ", " + part + ", '" + group + "')");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Part assigned to Staff member and added to schedule");
    }
    
    /**
     * Displays the parts a student has earned, given the students name as an input
     * @param first The first name of the student to search
     * @param last  The last name of the student to search
     */
    public static void calculateParts(String first, String last){
        ResultSet rs = null;
        int badge;
        
        try {
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:sadface.db");
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("SELECT Badges.BadgeID FROM Badges INNER JOIN\n" +
                "(SELECT BadgeID, COUNT(*) AS numTest FROM Tests WHERE TestID IN\n" +
                "(SELECT Tests.TestID FROM Tests INNER JOIN\n" +
                "(SELECT Tests.TestID, COUNT(*) AS numTopics FROM Topics WHERE TopicID IN\n" +
                "(SELECT Topics.TopicID FROM Topics INNER JOIN \n" +
                "(SELECT Topics.TopicID, COUNT(*) AS numParts FROM Parts WHERE PartID IN \n" +
                "(SELECT DISTINCT(PartID) FROM Schedule WHERE WeekNum IN \n" +
                "(SELECT weekNum FROM StudentAttendance\n" +
                "INNER JOIN Student ON  StudentAttendance.StudentID = Student.StudentID AND Student.fName = '" + first + "' AND Student.lName = '" + last + "')) GROUP BY TopicID) \n" +
                "AS partCount ON Topics.TopicID = partCount.TopicID \n" +
                "AND Topics.NumParts = partCount.numParts) GROUP BY TestID)\n" +
                "AS topicCount ON Tests.TestID = topicCount.TestID AND Tests.numTopics = topicCount.numTopics) GROUP BY BadgeID)\n" +
                "AS testCount ON Badges.BadgeID = testCount.BadgeID AND testCount.numTest = 14;");
            System.out.println("Badges acquired:");
            
            while (rs.next()){
                badge = rs.getInt(1);
                System.out.println(badge);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method, which acts as an UI for the rest of the methods
     * @param args 
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int ans = 0;
        
        System.out.println("Hello and welcome to the Student database!!");
        while (ans != 11) {
            System.out.println("Would you like to: \n(1) Add a student\n(2) Update a students group"
                    + "\n(3) Search for a student\n(4) Delete a student\n(5) Mark Attendance"
                    + "\n(6) Display the trimesters schedule\n(7) Add a staff member"
                    + "\n(8) Assign a staff member to a part \n(9) Show a student's completed parts/topics/tests"
                    + "\n(10) View a student's badges or\n(11) Quit");
            ans = s.nextInt();

            switch (ans) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    updateGroup();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    markAttendance();
                    break;

                case 6:
                    displaySchedule();
                    break;

                case 7:
                    addStaff();
                    break;

                case 8:
                    assignPart();
                    break;
                    
                case 9:
                    viewStuff();
                    break;
                    
                case 10:
                    viewBadges();
                    break;                    
                    
            }
        }
    }
}
