Vue.component('ship-field', {
    template: `
        <div class="gamecontainer">
            <div class="game">
                <div class="player">
                    <div class="playerName"></div>
                    <div v-for="row in 10" class="grid-container">
                        <div v-for="col in 10" class="grid-item" v-bind:id="(row-1)+''+(col-1)"></div>
                    </div>
                    <div class="shipSetPossibilities">
                        <div class="separation">
                            <div v-for="nr in 4" v-bind:id="'ship'+(nr-1)""></div>
                        </div>
                </div>
            </div>
        </div>
    </div>
    `,
})

Vue.component('idle-field', {
    template: `
        <div class="gamecontainer">
            <div class="game row">
                <div class="player1 col-md-6">
                    <div class="playerName1"></div>
                    <div v-for="row in 10" class="grid-container">
                        <div v-for="col in 10" class="grid-item" v-bind:id="'1'+(row-1)+''+(col-1)"></div>
                    </div>
                </div>
                <div class="player1 col-md-6">
                    <div class="playerName2"></div>
                    <div v-for="row in 10" class="grid-container">
                        <div v-for="col in 10" class="grid-item" v-bind:id="'2'+(row-1)+''+(col-1)"></div>
                    </div>
                </div>
            </div>
        </div>
    `
})


$(document).ready(function () {
    var game = new Vue({
        el: '#game'
    })

})
