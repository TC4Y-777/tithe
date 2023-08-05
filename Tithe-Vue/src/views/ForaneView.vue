<script setup>
import { ref } from "vue";

import SearchBox from "@/components/SearchBox.vue";
import LayoutAuthenticated from "@/layouts/LayoutAuthenticated.vue";
import SectionMain from "@/components/SectionMain.vue";
import CardBox from "@/components/CardBox.vue";
import BaseDivider from "@/components/BaseDivider.vue";

const options = [
  { id: 1, label: "Wade Cooper" },
  { id: 2, label: "Arlene Mccoy" },
  { id: 3, label: "Devon Webb" },
  { id: 4, label: "Tom Cook" },
  { id: 5, label: "Tanya Fox" },
  { id: 6, label: "Hellen Schmidt" },
];

const user = ref();

function loadUsers()
// I have watched till 6:20
</script>

<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SearchBox v-model="user" :options="options" />
    </SectionMain>
  </LayoutAuthenticated>
</template>
