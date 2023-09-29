<script setup>
import { computed, ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery } from "@vue/apollo-composable";

import FormField from "@/components/FormField.vue";
import SingleSelectBox from "@/components/SearchBoxes/SingleSelectBox.vue";

import { personAllRelationListQuery } from "@/externalized-data/graphqlQueries";

const props = defineProps({
  heading: {
    type: String,
    default: "Select Relation",
  },
});

const emits = defineEmits(["changeInRelation"]);

const RELATION_LIST_QUERY = gql`
  ${personAllRelationListQuery}
`;

const {
  result: formRelationList,
  load: formRelationListLoad,
  refetch: formRelationListRefetch,
} = useLazyQuery(RELATION_LIST_QUERY);
formRelationListLoad();

const loadRelations = computed((query, setOptions) => {
  return (
    formRelationList.value?.getAllRelations?.map((entity) => {
      return {
        id: entity.relationId,
        label: entity.relationName,
        value: {
          id: entity.relationId,
          label: entity.relationName,
        },
      };
    }) ?? []
  );
});

const changeInRelation = (entity) => {
  emits("changeInRelation", entity);
};

const relationRef = ref(null);
const clearRelation = () => {
  relationRef.value.clearField();
};
defineExpose({
  clearRelation,
});
</script>

<template>
  <FormField :label="props.heading">
    <SingleSelectBox
      ref="relationRef"
      :options="loadRelations"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :meta-label-enabled="false"
      @value-change="changeInRelation"
    />
  </FormField>
</template>
