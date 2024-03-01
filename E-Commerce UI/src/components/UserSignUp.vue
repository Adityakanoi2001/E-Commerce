<template>
  <div>
    <HeaderVue></HeaderVue>
    <!-- <h1 style="margin-top: 30px">Merchant Login Form</h1> -->
    <div class="container">
      <div class="screen">
        <div class="screen__content">
          <h1
            style="
              background-color: white;
              font-family: 'Times New Roman', Times, serif;
            "
          >
            User SignUp Form
          </h1>
          <form class="login" @submit.prevent="saveData">
            <div class="login__field">
              <input
                type="text"
                class="login__input"
                placeholder="Enter First Name"
                v-model="user.firstName"
              />
            </div>
            <div class="login__field">
              <input
                type="text"
                class="login__input"
                placeholder="Enter Last Name"
                v-model="user.lastName"
              />
            </div>
            <div class="login__field">
              <input
                type="email"
                class="login__input"
                placeholder="Enter Your Email"
                v-model="user.userEmail"
              />
            </div>
            <div class="login__field">
              <input
                type="password"
                class="login__input"
                placeholder="Password"
                v-model="user.userPassword"
              />
            </div>
            <div class="login__field">
              <input
                type="text"
                class="login__input"
                placeholder="Enter Your City"
                v-model="user.city"
              />
            </div>
            <div class="login__field">
              <input
                type="text"
                class="login__input"
                placeholder="Enter Your State"
                v-model="user.state"
              />
            </div>
            <div class="login__field">
              <input
                type="number"
                class="login__input"
                placeholder="Enter Your Phone Number"
                v-model="user.phoneNumber"
              />
            </div>
            <button type="submit" class="button login__submit">
              <span class="button__text">Submit</span>
            </button>
          </form>
          <!-- <div class="social-login">
            <div class="social-icons">
              <a href="#" class="social-login__icon fab fa-instagram"></a>
              <a href="#" class="social-login__icon fab fa-facebook"></a>
              <a href="#" class="social-login__icon fab fa-twitter"></a>
            </div>
          </div> -->
        </div>
        <div class="screen__background">
          <span
            class="screen__background__shape screen__background__shape4"
          ></span>
          <span
            class="screen__background__shape screen__background__shape3"
          ></span>
          <span
            class="screen__background__shape screen__background__shape2"
          ></span>
          <span
            class="screen__background__shape screen__background__shape1"
          ></span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import firebase from "firebase";
// export default {
//   data() {
//     return {
//       email: "",
//       password: "",
//     };
//   },
//   methods: {
//     firebase.auth().signInWithEmailAndPassword(this.email, this.password).then(user => console.log(user.data),
//     err=>alert(err)
//     )}
// };
import axios from "axios";
import { mapActions, mapGetters } from "vuex";
import firebase from "firebase/compat/app";
import "firebase/compat/auth";
import HeaderVue from "./DeleteHeader.vue";
// import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";

export default {
  name: "UserSignUp",
  components: {
    HeaderVue,
  },
  data() {
    return {
      result: {},
      user: {
        firstName: "",
        lastName: "",
        userEmail: "",
        userPassword: "",
        city: "",
        state: "",
        phoneNumber: "",
      },
    };
  },
  computed: {
    ...mapGetters(["getUserList"]),
  },
  created() {},
  mounted() {
    console.log("mounted() called.......");
  },
  methods: {
    ...mapActions(["SET_USER_DATA"]),
    // register: function () {
    //   firebase
    //     .auth()
    //     .signInWithEmailAndPassword(this.user.userEmail, this.user.userPassword)
    //     .then(
    //       (user) => {
    //         console.log(user.data);
    //       },
    //       (err) => {
    //         alert(err);
    //       }
    //     );
    // },
    // saveData() {
    //   const payload = {
    //     firstName: this.user.firstName,
    //     lastName: this.user.lastName,
    //     email: this.user.userEmail,
    //     password: this.user.userPassword,
    //     city: this.user.city,
    //     state: this.user.state,
    //     phoneNumber: this.user.phoneNumber,
    //   };
    //   axios
    //     .post("/api/user/signup", payload)
    //     .then((response) => {
    //       console.log(response);
    //       this.SET_USER_DATA(response.data);
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // axios.post("/api/user/signup", this.user).then((data) => {
    //   alert("saveddddd");
    // });
    async saveData() {
      try {
        await firebase
          .auth()
          .createUserWithEmailAndPassword(
            this.user.userEmail,
            this.user.userPassword
          );
        firebase.auth().onAuthStateChanged((user) => {
          if (user) {
            // this.id = user.uid;
            // console.log("user id", this.id);
            const requestBody = {
              firstName: this.user.firstName,
              lastName: this.user.lastName,
              email: this.user.userEmail,
              password: this.user.userPassword,
              city: this.user.city,
              state: this.user.state,
              phoneNumber: this.user.phoneNumber,
            };
            axios
              .post("/api/user/signup", requestBody)
              .then((response) => {
                console.log(response);
                this.SET_USER_DATA(response.data);
              })
              .catch((error) => {
                console.log(error);
              });
          }
        });
        //console.log(user);
        alert("Registered New User");
        this.$router.push("/userlogin");
      } catch (err) {
        console.log(err);
        alert(err);
      }
    },
  },
};
</script>
<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  font-family: Raleway, sans-serif;
}

