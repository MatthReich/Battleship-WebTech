<template>
  <div>
    <div v-if="getGameState() === shipSetting">
      <br />
      <div v-if="getPlayerID() === player1">
        <div v-if="getPlayerState() === p1">
          <playername1 v-bind:player-name="player1" />
          <gridmodel1 v-bind:player-name="player1" />
          <shipsetting v-bind:player-name="player1" />
          <br />
        </div>
        <div v-else>
          <br />
          <h2 class="text-center">
            Waiting for other player to seting his/her ships
          </h2>
          <br />
        </div>
      </div>
      <div v-else-if="getPlayerID() === player2">
        <div v-if="getPlayerState() === p2">
          <playername1 v-bind:player-name="player2" />
          <gridmodel1 v-bind:player-name="player2" />
          <shipsetting v-bind:player-name="player2" />
          <br />
        </div>
        <div v-else>
          <br />
          <h2 class="text-center">
            Waiting for other player to seting his/her ships
          </h2>
          <br />
        </div>
      </div>
    </div>
    <div v-else-if="getGameState() === idle">
      <br />
      <playername1 v-bind:player-name="player1" />
      <gridmodel1 v-bind:player-name="player1" />
      <br />
      <playername2 v-bind:player-name="player2" />
      <gridmodel2 v-bind:player-name="player2" />
      <br />
    </div>
    <div v-else-if="getGameState() === solved" class="text-center">
      <br />
      <div v-if="getPlayerState() === p1">
        <p>You have won:</p>
        <playername1 v-bind:player-name="player1" />
      </div>
      <div v-else-if="getPlayerState() === p2">
        You have won:<playername2 v-bind:player-name="player2" />
      </div>
      <br />
      <borat />
      <br />
    </div>
    <div v-else>
      <p class="text-center justify-center">
        Start the game for this page. Read more in <a href="/#/about">ABOUT</a>.
      </p>
    </div>
  </div>
</template>

<script>
import gridmodel1 from "@/components/model/gridmodel";
import gridmodel2 from "@/components/model/gridmodel";
import playername1 from "@/components/model/playername";
import playername2 from "@/components/model/playername";
import shipsetting from "@/components/model/shipsetting";
import borat from "@/components/media/borat";

export default {
  name: "Game",
  components: {
    gridmodel1,
    gridmodel2,
    playername1,
    playername2,
    shipsetting,
    borat
  },
  data() {
    return {
      player1: "player1",
      player2: "player2",
      idle: "IDLE",
      shipSetting: "SHIPSETTING",
      p1: "PLAYER_ONE",
      p2: "PLAYER_TWO",
      solved: "SOLVED"
    };
  },
  methods: {
    getGameState() {
      return this.$store.getters.getGameState;
    },
    getPlayerState() {
      return this.$store.getters.getPlayerState;
    },
    getPlayerID() {
      return this.$store.getters.getPlayerID;
    },
    fetchData: function() {
      this.$store.dispatch("fetchData");
    }
  },
  mounted() {
    this.fetchData();
  }
};
</script>
