CREATE OR REPLACE FUNCTION option.calculateTrendInInterval(
   counter_end integer,
   counter_start integer,
   option_contract_no bigint,
   OUT trend double precision,
   OUT strength double precision,
   OUT avgLtp double precision
)
RETURNS record
AS
$$
    DECLARE
    counter_interval INTEGER;
    begin
    counter_interval = counter_end - counter_start + 1;

    select sum((ABS(P_DELTA_LTP_WRT_PREV) * trend_indicator))/counter_interval as trend,
    sum((ABS(OI_CHG_WRT_PREV) * trend_strength))/counter_interval as strength,sum(latest_price)/counter_interval as avgLtp
    into trend,strength,avgLtp
    from option.option_detail_history where option_option_record_no = option_contract_no
    and counter between counter_start and counter_end;
end
$$ LANGUAGE plpgsql;