package yalong.site.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import yalong.site.bo.*;
import yalong.site.enums.GameStatusEnum;
import yalong.site.utils.ProcessUtil;
import yalong.site.utils.RequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author yaLong
 */
public class LeagueClientService {
    private final LinkLeagueClientApi api;
    private final SummonerInfoBO owner;

    public static void start() throws IOException, InterruptedException {
        LeagueClientService service = new LeagueClientService();
        service.setRank(GlobalData.currentRankBO);
        service.runForever();
    }

    public LeagueClientService() throws IOException {
        LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
        if (leagueClientBO.equals(new LeagueClientBO())) {
            throw new IOException("未检测到游戏启动");
        }
        RequestUtil requestUtil = new RequestUtil(leagueClientBO);
        api = new LinkLeagueClientApi(requestUtil);
        // 获取登录人的信息
        owner = api.getCurrentSummoner();
        // 添加到全局变量
        GlobalData.service = this;
    }

    /**
     * 设置游戏状态
     *
     * @param status 状态值
     */
    public void setGameStatus(String status) throws IOException {
        api.changeStatus(status);
    }

    /**
     * 设置段位
     */
    public void setRank(RankBO bo) throws IOException {
        api.setRank(bo);
    }

    /**
     * 查询对方puuid,必须进入游戏后才能查到
     */
    public List<String> getOtherPuuid() throws IOException {
        String ownerPuuid = owner.getPuuid();
        //查询两队的所有人puuid
        TeamPuuidBO teamPuuidBO = api.getTeamPuuid();
        //找出对手的puuid
        List<String> puuidList = teamPuuidBO.getTeamPuuid1();
        if (puuidList.contains(ownerPuuid)) {
            puuidList = teamPuuidBO.getTeamPuuid2();
        }
        return puuidList;
    }


    /**
     * 查询战绩,格式化为要发送的消息
     */
    public ArrayList<String> dealScore2Msg(List<String> puuidList) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        TreeMap<Float, String> treeMap = new TreeMap<>();
        //根据puuid查最近几次的战绩
        for (String puuid : puuidList) {
            //查看玩家名称
            SummonerInfoBO summonerInfo = api.getInfoByPuuId(puuid);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("【");
            stringBuilder.append(summonerInfo.getDisplayName());
            stringBuilder.append("】, 最近三场战绩为：");
            //查询战绩
            List<ScoreBO> scoreBOList = api.getScoreById(puuid, 3);
            // 计算得分 最近三把(KDA+输赢)的平均值
            // KDA->(击杀*1.2+助攻*0.8)/(死亡*1.2)
            // 输赢->赢+1 输-1
            float score = 0.0f;
            for (ScoreBO scoreBO : scoreBOList) {
                if (scoreBO.getWin()) {
                    score += 1;
                } else {
                    score -= 1;
                }
                Integer kills = scoreBO.getKills();
                Integer deaths = scoreBO.getDeaths();
                Integer assists = scoreBO.getAssists();
                score += (kills * 1.2 + assists * 0.8) / Math.max(deaths * 1.2, 1);
                stringBuilder.append(kills);
                stringBuilder.append("-");
                stringBuilder.append(deaths);
                stringBuilder.append("-");
                stringBuilder.append(assists);
                stringBuilder.append(",  ");
            }
            score /= 3.0f;
            stringBuilder.append("评分: ");
            stringBuilder.append(String.format("%.2f", score));
            treeMap.put(score, stringBuilder.toString());
        }
        Map.Entry<Float, String> firstEntry = treeMap.firstEntry();
        Map.Entry<Float, String> lastEntry = treeMap.lastEntry();
        result.add("傻鸟是:" + firstEntry.getValue());
        result.add("大神是:<" + lastEntry.getValue());
        return result;
    }

    /**
     * 持续监听游戏状态
     */
    public void runForever() throws IOException, InterruptedException {
        while (true) {
            this.switchGameStatus();
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    /**
     * 不同游戏状态做不同的事情
     */
    public void switchGameStatus() throws IOException {
        //监听游戏状态
        GameStatusEnum gameStatus = api.getGameStatus();
        GlobalData.gameStatus = gameStatus;
        switch (gameStatus) {
            case ReadyCheck: {
                if (GlobalData.autoAccept) {
                    // 自动接受对局
                    String accept = api.accept();
                }
                break;
            }
            case Reconnect: {
                if (GlobalData.autoReconnect) {
                    //重连
                    String reconnect = api.reconnect();
                }
                break;
            }
            case ChampSelect: {
                //{"errorCode":"RPC_ERROR","httpStatus":500,"implementationDetails":{},"message":"Error response for PATCH /lol-lobby-team-builder/champ-select/v1/session/actions/2: Unable to process action change: Received status Error: INVALID_STATE instead of expected status of OK from request to teambuilder-draft:updateActionV1"}
                if (GlobalData.autoPick) {
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
                                    String resp = api.banPick("pick", actionId, GlobalData.pickChampionId);
                                    System.out.println("pick:" + resp);
                                }
                            }
                        }

                    }
                }

                if (GlobalData.autoBan) {
                    String roomGameInfo = api.getChampSelectInfo();
                    System.out.println(roomGameInfo);
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
                                if ("ban".equals(type)) {
                                    String resp = api.banPick("ban", actionId, GlobalData.banChampionId);
                                    System.out.println("ban:" + resp);
                                }
                            }
                        }

                    }
                }

                if (GlobalData.sendScore) {
                    String roomGameInfo = api.getChampSelectInfo();
                    JSONObject jsonObject = JSONObject.parseObject(roomGameInfo);
                    JSONArray myTeam = jsonObject.getJSONArray("myTeam");
                    //可能队友还没进入房间
                    if (myTeam.size() != 5) {
                        break;
                    }
                    ArrayList<String> puuidList = new ArrayList<>();
                    for (int i = 0; i < myTeam.size(); i++) {
                        String puuid = myTeam.getJSONObject(i).getString("puuid");
                        puuidList.add(puuid);
                    }
                    ArrayList<String> msg = this.dealScore2Msg(puuidList);
                    if (msg != null) {
                        // 查询我方队员战绩,放到公共数据区
                        GlobalData.myTeamScore = msg;
                    }
                }
                break;
            }
            case InProgress: {
                if (GlobalData.sendScore) {
                    List<String> otherPuuid = getOtherPuuid();
                    if (!otherPuuid.contains(null) && otherPuuid.size() == 5) {
                        // 查询对方队员战绩,放到公共数据区
                        GlobalData.otherTeamScore = dealScore2Msg(otherPuuid);
                    }
                }
                break;
            }
            case EndOfGame: {
                api.playAgain();
                break;
            }
            default: {
                break;
            }
        }
    }

}
