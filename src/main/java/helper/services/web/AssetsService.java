package helper.services.web;

import helper.bo.*;
import helper.cache.AppCache;
import helper.cache.GameDataCache;
import helper.frame.constant.GameConstant;
import helper.utils.ProcessUtil;
import helper.vo.ChampionVO;
import helper.vo.PlayerVO;
import helper.vo.SkinVO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author WuYi
 */
@Service
public class AssetsService {
    
    @SneakyThrows
    public List<SkinVO> getSkinList(Integer championId) {
        return AppCache.api.getSkinByChampionId(championId);
    }

    @SneakyThrows
    public List<ChampionVO> getChampionList() {
        return GameDataCache.allChampion;
    }

    @SneakyThrows
    public Map<Integer, GameQueue> getAllQueue() {
        return GameDataCache.allGameQueuesList;
    }

    @SneakyThrows
    public List<SummonerSpellsBO> getAllSummonerSpells() {
        return GameDataCache.summonerSpellsList;
    }

    @SneakyThrows
    public List<PerkBO> getAllPerk() {
        return GameDataCache.perkList;
    }

    @SneakyThrows
    public List<LOLItemBO> getAllItems() {
        return GameDataCache.itemList;
    }

    @SneakyThrows
    public List<PerkStyleBO> getAllPerkStyleBO() {
        return GameDataCache.perkStyleList;
    }
    @SneakyThrows
    public String getChampionIcon(Integer championId) {
        return AppCache.api.getChampionIconUrl(championId);
    }
    @SneakyThrows
    public Boolean loadImage(String url) {
        return AppCache.api.loadImage(url);
    }

    @SneakyThrows
    public PlayerVO getCurrentPlayer() {
        return AppCache.api.getCurrentSummoner();
    }

    public Map<String, String> getRecordRank() {
        return GameConstant.RANK;
    }

    @SneakyThrows
    public String getRegion() {
        return ProcessUtil.getClientProcess().getRegion();
    }
}
