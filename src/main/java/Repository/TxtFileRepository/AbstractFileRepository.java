package Repository.TxtFileRepository;

import Domain.HasId;
import Repository.MemoryRepository.AbstractCrudRepo;
import Validator.IValidator;
import Exceptions.ValidatorException;
import Exceptions.RepositoryException;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

public abstract class AbstractFileRepository<ID,E extends HasId<ID>> extends AbstractCrudRepo<ID,E> {
    private String filename;

    public AbstractFileRepository(IValidator v,String filename) {
        super(v);
        this.filename=filename;
        try {
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    private void writeAll() throws IOException{
        FileWriter myWriter = new FileWriter(filename);
        super.findAll().forEach(x-> { try { myWriter.write(x.toString()); myWriter.write("\n");} catch (IOException e) { e.printStackTrace(); } });
        myWriter.close();
    }

    private void writeToFile(E entity)throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
        out.writeChars(entity.toString());
    }

    private void readFromFile() throws IOException, ValidatorException {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filename));
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] info = data.split("#");
                E e = extractEntity(info);
                E saved = super.save(e);
            }
        } catch (IOException e) {
           System.out.println(e.getStackTrace());
        }
    }

    public abstract E extractEntity(String[] info);
    @Override
    public E findOne(ID id) {
        return super.findOne(id);
    }

    @Override
    public Iterable<E> findAll() {
        return super.findAll();
    }
    @Override
    public E save(E entity) throws ValidatorException {
        try{
            E e=super.save(entity);
           // writeToFile(entity);
            writeAll();
            return e;
        }
        catch (IOException ex){
            throw new RepositoryException("The file "+filename+" cannot be found!\n");
        }
    }

    @Override
    public E delete(ID id) {
        try{
            E e=super.delete(id);
            writeAll();
            return e;
        }
        catch (IOException ex){
            throw new RepositoryException("The file "+filename+" cannot be found!\n");
        }
    }

    @Override
    public E update(E entity) {
        try{
            E e= super.update(entity);
            writeAll();
            return e;
        }catch (IOException ex){
            throw  new RepositoryException("The file "+filename+" cannot be found!\n");
        }
    }


}