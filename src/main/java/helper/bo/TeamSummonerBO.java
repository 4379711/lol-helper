package helper.bo;

import helper.frame.bo.ChampionWin;
import lombok.Data;

import javax.swing.*;
import java.util.List;

/**
 * 展示侧边栏队友数据
 *
 * @author WuYi
 */
@Data
public class TeamSummonerBO {
	private String puuid;
	/**
	 * 段位
	 */
	private SGPRank rank;
	/**
	 * 胜率
	 */
	private String winRate;
	/**
	 * 场均击杀
	 */
	private String avgDeath;
	/**
	 * 场均死亡
	 */
	private String avgKills;
	/**
	 * 召唤师等级
	 */
	private int level;
	/**
	 * 召唤师姓名
	 */
	private String name;
	/**
	 * 召唤师尾标
	 */
	private String tagLine;
	/**
	 * 召唤师头像id
	 */
	private int profileIconId;
	/**
	 * 召唤师头像id
	 */
	private ImageIcon profileIcon;
	/**
	 * 是否隐藏战绩
	 */
	private boolean privacy;
	/**
	 * 召唤师近期战绩
	 */
	private List<SpgProductsMatchHistoryBO> matchHistory;
	/**
	 * 英雄胜率
	 */
	private List<ChampionWin> championWinList;
	/**
	 * 所在红蓝方
	 */
	private String mapSide;
	/**
	 * 最近一把是否胜利
	 */
	private boolean successiveWin;
	/**
	 * 连胜连败的场次
	 */
	private int successiveCount;
}
