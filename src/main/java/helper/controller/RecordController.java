package helper.controller;

import helper.bo.BooleanBO;
import helper.bo.GameModeSelectBO;
import helper.constant.R;
import helper.services.web.RecordService;
import helper.vo.SettingRecordVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author WuYi
 */
@RestController
@RequestMapping("/record")
@Tag(name = "战绩设置")
@Validated
public class RecordController {
    @Resource
    private RecordService recordService;

    @GetMapping("/list")
    @Operation(summary = "获取游戏战绩设置")
    public R<SettingRecordVO> getRecord() {
        return R.ok(recordService.getSettingRecord());
    }

    @PostMapping("/sendScore/update")
    @Operation(summary = "发送战绩")
    public R<?> sendScoreUpdate(@RequestBody @Validated BooleanBO bo) {
        recordService.sendScoreUpdate(bo.getFlag());
        return R.ok();
    }

    @PostMapping("/showTeamRecord/update")
    @Operation(summary = "显示队友战绩")
    public R<?> showTeamRecordUpdate(@RequestBody @Validated BooleanBO bo) {
        recordService.showTeamRecordUpdate(bo.getFlag());
        return R.ok();
    }

    @PostMapping("/gameModeSelect/update")
    @Operation(summary = "选择筛选模式")
    public R<?> gameModeSelectUpdate(@RequestBody @Validated GameModeSelectBO bo) {
        recordService.gameModeSelectUpdate(bo.getGameModeSelect());
        return R.ok();
    }
}
