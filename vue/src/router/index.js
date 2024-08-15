import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// Solve the problem of frequent menu errors in the navigation bar or bottom navigation tabBar in vue-router version 3.0 and above.
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // Redirect to the homepage
    children: [
      { path: '403', name: 'NoAuth', meta: { name: 'No Authorization' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: 'System Homepage' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: 'Admin Information' }, component: () => import('../views/manager/Admin') },
      { path: 'user', name: 'User', meta: { name: 'User Information' }, component: () => import('../views/manager/User') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: 'Personal Information' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'userPerson', name: 'UserPerson', meta: { name: 'Personal Information' }, component: () => import('../views/manager/UserPerson') },
      // { path: 'password', name: 'Password', meta: { name: 'Change Password' }, component: () => import('../views/manager/Password') },
      // { path: 'notice', name: 'Notice', meta: { name: 'Notice Board' }, component: () => import('../views/manager/Notice.vue') },
      { path: 'animal', name: 'Animal', meta: { name: 'Animal Information' }, component: () => import('../views/manager/Animal') },
      { path: 'adopt', name: 'Adopt', meta: { name: 'Adopt record' }, component: () => import('../views/manager/Adopt') },
      { path: 'room', name: 'Room', meta: { name: 'Paws on Pause' }, component: () => import('../views/manager/Room') },
      { path: 'applications', name: 'Applications', meta: { name: 'Adoption Applications' }, component: () => import('../views/manager/Applications.vue') },
      { path: 'submit', name: 'Submit', meta: { userTitle: 'Report Stray Animal', adminTitle: 'Reports' }, component: () => import('../views/manager/Submit.vue') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: 'System Homepage' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', meta: { name: 'Personal Information' }, component: () => import('../views/front/Person') },

    ]
  },
  { path: '/privacy', name: 'Privacy', meta: { name: 'Privacy Policy' }, component: () => import('../views/Privacy.vue') },
  { path: '/login', name: 'Login', meta: { name: 'Login' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: 'Register' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: 'Page Not Found' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// Router guard
router.beforeEach((to, from, next) => {
  let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
  console.log('to.meta:', to.meta);
  // Set dynamic title based on user role
  if (to.meta) {
    if (user.role === 'ADMIN') {
      document.title = to.meta.adminTitle || to.meta.name;
    } else {
      document.title = to.meta.userTitle || to.meta.name;
    }
  }

  if (to.path === '/') {
    if (user.role) {
      if (user.role === 'USER') {
        next('/front/home')
      } else {
        next('/home')
      }
    } else {
      next('/login')
    }
  } else {
    next()
  }
})

export default router
