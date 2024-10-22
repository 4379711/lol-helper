package helper.site.bo;

import lombok.Data;

/**
 * 比赛数据
 *
 * @author WuYi
 */
@Data
public class Stats {
	/**
	 * 助攻
	 **/
	private Integer assists;
	/**
	 * 提前投降
	 **/
	private Boolean causedEarlySurrender;
	/**
	 * Champion等级
	 **/
	private Integer champLevel;
	/**
	 * 战斗玩家分数
	 **/
	private Integer combatPlayerScore;
	/**
	 * 对目标造成的伤害
	 **/
	private Integer damageDealtToObjectives;
	/**
	 * 对塔造成的伤害
	 **/
	private Integer damageDealtToTurrets;
	/**
	 * 自我缓解的伤害
	 **/
	private Integer damageSelfMitigated;
	/**
	 * 死亡
	 **/
	private Integer deaths;
	/**
	 * 双杀
	 **/
	private Integer doubleKills;
	/**
	 * 提前投降的同谋
	 **/
	private Boolean earlySurrenderAccomplice;
	/**
	 * 一血助攻
	 **/
	private Boolean firstBloodAssist;
	/**
	 * 一血击杀
	 **/
	private Boolean firstBloodKill;
	/**
	 * 第一次破坏抑制器助攻
	 **/
	private Boolean firstInhibitorAssist;
	/**
	 * 第一次破坏抑制器击杀
	 **/
	private Boolean firstInhibitorKill;
	/**
	 * 第一次破坏塔助攻
	 **/
	private Boolean firstTowerAssist;
	/**
	 * 第一次破坏塔击杀
	 **/
	private Boolean firstTowerKill;
	/**
	 * 游戏因早投而结束
	 **/
	private Boolean gameEndedInEarlySurrender;
	/**
	 * 游戏因投降而结束
	 **/
	private Boolean gameEndedInSurrender;
	/**
	 * 赚取的金币
	 **/
	private Integer goldEarned;
	/**
	 * 消耗的金币
	 **/
	private Integer goldSpent;
	/**
	 * 破坏抑制器的次数
	 **/
	private Integer inhibitorKills;
	/**
	 * 物品0
	 **/
	private Integer item0;
	/**
	 * 物品1
	 **/
	private Integer item1;
	/**
	 * 物品2
	 **/
	private Integer item2;
	/**
	 * 物品3
	 **/
	private Integer item3;
	/**
	 * 物品4
	 **/
	private Integer item4;
	/**
	 * 物品5
	 **/
	private Integer item5;
	/**
	 * 物品6
	 **/
	private Integer item6;
	/**
	 * 连杀
	 **/
	private Integer killingSprees;
	/**
	 * 击杀
	 **/
	private Integer kills;
	/**
	 * 最大临界击杀
	 **/
	private Integer largestCriticalStrike;
	/**
	 * 最大连续杀人数
	 **/
	private Integer largestKillingSpree;
	/**
	 * 最多杀人数
	 **/
	private Integer largestMultiKill;
	/**
	 * 最长生存时间
	 **/
	private Integer longestTimeSpentLiving;
	/**
	 * 对奥术伤害
	 **/
	private Integer magicDamageDealt;
	/**
	 * 对英雄的奥术伤害
	 **/
	private Integer magicDamageDealtToChampions;
	/**
	 * 承受的魔法伤害
	 **/
	private Integer magicalDamageTaken;
	/**
	 * 中立小兵击杀
	 **/
	private Integer neutralMinionsKilled;
	/**
	 * 敌方丛林中立单位击杀
	 **/
	private Integer neutralMinionsKilledEnemyJungle;
	/**
	 * 己方丛林的中立单位击杀
	 **/
	private Integer neutralMinionsKilledTeamJungle;
	/**
	 * 目标玩家得分
	 **/
	private Integer objectivePlayerScore;
	/**
	 * 参与者ID
	 **/
	private Integer participantId;
	/**
	 * 五连杀
	 **/
	private Integer pentaKills;
	/**
	 * 天赋0
	 **/
	private Integer perk0;
	/**
	 * 天赋0变量1
	 **/
	private Integer perk0Var1;
	/**
	 * 天赋0变量2
	 **/
	private Integer perk0Var2;
	/**
	 * 天赋0变量3
	 **/
	private Integer perk0Var3;
	/**
	 * 天赋1
	 **/
	private Integer perk1;
	/**
	 * 天赋1变量1
	 **/
	private Integer perk1Var1;
	/**
	 * 天赋1变量2
	 **/
	private Integer perk1Var2;
	/**
	 * 天赋1变量3
	 **/
	private Integer perk1Var3;
	/**
	 * 天赋2
	 **/
	private Integer perk2;
	/**
	 * 天赋2变量1
	 **/
	private Integer perk2Var1;
	/**
	 * 天赋2变量2
	 **/
	private Integer perk2Var2;
	/**
	 * 天赋2变量3
	 **/
	private Integer perk2Var3;
	/**
	 * 天赋3
	 **/
	private Integer perk3;
	/**
	 * 天赋3变量1
	 **/
	private Integer perk3Var1;
	/**
	 * 天赋3变量2
	 **/
	private Integer perk3Var2;
	/**
	 * 天赋3变量3
	 **/
	private Integer perk3Var3;
	/**
	 * 天赋4
	 **/
	private Integer perk4;
	/**
	 * 天赋4变量1
	 **/
	private Integer perk4Var1;
	/**
	 * 天赋4变量2
	 **/
	private Integer perk4Var2;
	/**
	 * 天赋4变量3
	 **/
	private Integer perk4Var3;
	/**
	 * 天赋5
	 **/
	private Integer perk5;
	/**
	 * 天赋5变量1
	 **/
	private Integer perk5Var1;
	/**
	 * 天赋5变量2
	 **/
	private Integer perk5Var2;
	/**
	 * 天赋5变量3
	 **/
	private Integer perk5Var3;
	/**
	 * 天赋主样式
	 **/
	private Integer perkPrimaryStyle;
	/**
	 * 天赋副样式
	 **/
	private Integer perkSubStyle;
	/**
	 * 造成的物理伤害
	 **/
	private Integer physicalDamageDealt;
	/**
	 * 对英雄造成的物理伤害
	 **/
	private Integer physicalDamageDealtToChampions;
	/**
	 * 承受的物理伤害
	 **/
	private Integer physicalDamageTaken;
	/**
	 * 玩家援助1
	 **/
	private Integer playerAugment1;
	/**
	 * 玩家援助2
	 **/
	private Integer playerAugment2;
	/**
	 * 玩家援助3
	 **/
	private Integer playerAugment3;
	/**
	 * 玩家援助4
	 **/
	private Integer playerAugment4;
	/**
	 * 玩家得分0
	 **/
	private Integer playerScore0;
	/**
	 * 玩家得分1
	 **/
	private Integer playerScore1;
	/**
	 * 玩家得分2
	 **/
	private Integer playerScore2;
	/**
	 * 玩家得分3
	 **/
	private Integer playerScore3;
	/**
	 * 玩家得分4
	 **/
	private Integer playerScore4;
	/**
	 * 玩家得分5
	 **/
	private Integer playerScore5;
	/**
	 * 玩家得分6
	 **/
	private Integer playerScore6;
	/**
	 * 玩家得分7
	 **/
	private Integer playerScore7;
	/**
	 * 玩家得分8
	 **/
	private Integer playerScore8;
	/**
	 * 玩家得分9
	 **/
	private Integer playerScore9;
	/**
	 * 玩家分队ID
	 **/
	private Integer playerSubteamId;
	/**
	 * 四连杀次数
	 **/
	private Integer quadraKills;
	/**
	 * 游戏中购买视野守卫的次数
	 **/
	private Integer sightWardsBoughtInGame;
	/**
	 * 队伍位置
	 **/
	private Integer subteamPlacement;
	/**
	 * 早期投降的队伍
	 **/
	private Boolean teamEarlySurrendered;
	/**
	 * 对其他人的控制时间
	 **/
	private Integer timeCCingOthers;
	/**
	 * 总伤害量
	 **/
	private Integer totalDamageDealt;
	/**
	 * 对英雄的总伤害量
	 **/
	private Integer totalDamageDealtToChampions;
	/**
	 * 承受的总伤害量
	 **/
	private Integer totalDamageTaken;
	/**
	 * 总治疗量
	 **/
	private Integer totalHeal;
	/**
	 * 总兵线小兵杀死数量
	 **/
	private Integer totalMinionsKilled;
	/**
	 * 玩家总得分
	 **/
	private Integer totalPlayerScore;
	/**
	 * 总得分排名
	 **/
	private Integer totalScoreRank;
	/**
	 * 造成的总控制时间
	 **/
	private Integer totalTimeCrowdControlDealt;
	/**
	 * 治疗的单位数量
	 **/
	private Integer totalUnitsHealed;
	/**
	 * 三连击
	 **/
	private Integer tripleKills;
	/**
	 * 造成的真实伤害
	 **/
	private Integer trueDamageDealt;
	/**
	 * 对英雄造成的真实伤害
	 **/
	private Integer trueDamageDealtToChampions;
	/**
	 * 承受的真实伤害
	 **/
	private Integer trueDamageTaken;
	/**
	 * 推塔数量
	 **/
	private Integer turretKills;
	/**
	 * 极限击杀
	 **/
	private Integer unrealKills;
	/**
	 * 视野评分
	 **/
	private Integer visionScore;
	/**
	 * 游戏中购买的视野守卫数量
	 **/
	private Integer visionWardsBoughtInGame;
	/**
	 * 摧毁的守卫数量
	 **/
	private Integer wardsKilled;
	/**
	 * 放置的守卫数量
	 **/
	private Integer wardsPlaced;
	/**
	 * 是否获胜
	 **/
	private Boolean win;
}
