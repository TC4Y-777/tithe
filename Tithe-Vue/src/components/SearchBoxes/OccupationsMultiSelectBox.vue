<script setup>
import { computed, ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery, useMutation } from "@vue/apollo-composable";

import FormField from "@/components/FormField.vue";
import MultiSelectBox from "@/components/SearchBoxes/MultiSelectBox.vue";

import { personAllOccupationsListQuery } from "@/externalized-data/graphqlQueries";
import { createOneOccupationMutation } from "@/externalized-data/graphqlMutations";

const props = defineProps({
  heading: {
    type: String,
    default: "Select Occupations",
  },
});

const emits = defineEmits(["changeInOccupation"]);

const OCCUPATIONS_LIST_QUERY = gql`
  ${personAllOccupationsListQuery}
`;

const {
  result: occupationsList,
  load: occupationsListLoad,
  refetch: occupationsListRefetch,
} = useLazyQuery(OCCUPATIONS_LIST_QUERY);
occupationsListLoad();

const loadOccupations = computed((query, setOptions) => {
  return (
    occupationsList.value?.getAllOccupations?.map((entity) => {
      return {
        id: entity.occupationId,
        label: entity.occupationName,
        value: {
          id: entity.occupationId,
          label: entity.occupationName,
        },
      };
    }) ?? []
  );
});

// Create Education Option
const CREATE_EDUCATION_MUTATION = gql`
  ${createOneOccupationMutation}
`;

const {
  mutate: createOccupation,
  loading: createOccupationLoading,
  onDone: createOccupationDone,
  onError: createOccupationError,
} = useMutation(CREATE_EDUCATION_MUTATION);

const createOccupationOption = async (option, select$) => {
  createOccupation({ occupation: option.label });

  await new Promise((resolve, reject) => {
    createOccupationDone(() => {
      select$.clear();
      occupationsListRefetch();
      resolve("Success");
    });
  });

  return false;
};

const changeInOccupation = (entity) => {
  emits("changeInOccupation", entity);
};

const occupationsRef = ref(null);
const clearOccupations = () => {
  occupationsRef.value.clearField();
};
defineExpose({
  clearOccupations,
});
</script>

<template>
  <FormField :label="props.heading">
    <MultiSelectBox
      ref="occupationsRef"
      mode="tags"
      :options="loadOccupations"
      :can-clear="false"
      :create-option="true"
      :on-create="createOccupationOption"
      :searchable="true"
      :meta-label-enabled="false"
      @value-change="changeInOccupation"
    />
  </FormField>
</template>
