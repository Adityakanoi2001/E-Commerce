<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Exclusive Glosses</a>
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <form class="form-inline my-2 my-lg-0">
      <input
        class="form-control mr-sm-2 searchbar"
        type="search"
        placeholder="Search"
        aria-label="Search"
        v-model="searchText"
      />
      <button
        @click="searchFunction"
        class="btn btn-outline-success my-2 my-sm-0"
        type="submit"
      >
        Search
      </button>
    </form>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <router-link to="/"><a class="nav-link">Home</a></router-link>
        </li>

        <li class="nav-item">
          <router-link to="/products">
            <a class="nav-link">Products</a>
          </router-link>
          <!-- <a class="nav-link" href="#">Link</a> -->
        </li>

        <li class="nav-item">
          <router-link to="/mlogin">
            <a class="nav-link">Merchant</a>
          </router-link>
        </li>

        <li class="nav-item" v-if="!this.isLogin">
          <router-link to="/userlogin">
            <a class="nav-link"><i class="fa fa-user fa-lg"></i></a>
          </router-link>
        </li>

        <li class="nav-item" v-if="this.isLogin">
          <router-link to="/cartview">
            <a class="nav-link"><i class="fa fa-shopping-cart fa-lg"></i></a>
          </router-link>
          <!-- <a class="nav-link" href="#">Link</a> -->
        </li>

        <li class="nav-item" v-if="this.isLogin">
          <router-link to="/userdetails">
            <a class="nav-link">Profile</a>
          </router-link>
          <!-- <a class="nav-link" href="#">Link</a> -->
        </li>

        <li class="nav-item" v-if="this.isLogin">
          <router-link to="/signout">
            <a class="nav-link">Sign Out</a>
          </router-link>
          <!-- <a class="nav-link" href="#">Link</a> -->
        </li>
        <!-- <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            Dropdown
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdown">
            <a class="dropdown-item" href="#">Action</a>
            <a class="dropdown-item" href="#">Another action</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Something else here</a>
          </div>
        </li>
        <li class="nav-item">
          <a
            class="nav-link disabled"
            href="#"
            tabindex="-1"
            aria-disabled="true"
            >Disabled</a
          >
        </li> -->
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      searchText: "",
      isLogin: localStorage.getItem("loginStatus"),
    };
  },
  methods: {
    searchFunction() {
      localStorage.setItem("searchTerm", this.searchText),
        console.log(
          "Setting the local data",
          localStorage.getItem("searchTerm")
        );
      this.$router.push(`/searchResults/${this.searchText}`);
    },
  },
};
</script>
<style scoped>
.fa {
  color: white;
}
nav {
  position: sticky;
  top: 0;
}
.nav-link:hover {
  border-bottom: 1px solid red;
}
a {
  text-decoration: none;
}
.form-inline {
  display: flex;
  /* border: 2px solid red; */
  justify-content: space-around;
  width: 55vw;
}
.searchbar {
  border-radius: 10px 100px / 120px;
}
.form-control {
  width: 45vw;
}
</style>
