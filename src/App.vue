<template>
  <v-app class="grey lighten-4">
    <navbar/>
    <v-main>
      <router-view></router-view>
    </v-main>
    <Myfooter />
  </v-app>
</template>

<script>
import navbar from '@/components/navbar'
import Myfooter from "@/components/footer";
export default {
  name: 'App',
  components: {navbar, Myfooter},
}
</script>