body {
  background: linear-gradient(90deg, #c7c5f4, #776bcc);
}

.container {
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}
.login {
  /* border: 2px solid red; */
  /* margin-left: 1.5em; */
}
.screen {
  background: linear-gradient(90deg, #5d54a4, #7c78b8);
  position: relative;
  height: 600px;
  width: 360px;
  box-shadow: 0px 0px 24px #5c5696;
}

.screen__content {
  z-index: 1;
  position: relative;
  height: 100%;
}

.screen__background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
  -webkit-clip-path: inset(0 0 0 0);
  clip-path: inset(0 0 0 0);
}

.screen__background__shape {
  transform: rotate(45deg);
  position: absolute;
}

.screen__background__shape1 {
  height: 520px;
  width: 520px;
  background: #fff;
  top: -50px;
  right: 120px;
  border-radius: 0 72px 0 0;
}

.screen__background__shape2 {
  height: 220px;
  width: 220px;
  background: #6c63ac;
  top: -172px;
  right: 0;
  border-radius: 32px;
}

.screen__background__shape3 {
  height: 540px;
  width: 190px;
  background: linear-gradient(270deg, #5d54a4, #6a679e);
  top: -24px;
  right: 0;
  border-radius: 32px;
}

.screen__background__shape4 {
  height: 400px;
  width: 200px;
  background: #7e7bb9;
  top: 420px;
  right: 50px;
  border-radius: 60px;
}

.login {
  width: 320px;
  padding: 30px;
}

.login__field {
  padding: 15px 0px;
  position: relative;
}

.login__icon {
  position: absolute;
  top: 30px;
  color: #7875b5;
}

.login__input {
  border: none;
  border-bottom: 2px solid #d1d1d4;
  background: none;
  font-weight: 700;
  width: 75%;
  transition: 0.2s;
}

.login__input:active,
.login__input:focus,
.login__input:hover {
  outline: none;
  border-bottom-color: #6a679e;
}

.login__submit {
  background: #fff;
  font-size: 14px;
  margin-top: 30px;
  padding: 16px 20px;
  border-radius: 26px;
  border: 1px solid #d4d3e8;
  text-transform: uppercase;
  font-weight: 700;
  display: flex;
  align-items: center;
  width: 100%;
  color: #4c489d;
  box-shadow: 0px 2px 2px #5c5696;
  cursor: pointer;
  transition: 0.2s;
}

.login__submit:active,
.login__submit:focus,
.login__submit:hover {
  border-color: #6a679e;
  outline: none;
}

.button__icon {
  font-size: 24px;
  margin-left: auto;
  color: #7875b5;
}

.social-login {
  position: absolute;
  height: 140px;
  width: 160px;
  text-align: center;
  bottom: 0px;
  right: 0px;
  color: #fff;
}

.social-icons {
  display: flex;
  align-items: center;
  justify-content: center;
}

.social-login__icon {
  padding: 20px 10px;
  color: #fff;
  text-decoration: none;
  text-shadow: 0px 0px 8px #7875b5;
}

.social-login__icon:hover {
  transform: scale(1.5);
}
</style>
