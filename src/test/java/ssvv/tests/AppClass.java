package ssvv.tests;

import Exceptions.ValidatorException;
import Repository.Repo;
import Repository.TxtFileRepository.StudentFileRepo;
import Service.Service;
import Service.TxtFileService.StudentService;
import Validator.StudentValidator;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;


public class AppClass {
    private Repo repo;
    private Service service;



    @Test
    public void testCase1AddStudent() throws IOException {
        StudentValidator vs=new StudentValidator();
        Repo studentRepo = new StudentFileRepo("studenti.txt", vs);
        Service studentService=new StudentService((StudentFileRepo) studentRepo);

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
    public void testCase2AddStudent() throws IOException {
        StudentValidator vs=new StudentValidator();
        Repo studentRepo = new StudentFileRepo("studenti.txt", vs);
        Service studentService=new StudentService((StudentFileRepo) studentRepo);

        int sizeInitial=studentService.getSize();

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
            //e.printStackTrace();
        }
    }

    @Test
    public void testCase3AddStudent() throws IOException {
        /**
         * Invalid group
         */
        StudentValidator vs=new StudentValidator();
        Repo studentRepo = new StudentFileRepo("studenti.txt", vs);
        Service studentService=new StudentService((StudentFileRepo) studentRepo);

        int sizeInitial=studentService.getSize();

        //input data
        String idStudent="1234";
        String nameStudent="Ion Glanetasu";
        String group="lala";
        String email="lala@";
        String teacher="lalaMare";

        String[] parameters=new String[]{idStudent,nameStudent,group,email,teacher};

        try {
            studentService.add(parameters);
            assert false;
        } catch (ValidatorException e) {
            assert true;
            //e.printStackTrace();
        }
    }

}
