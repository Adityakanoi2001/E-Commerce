//import axios from "axios";
export default {
  state: {
    MerchnatsList: [],
  },
  getters: {
    getMerchnatsList: (state) => state.MerchnatsList,
  },
  mutations: {
    setMerchnatsList: (state, value) => (state.MerchnatsList = value),
  },
  actions: {
    getMerchnatsListApi: ({ commit }, { success, MerchantId, productName }) => {
      fetch(
        `/api/products/findMerchnatByProductName/${MerchantId}/${productName}`
      )
        .then((resp) => resp.json())
        .then((res) => {
          commit("setMerchnatsList", res);
          success && success(res);
        });
    },
  },
};
