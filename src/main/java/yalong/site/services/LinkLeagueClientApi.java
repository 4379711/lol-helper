package yalong.site.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import yalong.site.bo.RankBO;
import yalong.site.bo.ScoreBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.bo.TeamPuuidBO;
import yalong.site.enums.GameStatusEnum;
import yalong.site.utils.RequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 连接lol客户端服务
 *
 * @author yaLong
 */
public class LinkLeagueClientApi {
    private final RequestUtil requestUtil;

    public LinkLeagueClientApi(RequestUtil requestUtil) {
        this.requestUtil = requestUtil;
    }

    /**
     * 获取游戏时长
     */
    public Float getOnlineData() throws IOException {
        String resp = requestUtil.doGet("https://127.0.0.1:2999", "/liveclientdata/allgamedata");
        return JSON.parseObject(resp).getJSONObject("gameData").getFloat("gameTime");
    }

    /**
     * 获取登录用户信息
     */
    public String getLoginInfo() throws IOException {
        return requestUtil.doGet("/lol-login/v1/session");
    }

    /**
     * 获取登录用户信息
     */
    public SummonerInfoBO getCurrentSummoner() throws IOException {
        String resp = requestUtil.doGet("/lol-summoner/v1/current-summoner");
        return JSON.parseObject(resp, SummonerInfoBO.class);

    }

    /**
     * 设置rank段位,默认设置为单排最强王者1
     * <p>
     * 段位:
     * value="IRON">坚韧黑铁
     * value="BRONZE">英勇黄铜
     * value="SILVER">不屈白银
     * value="GOLD">荣耀黄金
     * value="PLATINUM">华贵铂金
     * value="DIAMOND">璀璨钻石
     * value="MASTER">超凡大师
     * value="GRANDMASTER">傲世宗师
     * value="CHALLENGER">最强王者
     * value="UNRANKED">没有段位
     * 段位级别
     * value="IV"
     * value="III"
     * value="II"
     * value="I"
     * rank模式
     * value="RANKED_SOLO_5x5">单排/双排
     * value="RANKED_FLEX_SR">灵活组排 5v5
     * value="RANKED_FLEX_TT">灵活组排 3v3
     * value="RANKED_TFT">云顶之弈
     */
    public void setRank(RankBO bo) throws IOException {
        JSONObject body = new JSONObject(1);
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put("rankedLeagueQueue", bo.getFirstRank());
        jsonObject.put("rankedLeagueTier", bo.getSecondRank());
        jsonObject.put("rankedLeagueDivision", bo.getThirdRank());
        body.put("lol", jsonObject);
        requestUtil.doPut("/lol-chat/v1/me", body.toJSONString());
    }

    /**
     * 生涯设置背景皮肤
     *
     * @param skinId 皮肤id,长度5位
     *               比如:其中 13006，这个ID分为两部分 13 和 006,
     *               13是英雄id,6是皮肤id(不足3位,前面补0)
     */
    public void setBackgroundSkin(int skinId) throws IOException {
        JSONObject body = new JSONObject(1);
        body.put("key", "backgroundSkinId");
        body.put("value", skinId);
        requestUtil.doPost("/lol-summoner/v1/current-summoner/summoner-profile", body.toJSONString());
    }

    /**
     * 获取某个英雄的皮肤
     *
     * @param heroId 英雄id
     */
    public String getBackgroundSkin(int heroId) throws IOException {
        return requestUtil.doGet("/lol-game-data/assets/v1/champions/" + heroId + ".json");
    }

    /**
     * 获取所有英雄
     */
    public String getHero() throws IOException {
        return requestUtil.doGet("/lol-game-data/assets/v1/champion-summary.json");
    }


    /**
     * 更改游戏状态
     * "chat"->在线
     * "away"->离开
     * "dnd"->游戏中
     * "offline"->离线
     * "mobile"->手机在线
     *
     * @param status 状态值
     */
    public void changeStatus(String status) throws IOException {
        JSONObject body = new JSONObject(1);
        body.put("availability", status);
        requestUtil.doPut("/lol-chat/v1/me", body.toJSONString());
    }

    /**
     * 发送消息到组队房间
     *
     * @param roomId 房间id
     * @param msg    消息
     */
    public String msg2Room(String roomId, String msg) throws IOException {
        JSONObject body = new JSONObject(2);
        body.put("body", msg);
        body.put("type", "chat");
        String endpoint = "/lol-chat/v1/conversations/" + roomId + "/messages";
        return requestUtil.doPost(endpoint, body.toJSONString());
    }

    /**
     * 给好友发消息
     *
     * @param name 好友的游戏名
     * @param msg  消息内容
     */
    public String msg2Friend(String name, String msg) throws IOException {
        String endpoint = "/lol-game-client-chat/v1/instant-messages?summonerName=" + name + "&message=" + msg;
        return requestUtil.doGet(endpoint);
    }


    /**
     * 获取好友列表
     */
    public String getFriendList() throws IOException {
        return requestUtil.doGet("/lol-game-client-chat/v1/buddies");
    }

