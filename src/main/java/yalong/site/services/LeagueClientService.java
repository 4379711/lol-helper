package yalong.site.services;

import yalong.site.bo.*;
import yalong.site.enums.GameStatusEnum;
import yalong.site.frame.utils.FrameMsgUtil;
import yalong.site.utils.ProcessUtil;
import yalong.site.utils.RequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        //根据puuid查最近几次的战绩
        for (String id : puuidList) {
            //查询战绩
            List<ScoreBO> scoreBOList = api.getScoreById(id, 3);
            //战绩
            StringBuilder scoreBuilder = new StringBuilder();
            for (ScoreBO scoreBO : scoreBOList) {
                if (scoreBO.getWin()) {
                    scoreBuilder.append("胜");
                } else {
                    scoreBuilder.append("败");
                }
                scoreBuilder.append("(");
                scoreBuilder.append(scoreBO.getKills());
                scoreBuilder.append("/");
                scoreBuilder.append(scoreBO.getDeaths());
                scoreBuilder.append("/");
                scoreBuilder.append(scoreBO.getAssists());
                scoreBuilder.append(") ");
            }
            //查看玩家名称
            SummonerInfoBO infoByPuuId = api.getInfoByPuuId(id);
            String displayName = infoByPuuId.getDisplayName();

            //整理输出信息
            if (scoreBuilder.length() != 0) {
                String score = scoreBuilder.toString();
                result.add("玩家名称:<" + displayName + ">\n近几局战绩:" + score);
            }
        }
        return result;
    }

    /**
     * 持续监听游戏状态
     */
    public void runForever() throws IOException, InterruptedException {
        while (true) {
            this.switchGameStatus();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * 不同游戏状态做不同的事情
     */
    public void switchGameStatus() throws IOException {
        //监听游戏状态
        GameStatusEnum gameStatus = api.getGameStatus();
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
                if (!roomMessageSend && GlobalData.autoSend) {
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
                            for (String s : msg) {
                                api.msg2Room(roomId, s);
                            }
                            roomMessageSend = true;
                        }
                    } catch (Exception ignored) {
                        break;
                    }
                }
                break;
            }
            case InProgress: {
                if (GlobalData.autoSend && !gameMessageSend) {
                    List<String> otherPuuid = getOtherPuuid();
                    if (!otherPuuid.contains(null) && otherPuuid.size() == 5) {
                        ArrayList<String> strings = dealScore2Msg(otherPuuid);
                        for (String string : strings) {
                            FrameMsgUtil.sendLine(string);
                        }
                        FrameMsgUtil.sendLine("-----------------------------------");
                        gameMessageSend = true;
                    }

                }
                break;
            }
            case EndOfGame: {
                //暂停挂机
                GlobalData.leave = false;
                break;
            }
            default: {
                this.clearFlag();
                break;
            }
        }
    }

}
