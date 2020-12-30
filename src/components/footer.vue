<template>
  <v-footer
    color="light-blue lighten-1"
    padless>
    <v-row
        justify="center"
        no-gutters
    >
      <v-btn
          v-for="link in links"
          :key="link.title"
          :to="link.path"
          color="white"
          text
          rounded
          class="my-2"
      >
        {{ link.title }}
      </v-btn>
      <v-col
          class="lighten-2 py-4 text-center white--text"
          cols="12"
      >
        {{ new Date().getFullYear() }} — <strong>Powered by Vuetify</strong>
      </v-col>
    </v-row>
  </v-footer>
</template>

<script>
export default {
  name: "Myfooter",
  data: () => ({
    links: [
      { title: 'Home', path: '/' },
      { title: 'Game', path: '/game' },
      { title: 'Save', path: '/save' },
      { title: 'Load', path: '/load' },
      { title: 'About', path: '/about' }
    ],
  }),
}
</script>

<style scoped>

</style>