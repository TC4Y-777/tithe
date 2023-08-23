<script setup>
import { reactive, ref, onMounted, computed, watch } from "vue";
import gql from "graphql-tag";
import { useLazyQuery, useMutation, useQuery } from "@vue/apollo-composable";
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
  mdiInformation,
  mdiCheckCircle,
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
import CardBox from "@/components/CardBox.vue";
import CardBoxWidget from "@/components/CardBoxWidget.vue";
import LineChart from "@/components/Charts/LineChart.vue";
import NotificationBar from "@/components/NotificationBar.vue";
import TableSampleClients from "@/components/TableSampleClients.vue";

import TableTabs from "@/components/TableTabs.vue";
import {
  foraneAllForaneListQuery,
  foranePageActiveEnityCountQuery,
  similarStreetListQuery,
  similarCityListQuery,
  similarDistrictListQuery,
  similarStateListQuery,
  similarPincodeListQuery,
  foranePageActiveParishTableQuery,
  foranePageActiveFamilyTableQuery,
  foranePageActivePersonTableQuery,
} from "@/externalized-data/graphqlQueries";
import { createForaneMutation } from "@/externalized-data/graphqlMutations";
import {
  foranePageTableTabTitle,
  parishTableHeaders,
  familyTableHeaders,
  personTableHeaders,
} from "@/externalized-data/tableData";

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
  // activeForaneListLoad() || activeForaneListRefetch();
  setOptions(
    activeForaneList.value?.getAllForanes.map((entity) => {
      return {
        id: entity.foraneId,
        label: entity.foraneName,
      };
    }) ?? []
  );
};

