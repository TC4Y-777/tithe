<template>
  <div>
    <div class="tabs">
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        class="tab"
        :class="{ active: activeTab === index }"
        @click="changeTab(index)"
      >
        {{ tab.title }}
      </div>
    </div>
    <div>
      <slot :index="activeTab"></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    tabs: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      activeTab: 0,
    };
  },
  methods: {
    changeTab(index) {
      this.activeTab = index;
    },
  },
};
</script>

<style scoped>
.tabs {
  display: flex;
  justify-content: space-between;
}

.tab {
  padding: 10px;
  cursor: pointer;

  border-bottom: none;
  border-radius: 4px 4px 0 0;
}

.tab.active {
  box-shadow: inset 0px 0px 10px 5px rgba(255, 255, 255, 0.3);
}
</style>
