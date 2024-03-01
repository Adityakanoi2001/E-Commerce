//import axios from "axios";
export default {
  state: {
    ProductId: [],
  },
  getters: {
    getProductId: (state) => state.ProductId,
  },
  mutations: {
    setProductId: (state, value) => (state.ProductId = value),
  },
  actions: {
    getProductIdApi: ({ commit }, { success, productId }) => {
      fetch(`/api/products/getByProductId/${productId}`)
        .then((resp) => resp.json())
        .then((res) => {
          commit("setProductId", res);
          success && success(res);
        });
    },
  },
};
