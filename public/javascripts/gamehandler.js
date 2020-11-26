var lastCall;
var startisSet = false;
var endcall;

function handleShipSetClick(row, col) {
    if (startisSet) {
        endcall = lastCall + " " + row + " " + col
        lastCall = null
        startisSet = false
        window.location = "http://localhost:9000/setShip/" + endcall
    } else {
        lastCall = row + " " + col
        startisSet = true
    }
}

function handleClick(row, col) {
    window.location = "http://localhost:9000/idle/" + row + " " + col
}