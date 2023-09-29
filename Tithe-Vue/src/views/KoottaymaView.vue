<script setup>
import { reactive, ref, onMounted, computed, watch } from "vue";
import gql from "graphql-tag";
import { useLazyQuery, useMutation, useQuery } from "@vue/apollo-composable";

import { Disclosure, DisclosureButton, DisclosurePanel } from "@headlessui/vue";
import { ChevronUpIcon } from "@heroicons/vue/20/solid";
import {
  mdiReload,
  mdiFinance,
  mdiEye,
  mdiChartTimelineVariant,
  mdiHandsPray,
  mdiAccountMultiple,
  mdiAccount,
  mdiCashMultiple,
  mdiTableLarge,
} from "@mdi/js";

import ForaneSingleSelectBox from "@/components/SearchBoxes/ForaneSingleSelectBox.vue";
import ParishByForaneSingleSelectBox from "@/components/SearchBoxes/ParishByForaneSingleSelectBox.vue";
import KoottaymaByParishSingleSelectBox from "@/components/SearchBoxes/KoottaymaByParishSingleSelectBox.vue";
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
import AllNotifications from "@/components/AllNotifications.vue";
import TableSampleClients from "@/components/TableSampleClients.vue";
import RemoveEntityDisclosure from "@/components/RemoveEntityDisclosure.vue";

