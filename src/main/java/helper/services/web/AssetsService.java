package helper.services.web;

import helper.bo.*;
import helper.cache.AppCache;
import helper.services.lcu.LinkLeagueClientApi;
import helper.vo.ChampionVO;
import helper.vo.SkinVO;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author WuYi
 */
@Service
public class AssetsService {
    
    @Resource
    private LinkLeagueClientApi lcuApi;

    @SneakyThrows
    public List<SkinVO> getSkinList(Integer championId) {
        return lcuApi.getSkinByChampionId(championId);
    }

    @SneakyThrows
    public List<ChampionVO> getChampionList() {
        return lcuApi.getAllChampion();
    }

    @SneakyThrows
    public Map<Integer, GameQueue> getAllQueue() {
        return lcuApi.getAllQueue();
    }

    @SneakyThrows
    public List<SummonerSpellsBO> getAllSummonerSpells() {
        return lcuApi.getAllSummonerSpells();
    }

    @SneakyThrows
    public List<PerkBO> getAllPerk() {
        return lcuApi.getAllPerk();
    }

    @SneakyThrows
    public List<LOLItemBO> getAllItems() {
        return lcuApi.getAllItems();
    }

    @SneakyThrows
    public List<PerkStyleBO> getAllPerkStyleBO() {
        return lcuApi.getAllPerkStyleBO();
    }
    @SneakyThrows
    public String getChampionIcon(Integer championId) {
        return lcuApi.getChampionIconUrl(championId);
    }
    @SneakyThrows
    public Boolean loadImage(String url) {
        return lcuApi.loadImage(url);
    }

}
