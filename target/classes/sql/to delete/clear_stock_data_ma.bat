@ECHO OFF
set pw=root
set /p pw=password of mysql root user:
:begin
mysql -uroot -p%pw% < clear_stock_data_ma.sql
@echo clear tables stock_data, ma finished
pause
:end
