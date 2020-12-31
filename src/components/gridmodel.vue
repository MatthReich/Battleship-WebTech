<template>
  <v-sheet
      class="mx-auto mt-4" max-width="960" align="center">
    <v-row
        no-gutters
        v-for="row in 10"
        :key="row">
      <v-col
          v-for="col in fieldValue"
          :key="col">
        <v-btn
            block
            outlined
            tile
            :color="col.color">
          {{ col.text }}
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