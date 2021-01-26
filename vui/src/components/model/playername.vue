<template>
  <h2 class="text-center">{{ getPlayername() }}</h2>
</template>

<script>
export default {
  name: "playername",
  props: {
    playerName: {
      type: String,
      require: true
    }
  },
  methods:{
    getPlayername: function(){
      if (this.playerName === "player1") {
        return this.$store.getters.getPlayer1;
      } else if (this.playerName === "player2") {
        return this.$store.getters.getPlayer2;
      } else {
        return "player";
      }
    }
  }
};
</script>
