// import Vue from "vue";
// import App from "./App.vue";
// import router from "./router";
// import store from "./store";
// import "bootstrap/dist/css/bootstrap.min.css";

// import firebase from "firebase/compat/app";
// import "firebase/compat/auth";
// import "firebase/compat/firestore";
// import "firebase/database";

// Vue.config.productionTip = false;
// Vue.prototype.$globalData = {
//   notLogin: true,
//   userEmail: "",
// };

// const firebaseconfig = {
//   apiKey: "AIzaSyDvf3iqsii_FxczvDyEXzKMgf2k_UnkT_k",
//   authDomain: "user-auth-a846b.firebaseapp.com",
//   projectId: "user-auth-a846b",
//   storageBucket: "user-auth-a846b.appspot.com",
//   messagingSenderId: "379800778573",
//   appId: "1:379800778573:web:d4a9f933acc8a7082da904",
// };
// firebase.initializeApp(firebaseconfig);

// const auth = firebase.default.auth();
// const database = firebase.default.database();

// export { auth, database };

// new Vue({
//   router,
//   store,
//   render: (h) => h(App),
// }).$mount("#app");

import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap/dist/css/bootstrap.min.css";
import firebase from "firebase/compat/app";
import "firebase/compat/auth";
import "firebase/compat/database";
import "firebase/compat/firestore";
import axios from "axios";
// import createPersistedState from 'vuex-persistedstate'
import Toasted from "vue-toasted";

Vue.use(Toasted, {
  duration: 2500,
});

Vue.prototype.$http = axios;
Vue.config.productionTip = false;
Vue.prototype.$globalData = {
  notlogin: true,
  email: "",
  searchTerm: "",
};

const firebaseConfig = {
  apiKey: "AIzaSyDvf3iqsii_FxczvDyEXzKMgf2k_UnkT_k",
  authDomain: "user-auth-a846b.firebaseapp.com",
  projectId: "user-auth-a846b",
  storageBucket: "user-auth-a846b.appspot.com",
  messagingSenderId: "379800778573",
  appId: "1:379800778573:web:d4a9f933acc8a7082da904",
};

firebase.initializeApp(firebaseConfig);

const auth = firebase.default.auth();
const database = firebase.default.database();

export { auth, database };

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
