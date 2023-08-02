<script setup>
import { containerMaxW } from "@/config.js";
import BaseLevel from "@/components/BaseLevel.vue";

const year = new Date().getFullYear();
</script>

<template>
  <footer class="py-2 px-6" :class="containerMaxW">
    <BaseLevel>
      <div class="text-center md:text-left">
        <b
          >&copy;{{ year }},
          <a href="https://tc4y-777.github.io/" target="_blank">By Ashish</a>.</b
        >
        <slot />
      </div>
      <!-- Insert my logo here -->
    </BaseLevel>
  </footer>
</template>
