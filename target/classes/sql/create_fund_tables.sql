-- -- @author XWGuan 
-- create at 2017-12-12

-- 创建fund相关数据库表

-- 创建fund表
CREATE TABLE IF NOT EXISTS `autofund`.`fund` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(15) NOT NULL,
  `name` VARCHAR(30) NULL,
  `pinyin_name` VARCHAR(80) NULL,
  `abbr_pinyin_name` VARCHAR(30) NULL,
  `type` VARCHAR(15) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_code` (`code` ASC),
  INDEX `idx_name` (`name` ASC),
  INDEX `idx_pinyin_name` (`pinyin_name` ASC),
  INDEX `idx_abbr_pinyin_name` (`abbr_pinyin_name` ASC),
  INDEX `idx_type` (`type` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建fund_company表
CREATE TABLE IF NOT EXISTS `autofund`.`fund_company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(15) NOT NULL,
  `sname` VARCHAR(15) NULL,
  `fname` VARCHAR(30) NULL,
  `pinyin_sname` VARCHAR(60) NULL,
  `abbr_pinyin_sname` VARCHAR(15) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_code` (`code` ASC),
  INDEX `idx_sname` (`sname` ASC),
  INDEX `idx_fname` (`fname` ASC),
  INDEX `idx_pinyin_sname` (`pinyin_sname` ASC),
  INDEX `idx_abbr_pinyin_sname` (`abbr_pinyin_sname` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建fund_manager表
CREATE TABLE IF NOT EXISTS `autofund`.`fund_manager` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(15) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `company_code` VARCHAR(15) NOT NULL,
  `company_name` VARCHAR(20) NULL,
  `current_fund_codes` VARCHAR(500) NULL,
  `current_fund_names` VARCHAR(1000) NULL,
  `career_days` VARCHAR(10) NULL,
  `current_total_asset` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_code` (`code` ASC),
  INDEX `idx_company_code` (`company_code` ASC),
  INDEX `idx_name` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建fund_history表
CREATE TABLE IF NOT EXISTS `autofund`.`fund_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `fund_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `nav` DOUBLE NOT NULL,
  `acc_nav` DOUBLE NOT NULL,
  `equity_return` DOUBLE NOT NULL,
  `unit_money` VARCHAR(40) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_fund_id_date` (`fund_id` ASC, `date` DESC),
  INDEX `idx_date` (`date` DESC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建fund_info表
CREATE TABLE IF NOT EXISTS `autofund`.`fund_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fund_id` INT NOT NULL,
  `date` DATE NULL,
  `manager_code` VARCHAR(20) NULL,
  `manager_work_time` VARCHAR(20) NULL,
  `company_code` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_fund_id` (`fund_id` ASC),
  INDEX `idx_manager_code` (`manager_code` ASC),
  INDEX `idx_company_code` (`company_code` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建asset_allocation表
CREATE TABLE IF NOT EXISTS `autofund`.`asset_allocation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fund_id` INT NOT NULL,
  `date` DATE NULL,
  `stock_pct` DOUBLE NULL,
  `debenture_pct` DOUBLE NULL,
  `cash_pct` DOUBLE NULL,
  `fund_pct` DOUBLE NULL,
  `net_asset` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_fund_id` (`fund_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建fund_position表
CREATE TABLE IF NOT EXISTS `autofund`.`fund_position` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `asset_allocation_id` INT NOT NULL,
  `security_type` TINYINT NOT NULL,
  `security_code` VARCHAR(20) NOT NULL,
  `position_pct` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_allocation_id` (`asset_allocation_id` ASC),
  INDEX `idx_security_type` (`security_type` ASC),
  INDEX `idx_security_code` (`security_code` ASC),
  INDEX `idx_position_pct` (`position_pct` DESC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


