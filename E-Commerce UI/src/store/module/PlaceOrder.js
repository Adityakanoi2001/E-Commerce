export default {
  state: {
    order: [],
  },
  getters: {
    getOrderList: (state) => state.order,
  },
  mutations: {
    setOrderList(state, value) {
      state.order = value;
    },
  },
  actions: {
    SET_ORDERS: ({ commit }, order) => {
      commit("setOrderList", order);
    },
  },
};
