# autofund
Autofund is a tool for automatic investment plan (AIP), providing customizable tactics, and generating trading schemes based on historical and real-time data of funds, stocks and indexes.

基金定投工具, 提供基于历史数据和实时数据的, 可定制化的定投策略. 本项目包含
- A股股票和基金历史数据库
- 定投计划和策略生成
- 虚拟交易
- 定投计划回测

## 运行环境
- JDK 1.8
- MySQL 5.7
- Tomcat 9.0
- Maven 3.5

由于大量使用了LocalDate, Lambda表达式, JDK 1.8是必须的. 其他版本的MySQL, Tomcat和Maven不确定能否正常工作.

## 使用autofund
### WEB API
#### COMMON
##### 返回数据
所有返回数据用`com.xwguan.autofund.dto.common.Result`包装, 并序列化为json字符串
```Java
public class Result<T> {
    private boolean success; // 请求是否成功
    private T data; // 请求返回的数据
    private String error; // 请求返回的错误信息
}
```

##### 日期格式
所有URL中的日期格式均为`yyyy-MM-dd`

##### 股票代码格式
默认A股股票代码格式为6位数字股票代码加交易所后缀, 上证在股票代码后加后缀`.SH`, 深证加后缀`.SZ`. 例如: 上证指数为`000001.SH`, 平安银行为`000001.SZ`. 注意URL中`.`应转码为`%2E`

#### 股票 Stock
##### 查询股票或指数
    /autofund/stock/{symbolOrName}
- GET请求
- 返回包含最新历史数据(不是实时数据)的股票, json字符串格式, 数据模型见`com.xwguan.autofund.dto.stock.LatestStockDto`
- `{symbolOrName}`为股票代码或名称, 例: 搜索000001返回包含上证指数和平安银行的列表, 搜索000001.SH返回上证指数

##### 更新股票和指数
    /autofund/stock
- POST请求
- 获取数据库中的股票列表, 从网络批量更新股票数据至最近一个交易日, 交易日当日股票数据在次日零点才能获取. 若无股票列表则会先对股票列表进行初始化.
- 可提供一个键为`useMultiThread`值为`true`的Request参数以使用多线程更新
- 初次更新股票数据可能超过2小时(待优化)

#### 基金 Fund
##### 查询基金
    /autofund/fund/{searchField}
- GET请求
- 返回包含最新历史数据(不是实时数据)的基金, json字符串格式, 数据模型见`com.xwguan.autofund.dto.fund.LatestFundDto`
- `{searchField}`为基金代码, 基金名或基金名拼音, 例: 搜索000001返回华夏成长混合

##### 更新基金
    /autofund/fund
- POST请求
- 获取数据库中的基金列表, 从网络批量更新基金数据至最近一个交易日, 交易日当日基金数据在次日零点才能获取. 若无基金列表则会先对基金列表进行初始化.
- 可提供一个键为`useMultiThread`值为`true`的Request参数以使用多线程更新
- 初次更新基金数据可能超过3小时(待优化)

#### 计划 Plan
(计划说明待补充)
##### URL参数说明
- `{planId}`为计划id

##### 获取计划模板
###### 计划模板
    /autofund/plan/templateResult/{templateCode}
- GET请求
- Autofund已经实现了一些计划作为模板, 该方法根据模板代码返回对应的计划模板, 数据模型见`com.xwguan.autofund.dto.plan.PlanDto`
- `{templateCode}`为计划模板代码, 目前已实现的包括:
  - PB: 普通定投计划(Period Buy plan)
  - MA: 均线计划(Moving Average plan)
  - GL: 盈亏计划(Gain Loss plan)
  - MAPT: 止盈均线计划(MA plan with Profit Taking)

###### 策略模板
    /autofund/templateResult/{templateCode}
