
delete from price_record where record_time=date(now());
update commodity set crawl_date='1900-00-00';
