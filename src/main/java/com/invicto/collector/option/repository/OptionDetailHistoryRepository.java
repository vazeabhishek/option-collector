package com.invicto.collector.option.repository;

import com.invicto.collector.option.entity.Option;
import com.invicto.collector.option.entity.OptionDetailHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionDetailHistoryRepository extends CrudRepository<OptionDetailHistory, Long> {
    OptionDetailHistory findTop1ByOptionOrderByCollectionTimeDesc(Option option);
}
