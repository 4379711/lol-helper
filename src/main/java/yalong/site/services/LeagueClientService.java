package yalong.site.services;

import org.jawin.COMException;
import yalong.site.bo.LeagueClientBO;
import yalong.site.bo.ScoreBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.bo.TeamPuuidBO;
import yalong.site.enums.GameStatusEnum;
import yalong.site.utils.DmUtil;
import yalong.site.utils.ProcessUtil;
import yalong.site.utils.RequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yaLong
 */
public class LeagueClientService {
    public final LinkLeagueClientApi api;
    private boolean roomMessageSend;
    private boolean gameMessageSend;
    private final SummonerInfoBO owner;
    private final DmUtil dmUtil;

    public LeagueClientService(DmUtil dmUtil) throws IOException {
        this.clearFlag();
        LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
        if (leagueClientBO.equals(new LeagueClientBO())) {
            System.out.println("未检测到游戏启动");
            System.exit(0);
        }
        RequestUtil requestUtil = new RequestUtil(leagueClientBO);
        api = new LinkLeagueClientApi(requestUtil);
        // 设置段位
        api.setRank();
        // 获取登录人的信息
        owner = api.getCurrentSummoner();
        this.dmUtil = dmUtil;
    }

    /**
     * 清空标记
     */
    private void clearFlag() {
        roomMessageSend = false;
        gameMessageSend = false;
    }

    /**
     * 查询战绩,格式化为要发送的消息
     */
    public ArrayList<String> dealScore2Msg() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        String ownerPuuid = owner.getPuuid();
        //查询两队的所有人puuid
        TeamPuuidBO teamPuuidBO = api.getTeamPuuid();
        //找出对手的puuid
        List<String> puuidList = teamPuuidBO.getTeamPuuid1();
        if (puuidList.contains(ownerPuuid)) {
            puuidList = teamPuuidBO.getTeamPuuid2();
        }
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
                result.add("玩家名称:<" + displayName + ">近几局战绩:       "
                        + score + "                 .");
            }
        }
        return result;
    }

    public void switchGameStatus() throws IOException, InterruptedException, COMException {
        //监听游戏状态
        GameStatusEnum gameStatus = api.getGameStatus();
        switch (gameStatus) {
            case ReadyCheck: {
                // 自动接受对局
                api.accept();
                this.clearFlag();
                break;
            }
            case Reconnect: {
                //重连
                api.reconnect();
                break;
            }
            case PreEndOfGame:
            case WaitingForStats:
            case EndOfGame: {
                this.clearFlag();
                break;
            }
            case ChampSelect: {
                if (!roomMessageSend) {
                    roomMessageSend = true;
                    // 获取红蓝方
                    String mapSide = api.getBlueRed();
                    String message = null;
                    if ("blue".equals(mapSide)) {
                        message = "这把是蓝色方,泉水在左下角.";
                    } else if ("red".equals(mapSide)) {
                        message = "这把是红色方,泉水在右上角.";
                    }
                    if (message != null) {
                        //获取房间号
                        String roomId = api.getRoomId();
                        if (roomId != null) {
                            api.msg2Room(roomId, message);
                        }
                    }
                }
                break;
            }
            case InProgress: {
                if (!gameMessageSend) {
                    Float gameTime;
                    try {
                        gameTime = api.getOnlineData();
                    } catch (Throwable e) {
                        System.out.println("getOnlineData出错:\n" + e.getMessage());
                        break;
                    }
                    //游戏进去的30秒内,发送这些消息
                    if (gameTime < 30) {
                        int hwnd = dmUtil.getHwnd();
                        if (hwnd != 0) {
                            ArrayList<String> strings = dealScore2Msg();
                            for (String msg : strings) {
                                dmUtil.sendMessage(hwnd, msg);
                            }
                        }
                    }
                    gameMessageSend = true;
                }
                break;
            }
            default: {
                break;
            }
        }
    }
}
