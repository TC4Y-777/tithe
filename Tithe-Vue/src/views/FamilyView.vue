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
  mdiAccountMultiple,
  mdiAccount,
  mdiCashMultiple,
  mdiTableLarge,
} from "@mdi/js";

import AddPersonInFamilyForm from "@/components/Forms/AddPersonInFamilyForm.vue";
import ForaneSingleSelectBox from "@/components/SearchBoxes/ForaneSingleSelectBox.vue";
import ParishByForaneSingleSelectBox from "@/components/SearchBoxes/ParishByForaneSingleSelectBox.vue";
import FamilyByParishSingleSelectBox from "@/components/SearchBoxes/FamilyByParishSingleSelectBox.vue";
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
import AddressForm from "@/components/AddressForm.vue";
import RemoveEntityDisclosure from "@/components/RemoveEntityDisclosure.vue";

import TableTabs from "@/components/TableTabs.vue";
import {
  familyPageActiveEnityCountQuery,
  familyPageActivePersonTableQuery,
} from "@/externalized-data/graphqlQueries";
import {
  createFamilyMutation,
  deactivateFamilyMutation,
} from "@/externalized-data/graphqlMutations";
import {
  familyPageTableTabTitle,
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

const tableTabTitle = familyPageTableTabTitle;

const forane = ref();
const parish = ref();
const family = ref();

const changeInForane = (entity) => {
  forane.value = entity;
};

const changeInParish = (entity) => {
  parish.value = entity;
};

const changeInFamily = (entity) => {
  family.value = entity;
};

// Entity Count in Family Page
const activeEntityByFamilyCountEnabled = ref(false);

const ACTIVE_ENTITY_BY_FAMILY_COUNT_QUERY = gql`
  ${familyPageActiveEnityCountQuery}
`;

const {
  result: activeEntityByFamilyCount,
  refetch: activeEntityByFamilyCountRefetch,
} = useQuery(
  ACTIVE_ENTITY_BY_FAMILY_COUNT_QUERY,
  () => ({ id: family.value.id }),
  () => ({ enabled: activeEntityByFamilyCountEnabled })
);
const activePersonCount = computed(
  () => activeEntityByFamilyCount.value?.getPersonCountByFamily ?? 0
);

watch(family, () => {
  activeEntityByFamilyCountEnabled.value = true;
});

const createFamilyForm = reactive({
  familyName: "",
  address: {
    buildingName: "",
    streetId: "",
    cityId: "",
    districtId: "",
    stateId: "",
    pincodeId: "",
  },
  phone: "",
  koottaymaId: "",
});

// Form Forane Search Box
const formForane = ref();

// Form Parish Search Box
const formParish = ref();

// Form Koottayma Search Box
const formKoottayma = ref();

const changeInFormForane = (entity) => {
  formForane.value = entity;
};

const changeInFormParish = (entity) => {
  formParish.value = entity;
};

const changeInFormKoottayma = (entity) => {
  formKoottayma.value = entity;
};

watch(formKoottayma, (value) => {
  createFamilyForm.koottaymaId = value.id;
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
  createFamilyForm.address = eventData;
};

const addressFormComponent = ref(null);

// Submit Create Family Form
const CREATE_FAMILY_MUTATION = gql`
  ${createFamilyMutation}
`;

const {
  mutate: createFamily,
  loading: createFamilyLoading,
  onDone: createFamilyDone,
} = useMutation(CREATE_FAMILY_MUTATION);

const submitCreateFamilyForm = () => {
  if (hasEmptyValues(createFamilyForm, ["buildingName", "phone"])) {
    console.log("Empty Values: " + createFamilyForm);
  } else {
    console.log("Complete Values: " + createFamilyForm);
    createFamily({ family: createFamilyForm });
  }
};

watch(createFamilyLoading, (value) => {
  infoNotificationEnabled.value = createFamilyLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Creating Family.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

createFamilyDone(() => {
  console.log("onDone called");
  successNotificationEnabled.value = true;
  successNotificationHeading.value = "Created Family.";
  successNotificationContent.value = "";

  createFamilyForm.koottaymaName = "";
  createFamilyForm.phone = "";
  createFamilyForm.address.buildingName = "";
  formForane.value = "";
  formParish.value = "";
  formKoottayma.value = "";
  addressFormComponent.value.clearAddressFields();

  setTimeout(() => {
    successNotificationEnabled.value = false;
    successNotificationHeading.value = "";
    successNotificationContent.value = "";
  }, 3000);
});

// Remove Family
const DEACTIVATE_FAMILY_MUTATION = gql`
  ${deactivateFamilyMutation}
`;

const {
  mutate: deactivateFamily,
  loading: deactivateFamilyLoading,
  onDone: deactivateFamilyDone,
  onError: deactivateFamilyError,
} = useMutation(DEACTIVATE_FAMILY_MUTATION);

const deactivateFamilyButtonMethod = () => {
  if (parish.value.id != "") {
    deactivateFamily({ familyId: family.value.id });
  } else {
    console.log("Family ID is empty");
  }
};

watch(deactivateFamilyLoading, (value) => {
  infoNotificationEnabled.value = deactivateFamilyLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Removing Family.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

deactivateFamilyDone(() => location.reload());

deactivateFamilyError(() => {
  console.log("Some Error occured while removing family");
  dangerNotificationEnabled.value = true;
  dangerNotificationHeading.value = "Error Removing Family.";
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

// Person Table Data
const ACTIVE_PERSON_QUERY = gql`
  ${familyPageActivePersonTableQuery}
`;
const {
  result: activePersonData,
  load: activePersonDataLoad,
  refetch: activePersonDataRefetch,
} = useLazyQuery(ACTIVE_PERSON_QUERY, () => ({
  familyId: family.value.id,
}));
const getActivePersonRows = computed(() => {
  return activePersonData.value?.getAllPersonsByFamily ?? [];
});
</script>

<template>
  <LayoutAuthenticated>
    <!-- Show only if super admin is logged in -->
    <SectionTitle :first="true" :last="true"> Family </SectionTitle>
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
              <FamilyByParishSingleSelectBox
                :selected-parish="parish"
                @change-in-family="changeInFamily"
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
                  <span>Create New Family</span>
                  <ChevronUpIcon
                    :class="open ? 'rotate-180 transform' : ''"
                    class="disclosure-heading h-5 w-5 text-purple-500"
                  />
                </DisclosureButton>
                <DisclosurePanel class="px-4 pt-4 pb-2 text-sm text-white">
                  <FormField label="Family Name">
                    <FormControl
                      v-model="createFamilyForm.familyName"
                      type="text"
                      :icon="mdiAccountMultiple"
                      :borderless="true"
                      placeholder="Shalom House"
                    />
                  </FormField>
                  <!-- Remove Building name -->
                  <!-- <FormField label="Building">
                    <FormControl
                      v-model="createFamilyForm.address.buildingName"
                    />
                  </FormField> -->
                  <ForaneSingleSelectBox
                    heading="Forane"
                    class="multipleSelectAddressBox"
                    @change-in-forane="changeInFormForane"
                  />
                  <ParishByForaneSingleSelectBox
                    heading="Parish"
                    :selected-forane="formForane"
                    class="multipleSelectAddressBox"
                    @change-in-parish="changeInFormParish"
                  />

                  <KoottaymaByParishSingleSelectBox
                    heading="Koottayma"
                    :selected-parish="formParish"
                    class="multipleSelectAddressBox"
                    @change-in-koottayma="changeInFormKoottayma"
                  />
                  <FormField label="Phone">
                    <FormControl
                      v-model="createFamilyForm.phone"
                      type="tel"
                      :borderless="true"
                      placeholder="04792662745"
                    />
                  </FormField>
                  <AddressForm
                    ref="addressFormComponent"
                    @address-form-change="changeInAddressFormData"
                  />
                  <AddPersonInFamilyForm />
                  <BaseButton
                    class="baseButtonStyle"
                    color="success"
                    label="Submit"
                    @click="submitCreateFamilyForm"
                  />
                </DisclosurePanel>
              </Disclosure>
              <RemoveEntityDisclosure
                :entity="family"
                heading="Remove Family"
                content="Are you sure you want to remove this family"
                button-label="Yes, Remove this Family"
                :button-method="deactivateFamilyButtonMethod"
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

    <SectionMain v-if="family">
      <SectionTitleLineWithButton
        :icon="mdiChartTimelineVariant"
        :title="family.label"
        main
      >
        <BaseButton
          :icon="mdiReload"
          color="whiteDark"
          @click="activeEntityByFamilyCountRefetch"
        />
      </SectionTitleLineWithButton>
      <div class="grid grid-cols-1 gap-6 lg:grid-cols-3 mb-6">
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