- GET请求
- 该方法根据模板代码返回对应的策略模板, 数据模型见`com.xwguan.autofund.dto.plan.tactic.TacticDto`
- `{templateCode}`为持仓模板代码, 目前已实现的包括:
  - PB: 普通定投策略(Period Buy)
  - MA250: 均线策略(Moving Average in 250 trade days), 追踪250日均线
  - GL: 盈亏策略(Gain Loss), 根据账户盈亏状态计算买入卖出值
  - IC22: 指数变化策略(Index Change in 22 trade days), 追踪22个交易日的指数变化
  - NC22: 净值变化策略(Net asset value Change in 22 trade days), 追踪22个交易日的基金净值变化
  - PTPL: 计划止盈策略(Profit Taking of PLan), 计划盈利超过一定数额止盈
  - PTPST: 持仓止盈策略(Profit Taking of PoSiTion), 持仓盈利超过一定数额止盈

###### 全部策略模板
    /autofund/tactic/templateResults
- GET请求
- 列出所有计划模板

##### 获取计划
    /autofund/plan/{planId}
- GET请求
- 返回计划, 包含策略和持仓, 数据模型见`com.xwguan.autofund.dto.plan.PlanDto`
- 用于修改计划

###### 获取包含最新数据计划
    /autofund/plan/{planId}/latest
- GET请求
- 返回计划, 包含包含策略, 持仓和最新的账户历史, 数据模型见`com.xwguan.autofund.dto.plan.LatestPlanDto`

##### 创建计划
    /autofund/plan
- POST请求
- 请求参数包含json字符串, 其结构应与`com.xwguan.autofund.dto.plan.PlanDto`一致

##### 更新计划
    /autofund/plan
- PUT请求
- 只更新计划基本信息
- 请求参数包含json字符串, 其结构应与`com.xwguan.autofund.dto.plan.PlanInfoDto`一致

###### 更新计划中的策略
    /autofund/tactic/{tacticType}/{tacticId}
- PUT请求
- 更新策略类型和策略id对应的策略
- 请求参数包含json字符串, 其结构对应的dto参见`com.xwguan.autofund.dto.plan.tactic`包中的各TacticDto类
- `{tacticType}`为策略类型代码, 取值为
  - GLT: 盈亏策略
  - ICT: 指数变化策略
  - MAT: 均线策略
  - NCT: 净值变化策略
  - PBT: 定期买入策略
  - PTT: 计划止盈策略

###### 在计划中新增持仓
    /autofund/plan/{planId}/position
- POST请求
- 需要键为`fundCode`和`refIndexSymbol`的请求参数, 分别对应股票代码和参考指数

##### 删除计划
    /autofund/plan/{planId}
- DELETE请求
- 若计划账户中仍有资产则不能删除计划

##### 获取回测结果
    /autofund/plan/{planId}/backTest?from={fromDate}&to={toDate}
- GET请求
- 返回回测结果页面(未实现), Model中包含回测结果数据, 数据模型见`com.xwguan.autofund.entity.plan.backTest.PlanBackTestResult`
- `{fromDate}`为开始日期`{toDate}`为结束日期


    /autofund/plan/{planId}/backTestResult?from={fromDate}&to={toDate}
- 返回json格式的回测结果, 其余同上

##### 获取交易方案
    /autofund/plan/{planId}/tradeSchemeResult/{date}
- GET请求
- 返回给定计划在给定交易日的交易方案结果, json字符串格式, 数据模型见`com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme`
- `{date}`生成方案的日期
- 非交易返回错误信息`该日期不是交易日`


#### 管理界面
##### 状态显示
    /autofund/admin/state
(尚未实现)
##### 股票管理
    /autofund/admin/stock
- 该页面显示股票总数, 是否需要更新, 提供查询和更新功能(待完善)

##### 基金管理
    /autofund/admin/fund
- 该页面显示基金总数, 是否需要更新, 提供查询和更新功能(尚未实现)

##### 用户管理
    /autofund/admin/user
(尚未实现)

## 未来工作
1. 补全前端用户页面
1. 提升股票和基金数据更新效率
1. 插件化的策略
