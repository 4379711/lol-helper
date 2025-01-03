package helper.bo;

import helper.enums.GameQueueEnum;
import helper.frame.constant.GameConstant;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 带有每个玩家的比赛历史记录
 *
 * @author WuYi
 */
@Data
public class GameMatchHistoryBO {
	private String endOfGameResult;
	private String gameCreation;
	private Date gameCreationDate;
	private Long gameDuration;
	private Long gameId;
	private String gameVersion;
	private Integer mapId;
	private String gameMode;
	private String gameType;
	private List<ParticipantIdentities> participantIdentities;
	private List<Participants> participants;
	/**
	 * 游戏类型ID 使用该类型区分游戏类型
	 * 在{@link GameConstant#GAME_TYPE}和{@link GameQueueEnum}
	 * 进行映射和判断是否能进行对局详情展示
	 */
	private Integer queueId;
	private List<Teams> teams;

}
