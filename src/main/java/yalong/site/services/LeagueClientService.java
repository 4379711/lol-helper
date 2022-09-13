package yalong.site.services;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yaLong
 */
public class LeagueClientService {
    private final LinkLeagueClientApi api;
    private boolean roomMessageSend;
    private boolean gameMessageSend;
    private final SummonerInfoBO owner;
    private final Pattern roomIdPattern = Pattern.compile("\\{\"chatRoomName\":\"(.*)?\\@");

    public static void start() throws IOException, InterruptedException {
        LeagueClientService service = new LeagueClientService();
        service.setRank(GlobalData.currentRankBO);
        service.runForever();
    }

    public LeagueClientService() throws IOException {
        this.clearFlag();
        LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
        if (leagueClientBO.equals(new LeagueClientBO())) {
            throw new IOException("未检测到游戏启动");
        }
        RequestUtil requestUtil = new RequestUtil(leagueClientBO);
        api = new LinkLeagueClientApi(requestUtil);
        // 获取登录人的信息
        owner = api.getCurrentSummoner();
        api.getLoginInfo();
        // 添加到全局变量
        GlobalData.service = this;
    }

    /**
     * 清空标记
     */
    private void clearFlag() {
        roomMessageSend = false;
        gameMessageSend = false;
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
        for (String id : puuidList) {
            //查询战绩
            List<ScoreBO> scoreBOList = api.getScoreById(id, 3);
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
            }
            score /= 3.0f;
            //查看玩家名称
            SummonerInfoBO infoByPuuId = api.getInfoByPuuId(id);
            String displayName = infoByPuuId.getDisplayName();
            treeMap.put(score, displayName);
        }
        Map.Entry<Float, String> firstEntry = treeMap.firstEntry();
        Map.Entry<Float, String> lastEntry = treeMap.lastEntry();
        result.add("牛马是:<" + firstEntry.getValue() + "> 得分:" + String.format("%.2f", firstEntry.getKey()));
        result.add("大神是:<" + lastEntry.getValue() + "> 得分:" + String.format("%.2f", lastEntry.getKey()));
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
                this.clearFlag();
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
                if (!roomMessageSend && GlobalData.sendScore) {
                    try {
                        //获取房间号
                        String roomInfo = api.getRoomGameInfo();
                        Matcher matcher = roomIdPattern.matcher(roomInfo);
                        String roomId;
                        if (matcher.find()) {
                            roomId = matcher.group(1);
                        } else {
                            break;
                        }
                        ArrayList<String> summonerIdList = api.getRoomSummonerId(roomId);
                        //可能队友还没进入房间
                        if (summonerIdList.size() != 5) {
                            break;
                        }
                        ArrayList<String> strings = new ArrayList<>();
                        for (String id : summonerIdList) {
                            String mySummonerId = owner.getSummonerId();
                            // 排除自己
                            if (mySummonerId.equals(id)) {
                                continue;
                            }
                            SummonerInfoBO infoBySummonerId = api.getInfoBySummonerId(id);
                            String puuid = infoBySummonerId.getPuuid();
                            strings.add(puuid);
                        }
                        ArrayList<String> msg = this.dealScore2Msg(strings);
                        if (msg != null) {
                            // 查询我方队员战绩,放到公共数据区
                            GlobalData.myTeamScore = msg;
                            roomMessageSend = true;
                        }
                    } catch (Exception ignored) {
                        break;
                    }
                }
                break;
            }
            case InProgress: {
                if (GlobalData.sendScore && !gameMessageSend) {
                    List<String> otherPuuid = getOtherPuuid();
                    if (!otherPuuid.contains(null) && otherPuuid.size() == 5) {
                        // 查询对方队员战绩,放到公共数据区
                        GlobalData.otherTeamScore = dealScore2Msg(otherPuuid);
                        gameMessageSend = true;
                    }
                }
                break;
            }
            default: {
                this.clearFlag();
                break;
            }
        }
    }

}
