package ssvv.tests;

import Exceptions.ValidatorException;
import Repository.Repo;
import Repository.TxtFileRepository.StudentFileRepo;
import Service.Service;
import Service.TxtFileService.StudentService;
import Validator.StudentValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;

public class AppClass {
    private Repo studentRepo;
    private Service studentService;

    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(AppClass.class);
        for (Failure failure : result.getFailures())
        {System.out.println(failure.toString());}
        System.out.println(result.wasSuccessful());
    }

    @Before
    public void setup()
    {
        StudentValidator vs=new StudentValidator();
        studentRepo = null;
        try {
            studentRepo = new StudentFileRepo("studenti.txt", vs);
        } catch (IOException e) {
            e.printStackTrace();
        }
         studentService=new StudentService((StudentFileRepo) studentRepo);
    }

    @Test
    public void testCase1AddStudent(){
        int sizeInitial=studentService.getSize();

        //input data
        String idStudent="1234";
        String nameStudent="lala";
        String group="100";
        String email="lala@";
        String teacher="lalaMare";

        String[] parameters=new String[]{idStudent,nameStudent,group,email,teacher};

        try {
            studentService.add(parameters);
            assert studentService.getSize() == sizeInitial+1;
        } catch (ValidatorException e) {
            assert false;
        }
    }

    @Test
    public void testCase2AddStudent() {
        //input data
        String idStudent="1234";
        String nameStudent="la";
        String group="-5";
        String email="lala@";
        String teacher="lalaMare";

        String[] parameters=new String[]{idStudent,nameStudent,group,email,teacher};

        try {
            studentService.add(parameters);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void TC1_BBT_EC() {
        int sizeInitial=studentService.getSize();

        try {
            studentService.add(new String[]{"0", "Lala", "934","lala@","lalaMare"});
            assert studentService.getSize() == sizeInitial+1;
        } catch (ValidatorException e) {
            assert false;
        }
    }

    @Test
    public void TC2_BBT_EC() {
        try {
            studentService.add(new String[]{"0", "", "934","lala@","lalaMare"});
            assert false;
        } catch (ValidatorException e) {
            assert true;
        }
    }

    @Test
    public void TC3_BBT_EC() {
        try {
            studentService.add(new String[]{"0", "Lala", "-5","lala@","lalaMare"});
            assert false;
        } catch (ValidatorException e) {
            assert true;
        }
    }

    @Test
    public void TC4_BBT_EC() {
        try {
            studentService.add(new String[]{"", "Lala", "939","lala@","lalaMare"});
            assert false;
        } catch (ValidatorException e) {
            assert true;
        }
    }

    @Test
    public void TC5_BBT_EC() {
        try {
            studentService.add(new String[]{"0", "Lala", "93aa","lala@","lalaMare"});
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void TC6_BBT_EC() {
        try {
            studentService.add(new String[]{"0", "Lala", "","lala@","lalaMare"});
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void TC7_BBT_BVA() {
        int sizeInitial=studentService.getSize();

        try {
            studentService.add(new String[]{"10", "Lalaa", "934","lala@","lalaMare"});
            assert studentService.getSize() == sizeInitial+1;
        } catch (ValidatorException e) {
            assert false;
        }
    }

    @Test
    public void TC8_BBT_BVA() {
        try {
            studentService.add(new String[]{"11", "Lala", "-10","lala@","lalaMare"});
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void TC9_BBT_BVA() {
        try {
            studentService.add(new String[]{"11", "", "-10","lala@","lalaMare"});
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }


}
