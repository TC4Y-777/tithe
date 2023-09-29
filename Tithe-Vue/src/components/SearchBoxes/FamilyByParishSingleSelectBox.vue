<script setup>
import { computed, watchEffect, ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery } from "@vue/apollo-composable";

import FormField from "@/components/FormField.vue";
import SingleSelectBox from "@/components/SearchBoxes/SingleSelectBox.vue";

import { familyAllFamilyListQuery } from "@/externalized-data/graphqlQueries";

const props = defineProps({
  heading: {
    type: String,
    default: "Select Family",
  },
  selectedParish: {
    type: Object,
    default: () => {},
  },
});

const emits = defineEmits(["changeInFamily"]);

const ACTIVE_FAMILY_BY_PARISH_LIST_QUERY = gql`
  ${familyAllFamilyListQuery}
`;

const {
  result: activeFamilyList,
  load: activeFamilyListLoad,
  refetch: activeFamilyListRefetch,
} = useLazyQuery(ACTIVE_FAMILY_BY_PARISH_LIST_QUERY, () => ({
  parishId: props.selectedParish?.id,
}));

const loadFamilies = computed((query, setOptions) => {
  return (
    activeFamilyList.value?.getAllFamiliesByParish?.map((entity) => {
      return {
        id: entity.familyId,
        label: entity.familyName,
        value: {
          id: entity.familyId,
          label: entity.familyName,
        },
      };
    }) ?? []
  );
});

watchEffect(() => {
  console.log(props.selectedParish);
  activeFamilyListLoad();
});

const changeInFamily = (entity) => {
  emits("changeInFamily", entity);
};

const familyRef = ref(null);
const clearFamily = () => {
  familyRef.value.clearField();
};
defineExpose({
  clearFamily,
});
</script>

<template>
  <FormField :label="props.heading">
    <SingleSelectBox
      ref="familyRef"
      :options="loadFamilies"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :meta-label-enabled="false"
      @value-change="changeInFamily"
    />
  </FormField>
</template>
