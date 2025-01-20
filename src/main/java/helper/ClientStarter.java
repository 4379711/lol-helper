package helper;

import helper.bo.LeagueClientBO;
import helper.cache.AppCache;
import helper.exception.NoLcuApiException;
import helper.exception.NoProcessException;
import helper.http.RequestLcuUtil;
import helper.http.RequestSgpUtil;
import helper.services.lcu.LinkLeagueClientApi;
import helper.services.sgp.RegionSgpApi;
import helper.services.strategy.StrategyStarter;
import helper.utils.ProcessUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author @_@
 */
@Slf4j
public class ClientStarter {

	public void initLcu() throws Exception {
		LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
		if (leagueClientBO.equals(new LeagueClientBO())) {
			leagueClientBO = ProcessUtil.getClientProcessByWmic();
			if(leagueClientBO.equals(new LeagueClientBO())){
				throw new NoProcessException();
			}
		}
		RequestLcuUtil requestUtil = new RequestLcuUtil(leagueClientBO);
		AppCache.api = new LinkLeagueClientApi(requestUtil);
	}

	public void initSgp() throws Exception {
		LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
		if (leagueClientBO.equals(new LeagueClientBO())) {
			leagueClientBO = ProcessUtil.getClientProcessByWmic();
			if(leagueClientBO.equals(new LeagueClientBO())){
				throw new NoProcessException();
			}
		}
		if (AppCache.api == null) {
			throw new NoLcuApiException();
		}
		if (leagueClientBO.getRegion() != null) {
			RequestSgpUtil requestUtil = new RequestSgpUtil(AppCache.api.getSgpAccessToken(), leagueClientBO.getRegion().toLowerCase());
			AppCache.sgpApi = new RegionSgpApi(requestUtil);
		} else {
			log.error("未识别到国服大区");
		}

	}

	public void startWSS() throws IOException {
		AppCache.api.openWss();
	}

	public void initGameStatus() throws IOException {
		StrategyStarter.listenGameStatus(AppCache.api.getGameStatus());
	}

}
