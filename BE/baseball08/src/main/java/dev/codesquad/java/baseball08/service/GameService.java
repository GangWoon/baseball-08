package dev.codesquad.java.baseball08.service;

import dev.codesquad.java.baseball08.dao.GameDao;
import dev.codesquad.java.baseball08.dto.AvailableDto;
import dev.codesquad.java.baseball08.dao.henry.GameDao2;
import dev.codesquad.java.baseball08.dto.henry.AvailabilityResponse;
import dev.codesquad.java.baseball08.dto.henry.GameListResponse;
import dev.codesquad.java.baseball08.dto.henry.GamePlayResponse;
import dev.codesquad.java.baseball08.dto.StageDto;
import dev.codesquad.java.baseball08.entity.History;
import dev.codesquad.java.baseball08.entity.Inning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {
    private Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameDao2 gameDao2;

    @Autowired
    private GameDao gameDao;

    public AvailableDto isGamePossible(Long game) {
        return new AvailableDto(!gameDao.getGameUserId(game).contains(null));
    }

    public AvailabilityResponse isGameAvailable(Long id) {
        return gameDao2.IsGameAvailable(id);
    }

    public List<GameListResponse> getGameList() {
        return gameDao2.findAllGame();
    }

    public GamePlayResponse getGamePlay(Long id, Long teamId) {
        GamePlayResponse gamePlayResponse = gameDao2.findGameInfoById(id);
        GamePlayResponse gamePlayResponse2 = gameDao2.findGameTeamInfoById(id, teamId);
        gamePlayResponse.setUser(gamePlayResponse2.getUser());
        gamePlayResponse.setPitcher(gamePlayResponse2.getPitcher());
        gamePlayResponse.setHitter(gamePlayResponse2.getHitter());
        gamePlayResponse.setHistory(gamePlayResponse2.getHistory());
        //return gameDao2.findGameInfoById(id);
        return gamePlayResponse;
    }

    public List<StageDto> getGameInfo() {
        return gameDao.getGameInfo();
    }

    public void saveHistory() {
        int gameKey = gameDao.getHistoryCount(1L);
        History history = new History("test", 1, "S", 1L, gameKey);
        gameDao.saveHistory(history);
    }

    public void saveNewInning() {
        int gameKey = gameDao.getGameKeyForInning(1L);
        List<String> teamNames = gameDao.getTeamNamesByGameId(1L);
        Inning inning = Inning.builder()
                .awayName(teamNames.get(1)).homeName(teamNames.get(0))
                .awayScore(0).homeScore(0)
                .strikeCount(0).ballCount(0).outCount(0).baseCount(0)
                .game(1L)
                .game_key(gameKey)
                .build();
        gameDao.saveInning(inning);
    }

    public void updateInning(Long game, String teamName) {

    }
}
