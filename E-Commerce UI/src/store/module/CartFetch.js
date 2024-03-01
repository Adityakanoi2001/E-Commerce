//import axios from "axios";
export default {
  state: {
    ItemsCartList: [],
  },
  getters: {
    getItemsCartList: (state) => state.ItemscartList,
  },
  mutations: {
    setItemsCartList: (state, value) => (state.ItemCartList = value),
  },
  actions: {
    getItemsCartListApi: ({ commit }, { success, userID }) => {
      fetch(`/api/cart/CartProductsById/${userID}`)
        .then((resp) => resp.json())
        .then((res) => {
          commit("setItemsCartList", res);
          success && success(res);
        });
    },
  },
};
