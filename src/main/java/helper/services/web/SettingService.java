package helper.services.web;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import helper.bo.RankBO;
import helper.bo.SettingStateBO;
import helper.bo.SkinBO;
import helper.services.lcu.LinkLeagueClientApi;
import helper.vo.SkinVO;
import helper.cache.AppCache;
import helper.vo.SettingStateVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author WuYi
 */
@Service
@Slf4j
public class SettingService {
    @Resource
    private SettingStateBO settingStateBO;
    @Resource
    private LinkLeagueClientApi lcuApi;

    public SettingStateVO settingStateList() {
        return BeanUtil.toBean(settingStateBO, SettingStateVO.class);
    }

    public Boolean updateRank(RankBO rank) {
        try {
            lcuApi.setRank(rank);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void updateAutoAccept(Boolean flag) {
        settingStateBO.setAutoAccept(flag);
    }

    public void updateAutoSearch(Boolean flag) {
        settingStateBO.setAutoSearch(flag);
    }

    public void updateAutoPlayAgain(Boolean flag) {
        settingStateBO.setAutoPlayAgain(flag);
    }

    public void updateAutoReconnect(Boolean flag) {
        settingStateBO.setAutoReconnect(flag);
    }

    public void updateAutoKey(Boolean flag) {
        settingStateBO.setAutoKey(flag);
    }

    public void updateCommunicate(Boolean flag) {
        settingStateBO.setCommunicate(flag);
    }

    public Boolean updateClientState(String state) {
        try {
            lcuApi.changeStatus(state);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void updatePickChampion(Integer championId) {
        settingStateBO.setPickChampionId(championId);
    }

    public void updateBanChampion(Integer championId) {
        settingStateBO.setBanChampionId(championId);
    }

    public Boolean updateBackgroundSkin(SkinBO bo) {
        try {
            // 设置生涯背景
            JSONObject body = new JSONObject(2);
            body.put("key", "backgroundSkinId");
            body.put("value", bo.getSkinId());
            lcuApi.setBackgroundSkin(body.toJSONString());
            //皮肤增强
            if (StrUtil.isBlank(bo.getContentId())) {
                body.put("key", "backgroundSkinAugments");
                body.put("value", bo.getContentId());
                lcuApi.setBackgroundSkin(body.toJSONString());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
