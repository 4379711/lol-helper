package helper.services.web;

import cn.hutool.core.bean.BeanUtil;
import helper.bo.*;
import helper.cache.AppCache;
import helper.cache.GlobalData;
import helper.vo.SettingRecordVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WuYi
 */
@Service
@Slf4j
public class RecordService {


    public SettingRecordVO getSettingRecord() {
        return BeanUtil.toBean(GlobalData.settingRecord, SettingRecordVO.class);
    }

    public void sendScoreUpdate(Boolean flag) {
        GlobalData.settingRecord.setSendScore(flag);
    }

    public void showTeamRecordUpdate(Boolean flag) {
        GlobalData.settingRecord.setShowTeamRecord(flag);
    }

    public void gameModeSelectUpdate(List<Integer> gameModeSelect) {
        GlobalData.settingRecord.setGameModeSelect(gameModeSelect);
    }

    @SneakyThrows
    public ProductsMatchHistoryBO getMatchLcu(LcuMatchBO bo) {
        return AppCache.api.getProductsMatchHistoryByPuuid(bo.getPuuid(), bo.getStartIndex(), bo.getStartIndex());
    }

    @SneakyThrows
    public List<SpgProductsMatchHistoryBO> getMatchSgp(SgpMatchBO bo) {
        return AppCache.sgpApi.getProductsMatchHistoryByPuuid(bo.getRegion().toString(), bo.getPuuid(), bo.getStartIndex(), bo.getCount());
    }

    @SneakyThrows
    public Rank getRank(String puuid) {
        return AppCache.api.getRank(puuid);
    }
}
