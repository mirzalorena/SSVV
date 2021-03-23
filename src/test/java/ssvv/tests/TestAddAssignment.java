package ssvv.tests;

import Exceptions.ValidatorException;
import Repository.Repo;
import Repository.TxtFileRepository.StudentFileRepo;
import Repository.TxtFileRepository.TemaLabFileRepo;
import Service.Service;
import Service.TxtFileService.StudentService;
import Service.TxtFileService.TemaLabService;
import Validator.StudentValidator;
import Validator.TemaLabValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;

public class TestAddAssignment {
    private Repo assignRepo;
    private Service assignService;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppClass.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

    @Before
    public void setup() {
        TemaLabValidator vs = new TemaLabValidator();
        assignRepo = null;
        try {
            assignRepo = new TemaLabFileRepo("TemaLaborator.txt", vs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assignService = new TemaLabService((TemaLabFileRepo) assignRepo);

    }

    @Test
    public void testAddAssignmentSuccess() {
        int sizeInitial=assignService.getSize();

        try {
            assignService.add(new String[]{"1", "LalaTema", "8","6"});
            assert assignService.getSize() == sizeInitial+1;
        } catch (ValidatorException e) {
            assert false;
        }
    }

    @Test
    public void testAddAssignmentFailure() {
        try {
            assignService.add(new String[]{"1", "LalaTema", "16","16"});
            assert false;
        } catch (ValidatorException e) {
            assert true;
        }
    }
}
