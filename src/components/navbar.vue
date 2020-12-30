<template>
  <nav>
    <v-app-bar flat app>
      <v-toolbar-title class="text-uppercase light-blue--text mx-2">
        <v-avatar>
          <img
              src="data:image/png;base64,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"
              alt="Battleship"
          >
        </v-avatar>
        <router-link to="/" tag="span" style="cursor: pointer" class="ms-2">Battleship</router-link>
      </v-toolbar-title>
      <v-toolbar-items>
        <v-btn depressed
            flat
            v-for="item in menuItems"
            :key="item.title"
            :to="item.path">
          {{ item.title }}
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>
  </nav>
</template>

<script>
export default {
  name: "navbar",
  data(){
    return {
      menuItems: [
        { title: 'Home', path: '/' },
        { title: 'Game', path: '/game' },
        { title: 'Save', path: '/save' },
        { title: 'Load', path: '/load' },
        { title: 'About', path: '/about' }
      ]
    }
  },
}
</script>

<style scoped>

</style>