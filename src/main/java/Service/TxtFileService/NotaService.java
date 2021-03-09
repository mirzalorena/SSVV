package Service.TxtFileService;
import Domain.*;
import Exceptions.ValidatorException;
import Repository.TxtFileRepository.NotaFileRepo;
import Service.XMLFileService.TemaLabXMLService;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class NotaService extends AbstractService<Integer,Nota> {
    public NotaService(NotaFileRepo notaRepo){
        super(notaRepo);
    }

    public String depunctare(TemaLabService srv, String idt, String val){
        TemaLab t=srv.findOne(Integer.parseInt(idt));
        System.out.println(t);
        //Nota n=this.findOne(Integer.parseInt(idn));
        //System.out.println(n);
        String val1=Double.toString(Double.parseDouble(val)-(t.getSaptammanaPredarii()-t.getTermenLimita())*2.5);
        System.out.println(val1);
        //Nota n1=new Nota(idn,n.getStudentId(),idt,n.getValoare()-(t.getSaptammanaPredarii()-t.getTermenLimita()),n.getLdt());
        return val1;
    }

    public void printAllNotes(TemaLabService srv) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream("NoteStudenti.txt"));
        this.findAll().forEach(n-> {
            try {
                out.writeChars("Tema: "+n.getTemaLabId()+
                        "\nNota: "+n.getValoare()+
                        "\nPredata in saptamana: "+srv.findOne(n.getTemaLabId()).getSaptammanaPredarii()+
                        "\nDeadline: "+srv.findOne(n.getTemaLabId()).getTermenLimita()+
                        "\nFeedback: Se putea si mai bine!\n\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void add(String[] params) throws ValidatorException {

    }

    @Override
    protected Nota extractEntity(String[] params){
        Nota n=new Nota(Integer.parseInt(params[0]),params[1],Integer.parseInt(params[2]),Double.parseDouble(params[3]), LocalDateTime.parse(params[4]));
        return n;

    }
}