import TableTabs from "@/components/TableTabs.vue";
import {
  koottaymaPageActiveEnityCountQuery,
  koottaymaPageActiveFamilyTableQuery,
  koottaymaPageActivePersonTableQuery,
} from "@/externalized-data/graphqlQueries";
import {
  createKoottaymaMutation,
  deactivateKoottaymaMutation,
} from "@/externalized-data/graphqlMutations";
import {
  koottaymaPageTableTabTitle,
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

const tableTabTitle = koottaymaPageTableTabTitle;

const forane = ref();
const parish = ref();
const koottayma = ref();

const changeInForane = (entity) => {
  forane.value = entity;
};

const changeInParish = (entity) => {
  parish.value = entity;
};

const changeInKoottayma = (entity) => {
  koottayma.value = entity;
};

// Entity Count in Koottayma Page
const activeEntityByKoottaymaCountEnabled = ref(false);

const ACTIVE_ENTITY_BY_KOOTTAYMA_COUNT_QUERY = gql`
  ${koottaymaPageActiveEnityCountQuery}
`;

const {
  result: activeEntityByKoottaymaCount,
  refetch: activeEntityByKoottaymaCountRefetch,
} = useQuery(
  ACTIVE_ENTITY_BY_KOOTTAYMA_COUNT_QUERY,
  () => ({ id: koottayma.value.id }),
  () => ({ enabled: activeEntityByKoottaymaCountEnabled })
);
const activeFamilyCount = computed(
  () => activeEntityByKoottaymaCount.value?.getFamilyCountByKoottayma ?? 0
);
const activePersonCount = computed(
  () => activeEntityByKoottaymaCount.value?.getPersonCountByKoottayma ?? 0
);

watch(koottayma, () => {
  activeEntityByKoottaymaCountEnabled.value = true;
});

const createKoottaymaForm = reactive({
  koottaymaName: "",
  parishId: "",
});

// Form Forane Search Box
const formForane = ref();

// Form Parish Search Box
const formParish = ref();

const changeInFormForane = (entity) => {
  formForane.value = entity;
};

const changeInFormParish = (entity) => {
  formParish.value = entity;
};

watch(formParish, (value) => {
  createKoottaymaForm.parishId = value?.id ?? "";
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

// Submit Create Koottayma Form
const CREATE_KOOTTAYMA_MUTATION = gql`
  ${createKoottaymaMutation}
`;

const {
  mutate: createKoottayma,
  loading: createKoottaymaLoading,
  onDone: createKoottaymaDone,
} = useMutation(CREATE_KOOTTAYMA_MUTATION);

const submitCreateKoottaymaForm = () => {
  if (hasEmptyValues(createKoottaymaForm, ["buildingName", "phone"])) {
    console.log("Empty Values: " + createKoottaymaForm);
  } else {
    console.log("Complete Values: " + createKoottaymaForm);
    createKoottayma({ koottayma: createKoottaymaForm });
  }
};

watch(createKoottaymaLoading, (value) => {
  infoNotificationEnabled.value = createKoottaymaLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Creating Koottayma.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

const formForaneSelectBoxRef = ref(null);
const formParishSelectBoxRef = ref(null);

createKoottaymaDone(() => {
  console.log("onDone called");
  successNotificationEnabled.value = true;
  successNotificationHeading.value = "Created Koottayma.";
  successNotificationContent.value = "";

  createKoottaymaForm.koottaymaName = "";
  formForaneSelectBoxRef.value.clearForane();
  formForane.value = "";
  formParishSelectBoxRef.value.clearParish();
  formParish.value = "";
  createKoottaymaForm.parishId = "";

  setTimeout(() => {
    successNotificationEnabled.value = false;
    successNotificationHeading.value = "";
    successNotificationContent.value = "";
  }, 3000);
});

// Remove Koottayma
const DEACTIVATE_KOOTTAYMA_MUTATION = gql`
  ${deactivateKoottaymaMutation}
`;

const {
  mutate: deactivateKoottayma,
  loading: deactivateKoottaymaLoading,
  onDone: deactivateKoottaymaDone,
  onError: deactivateKoottaymaError,
} = useMutation(DEACTIVATE_KOOTTAYMA_MUTATION);

const deactivateKoottaymaButtonMethod = () => {
  if (koottayma.value.id != "") {
    deactivateKoottayma({ koottaymaId: koottayma.value.id });
  } else {
    console.log("Koottayma ID is empty");
  }
};

watch(deactivateKoottaymaLoading, (value) => {
  infoNotificationEnabled.value = deactivateKoottaymaLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Removing Koottayma.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

deactivateKoottaymaDone(() => location.reload());

deactivateKoottaymaError(() => {
  console.log("Some Error occured while removing Koottayma");
  dangerNotificationEnabled.value = true;
  dangerNotificationHeading.value = "Error Removing Koottayma.";
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

// Family Table Data
const ACTIVE_FAMILY_QUERY = gql`
  ${koottaymaPageActiveFamilyTableQuery}
`;
const {
  result: activeFamilyData,
  load: activeFamilyDataLoad,
  refetch: activeFamilyDataRefetch,
} = useLazyQuery(ACTIVE_FAMILY_QUERY, () => ({
  koottaymaId: koottayma.value.id,
}));
const getActiveFamilyRows = computed(() => {
  return activeFamilyData.value?.getAllFamiliesByKoottayma ?? [];
});

// Person Table Data
const ACTIVE_PERSON_QUERY = gql`
  ${koottaymaPageActivePersonTableQuery}
`;
const {
  result: activePersonData,
  load: activePersonDataLoad,
  refetch: activePersonDataRefetch,
} = useLazyQuery(ACTIVE_PERSON_QUERY, () => ({
  koottaymaId: koottayma.value.id,
}));
const getActivePersonRows = computed(() => {
  return activePersonData.value?.getAllPersonsByKoottayma ?? [];
});
</script>

<template>
  <LayoutAuthenticated>
    <!-- Show only if super admin is logged in -->
    <SectionTitle :first="true" :last="true"> Koottayma </SectionTitle>
    <SectionMain>
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div class="flex-col justify-between">
          <ForaneSingleSelectBox @change-in-forane="changeInForane" />
          <span v-if="forane">
            <ParishByForaneSingleSelectBox
              :selected-forane="forane"
              @change-in-parish="changeInParish"
            />
            <span></span>
          </span>
          <FormField>
            <span v-if="parish">
              <KoottaymaByParishSingleSelectBox
                :selected-parish="parish"
                @change-in-koottayma="changeInKoottayma"
              />
            </span>
          </FormField>
        </div>
        <div class="flex flex justify-between">
          <div class="w-full pt-7">
            <div class="theme-color w-full rounded-2xl bg-white p-2">
              <Disclosure v-slot="{ open }">
                <DisclosureButton
                  class="disclosure-heading flex w-full justify-between rounded-lg bg-purple-100 px-4 py-2 text-left text-sm font-medium text-purple-900 hover:bg-transparent focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75"
                >
                  <span>Create New Koottayma</span>
                  <ChevronUpIcon
                    :class="open ? 'rotate-180 transform' : ''"
                    class="disclosure-heading h-5 w-5 text-purple-500"
                  />
                </DisclosureButton>
                <DisclosurePanel class="px-4 pt-4 pb-2 text-sm text-white">
                  <FormField label="Koottayma Name">
                    <FormControl
                      v-model="createKoottaymaForm.koottaymaName"
                      type="text"
                      :icon="mdiHandsPray"
                      :borderless="true"
                      placeholder="St. George Koottayma"
                    />
                  </FormField>

                  <ForaneSingleSelectBox
                    ref="formForaneSelectBoxRef"
                    heading="Forane"
                    class="multipleSelectAddressBox"
                    @change-in-forane="changeInFormForane"
                  />

                  <ParishByForaneSingleSelectBox
                    ref="formParishSelectBoxRef"
                    heading="Parish"
                    :selected-forane="formForane"
                    class="multipleSelectAddressBox"
                    @change-in-parish="changeInFormParish"
                  />

                  <BaseButton
                    class="baseButtonStyle"
                    color="success"
                    label="Submit"
                    @click="submitCreateKoottaymaForm"
                  />
                </DisclosurePanel>
              </Disclosure>
              <RemoveEntityDisclosure
                :entity="koottayma"
                heading="Remove Koottayma"
                content="Are you sure you want to remove this Koottayma"
                button-label="Yes, Remove this Koottayma"
                :button-method="deactivateKoottaymaButtonMethod"
              />
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
      />
    </SectionMain>

    <SectionMain v-if="koottayma">
      <SectionTitleLineWithButton
        :icon="mdiChartTimelineVariant"
        :title="koottayma.label"
        main
      >
        <BaseButton
          :icon="mdiReload"
          color="whiteDark"
          @click="activeEntityByKoottaymaCountRefetch"
        />
      </SectionTitleLineWithButton>
      <div class="grid grid-cols-1 gap-6 lg:grid-cols-3 mb-6">
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
          <div v-if="index === 1">
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

.multipleSelectAddressBox :deep(.multiselect-theme) {
  --ms-bg: #1e293b;
  --ms-dropdown-bg: #1e293b;
  --ms-dropdown-border-color: #1e293b;

  --ms-py: 0.757rem;
}
</style>
