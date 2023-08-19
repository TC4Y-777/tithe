<script setup>
import { reactive, ref, onMounted } from "vue";
import gql from "graphql-tag";
import { useLazyQuery } from "@vue/apollo-composable";
import { useRouter } from "vue-router";

import { Disclosure, DisclosureButton, DisclosurePanel } from "@headlessui/vue";
import { ChevronUpIcon } from "@heroicons/vue/20/solid";
import {
  mdiChurch,
  mdiReload,
  mdiFinance,
  mdiEye,
  mdiChartTimelineVariant,
  mdiChurchOutline,
  mdiHandsPray,
  mdiAccountMultiple,
  mdiAccount,
  mdiCashMultiple,
} from "@mdi/js";

import SearchBox from "@/components/SearchBox.vue";
import LayoutAuthenticated from "@/layouts/LayoutAuthenticated.vue";
import SectionMain from "@/components/SectionMain.vue";
import FormField from "@/components/FormField.vue";
import FormControl from "@/components/FormControl.vue";
import BaseButton from "@/components/BaseButton.vue";
import BaseButtons from "@/components/BaseButtons.vue";
import SectionTitleLineWithButton from "@/components/SectionTitleLineWithButton.vue";
import CardBox from "@/components/CardBox.vue";
import CardBoxWidget from "@/components/CardBoxWidget.vue";
import LineChart from "@/components/Charts/LineChart.vue";

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

const street = ref();
const city = ref();
const district = ref();
const state = ref();
const pincode = ref();

const form = reactive({
  foraneName: "",
  address: {
    buildingName: "",
    streetId: street.value,
    cityId: city.value,
    districtId: district.value,
    stateId: state.value,
    pincodeId: pincode.value,
  },
  phone: "",
});

// =================
// Remove this
import * as chartConfig from "@/components/Charts/chart.config.js";
const chartData = ref(null);
const fillChartData = () => {
  chartData.value = chartConfig.sampleChartData();
};
onMounted(() => {
  fillChartData();
});
// =================
</script>

<template>
  <LayoutAuthenticated>
    <!-- Show only if super admin is logged in -->
    <SectionMain>
      <!-- <SearchBox v-model="user" :options="options" /> -->

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div class="flex flex-col justify-between">
          <FormField label="Search Foranes">
            <SearchBox
              v-model="forane"
              :load-options="loadForanes"
              :create-option="createForane"
              :is-reload-enabled="true"
              bg-color="#0f172a"
            />
          </FormField>
        </div>
        <div class="flex flex-col justify-between">
          <div class="w-full px-4 pt-7">
            <div
              class="theme-color mx-auto w-full max-w-md rounded-2xl bg-white p-2"
            >
              <Disclosure v-slot="{ open }">
                <DisclosureButton
                  class="disclosure-heading flex w-full justify-between rounded-lg bg-purple-100 px-4 py-2 text-left text-sm font-medium text-purple-900 hover:bg-transparent focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75"
                >
                  <span>Create New Forane</span>
                  <ChevronUpIcon
                    :class="open ? 'rotate-180 transform' : ''"
                    class="disclosure-heading h-5 w-5 text-purple-500"
                  />
                </DisclosureButton>
                <DisclosurePanel class="px-4 pt-4 pb-2 text-sm text-white">
                  <FormField label="Forane Name">
                    <FormControl
                      v-model="form.foraneName"
                      type="text"
                      :icon="mdiChurch"
                      placeholder="St. Peter's Church"
                    />
                  </FormField>
                  <FormField label="Building">
                    <FormControl v-model="form.address.buildingName" />
                  </FormField>
                  <FormField label="Phone">
                    <FormControl
                      v-model="form.phone"
                      type="tel"
                      placeholder="04792662745"
                    />
                  </FormField>
                  <FormField label="Street">
                    <SearchBox
                      v-model="street"
                      :is-reload-enabled="true"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="City">
                    <SearchBox
                      v-model="city"
                      :is-reload-enabled="true"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="District">
                    <SearchBox
                      v-model="district"
                      :is-reload-enabled="true"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="State">
                    <SearchBox
                      v-model="state"
                      :is-reload-enabled="true"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="Pincode">
                    <SearchBox
                      v-model="pincode"
                      :is-reload-enabled="true"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <BaseButton
                    class="baseButtonStyle"
                    color="info"
                    label="Submit"
                  />
                </DisclosurePanel>
              </Disclosure>
              <!-- <Disclosure v-slot="{ open }" as="div" class="mt-2">
                <DisclosureButton
                  class="flex w-full justify-between rounded-lg bg-purple-100 px-4 py-2 text-left text-sm font-medium text-purple-900 hover:bg-purple-200 focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75"
                >
                  <span>Do you offer technical support?</span>
                  <ChevronUpIcon
                    :class="open ? 'rotate-180 transform' : ''"
                    class="h-5 w-5 text-purple-500"
                  />
                </DisclosureButton>
                <DisclosurePanel class="px-4 pt-4 pb-2 text-sm text-gray-500">
                  No.
                </DisclosurePanel>
              </Disclosure> -->
            </div>
          </div>
        </div>
      </div>
    </SectionMain>

    <SectionMain v-if="forane">
      <SectionTitleLineWithButton
        :icon="mdiChartTimelineVariant"
        :title="forane.label"
        main
      >
      </SectionTitleLineWithButton>
      <div class="grid grid-cols-1 gap-6 lg:grid-cols-3 mb-6">
        <CardBoxWidget
          color="text-blue-500"
          :icon="mdiChurchOutline"
          :number="10"
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
        <BaseButtons>
          <BaseButton
            :icon="mdiReload"
            color="whiteDark"
            @click="fillChartData"
          />
          <BaseButton :icon="mdiEye" color="whiteDark" @click="fillChartData" />
        </BaseButtons>
      </SectionTitleLineWithButton>
      <CardBox class="mb-6">
        <div v-if="chartData">
          <line-chart :data="chartData" class="h-96" />
        </div>
      </CardBox>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<style scoped>
.theme-color {
  background-color: #0f172a;
}
.disclosure-heading {
  background-color: #1e293b;
  color: white;
  font-weight: bold;
}
.baseButtonStyle {
  width: 100%;
}
</style>
