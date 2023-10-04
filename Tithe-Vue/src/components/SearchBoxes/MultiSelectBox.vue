<script setup>
import { watch, ref } from "vue";

defineProps({
  mode: {
    type: String,
    default: "multiple",
  },
  max: {
    type: Number,
    default: 1,
  },
  options: {
    type: [Array, Object, Function],
    required: false,
    default: () => [],
  },
  canClear: Boolean,
  closeOnSelect: Boolean,
  searchable: Boolean,
  metaLabelEnabled: Boolean,
  createOption: Boolean,
  resolveOnLoad: Boolean,
  onCreate: {
    type: Function,
    default: () => {},
  },
});

const emit = defineEmits(["valueChange", "searchChange"]);

const searchChange = (query) => {
  emit("searchChange", query);
};

const entity = ref();

watch(entity, () => {
  emit("valueChange", entity.value);
});

const multiselect = ref(null);
const clearField = () => {
  multiselect.value.clear();
};
defineExpose({
  clearField,
});
</script>

<template>
  <Multiselect
    ref="multiselect"
    v-model="entity"
    :mode="mode"
    :max="max"
    :options="options"
    :can-clear="canClear"
    :close-on-select="closeOnSelect"
    :searchable="searchable"
    :meta-label-enabled="metaLabelEnabled"
    :create-option="createOption"
    :on-create="onCreate"
    :resolve-on-load="resolveOnLoad"
    class="multiselect-theme"
    @search-change="searchChange"
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
</style>
