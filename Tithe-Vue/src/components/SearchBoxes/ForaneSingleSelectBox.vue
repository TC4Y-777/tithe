<script setup>
import { computed, ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery } from "@vue/apollo-composable";

import FormField from "@/components/FormField.vue";
import SingleSelectBox from "@/components/SearchBoxes/SingleSelectBox.vue";

import { foraneAllForaneListQuery } from "@/externalized-data/graphqlQueries";

defineProps({
  heading: {
    type: String,
    default: "Select Forane",
  },
});

const emits = defineEmits(["changeInForane"]);

const ACTIVE_FORANE_LIST_QUERY = gql`
  ${foraneAllForaneListQuery}
`;

const {
  result: activeForaneList,
  load: activeForaneListLoad,
  refetch: activeForaneListRefetch,
} = useLazyQuery(ACTIVE_FORANE_LIST_QUERY);
activeForaneListLoad();

// const loadForanes = (query, setOptions) => {
//   setOptions(
//     activeForaneList.value?.getAllForanes?.map((entity) => {
//       return {
//         id: entity.foraneId,
//         label: entity.foraneName,
//       };
//     }) ?? []
//   );
// };

// For the new Forane Search Box
const loadForanes = computed((query, setOptions) => {
  return (
    activeForaneList.value?.getAllForanes?.map((entity) => {
      return {
        id: entity.foraneId,
        label: entity.foraneName,
        value: {
          id: entity.foraneId,
          label: entity.foraneName,
        },
        meta: {
          address: entity.address?.street?.streetName ?? "",
        },
      };
    }) ?? []
  );
});

const changeInForane = (entity) => {
  emits("changeInForane", entity);
};

const foraneRef = ref(null);
const clearForane = () => {
  foraneRef.value.clearField();
};
defineExpose({
  clearForane,
});
</script>

<template>
  <FormField :label="heading">
    <SingleSelectBox
      ref="foraneRef"
      :options="loadForanes"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :meta-label-enabled="true"
      @value-change="changeInForane"
    />
  </FormField>
</template>
