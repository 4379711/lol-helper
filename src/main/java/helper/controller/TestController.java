package helper.controller;

import cn.hutool.core.date.DateTime;
import helper.constant.R;
import helper.dto.TestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @_@
 */
@RestController
@RequestMapping("/test")
@Tag(name = "测试一下")
@Validated
public class TestController {

	@Operation(summary = "测试")
	@GetMapping("/test1")
	public R<TestDto> login() {
		TestDto testDto = new TestDto();
		testDto.setMsg("ok");
		testDto.setDateTime(DateTime.now());
		return R.ok(testDto);
	}
}
