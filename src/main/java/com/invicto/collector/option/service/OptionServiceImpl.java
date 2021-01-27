package com.invicto.collector.option.service;

import com.invicto.collector.option.entity.Option;
import com.invicto.collector.option.entity.OptionDetailHistory;
import com.invicto.collector.option.model.DataVo;
import com.invicto.collector.option.model.SnapshotVo;
import com.invicto.collector.option.repository.OptionDetailHistoryRepository;
import com.invicto.collector.option.repository.OptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service("optionServiceBean")
@Slf4j
public class OptionServiceImpl {

    private final long STRONG = 1;
    private final long WEAK = -1;
    private final long BULLISH = 1;
    private final long BEARISH = -1;
    private final long NEUTRAL = 0;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionDetailHistoryRepository optionDetailHistoryRepository;

    @Autowired
    @Qualifier("longDateFormatterBean")
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    @Qualifier("shortDateFormatterBean")
    private DateTimeFormatter dateFormatter;

    @Autowired
    private LocalDateTimeConverter localDateTimeConverter;

    @Autowired
    private LocalDateConverter localDateConverter;

    public void saveSnapshot(SnapshotVo root) {
        SnapshotVo snapshotVo = (SnapshotVo) root;
        for (DataVo data : snapshotVo.getData()) {
            Option option = optionRepository.findByIdentifier(data.getIdentifier());
            if (option != null) {
                OptionDetailHistory latest = optionDetailHistoryRepository.findTop1ByOptionOrderByCollectionTimeDesc(option);
                OptionDetailHistory optionDetailHistory = new OptionDetailHistory();
                optionDetailHistory.setOption(option);
                optionDetailHistory.setCounter(latest.getCounter() + 1);
                optionDetailHistory.setVolume(data.getVolume());
                optionDetailHistory.setChange(data.getChange());
                optionDetailHistory.setPChange(data.getPChange());
                optionDetailHistory.setLastprice(data.getLastPrice());
                optionDetailHistory.setCollectionTime(localDateTimeConverter.convertToDatabaseColumn(LocalDateTime.parse(snapshotVo.getTimestamp(), dateTimeFormatter)));
                optionDetailHistory.setNoOfTrades(data.getNoOfTrades());
                optionDetailHistory.setOpenInterest(data.getOpenInterest());
                optionDetailHistory.setUnderlyingValue(data.getUnderlyingValue());
                optionDetailHistory.setTrendStrength(NEUTRAL);
                optionDetailHistory.setTrendIndicator(NEUTRAL);
                //calculations
                if (latest.getOpenInterest() != 0) {
                    optionDetailHistory.setPercentChangeInOiWrtPrev(100 * ((data.getOpenInterest() - latest.getOpenInterest()) / latest.getOpenInterest()));
                }
                if (latest.getLastprice() != 0) {
                    optionDetailHistory.setPercentChangeInLtpWrtPrev(100 * ((data.getLastPrice() - latest.getLastprice()) / latest.getLastprice()));
                }
                if (data.getOpenInterest() > latest.getOpenInterest() && data.getLastPrice() > latest.getLastprice() && data.getNoOfTrades() > latest.getNoOfTrades()) {
                    optionDetailHistory.setTrendStrength(STRONG);
                    optionDetailHistory.setTrendIndicator(BULLISH);
                }
                if (data.getOpenInterest() < latest.getOpenInterest() && data.getLastPrice() < latest.getLastprice() && data.getNoOfTrades() > latest.getNoOfTrades()) {
                    optionDetailHistory.setTrendStrength(STRONG);
                    optionDetailHistory.setTrendIndicator(BEARISH);
                }
                if (data.getOpenInterest() < latest.getOpenInterest() && data.getLastPrice() > latest.getLastprice()) {
                    optionDetailHistory.setTrendStrength(WEAK);
                    optionDetailHistory.setTrendIndicator(BULLISH);
                }
                if (data.getOpenInterest() > latest.getOpenInterest() && data.getLastPrice() < latest.getLastprice()) {
                    optionDetailHistory.setTrendStrength(WEAK);
                    optionDetailHistory.setTrendIndicator(BEARISH);
                }
                optionDetailHistory.setMarketForce((data.getNoOfTrades() - latest.getNoOfTrades()) * (data.getLastPrice() - latest.getLastprice()));
                optionDetailHistory.setCumulativeForce(latest.getCumulativeForce() + optionDetailHistory.getMarketForce());
                option.getOptionDetailHistoryList().add(optionDetailHistory);
            } else {
                option = new Option();
                option.setContract(data.getContract());
                option.setExpiryDate(localDateConverter.convertToDatabaseColumn(LocalDate.parse(data.getExpiryDate(), dateFormatter)));
                option.setIdentifier(data.getIdentifier());
                option.setUnderlying(data.getUnderlying());
                option.setStrikePrice(data.getStrikePrice());
                OptionDetailHistory optionDetailHistory = new OptionDetailHistory();
                optionDetailHistory.setOption(option);
                optionDetailHistory.setCounter(1);
                optionDetailHistory.setVolume(data.getVolume());
                optionDetailHistory.setChange(data.getChange());
                optionDetailHistory.setPChange(data.getPChange());
                optionDetailHistory.setLastprice(data.getLastPrice());
                optionDetailHistory.setCollectionTime(localDateTimeConverter.convertToDatabaseColumn(LocalDateTime.parse(snapshotVo.getTimestamp(), dateTimeFormatter)));
                optionDetailHistory.setNoOfTrades(data.getNoOfTrades());
                optionDetailHistory.setOpenInterest(data.getOpenInterest());
                optionDetailHistory.setUnderlyingValue(data.getUnderlyingValue());
                optionDetailHistory.setTrendStrength(NEUTRAL);
                optionDetailHistory.setTrendIndicator(NEUTRAL);
                List<OptionDetailHistory> optionDetailHistoryList = new LinkedList<>();
                optionDetailHistoryList.add(optionDetailHistory);
                option.setOptionDetailHistoryList(optionDetailHistoryList);
            }
            option.setLastUpdatedTime(localDateTimeConverter.convertToDatabaseColumn(LocalDateTime.parse(snapshotVo.getTimestamp(), dateTimeFormatter)));
            option.setRunNumber(root.getRunNumber());
            try {
                optionRepository.save(option);
                log.info("Processed Option " + option.getContract() + ", Option Record No " + option.getOptionRecordNo() + ", Run Number " + option.getRunNumber());
            } catch (Exception ex) {
                log.error("Failed processing Option : " + option.getContract() + ", Run Number : " + option.getRunNumber());
                log.error(ex.getMessage());
            }
        }
    }
    @Transactional
    public boolean deleteSnapShot(long runNumber) {
        try {
            long count = optionRepository.deleteByRunNumberLessThan(runNumber);
            log.info("Deleted, No of Contract : " + count);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
