package com.invicto.collector.option.repository;

import com.invicto.collector.option.entity.RunBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunBookRepository extends CrudRepository<RunBook,Long> {
}
