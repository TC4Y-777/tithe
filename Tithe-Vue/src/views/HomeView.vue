<script setup>
// import { computed, ref, onMounted } from "vue";
import { ref, onMounted } from "vue";
// import { useMainStore } from "@/stores/main";
import {
  mdiChurch,
  mdiChurchOutline,
  mdiHandsPray,
  mdiAccountMultiple,
  mdiAccount,
  mdiChartTimelineVariant,
  mdiReload,
  mdiFinance,
} from "@mdi/js";
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

const chartData = ref(null);

const tableTabTitle = [
  { title: "Forane", icon: "mdiChurch" },
  { title: "Parish", icon: "mdiChurchOutline" },
  { title: "Family", icon: "mdiAccountMultiple" },
  { title: "Person", icon: "mdiAccount" },
];

const fillChartData = () => {
  chartData.value = chartConfig.sampleChartData();
};

onMounted(() => {
  fillChartData();
});

// const mainStore = useMainStore();

// const clientBarItems = computed(() => mainStore.clients.slice(0, 4));

// const transactionBarItems = computed(() => mainStore.history);
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
          :number="100"
          label="Foranes"
        />
        <CardBoxWidget
          color="text-blue-500"
          :icon="mdiChurchOutline"
          :number="1000"
          label="Parishes"
        />
        <CardBoxWidget
          color="text-red-500"
          :icon="mdiHandsPray"
          :number="10000"
          label="Koottaymas"
        />
        <CardBoxWidget
          color="text-yellow-500"
          :icon="mdiAccountMultiple"
          :number="100000"
          label="Families"
        />
        <CardBoxWidget
          color="text-orange-500"
          :icon="mdiAccount"
          :number="400000"
          label="Persons"
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

      <SectionTitleLineWithButton :icon="mdiAccountMultiple" title="Clients" />

      <TableTabs :tabs="tableTabTitle">
        <template #default="{ index }">
          <div v-if="index === 0">
            <CardBox has-table>
              <TableSampleClients />
            </CardBox>
          </div>
          <div v-if="index === 1">
            <CardBox has-table>
              <TableSampleClients />
            </CardBox>
          </div>
          <div v-if="index === 2">
            <CardBox has-table>
              <TableSampleClients />
            </CardBox>
          </div>
          <div v-if="index === 3">
            <CardBox has-table>
              <TableSampleClients />
            </CardBox>
          </div>
        </template>
      </TableTabs>

      <!-- <CardBox has-table>
        <TableSampleClients />
      </CardBox> -->
    </SectionMain>
  </LayoutAuthenticated>
</template>
