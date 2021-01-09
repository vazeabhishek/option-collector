package com.invicto.collector.option.repository;
import com.invicto.collector.option.entity.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository<Option,Long> {
    Option findByIdentifier(String identifier);
    long  deleteByRunNumber(long runNumber);
}
