<template>
  <v-sheet
      class="mx-auto mt-4" max-width="960" align="center">
    <v-row
        no-gutters
        v-for="row in 10"
        :key="row-1">
      <v-col
          v-for="col in 10"
          :key="col-1">
        <v-btn
            block
            outlined
            tile
            :key="(row-1).toString() + (col-1).toString()"
            :color="col.color">
          {{ grid1(row, col) }}
        </v-btn>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
export default {
  name: "gridmodel",
  data:() =>({
    grid1:this.$store.getters.getGrid1
  }),
  mounted() {
    this.$store.dispatch("getJson")
  },
  methods:{
    getGrid:function (){
      this.$store.dispatch("getJson")
    }
  }
}
</script>