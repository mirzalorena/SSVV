package Service.XMLFileService;
import Domain.*;
import Exceptions.ValidatorException;
import Repository.XMLFileRepository.AbstractXMLRepo;
import Service.Service;

public abstract class AbstractXMLService<ID,E extends HasId<ID>> implements Service<ID, E> {
    private AbstractXMLRepo xmlrepo;

    public AbstractXMLService(AbstractXMLRepo xmlrepo)  {
        this.xmlrepo = xmlrepo;
    }

    protected abstract E extractEntity(String[] params);
        //return new Student(params[0],params[1],Integer.parseInt(params[2]),params[3],params[4]);
    //}

    @Override
    public void add(String params[]) throws ValidatorException {
        E e=extractEntity(params);
        xmlrepo.save(e);
    }
    @Override
    public void remove(ID id){
        xmlrepo.delete(id);
    }

    @Override
    public void update(String params[]){
        E s=extractEntity(params);
        xmlrepo.update(s);
    }
    @Override
    public E findOne(ID id){
        return (E) xmlrepo.findOne(id);
    }

    @Override
    public Iterable<E>findAll(){
        return xmlrepo.findAll();
    }

    @Override
    public int getSize(){
        return xmlrepo.getSize();
    }
}