package yalong.site;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ChampionBO;
import yalong.site.bo.LeagueClientBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.GameDataCache;
import yalong.site.enums.GameStatusEnum;
import yalong.site.exception.NoProcessException;
import yalong.site.frame.bo.ItemBO;
import yalong.site.http.RequestLcuUtil;
import yalong.site.services.lcu.*;
import yalong.site.services.word.LoadGarbageWord;
import yalong.site.utils.ProcessUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yalong
 */
@Slf4j
public class ClientStarter {
    private LinkLeagueClientApi api;

    public void initLcu() throws Exception {
        LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
        if (leagueClientBO.equals(new LeagueClientBO())) {
            throw new NoProcessException();
        }
        RequestLcuUtil requestUtil = new RequestLcuUtil(leagueClientBO);
        api = new LinkLeagueClientApi(requestUtil);
    }

    public void cacheData() {
        // 加载垃圾话
        LoadGarbageWord loadGarbageWord = new LoadGarbageWord();
        try {
            AppCache.items = api.getAllItems();
            AppCache.summonerSpellsList = api.getAllSummonerSpells();
        } catch (Exception e) {
            log.error("获取战局记录信息错误");
        }
        AppCache.garbageWordList = loadGarbageWord.loadWord();
        log.info("垃圾话加载完成");

        // 缓存登录人的信息
        try {
            GameDataCache.me = api.getCurrentSummoner();
        } catch (Exception e) {
            log.error("获取登录人信息错误");
        }

        //缓存所有英雄
        try {
            AppCache.allChampion = api.getAllChampion();
        } catch (Exception e) {
            log.error("获取所有英雄错误");
        }
    }

    public void loadFrameData() {
        // 所有英雄添加到面板下拉框
        int itemCount = FrameInnerCache.pickBox.getItemCount();
        if (itemCount == 1) {
            for (ChampionBO bo : AppCache.allChampion) {
                FrameInnerCache.pickBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
                FrameInnerCache.banBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
            }
            log.info("添加到面板下拉框完成");
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listenGameStatus() throws InterruptedException, IOException {
        int currentStatus = 0;
        while (true) {
            TimeUnit.MILLISECONDS.sleep(500);
            GameStatusContext gameStatusContext = new GameStatusContext();
            CalculateScore calculateScore = new CalculateScore(api);
            //监听游戏状态
            GameStatusEnum gameStatus = api.getGameStatus();
            switch (gameStatus) {
                case None:
                case Lobby:
                case Matchmaking:
                case PreEndOfGame:
                case WaitingForStats: {
                    gameStatusContext.setStrategy(new OtherStatusStrategy());
                    GameDataCache.reset();
                    break;
                }
                case ReadyCheck: {
                    gameStatusContext.setStrategy(new ReadyCheckStrategy(api));
                    break;
                }
                case ChampSelect: {
                    gameStatusContext.setStrategy(new ChampSelectStrategy(api, calculateScore));
                    break;
                }
                case InProgress: {
                    gameStatusContext.setStrategy(new InProgressStrategy(api, calculateScore));
                    break;
                }
                case EndOfGame: {
                    gameStatusContext.setStrategy(new EndOfGameStrategy(api));
                    break;
                }
                case Reconnect: {
                    gameStatusContext.setStrategy(new ReconnectStrategy(api));
                    break;
                }
                default: {
                    gameStatusContext.setStrategy(new OtherStatusStrategy());
                    break;
                }
            }
            gameStatusContext.executeStrategy();
        }
    }

}
