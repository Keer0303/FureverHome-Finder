<template>
  <div class="centered-wrapper">
    <el-card class="user-card">
      <el-form
          ref="formRef"
          :model="user"
          :rules="profileRules"
          label-width="120px"
          class="user-form"
      >
        <!-- Avatar Upload -->
        <div class="avatar-upload-container">
          <el-tooltip content="Upload Image" placement="top">
            <el-upload
                class="avatar-uploader"
                :action="$baseUrl + '/files/upload'"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <img v-if="user.avatar" :src="user.avatar" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-tooltip>
        </div>

        <!-- Profile Information -->
        <el-form-item label="Username" prop="username">
          <el-input
              v-model="user.username"
              placeholder="Username"
              disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="user.name" placeholder="Name"></el-input>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="user.phone" placeholder="Phone"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="user.email" placeholder="Email"></el-input>
        </el-form-item>

        <!-- Password Update Button -->
        <div class="button-container">
          <el-button type="primary" @click="openPasswordDialog">Change Password</el-button>
        </div>

        <!-- Save Button -->
        <div class="button-container">
          <el-button type="primary" @click="updateProfile">Save Changes</el-button>
        </div>
      </el-form>
    </el-card>

    <!-- Password Change Dialog -->
    <el-dialog
        title="Change Password"
        :visible.sync="passwordDialogVisible"
        width="400px"
    >
      <el-form
          ref="passwordFormRef"
          :model="passwordData"
          :rules="passwordRules"
          label-width="160px"
          class="password-form"
      >
        <el-form-item label="Current Password" prop="currentPassword">
          <el-input
              show-password
              v-model="passwordData.currentPassword"
              placeholder="Current Password"
          ></el-input>
        </el-form-item>
        <el-form-item label="New Password" prop="newPassword">
          <el-input
              show-password
              v-model="passwordData.newPassword"
              placeholder="New Password"
          ></el-input>
        </el-form-item>
        <el-form-item label="Confirm Password" prop="confirmPassword">
          <el-input
              show-password
              v-model="passwordData.confirmPassword"
              placeholder="Confirm Password"
          ></el-input>
        </el-form-item>
      </el-form>

      <!-- Dialog Footer -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="passwordDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updatePassword">Confirm Changes</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "UserPersonalCenter",
  data() {
    const validatePassword = (rule, value, callback) => {
      const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
      if (value === '') {
        callback(new Error('Please enter the password'));
      } else if (!passwordPattern.test(value)) {
        callback(new Error('Password must be at least 8 characters long and include both letters and numbers'));
      } else {
        callback();
      }
    };

    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      passwordData: {
        currentPassword: '',
        newPassword: '',
        confirmPassword: '',
      },
      profileRules: {
        phone: [
          { required: true, message: 'Phone number is required', trigger: 'blur' },
          { pattern: /^[0-9]{10}$/, message: 'Please enter a valid 10-digit phone number', trigger: ['blur', 'change'] },
        ],
        email: [
          { required: true, message: 'Email is required', trigger: 'blur' },
          { type: 'email', message: 'Please enter a valid email address', trigger: ['blur', 'change'] },
        ],
      },
      passwordRules: {
        currentPassword: [
          { required: true, message: 'Please enter the current password', trigger: 'blur' },
        ],
        newPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' },
        ],
      },
      passwordDialogVisible: false,
    };
  },
  methods: {
    openPasswordDialog() {
      this.passwordDialogVisible = true;
    },
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.passwordData.newPassword) {
        callback(new Error('Passwords do not match'));
      } else {
        callback();
      }
    },
    updateProfile() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put('/user/update', this.user).then((res) => {
            if (res.code === '200') {
              // Successfully updated
              this.$message.success('Profile updated successfully');

              // Update the cached user information in the browser
              localStorage.setItem('xm-user', JSON.stringify(this.user));

              // Trigger parent data update
              this.$emit('update:user');
            } else {
              this.$message.error(res.msg);
            }
          });
        } else {
          this.$message.error('Please check your input and try again.');
        }
      });
    },
    updatePassword() {
      this.$refs.passwordFormRef.validate((valid) => {
        if (valid) {
          this.$request.put('/updatePassword', this.passwordData).then((res) => {
            if (res.code === '200') {
              // Successfully updated password
              localStorage.removeItem('xm-user'); // Clear cached user information
              this.$message.success('Password changed successfully');
              this.$router.push('/login');
            } else {
              this.$message.error(res.msg);
            }
          });
        } else {
          this.$message.error('Please check your input and try again.');
        }
      });
    },
    handleAvatarSuccess(response, file, fileList) {
      // Replace the user's avatar property with the link to the uploaded image
      this.$set(this.user, 'avatar', response.data);
    },
  },
};
</script>

<style scoped>
.centered-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* Full viewport height for vertical centering */
  background-color: #f2f2f5;
}

.user-card {
  width: 50%; /* Adjust width to make the form larger */
  max-width: 600px; /* Limit the maximum width for better responsiveness */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  background-color: #fff;
}

.user-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.user-form {
  padding: 40px 60px; /* Increased padding for more spacious layout */
  background-color: #fff;
}

.avatar-upload-container {
  margin: 20px;
  text-align: center;
  transition: transform 0.3s ease;
}

.avatar-upload-container:hover {
  transform: scale(1.05);
}

.avatar-uploader .el-upload {
  border-radius: 50%;
}

.avatar-uploader .el-upload {
  border: 2px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
  transition: border-color 0.3s ease;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 32px;
  color: #8c939d;
  width: 150px; /* Increase avatar size */
  height: 150px;
  line-height: 150px;
  text-align: center;
  border-radius: 50%;
  background-color: #e9ebf0;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.avatar-uploader-icon:hover {
  background-color: #d1d5db;
  transform: rotate(45deg);
}

.avatar {
  width: 150px; /* Increase avatar size */
  height: 150px;
  display: block;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #409eff;
  transition: border-color 0.3s ease;
}

.avatar:hover {
  border-color: #66b1ff;
}

.el-form-item__label {
  font-weight: bold;
  color: #333;
}

.input-field {
  border-radius: 5px;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.input-field:focus {
  border-color: #409eff;
  box-shadow: 0 0 5px rgba(64, 158, 255, 0.5);
}

.button-container {
  text-align: center;
  margin-bottom: 20px;
}

.save-button {
  background-color: #409eff;
  border-color: #409eff;
  transition: background-color 0.3s ease, border-color 0.3s ease;
}

.save-button:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

.dialog-footer {
  text-align: right;
  margin-top: 20px;
}
</style>
