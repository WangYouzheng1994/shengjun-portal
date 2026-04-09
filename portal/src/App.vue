<script setup lang="ts">
import NavBar from './components/layout/NavBar.vue'
import Footer from './components/layout/Footer.vue'
</script>

<template>
  <div class="min-h-screen bg-white">
    <NavBar />
    <main class="pt-20">
      <router-view />
    </main>
    <Footer />
  </div>
</template>
