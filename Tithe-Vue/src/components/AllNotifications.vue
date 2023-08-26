<script setup>
import { ref, watch } from "vue";

import {
  mdiAlert,
  mdiAlertCircle,
  mdiInformation,
  mdiCheckCircle,
} from "@mdi/js";

import NotificationBar from "@/components/NotificationBar.vue";
import BaseButton from "@/components/BaseButton.vue";

const props = defineProps({
  infoNotificationEnabled: Boolean,
  infoNotificationHeading: {
    type: String,
    default: "",
  },
  infoNotificationContent: {
    type: String,
    default: "",
  },
  successNotificationEnabled: Boolean,
  successNotificationHeading: {
    type: String,
    default: "",
  },
  successNotificationContent: {
    type: String,
    default: "",
  },
  warningNotificationEnabled: Boolean,
  warningNotificationHeading: {
    type: String,
    default: "",
  },
  warningNotificationContent: {
    type: String,
    default: "",
  },
  dangerNotificationEnabled: Boolean,
  dangerNotificationHeading: {
    type: String,
    default: "",
  },
  dangerNotificationContent: {
    type: String,
    default: "",
  },
});

const reactiveInfoNotificationEnabled = ref(props.infoNotificationEnabled);
watch(
  () => props.infoNotificationEnabled,
  () => {
    reactiveInfoNotificationEnabled.value = props.infoNotificationEnabled;
  }
);

const reactiveSuccessNotificationEnabled = ref(
  props.successNotificationEnabled
);
watch(
  () => props.successNotificationEnabled,
  () => {
    reactiveSuccessNotificationEnabled.value = props.successNotificationEnabled;
  }
);

const reactiveWarningNotificationEnabled = ref(
  props.warningNotificationEnabled
);
watch(
  () => props.warningNotificationEnabled,
  () => {
    reactiveWarningNotificationEnabled.value = props.warningNotificationEnabled;
  }
);

const reactiveDangerNotificationEnabled = ref(props.dangerNotificationEnabled);
watch(
  () => props.dangerNotificationEnabled,
  () => {
    reactiveDangerNotificationEnabled.value = props.dangerNotificationEnabled;
  }
);
</script>

<template>
  <!-- Info Notification -->
  <NotificationBar
    v-if="reactiveInfoNotificationEnabled"
    color="info"
    :icon="mdiInformation"
    :outline="false"
    @click="reactiveInfoNotificationEnabled = !reactiveInfoNotificationEnabled"
  >
    <b>{{ props.infoNotificationHeading }}</b>
    {{ props.infoNotificationContent }}
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

  <!-- Success Notification -->
  <NotificationBar
    v-if="reactiveSuccessNotificationEnabled"
    color="success"
    :icon="mdiCheckCircle"
    :outline="false"
    @click="
      reactiveSuccessNotificationEnabled = !reactiveSuccessNotificationEnabled
    "
  >
    <b>{{ props.successNotificationHeading }}</b>
    {{ props.successNotificationContent }}
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

  <!-- Warning Notification -->
  <NotificationBar
    v-if="reactiveWarningNotificationEnabled"
    color="warning"
    :icon="mdiAlert"
    :outline="false"
    @click="
      reactiveWarningNotificationEnabled = !reactiveWarningNotificationEnabled
    "
  >
    <b>{{ props.warningNotificationHeading }}</b>
    {{ props.warningNotificationContent }}
    <template #right>
      <BaseButton
        color="warning"
        :icon="mdiAlert"
        :outline="false"
        rounded-full
        small
      />
    </template>
  </NotificationBar>

  <!-- Danger Notification -->
  <NotificationBar
    v-if="reactiveDangerNotificationEnabled"
    color="danger"
    :icon="mdiAlertCircle"
    :outline="false"
    @click="
      reactiveDangerNotificationEnabled = !reactiveDangerNotificationEnabled
    "
  >
    <b>{{ props.dangerNotificationHeading }}</b>
    {{ props.dangerNotificationContent }}
    <template #right>
      <BaseButton
        color="danger"
        :icon="mdiAlertCircle"
        :outline="false"
        rounded-full
        small
      />
    </template>
  </NotificationBar>
</template>
