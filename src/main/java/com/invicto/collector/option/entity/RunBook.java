package com.invicto.collector.option.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "RUN_BOOK")
@Getter
@Setter
public class RunBook {
    @Id
    @Column(name = "RUN_BOOK_ID")
    private long runBookId;
    @Column(name = "RUN_COUNTER")
    private long runNumber;
    @Column(name = "RUN_AT", columnDefinition = "timestamp without time zone")
    private Timestamp runAt;
}
