let startIsSet = false;
let call = "";

function handleShipSetClick(row, col) {
    if (startIsSet) {
        call += " " + row + " " + col
        startIsSet = false
        window.location = "http://localhost:9000/setShip/" + call
    } else {
        call = row + " " + col
        startIsSet = true
    }
}

function handleClick(row, col) {
    window.location = "http://localhost:9000/idle/" + row + " " + col
}

function playAgain() {
    window.location = "http://localhost:9000/playAgain"
}