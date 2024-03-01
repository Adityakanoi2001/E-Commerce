import Vue from "vue";
import VueRouter from "vue-router";
// import HomeView from "../views/HomeView.vue";
import HeaderView from "../views/HeaderView.vue";
import CartPage from "../views/CartPage.vue";
import Mlogin from "../components/MerchantLoginForm.vue";
import UserLogin from "../components/UserLoginForm.vue";
import ProductDescription from "../views/ProductDescription.vue";
import SearchingPage from "@/components/ProductsPage.vue";
import SignUp from "../components/UserSignUp.vue";
import MSignUp from "../components/MerchantRegForm.vue";
import Category from "../components/FilterByCategory.vue";
import MyOrders from "../views/MyOrders.vue";
import PersonalDetails from "../views/PersonalDetails.vue";
import UserDetails from "../views/UserDetails.vue";
import OrderSuccessful from "../views/OrderSuccessful.vue";
import SignOut from "../views/SignOut.vue";
import loadingPage from "../views/LoadingPage.vue";
import SearcResults from "../components/SearchResults.vue";
import SearchLoading from "../views/SearchLoading.vue";
import FinalCheckout from "../components/FinalCheckout.vue";
import PaymentDetails from "../components/PaymentDetails.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HeaderView,
    childern: [
      {
        path: "mlogin",
        name: "merchantlogin",
        component: Mlogin,
      },
      {
        path: "userlogin",
        name: "userlogin",
        component: UserLogin,
      },
    ],
  },
  {
    path: "/cartview",
    name: "cartview",
    component: CartPage,
  },
  {
    path: "/productCategory",
    name: "productCategory",
    component: Category,
  },
  {
    path: "/mlogin",
    name: "merchantlogin",
    component: Mlogin,
  },
  {
    path: "/userlogin",
    name: "userlogin",
    component: UserLogin,
  },
  {
    path: "/usersignup",
    name: "usersignup",
    component: SignUp,
  },
  {
    path: "/merchantsignup",
    name: "merchantsignup",
    component: MSignUp,
  },
  {
    path: "/ordersuccess",
    name: "ordersuccess",
    component: OrderSuccessful,
  },
  {
    path: "/signout",
    name: "signout",
    component: SignOut,
  },
  {
    path: "/loading",
    name: "loadingPage",
    component: loadingPage,
  },
  {
    path: "/checkoutfinal",
    name: "checkoutfinal",
    component: FinalCheckout,
  },
  {
    path: "/searchloading",
    name: "searchloadingPage",
    component: SearchLoading,
  },
  {
    path: "/payment",
    name: "payment",
    component: PaymentDetails,
  },
  {
    path: "/searchResults/:searchTerm",
    name: "searchResults",
    component: SearcResults,
  },

  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
  {
    path: "/userdetails",
    name: "userdetails",
    component: UserDetails,
    children: [
      {
        path: "myorders",
        name: "orders",
        component: MyOrders,
      },
      {
        path: "personaldetails",
        name: "PersonalDetails",
        component: PersonalDetails,
      },
    ],
  },
  {
    path: "/products",
    name: "productsPage",
    component: SearchingPage,
  },
  {
    path: "/product/:productId",
    name: "singleProductPage",
    component: ProductDescription,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
// hhh
export default router;
