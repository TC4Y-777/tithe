<script setup>
import { ref } from "vue";

import { mdiClose, mdiPlus } from "@mdi/js";

import FormField from "@/components/FormField.vue";
import FormControl from "@/components/FormControl.vue";
import BaseButton from "@/components/BaseButton.vue";
import PersonForm from "@/components/Forms/PersonForm.vue";

const persons = ref([]);

const addInput = () => {
  persons.value.push({});
};

const removeInput = (counter) => {
  persons.value.splice(counter, 1);
};
</script>

<template>
  <div v-for="(person, index) in persons" :key="index">
    <FormField>
      <div class="flex justify-between">
        <span>Person {{ index + 1 }}</span>
        <BaseButton
          color="danger"
          :icon="mdiClose"
          :outline="true"
          :rounded-full="false"
          @click="removeInput(index)"
        />
      </div>
    </FormField>
    <!-- <FormField>
      <FormControl
        v-model="persons[index]"
        type="text"
        :borderless="false"
        placeholder="Shalom House"
      />
    </FormField> -->
    <PersonForm v-model="persons[index]" />
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
