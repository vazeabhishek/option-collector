package com.invicto.collector.option.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.collector.option.model.SnapshotVo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Component
public class OptionContractCollector {
    @Autowired
    private ObjectMapper objectMapper;
    private LocalDateTime lastCollectionTime;
    @Autowired
    @Qualifier("longDateFormatterBean")
    private DateTimeFormatter dateTimeFormatter;
  //  private final String indexFutureApi = "https://www.nseindia.com/api/liveEquity-derivatives?index=nse50_opt";
    private final String stockOptionApi = "https://www.nseindia.com/api/liveEquity-derivatives?index=stock_opt";
   // private final String bankNiftyFutureApi = "https://www.nseindia.com/api/liveEquity-derivatives?index=nifty_bank_opt";

    public SnapshotVo fetchIndexAndStockOptions(Map<String, String> cookies, Map<String, String> headers) throws IOException {
        Connection.Response stockOptionData = Jsoup.connect(stockOptionApi).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(true).followRedirects(true).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36").headers(headers).method(Connection.Method.GET).cookies(cookies)
                .ignoreContentType(true).execute();
        SnapshotVo stockOptionSnap = objectMapper.readValue(stockOptionData.body(), SnapshotVo.class);

      if (stockOptionSnap.getMarketStatusVo().getMarketStatusMessage().contains("Closed")) {
            log.info("Option Market is closed");
            //return null;
        }
        if (lastCollectionTime == null)
            lastCollectionTime = LocalDateTime.parse(stockOptionSnap.getTimestamp(), dateTimeFormatter);
        else {
            if (lastCollectionTime.isEqual(LocalDateTime.parse(stockOptionSnap.getTimestamp(), dateTimeFormatter))) {
                log.info("Snap for Options @ " + lastCollectionTime + " already collected");
                return null;
            } else
                lastCollectionTime = LocalDateTime.parse(stockOptionSnap.getTimestamp(), dateTimeFormatter);
        }
       /* Connection.Response indexOptionData = Jsoup.connect(indexFutureApi).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(true).followRedirects(true).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36").headers(headers).method(Connection.Method.GET).cookies(cookies)
                .ignoreContentType(true).execute();
        SnapshotVo indexOptionSnap = objectMapper.readValue(indexOptionData.body(), SnapshotVo.class);
        Connection.Response bankNiftyOptionData = Jsoup.connect(bankNiftyFutureApi).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(true).followRedirects(true).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36").headers(headers).method(Connection.Method.GET).cookies(cookies)
                .ignoreContentType(true).execute();
        SnapshotVo bankNiftyOptionSnap = objectMapper.readValue(bankNiftyOptionData.body(), SnapshotVo.class);
        indexOptionSnap.getData().addAll(stockOptionSnap.getData());
        indexOptionSnap.getData().addAll(bankNiftyOptionSnap.getData());*/
        return stockOptionSnap;
    }

}
