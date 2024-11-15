package helper.frame.utils;

import helper.bo.PerkBO;
import helper.bo.SpgParticipants;
import helper.bo.Stats;
import helper.bo.Teams;
import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.enums.GameTypeEnum;
import helper.enums.ImageEnum;
import helper.frame.bo.ScoreLevelBO;
import helper.frame.constant.GameConstant;
import helper.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 对局战绩工具类
 */
@Slf4j
public class MatchHistoryUtil {

	/**
	 * 获取道具技能图标
	 *
	 * @param id 道具id
	 * @return ImageIcon 图标
	 */
	public static ImageIcon getItemImageIcon(Integer id, Integer itemIconWidth, Integer itemIconHeight) {
		if (id != 0) {
			try {
				String iconPath = GameDataCache.itemList.stream().filter(item -> item.getId().equals(id)).findFirst().get().getIconPath();
				return new ImageIcon(AppCache.api.geImageByPath(iconPath).getScaledInstance(itemIconWidth, itemIconHeight, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				log.error("未检测到图像");
				new ImageIcon(new BufferedImage(itemIconWidth, itemIconHeight, BufferedImage.TYPE_INT_ARGB));
			}
		}
		return new ImageIcon(new BufferedImage(itemIconWidth, itemIconHeight, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * 获取符文图标
	 *
	 * @param id 道具id
	 * @return ImageIcon 图标
	 */
	public static ImageIcon getPerkImageIcon(Integer id, Integer perkIconWidth, Integer perkIconHeight) {
		if (id != 0) {
			try {
				String iconPath = GameDataCache.perkList.stream().filter(perk -> perk.getId().equals(id)).findFirst().get().getIconPath();
				return new ImageIcon(AppCache.api.geImageByPath(iconPath).getScaledInstance(perkIconWidth, perkIconHeight, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				log.error("未检测到图像");
				new ImageIcon(new BufferedImage(perkIconWidth, perkIconHeight, BufferedImage.TYPE_INT_ARGB));
			}
		}
		return new ImageIcon(new BufferedImage(perkIconWidth, perkIconHeight, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * 获取基石符文图标
	 *
	 * @param id 道具id
	 * @return ImageIcon 图标
	 */
	public static ImageIcon getPerkStyleImageIcon(Integer id, Integer perkStyleIconWidth, Integer perkStyleIconHeight) {
		if (id != 0) {
			try {
				String iconPath = GameDataCache.perkStyleList.stream().filter(perk -> perk.getId().equals(id)).findFirst().get().getIconPath();
				return new ImageIcon(AppCache.api.geImageByPath(iconPath).getScaledInstance(perkStyleIconWidth, perkStyleIconHeight, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				log.error("未检测到图像");
				new ImageIcon(new BufferedImage(perkStyleIconWidth, perkStyleIconHeight, BufferedImage.TYPE_INT_ARGB));
			}
		}
		return new ImageIcon(new BufferedImage(perkStyleIconWidth, perkStyleIconHeight, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * 获取符文数据
	 *
	 * @param stats 玩家数据
	 */
	public static String getPerkData(Stats stats) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body>");
		if (stats.getPerk0() != 0) {
			PerkBO perk0 = GameDataCache.perkList.stream().filter(perk -> perk.getId().equals(stats.getPerk0())).findFirst().get();
			buffer.append(perk0.getName());
			String temp = "";
			for (String s : perk0.getEndOfGameStatDescs()) {
				temp = s;
				if (s.contains("@eogvar1@")) {
					temp = temp.replaceAll("@eogvar1@", stats.getPerk0Var1().toString());
				}
				if (s.contains("@eogvar2@")) {
					temp = temp.replaceAll("@eogvar2@", stats.getPerk0Var2().toString());
				}
				if (s.contains("@eogvar3@")) {
					temp = temp.replaceAll("@eogvar3@", stats.getPerk0Var3().toString());
				}
			}
			buffer.append(temp).append("<hr>");
		}
		if (stats.getPerk1() != 0) {
			PerkBO perk1 = GameDataCache.perkList.stream().filter(perk -> perk.getId().equals(stats.getPerk1())).findFirst().get();
			buffer.append(perk1.getName());
			String temp = "";
			for (String s : perk1.getEndOfGameStatDescs()) {
				temp = s;
				if (s.contains("@eogvar1@")) {
					temp = temp.replaceAll("@eogvar1@", stats.getPerk1Var1().toString());
				}
				if (s.contains("@eogvar2@")) {
					temp = temp.replaceAll("@eogvar2@", stats.getPerk1Var2().toString());
				}
				if (s.contains("@eogvar3@")) {
					temp = temp.replaceAll("@eogvar3@", stats.getPerk1Var3().toString());
				}
			}
			buffer.append(temp).append("<hr>");
		}
		if (stats.getPerk2() != 0) {
			PerkBO perk2 = GameDataCache.perkList.stream().filter(perk -> perk.getId().equals(stats.getPerk2())).findFirst().get();
			buffer.append(perk2.getName());
			String temp = "";
			for (String s : perk2.getEndOfGameStatDescs()) {
				temp = s;
				if (s.contains("@eogvar1@")) {
					temp = temp.replaceAll("@eogvar1@", stats.getPerk2Var1().toString());
				}
				if (s.contains("@eogvar2@")) {
					temp = temp.replaceAll("@eogvar2@", stats.getPerk2Var2().toString());
				}
				if (s.contains("@eogvar3@")) {
					temp = temp.replaceAll("@eogvar3@", stats.getPerk2Var3().toString());
				}
			}
			buffer.append(temp).append("<hr>");
		}
		buffer.append("</body></html>");
		return buffer.toString();
	}

	/**
	 * 获取召唤师技能图标
	 *
	 * @param id 召唤师技能图标
	 * @return ImageIcon 图标
	 */
	public static ImageIcon getSummonerSpellImageIcon(Integer id, Integer spellIconWidth, Integer spellIconHeight) {
		if (id != 0) {
			try {
				String iconPath = GameDataCache.summonerSpellsList.stream().filter(item -> item.getId().equals(id)).findFirst().get().getIconPath();
				return new ImageIcon(AppCache.api.geImageByPath(iconPath).getScaledInstance(spellIconWidth, spellIconHeight, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				log.error("未检测到图像");
				new ImageIcon(new BufferedImage(spellIconWidth, spellIconHeight, BufferedImage.TYPE_INT_ARGB));
			}
		}
		return new ImageIcon(new BufferedImage(spellIconWidth, spellIconHeight, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * 查看队伍是否获胜
	 *
	 * @param teamId 本人的队伍ID
	 * @param teams  双方队伍
	 * @return 获胜值
	 */
	public static String getWin(Integer teamId, List<Teams> teams) {
		Optional<Teams> optional = teams.stream().filter(team -> team.getTeamId().equals(teamId)).findFirst();
		if (optional.isPresent()) {
			String win = optional.get().getWin();
			if (win.equals("Win")) {
				return "胜利";
			} else if (win.equals("Fail")) {
				return "失败";
			}
			return win;
		} else {
			log.error("未读取到胜利队伍");
			return "";
		}
	}

	/**
	 * 获取指定玩家的英雄图标
	 *
	 * @param id                 英雄ID
	 * @param championIconWidth  英雄图标高
	 * @param championIconHeight 英雄图标宽
	 * @param imageEnum          图片新装
	 * @return 图片
	 */

	public static ImageIcon getChampionIcon(Integer id, Integer championIconWidth, Integer championIconHeight, ImageEnum imageEnum) {
		if (id != 0) {
			if (imageEnum == ImageEnum.ROUND) {
				try {
					return new ImageIcon(ImageUtil.makeRoundedCorner(AppCache.api.getChampionIcons(id).getScaledInstance(championIconWidth, championIconHeight, Image.SCALE_REPLICATE)));
				} catch (IOException e) {
					return new ImageIcon(new BufferedImage(championIconWidth, championIconHeight, BufferedImage.TYPE_INT_ARGB));
				}
			} else if (imageEnum == ImageEnum.SQUARE) {
				try {
					return new ImageIcon(AppCache.api.getChampionIcons(id).getScaledInstance(championIconWidth, championIconHeight, Image.SCALE_REPLICATE));
				} catch (IOException e) {
					return new ImageIcon(new BufferedImage(championIconWidth, championIconHeight, BufferedImage.TYPE_INT_ARGB));
				}
			}
		}

		return new ImageIcon(new BufferedImage(championIconWidth, championIconHeight, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * 获取背景色
	 */
	public static Color getBackGroundColor(boolean win) {
		if (win) {
			//半透明绿色
			return new Color(35, 53, 92);
		} else {
			//半透明红色
			return new Color(101, 46, 56);
		}
	}

	/**
	 * 该游戏模式是否能查看详情对局数据
	 */
	public static boolean isQueryHistoryDetail(Integer queueId) {
		GameTypeEnum typeEnum = GameTypeEnum.valueOf(queueId);
		switch (typeEnum) {
			case ARAM, RANK_FLEX, RANK_SOLO -> {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取段位
	 *
	 * @param puuid 玩家puuid
	 */

	public static String getRanked(String puuid) {
		String rank = null;
		try {
			rank = AppCache.api.getRank(puuid).getHighestCurrentSeasonReachedTierSR();
		} catch (IOException e) {
			log.error("获取段位错误");
		}
		return GameConstant.RANK.get(rank);
	}

	/**
	 * 计算评分返回列表
	 * 会用团战击杀参与率、伤害占比、承伤占比、治疗占比、视野评分占比、控制占比来计算评分
	 */
	public static Map<Integer, List<ScoreLevelBO>> getScoreLevelList(List<SpgParticipants> teamOne, List<SpgParticipants> teamTwo) {
		ArrayList<ScoreLevelBO> scoreLevelList = new ArrayList<>();
		if (teamOne != null) {
			double Team1Kill = teamOne.stream().mapToInt(SpgParticipants::getKills).sum();
			double Team1TotalDamageDealtToChampions = teamOne.stream().mapToInt(SpgParticipants::getTotalDamageDealtToChampions).sum();
			double Team1TotalDamageTaken = teamOne.stream().mapToInt(SpgParticipants::getTotalDamageTaken).sum();
			double Team1TotalHeal = teamOne.stream().mapToInt(SpgParticipants::getTotalHeal).sum();
			double Team1VisionScore = teamOne.stream().mapToInt(SpgParticipants::getVisionScore).sum();
			double Team1TotalTimeCrowdControlDealt = teamOne.stream().mapToInt(SpgParticipants::getTotalTimeCCDealt).sum();

			for (SpgParticipants item : teamOne) {
				double killScore = Team1Kill == 0 ? 0 : ((item.getKills() + item.getAssists()) / Team1Kill) * 10;
				double damageDealtScore = Team1TotalDamageDealtToChampions == 0 ? 0 : ((item.getTotalDamageDealtToChampions() / Team1TotalDamageDealtToChampions)) * 5;
				double damageTakenScore = Team1TotalDamageTaken == 0 ? 0 : ((item.getTotalDamageTaken() / Team1TotalDamageTaken)) * 4;
				double totalHealScore = Team1TotalHeal == 0 ? 0 : ((item.getTotalHeal() / Team1TotalHeal)) * 2;
				double visionScore = Team1VisionScore == 0 ? 0 : ((item.getVisionScore() / Team1VisionScore)) * 2;
				double ControlScore = Team1TotalTimeCrowdControlDealt == 0 ? 0 : ((item.getTotalTimeCCDealt() / Team1TotalTimeCrowdControlDealt)) * 2;
				ScoreLevelBO scoreLevelBO = new ScoreLevelBO();
				scoreLevelBO.setScore(killScore + damageDealtScore + damageTakenScore + totalHealScore + visionScore + ControlScore);
				scoreLevelBO.setTeamId(GameConstant.TEAM_ONE);
				scoreLevelList.add(scoreLevelBO);
			}
		}
		if (teamTwo != null) {
			double Team2Kill = teamTwo.stream().mapToInt(SpgParticipants::getKills).sum();
			double Team2TotalDamageDealtToChampions = teamTwo.stream().mapToInt(SpgParticipants::getTotalDamageDealtToChampions).sum();
			double Team2TotalDamageTaken = teamTwo.stream().mapToInt(SpgParticipants::getTotalDamageTaken).sum();
			double Team2TotalHeal = teamTwo.stream().mapToInt(SpgParticipants::getTotalHeal).sum();
			double Team2VisionScore = teamTwo.stream().mapToInt(SpgParticipants::getVisionScore).sum();
			double Team2TotalTimeCrowdControlDealt = teamTwo.stream().mapToInt(SpgParticipants::getTotalTimeCCDealt).sum();

			for (SpgParticipants item : teamTwo) {
				double killScore = Team2Kill == 0 ? 0 : ((item.getKills() + item.getAssists()) / Team2Kill) * 10;
				double damageDealtScore = Team2TotalDamageDealtToChampions == 0 ? 0 : ((item.getTotalDamageDealtToChampions() / Team2TotalDamageDealtToChampions)) * 5;
				double damageTakenScore = Team2TotalDamageTaken == 0 ? 0 : ((item.getTotalDamageTaken() / Team2TotalDamageTaken)) * 4;
				double totalHealScore = Team2TotalHeal == 0 ? 0 : ((item.getTotalHeal() / Team2TotalHeal)) * 2;
				double visionScore = Team2VisionScore == 0 ? 0 : ((item.getVisionScore() / Team2VisionScore)) * 2;
				double ControlScore = Team2TotalTimeCrowdControlDealt == 0 ? 0 : ((item.getTotalTimeCCDealt() / Team2TotalTimeCrowdControlDealt)) * 2;
				ScoreLevelBO scoreLevelBO = new ScoreLevelBO();
				scoreLevelBO.setScore(killScore + damageDealtScore + damageTakenScore + totalHealScore + visionScore + ControlScore);
				scoreLevelBO.setTeamId(GameConstant.TEAM_TWO);
				scoreLevelList.add(scoreLevelBO);
			}
		}
		// 创建优先队列，比较器定义为得分降序
		PriorityQueue<ScoreLevelBO> pq = new PriorityQueue<>(
				(a, b) -> Double.compare(b.getScore(), a.getScore())
		);

		// 元素加入优先队列
		for (ScoreLevelBO scoreLevelBO : scoreLevelList) {
			pq.offer(scoreLevelBO);
		}

		// 使用HashMap记录每支队伍的最低排名和排名最低的队员
		Map<Integer, Map.Entry<Integer, ScoreLevelBO>> teamLowestRank = new HashMap<>();

		int rank = 1;  // 排名初始值
		while (!pq.isEmpty()) {
			// 弹出得分最高的元素
			ScoreLevelBO topScore = pq.poll();
			// 设定排名
			topScore.setOrder(rank);

			// 更新每支队伍的最低排名和排名最低的队员
			int teamId = topScore.getTeamId();
			if (!teamLowestRank.containsKey(teamId) || teamLowestRank.get(teamId).getKey() > rank) {
				teamLowestRank.put(teamId, new AbstractMap.SimpleEntry<>(rank, topScore));
			}

			++rank;
		}

		// 设定每支队伍的MVP
		for (Map.Entry<Integer, ScoreLevelBO> entry : teamLowestRank.values()) {
			entry.getValue().setIsMvp(true);
		}
		Map<Integer, List<ScoreLevelBO>> collect = scoreLevelList.stream().collect(Collectors.groupingBy(ScoreLevelBO::getTeamId));

		return collect;
	}

	/**
	 * 获取召唤师头像
	 *
	 * @param profileIconId 头像id
	 * @param iconWidth     图标宽度
	 * @param iconHeight    图标高度
	 */

	public static ImageIcon getSummonerIcon(Integer profileIconId, Integer iconWidth, Integer iconHeight) {
		if (profileIconId != null) {
			try {
				Image imageSrc = ImageUtil.makeRoundedCorner(AppCache.api.getProfileIcon(profileIconId)).getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT);
				return new ImageIcon(imageSrc);

			} catch (IOException e) {
				log.error("未检测到图像");
				new ImageIcon(new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB));
			}
		}
		return new ImageIcon(new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB));
	}

}
