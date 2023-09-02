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
  mdiHandsPray,
  mdiAccountMultiple,
  mdiAccount,
  mdiCashMultiple,
  mdiTableLarge,
} from "@mdi/js";

import SearchBox from "@/components/SearchBox.vue";
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
  koottaymaAllForaneListQuery,
  koottaymaAllParishListQuery,
  koottaymaAllKoottaymaListQuery,
  koottaymaPageActiveEnityCountQuery,
  parishPageActiveKoottaymaTableQuery,
  parishPageActiveFamilyTableQuery,
  parishPageActivePersonTableQuery,
} from "@/externalized-data/graphqlQueries";
import {
  createParishMutation,
  deactivateParishMutation,
} from "@/externalized-data/graphqlMutations";
import {
  koottaymaPageTableTabTitle,
  koottaymaTableHeaders,
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

const ACTIVE_FORANE_LIST_QUERY = gql`
  ${koottaymaAllForaneListQuery}
`;

const {
  result: activeForaneList,
  load: activeForaneListLoad,
  refetch: activeForaneListRefetch,
} = useLazyQuery(ACTIVE_FORANE_LIST_QUERY);
activeForaneListLoad();
const loadForanes = (query, setOptions) => {
  setOptions(
    activeForaneList.value?.getAllForanes?.map((entity) => {
      return {
        id: entity.foraneId,
        label: entity.foraneName,
      };
    }) ?? []
  );
};

const ACTIVE_PARISH_BY_FORANE_LIST_QUERY = gql`
  ${koottaymaAllParishListQuery}
`;

const {
  result: activeParishList,
  load: activeParishListLoad,
  refetch: activeParishListRefetch,
} = useLazyQuery(ACTIVE_PARISH_BY_FORANE_LIST_QUERY, () => ({
  foraneId: forane.value.id,
}));
const loadParishesByForane = (query, setOptions) => {
  setOptions(
    activeParishList.value?.getAllParishesByForane?.map((entity) => {
      return {
        id: entity.parishId,
        label: entity.parishName,
      };
    }) ?? []
  );
};

const ACTIVE_KOOTTAYMA_BY_PARISH_LIST_QUERY = gql`
  ${koottaymaAllKoottaymaListQuery}
`;

const {
  result: activeKoottaymaList,
  load: activeKoottaymaListLoad,
  refetch: activeKoottaymaListRefetch,
} = useLazyQuery(ACTIVE_KOOTTAYMA_BY_PARISH_LIST_QUERY, () => ({
  parishId: parish.value.id,
}));
const loadKoottaymasByParish = (query, setOptions) => {
  setOptions(
    activeKoottaymaList.value?.getAllKoottaymasByParish?.map((entity) => {
      return {
        id: entity.koottaymaId,
        label: entity.koottaymaName,
      };
    }) ?? []
  );
};

// Entity Count in Parish Page
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

watch(forane, () => {
  activeParishListLoad();
});

watch(parish, () => {
  activeKoottaymaListLoad();
});

watch(koottayma, () => {
  activeEntityByKoottaymaCountEnabled.value = true;
});

const createParishForm = reactive({
  parishName: "",
  address: {
    buildingName: "",
    streetId: "",
    cityId: "",
    districtId: "",
    stateId: "",
    pincodeId: "",
  },
  phone: "",
  foraneId: "",
});

// Form Forane Search Box
const formForane = ref();

watch(formForane, (value) => {
  createParishForm.foraneId = value.id;
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
  createParishForm.address = eventData;
};

const addressFormComponent = ref(null);

// Submit Create Parish Form
const CREATE_PARISH_MUTATION = gql`
  ${createParishMutation}
`;

const {
  mutate: createParish,
  loading: createParishLoading,
  onDone: createParishDone,
} = useMutation(CREATE_PARISH_MUTATION);

const submitCreateParishForm = () => {
  if (hasEmptyValues(createParishForm, ["buildingName", "phone"])) {
    console.log("Empty Values: " + createParishForm);
  } else {
    console.log("Complete Values: " + createParishForm);
    createParish({ parish: createParishForm });
  }
};

watch(createParishLoading, (value) => {
  infoNotificationEnabled.value = createParishLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Creating Parish.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

createParishDone(() => {
  console.log("onDone called");
  successNotificationEnabled.value = true;
  successNotificationHeading.value = "Created Parish.";
  successNotificationContent.value = "";

  createParishForm.parishName = "";
  createParishForm.phone = "";
  createParishForm.address.buildingName = "";
  formForane.value = "";
  addressFormComponent.value.clearAddressFields();

  setTimeout(() => {
    successNotificationEnabled.value = false;
    successNotificationHeading.value = "";
    successNotificationContent.value = "";
  }, 3000);
});

// Remove Parish
const DEACTIVATE_PARISH_MUTATION = gql`
  ${deactivateParishMutation}
`;

const {
  mutate: deactivateParish,
  loading: deactivateParishLoading,
  onDone: deactivateParishDone,
  onError: deactivateParishError,
} = useMutation(DEACTIVATE_PARISH_MUTATION);

const deactivateParishButtonMethod = () => {
  if (parish.value.id != "") {
    deactivateParish({ parishId: parish.value.id });
  } else {
    console.log("Parish ID is empty");
  }
};

watch(deactivateParishLoading, (value) => {
  infoNotificationEnabled.value = deactivateParishLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Removing Parish.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

deactivateParishDone(() => location.reload());

deactivateParishError(() => {
  console.log("Some Error occured while removing parish");
  dangerNotificationEnabled.value = true;
  dangerNotificationHeading.value = "Error Removing Parish.";
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

// Koottayma Table Data
const ACTIVE_KOOTTAYMA_QUERY = gql`
  ${parishPageActiveKoottaymaTableQuery}
`;
const {
  result: activeKoottaymaData,
  load: activeKoottaymaDataLoad,
  refetch: activeKoottaymaDataRefetch,
} = useLazyQuery(ACTIVE_KOOTTAYMA_QUERY, () => ({
  parishId: parish.value.id,
}));
const getActiveKoottaymaRows = computed(() => {
  return activeKoottaymaData.value?.getAllKoottaymasByParish ?? [];
});

// Family Table Data
const ACTIVE_FAMILY_QUERY = gql`
  ${parishPageActiveFamilyTableQuery}
`;
const {
  result: activeFamilyData,
  load: activeFamilyDataLoad,
  refetch: activeFamilyDataRefetch,
} = useLazyQuery(ACTIVE_FAMILY_QUERY, () => ({
  parishId: parish.value.id,
}));
const getActiveFamilyRows = computed(() => {
  return activeFamilyData.value?.getAllFamiliesByParish ?? [];
});

// Person Table Data
const ACTIVE_PERSON_QUERY = gql`
  ${parishPageActivePersonTableQuery}
`;
const {
  result: activePersonData,
  load: activePersonDataLoad,
  refetch: activePersonDataRefetch,
} = useLazyQuery(ACTIVE_PERSON_QUERY, () => ({
  parishId: parish.value.id,
}));
const getActivePersonRows = computed(() => {
  return activePersonData.value?.getAllPersonsByParish ?? [];
});
</script>

<template>
  <LayoutAuthenticated>
    <!-- Show only if super admin is logged in -->
    <SectionTitle :first="true" :last="true"> Koottayma </SectionTitle>
    <SectionMain>
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div class="flex-col justify-between">
          <FormField label="Select Forane">
            <SearchBox
              v-model="forane"
              :load-options="loadForanes"
              :create-option="false"
              :reload-method="activeForaneListRefetch"
              bg-color="#0f172a"
            />
          </FormField>
          <FormField v-if="forane" label="Select Parish">
            <SearchBox
              v-model="parish"
              :load-options="loadParishesByForane"
              :create-option="false"
              :reload-method="activeParishListRefetch"
              bg-color="#0f172a"
            />
          </FormField>
          <FormField v-if="parish" label="Select Koottayma">
            <SearchBox
              v-model="koottayma"
              :load-options="loadKoottaymasByParish"
              :create-option="false"
              :reload-method="activeKoottaymaListRefetch"
              bg-color="#0f172a"
            />
          </FormField>
        </div>
        <div class="flex flex justify-between">
          <div class="w-full pt-7">
            <div class="theme-color w-full rounded-2xl bg-white p-2">
              <Disclosure v-slot="{ open }">
                <DisclosureButton
                  class="disclosure-heading flex w-full justify-between rounded-lg bg-purple-100 px-4 py-2 text-left text-sm font-medium text-purple-900 hover:bg-transparent focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75"
                >
                  <span>Create New Parish</span>
                  <ChevronUpIcon
                    :class="open ? 'rotate-180 transform' : ''"
                    class="disclosure-heading h-5 w-5 text-purple-500"
                  />
                </DisclosureButton>
                <DisclosurePanel class="px-4 pt-4 pb-2 text-sm text-white">
                  <FormField label="Parish Name">
                    <FormControl
                      v-model="createParishForm.parishName"
                      type="text"
                      :icon="mdiChurch"
                      placeholder="St. Peter's Church"
                    />
                  </FormField>
                  <FormField label="Building">
                    <FormControl
                      v-model="createParishForm.address.buildingName"
                    />
                  </FormField>
                  <FormField label="Forane">
                    <SearchBox
                      v-model="formForane"
                      :load-options="loadForanes"
                      :create-option="false"
                      :reload-method="activeForaneListRefetch"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="Phone">
                    <FormControl
                      v-model="createParishForm.phone"
                      type="tel"
                      placeholder="04792662745"
                    />
                  </FormField>
                  <AddressForm
                    ref="addressFormComponent"
                    @address-form-change="changeInAddressFormData"
                  />
                  <BaseButton
                    class="baseButtonStyle"
                    color="info"
                    label="Submit"
                    @click="submitCreateParishForm"
                  />
                </DisclosurePanel>
              </Disclosure>
              <RemoveEntityDisclosure
                :entity="koottayma"
                heading="Remove Koottayma"
                content="Are you sure you want to remove this Koottayma"
                button-label="Yes, Remove this Koottayma"
                :button-method="deactivateParishButtonMethod"
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
</style>
