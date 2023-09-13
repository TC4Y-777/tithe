<script setup>
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

const emits = defineEmits([
  "infoFalse",
  "successFalse",
  "warningFalse",
  "dangerFalse",
]);

const infoFalse = () => {
  emits("infoFalse");
};

const successFalse = () => {
  emits("successFalse");
};

const warningFalse = () => {
  emits("warningFalse");
};

const dangerFalse = () => {
  emits("dangerFalse");
};
</script>

<template>
  <!-- Info Notification -->
  <NotificationBar
    v-if="props.infoNotificationEnabled"
    color="info"
    :icon="mdiInformation"
    :outline="false"
    @click="infoFalse"
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
    v-if="props.successNotificationEnabled"
    color="success"
    :icon="mdiCheckCircle"
    :outline="false"
    @click="successFalse"
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
    v-if="props.warningNotificationEnabled"
    color="warning"
    :icon="mdiAlert"
    :outline="false"
    @click="warningFalse"
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
    v-if="props.dangerNotificationEnabled"
    color="danger"
    :icon="mdiAlertCircle"
    :outline="false"
    @click="dangerFalse"
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
