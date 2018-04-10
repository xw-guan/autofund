-- -- @author XWGuan 
-- create at 2018-03-07

-- 创建account相关数据库表

-- 创建account表
CREATE TABLE IF NOT EXISTS `autofund`.`account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `super_account_id` BIGINT NULL DEFAULT -1,
  `owner_id` BIGINT NULL DEFAULT -1,
  `owner_type` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_owner_id_owner_type` (`owner_id` ASC, `owner_type` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建asset_history表
CREATE TABLE IF NOT EXISTS `autofund`.`asset_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT NULL DEFAULT -1,
  `date` DATE NULL,
  `pos_asset` DOUBLE NULL DEFAULT 0,
  `pos_income` DOUBLE NULL DEFAULT 0,
  `pos_income_rate_pct` DOUBLE NULL DEFAULT 0,
  `pos_cost` DOUBLE NULL DEFAULT 0,
  `total_cost` DOUBLE NULL DEFAULT 0,
  `total_income` DOUBLE NULL DEFAULT 0,
  `total_income_rate_pct` DOUBLE NULL DEFAULT 0,
  `total_history_income` DOUBLE NULL DEFAULT 0,
  `pos_share` DOUBLE NULL DEFAULT 0,
  `pos_price` DOUBLE NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_account_id_date` (`account_id` ASC, `date` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建trade_detail表
CREATE TABLE IF NOT EXISTS `autofund`.`trade_detail` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT NULL DEFAULT -1,
  `date` DATE NULL,
  `trade_yuan` DOUBLE NULL DEFAULT 0,
  `trade_share` DOUBLE NULL DEFAULT 0,
  `buy_sum` DOUBLE NULL DEFAULT 0,
  `sell_sum` DOUBLE NULL DEFAULT 0,
  `trade_state`  VARCHAR(20) NULL DEFAULT 'TO_TRADE',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_account_id_date` (`account_id` ASC, `date` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;