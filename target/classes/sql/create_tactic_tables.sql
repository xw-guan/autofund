-- -- @author XWGuan 
-- create at 2018-03-07

-- 创建tactic相关数据库表

-- 创建gain_loss_tactic表
CREATE TABLE IF NOT EXISTS `autofund`.`gain_loss_tactic` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `activated` BOOLEAN NULL DEFAULT true,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_plan_id` (`plan_id` ASC),
  INDEX `idx_activated` (`activated` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建index_change_tactic表
CREATE TABLE IF NOT EXISTS `autofund`.`index_change_tactic` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `activated` TINYINT NULL DEFAULT 1,
  `in_trade_days` SMALLINT NULL DEFAULT -1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_plan_id` (`plan_id` ASC),
  INDEX `idx_activated` (`activated` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建ma_tactic表
CREATE TABLE IF NOT EXISTS `autofund`.`ma_tactic` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `activated` TINYINT NULL DEFAULT 1,
  `ma` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_plan_id` (`plan_id` ASC),
  INDEX `idx_activated` (`activated` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- 创建nav_change_tactic表
CREATE TABLE IF NOT EXISTS `autofund`.`nav_change_tactic` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `activated` TINYINT NULL DEFAULT 1,
  `in_trade_days` SMALLINT NULL DEFAULT -1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_plan_id` (`plan_id` ASC),
  INDEX `idx_activated` (`activated` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建period_buy_tactic表
CREATE TABLE IF NOT EXISTS `autofund`.`period_buy_tactic` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `activated` BOOLEAN NULL DEFAULT true,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_plan_id` (`plan_id` ASC),
  INDEX `idx_activated` (`activated` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- 创建profit_taking_tactic表
CREATE TABLE IF NOT EXISTS `autofund`.`profit_taking_tactic` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `plan_id` BIGINT NULL DEFAULT -1,
  `activated` BOOLEAN NULL DEFAULT true,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_plan_id` (`plan_id` ASC),
  INDEX `idx_activated` (`activated` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;