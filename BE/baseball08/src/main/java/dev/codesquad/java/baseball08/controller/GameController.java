package dev.codesquad.java.baseball08.controller;

import dev.codesquad.java.baseball08.dto.dto.AvailableDto;
import dev.codesquad.java.baseball08.dto.response.GameListResponse;
import dev.codesquad.java.baseball08.dto.response.GamePlayResponse;
import dev.codesquad.java.baseball08.dto.response.TeamScoreResponse;
import dev.codesquad.java.baseball08.service.GameService;
import dev.codesquad.java.baseball08.service.alex.PitchService;
import dev.codesquad.java.baseball08.service.henry.PitchServiceHenry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;
    private final PitchService pitchService;
    private final PitchServiceHenry pitchServiceHenry;

    // 게임 초기 데이터 출력
    @GetMapping("/game")
    public ResponseEntity<List<GameListResponse>> gameInit() {
        return new ResponseEntity<>(gameService.getGameList(), HttpStatus.OK);
    }

    // 게임 플레이 가능 여부 API
    @GetMapping("/game/{gameId}")
    public ResponseEntity<AvailableDto> isGamePossible(@PathVariable("gameId") Long gameId) {
        return new ResponseEntity<>(gameService.isGamePossible(gameId), HttpStatus.OK);
    }

    @GetMapping("/game/{gameId}/select")
    public ResponseEntity<HttpStatus> selectGame(@PathVariable("gameId") Long gameId) {
        if (gameService.isGamePossible(gameId).isAvailable()) {
            gameService.updateGameStatus(gameId, true);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        gameService.updateGameStatus(gameId, false);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Game의 team별 스코어 가져오는 API
    @GetMapping("/detail/{gameId}/score")
    public ResponseEntity<List<TeamScoreResponse>> getGameScore(@PathVariable("gameId") Long gameId) {
        return new ResponseEntity<>(gameService.getGameScore(gameId), HttpStatus.OK);
    }

    @GetMapping("/pitch/{teamId}")
    public ResponseEntity<HttpStatus> playGame(@PathVariable("teamId") Long teamId) {
        pitchService.pitch(teamId); // service
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/detail/{gameId}")
    public ResponseEntity<GamePlayResponse> getGameInfo(@PathVariable("gameId") Long gameId) {
        return new ResponseEntity<>(gameService.getGamePlay(gameId), HttpStatus.OK);
    }
}
