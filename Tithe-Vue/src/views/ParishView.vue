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
import SectionTitle from "@/components/SectionTitle.vue";
import CardBox from "@/components/CardBox.vue";
import CardBoxWidget from "@/components/CardBoxWidget.vue";
import LineChart from "@/components/Charts/LineChart.vue";
import NotificationBar from "@/components/NotificationBar.vue";
import TableSampleClients from "@/components/TableSampleClients.vue";

import TableTabs from "@/components/TableTabs.vue";
import {
  parishAllForaneListQuery,
  parishAllParishListQuery,
  parishPageActiveEnityCountQuery,
  similarStreetListQuery,
  similarCityListQuery,
  similarDistrictListQuery,
  similarStateListQuery,
  similarPincodeListQuery,
  parishPageActiveKoottaymaTableQuery,
  parishPageActiveFamilyTableQuery,
  parishPageActivePersonTableQuery,
} from "@/externalized-data/graphqlQueries";
import { createForaneMutation } from "@/externalized-data/graphqlMutations";
import {
  parishPageTableTabTitle,
  koottaymaTableHeaders,
  familyTableHeaders,
  personTableHeaders,
} from "@/externalized-data/tableData";

const tableTabTitle = parishPageTableTabTitle;

// const options = [
//   { id: 1, label: "Wade Cooper" },
//   { id: 2, label: "Arlene Mccoy" },
//   { id: 3, label: "Devon Webb" },
//   { id: 4, label: "Tom Cook" },
//   { id: 5, label: "Tanya Fox" },
//   { id: 6, label: "Hellen Schmidt" },
// ];

const forane = ref();
const parish = ref();

const ACTIVE_FORANE_LIST_QUERY = gql`
  ${parishAllForaneListQuery}
`;

const {
  result: activeForaneList,
  load: activeForaneListLoad,
  refetch: activeForaneListRefetch,
} = useLazyQuery(ACTIVE_FORANE_LIST_QUERY);
activeForaneListLoad();
const loadForanes = (query, setOptions) => {
  setOptions(
    activeForaneList.value?.getAllForanes.map((entity) => {
      return {
        id: entity.foraneId,
        label: entity.foraneName,
      };
    }) ?? []
  );
};

const ACTIVE_PARISH_BY_FORANE_LIST_QUERY = gql`
  ${parishAllParishListQuery}
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
    activeParishList.value?.getAllParishesByForane.map((entity) => {
      return {
        id: entity.parishId,
        label: entity.parishName,
      };
    }) ?? []
  );
};

// Entity Count in Parish Page
const activeEntityByParishCountEnabled = ref(false);

const ACTIVE_ENTITY_BY_PARISH_COUNT_QUERY = gql`
  ${parishPageActiveEnityCountQuery}
`;

const {
  result: activeEntityByParishCount,
  refetch: activeEntityByParishCountRefetch,
} = useQuery(
  ACTIVE_ENTITY_BY_PARISH_COUNT_QUERY,
  () => ({ id: parish.value.id }),
  () => ({ enabled: activeEntityByParishCountEnabled })
);
const activeKoottaymaCount = computed(
  () => activeEntityByParishCount.value?.getKoottaymaCountByParish ?? 0
);
const activeFamilyCount = computed(
  () => activeEntityByParishCount.value?.getFamilyCountByParish ?? 0
);
const activePersonCount = computed(
  () => activeEntityByParishCount.value?.getPersonCountByParish ?? 0
);

watch(forane, () => {
  activeParishListLoad();
});

watch(parish, () => {
  activeEntityByParishCountEnabled.value = true;
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
  createParishForm.address.streetId = value.id;
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
  createParishForm.address.cityId = value.id;
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
  createParishForm.address.districtId = value.id;
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
  createParishForm.address.stateId = value.id;
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
  createParishForm.address.pincodeId = value.id;
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

// Remove Forane
const DEACTIVATE_FORANE_MUTATION = gql`
  ${activateForaneMutation}
`;

const {
    mutate: deactivateForane,
    loading: deactivateForaneLoading,
    onDone: deactivateForaneDone,
    onError: deactivateForaneError,
} = useMutation(DEACTIVATE_FORANE_MUTATION);

const removeForaneButtonMethod = () => {
    if (forane.value.id != "") {
        deactivateForane({ foraneId: forane.value.id });
    } else {
        console.log("Forane ID is empty");
    }
};

deactivateForaneDone(() => location.reload());


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
    <SectionTitle :first="true" :last="true"> Parish </SectionTitle>
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
                  <FormField label="Forane Name">
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
                    @click="submitcreateParishForm"
                  />
                </DisclosurePanel>
              </Disclosure>
              <RemoveEntityDisclosure
                    :entity="parish"
                    heading="Remove Parish"
                    content="Are you sure you want to remove this parish"
                    button-label="Yes, Remove this Parish"
                    :button-method="removeForaneButtonMethod"
                  />
            </div>
          </div>
        </div>
      </div>
      <NotificationBar
        v-if="createParishLoading"
        color="info"
        :icon="mdiInformation"
        :outline="false"
      >
        <b>Creating Parish</b>. Please Wait...
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
        v-if="parishCreatedNotification"
        color="success"
        :icon="mdiCheckCircle"
        :outline="false"
      >
        <b>Parish Created</b>.
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

    <SectionMain v-if="parish">
      <SectionTitleLineWithButton
        :icon="mdiChartTimelineVariant"
        :title="parish.label"
        main
      >
        <BaseButton
          :icon="mdiReload"
          color="whiteDark"
          @click="activeEntityByParishCountRefetch"
        />
      </SectionTitleLineWithButton>
      <div class="grid grid-cols-1 gap-6 lg:grid-cols-3 mb-6">
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
                activeKoottaymaDataLoad() || activeKoottaymaDataRefetch()
              "
            >
              <TableSampleClients
                id-name="koottaymaId"
                :table-headers="koottaymaTableHeaders"
                :row-data="getActiveKoottaymaRows"
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

.disclosure-heading-careful {
  background-color: #ef4444;
  color: white;
  font-weight: bold;
}

.baseButtonStyle {
  width: 100%;
}
</style>
