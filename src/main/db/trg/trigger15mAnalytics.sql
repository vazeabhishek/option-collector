CREATE TRIGGER TRG_15MINS_OPTIONANALYTICS
  AFTER INSERT
  ON option.OPTION_DETAIL_HISTORY
  FOR EACH ROW
  EXECUTE PROCEDURE FN_15MINS_OPTIONANALYTICS();