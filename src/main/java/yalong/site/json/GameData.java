package yalong.site.json;

import lombok.Data;
import yalong.site.json.entity.match.ParticipantIdentities;
import yalong.site.json.entity.match.Participants;

import java.util.Date;
import java.util.List;

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
