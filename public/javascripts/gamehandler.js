let startIsSet = false;
let call = "";

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
}

function playAgain() {
    window.location = "/playAgain"
}
