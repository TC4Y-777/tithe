<script setup>
import { ref, watch } from "vue";

import { mdiClose, mdiPlus } from "@mdi/js";

import FormField from "@/components/FormField.vue";
import BaseButton from "@/components/BaseButton.vue";
import PersonForm from "@/components/Forms/PersonForm.vue";

const emits = defineEmits(["changeInFamilyMembers"]);

const persons = ref([]);

const addInput = () => {
  persons.value.push({});
};

const removeInput = (counter) => {
  persons.value.splice(counter, 1);
};

watch(persons.value, () => {
  emits("changeInFamilyMembers", persons.value);
});

const clearFamilyMembersForm = () => {
  persons.value.splice(0, persons.value.length);
};
defineExpose({
  clearFamilyMembersForm,
});
</script>

<template>
  <div v-for="(person, index) in persons" :key="index">
    <FormField>
      <div class="flex justify-between">
        <span></span>
        <h1 class="text-lg">Person {{ index + 1 }}</h1>
        <BaseButton
          color="danger"
          :icon="mdiClose"
          :outline="true"
          :rounded-full="false"
          @click="removeInput(index)"
        />
      </div>
    </FormField>
    <PersonForm :key="index" v-model="persons[index]" />
    <span></span>
  </div>
  <FormField class="text-center">
    <BaseButton
      color="contrast"
      label="Add Family Member"
      :icon="mdiPlus"
      :small="true"
      :outline="true"
      :rounded-full="true"
      @click="addInput"
    />
  </FormField>
</template>
