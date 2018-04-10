-- -- @author XWGuan 
-- create at 2018-03-07

-- 创建plan相关数据库表

-- 创建plan表
CREATE TABLE IF NOT EXISTS `autofund`.`plan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NULL DEFAULT -1,
  `name` VARCHAR(30) NULL DEFAULT 'Default Plan',
  `execution_mode` VARCHAR(20) DEFAULT 'IN_APP',
  `is_activated` BOOLEAN NULL DEFAULT true,
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id` ASC),
  INDEX `idx_is_activated` (`is_activated` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建plan_history表
CREATE TABLE IF NOT EXISTS `autofund`.`plan_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `start_date` DATE NULL,
  `history_date` DATE NULL,
  `plan_state` VARCHAR(20) NULL,
  `plan_period_income` DOUBLE NULL DEFAULT 0,
  `plan_total_income` DOUBLE NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `idx_plan_id` (`plan_id` ASC),
  INDEX `idx_plan_state` (`plan_state` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建position表
CREATE TABLE IF NOT EXISTS `autofund`.`position` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `fund_id` INT NULL DEFAULT -1,
  `ref_index_id` INT NULL DEFAULT -1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_plan_id_fund_id` (`plan_id` ASC, `fund_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;