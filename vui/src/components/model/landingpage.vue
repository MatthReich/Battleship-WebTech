<template>
  <v-main data-app>
    <v-container class="mx-auto text-center">
      <h1>Battleship</h1>
      <br/>
      <v-img
          class="mx-auto"
          max-width="960"
          src="https://media.giphy.com/media/RKS1aUdDGFUKxRecSL/source.gif"
      />
      <br/>
      <v-dialog v-model="dialog" persistent max-width="480">
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="my-4" color="light-blue" dark v-bind="attrs" v-on="on">
            Start Game
          </v-btn>
        </template>
        <v-card>
          <v-card-title class="headline">
            Start a new game
          </v-card-title>
          <v-card-text>
            <v-text-field
                v-model="yourplayername"
                label="Your Name"
                required
            ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="red darken-1" text @click="dialog = false">
              Cancel
            </v-btn>
            <v-btn color="light-blue darken-1" text @click="submit()">
              Start
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <br />
      <v-dialog v-model="loading" persistent max-width="480" hide-overlay>
        <v-card color="light-blue" dark>
          <v-card-text>
            Waiting for second player
            <v-progress-linear
                indeterminate
                color="white"
                class="mb-0"
            ></v-progress-linear>
          </v-card-text>
        </v-card>
      </v-dialog>
      <br />
      <h2>How to start</h2>
      <br />
      <p>start the game by pressing play button</p>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "landingpage",
  data() {
    return {
      dialog: false,
      loading: false,
      yourplayername: ""
    };
  },
  methods: {
    submit() {
      this.loading = true;
      this.dialog = false;
      this.$store.commit("SENDING_MESSAGE", this.yourplayername);
    }
  }
};
</script>
