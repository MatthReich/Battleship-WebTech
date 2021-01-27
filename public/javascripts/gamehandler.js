let startIsSet = false;
let playerState
let colTmp
let rowTmp
let gameLastState = ""
let websocket
let websocketClosed = false

function handleShipSetClick(row, col) {
    console.log("testtttstst")
    if (startIsSet) {
        startIsSet = false
        try {
            websocket.send(rowTmp + " " + colTmp + " " + row + " " + col)
        } catch (e) {
            if (!websocketClosed) {
                connectWebSocket();
                websocket.send(rowTmp + " " + colTmp + " " + row + " " + col)
            }
        }
    } else {
        colTmp = col
        rowTmp = row
        startIsSet = true
    }
}

function handleClick(row, col) {
    console.log("testttt567567stst")
    try {
        websocket.send(row + " " + col + " " + "test" + " " + "test")
    } catch (e) {
        if (!websocketClosed) {
            connectWebSocket();
            websocket.send(row + " " + col + " " + "test" + " " + "test")
        }
    }
}

function connectWebSocket() {
    if (!websocketClosed) {
        console.log("Connecting to Websocket");
        websocket = new WebSocket("wss://battleship-webtech.herokuapp.com/websocket");
        console.log("Connected to Websocket");
    }

    websocket.onopen = function (event) {
        console.log("Trying to connect to Server");
        websocket.send("Trying to connect to Server");
    }

    websocket.onclose = function () {
        console.log('Connection Closed!');
        setTimeout(connectWebSocket, 2000);
    };

    websocket.onerror = function (error) {
        console.log('Error Occured: ' + error);
    };

    websocket.onmessage = function (message) {
        try {
            const {event, object} = JSON.parse(message.data);
            console.log("websocket receiving message: " + event + " | " + object)
            switch (event) {
                case "cell-changed":
                    readJson(JSON.parse(object))
                    break
                case "player-changed":
                    readJson(JSON.parse(object))
                    break
            }
        } catch (e) {
            console.error(e)
        }
    };
}

function disconnectWebSocket() {
    websocket.close()
    websocketClosed = true
    console.log("user disconnected")
}

function reconnectWebSocket() {
    websocketClosed = false
    connectWebSocket()
    console.log("user connected")

}

function readJson(json) {
    gameState = json[3].gameState
    playerState = json[4].playerState
    console.log("reading json in gameState: " + gameState + " and playerState: " + playerState)

    if (gameState === "IDLE") {
        if ("SHIPSETTING" === gameLastState) {
            window.location = "/toIdle"
            gameLastState = ""
        }
        updateGrid(json[0].grid1.cells, "1")
        updateGrid(json[1].grid2.cells, "2")
    } else if (gameState === "SOLVED") {
        window.location = "/winningpage"
    } else if (gameState === "SHIPSETTING") {
        updatePlayerName(json[5].players.player1, json[5].players.player2)
        if (playerState === "PLAYER_ONE") {
            updateGrid(json[0].grid1.cells, "")
            setShips(json[2].arraysInt.shipSetting)
        } else {
            updateGrid(json[1].grid2.cells, "")
            setShips(json[2].arraysInt.shipSetting2)
        }
        gameLastState = "SHIPSETTING"
    }
}

function updatePlayerName(playerOne, playerTwo) {
    if (playerState === "PLAYER_ONE") {
        $("." + "playerName").html("<span>" + playerOne + "</span>")
    } else {
        $("." + "playerName").html("<span>" + playerTwo + "</span>")
    }
}

function playAgain() {
    window.location = "/playAgain"
}

function updateGrid(cells, id) {
    let col = 0
    let row = 0;
    for (let index = 0; index < 100; index++) {
        if (cells[index].valueY === 0) {
            $("#" + id + row + col).html("<span class=\"blue\">~</span>");
        } else if (cells[index].valueY === 1) {
            if ((id === "1" && playerState === "PLAYER_TWO") || (id === "2" && playerState === "PLAYER_ONE"))
                $("#" + id + row + col).html("<span class=\"blue\">~</span>");
            else
                $("#" + id + row + col).html("<span class=\"green\">x</span>");

        } else if (cells[index].valueY === 2) {
            $("#" + id + row + col).html("<span class=\"red\">x</span>");

        } else if (cells[index].valueY === 3) {
            $("#" + id + row + col).html("<span class=\"lightblue\">0</span>");
        }

        if (col === 9) {
            row++
            col = 0
        } else {
            col++
        }
    }
}

function setShips(ships) {
    for (let index = 0; index < 4; index++) {
        $("#ship" + index).html(ships[index] + " of the " + (index + 2) + " long ships");
    }
}

document.onreadystatechange = () => {
    if (document.readyState === "complete") {
        connectWebSocket();
    }
};
