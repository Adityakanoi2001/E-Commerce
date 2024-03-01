<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <header id="header">
      <p
        id="headertext"
        style="margin-left: 0px; font-size: 20px; color: white"
      >
        Exclusive glosses
      </p>
      <input id="input" v-model="searchText" />
      <button id="search">Search</button>
      <router-link to="/mlogin">
        <button id="search">Merchant</button> </router-link
      >|
      <router-link to="/userlogin">
        <button id="search">Login</button></router-link
      >
      <router-link to="/cartview">
        <button id="search">cart</button></router-link
      >
    </header>
  </div>
</template>
<script>
export default {
  data() {
    return { searchtext: "" };
  },
};
</script>
<style scoped>
#header {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: black;
  height: 8vh;
  position: fixed;
  top: 0;
  width: 100vw;
  opacity: 0.8;
}
#input {
  width: 50vw;
  border-radius: 10px 100px / 120px;
  height: 5vh;
  margin-right: 40px;
}
#search {
  width: 10vw;
  border-radius: 30px;
  height: 5vh;
  margin-right: 100px;
  cursor: pointer;
}
</style>
