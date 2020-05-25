package dao;

import java.util.List;

public interface AbstractDAO<E> {

    public abstract void add(E entity);

    public abstract List<E> getAll();

    public abstract E getById(Long id);

    public abstract void update(E entity);

    public abstract void delete(E entity);

    public abstract void addOrUpdate(E entity);
}
