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
  mdiAccount,
  mdiCashMultiple,
  mdiTableLarge,
} from "@mdi/js";

import SearchBox from "@/components/SearchBox.vue";
import LayoutAuthenticated from "@/layouts/LayoutAuthenticated.vue";
import SectionMain from "@/components/SectionMain.vue";
import FormField from "@/components/FormField.vue";
import FormCheckRadioGroup from "@/components/FormCheckRadioGroup.vue";
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
  personAllForaneListQuery,
  personAllParishListQuery,
  personAllPersonListQuery,
  parishPageActivePersonTableQuery,
  personAllFamilyListQuery,
  personAllRelationListQuery,
} from "@/externalized-data/graphqlQueries";
import {
  createPersonMutation,
  deactivatePersonMutation,
  createRelationMutation,
} from "@/externalized-data/graphqlMutations";
import { personTableHeaders } from "@/externalized-data/tableData";

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

// const tableTabTitle = parishPageTableTabTitle;

const forane = ref();
const parish = ref();
const person = ref();

const ACTIVE_FORANE_LIST_QUERY = gql`
  ${personAllForaneListQuery}
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
  ${personAllParishListQuery}
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

const ACTIVE_PERSON_BY_PARISH_LIST_QUERY = gql`
  ${personAllPersonListQuery}
`;

const {
  result: activePersonList,
  load: activePersonListLoad,
  refetch: activePersonListRefetch,
} = useLazyQuery(ACTIVE_PERSON_BY_PARISH_LIST_QUERY, () => ({
  parishId: parish.value.id,
}));
const loadPersonsByParish = (query, setOptions) => {
  setOptions(
    activePersonList.value?.getAllPersonsByParish?.map((entity) => {
      return {
        id: entity.personId,
        label: entity.personName,
      };
    }) ?? []
  );
};

// Entity Count in Person Page
// const activeEntityByPersonCountEnabled = ref(false);

// const ACTIVE_ENTITY_BY_PERSON_COUNT_QUERY = gql`
//   ${personPageActiveEnityCountQuery}
// `;

// const {
//   result: activeEntityByPersonCount,
//   refetch: activeEntityByPersonCountRefetch,
// } = useQuery(
//   ACTIVE_ENTITY_BY_PERSON_COUNT_QUERY,
//   () => ({ id: parish.value.id }),
//   () => ({ enabled: activeEntityByPersonCountEnabled })
// );

watch(forane, () => {
  activeParishListLoad();
});

watch(parish, () => {
  activePersonListLoad();
});

// watch(person, () => {
//   activeEntityByParishCountEnabled.value = true;
// });

