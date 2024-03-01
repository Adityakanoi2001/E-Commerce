<template>
  <div class="Personal Details">
    <h1>Personal Details</h1>
    <div class="container">
      <!-- <table border="2px">
        <tr>
          <th>FirstName</th>
          <th>LastName</th>
          <th>Email</th>
          <th>PhoneNumber</th>
          <th>City</th>
          <th>State</th>
        </tr>
        <tr>
           {{ this.userDetails.firstName }} 
           {{ this.userDetails.lastName }} 
           {{ this.userDetails.email }} 
           {{ this.userDetails.phoneNumber }} 
           {{ this.userDetails.city }} 
           {{ this.userDetails.state }} 
        </tr>
      </table> -->
      <img
        src="https://static.vecteezy.com/system/resources/previews/007/033/146/original/profile-icon-login-head-icon-vector.jpg"
        alt="user icon"
      />
      <div class="details">
        First Name :{{ this.userDetails.firstName }} <br />
        Last Name : {{ this.userDetails.lastName }} <br />
        Email :{{ this.userDetails.email }} <br />
        Phone Number:{{ this.userDetails.phoneNumber }} <br />
        City :{{ this.userDetails.city }} <br />
        State :{{ this.userDetails.state }}
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      userDetails: [],
      userId: localStorage.getItem("userId"),
    };
  },
  created: function () {
    this.$store.dispatch("getUserDetailsApi", {
      success: (res) => {
        this.userDetails = res;
        console.log("userDetails", res);
      },
      value: this.userId,
    });
  },
  computed: {
    ...mapGetters(["getUserDetails"]),
  },
  methods: {
    ...mapActions(["getUserDetailsApi"]),
  },
};
</script>
<style scoped>
.container {
  /* border: 1px solid red; */
  width: 100vw;
  height: 100vh;
  display: flex;
  /* justify-content: center; */
  /* border: 2px solid black; */
  flex-direction: column;
  align-items: center;
  /* margin-top: 15em; */
}
.container img {
  width: 15vw;
  height: 25vh;
}
.details {
  /* border: 2px solid red; */
  margin-top: 2em;
  width: 30vw;
  height: 50vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: start;
}
</style>
