//import axios from "axios";
export default {
  state: {
    SearchResults: [],
  },
  getters: {
    getSearchResults: (state) => state.SearchResults,
  },
  mutations: {
    setSearchResults: (state, value) => (state.SearchResults = value),
  },
  actions: {
    getSearchResultsApi: ({ commit }, { success, searchTerm }) => {
      fetch(`/api/products/search/${searchTerm}`)
        .then((resp) => resp.json())
        .then((res) => {
          commit("setSearchResults", res);
          success && success(res);
        });
    },
  },
};
