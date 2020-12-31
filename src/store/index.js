import Vue from 'vue'
import Vuex from 'vuex'
import $ from 'jquery'

Vue.use(Vuex)

const initState = {
    grid1:{},
    grid2:{},
    shipSetting1:{},
    shipSetting2:{},
    gameState:{},
    playerState:{},
    player1:{},
    player2:{}
}

var websocket = new WebSocket("ws://localhost:9000/websocket");

const store = new Vuex.Store({
    state: initState,
    actions: {
        getJson({commit}) {
            $.ajax({
                method: "GET", url: "http://localhost:9000/json", dataType: "json", contentType: "application/json",
                success: function (response) {
                    console.log("Connected")
                    commit('SET_OBJECT', response)
                },
                error:function(){
                    console.log("Something went wrong");
                }
            })
        }
    },
    mutations:{
        SET_OBJECT(state, object) {
            state.grid1 = object
            state.grid2 = object
            state.shipSetting1 = object
            state.shipSetting2 = object
            state.gameState = object
            state.playerState = object
            state.player1 = object
            state.player2 = object
        }
    }
})

websocket.onopen = function() {
    console.log("Trying to connect to Server");
}
websocket.onclose = function () {
    console.log('Connection Closed!');
};
websocket.onerror = function (error) {
    console.log('Error Occured: ' + error);
};
websocket.onmessage = function (message){
    const object = JSON.parse(message.data);
    console.log("Reseived Message")
    store.commit('SET_OBJECT', object)
}

export default store;
