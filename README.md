# autofund

Autofund is a tool for automatic investment plan (AIP), providing customizable tactics, and generating trade schemes based on historical and real-time data of funds, stocks and indexes.

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

由于大量使用了LocalDate, lamda, JDK 1.8是必须的. 其他版本的MySQL, Tomcat和Maven不确定能否正常工作.

