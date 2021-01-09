package com.invicto.collector.option.service;

import com.invicto.collector.option.entity.RunBook;
import com.invicto.collector.option.model.SnapshotVo;
import com.invicto.collector.option.repository.RunBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class OptionCollector implements Runnable {

    @Autowired
    private RunBookRepository runBookRepository;
    @Autowired
    private OptionContractCollector optionContractCollector;

    private String baselink = "https://www.nseindia.com/market-data/equity-derivatives-watch";

    @Autowired
    @Qualifier("longDateFormatterBean")
    private DateTimeFormatter longDateTimeFormatter;

    @Autowired
    private LocalDateTimeConverter localDateTimeConverter;

    @Autowired
    private OptionServiceImpl optionService;

    @Override
    public void run() {
        try {
            Optional<RunBook> optionRunBookOptional = runBookRepository.findById(1L);
            if (optionRunBookOptional.isPresent()) {
                long currentOptionRunNumber = optionRunBookOptional.get().getRunNumber() + 1;
                log.info("Option Run Number : " + currentOptionRunNumber);
                SnapshotVo optionSnap = optionContractCollector.fetchIndexAndStockOptions(getCookies(), createHeader());
                if (optionSnap != null) {
                    optionSnap.setRunNumber(currentOptionRunNumber);
                    optionService.saveSnapshot(optionSnap);
                    RunBook optionRunBook = optionRunBookOptional.get();
                    optionService.deleteSnapShot(optionRunBook.getRunNumber());
                    optionRunBook.setRunNumber(currentOptionRunNumber);
                    optionRunBook.setRunAt(localDateTimeConverter.convertToDatabaseColumn(LocalDateTime.parse(((SnapshotVo) optionSnap).getTimestamp(), longDateTimeFormatter)));
                    runBookRepository.save(optionRunBook);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private Map<String, String> getCookies() throws IOException {
        Connection.Response response = Jsoup.connect(this.baselink).timeout(10000).ignoreHttpErrors(true).validateTLSCertificates(true).followRedirects(true).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36").headers(this.createHeader()).method(Connection.Method.GET).execute();
        return response.cookies();
    }

    private Map<String, String> createHeader() {
        Map<String, String> header = new HashMap();
        header.put("Connection", "keep-alive");
        header.put("Upgrade-Insecure-Requests", "1");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.put("Sec-Fetch-Site", "none");
        header.put("Sec-Fetch-Mode", "navigate");
        header.put("Sec-Fetch-User", "?1");
        header.put("Sec-Fetch-Dest", "document");
        header.put("Accept-Encoding", "kgzip, deflate, br");
        header.put("Accept-Language", "en-US,en;q=0.9,sv;q=0.8");
        return header;
    }
}