    /**
     * 获取选英雄房间信息
     */
    public ArrayList<String> getRoomSummonerId(String roomId) throws IOException {
        String endpoint = "/lol-chat/v1/conversations/" + roomId + "/messages";
        String resp = requestUtil.doGet(endpoint);
        JSONArray array = JSON.parseArray(resp);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            // jsonObject0.getString("fromId")
            String fromSummonerId = array.getJSONObject(i).getString("fromSummonerId");
            if (!arrayList.contains(fromSummonerId)) {
                arrayList.add(fromSummonerId);
            }
        }
        return arrayList;
    }

    public void getRoomInfo() throws IOException {
        String resp = requestUtil.doGet("/lol-chat/v1/conversations");
        System.out.println(resp);
    }

    /**
     * 获取组队房间的信息
     */
    public String getRoomGameInfo() throws IOException {
        return requestUtil.doGet("/lol-champ-select/v1/session");
    }

    /**
     * 通过玩家游戏名,查他的信息
     *
     * @param name 玩家游戏名
     */
    public SummonerInfoBO getInfoByName(String name) throws IOException {
        String resp = requestUtil.doGet("/lol-summoner/v1/summoners?name=" + name);
        return JSON.parseObject(resp, SummonerInfoBO.class);
    }

    /**
     * 通过玩家id查玩家信息
     *
     * @param summonerId 玩家游戏id
     */
    public SummonerInfoBO getInfoBySummonerId(String summonerId) throws IOException {
        String resp = requestUtil.doGet("/lol-summoner/v1/summoners/" + summonerId);
        return JSON.parseObject(resp, SummonerInfoBO.class);
    }

    /**
     * 通过puuid查玩家信息
     *
     * @param puuId 玩家puuid
     */
    public SummonerInfoBO getInfoByPuuId(String puuId) throws IOException {
        String resp = requestUtil.doGet("/lol-summoner/v2/summoners/puuid/" + puuId);
        return JSON.parseObject(resp, SummonerInfoBO.class);
    }

    /**
     * 段位查询
     */
    public String getRank(String puuid) throws IOException {
        return requestUtil.doGet("/lol-ranked/v1/ranked-stats/" + puuid);
    }

    /**
     * 通过玩家puuid查询近几把战绩
     *
     * @param id       玩家信息中的puuid
     * @param endIndex 局数
     */
    public List<ScoreBO> getScoreById(String id, int endIndex) throws IOException {
        String endpoint = "/lol-match-history/v1/products/lol/" + id + "/matches?begIndex=0&endIndex=" + endIndex;
        String resp = requestUtil.doGet(endpoint);
        JSONObject jsonObject = JSON.parseObject(resp);
        List<ScoreBO> collect = new ArrayList<>();
        try {
            collect = jsonObject.getJSONObject("games").getJSONArray("games")
                    .toJavaList(JSONObject.class)
                    .stream().map(i -> {
                        JSONObject participants = i.getJSONArray("participants").getJSONObject(0);
                        JSONObject stats = participants.getJSONObject("stats");
                        return stats.toJavaObject(ScoreBO.class);
                    }).collect(Collectors.toList());
        } catch (NullPointerException ignored) {

        }
        return collect;
    }

    /**
     * 查看游戏当前状态
     * 游戏大厅:None
     * 房间内:Lobby
     * 匹配中:Matchmaking
     * 找到对局:ReadyCheck
     * 选英雄中:ChampSelect
     * 游戏中:InProgress
     * 游戏即将结束:PreEndOfGame
     * 等待结算页面:WaitingForStats
     * 游戏结束:EndOfGame
     * 等待重新连接:Reconnect
     */
    public GameStatusEnum getGameStatus() throws IOException {
        String s = requestUtil.doGet("/lol-gameflow/v1/gameflow-phase");
        String s1 = JSON.parseObject(s, String.class);
        return GameStatusEnum.valueOf(s1);
    }

    /**
     * 接受对局
     */
    public String accept() throws IOException {
        return requestUtil.doPost("/lol-matchmaking/v1/ready-check/accept", "");
    }

    /**
     * 重连游戏
     */
    public String reconnect() throws IOException {
        return requestUtil.doPost("/lol-gameflow/v1/reconnect", "");
    }

    /**
     * 再来一局
     */
    public void playAgain() throws IOException {
        requestUtil.doPost("/lol-lobby/v2/play-again", "");
    }

    /**
     * 判断是红方还是蓝方
     */
    public String getBlueRed() throws IOException {
        String resp = requestUtil.doGet("/lol-champ-select/v1/pin-drop-notification");
        JSONObject jsonObject = JSON.parseObject(resp);
        return jsonObject.getString("mapSide");
    }

    public String getSelectChampSession() throws IOException {
        return requestUtil.doGet("/lol-champ-select/v1/session");
    }

    public String getSearchState() throws IOException {
        return requestUtil.doGet("/lol-lobby/v2/lobby/matchmaking/search-state");
    }

    /**
     * 获取当前游戏两队人的puuid
     */
    public TeamPuuidBO getTeamPuuid() throws IOException {
        TeamPuuidBO result = new TeamPuuidBO();
        String resp = requestUtil.doGet("/lol-gameflow/v1/session");
        try {
            JSONObject jsonObject = JSON.parseObject(resp).getJSONObject("gameData");
            List<String> teamOne = jsonObject.getJSONArray("teamOne")
                    .toJavaList(JSONObject.class)
                    .stream()
                    .map(i -> i.getString("puuid"))
                    .collect(Collectors.toList());

            List<String> teamTwo = jsonObject.getJSONArray("teamTwo")
                    .toJavaList(JSONObject.class)
                    .stream()
                    .map(i -> i.getString("puuid"))
                    .collect(Collectors.toList());
            result.setTeamPuuid1(teamOne);
            result.setTeamPuuid2(teamTwo);
            return result;
        } catch (NullPointerException ignored) {
            throw new IOException("游戏对局未开始");
        }
    }
}
