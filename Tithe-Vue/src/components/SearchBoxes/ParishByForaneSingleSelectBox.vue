<script setup>
import { computed, watchEffect, ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery } from "@vue/apollo-composable";

import FormField from "@/components/FormField.vue";
import SingleSelectBox from "@/components/SearchBoxes/SingleSelectBox.vue";

import { parishAllParishListQuery } from "@/externalized-data/graphqlQueries";

const props = defineProps({
  heading: {
    type: String,
    default: "Select Parish",
  },
  selectedForane: {
    type: Object,
    default: () => {},
  },
});

const emits = defineEmits(["changeInParish"]);

const ACTIVE_PARISH_BY_FORANE_LIST_QUERY = gql`
  ${parishAllParishListQuery}
`;

const {
  result: activeParishList,
  load: activeParishListLoad,
  refetch: activeParishListRefetch,
} = useLazyQuery(ACTIVE_PARISH_BY_FORANE_LIST_QUERY, () => ({
  foraneId: props.selectedForane?.id,
}));

const loadParishes = computed((query, setOptions) => {
  return (
    activeParishList.value?.getAllParishesByForane?.map((entity) => {
      return {
        id: entity.parishId,
        label: entity.parishName,
        value: {
          id: entity.parishId,
          label: entity.parishName,
        },
        meta: {
          address: entity.address?.street?.streetName ?? "",
        },
      };
    }) ?? []
  );
});

watchEffect(() => {
  console.log(props.selectedForane);
  activeParishListLoad();
});

const changeInParish = (entity) => {
  emits("changeInParish", entity);
};

const parishRef = ref(null);
const clearParish = () => {
  parishRef.value.clearField();
};
defineExpose({
  clearParish,
});
</script>

<template>
  <FormField :label="props.heading">
    <SingleSelectBox
      ref="parishRef"
      :options="loadParishes"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :meta-label-enabled="true"
      @value-change="changeInParish"
    />
  </FormField>
</template>
