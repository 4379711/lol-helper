package helper.services.web;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import helper.bo.RankBO;
import helper.bo.SkinBO;
import helper.cache.AppCache;
import helper.cache.GlobalData;
import helper.vo.SettingStateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author WuYi
 */
@Service
@Slf4j
public class SettingService {


    public SettingStateVO settingStateList() {
        return BeanUtil.toBean(GlobalData.settingState, SettingStateVO.class);
    }

    public Boolean updateRank(RankBO rank) {
        try {
            AppCache.api.setRank(rank);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void updateAutoAccept(Boolean flag) {
        GlobalData.settingState.setAutoAccept(flag);
    }

    public void updateAutoSearch(Boolean flag) {
        GlobalData.settingState.setAutoSearch(flag);
    }

    public void updateAutoPlayAgain(Boolean flag) {
        GlobalData.settingState.setAutoPlayAgain(flag);
    }

    public void updateAutoReconnect(Boolean flag) {
        GlobalData.settingState.setAutoReconnect(flag);
    }

    public void updateAutoKey(Boolean flag) {
        GlobalData.settingState.setAutoKey(flag);
    }

    public void updateCommunicate(Boolean flag) {
        GlobalData.settingState.setCommunicate(flag);
    }

    public Boolean updateClientState(String state) {
        try {
            AppCache.api.changeStatus(state);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void updatePickChampion(Integer championId) {
        GlobalData.settingState.setPickChampionId(championId);
    }

    public void updateBanChampion(Integer championId) {
        GlobalData.settingState.setBanChampionId(championId);
    }

    public Boolean updateBackgroundSkin(SkinBO bo) {
        try {
            // 设置生涯背景
            JSONObject body = new JSONObject(2);
            body.put("key", "backgroundSkinId");
            body.put("value", bo.getSkinId());
            AppCache.api.setBackgroundSkin(body.toJSONString());
            //皮肤增强
            if (StrUtil.isBlank(bo.getContentId())) {
                body.put("key", "backgroundSkinAugments");
                body.put("value", bo.getContentId());
                AppCache.api.setBackgroundSkin(body.toJSONString());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
