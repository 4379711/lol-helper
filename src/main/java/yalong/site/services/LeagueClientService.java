package yalong.site.services;

import yalong.site.bo.LeagueClientBO;
import yalong.site.bo.ScoreBO;
import yalong.site.bo.SummonerInfoBO;
import yalong.site.bo.TeamPuuidBO;
import yalong.site.enums.GameStatusEnum;
import yalong.site.utils.ProcessUtil;
import yalong.site.utils.RequestUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yaLong
 */
public class LeagueClientService {
    public final LinkLeagueClientApi api;
    private boolean roomMessageSend;
    private boolean gameMessageSend;
    private final SummonerInfoBO owner;

    public LeagueClientService() throws IOException {
        this.clearFlag();
        LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
        RequestUtil requestUtil = new RequestUtil(leagueClientBO);
        api = new LinkLeagueClientApi(requestUtil);
        // 设置段位
        api.setRank();
        // 获取登录人的信息
        owner = api.getCurrentSummoner();
    }

    /**
     * 清空标记
     */
    private void clearFlag() {
        roomMessageSend = false;
        gameMessageSend = false;
    }

    public void dealScore() throws IOException {
        String ownerPuuid = owner.getPuuid();
        //查询两对的所有人puuid
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
                System.out.println("玩家名称:<" + displayName + ">近几局战绩:");
                System.out.println(score);
                System.out.println("-------------------");
            }
        }

    }

    public void doit() throws IOException, InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(3);
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
                            api.msg2Room(roomId, message);
                        }
                    }
                    break;
                }
                case InProgress: {
                    if (!gameMessageSend) {
                        Float gameTime;
                        try {
                            gameTime = api.getOnlineData();
                        } catch (Throwable ignored) {
                            TimeUnit.SECONDS.sleep(3);
                            break;
                        }

                        //游戏进去的30秒内,发送这些消息
                        if (gameTime < 30) {
                            dealScore();
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
}
