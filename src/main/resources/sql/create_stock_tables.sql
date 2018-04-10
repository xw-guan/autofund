-- -- @author XWGuan 
-- create at 2017-10-12, modify at 2017-10-21

-- 创建数据库表

-- 创建stock表
CREATE TABLE IF NOT EXISTS `autofund`.`stock` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `symbol` VARCHAR(20) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `is_index` BOOLEAN NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_symbol` (`symbol` ASC),
  UNIQUE INDEX `uk_name` (`name` ASC),
  INDEX  `idx_is_index` (`is_index` DESC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建stock_data表
CREATE TABLE IF NOT EXISTS `autofund`.`stock_data` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `stock_id` INT UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `close` DOUBLE NULL,
  `high` DOUBLE NULL,
  `low` DOUBLE NULL,
  `open` DOUBLE NULL,
  `pclose` DOUBLE NULL,
  `chg` DOUBLE NULL,
  `pchg` DOUBLE NULL,
  `voturnover` DOUBLE NULL,
  `vaturnover` DOUBLE NULL,
  `turnover` DOUBLE NULL,
  `tcap` DOUBLE NULL,
  `mcap` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_stock_id_date` (`stock_id` ASC, `date` DESC),
  INDEX `idx_date` (`date` DESC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建ma表
CREATE TABLE IF NOT EXISTS `autofund`.`ma` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `stock_id` INT UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `ma5` DOUBLE NULL,
  `ma10` DOUBLE NULL,
  `ma20` DOUBLE NULL,
  `ma30` DOUBLE NULL,
  `ma60` DOUBLE NULL,
  `ma120` DOUBLE NULL,
  `ma250` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_stock_id_date` (`stock_id` ASC, `date` DESC),
  INDEX `idx_date` (`date` DESC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建holiday表
CREATE TABLE IF NOT EXISTS`autofund`.`non_weekend_holiday` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_date` (`date` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

