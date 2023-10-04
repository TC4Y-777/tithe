<script setup>
import { reactive, ref, watch } from "vue";
import gql from "graphql-tag";
import { useQuery, useMutation } from "@vue/apollo-composable";

import {
  similarStreetListQuery,
  similarCityListQuery,
  similarDistrictListQuery,
  similarStateListQuery,
  similarPincodeListQuery,
} from "@/externalized-data/graphqlQueries";
import {
  createStreetMutation,
  createCityMutation,
  createDistrictMutation,
  createStateMutation,
  createPincodeMutation,
} from "@/externalized-data/graphqlMutations";

import FormField from "@/components/FormField.vue";
import SearchBox from "@/components/SearchBox.vue";
import SingleSelectBox from "@/components/SearchBoxes/SingleSelectBox.vue";

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
    fetchPolicy: "no-cache",
  })
);

// const loadStreets = (query, setOptions) => {
//   streetListQueryEnabled.value = true;
//   similarStreetListVariables.value = {
//     streetName: query,
//   };
//   similarStreetListOnResult((queryResult) => {
//     setOptions(
//       queryResult.data?.getSimilarStreets?.map((entity) => {
//         return {
//           id: entity.streetId,
//           label: entity.streetName,
//         };
//       }) ?? []
//     );
//   });
// };

const streetOptions = ref({});

const streetSearchChange = (query) => {
  if (query != "") {
    streetListQueryEnabled.value = true;
    similarStreetListVariables.value = {
      streetName: query,
    };

    similarStreetListOnResult((queryResult) => {
      streetOptions.value =
        queryResult.data?.getSimilarStreets?.map((entity) => {
          return {
            id: entity.streetId,
            label: entity.streetName,
            value: {
              id: entity.streetId,
              label: entity.streetName,
            },
          };
        }) ?? [];
    });
  }
};

const changeInStreet = (entity) => {
  street.value = entity;
};

watch(street, (value) => {
  address.streetId = value?.id ?? "";
  emit("addressFormChange", address);
});

// Create Street Option
const CREATE_STREET_MUTATION = gql`
  ${createStreetMutation}
`;

const {
  mutate: createStreet,
  loading: createStreetLoading,
  onDone: createStreetDone,
  onError: createStreetError,
} = useMutation(CREATE_STREET_MUTATION);

const createStreetOption = async (option, select$) => {
  createStreet({ streetName: option.label });

  // Not using loading for now

  await new Promise((resolve, reject) => {
    createStreetDone(() => {
      select$.clear();
      resolve("Success");
    });
  });

  return false;
};

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
    fetchPolicy: "no-cache",
  })
);

// const loadCities = (query, setOptions) => {
//   cityListQueryEnabled.value = true;
//   similarCityListVariables.value = {
//     cityName: query,
//   };
//   similarCityListOnResult((queryResult) => {
//     setOptions(
//       queryResult.data?.getSimilarCities?.map((entity) => {
//         return {
//           id: entity.cityId,
//           label: entity.cityName,
//         };
//       }) ?? []
//     );
//   });
// };

const cityOptions = ref({});

const citySearchChange = (query) => {
  if (query != "") {
    cityListQueryEnabled.value = true;
    similarCityListVariables.value = {
      cityName: query,
    };

    similarCityListOnResult((queryResult) => {
      cityOptions.value =
        queryResult.data?.getSimilarCities?.map((entity) => {
          return {
            id: entity.cityId,
            label: entity.cityName,
            value: {
              id: entity.cityId,
              label: entity.cityName,
            },
          };
        }) ?? [];
    });
  }
};

const changeInCity = (entity) => {
  city.value = entity;
};

watch(city, (value) => {
  address.cityId = value?.id ?? "";
  emit("addressFormChange", address);
});

// Create City Option
const CREATE_CITY_MUTATION = gql`
  ${createCityMutation}
`;

const {
  mutate: createCity,
  loading: createCityLoading,
  onDone: createCityDone,
  onError: createCityError,
} = useMutation(CREATE_CITY_MUTATION);

const createCityOption = async (option, select$) => {
  createCity({ cityName: option.label });

  await new Promise((resolve, reject) => {
    createCityDone(() => {
      select$.clear();
      resolve("Success");
    });
  });

  return false;
};

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
    fetchPolicy: "no-cache",
  })
);

const districtOptions = ref({});

const districtSearchChange = (query) => {
  if (query != "") {
    districtListQueryEnabled.value = true;
    similarDistrictListVariables.value = {
      districtName: query,
    };

    similarDistrictListOnResult((queryResult) => {
      districtOptions.value =
        queryResult.data?.getSimilarDistricts?.map((entity) => {
          return {
            id: entity.districtId,
            label: entity.districtName,
            value: {
              id: entity.districtId,
              label: entity.districtName,
            },
          };
        }) ?? [];
    });
  }
};

const changeInDistrict = (entity) => {
  district.value = entity;
};

watch(district, (value) => {
  address.districtId = value?.id ?? "";
  emit("addressFormChange", address);
});

// Create District Option
const CREATE_DISTRICT_MUTATION = gql`
  ${createDistrictMutation}
`;

const {
  mutate: createDistrict,
  loading: createDistrictLoading,
  onDone: createDistrictDone,
  onError: createDistrictError,
} = useMutation(CREATE_DISTRICT_MUTATION);

const createDistrictOption = async (option, select$) => {
  createDistrict({ districtName: option.label });

  await new Promise((resolve, reject) => {
    createDistrictDone(() => {
      select$.clear();
      resolve("Success");
    });
  });

  return false;
};

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
    fetchPolicy: "no-cache",
  })
);

