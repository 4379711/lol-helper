package yalong.site.cache;

import yalong.site.bo.SkinBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.frame.bo.ItemBO;

import java.util.ArrayList;
import java.util.List;

/**
 * 每一局游戏的数据缓存
 *
 * @author yalong
 */
public class GameDataCache {
	public static SummonerInfoBO me;
	/**
	 * 本局游戏中,所选英雄的所有炫彩皮肤
	 */
	public static List<SkinBO> currentChampionSkins;
	/**
	 * 本局游戏中,要选择的炫彩皮肤
	 */
	public static Integer skinId;
	public static ArrayList<String> otherTeamScore = new ArrayList<>();
	public static ArrayList<String> myTeamScore = new ArrayList<>();

	public static void reset() {
		skinId = null;
		currentChampionSkins = new ArrayList<>();
		otherTeamScore = new ArrayList<>();
		myTeamScore = new ArrayList<>();
		resetPickSkinBoxData();
	}

	public static void resetPickSkinBoxData() {
		for (int i = 1; i < FrameInnerCache.pickSkinBox.getItemCount(); i++) {
			FrameInnerCache.pickSkinBox.removeItemAt(i);
		}
	}

}
