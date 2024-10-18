package helper.controller;

import helper.bo.TextEditBO;
import helper.constant.R;
import helper.enums.SuccessEnum;
import helper.services.web.FuckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WuYi
 */
@RestController
@RequestMapping("/fuck")
@Tag(name = "问候功能")
@Validated
@Slf4j
public class FuckController {
    @Resource
    private FuckService fuckService;

    @GetMapping("text")
    @Operation(summary = "获取问候文本")
    public R<List<String>> text() {
        return R.ok(fuckService.getFuckText());
    }

    @PostMapping("edit")
    @Operation(summary = "编辑文本")
    public R<?> edit(@RequestBody @Validated TextEditBO bo) {
        boolean flag = fuckService.editText(bo);
        if (flag) {
            return R.ok();
        }
        return R.fail(SuccessEnum.ERROR, "编辑文本失败请检查参数");
    }


}

