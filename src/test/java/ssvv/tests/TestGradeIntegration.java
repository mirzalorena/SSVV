package ssvv.tests;

import Exceptions.ValidatorException;
import Repository.MemoryRepository.NotaRepo;
import Repository.Repo;
import Repository.TxtFileRepository.NotaFileRepo;
import Repository.TxtFileRepository.StudentFileRepo;
import Repository.TxtFileRepository.TemaLabFileRepo;
import Service.Service;
import Service.TxtFileService.NotaService;
import Service.TxtFileService.StudentService;
import Service.TxtFileService.TemaLabService;
import Validator.NotaValidator;
import Validator.StudentValidator;
import Validator.TemaLabValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.time.LocalDateTime;

public class TestGradeIntegration {
    private Repo assignRepo;
    private Repo studentRepo;
    private Repo gradeRepo;

    private Service assignService;
    private Service studentService;
    private Service gradeService;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestGradeIntegration.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

    @Before
    public void setup() {
        TemaLabValidator vs = new TemaLabValidator();
        StudentValidator studentValidator=new StudentValidator();
        NotaValidator notaValidator=new NotaValidator();

        assignRepo = null;
        studentRepo=null;
        gradeRepo=null;

        try {
            assignRepo = new TemaLabFileRepo("TemaLaborator.txt", vs);
            studentRepo = new StudentFileRepo("studenti.txt", studentValidator);
            gradeRepo = new NotaFileRepo("NoteStudenti.txt",notaValidator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assignService = new TemaLabService((TemaLabFileRepo) assignRepo);
        studentService=new StudentService((StudentFileRepo) studentRepo);
        gradeService = new NotaService((NotaFileRepo) gradeRepo);

    }

    @Test
    public void TC1_Integration_AddStudent(){
        int sizeInitial=studentService.getSize();

        //input data
        String idStudent="10";
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
    public void TC2_Integration_AddAssign()
    {
        int sizeInitial=assignService.getSize();

        try {
            assignService.add(new String[]{"11", "LalaTema", "8","6"});
            assert assignService.getSize() == sizeInitial+1;
        } catch (ValidatorException e) {
            assert false;
        }
    }

    @Test
    public void TC3_Integration_AddGrade()
    {
        int sizeInitial=gradeService.getSize();

        try {
            LocalDateTime datetime1 = LocalDateTime.of(2017, 1, 14, 10, 34);
            String date=datetime1.toString();
            gradeService.add(new String[]{"1", "10", "11","6",date});
            assert gradeService.getSize() == sizeInitial+1;
        } catch (ValidatorException e) {
            assert false;
        }
    }

    @Test
    public void TC4_Integration_Integrate()
    {
        int sizeInitialStudent=studentService.getSize();
        int sizeInitialAssign=assignService.getSize();
        int sizeInitialGrade=gradeService.getSize();

        try {
            studentService.add(new String[]{"15","lala","100","Lala@","LalaMare"});
            assignService.add(new String[]{"13", "LalaTema", "8","6"});
            gradeService.add(new String[]{"2", "10", "11","6","2018-07-14T17:45:55.9483536"});
            assert studentService.getSize() == sizeInitialStudent+1 && assignService.getSize()==sizeInitialAssign+1 && gradeService.getSize()==sizeInitialGrade+1;
        } catch (ValidatorException e) {
            assert false;
        }
    }
}
