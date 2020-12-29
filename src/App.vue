<script src="main.js"></script>
<template>
  <v-app id="inspire">
    <v-app-bar app color="grey darken-2" flat>
      <v-container class="py-0 fill-height">
        <v-btn
            v-for="navitem in navbar"
            :key="navitem.title"
            :to="navitem.to"
            text
        >
          {{ navitem.title }}
        </v-btn>
      </v-container>
    </v-app-bar>

    <v-main class="grey">
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    navbar: [
      { title: "Home", to: "/" },
      { title: "Start", to: "/game" },
      { title: "Save", to: "/save" },
      { title: "Load", to: "/load" },
      { title: "About", to: "/about" }
    ]
  })
};
</script>
