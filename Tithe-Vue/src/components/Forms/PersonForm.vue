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
import EducationsMultiSelectBox from "@/components/SearchBoxes/EducationsMultiSelectBox.vue";
import OccupationsMultiSelectBox from "@/components/SearchBoxes/OccupationsMultiSelectBox.vue";

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
            occupationSector: "",
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
            occupationSector: "",
            occupationIds: [],
          },
  },
});

const emits = defineEmits(["update:modelValue", "changeInPersonForm"]);

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

watch(formFamily, (value) => {
  createPersonForm.value.familyId = value?.id ?? "";
});

const changeInFormRelation = (entity) => {
  createPersonForm.value.relationId = entity?.id ?? "";
};

const changeInEducation = (entity) => {
  if (entity !== null && entity !== undefined) {
    createPersonForm.value.educationIds = entity.map((obj) => obj.id);
  }
};

const changeInOccupation = (entity) => {
  if (entity !== null && entity !== undefined) {
    createPersonForm.value.occupationIds = entity.map((obj) => obj.id);
  }
};

watchEffect(() => {
  emits("update:modelValue", createPersonForm.value);
});

const personFormForaneRef = ref(null);
const personFormParishRef = ref(null);
const personFormFamilyRef = ref(null);
const personFormRelationRef = ref(null);
const personFormEducationsRef = ref(null);
const personFormOccupationsRef = ref(null);

const clearPersonForm = () => {
  createPersonForm.value.personName = "";
  createPersonForm.value.baptismName = "";
  createPersonForm.value.gender = "";
  createPersonForm.value.dob = "";
  createPersonForm.value.phone = "";
  createPersonForm.value.occupationSector = "";

  personFormForaneRef.value?.clearForane();
  formForane.value = "";
  personFormParishRef.value?.clearParish();
  formParish.value = "";
  personFormFamilyRef.value?.clearFamily();
  formFamily.value = "";
  createPersonForm.value.familyId = "";

  personFormRelationRef.value.clearRelation();
  createPersonForm.value.relationId = "";

  personFormEducationsRef.value.clearEducations();
  createPersonForm.value.educationIds = [];
  personFormOccupationsRef.value.clearOccupations();
  createPersonForm.value.occupationIds = [];
};

defineExpose({
  clearPersonForm,
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
    ref="personFormForaneRef"
    heading="Forane"
    class="multipleSelectAddressBox"
    @change-in-forane="changeInFormForane"
  />
  <ParishByForaneSingleSelectBox
    v-if="props.isFamilyRequired"
    ref="personFormParishRef"
    heading="Parish"
    :selected-forane="formForane"
    class="multipleSelectAddressBox"
    @change-in-parish="changeInFormParish"
  />
  <FamilyByParishSingleSelectBox
    v-if="props.isFamilyRequired"
    ref="personFormFamilyRef"
    heading="Family"
    :selected-parish="formParish"
    class="multipleSelectAddressBox"
    @change-in-family="changeInFormFamily"
  />
  <RelationSingleSelectBox
    ref="personFormRelationRef"
    heading="Relation"
    class="multipleSelectAddressBox"
    @change-in-relation="changeInFormRelation"
  />

  <EducationsMultiSelectBox
    ref="personFormEducationsRef"
    class="multipleSelectAddressBox"
    @change-in-education="changeInEducation"
  />

  <FormField label="Occupation Sector">
    <FormCheckRadioGroup
      v-model="createPersonForm.occupationSector"
      name="sample-radio"
      type="radio"
      :options="{ GOVERNMENT: 'Government', PRIVATE: 'Private' }"
    />
  </FormField>

  <OccupationsMultiSelectBox
    ref="personFormOccupationsRef"
    class="multipleSelectAddressBox"
    @change-in-occupation="changeInOccupation"
  />
</template>

<style scoped>
.multipleSelectAddressBox :deep(.multiselect-theme) {
  --ms-bg: #1e293b;
  --ms-dropdown-bg: #1e293b;
  --ms-dropdown-border-color: #1e293b;

  --ms-py: 0.757rem;
}

.multipleSelectAddressBox :deep(.multiselect-tags-search) {
  background: transparent;
}
</style>
