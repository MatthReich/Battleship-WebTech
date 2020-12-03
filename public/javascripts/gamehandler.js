let startIsSet = false;
let playerState
let colTmp
let rowTmp
let gameLastState = ""

function handleShipSetClick(row, col) {
    if (startIsSet) {
        startIsSet = false
        var payload = {
            "row": rowTmp,
            "col": colTmp,
            "row2": row,
            "col2": col
        }
        sendRequest("POST", "/battleship/api/command", payload)
    } else {
        colTmp = col
        rowTmp = row
        startIsSet = true
    }
}

function handleClick(row, col) {
    var payload = {
        "row": row,
        "col": col,
        "row2": "",
        "col2": ""
    }
    sendRequest("POST", "/battleship/api/command", payload)
}

function sendRequest(type, path, payload) {
    var request = $.ajax({
        method: type,
        url: path,
        data: JSON.stringify(payload),
        dataType: "json",
        contentType: "application/json",
        success: function (JsonAr) {
            readJson(JsonAr)
        }
    });
}

function readJson(json) {
    gameState = json[3].gameState
    playerState = json[4].playerState

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
        updatePlayerName()
        if (playerState === "PLAYER_ONE") {
            updatePlayerName()
            updateGrid(json[0].grid1.cells, "")
            setShips(json[2].arraysInt.shipSetting)
        } else {
            updatePlayerName()
            updateGrid(json[1].grid2.cells, "")
            setShips(json[2].arraysInt.shipSetting2)
        }
        gameLastState = "SHIPSETTING"
    }
}

function updatePlayerName() {
    if (playerState === "PLAYER_ONE") {
        $("." + "playerName").html("<span>Merlin</span>")
    } else {
        $("." + "playerName").html("<span>Matthias</span>")
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
            if ((id == 1 && playerState === "PLAYER_TWO") || (id == 2 && playerState === "PLAYER_ONE"))
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

