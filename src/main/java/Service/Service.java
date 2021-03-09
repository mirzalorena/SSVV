package Service;

import Exceptions.ValidatorException;

public interface Service<ID,E> {
    public void add(String params[]) throws ValidatorException;
    public void remove(ID id);
    public void update(String params[]);
    public E findOne(ID id);
    public Iterable<E>findAll();
    public int getSize();
}
