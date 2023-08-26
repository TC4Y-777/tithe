<script setup>
import { reactive, ref, watch } from "vue";
import gql from "graphql-tag";
import { useQuery } from "@vue/apollo-composable";

import {
  similarStreetListQuery,
  similarCityListQuery,
  similarDistrictListQuery,
  similarStateListQuery,
  similarPincodeListQuery,
} from "@/externalized-data/graphqlQueries";

import FormField from "@/components/FormField.vue";
import SearchBox from "@/components/SearchBox.vue";

const emit = defineEmits(["addressFormChange"]);

const address = reactive({
  buildingName: "",
  streetId: "",
  cityId: "",
  districtId: "",
  stateId: "",
  pincodeId: "",
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
  address.streetId = value.id;
  emit("addressFormChange", address);
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
  address.cityId = value.id;
  emit("addressFormChange", address);
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
  address.districtId = value.id;
  emit("addressFormChange", address);
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
  address.stateId = value.id;
  emit("addressFormChange", address);
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
  address.pincodeId = value.id;
  emit("addressFormChange", address);
});

const clearAddressFields = () => {
  street.value = "";
  city.value = "";
  district.value = "";
  state.value = "";
  pincode.value = "";
};

defineExpose({
  clearAddressFields,
});
</script>

<template>
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
</template>
