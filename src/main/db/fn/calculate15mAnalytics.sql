CREATE OR REPLACE FUNCTION option.calcAndStoreAnalytics()
  RETURNS TRIGGER
  LANGUAGE PLPGSQL
  AS
$$
DECLARE
v_sentiment character varying(255);
v_signal character varying(255);
v_trend double precision;
v_strength double precision;
v_ltp double precision;
latest record;
BEGIN
	IF NEW.COUNTER % 5 = 0
	THEN
         select * from option.calculateTrendInInterval(NEW.COUNTER,(NEW.COUNTER - 4),NEW.option_option_record_no)
         INTO v_trend,v_strength,v_ltp;
         IF v_trend > 0 AND v_strength > 0
         THEN
          v_sentiment := 'STRONG BULLISH';
         END IF;
         IF v_trend < 0 AND v_strength < 0
          THEN
           v_sentiment := 'STRONG BEARISH';
         END IF;
         IF v_trend > 0 AND v_strength < 0
          THEN
           v_sentiment := 'WEAK BULLISH';
         END IF;
         IF v_trend < 0 AND v_strength > 0
          THEN
           v_sentiment := 'WEAK BEARISH';
         END IF;

         SELECT collection_time,ltp,option_option_record_no,trend,strength,sentiment FROM option.OPTION_15M_A
         WHERE option_option_record_no = NEW.option_option_record_no
         ORDER BY collection_time desc LIMIT 1
         INTO latest;

         v_signal := 'WAIT';
         IF (latest.sentiment = 'WEAK BULLISH' OR latest.sentiment = v_sentiment) AND v_sentiment = 'STRONG BULLISH'
            THEN
             v_signal := 'BUY';
         END IF;

         IF (latest.sentiment = 'WEAK BEARISH' OR latest.sentiment = v_sentiment) AND v_sentiment = 'STRONG BEARISH'
            THEN
             v_signal := 'SELL';
         END IF;

		 INSERT INTO option.OPTION_15M_A(collection_time,ltp,option_option_record_no,trend,strength,sentiment,signal,cumulative_trend)
		 VALUES(NEW.collection_time,v_ltp,NEW.option_option_record_no,v_trend,v_strength,v_sentiment,v_signal,NEW.cumulative_market_trend);
	END IF;
	RETURN NEW;
END;
$$

