export default {
  state: {
    merchantDetails: {},
  },
  getters: {
    getMerchantDetails: (state) => state.merchantDetails,
  },
  mutations: {
    setMerchantDetails: (state, value) => {
      state.merchantDetails = value;
    },
  },
  actions: {
    SET_MERCHANT_DATA: ({ commit }, merchantDetails) => {
      commit("setMerchantDetails", merchantDetails);
    },
  },
};
