<script setup>
import { ref } from "vue";
import gql from "graphql-tag";
import { useLazyQuery } from "@vue/apollo-composable";
import { useRouter } from "vue-router";

import SearchBox from "@/components/SearchBox.vue";
import LayoutAuthenticated from "@/layouts/LayoutAuthenticated.vue";
import SectionMain from "@/components/SectionMain.vue";
import { foraneAllForaneListQuery } from "@/externalized-data/graphqlQueries";

// const options = [
//   { id: 1, label: "Wade Cooper" },
//   { id: 2, label: "Arlene Mccoy" },
//   { id: 3, label: "Devon Webb" },
//   { id: 4, label: "Tom Cook" },
//   { id: 5, label: "Tanya Fox" },
//   { id: 6, label: "Hellen Schmidt" },
// ];

const forane = ref();

const ACTIVE_FORANE_LIST_QUERY = gql`
  ${foraneAllForaneListQuery}
`;

const {
  result: activeForaneList,
  load: activeForaneListLoad,
  refetch: activeForaneListRefetch,
} = useLazyQuery(ACTIVE_FORANE_LIST_QUERY);
activeForaneListLoad();
const loadForanes = (query, setOptions) => {
  activeForaneListLoad() || activeForaneListRefetch();
  setOptions(
    activeForaneList.value.getAllForanes.map((entity) => {
      return {
        id: entity.foraneId,
        label: entity.foraneName,
      };
    })
  );
};

const router = useRouter();
const createForane = (option, setSelected) => {
  router.push("/dashboard");

  // fetch("link", {
  //   method: "POST",
  //   headers: {
  //     "Content-Type": "application/json",
  //   },
  //   body: JSON.stringify({
  //     name: option.label,
  //   }),
  // })
  //   .then((response) => response.json())
  //   .then((user) => {
  //     setSelected({
  //       value: user.id,
  //       label: user.name,
  //     });
  //   });
};
</script>

<template>
  <LayoutAuthenticated>
    <SectionMain>
      <!-- <SearchBox v-model="user" :options="options" /> -->

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div class="flex flex-col justify-between">
          <SearchBox
            v-model="forane"
            :load-options="loadForanes"
            :create-option="createForane"
            :is-reload-enabled="true"
          />
        </div>
      </div>
    </SectionMain>
  </LayoutAuthenticated>
</template>
