<template>
  <gridmodel/>
</template>

<script>
import gridmodel from "@/components/gridmodel";

export default {
  name: 'Game',
  components: {gridmodel},
  mounted() {
    this.$store.dispatch("getJson")
  }
}

</script>
