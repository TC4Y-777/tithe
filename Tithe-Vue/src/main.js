import { createApp, provide, h } from "vue";
import { createPinia } from "pinia";
import {
  ApolloClient,
  createHttpLink,
  InMemoryCache,
} from "@apollo/client/core";
import { DefaultApolloClient } from "@vue/apollo-composable";

import App from "./App.vue";
import router from "./router";
import { useMainStore } from "@/stores/main.js";
import { useStyleStore } from "@/stores/style.js";
// import { darkModeKey, styleKey } from "@/config.js";
import { styleKey } from "@/config.js";

import Multiselect from "@/components/MultiSelectBox/Multiselect.vue";

import "./css/main.css";

/* Init Pinia */
const pinia = createPinia();

// HTTP connection to the API
const httpLink = createHttpLink({
  // You should use an absolute URL here
  uri: import.meta.env.VITE_GRAPHQL_ENDPOINT,
});

// Cache implementation
const cache = new InMemoryCache();

// Create the apollo client
const apolloClient = new ApolloClient({
  link: httpLink,
  cache,
});

const app = createApp({
  setup() {
    provide(DefaultApolloClient, apolloClient);
  },

  render: () => h(App),
});

/* Create Vue app */
// createApp(App).use(router).use(pinia).mount("#app");
app.use(router).use(pinia).component("Multiselect", Multiselect).mount("#app");

/* Init Pinia stores */
const mainStore = useMainStore(pinia);
const styleStore = useStyleStore(pinia);

/* Fetch sample data */
mainStore.fetch("clients");
mainStore.fetch("history");

/* App style */
styleStore.setStyle(localStorage[styleKey] ?? "basic");

/* Dark mode */
// if (
//   (!localStorage[darkModeKey] &&
//     window.matchMedia("(prefers-color-scheme: dark)").matches) ||
//   localStorage[darkModeKey] === "1"
// ) {
//   styleStore.setDarkMode(true);
// }

/* Always dark mode */
styleStore.setDarkMode(true);

/* Default title tag */
const defaultDocumentTitle = "Tithify";

/* Set document title from route meta */
router.afterEach((to) => {
  document.title = to.meta?.title
    ? `${to.meta.title} — ${defaultDocumentTitle}`
    : defaultDocumentTitle;
});
