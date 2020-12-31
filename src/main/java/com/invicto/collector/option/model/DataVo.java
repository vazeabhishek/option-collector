package com.invicto.collector.option.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DataVo {
    @JsonProperty("underlying")
    private String underlying;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("instrumentType")
    private String instrumentType;
    @JsonProperty("instrument")
    private String instrument;
    @JsonProperty("contract")
    private String contract;
    @JsonProperty("expiryDate")
    private String expiryDate;
    @JsonProperty("optionType")
    private String optionType;
    @JsonProperty("strikePrice")
    private int strikePrice;
    @JsonProperty("lastPrice")
    private double lastPrice;
    @JsonProperty("change")
    private double change;
    @JsonProperty("pChange")
    private double pChange;
    @JsonProperty("volume")
    private int volume;
    @JsonProperty("totalTurnover")
    private double totalTurnover;
    @JsonProperty("value")
    private double value;
    @JsonProperty("premiumTurnOver")
    private double premiumTurnOver;
    @JsonProperty("underlyingValue")
    private double underlyingValue;
    @JsonProperty("openInterest")
    private int openInterest;
    @JsonProperty("noOfTrades")
    private int noOfTrades;
    @JsonIgnore
    @JsonProperty("meta")
    private MetaVo metaVo;
}