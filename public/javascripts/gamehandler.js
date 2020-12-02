let startIsSet = false;
let call = "";
let playerState

function handleShipSetClick(row, col) {
    if (startIsSet) {
        call += " " + row + " " + col
        startIsSet = false
        window.location = "/setShip/" + call
    } else {
        call = row + " " + col
        startIsSet = true
    }
}

function handleClick(row, col) {
    var payload = {
        "row": row,
        "col": col
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
    if (gameState == "IDLE"){
        playerState = json[4].playerState
        updateGrid(json[0].grid1.cells, "1")
        updateGrid(json[1].grid2.cells, "2")
    } else if (gameState == "SOLVED"){
        window.location = "/winningpage"
    } else if (gameState == "SHIPSETTING"){

    }
}

function playAgain() {
    window.location = "/playAgain"
}

function updateGrid(cells, id) {
    let col = 0
    let row = 0;
    for (let index = 0; index < 100; index++) {
        if (cells[index].valueY == 0) {
            $("#" +id+ row + col).html("<span class=\"blue\">~</span>");
        } else if (cells[index].valueY == 1) {
            if ((id == 1 && playerState == "PLAYER_TWO")||(id == 2 && playerState == "PLAYER_ONE"))
                $("#" +id+ row + col).html("<span class=\"blue\">~</span>");
            else
                $("#" +id+ row + col).html("<span class=\"green\">x</span>");

        } else if (cells[index].valueY == 2) {
            $("#" +id+ row + col).html("<span class=\"red\">x</span>");

        } else if (cells[index].valueY == 3) {
            $("#" +id+ row + col).html("<span class=\"lightblue\">0</span>");
        }

        if (col == 9){
            row++
            col = 0
        }else{
            col++
        }
    }
}

