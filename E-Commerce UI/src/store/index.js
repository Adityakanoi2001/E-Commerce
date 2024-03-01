import Vue from "vue";
import Vuex from "vuex";
import product from "./module/product";
import ProductCart from "./module/ProductCart";
import CartFetch from "./module/CartFetch";
import UserRegistration from "./module/UserRegistration";
import merchantRegistration from "./module/merchantRegistration";
import UserDetailsFetch from "./module/UserDetailsFetch";
import PlaceOrder from "./module/PlaceOrder";
import OrdersFetch from "./module/OrdersFetch";
import MerchantsDetailsFetch from "./module/MerchantsDetailsFetch";
import ProductById from "./module/ProductById";
import SearchResultsFetch from "./module/SearchResultsFetch";
// import account from "./modules/account";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    product,
    ProductCart,
    CartFetch,
    UserRegistration,
    merchantRegistration,
    UserDetailsFetch,
    PlaceOrder,
    OrdersFetch,
    MerchantsDetailsFetch,
    ProductById,
    SearchResultsFetch,
    // account,
  },
  plugins: [createPersistedState()],
});
