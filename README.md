# lol-helper

- 英雄联盟助手,主要使用LCU API和SGP API,**未修改**客户端内容,**理论上不会封号!**,另外附上官方文档
- [拳头文档](https://developer.riotgames.com/docs/lol/)
- [lcu](https://hextechdocs.dev/tag/lcu/)
- [版本英雄游戏数据](https://developer.riotgames.com/docs/lol#data-dragon_champions)
- [CommunityDragon游戏资源](https://github.com/CommunityDragon/Docs/blob/master/assets.md)
- [在线swagger](http://www.mingweisamuel.com/lcu-schema/tool/#/)
- 导出swagger V2:https://127.0.0.1:2999/swagger/v2/swagger.json
- 导出swagger V3:https://127.0.0.1:2999/swagger/v3/openapi.json

## 功能说明

- 修改段位(此功能所有好友可见修改后的段位)
- 修改生涯背景图(即使没有皮肤也可更换,更换后永久生效)
- 修改游戏在线状态(游戏内如果改变状态,会覆盖本工具修改的状态)
- 自动秒选英雄,指定英雄后自动生效,取消指定后失效
- 自动禁用英雄,指定英雄后自动生效,取消指定后失效
- 自动寻找对局
- 自动接受对局(无需点击接受对局,**可以排队时候玩手机了**)
- 自动开始下一局(可以跳过结算等待页面)
- 掉线自动重连(有时候会游戏崩溃意外掉线,这些年很少发生游戏崩溃问题)
- ~~盲仔光速摸眼,即(眼闪W三键),默认设置D=闪现,4=眼,w=金钟罩;同理,可自定义其他英雄的连招等~~(由于xxx原因,已下架这个功能)
- ~~挂机模式,控制英雄乱走,防止掉线~~(由于xxx原因,已下架这个功能)
- 选英雄界面,提示红蓝双方
- 查看双方所有人的近期战绩,并计算得分,添加大神和牛马称谓,一键发送到聊天框
- **游戏内一键喊话,对喷**
- **游戏内一键鼓励队友,彩虹屁**
- **设置炫彩皮肤(没有原皮肤但有炫彩皮肤的情况下,此功能可以突破游戏限制)**(
  TX屏蔽了此功能,屏蔽前设置过并且有独立原画炫彩的部分人,仍然可以使用)
- 战绩查询(即使设置了隐藏生涯,可以查询)
- 选择英雄时 队友战绩查看 左侧显示最近改20把胜率,所在红蓝方 点击队友头像查看详情数据

## 使用说明

##### 安装步骤

###### 方式一(推荐)

1. 加入下方QQ群,在群文件里获取已经打包好的软件
2. 对着启动图标`右键->属性->兼容性->以管理员身份运行此程序->确定`,建议右键->发送到桌面快捷方式
3. 先启动游戏,双击桌面图标启动软件

###### 方式二(手动更新)

1. 点击[GITHUB Releases](https://github.com/4379711/lol-helper/releases)下载最新版本的`lol-helper.jar`
2. cmd执行 `java -jar lol-helper.jar` 命令进行启动

##### 项目说明

- 一键喊话功能,使用模拟按键的方式实现
- 使用模拟按键的方式实现的几个功能,说不定哪天游戏官方会认为**违规**,目前来看,本人使用两年多无任何负面影响
- 喷人喷多了,被举报,会被禁言,这是游戏内设定,和本工具无关
- 软件安装目录下,首次运行后,会产生一个`words.txt`,里面内置了喷人语录,可自行修改,也可在软件内的喷人语录里修改
- 发送战绩有时候会失效,大概率是因为玩家名字和数字**被游戏屏蔽**,小概率是因为某些词汇被屏蔽,例如`牛马`现已被屏蔽
- 将某人拉入**黑名单**,下次遇到会提示此人
- 功能失效可能是**未以管理员身份运行程序**
  - 方式一: 对着图标右键 -> 以管理员身份运行
  - 方式二(推荐): 对着图标右键 -> 属性 -> 以管理员身份运行此程序 -> 确定 -> 以后直接双击图标启动程序即可

##### 快捷键说明

- F1:根据队友得分,并选出大神和傻鸟是谁,发送到聊天框,可在选择英雄界面使用
- F2:根据对方得分,并选出大神和傻鸟是谁,发送到聊天框,只能在游戏内使用(因为选英雄界面看不到对面玩家)
- HOME(问候家人,所以用了home):发送垃圾话(如果修改了垃圾话文件,可以取消勾选,再打开勾选,即可重新加载垃圾话)
- END:发送鼓励的话,自动从网络上获取彩虹屁,并自动发送到聊天框
- DELETE:发送垃圾话时可能会被系统屏蔽,此时标记后会把这个垃圾话打印到结果面板里,方便后续对照修改
- PAUSE:暂停/开启自动操作,例如自动寻找对局,自动跳过结算界面,一键连招等
- 自定义快捷键

## 软件截图

![主界面](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/main.png)
![段位和状态](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/01.jpg)
![自动接受对局](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/02.jpg)
![房间内发送文字](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/03.jpg)
![游戏内发送文字](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/04.jpg)
![喷人语录](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/05.png)
![炫彩皮肤](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/6.png)
![侧边栏](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/10.png)
![战绩查询](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/08.png)
![黑名单界面](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/09.png)

## 免责声明

- 本项目仅供学习,未经许可,不得用作商业用途,不得用作违法行为!!!

## 联系

> 加QQ群,~~群号882050965~~已满 二群1001245686,或者用qq扫描下方二维码
![QQ群](https://github.com/4379711/lol-helper/raw/master/src/main/resources/assets/QR-Code.jpg)