@ECHO OFF
set pw=root
set /p pw=password of mysql root user:
:begin
@echo creating database autofund...
mysql -uroot -p%pw% < create_db.sql
@echo create database autofund finished
@echo creating stock tables...
mysql -uroot -p%pw% < create_stock_tables.sql
@echo create stock tables finished
@echo creating fund tables...
mysql -uroot -p%pw% < create_fund_tables.sql
@echo create fund tables finished
@echo creating plan tables...
mysql -uroot -p%pw% < create_plan_tables.sql
@echo create plan tables finished
@echo creating rule tables...
mysql -uroot -p%pw% < create_rule_tables.sql
@echo create rule tables finished
@echo creating tactic tables...
mysql -uroot -p%pw% < create_tactic_tables.sql
@echo create tactic tables finished
@echo creating user tables...
mysql -uroot -p%pw% < create_user_tables.sql
@echo create user tables finished
@echo creating account tables...
mysql -uroot -p%pw% < create_account_tables.sql
@echo create account tables finished
@echo init database in mysql finished
pause
:end
