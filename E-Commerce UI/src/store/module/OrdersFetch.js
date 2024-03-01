//import axios from "axios";
export default {
  state: {
    OrdersList: [],
  },
  getters: {
    getOrdersList: (state) => state.OrdersList,
  },
  mutations: {
    setOrdersList: (state, value) => (state.OrdersList = value),
  },
  actions: {
    getOrdersListApi: ({ commit }, { success, value }) => {
      // eslint-disable-next-line no-debugger
      fetch(`/api/orderDetails/getAllOrderDetails/${value}`)
        .then((resp) => resp.json())
        .then((res) => {
          commit("setOrdersList", res);
          success && success(res);
        });
    },
  },
};
