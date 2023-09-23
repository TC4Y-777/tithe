<script setup>
import { watch, ref } from "vue";

// import Multiselect from "@vueform/multiselect";

defineProps({
  options: {
    type: [Array, Object, Function],
    required: false,
    default: () => [],
  },
  canDeselect: Boolean,
  canClear: Boolean,
  searchable: Boolean,
  metaLabelEnabled: Boolean,
  resolveOnLoad: Boolean,
});

const emit = defineEmits(["valueChange", "searchChange"]);

const searchChange = (query) => {
  emit("searchChange", query);
};

const entity = ref();

watch(entity, () => {
  emit("valueChange", entity.value);
});
</script>

<template>
  <Multiselect
    v-model="entity"
    :options="options"
    :can-deselect="canDeselect"
    :can-clear="canClear"
    :searchable="searchable"
    :meta-label-enabled="metaLabelEnabled"
    :resolve-on-load="resolveOnLoad"
    @search-change="searchChange"
    class="multiselect-theme"
  />
</template>

<style scoped>
.multiselect-theme {
  --ms-bg: #0f172a;
  --ms-border-color: #0f172a;
  --ms-ring-width: 1px;
  --ms-ring-color: #3b82f6;

  --ms-dropdown-bg: #0f172a;
  --ms-dropdown-border-color: #0f172a;
  --ms-option-bg-pointed: white;
  --ms-option-color-pointed: #3b82f6;
  --ms-option-bg-selected: #3b82f6;
  --ms-option-color-selected: white;
  --ms-option-bg-selected-pointed: white;
  --ms-option-color-selected-pointed: #3b82f6;
}

/* .multiselect-theme :deep(input) {
  background-color: #0f172a;
  border-width: 0px;
  border-color: #0f172a;
} */
</style>
