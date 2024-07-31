<template>
  <div class="container">
    <div class="register-box">
      <div class="welcome-text">Welcome to Register</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="Please enter your username" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="Please enter your password" show-password v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPass">
          <el-input prefix-icon="el-icon-lock" placeholder="Please confirm your password" show-password v-model="form.confirmPass"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="register-button" @click="register">Register</el-button>
        </el-form-item>
        <div class="login-text-container">
          <div class="login-text">Already have an account?</div>
          <a href="/login" class="login-button">Login</a>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // Password validation
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('Please confirm your password'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('Passwords do not match'))
      } else {
        callback()
      }
    }
    return {
      form: { role: 'USER' },
      rules: {
        username: [
          { required: true, message: 'Please enter your username', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // Validation passed
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/')  // Redirect to login page
              this.$message.success('Registration successful')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg1.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}

.register-box {
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

.register-button {
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

.register-button:hover {
  background-color: #333;
  border-color: #333;
}

.login-text-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  margin-top: 20px;
}

.login-text {
  margin-bottom: 10px;
  font-weight: bold;
  font-size: 18px;
}

.login-button {
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

.login-button:hover {
  background-color: #333;
}

a {
  color: #2a60c9;
}
</style>
