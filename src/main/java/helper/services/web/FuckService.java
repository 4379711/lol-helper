package helper.services.web;

import helper.bo.TextEditBO;
import helper.cache.AppCache;
import helper.enums.EditEnum;
import helper.utils.GlobalDataUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WuYi
 */
@Service
public class FuckService {

    public List<String> getFuckText() {
        return AppCache.garbageWordList;
    }

    public boolean editText(TextEditBO bo) {
        boolean result = false;
        EditEnum editType = bo.getEditType();
        switch (editType) {
            case ADD -> {
                if (bo.getIndex() == null) {
                    AppCache.garbageWordList.add(bo.getText());
                } else {
                    if (bo.getIndex() < AppCache.garbageWordList.size()) {
                        AppCache.garbageWordList.add(bo.getIndex(), bo.getText());
                    } else {
                        AppCache.garbageWordList.add(bo.getText());
                    }
                }
                result = true;
            }
            case DELETE -> {
                if (bo.getIndex() != null && bo.getIndex() < AppCache.garbageWordList.size()) {
                    AppCache.garbageWordList.remove(bo.getIndex().intValue());
                    result = true;
                }
            }
            case UPDATE -> {
                if (bo.getIndex() != null && bo.getIndex() < AppCache.garbageWordList.size()) {
                    AppCache.garbageWordList.set(bo.getIndex(), bo.getText());
                    result = true;
                }
            }
        }
        if (result) {
            GlobalDataUtil.saveWordsFile();
            return result;
        }
        return result;
    }
}
