package Service.TxtFileService;

import Domain.HasId;
import Exceptions.ValidatorException;
import Repository.TxtFileRepository.AbstractFileRepository;
import Service.Service;
import Validator.IValidator;

public abstract  class AbstractService <ID,E extends HasId<ID>> implements Service<ID, E> {
    private IValidator<E> v;
    private AbstractFileRepository<ID,E> repo;
    public AbstractService(AbstractFileRepository repo){
        this.repo=repo;
    }

    protected abstract E extractEntity(String[] params);

    @Override
    public void add(String[] params) throws ValidatorException {
        E entity = repo.extractEntity(params);
        repo.save(entity);
    }
    @Override
    public void remove(ID id){
        //E entity = repo.extractEntity(params);
        repo.delete(id);
    }
    @Override
    public void update(String[] params) {
        E entity = repo.extractEntity(params);
        repo.update(entity);
    }
    @Override
    public Iterable<E> findAll(){
        return repo.findAll();
    }

    @Override
    public E findOne(ID id){
        return repo.findOne(id);
    }

    @Override
    public int getSize(){
        return (int) repo.size();
    }

}

