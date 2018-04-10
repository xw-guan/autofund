-- -- @author XWGuan 
-- create at 2018-03-07

-- 创建rule相关数据库表

-- 创建rule表
CREATE TABLE IF NOT EXISTS `autofund`.`rule` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tactic_id` BIGINT NULL DEFAULT -1,
  `tactic_type` VARCHAR(40) NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_tactic_id_and_type` (`tactic_id` ASC, `tactic_type` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建period_condition表
CREATE TABLE IF NOT EXISTS `autofund`.`period_condition` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tactic_id` BIGINT NULL DEFAULT -1,
  `tactic_type` VARCHAR(40) NULL,
  `period` VARCHAR(20) NULL,
  `day_of_period` SMALLINT NULL,
  `last_invest_date` DATE NULL,
  `next_invest_date` DATE NULL,
  `next_invest_date_in_theory` DATE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_tactic_id_and_type` (`tactic_id` ASC, `tactic_type` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建range_condition
CREATE TABLE IF NOT EXISTS `autofund`.`range_condition` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `rule_id` BIGINT NULL DEFAULT -1,
  `boundary_left` DOUBLE NULL,
  `boundary_right` DOUBLE NULL,
  `range_unit` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_rule_id` (`rule_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建operation
CREATE TABLE IF NOT EXISTS `autofund`.`operation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `rule_id` BIGINT NULL DEFAULT -1,
  `trade_value` DOUBLE NULL DEFAULT 0,
  `trade_unit` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_rule_id` (`rule_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
