package com.orchowski.smartcharginghexagon.shared;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class InMemoryGenericRepository<T, ID> implements MongoRepository<T,ID> {
    protected static final String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE = "Method not implemented";
    protected final Function<T, ID> idGetter;
    protected final Supplier<ID> idGenerator;
    protected final BiConsumer<T,ID> idSetter;
    protected Map<ID, T> map = new ConcurrentHashMap<>();

    public InMemoryGenericRepository(Function<T, ID> idGetter, Supplier<ID> idGenerator, BiConsumer<T,ID> idSetter) {
        this.idGetter = idGetter;
        this.idGenerator = idGenerator;
        this.idSetter = idSetter;
    }

    @Override
    public <S extends T> S insert(S entity) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> List<S> insert(Iterable<S> entities) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> S save(S entity) {
        ID id = getIdOrGenerateNewIfNotProvided(entity);
        map.put(id, entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public boolean existsById(ID id) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteById(ID id) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void delete(T entity) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public List<T> findAll(Sort sort) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    private  <S extends T> ID getIdOrGenerateNewIfNotProvided(S entity) {
        ID id = Optional.ofNullable(idGetter.apply(entity)).orElse(idGenerator.get());
        idSetter.accept(entity, id);
        return id;
    }
}
