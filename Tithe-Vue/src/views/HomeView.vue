<script setup>
import { ref, onMounted, computed } from "vue";
// import { useMainStore } from "@/stores/main";
import {
  mdiChurch,
  mdiChurchOutline,
  mdiHandsPray,
  mdiAccountMultiple,
  mdiAccount,
  mdiCashMultiple,
  mdiChartTimelineVariant,
  mdiReload,
  mdiFinance,
  mdiTableLarge,
} from "@mdi/js";
import gql from "graphql-tag";
import { useQuery, useLazyQuery } from "@vue/apollo-composable";

import * as chartConfig from "@/components/Charts/chart.config.js";
import LineChart from "@/components/Charts/LineChart.vue";
import SectionMain from "@/components/SectionMain.vue";
import CardBoxWidget from "@/components/CardBoxWidget.vue";
import CardBox from "@/components/CardBox.vue";
import TableSampleClients from "@/components/TableSampleClients.vue";
import BaseButton from "@/components/BaseButton.vue";
// import CardBoxTransaction from "@/components/CardBoxTransaction.vue";
// import CardBoxClient from "@/components/CardBoxClient.vue";
import LayoutAuthenticated from "@/layouts/LayoutAuthenticated.vue";
import SectionTitleLineWithButton from "@/components/SectionTitleLineWithButton.vue";

import TableTabs from "@/components/TableTabs.vue";
import {
  homepageTableTabTitle,
  foraneTableHeaders,
  parishTableHeaders,
  personTableHeaders,
  familyTableHeaders,
} from "@/externalized-data/tableData";
import {
  homepageActiveEnityCountQuery,
  homepageActiveForaneTableQuery,
  homepageActiveParishTableQuery,
  homepageActiveFamilyTableQuery,
  homepageActivePersonTableQuery,
} from "@/externalized-data/graphqlQueries";

const chartData = ref(null);

const tableTabTitle = homepageTableTabTitle;

const fillChartData = () => {
  chartData.value = chartConfig.sampleChartData();
};

onMounted(() => {
  fillChartData();
});

// const mainStore = useMainStore();

// const clientBarItems = computed(() => mainStore.clients.slice(0, 4));

// const transactionBarItems = computed(() => mainStore.history);

const ACTIVE_ENTITY_COUNT_QUERY = gql`
  ${homepageActiveEnityCountQuery}
`;
// Don't use any variable below. 'result' is a ref. So, it must be used.
const { result: activeEntityCount } = useQuery(ACTIVE_ENTITY_COUNT_QUERY);
const activeForaneCount = computed(
  () => activeEntityCount.value?.getForaneCount ?? 0
);
const activeParishCount = computed(
  () => activeEntityCount.value?.getParishCount ?? 0
);
const activeKoottaymaCount = computed(
  () => activeEntityCount.value?.getKoottaymaCount ?? 0
);
const activeFamilyCount = computed(
  () => activeEntityCount.value?.getFamilyCount ?? 0
);
const activePersonCount = computed(
  () => activeEntityCount.value?.getPersonCount ?? 0
);

const activeTitheAnnual = 1;

// Forane Table Data
const ACTIVE_FORANE_QUERY = gql`
  ${homepageActiveForaneTableQuery}
`;
const {
  result: activeForaneData,
  load: activeForaneDataLoad,
  refetch: activeForaneDataRefetch,
} = useLazyQuery(ACTIVE_FORANE_QUERY);
const getActiveForaneRows = computed(() => {
  return activeForaneData.value?.getAllForanes ?? [];
});

// Parish Table Data
const ACTIVE_PARISH_QUERY = gql`
  ${homepageActiveParishTableQuery}
`;
const {
  result: activeParishData,
  load: activeParishDataLoad,
  refetch: activeParishDataRefetch,
} = useLazyQuery(ACTIVE_PARISH_QUERY);
const getActiveParishRows = computed(() => {
  return activeParishData.value?.getAllParishes ?? [];
});

// Family Table Data
const ACTIVE_FAMILY_QUERY = gql`
  ${homepageActiveFamilyTableQuery}
`;
const {
  result: activeFamilyData,
  load: activeFamilyDataLoad,
  refetch: activeFamilyDataRefetch,
} = useLazyQuery(ACTIVE_FAMILY_QUERY);
const getActiveFamilyRows = computed(() => {
  return activeFamilyData.value?.getAllFamilies ?? [];
});

