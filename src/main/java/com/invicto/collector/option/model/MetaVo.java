package com.invicto.collector.option.model;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreType
public class MetaVo {
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("industry")
    private String industry;
    @JsonProperty("activeSeries")
    private List<String> activeSeries;
    @JsonProperty("debtSeries")
    private List<String> debtSeries;
    @JsonProperty("tempSuspendedSeries")
    private List<String> tempSuspendedSeries;
    @JsonProperty("isFNOSec")
    private boolean isFNOSec;
    @JsonProperty("isCASec")
    private boolean isCASec;
    @JsonProperty("isSLBSec")
    private boolean isSLBSec;
    @JsonProperty("isDebtSec")
    private boolean isDebtSec;
    @JsonProperty("isSuspended")
    private boolean isSuspended;
    @JsonProperty("isETFSec")
    private boolean isETFSec;
    @JsonProperty("isDelisted")
    private boolean isDelisted;
    @JsonProperty("isin")
    private String isin;
}
