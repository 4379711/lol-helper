package helper.bo;

import lombok.Data;

import java.util.List;

/**
 * @author WuYi
 */
@Data
public class SpgGames {
	private String endOfGameResult;
	private Long gameCreation;
	private Integer gameDuration;
	private Long gameId;
	private Long gameStartTimestamp;
	private Long gameEndTimestamp;
	private String gameMode;
	private String gameType;
	private String gameVersion;
	private Integer mapId;
	private List<SpgParticipants> participants;
	private String platformId;
	private Integer queueId;
	private Integer seasonId;
	private List<Teams> teams;

}
