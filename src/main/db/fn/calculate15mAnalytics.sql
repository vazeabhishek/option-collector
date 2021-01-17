CREATE OR REPLACE FUNCTION FN_15MINS_ANALYTICS()
  RETURNS TRIGGER
  LANGUAGE PLPGSQL
  AS
$$
DECLARE
v_sentiment character varying(255);
v_trend double precision;
v_strength double precision;
v_ltp double precision;
BEGIN
	IF NEW.COUNTER % 5 = 0
	THEN
         select * from option.calculateOptionTrendInInterval(NEW.COUNTER,(NEW.COUNTER - 4),NEW.option_option_record_no)
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
		 INSERT INTO option.OPTION_15M_A(collection_time,ltp,option_option_record_no,trend,strength,sentiment)
		 VALUES(NEW.collection_time,v_ltp,NEW.option_option_record_no,v_trend,v_strength,v_sentiment);
	END IF;
	RETURN NEW;
END;
$$

