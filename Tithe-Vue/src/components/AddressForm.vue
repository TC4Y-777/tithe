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

const createStreetOption = (option, setSelected) => {
  createStreet({ streetName: option.label });

  // Not using loading for now

  createStreetDone((mutationResult) => {
    setSelected({
      id: mutationResult.data?.createOneStreet?.streetId ?? "",
      label: mutationResult.data?.createOneStreet?.streetName ?? "",
    });
  });
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

const createCityOption = (option, setSelected) => {
  createCity({ cityName: option.label });

  // Not using loading for now

  createCityDone((mutationResult) => {
    setSelected({
      id: mutationResult.data?.createOneCity?.cityId ?? "",
      label: mutationResult.data?.createOneCity?.cityName ?? "",
    });
  });
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

const createDistrictOption = (option, setSelected) => {
  createDistrict({ districtName: option.label });

  // Not using loading for now

  createDistrictDone((mutationResult) => {
    setSelected({
      id: mutationResult.data?.createOneDistrict?.districtId ?? "",
      label: mutationResult.data?.createOneDistrict?.districtName ?? "",
    });
  });
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

const createStateOption = (option, setSelected) => {
  createState({ stateName: option.label });

  // Not using loading for now

  createStateDone((mutationResult) => {
    setSelected({
      id: mutationResult.data?.createOneState?.stateId ?? "",
      label: mutationResult.data?.createOneState?.stateName ?? "",
    });
  });
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

const createPincodeOption = (option, setSelected) => {
  createPincode({ pincode: option.label });

  // Not using loading for now

  createPincodeDone((mutationResult) => {
    setSelected({
      id: mutationResult.data?.createOnePincode?.pincodeId ?? "",
      label: mutationResult.data?.createOnePincode?.pincode ?? "",
    });
  });
};

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
  <!-- Changed street to address -->
  <FormField label="Address">
    <SearchBox
      v-model="street"
      :load-options="loadStreets"
      :create-option="createStreetOption"
      :reload-method="false"
      entity-name="Address"
      bg-color="#1e293b"
    />
  </FormField>
  <FormField label="City">
    <SearchBox
      v-model="city"
      :load-options="loadCities"
      :create-option="createCityOption"
      entity-name="City"
      :reload-method="false"
      bg-color="#1e293b"
    />
  </FormField>
  <FormField label="District">
    <SearchBox
      v-model="district"
      :load-options="loadDistricts"
      :create-option="createDistrictOption"
      entity-name="District"
      :reload-method="false"
      bg-color="#1e293b"
    />
  </FormField>
  <FormField label="State">
    <SearchBox
      v-model="state"
      :load-options="loadStates"
      :create-option="createStateOption"
      entity-name="State"
      :reload-method="false"
      bg-color="#1e293b"
    />
  </FormField>
  <FormField label="Pincode">
    <SearchBox
      v-model="pincode"
      :load-options="loadPincodes"
      :create-option="createPincodeOption"
      entity-name="Pincode"
      :reload-method="false"
      bg-color="#1e293b"
    />
  </FormField>
</template>
