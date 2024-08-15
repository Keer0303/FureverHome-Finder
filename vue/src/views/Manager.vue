<template>
  <div class="manager-container">
    <!--  Header  -->
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" style="border-radius: 50%" />
        <div class="title">
          <span v-if="user.role === 'ADMIN'">Admin Management System</span>
          <span v-else>Furever Home Finder</span>
        </div>
      </div>

      <div class="manager-header-center">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <div class="manager-header-right">
        <el-dropdown placement="bottom">
          <div class="avatar">
            <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
            <div>{{ user.name ||  'Admin' }}</div>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="goToPerson">Personal Center</el-dropdown-item>
<!--            <el-dropdown-item @click.native="$router.push('/password')">Change Password</el-dropdown-item>-->
            <el-dropdown-item @click.native="logout">Logout</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--  Main  -->
    <div class="manager-main">
      <!--  Sidebar  -->
      <div class="manager-main-left">
        <el-menu :default-openeds="['info', 'user']" router style="border: none" :default-active="$route.path">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">Home</span>
          </el-menu-item>
          <el-submenu index="info">
            <template slot="title">
              <i class="el-icon-menu"></i><span>Information</span>
            </template>
<!--            <el-menu-item index="/notice " v-if = "user.role === 'ADMIN'" >Notice Board</el-menu-item>-->
            <el-menu-item index="/animal"  >Animal Info</el-menu-item>
            <el-menu-item index="/room">Paws on Pause </el-menu-item>
            <el-menu-item index="/adopt">Adopt Record</el-menu-item>
            <el-menu-item index="/applications" >Applications </el-menu-item>
            <el-menu-item index="/submit">Stray Animal Report</el-menu-item>
          </el-submenu>

          <el-submenu index="user" v-if = "user.role === 'ADMIN'">
            <template slot="title">
              <i class="el-icon-menu"></i><span>User</span>
            </template>
            <el-menu-item index="/admin">Admin Info</el-menu-item>
            <el-menu-item index="/user">User Info</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!--  Data Table  -->
      <div class="manager-main-right">
        <router-view @update:user="updateUser" />
      </div>
    </div>

  </div>
</template>

<script>
import User from "@/views/manager/User.vue";

export default {
  name: "Manager",
  computed: {
    User() {
      return User
    }
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
    }
  },
  created() {
    if (!this.user.id) {
      this.$router.push('/login')
    }
  },
  methods: {
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // Re-fetch the latest user info
    },
    goToPerson() {
      if (this.user.role === 'ADMIN') {
        this.$router.push('/adminPerson')
      }
      if (this.user.role === 'USER') {
        this.$router.push('/userPerson')
      }
    },
    logout() {
      localStorage.removeItem('xm-user')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
@import "@/assets/css/manager.css";
</style>
