export default {
  state: {
    toDoList: [],
    categoryList: [],
  },
  getters: {
    getToDoList: (state) => state.toDoList,
    getcategoryList: (state) => state.categoryList,
  },
  mutations: {
    setToDoList: (state, value) => {
      state.toDoList = value;
    },
    setcategoryList: (state, value) => {
      state.categoryList = value;
    },
  },
  actions: {
    getToDoListApi: ({ commit }, { success }) => {
      fetch("/api/products/productList")
        .then((response) => response.json())
        .then((res) => {
          commit("setToDoList", res);
          success && success(res);
        });
    },
    getcategoryListApi: ({ commit }, { success, value }) => {
      if (value === "all") {
        fetch("/api/products/productList")
          .then((response) => response.json())
          .then((res) => {
            commit("setToDoList", res);
            success && success(res);
          });
      } else {
        fetch(`/api/products/getByCategory/${value}`)
          .then((response) => response.json())
          .then((res) => {
            commit("setcategoryList", res);
            success && success(res);
          });
      }
    },
  },
};
