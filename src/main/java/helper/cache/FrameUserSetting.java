package helper.cache;

import com.alibaba.fastjson2.annotation.JSONField;
import helper.bo.RankBO;
import helper.frame.utils.FrameConfigUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户的配置
 *
 * @author @_@
 */
@Getter
public class FrameUserSetting {

	/**
	 * 自动寻找对局
	 */
	private Boolean autoSearch = false;

	/**
	 * 自动接受对局
	 */
	private Boolean autoAccept = true;

	/**
	 * 自动重连
	 */
	private Boolean autoReconnect = false;

	/**
	 * 再来一局
	 */
	private Boolean autoPlayAgain = false;

	/**
	 * 发送战绩
	 */
	private Boolean sendScore = true;

	/**
	 * 互动模式
	 */
	private Boolean communicate = true;

	/**
	 * 一键连招
	 */
	private Boolean autoKey = false;
	/**
	 * 选择炫彩
	 */
	private Boolean pickSkin = false;
	/**
	 * 房间内显示战绩
	 */
	private Boolean showMatchHistory = true;
	/**
	 * 房间内显示战绩筛选的模式
	 */
	private List<Integer> selectMode = new ArrayList<>();

	/**
	 * 秒选英雄id
	 */
	@JSONField(serialize = false)
	private Integer pickChampionId = null;
	/**
	 * 禁用英雄id
	 */
	@JSONField(serialize = false)
	private Integer banChampionId = null;
	/**
	 * 生涯背景英雄id
	 */
	@JSONField(serialize = false)
	private Integer careerChampionId = null;
	/**
	 * 伪造段位
	 */
	@JSONField(serialize = false)
	private RankBO currentRankBO = new RankBO("RANKED_SOLO_5x5", "UNRANKED", "I");

	public void setAutoSearch(Boolean autoSearch) {
		this.autoSearch = autoSearch;
		FrameConfigUtil.save();
	}

	public void setAutoAccept(Boolean autoAccept) {
		this.autoAccept = autoAccept;
		FrameConfigUtil.save();
	}

	public void setAutoReconnect(Boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
		FrameConfigUtil.save();
	}

	public void setAutoPlayAgain(Boolean autoPlayAgain) {
		this.autoPlayAgain = autoPlayAgain;
		FrameConfigUtil.save();
	}

	public void setSendScore(Boolean sendScore) {
		this.sendScore = sendScore;
		FrameConfigUtil.save();
	}

	public void setCommunicate(Boolean communicate) {
		this.communicate = communicate;
		FrameConfigUtil.save();
	}

	public void setAutoKey(Boolean autoKey) {
		this.autoKey = autoKey;
		FrameConfigUtil.save();
	}

	public void setPickSkin(Boolean pickSkin) {
		this.pickSkin = pickSkin;
		FrameConfigUtil.save();
	}

	public void setShowMatchHistory(Boolean showMatchHistory) {
		this.showMatchHistory = showMatchHistory;
		FrameConfigUtil.save();
	}

	public void setSelectMode(List<Integer> selectMode) {
		this.selectMode = selectMode;
		FrameConfigUtil.save();
	}

	public void setPickChampionId(Integer pickChampionId) {
		this.pickChampionId = pickChampionId;
		FrameConfigUtil.save();
	}

	public void setBanChampionId(Integer banChampionId) {
		this.banChampionId = banChampionId;
		FrameConfigUtil.save();
	}

	public void setCareerChampionId(Integer careerChampionId) {
		this.careerChampionId = careerChampionId;
		FrameConfigUtil.save();
	}
}