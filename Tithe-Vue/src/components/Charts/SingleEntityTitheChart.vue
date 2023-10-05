<script setup>
import { computed, toRef, ref, watch } from "vue";

import LineChart from "@/components/Charts/LineChart.vue";

const props = defineProps({
  data: {
    type: Array,
    default: () => [],
  },
});

const chartColors = {
  default: {
    primary: "#00D1B2",
    info: "#209CEE",
    danger: "#FF3860",
  },
};

const datasetObject = (color, titheAmounts) => {
  return {
    fill: false,
    borderColor: chartColors.default[color],
    borderWidth: 2,
    borderDash: [],
    borderDashOffset: 0.0,
    pointBackgroundColor: chartColors.default[color],
    pointBorderColor: "rgba(255,255,255,0)",
    pointHoverBackgroundColor: chartColors.default[color],
    pointBorderWidth: 20,
    pointHoverRadius: 4,
    pointHoverBorderWidth: 15,
    pointRadius: 4,
    data: titheAmounts,
    tension: 0.5,
    cubicInterpolationMode: "default",
  };
};

const chartData = computed(() => {
  return {
    labels: props.data.map((obj) => obj.timeStamp),
    datasets: [
      datasetObject(
        "primary",
        props.data.map((obj) => obj.titheAmount)
      ),
    ],
  };
});
</script>

<template>
  <div v-if="props.data">
    <LineChart :data="chartData" class="h-96" />
  </div>
</template>
