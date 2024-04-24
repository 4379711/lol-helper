package yalong.site.services.lcu;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.FrameCache;
import yalong.site.cache.GameDataCache;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author yalong
 */
@Slf4j
public class ChampSelectStrategy implements GameStatusStrategy {
	private final LinkLeagueClientApi api;
	private final CalculateScore calculateScore;

	public ChampSelectStrategy(LinkLeagueClientApi api, CalculateScore calculateScore) {
		this.api = api;
		this.calculateScore = calculateScore;
	}

	private void autoBanPick() throws IOException {
		// todo 有些游戏模式会让选择多次,暂未发现什么标记能够分辨是预选和确认选择,所以目前的方式让程序一直发起pick请求
		if (FrameCache.pickChampionId != null ||FrameCache.banChampionId != null) {
			String roomGameInfo = api.getChampSelectInfo();
			JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
			int localPlayerCellId = jsonObject.getIntValue("localPlayerCellId");
			JSONArray actions = jsonObject.getJSONArray("actions");
			for (int j = 0; j < actions.size(); j++) {
				JSONArray action = actions.getJSONArray(j);
				for (int i = 0; i < action.size(); i++) {
					JSONObject actionElement = action.getJSONObject(i);
					if (localPlayerCellId == actionElement.getIntValue("actorCellId")) {
						int actionId = actionElement.getIntValue("id");
						String type = actionElement.getString("type");
						if ("pick".equals(type)) {
							//报错处理? {"errorCode":"RPC_ERROR","httpStatus":500,"implementationDetails":{},"message":"Error response for PATCH /lol-lobby-team-builder/champ-select/v1/session/actions/2: Unable to process action change: Received status Error: INVALID_STATE instead of expected status of OK from request to teambuilder-draft:updateActionV1"}
							api.banPick("pick", actionId, FrameCache.pickChampionId);
						}else {
							api.banPick("ban", actionId, FrameCache.banChampionId);
						}
					}
				}

			}
		}
	}

	private void selectScore() throws IOException {
		if (FrameCache.sendScore && GameDataCache.myTeamScore.isEmpty()) {
			String roomGameInfo = api.getChampSelectInfo();
			JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
			JSONArray myTeam = jsonObject.getJSONArray("myTeam");
			//可能队友还没进入房间
			if (myTeam.size() != 5) {
				return;
			}
			ArrayList<String> puuidList = new ArrayList<>();
			for (int i = 0; i < myTeam.size(); i++) {
				String puuid = myTeam.getJSONObject(i).getString("puuid");
				puuidList.add(puuid);
			}
			ArrayList<String> msg = calculateScore.dealScore2Msg(puuidList);
			if (msg != null) {
				// 查询我方队员战绩,放到公共数据区
				GameDataCache.myTeamScore = msg;
			}
		}
	}

	@Override
	public void doThis() {
		try {
			autoBanPick();
		} catch (Exception e) {
			log.error("ban/pick错误", e);
		}

		try {
			selectScore();
		} catch (Exception e) {
			log.error("sendScore错误", e);
		}
	}
}
