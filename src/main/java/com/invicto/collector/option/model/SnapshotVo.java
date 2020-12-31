package com.invicto.collector.option.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SnapshotVo{
    @JsonProperty("data")
    private List<DataVo> data;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("marketStatus")
    private MarketStatusVo marketStatusVo;
    @JsonIgnore
    private long runNumber;
}
