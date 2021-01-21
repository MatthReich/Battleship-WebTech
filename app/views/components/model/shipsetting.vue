<template>
  <v-sheet class="mx-auto mt-4" max-width="960" align="center">
    <h2>You can still place: </h2>
    <br>
    <v-row v-for="x in 4" :key="x - 1" justify="center" class="py-1">
      <h3>{{getShips(x-1)}} of the {{x+1}} long ships</h3>
    </v-row>
  </v-sheet>
</template>

<script>
export default {
  name: "shipsetting",
  props:{
    playerName:{
      type: String,
      require: true
    }
  },
  methods:{
    getShips(x){
      if (this.playerName == "player1") {
        return this.$store.getters.getShip1[Number(x)]
      } else {
        return this.$store.getters.getShip2[Number(x)]
      }
    },fetchData: function() {
      this.$store.dispatch("fetchData");
    }
  },
  mounted() {
    this.fetchData();
  }
}
</script>

<style scoped>

</style>