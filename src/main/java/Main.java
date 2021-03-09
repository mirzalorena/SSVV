import Exceptions.*;
import Repository.TxtFileRepository.NotaFileRepo;
import Repository.TxtFileRepository.StudentFileRepo;
import Repository.TxtFileRepository.TemaLabFileRepo;
import Repository.XMLFileRepository.NotaXMLRepo;
import Repository.XMLFileRepository.StudentXMLRepo;
import Repository.XMLFileRepository.TemaLabXMLRepo;
import Service.TxtFileService.NotaService;
import Service.TxtFileService.StudentService;
import Service.TxtFileService.TemaLabService;
import Service.XMLFileService.NotaXMLService;
import Service.XMLFileService.StudentXMLService;
import Service.XMLFileService.TemaLabXMLService;
import Validator.*;
import UI.*;
import java.io.IOException;



public class Main {
    public static void main(String[] args) throws IOException, ValidatorException {
        //System.out.println("Hello World!");
        StudentValidator vs=new StudentValidator();
        TemaLabValidator vt=new TemaLabValidator();
        NotaValidator vn=new NotaValidator();
        //StudentXMLRepo strepo=new StudentXMLRepo(vs,"StudentiXML.xml");
        //TemaLabXMLRepo tmrepo=new TemaLabXMLRepo(vt,"TemaLaboratorXML.xml");
        //NotaXMLRepo ntrepo=new NotaXMLRepo(vn,"NotaXML.xml");
        StudentFileRepo strepo = new StudentFileRepo("studenti.txt", vs);
        TemaLabFileRepo tmrepo = new TemaLabFileRepo("TemaLaborator.txt", vt);
        NotaFileRepo ntrepo = new NotaFileRepo("Nota.txt", vn);
        StudentService stsrv=new StudentService(strepo);
        TemaLabService tmsrv=new TemaLabService(tmrepo);
        NotaService ntsrv=new NotaService(ntrepo);
        ui ui=new ui(stsrv,tmsrv,ntsrv);
        ui.run();
    }
}