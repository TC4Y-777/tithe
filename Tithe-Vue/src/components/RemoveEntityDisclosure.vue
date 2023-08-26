<script setup>
import { ref } from "vue";

import { Disclosure, DisclosureButton, DisclosurePanel } from "@headlessui/vue";
import { ChevronUpIcon } from "@heroicons/vue/20/solid";
import BaseButton from "@/components/BaseButton.vue";

defineProps({
  entity: {
    type: Object,
    required: true,
  },
  heading: {
    type: String,
    required: true,
  },
  content: {
    type: String,
    default: "",
  },
  buttonLabel: {
    type: String,
    required: true,
  },
  buttonMethod: {
    type: Function,
    required: true,
  },
});
</script>

<template>
  <Disclosure v-if="entity" v-slot="{ open }" as="div" class="mt-2">
    <div class="rounded-lg disclosure-heading-careful">
      <DisclosureButton
        class="bg-transparent text-white flex w-full justify-between rounded-lg bg-purple-100 px-4 py-2 text-left text-sm font-medium text-purple-900 hover:bg-transparent focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75"
      >
        <span class="font-semibold">{{ heading }}</span>
        <ChevronUpIcon
          :class="open ? 'rotate-180 transform' : ''"
          class="disclosure-heading-careful h-5 w-5 text-purple-500 hover:bg-transparent bg-transparent"
        />
      </DisclosureButton>
    </div>
    <DisclosurePanel class="px-4 pt-4 pb-2 text-sm text-white">
      <div class="text-center font-semibold">
        {{ content }}
      </div>
      <BaseButton
        class="baseButtonStyle mt-4 font-bold"
        color="danger"
        :label="buttonLabel"
        @click="buttonMethod"
      />
    </DisclosurePanel>
  </Disclosure>
</template>

<style scoped>
.disclosure-heading-careful {
  background-color: #ef4444;
  color: white;
  font-weight: bold;
}
.baseButtonStyle {
  width: 100%;
}
</style>
