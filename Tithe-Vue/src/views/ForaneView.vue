<script setup>
import { reactive, ref, onMounted, computed, watch } from "vue";
import gql from "graphql-tag";
import { useLazyQuery, useMutation, useQuery } from "@vue/apollo-composable";

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
  mdiTableLarge,
} from "@mdi/js";

import ForaneSingleSelectBox from "@/components/SearchBoxes/ForaneSingleSelectBox.vue";
import LayoutAuthenticated from "@/layouts/LayoutAuthenticated.vue";
import SectionMain from "@/components/SectionMain.vue";
import FormField from "@/components/FormField.vue";
import FormControl from "@/components/FormControl.vue";
import BaseButton from "@/components/BaseButton.vue";
import BaseButtons from "@/components/BaseButtons.vue";
import SectionTitleLineWithButton from "@/components/SectionTitleLineWithButton.vue";
import SectionTitle from "@/components/SectionTitle.vue";
import CardBox from "@/components/CardBox.vue";
import CardBoxWidget from "@/components/CardBoxWidget.vue";
import LineChart from "@/components/Charts/LineChart.vue";
import TableSampleClients from "@/components/TableSampleClients.vue";
import RemoveEntityDisclosure from "@/components/RemoveEntityDisclosure.vue";
import AllNotifications from "@/components/AllNotifications.vue";
import AddressForm from "@/components/AddressForm.vue";

import TableTabs from "@/components/TableTabs.vue";
import {
  foraneAllForaneListQuery,
  foranePageActiveEnityCountQuery,
  foranePageActiveParishTableQuery,
  foranePageActiveFamilyTableQuery,
  foranePageActivePersonTableQuery,
} from "@/externalized-data/graphqlQueries";
import {
  createForaneMutation,
  deactivateForaneMutation,
  // activateForaneMutation,
} from "@/externalized-data/graphqlMutations";
import {
  foranePageTableTabTitle,
  parishTableHeaders,
  familyTableHeaders,
  personTableHeaders,
} from "@/externalized-data/tableData";

// Notification Settings

// Info Notification
const infoNotificationEnabled = ref(false);
const infoNotificationHeading = ref("");
const infoNotificationContent = ref("");

// Success Notification
const successNotificationEnabled = ref(false);
const successNotificationHeading = ref("");
const successNotificationContent = ref("");

// Warning Notification
const warningNotificationEnabled = ref(false);
const warningNotificationHeading = ref("");
const warningNotificationContent = ref("");

// Danger Notification
const dangerNotificationEnabled = ref(false);
const dangerNotificationHeading = ref("");
const dangerNotificationContent = ref("");

const tableTabTitle = foranePageTableTabTitle;

// const options = [
//   { id: 1, label: "Wade Cooper" },
//   { id: 2, label: "Arlene Mccoy" },
//   { id: 3, label: "Devon Webb" },
//   { id: 4, label: "Tom Cook" },
//   { id: 5, label: "Tanya Fox" },
//   { id: 6, label: "Hellen Schmidt" },
// ];

const forane = ref();

const changeInForane = (entity) => {
  forane.value = entity;
};

// Entity Count in Forane Page
const activeEntityByForaneCountEnabled = ref(false);

const ACTIVE_ENTITY_BY_FORANE_COUNT_QUERY = gql`
  ${foranePageActiveEnityCountQuery}
`;

const {
  result: activeEntityByForaneCount,
  refetch: activeEntityByForaneCountRefetch,
} = useQuery(
  ACTIVE_ENTITY_BY_FORANE_COUNT_QUERY,
  () => ({ id: forane.value.id }),
  () => ({ enabled: activeEntityByForaneCountEnabled })
);
const activeParishCount = computed(
  () => activeEntityByForaneCount.value?.getParishCountByForane ?? 0
);
const activeKoottaymaCount = computed(
  () => activeEntityByForaneCount.value?.getKoottaymaCountByForane ?? 0
);
const activeFamilyCount = computed(
  () => activeEntityByForaneCount.value?.getFamilyCountByForane ?? 0
);
const activePersonCount = computed(
  () => activeEntityByForaneCount.value?.getPersonCountByForane ?? 0
);

watch(forane, () => {
  activeEntityByForaneCountEnabled.value = true;
});

const createForaneForm = reactive({
  foraneName: "",
  address: {
    buildingName: "",
    streetId: "",
    cityId: "",
    districtId: "",
    stateId: "",
    pincodeId: "",
  },
  phone: "",
});

