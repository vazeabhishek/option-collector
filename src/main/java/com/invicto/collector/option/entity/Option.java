package com.invicto.collector.option.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "OPTION")
@Getter
@Setter
public class Option {
    @Id
    @Column(name = "OPTION_RECORD_NO")
    @GeneratedValue(generator = "option-sequence-generator")
    @GenericGenerator(
            name = "option-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "option_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long optionRecordNo;
    @Column(name = "UNDERLYING")
    private String underlying;
    @Column(name = "CONTRACT")
    private String contract;
    @Column(name = "IDENTIFIER")
    private String identifier;
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    @Column(name = "STRIKE_PRICE")
    private double strikePrice;
    @Column(name = "RUN_NUMBER")
    private long runNumber;
    @Column(name = "LAST_UPDATED_TIME",columnDefinition = "timestamp without time zone")
    private Timestamp lastUpdatedTime;
    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OptionDetailHistory> optionDetailHistoryList;
}
