package com.kodilla.rps.gameLogic;

import com.kodilla.rps.GameDataBase;
import com.kodilla.rps.GameGui;
import com.kodilla.rps.moves.*;

import java.util.List;

public class GameLogic {
    private final GameDataBase gameDataBase;
    private boolean hardMode = true;

    public boolean isHardMode() {
        return hardMode;
    }

    private final ComputerHardModeOrEasy computerHardModeOrEasy = new ComputerHardModeOrEasy();

    public GameLogic(GameDataBase gameDataBase) {
        this.gameDataBase = gameDataBase;
    }

    public void whatOptionUserChooseAndDecideWhatToDo() {
        String s = gameDataBase.getOptionThatUserChoose();
        switch (s) {
            case "1" -> gameDataBase.addUserMoveInThisRound(new Rock());
            case "2" -> gameDataBase.addUserMoveInThisRound(new Paper());
            case "3" -> gameDataBase.addUserMoveInThisRound(new Scissors());
            case "x" -> printMessageToUserAndMakeActionDependsIfHeWantsEndTheGameOrNo();
            case "n" -> printMessageToUserAndMakeActionDependsIfHeWantsResetTheGameOrNo();
        }
    }

    public void printMessageToUserAndMakeActionDependsIfHeWantsEndTheGameOrNo() {
        if (gameDataBase.getTheUserChoosesIsYOrN().contains("y")) {
            GameGui.whenUserChooseYesToEndGame();
            gameDataBase.setEndGame(true);
            gameDataBase.setResetGame(true);
        } else {
            GameGui.whenUserChooseNoToEndGame();
        }
    }

    public void printMessageToUserAndMakeActionDependsIfHeWantsResetTheGameOrNo() {
        if (gameDataBase.getTheUserChoosesIsYOrN().contains("y")) {
            GameGui.whenUserChooseYesToResetTheGame();
            gameDataBase.setResetGame(true);
        } else {
            GameGui.whenUserChooseNoToResetTheGame();
        }
    }

    private String takeComputerMove() {
        List<GameFigures> listWithComputerMovements = gameDataBase.getRecordOfAllComputerRounds();
        int actualRound = gameDataBase.getCurrentRound();
        return listWithComputerMovements.get(actualRound).getClass().getSimpleName();
    }

    private String takeUserMove() {
        List<GameFigures> listWithUserMovements = gameDataBase.getRecordOfAllUserRounds();
        int actualRound = gameDataBase.getCurrentRound();
        return listWithUserMovements.get(actualRound).getClass().getSimpleName();
    }

    private void logicForRock() {
        computerHardModeOrEasy.decideWithComputerModeUseForRockLogic(isHardMode(),gameDataBase);
        String s = takeComputerMove();
        switch (s) {
            case "Rock" -> GameGui.whenIsDraw(s);
            case "Paper" -> {
                gameDataBase.setCounterOfComputerWins();
                GameGui.whenIsComputerWin(s);
            }
            case "Scissors" -> {
                gameDataBase.setCounterOfUserWins();
                GameGui.whenIsComputerLose(s);
            }
        }
    }

    private void logicForPaper() {
        computerHardModeOrEasy.decideWithComputerModeUseForPaperLogic(isHardMode(),gameDataBase);
        String s = takeComputerMove();
        switch (s) {
            case "Paper" -> GameGui.whenIsDraw(s);
            case "Scissors" -> {
                gameDataBase.setCounterOfComputerWins();
                GameGui.whenIsComputerWin(s);
            }
            case "Rock" -> {
                gameDataBase.setCounterOfUserWins();
                GameGui.whenIsComputerLose(s);
            }
        }
    }

    private void logicForScissors() {
        computerHardModeOrEasy.decideWithComputerModeUseForScissorsLogic(isHardMode(),gameDataBase);
        String s = takeComputerMove();
        switch (s) {
            case "Paper" -> {
                gameDataBase.setCounterOfUserWins();
                GameGui.whenIsComputerLose(s);
            }
            case "Scissors" -> GameGui.whenIsDraw(s);
            case "Rock" -> {
                gameDataBase.setCounterOfComputerWins();
                GameGui.whenIsComputerWin(s);
            }
        }
    }

    private void takeUserMovementInThisRoundAndPassItFurther() {
        String s = takeUserMove();
        GameGui.infoWhatUserChoose(s, gameDataBase);
        switch (s) {
            case "Rock" -> logicForRock();
            case "Paper" -> logicForPaper();
            case "Scissors" -> logicForScissors();
        }
    }

    private void runTheLogicResponsibleForCheckingWhoWonTheRoundIfResetIsNotTrue() {
        if (!gameDataBase.isResetGame()) {
            takeUserMovementInThisRoundAndPassItFurther();
            GameGui.resultOfRound(gameDataBase);
            gameDataBase.setCurrentRound();
            GameGui.infoAboutNumberCurrentRound(gameDataBase);
        }
    }

    public void resultOfRoundAndGoToNextRound() {
        runTheLogicResponsibleForCheckingWhoWonTheRoundIfResetIsNotTrue();
    }

    public void checkIfIsNotEndOfGame(){
        int endGame = gameDataBase.getNumberOfSmalWinsToWinTheGame();
        int userWins = gameDataBase.getCounterOfUserWins();
        int computerWins = gameDataBase.getCounterOfComputerWins();

    }

    public boolean playerWinTheGame(int endGame, int userWin){
        if (endGame == userWin){
            GameGui.userWinTheGame(gameDataBase);
            return true;
        }
        return false;
    }
    public boolean computerWinTheGame(int endGame, int computerWins){
        if (endGame == computerWins){
            GameGui.computerWinTheGame(gameDataBase);
            return true;
        }
        return false;
    }

    public void setEndTheGame(){

    }

}