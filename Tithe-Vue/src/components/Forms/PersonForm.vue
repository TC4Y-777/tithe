<script setup>
import { computed, ref, watch, watchEffect } from "vue";

import { mdiAccount } from "@mdi/js";

import FormField from "@/components/FormField.vue";
import FormCheckRadioGroup from "@/components/FormCheckRadioGroup.vue";
import FormControl from "@/components/FormControl.vue";
import ForaneSingleSelectBox from "@/components/SearchBoxes/ForaneSingleSelectBox.vue";
import ParishByForaneSingleSelectBox from "@/components/SearchBoxes/ParishByForaneSingleSelectBox.vue";
import FamilyByParishSingleSelectBox from "@/components/SearchBoxes/FamilyByParishSingleSelectBox.vue";
import RelationSingleSelectBox from "@/components/SearchBoxes/RelationSingleSelectBox.vue";

const props = defineProps({
  // Since this form is being used in both family and person page
  isFamilyRequired: Boolean,
  modelValue: {
    type: Object,
    default: (props) =>
      props.isFamilyRequired
        ? {
            personName: "",
            baptismName: "",
            gender: "",
            dob: "",
            phone: "",
            familyId: "",
            relationId: "",
            educationIds: [],
            occupationIds: [],
          }
        : {
            personName: "",
            baptismName: "",
            gender: "",
            dob: "",
            phone: "",
            relationId: "",
            educationIds: [],
            occupationIds: [],
          },
  },
});

const emits = defineEmits(["update:modelValue", "changeInPersonForm"]);

// const createPersonForm = props.isFamilyRequired
//   ? reactive({
//       personName: "",
//       baptismName: "",
//       gender: "",
//       dob: "",
//       phone: "",
//       familyId: "",
//       relationId: "",
//       educationIds: [],
//       occupationIds: [],
//     })
//   : reactive({
//       personName: "",
//       baptismName: "",
//       gender: "",
//       dob: "",
//       phone: "",
//       relationId: "",
//       educationIds: [],
//       occupationIds: [],
//     });

const createPersonForm = computed({
  get: () => props.modelValue,
  set: (value) => {
    emits("update:modelValue", value);
  },
});

const formForane = ref();
const formParish = ref();
const formFamily = ref();

const changeInFormForane = (entity) => {
  formForane.value = entity;
};

const changeInFormParish = (entity) => {
  formParish.value = entity;
};

const changeInFormFamily = (entity) => {
  formFamily.value = entity;
};

const changeInFormRelation = (entity) => {
  createPersonForm.value.relationId = entity.id;
};

watch(formFamily, (value) => {
  createPersonForm.value.familyId = value.id;
});

watchEffect(() => {
  //   emits("changeInPersonForm", createPersonForm);
  emits("update:modelValue", createPersonForm);
});
</script>

<template>
  <FormField label="Person Name">
    <FormControl
      v-model="createPersonForm.personName"
      type="text"
      :icon="mdiAccount"
      placeholder="Mathew"
    />
  </FormField>
  <FormField label="Baptism Name">
    <FormControl
      v-model="createPersonForm.baptismName"
      type="text"
      placeholder="Matt"
    />
  </FormField>
  <FormField label="Gender">
    <FormCheckRadioGroup
      v-model="createPersonForm.gender"
      name="sample-radio"
      type="radio"
      :options="{ M: 'Male', F: 'Female' }"
    />
  </FormField>
  <FormField label="Date of Birth">
    <FormControl
      v-model="createPersonForm.dob"
      type="text"
      placeholder="YYYY-MM-DD"
    />
  </FormField>
  <FormField label="Phone">
    <FormControl
      v-model="createPersonForm.phone"
      type="tel"
      placeholder="04792662745"
    />
  </FormField>
  <ForaneSingleSelectBox
    v-if="props.isFamilyRequired"
    heading="Forane"
    class="multipleSelectAddressBox"
    @change-in-forane="changeInFormForane"
  />
  <ParishByForaneSingleSelectBox
    v-if="props.isFamilyRequired"
    heading="Parish"
    :selected-forane="formForane"
    class="multipleSelectAddressBox"
    @change-in-parish="changeInFormParish"
  />
  <FamilyByParishSingleSelectBox
    v-if="props.isFamilyRequired"
    heading="Family"
    :selected-parish="formParish"
    class="multipleSelectAddressBox"
    @change-in-family="changeInFormFamily"
  />
  <RelationSingleSelectBox
    heading="Relation"
    class="multipleSelectAddressBox"
    @change-in-relation="changeInFormRelation"
  />
</template>

<style scoped>
.multipleSelectAddressBox :deep(.multiselect-theme) {
  --ms-bg: #1e293b;
  --ms-dropdown-bg: #1e293b;
  --ms-dropdown-border-color: #1e293b;

  --ms-py: 0.757rem;
}
</style>
