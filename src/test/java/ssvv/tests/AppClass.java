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
    public void testCase1AddStudent() throws IOException {
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
        String nameStudent="";
        String group="-5";
        String email="lala@";
        String teacher="lalaMare";

        String[] parameters=new String[]{idStudent,nameStudent,group,email,teacher};

        try {
            studentService.add(parameters);
            assert false;
        } catch (ValidatorException e) {
            assert true;
        }
    }


}
