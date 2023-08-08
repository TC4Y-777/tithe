<script setup>
import { ref, computed, watch } from "vue";
import {
  Combobox,
  ComboboxInput,
  ComboboxButton,
  ComboboxOptions,
  ComboboxOption,
  TransitionRoot,
} from "@headlessui/vue";
import { mdiReload } from "@mdi/js";
import { CheckIcon, ChevronUpDownIcon } from "@heroicons/vue/20/solid";
import BaseButton from "@/components/BaseButton.vue";

const emit = defineEmits(["update:modelValue"]);

const props = defineProps({
  modelValue: {
    type: Object,
    required: true,
  },
  options: {
    type: Array,
    default: () => [],
  },
  loadOptions: {
    type: Function,
    required: true,
  },
  createOption: {
    type: Function,
    default: () => {},
  },
  isReloadEnabled: Boolean,
});

const options = ref(props.options);
const isLoading = ref(false);

const queryOption = computed(() => {
  return query.value === ""
    ? null
    : {
        missing: true,
        label: query.value,
      };
});

// let selected = ref(props.options[0]);
let query = ref("");

watch(query, (q) => {
  if (props.loadOptions) {
    isLoading.value = true;
    props.loadOptions(q, (results) => {
      options.value = results;

      if (
        props.modelValue &&
        !options.value.some((o) => {
          return o.value === props.modelValue?.value;
        })
      ) {
        options.value.unshift(props.modelValue);
      }

      isLoading.value = false;
    });
  }
});

let filteredOptions = computed(() =>
  query.value === ""
    ? options.value
    : options.value.filter((option) =>
        option.label
          .toLowerCase()
          .replace(/\s+/g, "")
          .includes(query.value.toLowerCase().replace(/\s+/g, ""))
      )
);

function handleUpdateModelValue(selected) {
  emit("update:modelValue", selected);
  if (props.createOption && selected?.missing) {
    isLoading.value = true;
    props.createOption(selected, (option) => {
      emit("update:modelValue", option);
      isLoading.value = false;
    });
  }
}
</script>

<template>
  <!-- Removed 'fixed' from the class below -->
  <div class="top-16 w-72">
    <Combobox
      by="id"
      :model-value="props.modelValue"
      @update:model-value="handleUpdateModelValue"
    >
      <!-- @update:model-value="(value) => $emit('update:modelValue', value)" -->
      <div class="relative mt-1">
        <div
          class="relative w-full cursor-default overflow-hidden rounded-lg text-left shadow-md focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75 focus-visible:ring-offset-2 focus-visible:ring-offset-teal-300 sm:text-sm"
        >
          <ComboboxInput
            class="theme-color w-full border-none py-2 pl-3 pr-10 text-sm leading-5 text-white-900 focus:ring-0"
            :display-value="(entity) => entity.label"
            @change="query = $event.target.value"
          />
          <ComboboxButton
            class="absolute inset-y-0 right-0 flex items-center pr-2"
          >
            <BaseButton
              v-if="props.isReloadEnabled"
              :icon="mdiReload"
              color="whiteDark"
              @click="fillChartData"
            />
            <ChevronUpDownIcon
              class="h-5 w-5 text-gray-400"
              aria-hidden="true"
            />
          </ComboboxButton>
        </div>
        <TransitionRoot
          leave="transition ease-in duration-100"
          leave-from="opacity-100"
          leave-to="opacity-0"
          @after-leave="query = ''"
        >
          <ComboboxOptions
            class="theme-color absolute mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm"
          >
            <div
              v-if="filteredOptions.length === 0 && !isLoading"
              class="relative cursor-default select-none py-2 px-4 text-white-700"
            >
              Nothing found.
            </div>

            <div
              v-if="isLoading"
              class="relative cursor-default select-none py-2 px-4 text-white-700"
            >
              Loading...
            </div>

            <template v-if="!isLoading">
              <ComboboxOption
                v-if="
                  queryOption && !filteredOptions.length && props.createOption
                "
                v-slot="{ active }"
                as="template"
                :value="queryOption"
              >
                <li
                  class="relative cursor-default select-none py-2 pl-10 pr-4"
                  :class="{
                    'option-active text-white': active,
                    'text-white-900': !active,
                  }"
                >
                  Create New Forane: {{ queryOption.label }}
                </li>
              </ComboboxOption>

              <ComboboxOption
                v-for="entity in filteredOptions"
                :key="entity.id"
                v-slot="{ selected, active }"
                as="template"
                :value="entity"
              >
                <li
                  class="relative cursor-default select-none py-2 pl-10 pr-4"
                  :class="{
                    'option-active text-white': active,
                    'text-white-900': !active,
                  }"
                >
                  <span
                    class="block truncate"
                    :class="{
                      'font-medium option-selected': selected,
                      'font-normal': !selected,
                    }"
                  >
                    {{ entity.label }}
                  </span>
                  <span
                    v-if="selected"
                    class="absolute inset-y-0 left-0 flex items-center pl-3"
                    :class="{
                      'text-white': active,
                      'option-active-check-icon': !active,
                    }"
                  >
                    <CheckIcon class="h-5 w-5" aria-hidden="true" />
                  </span>
                </li>
              </ComboboxOption>
            </template>
          </ComboboxOptions>
        </TransitionRoot>
      </div>
    </Combobox>
  </div>
</template>

<style scoped>
.theme-color {
  background-color: #0f172a;
}
.option-active {
  background-color: #3b82f6;
}
.option-active-check-icon {
  color: #3b82f6;
}
.option-selected {
  font-weight: bold;
}
</style>
