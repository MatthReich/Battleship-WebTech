<template>
  <v-sheet
      class="mx-auto mt-4" max-width="960" align="center">
    <v-row
        no-gutters
        v-for="n in 10"
        :key="n">
      <v-col
          v-for="n in fieldValue"
          :key="n">
        <v-btn
            block
            outlined
            tile
            :color="n.color">
          {{ n.text }}
        </v-btn>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
export default {
  name: "gridmodel",
    data: () => ({
      fieldValue: [
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" },
        { text: "x", color: "red--text text--lighten-1" }
      ],
    }),
}
</script>