<script setup>
import { computed, watchEffect, ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery } from "@vue/apollo-composable";

import FormField from "@/components/FormField.vue";
import SingleSelectBox from "@/components/SearchBoxes/SingleSelectBox.vue";

import { koottaymaAllKoottaymaListQuery } from "@/externalized-data/graphqlQueries";

const props = defineProps({
  heading: {
    type: String,
    default: "Select Koottayma",
  },
  selectedParish: {
    type: Object,
    default: () => {},
  },
});

const emits = defineEmits(["changeInKoottayma"]);

const ACTIVE_KOOTTAYMA_BY_PARISH_LIST_QUERY = gql`
  ${koottaymaAllKoottaymaListQuery}
`;

const {
  result: activeKoottaymaList,
  load: activeKoottaymaListLoad,
  refetch: activeKoottaymaListRefetch,
} = useLazyQuery(ACTIVE_KOOTTAYMA_BY_PARISH_LIST_QUERY, () => ({
  parishId: props.selectedParish?.id,
}));

const loadKoottaymas = computed((query, setOptions) => {
  return (
    activeKoottaymaList.value?.getAllKoottaymasByParish?.map((entity) => {
      return {
        id: entity.koottaymaId,
        label: entity.koottaymaName,
        value: {
          id: entity.koottaymaId,
          label: entity.koottaymaName,
        },
      };
    }) ?? []
  );
});

watchEffect(() => {
  console.log(props.selectedParish);
  activeKoottaymaListLoad();
});

const changeInKoottayma = (entity) => {
  emits("changeInKoottayma", entity);
};

const koottaymaRef = ref(null);
const clearKoottayma = () => {
  koottaymaRef.value.clearField();
};
defineExpose({
  clearKoottayma,
});
</script>

<template>
  <FormField :label="props.heading">
    <SingleSelectBox
      ref="koottaymaRef"
      :options="loadKoottaymas"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :meta-label-enabled="false"
      @value-change="changeInKoottayma"
    />
  </FormField>
</template>
