package com.invicto.collector.option.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "OPTION_DETAIL_HISTORY")
@Getter
@Setter
public class OptionDetailHistory {
    @Id
    @Column(name = "ODH_RECORD_NO")
    @GeneratedValue(generator = "odh-sequence-generator")
    @GenericGenerator(
            name = "odh-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "odh_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long recordNo;
    @Column(name = "LATEST_PRICE")
    private double lastprice;
    @Column(name = "LATEST_OI")
    private double openInterest;
    @Column(name = "P_DELTA_LTP_WRT_PREV")
    private double percentChangeInLtpWrtPrev;
    @Column(name = "OI_CHG_WRT_PREV")
    private double percentChangeInOiWrtPrev;
    @Column(name = "VOLUME")
    private double volume;
    @Column(name = "NO_OF_TRADES")
    private int noOfTrades;
    @Column(name = "COLLECTION_TIME",columnDefinition = "timestamp without time zone")
    private Timestamp collectionTime;
    @Column(name = "UNDERLYING_VALUE")
    private double underlyingValue;
    @Column(name = "PRICE_CHANGE_WRT_PDAY")
    private double change;
    @Column(name = "P_PRICE_CHANGE_WRT_PDAY")
    private  double pChange;
    @Column(name = "TREND_INDICATOR")
    private long trendIndicator;
    @Column(name = "TREND_STRENGTH")
    private long trendStrength;
    @Column(name = "MARKET_FORCE")
    private double marketForce;
    @Column(name = "CUMULATIVE_MARKET_FORCE")
    private double cumulativeForce;
    @Column(name = "COUNTER")
    private int counter;
    @ManyToOne(targetEntity = Option.class, fetch = FetchType.EAGER)
    private Option option;
}
