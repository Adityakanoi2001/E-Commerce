export default {
  state: {
    user: [],
  },
  getters: {
    getUserList: (state) => state.user,
  },
  mutations: {
    setUserList(state, value) {
      state.user = value;
    },
  },
  actions: {
    SET_USER_DATA: ({ commit }, user) => {
      commit("setUserList", user);
    },
  },
};
