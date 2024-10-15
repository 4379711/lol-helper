package helper.controller;

import helper.bo.*;
import helper.constant.R;
import helper.enums.SuccessEnum;
import helper.services.web.SettingService;
import helper.vo.SettingStateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author WuYi
 */
@RestController
@RequestMapping("/setting")
@Tag(name = "通用设置")
@Validated
public class SettingController {
    @Resource
    private SettingService settingService;

    @GetMapping("/list")
    @Operation(summary = "获取游戏设置")
    public R<SettingStateVO> settingStateList() {
        return R.ok(settingService.settingStateList());
    }

    @PostMapping("/rank/update")
    @Operation(summary = "设置段位")
    public R<?> updateRank(@RequestBody @Validated RankBO rank) {
        boolean result = settingService.updateRank(rank);
        if (result) {
            return R.ok();
        }
        return R.fail(SuccessEnum.ERROR, "设置段位错误");
    }

    @PostMapping("/autoAccept/update")
    @Operation(summary = "自动接受对局")
    public R<?> updateAutoAccept(@RequestBody @Validated BooleanBO bo) {
        settingService.updateAutoAccept(bo.getFlag());
        return R.ok();
    }

    @PostMapping("/autoSearch/update")
    @Operation(summary = "自动寻找对局")
    public R<?> updateAutoSearch(@RequestBody @Validated BooleanBO bo) {
        settingService.updateAutoSearch(bo.getFlag());
        return R.ok();
    }

    @PostMapping("/autoPlayAgain/update")
    @Operation(summary = "自动再来一局")
    public R<?> updateAutoPlayAgain(@RequestBody @Validated BooleanBO bo) {
        settingService.updateAutoPlayAgain(bo.getFlag());
        return R.ok();
    }

    @PostMapping("/autoReconnect/update")
    @Operation(summary = "掉线自动重连")
    public R<?> updateAutoReconnect(@RequestBody @Validated BooleanBO bo) {
        settingService.updateAutoReconnect(bo.getFlag());
        return R.ok();
    }

    @PostMapping("/autoKey/update")
    @Operation(summary = "一键连招")
    public R<?> updateAutoKey(@RequestBody @Validated BooleanBO bo) {
        settingService.updateAutoKey(bo.getFlag());
        return R.ok();
    }

    @PostMapping("/communicate/update")
    @Operation(summary = "互动模式")
    public R<?> updateCommunicate(@RequestBody @Validated BooleanBO bo) {
        settingService.updateCommunicate(bo.getFlag());
        return R.ok();
    }


    @PostMapping("/pickChampion/update")
    @Operation(summary = "设置秒选英雄")
    public R<?> updatePickChampion(@RequestBody @Validated ChampionBO bo) {
        settingService.updatePickChampion(bo.getChampionId());
        return R.ok();
    }

    @PostMapping("/banChampion/update")
    @Operation(summary = "设置禁用英雄")
    public R<?> updateBanChampion(@RequestBody @Validated ChampionBO bo) {
        settingService.updateBanChampion(bo.getChampionId());
        return R.ok();
    }

    @PostMapping("/backgroundSkin/update")
    @Operation(summary = "设置生涯背景")
    public R<?> updateBackgroundSkin(@RequestBody @Validated SkinBO bo) {
        Boolean flag = settingService.updateBackgroundSkin(bo);
        if (flag) {
            return R.ok();
        }
        return R.fail(SuccessEnum.ERROR, "设置生涯背景失败");
    }

    @PostMapping("/clientState/update")
    @Operation(summary = "设置客户端状态")
    public R<?> updatePickChampion(@RequestBody @Validated ClientStateBO bo) {
        boolean result = settingService.updateClientState(bo.getState());
        if (result) {
            return R.ok();
        }
        return R.fail(SuccessEnum.ERROR, "设置客户端错误");
    }

}
