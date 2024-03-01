export default {
  state: {
    cart: [],
  },
  getters: {
    getCartList: (state) => state.cart,
  },
  mutations: {
    setCartList(state, value) {
      state.cart = value;
    },
  },
  actions: {
    SET_CART_LIST: ({ commit }, cart) => {
      commit("setCartList", cart);
    },
  },
};
