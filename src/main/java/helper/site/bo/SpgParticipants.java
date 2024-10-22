package helper.site.bo;

import lombok.Data;

/**
 * @author WuYi
 */
@Data
public class SpgParticipants {
	/**
	 * "All-In"信号
	 */
	private int allInPings;

	/**
	 * "Assist Me"信号
	 */
	private int assistMePings;

	/**
	 * 助攻次数
	 */
	private int assists;

	/**
	 * 击杀男爵次数
	 */
	private int baronKills;

	/**
	 * 基本信号
	 */
	private int basicPings;

	/**
	 * 悬赏等级
	 */
	private int bountyLevel;

	/**
	 * 英雄经验
	 */
	private int champExperience;

	/**
	 * 英雄等级
	 */
	private int champLevel;

	/**
	 * 英雄ID
	 */
	private int championId;

	/**
	 * 英雄名字
	 */
	private String championName;

	/**
	 * 英雄形态
	 */
	private int championTransform;

	/**
	 * 指令信号
	 */
	private int commandPings;

	/**
	 * 购买消耗品次数
	 */
	private int consumablesPurchased;

	/**
	 * 对建筑物造成的伤害
	 */
	private int damageDealtToBuildings;

	/**
	 * 对目标造成的伤害
	 */
	private int damageDealtToObjectives;

	/**
	 * 对防御塔造成的伤害
	 */
	private int damageDealtToTurrets;

	/**
	 * 承受的伤害
	 */
	private int damageSelfMitigated;

	/**
	 * 危险信号
	 */
	private int dangerPings;

	/**
	 * 死亡次数
	 */
	private int deaths;

	/**
	 * 放置的探测守卫数
	 */
	private int detectorWardsPlaced;

	/**
	 * 双杀次数
	 */
	private int doubleKills;

	/**
	 * 击杀巨龙次数
	 */
	private int dragonKills;

	/**
	 * 进度是否合格
	 */
	private boolean eligibleForProgression;

	/**
	 * 敌方消失信号
	 */
	private int enemyMissingPings;

	/**
	 * 敌方视野信号
	 */
	private int enemyVisionPings;

	/**
	 * 首杀助攻
	 */
	private boolean firstBloodAssist;

	/**
	 * 首杀
	 */
	private boolean firstBloodKill;

	/**
	 * 首塔助攻
	 */
	private boolean firstTowerAssist;

	/**
	 * 首塔击杀
	 */
	private boolean firstTowerKill;

	/**
	 * 比赛是否早早投降结束
	 */
	private boolean gameEndedInEarlySurrender;

	/**
	 * 比赛是否投降结束
	 */
	private boolean gameEndedInSurrender;

	/**
	 * "Get Back"信号
	 */
	private int getBackPings;

	/**
	 * 获得金币
	 */
	private int goldEarned;

	/**
	 * 花费金币
	 */
	private int goldSpent;

	/**
	 * "Hold"信号
	 */
	private int holdPings;

	/**
	 * 个人位置
	 */
	private String individualPosition;

	/**
	 * 击杀的防御塔数量
	 */
	private int inhibitorKills;

	/**
	 * 击破的防御塔数
	 */
	private int inhibitorTakedowns;

	/**
	 * 失去的防御塔数
	 */
	private int inhibitorsLost;

	/**
	 * 物品1
	 */
	private int item0;

	/**
	 * 物品2
	 */
	private int item1;

	/**
	 * 物品3
	 */
	private int item2;

	/**
	 * 物品4
	 */
	private int item3;

	/**
	 * 物品5
	 */
	private int item4;

	/**
	 * 物品6
	 */
	private int item5;

	/**
	 * 物品7
	 */
	private int item6;

	/**
	 * 购买的物品数
	 */
	private int itemsPurchased;

	/**
	 * 连续击杀次数
	 */
	private int killingSprees;

	/**
	 * 击杀次数
	 */
	private int kills;

	/**
	 * 路线
	 */
	private String lane;

	/**
	 * 最高暴击伤害
	 */
	private int largestCriticalStrike;

	/**
	 * 最长连杀
	 */
	private int largestKillingSpree;

	/**
	 * 最大多杀次数
	 */
	private int largestMultiKill;

	/**
	 * 最长存活时间
	 */
	private int longestTimeSpentLiving;

	/**
	 * 魔法伤害
	 */
	private int magicDamageDealt;

	/**
	 * 对英雄造成的魔法伤害
	 */
	private int magicDamageDealtToChampions;

	/**
	 * 承受的魔法伤害
	 */
	private int magicDamageTaken;

	/**
	 * "Need Vision"信号
	 */
	private int needVisionPings;

	/**
	 * 击杀的中立小兵数
	 */
	private int neutralMinionsKilled;

	/**
	 * 击杀的水晶数
	 */
	private int nexusKills;

	/**
	 * 失去的水晶数
	 */
	private int nexusLost;

	/**
	 * 击破的水晶数
	 */
	private int nexusTakedowns;

	/**
	 * 盗取目标数
	 */
	private int objectivesStolen;

	/**
	 * 盗取目标助攻数
	 */
	private int objectivesStolenAssists;

	/**
	 * "On My Way"信号
	 */
	private int onMyWayPings;

