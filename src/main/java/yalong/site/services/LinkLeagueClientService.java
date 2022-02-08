package yalong.site.services;

import com.alibaba.fastjson.JSON;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.utils.RequestUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * 连接lol客户端服务
 *
 * @author yaLong
 */
public class LinkLeagueClientService {
    private final RequestUtil requestUtil;

    public LinkLeagueClientService(RequestUtil requestUtil) {
        this.requestUtil = requestUtil;
    }

    /**
     * 获取游戏内实时数据
     */
    public String getOnlineData() throws IOException {
        return requestUtil.doGet("https://127.0.0.1:2999", "/liveclientdata/allgamedata");
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
     * 更改游戏状态
     * "chat"->在线
     * "away"->离开
     * "dnd"->游戏中
     * "offline"->离线
     * "mobile"->手机在线
     *
     * @param status 状态值
     */
    public String changeStatus(String status) throws IOException {
        HashMap<String, String> map = new HashMap<>(1);
        map.put("availability", status);
        String body = JSON.toJSONString(map);
        return requestUtil.doPut("/lol-chat/v1/me", body);
    }

    /**
     * 发送消息到组队房间
     *
     * @param roomId 房间id
     * @param msg    消息
     */
    public String msg2Room(String roomId, String msg) throws IOException {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("body", msg);
        map.put("type", "chat");
        String body = JSON.toJSONString(map);
        String endpoint = "/lol-chat/v1/conversations/" + roomId + "/messages";
        return requestUtil.doPost(endpoint, body);
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
     * 获取组队房间的信息
     */
    public String getRoomInfo() throws IOException {
        return requestUtil.doGet("/lol-chat/v1/conversations");
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
     * 通用玩家puuid查询近几把战绩
     *
     * @param id       玩家信息中的puuid
     * @param endIndex 局数
     */
    public String getScoreById(String id, int endIndex) throws IOException {
        String endpoint = "/lol-match-history/v1/products/lol/" + id + "/matches?begIndex=0&endIndex=" + endIndex;
        return requestUtil.doGet(endpoint);
    }

    /**
     * 查看游戏当前状态
     * 游戏大厅:None
     * 房间内:Lobby
     * 开始匹配:Matchmaking
     * 等待点击对局按钮:ReadyCheck
     * 选英雄中:ChampSelect
     * 游戏中:InProgress
     * 游戏即将结束:PreEndOfGame
     * 游戏结束:EndOfGame
     */
    public String getGameStatus() throws IOException {
        return requestUtil.doGet("/lol-gameflow/v1/gameflow-phase");
    }

    /**
     * 接受对局
     */
    public void accept() throws IOException {
        requestUtil.doPost("/lol-matchmaking/v1/ready-check/accept", "");
    }

    /**
     * 自动接受对局
     */
    public void autoAcceptGame() throws IOException {
        String gameStatus = this.getGameStatus();
        if ("ReadyCheck".equals(gameStatus)) {
            this.accept();
        }
    }

    public String getCurrentGameInfo() throws IOException {
        String resp = requestUtil.doGet("/lol-gameflow/v1/session");
        return resp;
    }
}
