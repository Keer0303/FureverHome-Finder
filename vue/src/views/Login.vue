<template>
  <div class="container">
    <div class="login-box">

      <div class="welcome-text">Welcome to Furever Home Finder</div>

      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="Please enter your username" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="Please enter your password" show-password v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.role" placeholder="Please Select your Role" class="select-role">
            <el-option label="Admin" value="ADMIN"></el-option>
            <el-option label="User" value="USER"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button class="login-button" @click="login">Login</el-button>
        </el-form-item>
        <div class="login-with-text">Or login with</div>
        <div class="social-buttons">
          <el-button @click="socialLogin('twitter')" class="social-button twitter">Twitter</el-button>
          <el-button @click="socialLogin('facebook')" class="social-button facebook">Facebook</el-button>
          <el-button @click="socialLogin('google')" class="social-button google">Google</el-button>
        </div>
        <div class="signup-text-container">
          <div class="signup-text">Don't have an account?</div>
          <a href="/register" class="signup-button">Sign up now</a>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      form: {role: 'USER',},
      rules: {
        username: [
          { required: true, message: 'Please enter your username', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // Validation passed
          this.$request.post('/login', this.form).then(res => {
            if (res.code === '200') {
              localStorage.setItem("xm-user", JSON.stringify(res.data))  // Store user data
              this.$router.push('/')  // Redirect to homepage
              this.$message.success('Login successful')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    socialLogin(platform) {
      // Handle social login
      let oauthUrl;
      switch(platform) {
        case 'twitter':
          oauthUrl = 'https://api.twitter.com/oauth/authenticate';
          break;
        case 'facebook':
          oauthUrl = 'https://www.facebook.com/v9.0/dialog/oauth';
          break;
        case 'google':
          oauthUrl = 'https://accounts.google.com/o/oauth2/auth';
          break;
        default:
          return;
      }
      window.location.href = oauthUrl;
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}

.login-box {
  width: 500px;
  padding: 50px;
  background-color: white;
  border-radius: 5px;
  text-align: center;
}

.welcome-text {
  font-size: 26px;
  white-space: nowrap;
  color: #333;
  margin-bottom: 20px;
}

.select-role {
  width: 100%;
}

.login-button {
  width: 100%;
  background-color: #555;
  border-color: #555;
  color: white;
  border-radius: 50px;
  font-weight: bold;
  font-size: 16px;
  padding: 10px 20px;
  transition: background-color 0.3s, border-color 0.3s;
}

.login-button:hover {
  background-color: #333;
  border-color: #333;
}

.login-with-text {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
  font-size: 16px;
  font-weight: bold;
}

.social-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.social-button {
  flex: 1;
  color: white;
  border-radius: 50px;
  font-weight: bold;
  font-size: 16px;
  margin: 5px;
  padding: 10px;
}

.social-button.twitter {
  background-color: #1DA1F2;
}

.social-button.facebook {
  background-color: #3b5998;
}

.social-button.google {
  background-color: #4285F4;
}

.signup-text-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  margin-top: 20px;
}

.signup-text {
  margin-bottom: 10px;
  font-weight: bold;
  font-size: 18px;
}

.signup-button {
  display: inline-block;
  padding: 10px 20px;
  background-color: #555;
  color: white;
  border-radius: 50px;
  text-align: center;
  text-decoration: none;
  font-weight: bold;
  width: 100%;
  max-width: 200px;
  font-size: 16px;
  transition: background-color 0.3s;
}

.signup-button:hover {
  background-color: #333;
}
</style>
