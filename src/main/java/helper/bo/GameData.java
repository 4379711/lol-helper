package helper.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 单局的比赛记录
 */
@Data
public class GameData {
	private String endOfGameResult;
	private Long gameCreation;
	private Date gameCreationDate;
	private Integer gameDuration;
	private Long gameId;
	private String gameMode;
	private String gameType;
	private String gameVersion;
	private Integer mapId;
	private List<ParticipantIdentities> participantIdentities;
	private List<Participants> participants;
	private String platformId;
	private Integer queueId;
	private Integer seasonId;
	private List<Teams> teams;

}
