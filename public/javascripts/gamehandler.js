let startIsSet = false;
let call = "";
var Data = {}

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
    window.location = "/idle/" + row + " " + col
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
        success: function(JsonAr){
            readJson(JsonAr)
        }
    })
    request.done(function(JsonAr) {
        readJson(JsonAr)
    });
}

function readJson(json){
    Data[0] = json[0]
    Data[1] = json[1]
    console.log(Data[0])
}

function playAgain() {
    window.location = "/playAgain"
}
