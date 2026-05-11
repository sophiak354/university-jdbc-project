package com.solvd.university;

import com.solvd.university.dao.*;
import com.solvd.university.dao.impl.InstructorDaoImpl;
import com.solvd.university.dao.impl.jdbc.CourseDaoImpl;
import com.solvd.university.dao.impl.jdbc.StudentDaoImpl;
import com.solvd.university.dao.impl.mybatis.CourseOfferingMyBatisDaoImpl;
import com.solvd.university.dao.impl.mybatis.EnrollmentMyBatisDaoImpl;
import com.solvd.university.dao.impl.mybatis.SemesterMyBatisDaoImpl;
import com.solvd.university.model.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UniversityDbTest {
    private StudentDao studentDao;
    private CourseDao courseDao;
    private InstructorDao instructorDao;
    private SemesterDao semesterDao;
    private CourseOfferingDao courseOfferingDao;
    private EnrollmentDao enrollmentDao;

    private Integer createdSemesterId;
    private Integer createdInstructorId;

    @BeforeMethod
    public void setUp() {
        studentDao = new StudentDaoImpl();
        courseDao = new CourseDaoImpl();
        instructorDao = new InstructorDaoImpl();
        semesterDao = new SemesterMyBatisDaoImpl();
        courseOfferingDao = new CourseOfferingMyBatisDaoImpl();
        enrollmentDao = new EnrollmentMyBatisDaoImpl();

        createdSemesterId = null;
        createdInstructorId = null;
    }

    @AfterMethod
    public void tearDown() {
        if (createdInstructorId != null) {
            instructorDao.deleteById(createdInstructorId);
        }
        if (createdSemesterId != null) {
            semesterDao.deleteById(createdSemesterId);
        }
    }

    @Test
    public void shouldCreateStudentAndFindByEmail() {
        LocalDate dateOfBirth = LocalDate.of(2000, 5, 10);
        LocalDate admissionDate = LocalDate.now();
        long timestamp = System.currentTimeMillis();
        String email = "john" + timestamp + "@test.com";
        String phone = "+38099" + (timestamp % 10000000);

        Student student = new Student();

        student.setFirstName("John");
        student.setLastName("Doe");
        student.setDateOfBirth(dateOfBirth);
        student.setGender("Male");
        student.setEmail(email);
        student.setPhone(phone);
        student.setAdmissionDate(admissionDate);
        student.setStudentStatus("Active");
        student.setProgramId(1);

        studentDao.save(student);

        Optional<Student> savedStudent = studentDao.findAll()
                .stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst();

        Assert.assertTrue(savedStudent.isPresent());
        Student actualStudent = savedStudent.get();
        Assert.assertEquals(actualStudent.getFirstName(), "John");
        Assert.assertEquals(actualStudent.getLastName(), "Doe");
        Assert.assertEquals(actualStudent.getDateOfBirth(), dateOfBirth);
        Assert.assertEquals(actualStudent.getGender(), "Male");
        Assert.assertEquals(actualStudent.getEmail(), email);
        Assert.assertEquals(actualStudent.getPhone(), phone);
        Assert.assertEquals(actualStudent.getAdmissionDate(), admissionDate);
        Assert.assertEquals(actualStudent.getStudentStatus(), "Active");
        Assert.assertEquals(actualStudent.getProgramId(), 1);
    }

    @Test
    public void shouldFindExistingCourseById() {
        Optional<Course> savedCourse = courseDao.findById(1);

        Assert.assertTrue(savedCourse.isPresent());

        Course actualCourse = savedCourse.get();

        Assert.assertEquals(actualCourse.getCourseId(), 1);
        Assert.assertNotNull(actualCourse.getCourseCode());
        Assert.assertNotNull(actualCourse.getCourseName());
        Assert.assertTrue(actualCourse.getCredits() > 0);
        Assert.assertTrue(actualCourse.getDepartmentId() > 0);
    }

    @Test
    public void shouldCreateInstructorAndFindById() {
        long timestamp = System.currentTimeMillis();

        int instructorId = (int) (timestamp % 1_000_000);
        String email = "instructor" + timestamp + "@test.com";
        String phone = "+38067" + (timestamp % 10_000_000);

        LocalDate hireDate = LocalDate.of(2020, 9, 1);

        Instructor instructor = new Instructor();

        instructor.setInstructorId(instructorId);
        instructor.setFirstName("Alice");
        instructor.setLastName("Brown");
        instructor.setEmail(email);
        instructor.setPhone(phone);
        instructor.setHireDate(hireDate);
        instructor.setAcademicTitle("Professor");
        instructor.setDepartmentId(1);

        instructorDao.save(instructor);
        createdInstructorId = instructorId;

        Optional<Instructor> savedInstructor = instructorDao.findById(instructorId);

        Assert.assertTrue(savedInstructor.isPresent());

        Instructor actualInstructor = savedInstructor.get();

        Assert.assertEquals(actualInstructor.getInstructorId(), instructorId);
        Assert.assertEquals(actualInstructor.getFirstName(), "Alice");
        Assert.assertEquals(actualInstructor.getLastName(), "Brown");
        Assert.assertEquals(actualInstructor.getEmail(), email);
        Assert.assertEquals(actualInstructor.getPhone(), phone);
        Assert.assertEquals(actualInstructor.getHireDate(), hireDate);
        Assert.assertEquals(actualInstructor.getAcademicTitle(), "Professor");
        Assert.assertEquals(actualInstructor.getDepartmentId(), 1);
    }

    @Test
    public void shouldCreateSemesterAndFindAllSemesters() {
        long timestamp = System.currentTimeMillis();

        String semesterName = "Test Semester " + timestamp;
        LocalDate startDate = LocalDate.of(2026, 9, 1);
        LocalDate endDate = LocalDate.of(2027, 1, 31);
        String academicYear = "2026/2027";

        Semester semester = new Semester();

        semester.setSemesterName(semesterName);
        semester.setStartDate(startDate);
        semester.setEndDate(endDate);
        semester.setAcademicYear(academicYear);

        semesterDao.save(semester);

        Optional<Semester> savedSemester = semesterDao.findAll()
                .stream()
                .filter(s -> s.getSemesterName().equals(semesterName))
                .findFirst();

        Assert.assertTrue(savedSemester.isPresent());

        Semester actualSemester = savedSemester.get();
        createdSemesterId = actualSemester.getSemesterId();

        Assert.assertEquals(actualSemester.getSemesterName(), semesterName);
        Assert.assertEquals(actualSemester.getStartDate(), startDate);
        Assert.assertEquals(actualSemester.getEndDate(), endDate);
        Assert.assertEquals(actualSemester.getAcademicYear(), academicYear);
    }

    @Test
    public void shouldFindExistingCourseOfferingWithCourseInstructorAndSemester() {
        Optional<CourseOffering> savedOffering = courseOfferingDao.findById(1);

        Assert.assertTrue(savedOffering.isPresent());

        CourseOffering actualOffering = savedOffering.get();

        Assert.assertEquals(actualOffering.getOfferingId(), 1);
        Assert.assertNotNull(actualOffering.getSectionNumber());
        Assert.assertTrue(actualOffering.getMaxStudents() > 0);
        Assert.assertNotNull(actualOffering.getDeliveryMode());

        Assert.assertTrue(actualOffering.getCourseId() > 0);
        Assert.assertTrue(actualOffering.getSemesterId() > 0);
        Assert.assertTrue(actualOffering.getInstructorId() > 0);

        Assert.assertTrue(courseDao.findById(actualOffering.getCourseId()).isPresent());
        Assert.assertTrue(semesterDao.findById(actualOffering.getSemesterId()).isPresent());
        Assert.assertTrue(instructorDao.findById(actualOffering.getInstructorId()).isPresent());
    }

    @Test
    public void shouldEnrollStudentToCourseOffering() {
        int studentId = 3;
        int offeringId = 1;

        Assert.assertTrue(studentDao.findById(studentId).isPresent());
        Assert.assertTrue(courseOfferingDao.findById(offeringId).isPresent());

        boolean alreadyExists = enrollmentDao.existsByStudentIdAndOfferingId(studentId, offeringId);

        if (!alreadyExists) {
            Enrollment enrollment = new Enrollment();
            enrollment.setEnrollmentDate(LocalDate.now());
            enrollment.setEnrollmentStatus("Active");
            enrollment.setStudentId(studentId);
            enrollment.setOfferingId(offeringId);

            enrollmentDao.save(enrollment);
        }

        Assert.assertTrue(enrollmentDao.existsByStudentIdAndOfferingId(studentId, offeringId));
    }

    @Test
    public void shouldNotEnrollSameStudentTwice() {
        int studentId = 3;
        int offeringId = 1;

        Assert.assertTrue(studentDao.findById(studentId).isPresent());
        Assert.assertTrue(courseOfferingDao.findById(offeringId).isPresent());

        boolean alreadyExists =
                enrollmentDao.existsByStudentIdAndOfferingId(studentId, offeringId);

        if (!alreadyExists) {
            Enrollment enrollment = new Enrollment();

            enrollment.setEnrollmentDate(LocalDate.now());
            enrollment.setEnrollmentStatus("Active");
            enrollment.setStudentId(studentId);
            enrollment.setOfferingId(offeringId);

            enrollmentDao.save(enrollment);
        }

        boolean secondAttempt =
                enrollmentDao.existsByStudentIdAndOfferingId(studentId, offeringId);

        Assert.assertTrue(secondAttempt);
    }

    @Test
    public void shouldDeleteStudentAndVerifyItDoesNotExist() {
        LocalDate dateOfBirth = LocalDate.of(2000, 5, 10);
        LocalDate admissionDate = LocalDate.now();

        long timestamp = System.currentTimeMillis();

        String email = "delete.student" + timestamp + "@test.com";
        String phone = "+38099" + (timestamp % 10_000_000);

        Student student = new Student();

        student.setFirstName("Delete");
        student.setLastName("Student");
        student.setDateOfBirth(dateOfBirth);
        student.setGender("Male");
        student.setEmail(email);
        student.setPhone(phone);
        student.setAdmissionDate(admissionDate);
        student.setStudentStatus("Active");
        student.setProgramId(1);

        studentDao.save(student);

        Optional<Student> savedStudent = studentDao.findAll()
                .stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst();

        Assert.assertTrue(savedStudent.isPresent());

        int studentId = savedStudent.get().getStudentId();

        studentDao.deleteById(studentId);

        Optional<Student> deletedStudent = studentDao.findById(studentId);

        Assert.assertTrue(deletedStudent.isEmpty());
    }

    @Test
    public void shouldUpdateInstructor() {
        long timestamp = System.currentTimeMillis();

        int instructorId = (int) (timestamp % 1_000_000);
        String email = "update.instructor" + timestamp + "@test.com";
        String phone = "+38067" + (timestamp % 10_000_000);

        Instructor instructor = new Instructor();
        instructor.setInstructorId(instructorId);
        instructor.setFirstName("Before");
        instructor.setLastName("Update");
        instructor.setEmail(email);
        instructor.setPhone(phone);
        instructor.setHireDate(LocalDate.of(2020, 9, 1));
        instructor.setAcademicTitle("Assistant");
        instructor.setDepartmentId(1);

        instructorDao.save(instructor);
        createdInstructorId = instructorId;

        instructor.setFirstName("After");
        instructor.setAcademicTitle("Professor");

        instructorDao.update(instructor);

        Optional<Instructor> updatedInstructor = instructorDao.findById(instructorId);

        Assert.assertTrue(updatedInstructor.isPresent());
        Assert.assertEquals(updatedInstructor.get().getFirstName(), "After");
        Assert.assertEquals(updatedInstructor.get().getAcademicTitle(), "Professor");
    }

    @Test
    public void shouldUpdateSemester() {
        long timestamp = System.currentTimeMillis();

        Semester semester = new Semester();
        semester.setSemesterName("Update Semester " + timestamp);
        semester.setStartDate(LocalDate.of(2026, 9, 1));
        semester.setEndDate(LocalDate.of(2027, 1, 31));
        semester.setAcademicYear("2026/2027");

        semesterDao.save(semester);

        Optional<Semester> savedSemester = semesterDao.findAll()
                .stream()
                .filter(s -> s.getSemesterName().equals("Update Semester " + timestamp))
                .findFirst();

        Assert.assertTrue(savedSemester.isPresent());

        Semester actualSemester = savedSemester.get();
        createdSemesterId = actualSemester.getSemesterId();

        actualSemester.setSemesterName("Updated Semester " + timestamp);
        actualSemester.setAcademicYear("2027/2028");

        semesterDao.update(actualSemester);

        Optional<Semester> updatedSemester =
                semesterDao.findById(createdSemesterId);

        Assert.assertTrue(updatedSemester.isPresent());
        Assert.assertEquals(updatedSemester.get().getSemesterName(), "Updated Semester " + timestamp);
        Assert.assertEquals(updatedSemester.get().getAcademicYear(), "2027/2028");
    }
}
