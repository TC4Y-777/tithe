<script setup>
import { computed, ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery, useMutation } from "@vue/apollo-composable";

import FormField from "@/components/FormField.vue";
import MultiSelectBox from "@/components/SearchBoxes/MultiSelectBox.vue";

import { personAllEducationsListQuery } from "@/externalized-data/graphqlQueries";
import { createOneEducationMutation } from "@/externalized-data/graphqlMutations";

const props = defineProps({
  heading: {
    type: String,
    default: "Select Educations",
  },
});

const emits = defineEmits(["changeInEducation"]);

const EDUCATIONS_LIST_QUERY = gql`
  ${personAllEducationsListQuery}
`;

const {
  result: educationsList,
  load: educationsListLoad,
  refetch: educationsListRefetch,
} = useLazyQuery(EDUCATIONS_LIST_QUERY);
educationsListLoad();

const loadEducations = computed((query, setOptions) => {
  return (
    educationsList.value?.getAllEducations?.map((entity) => {
      return {
        id: entity.educationId,
        label: entity.educationName,
        value: {
          id: entity.educationId,
          label: entity.educationName,
        },
      };
    }) ?? []
  );
});

// Create Education Option
const CREATE_EDUCATION_MUTATION = gql`
  ${createOneEducationMutation}
`;

const {
  mutate: createEducation,
  loading: createEducationLoading,
  onDone: createEducationDone,
  onError: createEducationError,
} = useMutation(CREATE_EDUCATION_MUTATION);

const createEducationOption = async (option, select$) => {
  createEducation({ education: option.label });

  await new Promise((resolve, reject) => {
    createEducationDone(() => {
      select$.clear();
      educationsListRefetch();
      resolve("Success");
    });
  });

  return false;
};

const changeInEducation = (entity) => {
  emits("changeInEducation", entity);
};

const educationsRef = ref(null);
const clearEducations = () => {
  educationsRef.value.clearField();
};
defineExpose({
  clearEducations,
});
</script>

<template>
  <FormField :label="props.heading">
    <MultiSelectBox
      ref="educationsRef"
      mode="tags"
      :options="loadEducations"
      :can-clear="false"
      :create-option="true"
      :on-create="createEducationOption"
      :searchable="true"
      :meta-label-enabled="false"
      @value-change="changeInEducation"
    />
  </FormField>
</template>