// Person Table Data
const ACTIVE_PERSON_QUERY = gql`
  ${homepageActivePersonTableQuery}
`;
const {
  result: activePersonData,
  load: activePersonDataLoad,
  refetch: activePersonDataRefetch,
} = useLazyQuery(ACTIVE_PERSON_QUERY);
const getActivePersonRows = computed(() => {
  return activePersonData.value?.getAllPersons ?? [];
});
</script>

<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton
        :icon="mdiChartTimelineVariant"
        title="Dashboard"
        main
      >
      </SectionTitleLineWithButton>

      <div class="grid grid-cols-1 gap-6 lg:grid-cols-3 mb-6">
        <CardBoxWidget
          color="text-emerald-500"
          :icon="mdiChurch"
          :number="activeForaneCount"
          label="Foranes"
        />
        <CardBoxWidget
          color="text-blue-500"
          :icon="mdiChurchOutline"
          :number="activeParishCount"
          label="Parishes"
        />
        <CardBoxWidget
          color="text-red-500"
          :icon="mdiHandsPray"
          :number="activeKoottaymaCount"
          label="Koottaymas"
        />
        <CardBoxWidget
          color="text-yellow-500"
          :icon="mdiAccountMultiple"
          :number="activeFamilyCount"
          label="Families"
        />
        <CardBoxWidget
          color="text-orange-500"
          :icon="mdiAccount"
          :number="activePersonCount"
          label="Persons"
        />
        <CardBoxWidget
          color="text-purple-500"
          :icon="mdiCashMultiple"
          :number="activeTitheAnnual"
          label="Annual Tithe"
        />
      </div>

      <SectionTitleLineWithButton :icon="mdiFinance" title="Tithe History">
        <BaseButton
          :icon="mdiReload"
          color="whiteDark"
          @click="fillChartData"
        />
      </SectionTitleLineWithButton>

      <CardBox class="mb-6">
        <div v-if="chartData">
          <line-chart :data="chartData" class="h-96" />
        </div>
      </CardBox>

      <!-- List of cardboxes -->
      <!-- <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div class="flex flex-col justify-between">
          <CardBoxTransaction
            v-for="(transaction, index) in transactionBarItems"
            :key="index"
            :amount="transaction.amount"
            :date="transaction.date"
            :business="transaction.business"
            :type="transaction.type"
            :name="transaction.name"
            :account="transaction.account"
          />
        </div>
        <div class="flex flex-col justify-between">
          <CardBoxClient
            v-for="client in clientBarItems"
            :key="client.id"
            :name="client.name"
            :login="client.login"
            :date="client.created"
            :progress="client.progress"
          />
        </div>
      </div> -->

      <SectionTitleLineWithButton :icon="mdiTableLarge" title="Quick View" />

      <TableTabs :tabs="tableTabTitle">
        <template #default="{ index }">
          <div v-if="index === 0">
            <CardBox
              has-table
              @vnode-mounted="
                activeForaneDataLoad() || activeForaneDataRefetch()
              "
            >
              <TableSampleClients
                id-name="foraneId"
                :table-headers="foraneTableHeaders"
                :row-data="getActiveForaneRows"
              />
            </CardBox>
          </div>
          <div v-if="index === 1">
            <CardBox
              has-table
              @vnode-mounted="
                activeParishDataLoad() || activeParishDataRefetch()
              "
            >
              <TableSampleClients
                id-name="parishId"
                :table-headers="parishTableHeaders"
                :row-data="getActiveParishRows"
              />
            </CardBox>
          </div>
          <div v-if="index === 2">
            <CardBox
              has-table
              @vnode-mounted="
                activeFamilyDataLoad() || activeFamilyDataRefetch()
              "
            >
              <TableSampleClients
                id-name="familyId"
                :table-headers="familyTableHeaders"
                :row-data="getActiveFamilyRows"
              />
            </CardBox>
          </div>
          <div v-if="index === 3">
            <CardBox
              has-table
              @vnode-mounted="
                activePersonDataLoad() || activePersonDataRefetch()
              "
            >
              <TableSampleClients
                id-name="personId"
                :table-headers="personTableHeaders"
                :row-data="getActivePersonRows"
              />
            </CardBox>
          </div>
        </template>
      </TableTabs>
    </SectionMain>
  </LayoutAuthenticated>
</template>