const createPersonForm = reactive({
  personName: "",
  baptismName: "",
  gender: "",
  dob: "",
  phone: "",
  familyId: "",
  relationId: "",
  educationIds: [],
  occupationIds: [],
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

// Form Family Search Box
const formFamily = ref();

const ACTIVE_FAMILY_BY_PARISH_LIST_QUERY = gql`
  ${personAllFamilyListQuery}
`;

const {
  result: activeFormFamilyList,
  load: activeFormFamilyListLoad,
  refetch: activeFormFamilyListRefetch,
} = useLazyQuery(ACTIVE_FAMILY_BY_PARISH_LIST_QUERY, () => ({
  parishId: formParish.value.id,
}));
const loadFormFamiliesByParish = (query, setOptions) => {
  setOptions(
    activeFormFamilyList.value?.getAllFamiliesByParish?.map((entity) => {
      return {
        id: entity.familyId,
        label: entity.familyName,
      };
    }) ?? []
  );
};

watch(formForane, () => {
  activeFormParishListLoad();
});

watch(formParish, () => {
  activeFormFamilyListLoad();
});

watch(formFamily, (value) => {
  createPersonForm.familyId = value.id;
});

// Form Relation Search Box
const formRelation = ref();

const RELATION_LIST_QUERY = gql`
  ${personAllRelationListQuery}
`;

const {
  result: formRelationList,
  load: formRelationListLoad,
  refetch: formRelationListRefetch,
} = useLazyQuery(RELATION_LIST_QUERY);
formRelationListLoad();
const loadFormRelations = (query, setOptions) => {
  setOptions(
    formRelationList.value?.getAllRelations?.map((entity) => {
      return {
        id: entity.relationId,
        label: entity.relationName,
      };
    }) ?? []
  );
};

// Create Relation Option
const CREATE_RELATION_MUTATION = gql`
  ${createRelationMutation}
`;

const {
  mutate: createRelation,
  loading: createRelationLoading,
  onDone: createRelationDone,
  onError: createRelationError,
} = useMutation(CREATE_RELATION_MUTATION);

const createRelationOption = (option, setSelected) => {
  createRelation({ relation: option.label });

  // Not using loading for now

  createRelationDone((mutationResult) => {
    setSelected({
      id: mutationResult.data?.createOneRelation?.relationId ?? "",
      label: mutationResult.data?.createOneRelation?.relationName ?? "",
    });
  });
};

watch(formRelation, (value) => {
  createPersonForm.relationId = value.id;
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

// Submit Create Person Form
const CREATE_PERSON_MUTATION = gql`
  ${createPersonMutation}
`;

const {
  mutate: createPerson,
  loading: createPersonLoading,
  onDone: createPersonDone,
} = useMutation(CREATE_PERSON_MUTATION);

const submitCreatePersonForm = () => {
  if (
    hasEmptyValues(createPersonForm, [
      "buildingName",
      "phone",
      "educationIds",
      "occupationIds",
    ])
  ) {
    console.log("Empty Values: " + createPersonForm);
  } else {
    console.log("Complete Values: " + createPersonForm);
    createPerson({ person: createPersonForm });
  }
};

watch(createPersonLoading, (value) => {
  infoNotificationEnabled.value = createPersonLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Creating Person.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

createPersonDone(() => {
  console.log("onDone called");
  successNotificationEnabled.value = true;
  successNotificationHeading.value = "Created Person.";
  successNotificationContent.value = "";

  createPersonForm.personName = "";
  createPersonForm.baptismName = "";
  createPersonForm.gender = "";
  createPersonForm.dob = "";
  createPersonForm.phone = "";
  formForane.value = "";
  formParish.value = "";
  formFamily.value = "";
  formRelation.value = "";

  setTimeout(() => {
    successNotificationEnabled.value = false;
    successNotificationHeading.value = "";
    successNotificationContent.value = "";
  }, 3000);
});

// Remove Parish
const DEACTIVATE_PERSON_MUTATION = gql`
  ${deactivatePersonMutation}
`;

const {
  mutate: deactivatePerson,
  loading: deactivatePersonLoading,
  onDone: deactivatePersonDone,
  onError: deactivatePersonError,
} = useMutation(DEACTIVATE_PERSON_MUTATION);

const deactivatePersonButtonMethod = () => {
  if (person.value.id != "") {
    deactivatePerson({ personId: person.value.id });
  } else {
    console.log("Person ID is empty");
  }
};

watch(deactivatePersonLoading, (value) => {
  infoNotificationEnabled.value = deactivatePersonLoading.value;
  if (value === true) {
    infoNotificationHeading.value = "Removing Person.";
    infoNotificationContent.value = "Please Wait...";
  } else {
    infoNotificationHeading.value = "";
    infoNotificationContent.value = "";
  }
});

deactivatePersonDone(() => location.reload());

deactivatePersonError(() => {
  console.log("Some Error occured while removing person");
  dangerNotificationEnabled.value = true;
  dangerNotificationHeading.value = "Error Removing Person.";
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
// const ACTIVE_PERSON_QUERY = gql`
//   ${parishPageActivePersonTableQuery}
// `;
// const {
//   result: activePersonData,
//   load: activePersonDataLoad,
//   refetch: activePersonDataRefetch,
// } = useLazyQuery(ACTIVE_PERSON_QUERY, () => ({
//   parishId: parish.value.id,
// }));
// const getActivePersonRows = computed(() => {
//   return activePersonData.value?.getAllPersonsByParish ?? [];
// });
</script>

<template>
  <LayoutAuthenticated>
    <!-- Show only if super admin is logged in -->
    <SectionTitle :first="true" :last="true"> Person </SectionTitle>
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
          <FormField v-if="parish" label="Select Person">
            <SearchBox
              v-model="person"
              :load-options="loadPersonsByParish"
              :create-option="false"
              :reload-method="activePersonListRefetch"
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
                  <span>Create New Person</span>
                  <ChevronUpIcon
                    :class="open ? 'rotate-180 transform' : ''"
                    class="disclosure-heading h-5 w-5 text-purple-500"
                  />
                </DisclosureButton>
                <DisclosurePanel class="px-4 pt-4 pb-2 text-sm text-white">
                  <FormField label="Person Name">
                    <FormControl
                      v-model="createPersonForm.personName"
                      type="text"
                      :icon="mdiAccount"
                      placeholder="Mathew"
                    />
                  </FormField>
                  <FormField label="Baptism Name">
                    <FormControl
                      v-model="createPersonForm.baptismName"
                      type="text"
                      placeholder="Matt"
                    />
                  </FormField>
                  <FormField label="Gender">
                    <FormCheckRadioGroup
                      v-model="createPersonForm.gender"
                      name="sample-radio"
                      type="radio"
                      :options="{ M: 'Male', F: 'Female' }"
                    />
                  </FormField>
                  <FormField label="Date of Birth">
                    <FormControl
                      v-model="createPersonForm.dob"
                      type="text"
                      placeholder="YYYY-MM-DD"
                    />
                  </FormField>
                  <FormField label="Phone">
                    <FormControl
                      v-model="createPersonForm.phone"
                      type="tel"
                      placeholder="04792662745"
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
                  <FormField label="Parish">
                    <SearchBox
                      v-model="formParish"
                      :load-options="loadFormParishesByForane"
                      :create-option="false"
                      :reload-method="activeFormParishListRefetch"
                      bg-color="#1e293b"
                    />
                  </FormField>
                  <FormField label="Family">
                    <SearchBox
                      v-model="formFamily"
                      :load-options="loadFormFamiliesByParish"
                      :create-option="false"
                      :reload-method="activeFormFamilyListRefetch"
                      bg-color="#1e293b"
                    />
                  </FormField>

                  <FormField label="Relation">
                    <SearchBox
                      v-model="formRelation"
                      :load-options="loadFormRelations"
                      :create-option="createRelationOption"
                      :reload-method="formRelationListRefetch"
                      bg-color="#1e293b"
                    />
                  </FormField>

                  <BaseButton
                    class="baseButtonStyle"
                    color="success"
                    label="Submit"
                    @click="submitCreatePersonForm"
                  />
                </DisclosurePanel>
              </Disclosure>
              <RemoveEntityDisclosure
                :entity="person"
                heading="Remove Person"
                content="Are you sure you want to remove this person"
                button-label="Yes, Remove this Person"
                :button-method="deactivatePersonButtonMethod"
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

    <SectionMain v-if="person">
      <SectionTitleLineWithButton
        :icon="mdiChartTimelineVariant"
        :title="person.label"
        main
      >
        <BaseButton
          :icon="mdiReload"
          color="whiteDark"
          @click="activeEntityByPersonCountRefetch"
        />
      </SectionTitleLineWithButton>
      <div class="grid grid-cols-1 gap-6 lg:grid-cols-3 mb-6">
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

      <!-- <TableTabs :tabs="tableTabTitle">
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
      </TableTabs> -->
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
