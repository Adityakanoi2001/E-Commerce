<template>
  <div>
    <Header></Header>
    <div v-if="booleanValue">
      <h1>Search Results are for {{ this.searchTerm }} are</h1>
      <ChildVue
        class="products"
        :products-list="SearchResults"
        @product-selected="productSelected"
      >
        <template>
          <div>
            <!-- <button>Add to cart</button> -->
          </div>
        </template>
      </ChildVue>
    </div>
    <div v-else>
      <h1>No Search Results Found for the search term {{ this.searchTerm }}</h1>
      <button class="btn btn-primary" @click="products">
        Click Here to goto products page
      </button>
    </div>
    <!-- <Footer></Footer> -->
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import ChildVue from "./Child.vue";
import Header from "./DeleteHeader.vue";

export default {
  data() {
    return {
      SearchResults: [],
      booleanValue: true,
      searchTerm: this.$route.params.searchTerm,
      demo: [],
    };
  },
  components: {
    Header,
    ChildVue,
  },
  computed: {
    ...mapGetters(["getSearchResults"]),
  },
  created: function () {
    this.$store.dispatch("getSearchResultsApi", {
      success: (res) => {
        this.SearchResults = res.productsEntities;
        if (this.SearchResults.length === 0) {
          this.booleanValue = false;
        }
        console.log("Search Results", res);
      },
      searchTerm: this.searchTerm,
    });
  },
  methods: {
    ...mapActions(["getSearchResultsApi"]),
    productSelected(selectedProduct, msg) {
      this.$router.push(`/product/${selectedProduct.productId}`);
      console.log("from parent", selectedProduct, msg);
    },
    products() {
      this.$router.push("/products");
    },
  },
};
</script>
<style scoped></style>
