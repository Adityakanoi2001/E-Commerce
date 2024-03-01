//import axios from "axios";
export default {
  state: {
    userDetails: [],
  },
  getters: {
    getUserDetails: (state) => state.userDetails,
  },
  mutations: {
    setUserDetails: (state, value) => (state.userDetails = value),
  },
  actions: {
    getUserDetailsApi: ({ commit }, { success, value }) => {
      fetch(`/api/user/getDetails/${value}`)
        .then((resp) => resp.json())
        .then((res) => {
          commit("setUserDetails", res);
          success && success(res);
        });
    },
  },
};
