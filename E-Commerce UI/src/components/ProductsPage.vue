<template>
  <div class="paernt">
    <div class="header">
      <!-- <MySearch class="searchBar" v-model="searchTerm"></MySearch>

      <MyButton @button-clicked="myFunction"></MyButton> -->
      <Header></Header>
    </div>
    <div class="categorydiv">
      <select name="category" @change="values" class="form-control">
        <option value="" disabled selected>select an option</option>
        <option value="all">All Categories</option>
        <option value="laptops">laptops</option>
        <option value="tv">tv</option>
        <option value="mobiles">mobiles</option>
        <option value="music">music</option>
        <option value="watches">watches</option>
      </select>
    </div>
    <ChildVue
      v-if="booleanValue"
      class="products"
      :products-list="products"
      @product-selected="productSelected"
    >
      <template>
        <div>
          <!-- <button>Add to cart</button> -->
        </div>
      </template>
    </ChildVue>
    <Footer></Footer>
  </div>
</template>

<script>
import ChildVue from "./Child.vue";
import Footer from "./AnotherFooter.vue";
// import MyButton from "./MyButton.vue";
// import MySearch from "./MySearch.vue";
import { mapActions, mapGetters } from "vuex";
import Header from "./DeleteHeader.vue";

export default {
  name: "SearchingPage",
  components: {
    ChildVue,
    // MyButton,
    // MySearch,
    Header,
    Footer,
  },
  data() {
    return {
      searchTerm: " ",
      booleanValue: true,
      products: [],
    };
  },
  watch: {
    searchTerm(newValue) {
      if (newValue.length > 2) {
        this.searchFunction();
      } else {
        this.products = JSON.parse(JSON.stringify(this.getToDoList));
      }
    },
  },
  computed: {
    ...mapGetters(["getToDoList"], ["getcategoryList"]),
  },
  created: function () {
    this.$store.dispatch("getToDoListApi", {
      success: (res) => {
        this.products = res.productsDtoList;
        console.log("From Products Page", res);
      },
    });
  },

  methods: {
    ...mapActions(["getToDoListApi"], ["getcategoryListApi"]),
    values(e) {
      console.log(e.target.value);
      // if (e.target.value == "all") {
      //   this.$store.dispatch("getToDoListApi", {
      //     success: (res) => {
      //       this.products = res;
      //       console.log("From Products Page", res);
      //     },
      //   });
      // } else {
      this.$store.dispatch("getcategoryListApi", {
        success: (res) => {
          this.products = res.productsDtoList;
          console.log("categories from demoproducts", this.products);
        },
        value: e.target.value,
      });
    },
    searchFunction() {
      if (this.searchTerm.length > 0) {
        this.products = this.getToDoList.filter((item) =>
          item.productName
            .toLowerCase()
            .includes(this.searchTerm.toLocaleLowerCase().trim())
        );
      } else {
        this.products = JSON.parse(JSON.stringify(this.getToDoList));
      }
    },
    myFunction() {
      if (this.searchTerm.length !== 0) {
        this.products = this.getToDoList.filter((item) =>
          item.productName
            .toLowerCase()
            .includes(this.searchTerm.toLocaleLowerCase().trim())
        );
      } else {
        this.booleanValue = true;
      }
    },
    productSelected(selectedProduct, msg) {
      this.$router.push(`/product/${selectedProduct.productId}`);
      console.log("from parent", selectedProduct, msg);
    },
  },
};
</script>

<style scoped>
.header {
  width: 100vw;
  display: flex;
  justify-content: space-around;
  background: black;
  height: 10vh;
}
.products {
  display: flex;
  height: 80vh;
  margin-top: 2em;
  max-width: 100vw;
  flex-wrap: wrap;
  overflow: auto;
}
.form-control {
  cursor: pointer;
}
</style>
