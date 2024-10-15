package helper.services.web;

import cn.hutool.core.bean.BeanUtil;
import helper.bo.SettingRecordBO;
import helper.vo.SettingRecordVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WuYi
 */
@Service
@Slf4j
public class RecordService {
    @Resource
    private SettingRecordBO settingRecordBO;

    public SettingRecordVO getSettingRecord() {
        return BeanUtil.toBean(settingRecordBO, SettingRecordVO.class);
    }

    public void sendScoreUpdate(Boolean flag) {
        settingRecordBO.setSendScore(flag);
    }

    public void showTeamRecordUpdate(Boolean flag) {
        settingRecordBO.setShowTeamRecord(flag);
    }

    public void gameModeSelectUpdate(List<Integer> gameModeSelect) {
        settingRecordBO.setGameModeSelect(gameModeSelect);
    }
}
