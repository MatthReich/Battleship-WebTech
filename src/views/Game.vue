<template>
  <div>
    <v-container
        class="grey lighten-5 mb-6"
    >
      <v-row
          no-gutters
          style="height: 150px;"
          v-for="n in 10"
          :key="n"
      >
        <v-col
            v-for="n in fieldValue"
            :key="n"

        >
          <v-card
              class="pa-2"
              outlined
              tile
              :color="n.color"
              align="center"
          >
            {{ n.text }}
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
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
