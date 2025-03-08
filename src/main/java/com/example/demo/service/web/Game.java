package com.example.demo.service.web;

public class Game {
    private StateGame state;
    private String username1;
    private String username2;
    private boolean usernameOneIsReady;
    private boolean usernameTwoIsReady;
    private int[][] cells;
    private String playerTurn;

    public Game(String username1, String username2) {
        this.state = StateGame.NOT_STARTED;
        this.username1 = username1;
        this.username2 = username2;
        this.cells = new int[3][3];
        this.playerTurn = username1;
        this.usernameOneIsReady = false;
        this.usernameTwoIsReady = false;
    }

    public String getUsername1() {
        return username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsernameIsReady(String username) {
        if (username.equals(username1)) {
            this.usernameOneIsReady = true;
        } else {
            this.usernameTwoIsReady = true;
        }
    }

    public boolean PlayersAreReady() {
        return usernameOneIsReady == usernameTwoIsReady && usernameOneIsReady == true;
    }

    public void startGame() {
        this.state = StateGame.IN_PROGRESS;
    }

    public String getStateGame() {
        return this.state.toString();
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public boolean turn(String username, int xCell, int yCell) {
        if (username.equals(this.playerTurn)) {
            this.cells[xCell][yCell] = (username.equals(this.username1) ? 1 : 2);
            this.playerTurn = (username.equals(username2) ? username2 : username1);
            return isFinish();
        }
        return false;
    }

    public int[][] getCells() {
        return this.cells;
    }

    public boolean isFinish() {
        boolean verticalWin = verticalLineWin(0) || verticalLineWin(1) || verticalLineWin(2);
        boolean horizontalWin = horizontalLineWin(0) || horizontalLineWin(1) || horizontalLineWin(2);
        return diagonalLineWin() || verticalWin || horizontalWin;
    }

    //////////////////////////// Method to know if party is finished
    private boolean verticalLineWin(int index) {
        boolean sameValue = this.cells[index][0] == this.cells[index][1]
                && this.cells[index][2] == this.cells[index][1];
        boolean user1Oruser2 = this.cells[index][0] == 1 || this.cells[index][0] == 2;
        return sameValue && user1Oruser2;
    }

    private boolean horizontalLineWin(int index) {
        boolean sameValue = this.cells[0][index] == this.cells[1][index]
                && this.cells[2][index] == this.cells[1][index];
        boolean user1Oruser2 = this.cells[0][index] == 1 || this.cells[0][index] == 2;
        return sameValue && user1Oruser2;
    }

    private boolean diagonalLineWin() {
        boolean diagonalOne = this.cells[1][1] == this.cells[0][0] && this.cells[2][2] == this.cells[1][1];
        boolean diagonalTwo = this.cells[1][1] == this.cells[0][2] && this.cells[2][0] == this.cells[1][1];
        boolean user1Oruser2 = this.cells[1][1] == 1 || this.cells[1][1] == 2;
        return (diagonalOne || diagonalTwo) && user1Oruser2;
    }
}