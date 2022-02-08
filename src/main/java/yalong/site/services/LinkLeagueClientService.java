package yalong.site.services;

import com.alibaba.fastjson.JSON;
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
    public String getCurrentSummoner() throws IOException {
        return requestUtil.doGet("/lol-summoner/v1/current-summoner");
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
    public String getInfoByName(String name) throws IOException {
        return requestUtil.doGet("/lol-summoner/v1/summoners?name=" + name);
    }

    /**
     * 通用玩家puuid查询近几把战绩
     *
     * @param id       玩家信息中的puuid
     * @param begIndex 起止
     * @param endIndex 截止
     */
    public String getScoreById(String id, String begIndex, String endIndex) throws IOException {
        String endpoint = "/lol-match-history/v1/products/lol/" + id + "/matches?begIndex=" + begIndex + "&endIndex=" + endIndex;
        return requestUtil.doGet(endpoint);
    }

    /**
     * 查询游戏匹配状态
     */
    public String getMatchStatus() throws IOException {
        return requestUtil.doGet("/lol-matchmaking/v1/ready-check");
    }

    /**
     * 接受对局
     */
    public String accept() throws IOException {
        HashMap<String, String> map = new HashMap<>(0);
        String body = JSON.toJSONString(map);
        return requestUtil.doPost("/lol-matchmaking/v1/ready-check/accept", body);
    }

}