const stateOptions = ref({});

const stateSearchChange = (query) => {
  if (query != "") {
    stateListQueryEnabled.value = true;
    similarStateListVariables.value = {
      stateName: query,
    };

    similarStateListOnResult((queryResult) => {
      stateOptions.value =
        queryResult.data?.getSimilarStates?.map((entity) => {
          return {
            id: entity.stateId,
            label: entity.stateName,
            value: {
              id: entity.stateId,
              label: entity.stateName,
            },
          };
        }) ?? [];
    });
  }
};

const changeInState = (entity) => {
  state.value = entity;
};

watch(state, (value) => {
  address.stateId = value?.id ?? "";
  emit("addressFormChange", address);
});

// Create State Option
const CREATE_STATE_MUTATION = gql`
  ${createStateMutation}
`;

const {
  mutate: createState,
  loading: createStateLoading,
  onDone: createStateDone,
  onError: createStateError,
} = useMutation(CREATE_STATE_MUTATION);

const createStateOption = async (option, select$) => {
  createState({ stateName: option.label });

  await new Promise((resolve, reject) => {
    createStateDone(() => {
      select$.clear();
      resolve("Success");
    });
  });

  return false;
};

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
    fetchPolicy: "no-cache",
  })
);

const pincodeOptions = ref({});

const pincodeSearchChange = (query) => {
  if (query != "") {
    pincodeListQueryEnabled.value = true;
    similarPincodeListVariables.value = {
      pincode: query,
    };

    similarPincodeListOnResult((queryResult) => {
      pincodeOptions.value =
        queryResult.data?.getSimilarPincodes?.map((entity) => {
          return {
            id: entity.pincodeId,
            label: entity.pincode,
            value: {
              id: entity.pincodeId,
              label: entity.pincode,
            },
          };
        }) ?? [];
    });
  }
};

const changeInPincode = (entity) => {
  pincode.value = entity;
};

watch(pincode, (value) => {
  address.pincodeId = value?.id ?? "";
  emit("addressFormChange", address);
});

// Create Pincode Option
const CREATE_PINCODE_MUTATION = gql`
  ${createPincodeMutation}
`;

const {
  mutate: createPincode,
  loading: createPincodeLoading,
  onDone: createPincodeDone,
  onError: createPincodeError,
} = useMutation(CREATE_PINCODE_MUTATION);

const createPincodeOption = async (option, select$) => {
  createPincode({ pincode: option.label });

  await new Promise((resolve, reject) => {
    createPincodeDone(() => {
      select$.clear();
      resolve("Success");
    });
  });

  return false;
};

const addressRef = ref(null);
const cityRef = ref(null);
const districtRef = ref(null);
const stateRef = ref(null);
const pincodeRef = ref(null);

const clearAddressFields = () => {
  street.value = "";
  city.value = "";
  district.value = "";
  state.value = "";
  pincode.value = "";

  addressRef.value.clearField();
  cityRef.value.clearField();
  districtRef.value.clearField();
  stateRef.value.clearField();
  pincodeRef.value.clearField();
};

defineExpose({
  clearAddressFields,
});
</script>

<template>
  <!-- Changed street to address -->
  <!-- <FormField label="Address">
    <SearchBox
      v-model="street"
      :load-options="loadStreets"
      :create-option="createStreetOption"
      :reload-method="false"
      entity-name="Address"
      bg-color="#1e293b"
    />
  </FormField> -->

  <FormField label="Address">
    <SingleSelectBox
      ref="addressRef"
      :options="streetOptions"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :create-option="true"
      :resolve-on-load="false"
      :min-chars="1"
      :on-create="createStreetOption"
      class="multipleSelectAddressBox"
      @search-change="streetSearchChange"
      @value-change="changeInStreet"
    />
  </FormField>
  <FormField label="City">
    <SingleSelectBox
      ref="cityRef"
      :options="cityOptions"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :create-option="true"
      :resolve-on-load="false"
      :min-chars="1"
      :on-create="createCityOption"
      class="multipleSelectAddressBox"
      @search-change="citySearchChange"
      @value-change="changeInCity"
    />
  </FormField>
  <FormField label="District">
    <SingleSelectBox
      ref="districtRef"
      :options="districtOptions"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :create-option="true"
      :resolve-on-load="false"
      :min-chars="1"
      :on-create="createDistrictOption"
      class="multipleSelectAddressBox"
      @search-change="districtSearchChange"
      @value-change="changeInDistrict"
    />
  </FormField>
  <FormField label="State">
    <SingleSelectBox
      ref="stateRef"
      :options="stateOptions"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :create-option="true"
      :resolve-on-load="false"
      :min-chars="1"
      :on-create="createStateOption"
      class="multipleSelectAddressBox"
      @search-change="stateSearchChange"
      @value-change="changeInState"
    />
  </FormField>
  <FormField label="Pincode">
    <SingleSelectBox
      ref="pincodeRef"
      :options="pincodeOptions"
      :can-deselect="false"
      :can-clear="false"
      :searchable="true"
      :create-option="true"
      :resolve-on-load="false"
      :min-chars="1"
      :on-create="createPincodeOption"
      class="multipleSelectAddressBox"
      @search-change="pincodeSearchChange"
      @value-change="changeInPincode"
    />
  </FormField>
</template>

<style scoped>
.multiselect-theme {
  --ms-bg: #1e293b;
  --ms-dropdown-bg: #1e293b;
  --ms-dropdown-border-color: #1e293b;

  --ms-py: 0.757rem;
}
</style>
