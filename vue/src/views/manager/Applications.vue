<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search">
      <el-input
          placeholder="Search by Animal ID"
          v-model="search.animalId"
          style="width: 200px; margin-right: 10px;"
      ></el-input>
      <el-input
          placeholder="Search by User ID"
          v-if="user.role === 'ADMIN'"
          v-model="search.userId"
          style="width: 200px; margin-right: 10px;"
      ></el-input>
      <el-select
          v-model="search.status"
          placeholder="Search by Status"
          style="width: 200px; margin-right: 10px;"
      >
        <el-option label="Approved" value="Approved"></el-option>
        <el-option label="Rejected" value="Rejected"></el-option>
        <el-option label="Adoption Finished" value="Finished"></el-option>
      </el-select>
      <el-button type="primary" @click="loadApplications">Search</el-button>
      <el-button type="warning" @click="resetSearch">Reset</el-button>
    </div>

    <div class="operation" v-if="user.role !== 'USER'">
      <el-button
          type="danger"
          plain
          @click="deleteSelected"
          :disabled="multipleSelection.length === 0"
      >
        Batch Delete
      </el-button>
    </div>

    <!-- 申请列表 -->
    <el-table
        :data="applications"
        stripe
        @selection-change="handleSelectionChange"
        ref="applicationTable"
    >
      <!-- Enable selection -->
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" align="center" sortable></el-table-column>
      <el-table-column prop="animalId" label="Animal ID" show-overflow-tooltip></el-table-column>
      <el-table-column prop="userId" label="User ID" show-overflow-tooltip></el-table-column>
      <el-table-column prop="name" label="User Name" show-overflow-tooltip></el-table-column>
      <el-table-column prop="email" label="Email" min-width="150" show-overflow-tooltip></el-table-column>
      <el-table-column prop="phone" label="Phone"></el-table-column>
      <el-table-column prop="reason" label="Reason"></el-table-column>
      <el-table-column prop="status" label="Status"></el-table-column>

      <!-- Action Buttons for Admin -->
      <el-table-column v-if="user.role === 'ADMIN'" label="Actions" width="180" align="center">
        <template v-slot="scope">
          <el-button
              plain
              type="primary"
              @click="approveApplication(scope.row)"
              size="mini"
              :disabled="scope.row.status === 'Approved' || scope.row.status === 'Rejected'"
          >
            Approve
          </el-button>
          <el-button
              plain
              type="danger"
              @click="rejectApplication(scope.row)"
              size="mini"
              :disabled="scope.row.status === 'Approved' || scope.row.status === 'Rejected'"
          >
            Reject
          </el-button>
        </template>
      </el-table-column>

      <!-- Confirm Adoption Button -->
      <el-table-column
          v-if="user.role === 'ADMIN'"
          label="Confirm"
          width="150"
          align="center"
      >
        <template v-slot="scope">
          <el-button
              type="success"
              plain
              @click="confirmAdoption(scope.row)"
              size="mini"
              :disabled="scope.row.status === 'Adopted' || scope.row.status !== 'Approved'"
          >
            Confirm Adoption
          </el-button>
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
          :total="total"
      >
      </el-pagination>
    </div>

    <!-- 添加申请表单 -->
    <el-dialog
        title="Apply for Adoption"
        :visible.sync="formVisible"
        width="40%"
        :close-on-click-modal="false"
        destroy-on-close
    >
      <el-form
          label-width="100px"
          style="padding-right: 50px"
          :model="form"
          :rules="rules"
          ref="formRef"
      >
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
      },
      multipleSelection: [] // To store selected rows
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
      const updatedApplication = { ...application, status: 'Approved' };
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
      const updatedApplication = { ...application, status: 'Rejected' };
      this.$request.put('/application/update', updatedApplication).then(res => {
        if (res.code === '200') {
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
    confirmAdoption(application) {
      // Create the adoption record with integer IDs
      let adoptionRecord = {
        animalId: parseInt(application.animalId, 10), // Ensure animalId is an integer
        userId: parseInt(application.userId, 10)      // Ensure userId is an integer
      };

      // Send POST request to add adoption record
      this.$request.post('/adopt/add', adoptionRecord)
          .then((response) => {
            console.log('Response:', response);

            // Check if the response indicates success
            if (response.code === '200') {
              this.$message.success('Adoption confirmed successfully');

              // Call finishApplication to update the application status
              this.finishApplication(application);
            } else {
              this.$message.error('Failed to confirm adoption');
              console.error('Unexpected response structure:', response);
            }
          })
          .catch((error) => {
            console.error('Error confirming adoption:', error);
            this.$message.error('An error occurred while confirming the adoption');
          });
    },

    // Finish application method
    finishApplication(application) {
      // Create an updated application object with status 'Finished'
      const updatedApplication = { ...application, status: 'Finished' };

      // Send PUT request to update the application status
      this.$request.put('/application/update', updatedApplication)
          .then(res => {
            if (res.code === '200') {
              this.$message.success('Adoption finished successfully');
              this.loadApplications(); // Refresh the list of applications
            } else {
              this.$message.error('Failed to finish adoption');
              console.error('Unexpected response structure:', res);
            }
          })
          .catch(error => {
            console.error('Error finishing application:', error);
            this.$message.error('An error occurred while finishing the application');
          });
    },


    handlePageChange(pageNum) {
      this.pageNum = pageNum;
      this.loadApplications();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteSelected() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning('No applications selected');
        return;
      }
      this.$confirm(
          'Are you sure you want to delete the selected applications?',
          'Warning',
          {
            confirmButtonText: 'Yes',
            cancelButtonText: 'No',
            type: 'warning'
          }
      )
          .then(() => {
            const ids = this.multipleSelection.map(item => item.id);
            this.$request
                .delete('/application/delete/batch', { data: ids })
                .then(res => {
                  if (res.code === '200') {
                    this.$message.success('Selected applications deleted successfully');
                    this.loadApplications();
                  } else {
                    this.$message.error('Failed to delete selected applications');
                  }
                })
                .catch(error => {
                  console.error('Error deleting applications:', error);
                  this.$message.error('An error occurred while deleting applications');
                });
          })
          .catch(() => {
            this.$message.info('Delete action canceled');
          });
    }
  }
};
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