// Code for checking whether object has empty values
function hasEmptyValues(obj, arrKey) {
  for (let key in obj) {
    if (!arrKey.includes(key)) {
      if (Object.prototype.hasOwnProperty.call(obj, key)) {
        if (typeof obj[key] === "object") {
          if (hasEmptyValues(obj[key], arrKey)) {
            return true;
          }
        } else if (obj[key] === "") {
          return true;
        }
      }
    }
  }
  return false;
}

const changeInAddressFormData = (eventData) => {
  console.log(eventData);
  createForaneForm.address = eventData;
};

const addressFormComponent = ref(null);

// Submit Create Forane Form
const CREATE_FORANE_MUTATION = gql`
  ${createForaneMutation}
`;

const {
  mutate: createForane,
  loading: createForaneLoading,
  onDone: createForaneDone,
  onError: createForaneError,
} = useMutation(CREATE_FORANE_MUTATION);

const submitCreateForaneForm = () => {
  if (hasEmptyValues(createForaneForm, ["buildingName", "phone"])) {
    console.log("Empty Values: " + createForaneForm);
  } else {
    console.log("Complete Values: " + createForaneForm);
    createForane({ forane: createForaneForm });
  }
};

watch(createForaneLoading, (value) => {
  infoNotificationEnabled.value = createForaneLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Creating Forane.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

createForaneDone(() => {
  console.log("onDone called");
  successNotificationEnabled.value = true;
  successNotificationHeading.value = "Created Forane.";
  successNotificationContent.value = "";

  createForaneForm.foraneName = "";
  createForaneForm.phone = "";
  createForaneForm.address.buildingName = "";
  addressFormComponent.value.clearAddressFields();

  setTimeout(() => {
    successNotificationEnabled.value = false;
    successNotificationHeading.value = "";
    successNotificationContent.value = "";
  }, 3000);
});

createForaneError(() => {
  console.log("Some Error occured while creating forane");
  dangerNotificationEnabled.value = true;
  dangerNotificationHeading.value = "Error Creating Forane.";
  dangerNotificationContent.value = "Try Again";
});

// Remove Forane
const DEACTIVATE_FORANE_MUTATION = gql`
  ${deactivateForaneMutation}
`;

const {
  mutate: deactivateForane,
  loading: deactivateForaneLoading,
  onDone: deactivateForaneDone,
  onError: deactivateForaneError,
} = useMutation(DEACTIVATE_FORANE_MUTATION);

const deactivateForaneButtonMethod = () => {
  if (forane.value.id != "") {
    deactivateForane({ foraneId: forane.value.id });
  } else {
    console.log("Forane ID is empty");
  }
};

watch(deactivateForaneLoading, (value) => {
  infoNotificationEnabled.value = deactivateForaneLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Removing Forane.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

deactivateForaneDone(() => location.reload());

deactivateForaneError(() => {
  console.log("Some Error occured while removing forane");
  dangerNotificationEnabled.value = true;
  dangerNotificationHeading.value = "Error Removing Forane.";
  dangerNotificationContent.value = "Try Again";
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

// Parish Table Data
const ACTIVE_PARISH_QUERY = gql`
  ${foranePageActiveParishTableQuery}
`;
const {
  result: activeParishData,
  load: activeParishDataLoad,
  refetch: activeParishDataRefetch,
} = useLazyQuery(ACTIVE_PARISH_QUERY, () => ({
  foraneId: forane.value.id,
}));
const getActiveParishRows = computed(() => {
  return activeParishData.value?.getAllParishesByForane ?? [];
});

// Family Table Data
const ACTIVE_FAMILY_QUERY = gql`
  ${foranePageActiveFamilyTableQuery}
`;
const {
  result: activeFamilyData,
  load: activeFamilyDataLoad,
  refetch: activeFamilyDataRefetch,
} = useLazyQuery(ACTIVE_FAMILY_QUERY, () => ({
  foraneId: forane.value.id,
}));
const getActiveFamilyRows = computed(() => {
  return activeFamilyData.value?.getAllFamiliesByForane ?? [];
});

// Person Table Data
const ACTIVE_PERSON_QUERY = gql`
  ${foranePageActivePersonTableQuery}
`;
const {
  result: activePersonData,
  load: activePersonDataLoad,
  refetch: activePersonDataRefetch,
} = useLazyQuery(ACTIVE_PERSON_QUERY, () => ({
  foraneId: forane.value.id,
}));
const getActivePersonRows = computed(() => {
  return activePersonData.value?.getAllPersonsByForane ?? [];
});
</script>

<template>
  <LayoutAuthenticated>
    <!-- Show only if super admin is logged in -->
    <SectionMain>
      <!-- <SearchBox v-model="user" :options="options" /> -->

      <SectionTitle :first="true" :last="true"> Forane </SectionTitle>
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div class="flex flex-col justify-between">
          <!-- <FormField label="Select Forane">
            <SearchBox
              v-model="forane"
              :load-options="loadForanes"
              :create-option="false"
              :reload-method="activeForaneListRefetch"
              bg-color="#0f172a"
            />
          </FormField> -->
          <!-- New Search Box -->
          <ForaneSingleSelectBox @change-in-forane="changeInForane" />
        </div>
        <div class="flex flex-col justify-between">
          <div class="w-full pt-7">
            <div class="theme-color w-full rounded-2xl bg-white p-2">
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
                      v-model="createForaneForm.foraneName"
                      type="text"
                      :icon="mdiChurch"
                      :borderless="true"
                      placeholder="St. Peter's Church"
                    />
                  </FormField>
                  <!-- Remove Building Name -->
                  <!-- <FormField label="Building">
                    <FormControl
                      v-model="createForaneForm.address.buildingName"
                    />
                  </FormField> -->
                  <FormField label="Phone">
                    <FormControl
                      v-model="createForaneForm.phone"
                      :borderless="true"
                      type="tel"
                      placeholder="04792662745"
                    />
                  </FormField>
                  <AddressForm
                    ref="addressFormComponent"
                    @address-form-change="changeInAddressFormData"
                  />
                  <BaseButton
                    class="baseButtonStyle font-bold"
                    color="success"
                    label="Submit"
                    @click="submitCreateForaneForm"
                  />
                </DisclosurePanel>
              </Disclosure>
              <RemoveEntityDisclosure
                :entity="forane"
                heading="Remove Forane"
                content="Are you sure you want to remove this forane"
                button-label="Yes, Remove this Forane"
                :button-method="deactivateForaneButtonMethod"
              />

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

      <AllNotifications
        :info-notification-enabled="infoNotificationEnabled"
        :info-notification-heading="infoNotificationHeading"
        :info-notification-content="infoNotificationContent"
        :success-notification-enabled="successNotificationEnabled"
        :success-notification-heading="successNotificationHeading"
        :success-notification-content="successNotificationContent"
        :warning-notification-enabled="warningNotificationEnabled"
        :warning-notification-heading="warningNotificationHeading"
        :warning-notification-content="warningNotificationContent"
        :danger-notification-enabled="dangerNotificationEnabled"
        :danger-notification-heading="dangerNotificationHeading"
        :danger-notification-content="dangerNotificationContent"
        @info-false="infoNotificationEnabled = false"
        @success-false="successNotificationEnabled = false"
        @warning-false="warningNotificationEnabled = false"
        @danger-false="dangerNotificationEnabled = false"
      />
    </SectionMain>

    <SectionMain v-if="forane">
      <SectionTitleLineWithButton
        :icon="mdiChartTimelineVariant"
        :title="forane.label"
        main
      >
        <BaseButton
          :icon="mdiReload"
          color="whiteDark"
          @click="activeEntityByForaneCountRefetch"
        />
      </SectionTitleLineWithButton>
      <div class="grid grid-cols-1 gap-6 lg:grid-cols-3 mb-6">
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

      <SectionTitleLineWithButton :icon="mdiTableLarge" title="Quick View" />

      <TableTabs :tabs="tableTabTitle">
        <template #default="{ index }">
          <div v-if="index === 0">
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
          <div v-if="index === 1">
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
          <div v-if="index === 2">
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

<!-- <style src="@vueform/multiselect/themes/default.css"></style> -->

<style scoped>
.theme-color {
  background-color: #0f172a;
}
.disclosure-heading {
  background-color: #1e293b;
  color: white;
  font-weight: bold;
}
.disclosure-heading-careful {
  background-color: #ef4444;
  color: white;
  font-weight: bold;
}
.baseButtonStyle {
  width: 100%;
}
/* .multiselect-theme {
  --ms-bg: #0f172a;
  --ms-border-color: #0f172a;
  --ms-ring-width: 1px;
  --ms-ring-color: #3b82f6;

  --ms-dropdown-bg: #0f172a;
  --ms-dropdown-border-color: #0f172a;
  --ms-option-bg-pointed: white;
  --ms-option-color-pointed: #3b82f6;
  --ms-option-bg-selected: #3b82f6;
  --ms-option-color-selected: white;
  --ms-option-bg-selected-pointed: white;
  --ms-option-color-selected-pointed: #3b82f6;
} */
</style>