	/**
	 * 参赛者ID
	 */
	private int participantId;

	/**
	 * 五杀次数
	 */
	private int pentaKills;

	/**
	 * 物理伤害
	 */
	private int physicalDamageDealt;

	/**
	 * 对英雄造成的物理伤害
	 */
	private int physicalDamageDealtToChampions;

	/**
	 * 承受的物理伤害
	 */
	private int physicalDamageTaken;

	/**
	 * 排名
	 */
	private int placement;

	/**
	 * 玩家增益1
	 */
	private int playerAugment1;

	/**
	 * 玩家增益2
	 */
	private int playerAugment2;

	/**
	 * 玩家增益3
	 */
	private int playerAugment3;

	/**
	 * 玩家增益4
	 */
	private int playerAugment4;

	/**
	 * 玩家增益5
	 */
	private int playerAugment5;

	/**
	 * 玩家增益6
	 */
	private int playerAugment6;

	/**
	 * 玩家子团队ID
	 */
	private int playerSubteamId;

	/**
	 * 头像图标
	 */
	private int profileIcon;

	/**
	 * "Push"信号
	 */
	private int pushPings;

	/**
	 * PUUID
	 */
	private String puuid;

	/**
	 * 四杀次数
	 */
	private int quadraKills;

	/**
	 * 游戏名字
	 */
	private String riotIdGameName;

	/**
	 * 游戏标识
	 */
	private String riotIdTagline;

	/**
	 * 角色
	 */
	private String role;

	/**
	 * 购买的侦查守卫数
	 */
	private int sightWardsBoughtInGame;

	/**
	 * 技能1使用次数
	 */
	private int spell1Casts;

	/**
	 * 技能1 ID
	 */
	private int spell1Id;

	/**
	 * 技能2使用次数
	 */
	private int spell2Casts;

	/**
	 * 技能2 ID
	 */
	private int spell2Id;

	/**
	 * 技能3使用次数
	 */
	private int spell3Casts;

	/**
	 * 技能3 ID
	 */
	private int spell3Id;

	/**
	 * 技能4使用次数
	 */
	private int spell4Casts;

	/**
	 * 技能4 ID
	 */
	private int spell4Id;

	/**
	 * 子团队排名
	 */
	private int subteamPlacement;

	/**
	 * 召唤师技能1使用次数
	 */
	private int summoner1Casts;

	/**
	 * 召唤师技能2使用次数
	 */
	private int summoner2Casts;

	/**
	 * 召唤师ID
	 */
	private long summonerId;

	/**
	 * 召唤师等级
	 */
	private int summonerLevel;

	/**
	 * 召唤师名字
	 */
	private String summonerName;

	/**
	 * 团队是否早早投降
	 */
	private boolean teamEarlySurrendered;

	/**
	 * 团队ID
	 */
	private int teamId;

	/**
	 * 团队位置
	 */
	private String teamPosition;

	/**
	 * 对他人控制总时间
	 */
	private int timeCCingOthers;

	/**
	 * 游戏时长
	 */
	private int timePlayed;

	/**
	 * 击杀的友方打野小兵数
	 */
	private int totalAllyJungleMinionsKilled;

	/**
	 * 总伤害
	 */
	private int totalDamageDealt;

	/**
	 * 对英雄总伤害
	 */
	private int totalDamageDealtToChampions;

	/**
	 * 对友方护盾总时间
	 */
	private int totalDamageShieldedOnTeammates;

	/**
	 * 承受的总伤害
	 */
	private int totalDamageTaken;

	/**
	 * 击杀的敌方打野小兵数
	 */
	private int totalEnemyJungleMinionsKilled;

	/**
	 * 总治疗量
	 */
	private int totalHeal;

	/**
	 * 对友方总治疗量
	 */
	private int totalHealsOnTeammates;

	/**
	 * 击杀的总小兵数
	 */
	private int totalMinionsKilled;

	/**
	 * 控制总时间
	 */
	private int totalTimeCCDealt;

	/**
	 * 总死亡时间
	 */
	private int totalTimeSpentDead;

	/**
	 * 总治疗单位数
	 */
	private int totalUnitsHealed;

	/**
	 * 三杀次数
	 */
	private int tripleKills;

	/**
	 * 真伤
	 */
	private int trueDamageDealt;

	/**
	 * 对英雄造成的真伤
	 */
	private int trueDamageDealtToChampions;

	/**
	 * 承受的真伤
	 */
	private int trueDamageTaken;

	/**
	 * 击杀的防御塔数
	 */
	private int turretKills;

	/**
	 * 击破的防御塔数
	 */
	private int turretTakedowns;

	/**
	 * 失去的防御塔数
	 */
	private int turretsLost;

	/**
	 * "Unreal"信号
	 */
	private int unrealKills;

	/**
	 * "Vision Cleared"信号
	 */
	private int visionClearedPings;

	/**
	 * 视野得分
	 */
	private int visionScore;

	/**
	 * 购买的控制守卫数
	 */
	private int visionWardsBoughtInGame;

	/**
	 * 击杀的守卫数
	 */
	private int wardsKilled;

	/**
	 * 放置的守卫数
	 */
	private int wardsPlaced;

	/**
	 * 比赛是否获胜
	 */
	private boolean win;

}
