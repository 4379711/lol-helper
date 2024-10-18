package helper.controller;

import helper.bo.*;
import helper.constant.R;
import helper.services.web.AssetsService;
import helper.vo.ChampionVO;
import helper.vo.PlayerVO;
import helper.vo.SettingRankVO;
import helper.vo.SkinVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author WuYi
 */
@RestController
@RequestMapping("/assets")
@Tag(name = "客户端资源")
public class AssetsController {
    @Resource
    private AssetsService assetsService;

    @PostMapping("/skin/list")
    @Operation(summary = "获取指定英雄所有皮肤")
    public R<List<SkinVO>> skinList(@RequestBody ChampionBO bo) {
        return R.ok(assetsService.getSkinList(bo.getChampionId()));
    }

    @GetMapping("/champion/list")
    @Operation(summary = "获取所有英雄")
    public R<List<ChampionVO>> championList() {
        return R.ok(assetsService.getChampionList());
    }

    @GetMapping("/queue/list")
    @Operation(summary = "获取所有模式(除TFT)")
    public R<Map<Integer, GameQueue>> queueList() {
        return R.ok(assetsService.getAllQueue());
    }

    @GetMapping("/summonerSpell/list")
    @Operation(summary = "获取召唤师技能")
    public R<List<SummonerSpellsBO>> summonerSpellList() {
        return R.ok(assetsService.getAllSummonerSpells());
    }

    @GetMapping("/perk/list")
    @Operation(summary = "获取所有符文天赋")
    public R<List<PerkBO>> perkList() {
        return R.ok(assetsService.getAllPerk());
    }

    @GetMapping("/item/list")
    @Operation(summary = "获取所有道具")
    public R<List<LOLItemBO>> itemList() {
        return R.ok(assetsService.getAllItems());
    }

    @GetMapping("/perkStyle/list")
    @Operation(summary = "获取符文基石")
    public R<List<PerkStyleBO>> perkStyleList() {
        return R.ok(assetsService.getAllPerkStyleBO());
    }

    @PostMapping("/championIcon")
    @Operation(summary = "获取英雄头像地址")
    public R<String> perkStyleList(@RequestBody ChampionBO bo) {
        return R.ok(assetsService.getChampionIcon(bo.getChampionId()));
    }

    @GetMapping("/currentPlayer")
    @Operation(summary = "获取登录人信息")
    public R<PlayerVO> currentPlayer() {
        return R.ok(assetsService.getCurrentPlayer());
    }

    @GetMapping("/region")
    @Operation(summary = "获取当前大区")
    public R<String> region() {
        return R.ok(assetsService.getRegion());
    }

    @GetMapping("/settingRank")
    @Operation(summary = "获取用于设置伪造的段位")
    public R<SettingRankVO> SettingRank() {
        return R.ok(new SettingRankVO());
    }

    @GetMapping("/recordRank")
    @Operation(summary = "获取战绩段位")
    public R<Map<String, String>> RecordRank() {
        return R.ok(assetsService.getRecordRank());
    }


}
