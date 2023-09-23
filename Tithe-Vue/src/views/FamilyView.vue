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

import SearchBox from "@/components/SearchBox.vue";
import ForaneSingleSelectBox from "@/components/SearchBoxes/ForaneSingleSelectBox.vue";
import ParishByForaneSingleSelectBox from "@/components/SearchBoxes/ParishByForaneSingleSelectBox.vue";
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
  familyAllForaneListQuery,
  familyAllParishListQuery,
  familyAllFamilyListQuery,
  familyPageActiveEnityCountQuery,
  familyAllKoottaymaListQuery,
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

const ACTIVE_FORANE_LIST_QUERY = gql`
  ${familyAllForaneListQuery}
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
  ${familyAllParishListQuery}
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

const ACTIVE_FAMILY_BY_PARISH_LIST_QUERY = gql`
  ${familyAllFamilyListQuery}
`;

const {
  result: activeFamilyList,
  load: activeFamilyListLoad,
  refetch: activeFamilyListRefetch,
} = useLazyQuery(ACTIVE_FAMILY_BY_PARISH_LIST_QUERY, () => ({
  parishId: parish.value.id,
}));
const loadFamiliesByParish = (query, setOptions) => {
  setOptions(
    activeFamilyList.value?.getAllFamiliesByParish?.map((entity) => {
      return {
        id: entity.familyId,
        label: entity.familyName,
      };
    }) ?? []
  );
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

watch(forane, () => {
  activeParishListLoad();
});

watch(parish, () => {
  activeFamilyListLoad();
});

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

const {
  result: activeFormParishList,
  load: activeFormParishListLoad,
  refetch: activeFormParishListRefetch,
} = useLazyQuery(ACTIVE_PARISH_BY_FORANE_LIST_QUERY, () => ({
  foraneId: formForane.value.id,
}));
const loadFormParishesByForane = (query, setOptions) => {
  setOptions(
    activeFormParishList.value?.getAllParishesByForane?.map((entity) => {
      return {
        id: entity.parishId,
        label: entity.parishName,
      };
    }) ?? []
  );
};

// Form Koottayma Search Box
const formKoottayma = ref();

const ACTIVE_KOOTTAYMA_BY_PARISH_LIST_QUERY = gql`
  ${familyAllKoottaymaListQuery}
`;

const {
  result: activeFormKoottaymaList,
  load: activeFormKoottaymaListLoad,
  refetch: activeFormKoottaymaListRefetch,
} = useLazyQuery(ACTIVE_KOOTTAYMA_BY_PARISH_LIST_QUERY, () => ({
  parishId: formParish.value.id,
}));
const loadFormKoottaymasByParish = (query, setOptions) => {
  setOptions(
    activeFormKoottaymaList.value?.getAllKoottaymasByParish?.map((entity) => {
      return {
        id: entity.koottaymaId,
        label: entity.koottaymaName,
      };
    }) ?? []
  );
};

watch(formForane, () => {
  activeFormParishListLoad();
});

watch(formParish, () => {
  activeFormKoottaymaListLoad();
});

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
          <FormField v-if="parish" label="Select Family">
            <SearchBox
              v-model="family"
              :load-options="loadFamiliesByParish"
              :create-option="false"
              :reload-method="activeFamilyListRefetch"
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
                      placeholder="Shalom House"
                    />
                  </FormField>
                  <!-- Remove Building name -->
                  <!-- <FormField label="Building">
                    <FormControl
                      v-model="createFamilyForm.address.buildingName"
                    />
                  </FormField> -->
                  <FormField label="Forane">
                    <SearchBox
                      v-model="formForane"
                      :load-options="loadForanes"
                      :create-option="false"
                      :reload-method="activeForaneListRefetch"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="Parish">
                    <SearchBox
                      v-model="formParish"
                      :load-options="loadFormParishesByForane"
                      :create-option="false"
                      :reload-method="activeFormParishListRefetch"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="Koottayma">
                    <SearchBox
                      v-model="formKoottayma"
                      :load-options="loadFormKoottaymasByParish"
                      :create-option="false"
                      :reload-method="activeFormKoottaymaListRefetch"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="Phone">
                    <FormControl
                      v-model="createFamilyForm.phone"
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
</style>
