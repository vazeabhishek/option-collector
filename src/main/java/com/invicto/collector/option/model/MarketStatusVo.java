package com.invicto.collector.option.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketStatusVo {
    @JsonProperty("market")
    private String market;
    @JsonProperty("marketOpenOrClose")
    private String marketOpenOrClose;
    @JsonProperty("marketCurrentTradingDate")
    private String marketCurrentTradingDate;
    @JsonProperty("marketOpenTime")
    private String marketOpenTime;
    @JsonProperty("marketCloseTime")
    private String marketCloseTime;
    @JsonProperty("marketPreviousTradingDate")
    private String marketPreviousTradingDate;
    @JsonProperty("marketNextTradingDate")
    private String marketNextTradingDate;
    @JsonProperty("marketStatusMessage")
    private String marketStatusMessage;

    @Override
    public String toString() {
        return "MarketStatusVo{" +
                "market='" + market + '\'' +
                ", marketOpenOrClose='" + marketOpenOrClose + '\'' +
                ", marketCurrentTradingDate='" + marketCurrentTradingDate + '\'' +
                ", marketOpenTime='" + marketOpenTime + '\'' +
                ", marketCloseTime='" + marketCloseTime + '\'' +
                ", marketPreviousTradingDate='" + marketPreviousTradingDate + '\'' +
                ", marketNextTradingDate='" + marketNextTradingDate + '\'' +
                ", marketStatusMessage='" + marketStatusMessage + '\'' +
                '}';
    }
}
