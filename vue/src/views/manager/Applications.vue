<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search">
      <el-input placeholder="Search by Animal ID" v-model="search.animalId" style="width: 200px; margin-right: 10px;"></el-input>
      <el-input placeholder="Search by User ID" v-if="user.role === 'ADMIN'" v-model="search.userId" style="width: 200px; margin-right: 10px;"></el-input>
      <el-select v-model="search.status" placeholder="Search by Status" style="width: 200px; margin-right: 10px;">
        <el-option label="Approved" value="Approved"></el-option>
        <el-option label="Rejected" value="Rejected"></el-option>
      </el-select>
      <el-button type="primary" @click="loadApplications">Search</el-button>
      <el-button type="warning" @click="resetSearch">Reset</el-button>
    </div>
    <!-- 申请列表 -->
    <el-table :data="applications" stripe>
      <el-table-column prop="id" label="ID" width="80" align="center" sortable></el-table-column>
      <el-table-column prop="animalId" label="Animal ID" show-overflow-tooltip></el-table-column>
      <el-table-column prop="userId" label="User ID" show-overflow-tooltip></el-table-column>
      <el-table-column prop="name" label="Name" show-overflow-tooltip></el-table-column>
      <el-table-column prop="email" label="Email" show-overflow-tooltip></el-table-column>
      <el-table-column prop="phone" label="Phone"></el-table-column>
      <el-table-column prop="reason" label="Reason"></el-table-column>
      <el-table-column prop="status" label="Status"></el-table-column>
      <el-table-column v-if="user.role === 'ADMIN'" label="Actions" width="180" align="center">
        <template v-slot="scope">
          <el-button plain type="primary" @click="approveApplication(scope.row)" size="mini" :disabled="scope.row.status === 'Approved' || scope.row.status === 'Rejected'">Approve</el-button>
          <el-button plain type="danger" @click="rejectApplication(scope.row)" size="mini" :disabled="scope.row.status === 'Approved' || scope.row.status === 'Rejected'">Reject</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
          background
          @current-change="handlePageChange"
          :current-page="pageNum"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <!-- 添加申请表单 -->
    <el-dialog title="Apply for Adoption" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="animalId" label="Animal ID">
          <el-input v-model="form.animalId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="reason" label="Reason">
          <el-input type="textarea" :rows="4" v-model="form.reason" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitApplication">Submit</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Application",
  data() {
    return {
      applications: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      formVisible: false,
      form: {
        animalId: '',
        reason: ''
      },
      search: {
        animalId: '',
        userId: '',
        status: ''
      },
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        animalId: [
          { required: true, message: 'Please enter the animal ID', trigger: 'blur' }
        ],
        reason: [
          { required: true, message: 'Please enter the reason for adoption', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.loadApplications();
  },
  methods: {
    loadApplications() {
      const params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        userId: this.user.role !== 'ADMIN' ? this.user.id : null
      };

      if (this.search.animalId) {
        params.animalId = this.search.animalId;
      }
      if (this.search.userId && this.user.role === 'ADMIN') {
        params.userId = this.search.userId;
      }
      if (this.search.status) {
        params.status = this.search.status;
      }

      this.$request.get('/application/selectPage', { params })
          .then(res => {
            if (res.data) {
              this.applications = res.data.list;
              this.total = res.data.total;
            } else {
              this.$message.error('Failed to load applications');
            }
          }).catch(error => {
        console.error('Error loading applications:', error);
        this.$message.error('An error occurred while loading applications');
      });
    },
    resetSearch() {
      this.search = {
        animalId: '',
        userId: '',
        status: ''
      };
      this.loadApplications();
    },
    submitApplication() {
      if (!this.user.email) {
        this.$message.error('Unable to submit the application. Please complete your email address.');
        return;
      }
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.$request.post('/application/add', this.form).then(res => {
            if (res.data) {
              this.$message.success('Application submitted successfully');
              this.formVisible = false;
              this.loadApplications();
            } else {
              this.$message.error('Failed to submit application');
            }
          }).catch(error => {
            console.error('Error submitting application:', error);
            this.$message.error('An error occurred while submitting the application');
          });
        }
      });
    },
    approveApplication(application) {
      const updatedApplication = {...application, status: 'Approved'};
      this.$request.put('/application/update', updatedApplication).then(res => {
        if (res.code === '200') {
          this.$message.success('Application approved successfully');
          this.loadApplications();
        } else {
          this.$message.error('Failed to approve application');
        }
      }).catch(error => {
        console.error('Error approving application:', error);
        this.$message.error('An error occurred while approving the application');
      });
    },
    rejectApplication(application) {
      const updatedApplication = {...application, status: 'Rejected'};
      this.$request.put('/application/update', updatedApplication).then(res => {
        if (res.data) {
          this.$message.success('Application rejected successfully');
          this.loadApplications();
        } else {
          this.$message.error('Failed to reject application');
        }
      }).catch(error => {
        console.error('Error rejecting application:', error);
        this.$message.error('An error occurred while rejecting the application');
      });
    },
    handlePageChange(pageNum) {
      this.pageNum = pageNum;
      this.loadApplications();
    }
  }
}
</script>

<style scoped>
.operation {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style>
