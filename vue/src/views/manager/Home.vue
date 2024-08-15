<template>
  <div>
    <!-- Data Protection Regulation Dialog -->
    <el-dialog
        :visible.sync="dataProtectionDialogVisible"
        title="Data Protection & Privacy Notice"
        width="60%"
        :before-close="handleDataProtectionClose"
    >
      <div class="data-protection-content">
        <h3>Welcome to Furever Home Finder!</h3>
        <p>
          At Furever Home Finder, we are committed to safeguarding your privacy
          and ensuring the security of your personal data. Our website adheres
          to the highest standards of data protection and complies with the
          General Data Protection Regulation (GDPR).
        </p>

        <div class="data-protection-details">
          <h4>What We Collect</h4>
          <ul>
            <li>
              Personal identification information (Name, email address, etc.)
            </li>
            <li>Contact information (Phone number, address)</li>
            <li>Usage data and website interactions</li>
          </ul>

          <h4>Why We Collect</h4>
          <ul>
            <li>To provide personalized user experiences</li>
            <li>To improve our services and website functionality</li>
            <li>To process adoption applications efficiently</li>
          </ul>

          <h4>Your Rights</h4>
          <ul>
            <li>Right to access your personal data</li>
            <li>Right to rectify incorrect data</li>
            <li>Right to erasure ('right to be forgotten')</li>
            <li>Right to restrict processing of your data</li>
            <li>Right to data portability</li>
            <li>Right to object to data processing</li>
          </ul>

          <p>
            By clicking "Agree," you consent to our use of your data in
            accordance with our
            <router-link to="/Privacy">Privacy Policy</router-link>. Your
            information is stored securely and will not be shared with third
            parties without your explicit consent.
          </p>

          <p>
            For more information on how we protect your data, please visit our
            <router-link to="/Privacy">Privacy Policy</router-link> page.
          </p>
        </div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="declineDataProtection">Decline</el-button>
        <el-button type="primary" @click="acceptDataProtection">Agree</el-button>
      </div>
    </el-dialog>

    <div class="card header-card" v-if="!dataProtectionDialogVisible">
      Hello, {{ user?.name }}, Welcome to Furever Home Finder!
      <!-- <el-button type="primary" @click="showNotices" class="notice-button">Notice</el-button> -->
    </div>

    <div class="content" v-if="!dataProtectionDialogVisible">
      <div class="card pet-info-card">
        <!-- Pet Info -->
        <div class="pet-info-title">Adopt love, one paw at a time.</div>
        <el-row>
          <el-col :span="6" v-for="item in filteredAnimalData" :key="item.id">
            <div class="card pet-card">
              <img :src="item.img" alt="" class="pet-image" />
              <div class="pet-name">
                {{ item.name }} <br />（{{ item.species }}）
              </div>
              <div class="pet-details">
                Sex：{{ item.sex }}， Age：{{ item.age }}
              </div>
              <div class="pet-status">
                Status：<span class="status-text">{{ item.status }}</span>
              </div>
              <div class="about-me">
                About me：<br /><span class="about-me-text">{{ item.descr }}</span>
              </div>
              <div>
                <el-button
                    type="success"
                    size="mini"
                    class="adopt-button"
                    :disabled="
                    item.status === 'Adopted' ||
                    item.status === 'In Process' ||
                    user.role === 'ADMIN'
                  "
                    @click="openAdoptionForm(item)"
                >Apply to Adopt</el-button
                >
                <el-button
                    type="primary"
                    size="mini"
                    class="details-button"
                    @click="showDetails(item)"
                >View Details</el-button
                >
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 申请领养表单对话框 -->
    <el-dialog
        :visible.sync="adoptionFormVisible"
        width="40%"
        title="Adoption Application"
    >
      <el-form :model="adoptionForm" ref="adoptionForm">
        <el-form-item label="Reason" :label-width="formLabelWidth">
          <el-input
              type="textarea"
              :rows="4"
              v-model="adoptionForm.reason"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="adoptionFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitAdoptionForm">Submit</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose"
    >
      <span slot="title">{{ selectedAnimal.name }}'s Details</span>
      <div>
        <img :src="selectedAnimal.img" alt="" class="details-image" />
        <div class="details-name">{{ selectedAnimal.name }}</div>
        <div class="details-info">Species: {{ selectedAnimal.species }}</div>
        <div class="details-info">Sex: {{ selectedAnimal.sex }}</div>
        <div class="details-info">Age: {{ selectedAnimal.age }}</div>
        <div class="details-info">
          Status:
          <span
              :style="{
              color: selectedAnimal.status === 'Adopted' ? '#7d3d0c' : '#000',
            }"
          >{{ selectedAnimal.status }}</span
          >
        </div>
        <div class="details-about">
          About me: {{ selectedAnimal.descr }}
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("xm-user") || "{}"),
      animalData: [],
      dialogVisible: false,
      adoptionFormVisible: false,
      selectedAnimal: {},
      adoptionForm: {
        reason: "",
      },
      formLabelWidth: "120px",
      dataProtectionDialogVisible: true, // Initially show the data protection dialog
    };
  },
  computed: {
    filteredAnimalData() {
      return this.animalData.filter((item) => item.status !== "Adopted");
    },
  },
  created() {
    this.checkDataProtectionConsent();
    this.loadAnimal();
  },
  methods: {
    // Check if the user has already consented to data protection
    checkDataProtectionConsent() {
      const consentGiven = localStorage.getItem("dataProtectionConsent");
      if (consentGiven) {
        this.dataProtectionDialogVisible = false;
      }
    },

    loadAnimal() {
      this.$request
          .get("/animal/selectAll")
          .then((res) => {
            if (res.code === "200") {
              this.animalData = res.data;
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch((error) => {
            console.error("Error loading animal data:", error);
            this.$message.error(
                "An error occurred while loading animal data"
            );
          });
    },
    openAdoptionForm(animal) {
      this.selectedAnimal = animal;
      this.adoptionFormVisible = true;
    },
    submitAdoptionForm() {
      if (!this.adoptionForm.reason) {
        this.$message.error("Please fill in the reason for adoption");
        return;
      }

      let applicationData = {
        userId: this.user.id,
        animalId: this.selectedAnimal.id,
        name: this.user.name,
        email: this.user.email,
        phone: this.user.phone,
        reason: this.adoptionForm.reason,
        status: "Pending",
      };

      this.$request
          .post("/application/add", applicationData)
          .then((res) => {
            if (res.code === "200") {
              this.$message.success("Adoption request sent");
              this.adoptionFormVisible = false;
              this.resetAdoptionForm();
              this.loadAnimal(); // Reload animal data to reflect the status change
            } else {
              this.$message.error(
                  "Unable to submit the application. Please complete your email address."
              );
            }
          })
          .catch((error) => {
            console.error(
                "Error sending adoption request:",
                error.response || error.message || error
            );
            this.$message.error(
                "An error occurred while sending adoption request"
            );
          });
    },
    resetAdoptionForm() {
      this.adoptionForm = {
        reason: "",
      };
    },
    showDetails(animal) {
      this.selectedAnimal = animal;
      this.dialogVisible = true;
    },
    handleClose(done) {
      this.dialogVisible = false;
      done();
    },
    acceptDataProtection() {
      this.dataProtectionDialogVisible = false;
      localStorage.setItem("dataProtectionConsent", "true"); // Store consent state
    },
    declineDataProtection() {
      this.$message.error(
          "You must agree to the data protection regulation to use the website."
      );
      this.logoutUser(); // Call logout method
    },
    handleDataProtectionClose(done) {
      this.declineDataProtection();
      done();
    },
    logoutUser() {
      // Clear user data and navigate to Login page
      localStorage.removeItem("xm-user"); // Remove user data from localStorage
      localStorage.removeItem("dataProtectionConsent"); // Remove consent
      this.$router.push({ name: "Login" }); // Redirect to Login.vue
    },
  },
};
</script>

<style scoped>
.el-col-6 {
  padding: 10px;
}

.container {
  display: flex;
  flex-direction: row;
}

.header-card {
  padding: 15px;
  font-size: 24px;
  font-weight: bold;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.content {
  display: flex;
  flex-direction: row;
  width: 100%;
}

.pet-info-card {
  width: 100%;
  padding: 15px;
}

.pet-info-title {
  margin-bottom: 20px;
  font-size: 20px;
  font-weight: bold;
  color: #1f618d;
}

.pet-card {
  background-color: #f8f4e8;
  text-align: center;
}

.pet-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
}

.pet-name {
  font-weight: bold;
  color: #817a70;
  margin-top: 5px;
}

.pet-details,
.pet-status {
  color: #575353;
  font-size: 13px;
  margin-top: 5px;
}

.pet-status {
  color: #7d3d0c;
}

.status-text {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}

.about-me {
  margin-top: 15px;
  color: #4b4949;
  text-align: left;
}

.about-me-text {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
}

.adopt-button,
.details-button {
  margin-top: 20px;
}

.details-image {
  width: 300px;
  height: 300px;
  display: block;
  margin: 0 auto;
  object-fit: contain;
}

.details-name {
  text-align: center;
  margin-top: 10px;
  font-weight: bold;
  font-size: 18px;
}

.details-info,
.details-about {
  margin-top: 10px;
}

.data-protection-content {
  font-size: 16px;
  line-height: 1.5;
  color: #333;
}

.data-protection-content h3 {
  color: #2c3e50;
  margin-bottom: 20px;
}

.data-protection-content h4 {
  color: #2c3e50;
  margin-top: 15px;
  margin-bottom: 10px;
}

.data-protection-content ul {
  padding-left: 20px;
  margin-bottom: 20px;
}

.data-protection-content ul li {
  list-style-type: disc;
  margin-bottom: 5px;
}

.data-protection-content a {
  color: #3498db;
  text-decoration: none;
}

.data-protection-content a:hover {
  text-decoration: underline;
}
</style>