const router = useRouter();
const createForaneOption = (option, setSelected) => {
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

// Entity Count in Forane Page
const ACTIVE_ENTITY_BY_FORANE_COUNT_QUERY = gql`
  ${foranePageActiveEnityCountQuery}
`;

const {
  result: activeEntityByForaneCount,
  load: activeEntityByForaneCountLoad,
  refetch: activeEntityByForaneCountRefetch,
} = useLazyQuery(ACTIVE_ENTITY_BY_FORANE_COUNT_QUERY, forane);
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

watch(forane, (value) => {
  activeEntityByForaneCountLoad(null, value) ||
    activeEntityByForaneCountRefetch(value);
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

// Street Search Box
const street = ref();

const streetListQueryEnabled = ref(false);

const STREET_LIST_QUERY = gql`
  ${similarStreetListQuery}
`;

const {
  onResult: similarStreetListOnResult,
  variables: similarStreetListVariables,
} = useQuery(
  STREET_LIST_QUERY,
  {
    streetName: "",
  },
  () => ({
    enabled: streetListQueryEnabled,
  })
);

const loadStreets = (query, setOptions) => {
  streetListQueryEnabled.value = true;
  similarStreetListVariables.value = {
    streetName: query,
  };
  similarStreetListOnResult((queryResult) => {
    setOptions(
      queryResult.data?.getSimilarStreets?.map((entity) => {
        return {
          id: entity.streetId,
          label: entity.streetName,
        };
      }) ?? []
    );
  });
};

watch(street, (value) => {
  createForaneForm.address.streetId = value.id;
});

// City Search Box
const city = ref();

const cityListQueryEnabled = ref(false);

const CITY_LIST_QUERY = gql`
  ${similarCityListQuery}
`;

const {
  onResult: similarCityListOnResult,
  variables: similarCityListVariables,
} = useQuery(
  CITY_LIST_QUERY,
  {
    cityName: "",
  },
  () => ({
    enabled: cityListQueryEnabled,
  })
);

const loadCities = (query, setOptions) => {
  cityListQueryEnabled.value = true;
  similarCityListVariables.value = {
    cityName: query,
  };
  similarCityListOnResult((queryResult) => {
    setOptions(
      queryResult.data?.getSimilarCities?.map((entity) => {
        return {
          id: entity.cityId,
          label: entity.cityName,
        };
      }) ?? []
    );
  });
};

watch(city, (value) => {
  createForaneForm.address.cityId = value.id;
});

// District Search Box
const district = ref();

const districtListQueryEnabled = ref(false);

const DISTRICT_LIST_QUERY = gql`
  ${similarDistrictListQuery}
`;

const {
  onResult: similarDistrictListOnResult,
  variables: similarDistrictListVariables,
} = useQuery(
  DISTRICT_LIST_QUERY,
  {
    districtName: "",
  },
  () => ({
    enabled: districtListQueryEnabled,
  })
);

const loadDistricts = (query, setOptions) => {
  districtListQueryEnabled.value = true;
  similarDistrictListVariables.value = {
    districtName: query,
  };
  similarDistrictListOnResult((queryResult) => {
    setOptions(
      queryResult.data?.getSimilarDistricts?.map((entity) => {
        return {
          id: entity.districtId,
          label: entity.districtName,
        };
      }) ?? []
    );
  });
};

watch(district, (value) => {
  createForaneForm.address.districtId = value.id;
});

// State Search Box
const state = ref();

const stateListQueryEnabled = ref(false);

const STATE_LIST_QUERY = gql`
  ${similarStateListQuery}
`;

const {
  onResult: similarStateListOnResult,
  variables: similarStateListVariables,
} = useQuery(
  STATE_LIST_QUERY,
  {
    stateName: "",
  },
  () => ({
    enabled: stateListQueryEnabled,
  })
);

const loadStates = (query, setOptions) => {
  stateListQueryEnabled.value = true;
  similarStateListVariables.value = {
    stateName: query,
  };
  similarStateListOnResult((queryResult) => {
    setOptions(
      queryResult.data?.getSimilarStates?.map((entity) => {
        return {
          id: entity.stateId,
          label: entity.stateName,
        };
      }) ?? []
    );
  });
};

watch(state, (value) => {
  createForaneForm.address.stateId = value.id;
});

// Pincode Search Box
const pincode = ref();

const pincodeListQueryEnabled = ref(false);

const PINCODE_LIST_QUERY = gql`
  ${similarPincodeListQuery}
`;

const {
  onResult: similarPincodeListOnResult,
  variables: similarPincodeListVariables,
} = useQuery(
  PINCODE_LIST_QUERY,
  {
    pincode: "",
  },
  () => ({
    enabled: pincodeListQueryEnabled,
  })
);

const loadPincodes = (query, setOptions) => {
  pincodeListQueryEnabled.value = true;
  similarPincodeListVariables.value = {
    pincode: query,
  };
  similarPincodeListOnResult((queryResult) => {
    setOptions(
      queryResult.data?.getSimilarPincodes?.map((entity) => {
        return {
          id: entity.pincodeId,
          label: entity.pincode,
        };
      }) ?? []
    );
  });
};

watch(pincode, (value) => {
  createForaneForm.address.pincodeId = value.id;
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
// Code for assigning empty values to object fields
function assignEmptyValues(obj) {
  for (let key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key)) {
      if (typeof obj[key] === "object") {
        assignEmptyValues(obj[key]);
      } else {
        obj[key] = "";
      }
    }
  }
}

// Submit Create Forane Form
const foraneCreatedNotification = ref(false);

const CREATE_FORANE_MUTATION = gql`
  ${createForaneMutation}
`;

const {
  mutate: createForane,
  loading: createForaneLoading,
  onDone: createForaneDone,
} = useMutation(CREATE_FORANE_MUTATION);

const submitCreateForaneForm = () => {
  if (hasEmptyValues(createForaneForm, ["buildingName", "phone"])) {
    console.log("Empty Values: " + createForaneForm);
  } else {
    console.log("Complete Values: " + createForaneForm);
    createForane({ forane: createForaneForm });
  }
};

createForaneDone(() => {
  console.log("onDone called");
  foraneCreatedNotification.value = true;
  createForaneForm.foraneName = "";
  createForaneForm.phone = "";
  createForaneForm.address.buildingName = "";
  street.value = {};
  city.value = {};
  district.value = {};
  state.value = {};
  pincode.value = {};
  setTimeout(() => {
    foraneCreatedNotification.value = false;
  }, 3000);
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
} = useLazyQuery(ACTIVE_PARISH_QUERY);
const getActiveParishRows = computed(() => {
  return activeParishData.value?.getAllParishes ?? [];
});

// Family Table Data
const ACTIVE_FAMILY_QUERY = gql`
  ${foranePageActiveFamilyTableQuery}
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
  ${foranePageActivePersonTableQuery}
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
    <!-- Show only if super admin is logged in -->
    <SectionMain>
      <!-- <SearchBox v-model="user" :options="options" /> -->

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div class="flex flex-col justify-between">
          <FormField label="Select Forane">
            <SearchBox
              v-model="forane"
              :load-options="loadForanes"
              :create-option="createForaneOption"
              :reload-method="activeForaneListRefetch"
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
                      v-model="createForaneForm.foraneName"
                      type="text"
                      :icon="mdiChurch"
                      placeholder="St. Peter's Church"
                    />
                  </FormField>
                  <FormField label="Building">
                    <FormControl
                      v-model="createForaneForm.address.buildingName"
                    />
                  </FormField>
                  <FormField label="Phone">
                    <FormControl
                      v-model="createForaneForm.phone"
                      type="tel"
                      placeholder="04792662745"
                    />
                  </FormField>
                  <FormField label="Street">
                    <SearchBox
                      v-model="street"
                      :load-options="loadStreets"
                      :create-option="false"
                      :reload-method="false"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="City">
                    <SearchBox
                      v-model="city"
                      :load-options="loadCities"
                      :create-option="false"
                      :reload-method="false"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="District">
                    <SearchBox
                      v-model="district"
                      :load-options="loadDistricts"
                      :create-option="false"
                      :reload-method="false"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="State">
                    <SearchBox
                      v-model="state"
                      :load-options="loadStates"
                      :create-option="false"
                      :reload-method="false"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="Pincode">
                    <SearchBox
                      v-model="pincode"
                      :load-options="loadPincodes"
                      :create-option="false"
                      :reload-method="false"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <BaseButton
                    class="baseButtonStyle"
                    color="info"
                    label="Submit"
                    @click="submitCreateForaneForm"
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
      <NotificationBar
        v-if="createForaneLoading"
        color="info"
        :icon="mdiInformation"
        :outline="false"
      >
        <b>Creating Forane</b>. Please Wait...
        <template #right>
          <BaseButton
            color="info"
            :icon="mdiInformation"
            :outline="false"
            rounded-full
            small
          />
        </template>
      </NotificationBar>
      <NotificationBar
        v-if="foraneCreatedNotification"
        color="success"
        :icon="mdiCheckCircle"
        :outline="false"
      >
        <b>Forane Created</b>.
        <template #right>
          <BaseButton
            color="success"
            :icon="mdiCheckCircle"
            :outline="false"
            rounded-full
            small
          />
        </template>
      </NotificationBar>
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
