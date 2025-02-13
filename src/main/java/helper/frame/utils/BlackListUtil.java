package helper.frame.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import helper.bo.BlackListBO;
import helper.bo.BlackListPlayer;
import helper.bo.SpgGames;
import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.constant.GameConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuYi
 */
public class BlackListUtil {

    public static void init() {
        boolean exist = FileUtil.exist(new File(GameConstant.BLACK_LIST_FILE));
        if (!exist) {
            FileUtil.mkdir(new File(GameConstant.BLACK_LIST_FILE));
        }
        if (AppCache.settingPersistence.getBlackListLoad()) {
            List<String> blacklistFiles = FileUtil.listFileNames(new File(GameConstant.BLACK_LIST_FILE).getAbsolutePath());
            List<BlackListBO> blackLists = new ArrayList<BlackListBO>();
            List<String> errorList = new ArrayList<String>();
            for (String s : blacklistFiles) {
                File blacklistFile = new File(GameConstant.BLACK_LIST_FILE + s);
                String jsonString = FileUtil.readUtf8String(blacklistFile);
                BlackListBO blackListBO = JSON.parseObject(jsonString, BlackListBO.class);
                if (blackListBO != null) {
                    blackLists.add(blackListBO);
                } else {
                    errorList.add(blacklistFile.getAbsolutePath());
                }
            }
            addBlackList(blackLists);
            if (!errorList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("含有非战绩文件请删除：\n");
                for (String s : errorList) {
                    sb.append(s);
                    sb.append("\n");
                }
                FrameTipUtil.errorMsg(sb.toString());
            }
        }
    }

    /**
     * 添加黑名单到配置文件 用于选择英雄时查询
     */
    public static void addBlackList(List<BlackListBO> blackListBOList) {
        Map<String, String> blackPlayersList = new HashMap<String, String>();
        for (BlackListBO blackListBO : blackListBOList) {
            for (BlackListPlayer player : blackListBO.getBlackListPlayers()) {
                blackPlayersList.put(player.getPuuid(), blackListBO.getSpgGames().getGameId().toString());
            }
        }
        AppCache.settingPersistence.setBlacklistPlayers(blackPlayersList);
        FrameTipUtil.infoMsg(blackListBOList.size() + "场战绩的" + blackPlayersList.size() + "名玩家已加入黑名单");
        AppCache.settingPersistence.setBlackListLoad(false);
        FrameConfigUtil.save();
    }

    /**
     * 添加黑名单json
     */
    public static void addBlackListPlayer(BlackListPlayer player, SpgGames game) throws RuntimeException {
        File file = new File(GameConstant.BLACK_LIST_FILE + game.getGameId() + ".json");
        List<BlackListPlayer> players = new ArrayList<BlackListPlayer>();
        if (FileUtil.exist(file)) {
            String s = FileUtil.readUtf8String(file);
            BlackListBO blackListBO = JSON.parseObject(s, BlackListBO.class);
            if (!AppCache.settingPersistence.getBlacklistPlayers().containsKey(player.getPuuid())) {
                AppCache.settingPersistence.getBlacklistPlayers().put(player.getPuuid(), game.getGameId().toString());
                blackListBO.getBlackListPlayers().add(player);
            } else {
                AppCache.settingPersistence.getBlacklistPlayers().compute(player.getPuuid(), (k, gameIdList) -> "," + gameIdList);
            }
            String jsonString = JSONObject.toJSONString(blackListBO);
            FileUtil.writeUtf8String(jsonString, file);
            FrameConfigUtil.save();
        } else {
            String mePuuid = GameDataCache.me.getPuuid();
            boolean flag = game.getParticipants().stream().anyMatch(item -> item.getPuuid().equals(mePuuid));
            if (flag) {
                players.add(player);
                boolean win = game.getParticipants().stream().filter(item -> item.getPuuid().equals(mePuuid)).findFirst().get().isWin();
                BlackListBO blackListBO = new BlackListBO(mePuuid, players, game, win);
                String jsonString = JSONObject.toJSONString(blackListBO);
                AppCache.settingPersistence.getBlacklistPlayers().put(player.getPuuid(), game.getGameId().toString());
                FrameConfigUtil.save();
                FileUtil.writeUtf8String(jsonString, file);
            } else {
                throw new RuntimeException("您不在对局中");
            }
        }
    }

    /**
     * 获取黑名单用户备注
     */
    public static String getPlayerRemark(String gameId, String puuid) {
        StringBuilder remark = new StringBuilder();
        String jsonString = FileUtil.readUtf8String(new File(GameConstant.BLACK_LIST_FILE + gameId + ".json"));
        BlackListBO blackListBO = JSON.parseObject(jsonString, BlackListBO.class);
        if (blackListBO.getBlackListPlayers().size() == 1) {
            remark.append(blackListBO.getBlackListPlayers().get(0).getRemark());
        } else {
            for (BlackListPlayer player : blackListBO.getBlackListPlayers()) {
                if (player.getPuuid().equals(puuid)) {
                    remark.append(player.getRemark());
                }
            }
        }
        return remark.toString();
    }

}
