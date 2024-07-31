<template>
  <div>
    <div class="card header-card">
      Hello, {{ user?.name }},  Welcome to Furever Home Finder!
      <el-button type="primary" @click="showNotices" class="notice-button">Notice</el-button>
    </div>

    <div class="content">
      <div class="card pet-info-card">
        <!-- Pet Info -->
        <div class="pet-info-title">Adopt love, one paw at a time.</div>
        <el-row>
          <el-col :span="6" v-for="item in filteredAnimalData" :key="item.id">
            <div class="card pet-card">
              <img :src="item.img" alt="" class="pet-image">
              <div class="pet-name">{{ item.name }} <br>（{{ item.species }}）</div>
              <div class="pet-details">Sex：{{ item.sex }}， Age：{{ item.age }}</div>
              <div class="pet-status">Status：<span class="status-text">{{ item.status }}</span></div>
              <div class="about-me">About me：<br><span class="about-me-text">{{ item.descr }}</span></div>
              <div>
                <el-button type="success" size="mini" class="adopt-button" :disabled="item.status === 'Adopted' || item.status === 'In Process' || user.role === 'ADMIN'" @click="openAdoptionForm(item)">Apply to Adopt</el-button>
                <el-button type="primary" size="mini" class="details-button" @click="showDetails(item)">View Details</el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 申请领养表单对话框 -->
    <el-dialog :visible.sync="adoptionFormVisible" width="40%" title="Adoption Application">
      <el-form :model="adoptionForm" ref="adoptionForm">
        <el-form-item label="Reason" :label-width="formLabelWidth">
          <el-input type="textarea" :rows="4" v-model="adoptionForm.reason"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="adoptionFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitAdoptionForm">Submit</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <span slot="title">{{ selectedAnimal.name }}'s Details</span>
      <div>
        <img :src="selectedAnimal.img" alt="" class="details-image">
        <div class="details-name">{{ selectedAnimal.name }}</div>
        <div class="details-info">Species: {{ selectedAnimal.species }}</div>
        <div class="details-info">Sex: {{ selectedAnimal.sex }}</div>
        <div class="details-info">Age: {{ selectedAnimal.age }}</div>
        <div class="details-info">Status: <span :style="{ color: selectedAnimal.status === 'Adopted' ? '#7d3d0c' : '#000' }">{{ selectedAnimal.status }}</span></div>
        <div class="details-about">About me: {{ selectedAnimal.descr }}</div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Close</el-button>
      </div>
    </el-dialog>

    <!-- 公告对话框 -->
    <el-dialog :visible.sync="noticeDialogVisible" width="50%" title="Notice">
      <el-timeline reverse>
        <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
          <el-popover
              placement="right"
              width="200"
              trigger="hover"
              :content="item.content">
            <span slot="reference">{{ item.title }}</span>
          </el-popover>
        </el-timeline-item>
      </el-timeline>
      <div slot="footer" class="dialog-footer">
        <el-button @click="noticeDialogVisible = false">Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      animalData: [],
      dialogVisible: false,
      adoptionFormVisible: false,
      noticeDialogVisible: false,
      selectedAnimal: {},
      adoptionForm: {
        reason: ''
      },
      formLabelWidth: '120px'
    }
  },
  computed: {
    filteredAnimalData() {
      return this.animalData.filter(item => item.status !== 'Adopted');
    }
  },
  created() {
    this.loadNotices();
    this.loadAnimal();
  },
  methods: {
    loadNotices() {
      this.$request.get('/notice/selectAll').then(res => {
        if (res.code === '200') {
          this.notices = res.data || []
        } else {
          this.$message.error('Failed to load notices');
        }
      }).catch(error => {
        console.error('Error loading notices:', error);
        this.$message.error('An error occurred while loading notices');
      });
    },
    loadAnimal() {
      this.$request.get('/animal/selectAll').then(res => {
        if (res.code === '200') {
          this.animalData = res.data;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error('Error loading animal data:', error);
        this.$message.error('An error occurred while loading animal data');
      });
    },
    openAdoptionForm(animal) {
      this.selectedAnimal = animal;
      this.adoptionFormVisible = true;
    },
    submitAdoptionForm() {
      if (!this.adoptionForm.reason) {
        this.$message.error('Please fill in the reason for adoption');
        return;
      }

      let applicationData = {
        userId: this.user.id,
        animalId: this.selectedAnimal.id,
        name: this.user.name,
        email: this.user.email,
        phone: this.user.phone,
        reason: this.adoptionForm.reason,
        status: 'Pending'
      };

      this.$request.post('/application/add', applicationData).then(res => {
        if (res.code === '200') {
          this.$message.success('Adoption request sent');
          this.adoptionFormVisible = false;
          this.resetAdoptionForm();
          this.loadAnimal(); // Reload animal data to reflect the status change
        } else {
          this.$message.error('Unable to submit the application. Please complete your email address.');
        }
      }).catch(error => {
        console.error('Error sending adoption request:', error.response || error.message || error);
        this.$message.error('An error occurred while sending adoption request');
      });
    },
    resetAdoptionForm() {
      this.adoptionForm = {
        reason: ''
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
    showNotices() {
      this.noticeDialogVisible = true;
    }
  }
}
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

.notice-button {
  margin-left: auto;
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
  color: #1F618D;
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

.pet-details, .pet-status {
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

.adopt-button, .details-button {
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

.details-info, .details-about {
  margin-top: 10px;
}
</style>
