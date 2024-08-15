<template>
  <div>
    <!-- Search Section -->
    <div class="search">
      <el-input
          placeholder="Please enter shelter address"
          style="width: 200px"
          v-model="name"
      ></el-input>
      <el-button
          type="info"
          plain
          style="margin-left: 10px"
          @click="load(1)"
      >Search</el-button>
      <el-button
          type="warning"
          plain
          style="margin-left: 10px"
          @click="reset"
      >Reset</el-button>
    </div>

    <!-- Operation Section -->
    <div class="operation">
      <el-button type="primary" plain @click="openAddDialog">Add New Shelter</el-button>
      <el-button v-if="user.role === 'ADMIN'" type="danger" plain @click="delBatch">Batch Delete</el-button>
    </div>

    <!-- Table Section -->
    <div class="table">
      <el-table
          :data="tableData"
          stripe
          @selection-change="handleSelectionChange"
      >
        <el-table-column
            type="selection"
            width="55"
            align="center"
            v-if="user.role === 'ADMIN'"
        ></el-table-column>
        <el-table-column
            prop="id"
            label="ID"
            width="80"
            align="center"
            sortable
        ></el-table-column>
        <el-table-column
            prop="name"
            label="Shelter Address"
            show-overflow-tooltip
        ></el-table-column>
        <el-table-column
            prop="status"
            label="Shelter Status"
            show-overflow-tooltip
        ></el-table-column>
        <el-table-column
            prop="animal"
            label="Boarded Pet"
            width="120"
            show-overflow-tooltip
        ></el-table-column>

        <!-- Actions Column -->
        <el-table-column label="Actions" width="300" align="center">
          <template v-slot="scope">
            <div class="action-buttons">
              <el-button
                  plain
                  type="primary"
                  @click="openEditDialog(scope.row)"
                  size="mini"
                  v-if="user.role === 'ADMIN'"
              >Edit</el-button>
              <el-button
                  plain
                  type="danger"
                  size="mini"
                  @click="del(scope.row.id)"
                  v-if="user.role === 'ADMIN'"
              >Delete</el-button>
              <el-button
                  plain
                  type="success"
                  size="mini"
                  @click="markRoomVacant(scope.row)"
                  :disabled="scope.row.status !== 'Occupied'"
                  v-if="user.role === 'ADMIN'"
              >Mark as Vacant</el-button>

            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- Pagination Section -->
      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total"
        >
        </el-pagination>
      </div>
    </div>

    <!-- Dialog Section -->
    <el-dialog
        :title="isEditing ? 'Add Board Pets' : 'Add New Shelter'"
        :visible.sync="formVisible"
        width="60%"
        :close-on-click-modal="false"
        destroy-on-close
    >
      <el-form
          label-width="200px"
          style="padding-right: 50px"
          :model="form"
          :rules="dialogRules"
          ref="formRef"
      >
        <div class="inline-form-items">
          <el-form-item prop="name" label="Shelter Address" >
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item
              v-if="isEditing && user.role === 'ADMIN'"
              prop="animal"
              label="Boarded Pet"
          >
            <el-input v-model="form.animal" autocomplete="off"></el-input>
          </el-form-item>

        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Room",
  data() {
    return {
      tableData: [], // All data
      pageNum: 1, // Current page number
      pageSize: 10, // Number of items per page
      total: 0,
      name: null,
      formVisible: false,
      isEditing: false, // Determine if the dialog is for editing
      form: {
        name: '', // Initialize name
        animal: '', // Initialize animal
        status: 'Vacant', // Default status when adding a new shelter
      },
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      dialogRules: {
        name: [
          { required: true, message: 'Please enter shelter address', trigger: 'blur' },
        ],
        animal: [
          { required: true, message: 'Please enter the boarded pet name', trigger: 'blur' },
        ],
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    openAddDialog() { // Open dialog for adding new shelter
      this.isEditing = false;
      this.form = { name: '', animal: '', status: 'Vacant' }; // Clear form data
      this.formVisible = true; // Open dialog
    },
    openEditDialog(row) { // Open dialog for editing shelter
      if (this.user.role !== 'ADMIN') {
        this.$message.warning('You do not have permission to edit shelters');
        return;
      }
      this.isEditing = true;
      this.form = JSON.parse(JSON.stringify(row)); // Assign value to form object, deep copy data
      this.formVisible = true; // Open dialog
    },
    save() { // Logic triggered by the save button, it will trigger add or update
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          // Update status to "Occupied" if a pet is added during editing
          if (this.isEditing && this.user.role === 'ADMIN' && this.form.animal) {
            this.form.status = 'Occupied';
          }
          this.$request({
            url: this.isEditing ? '/room/update' : '/room/add',
            method: this.isEditing ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') { // Indicates successful save
              this.$message.success('Save successful')
              this.load(1)
              this.formVisible = false
            } else {
              this.$message.error(res.msg) // Show error message
            }
          })
        }
      })
    },
    del(id) { // Delete single item
      if (this.user.role !== 'ADMIN') {
        this.$message.warning('You do not have permission to delete shelters');
        return;
      }
      this.$confirm('Are you sure you want to delete?', 'Confirm Delete', { type: "warning" }).then(response => {
        this.$request.delete('/room/delete/' + id).then(res => {
          if (res.code === '200') { // Indicates successful operation
            this.$message.success('Operation successful')
            this.load(1)
          } else {
            this.$message.error(res.msg) // Show error message
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) { // Currently selected rows
      this.ids = rows.map(v => v.id) // [1,2]
    },
    delBatch() { // Batch delete
      if (this.user.role !== 'ADMIN') {
        this.$message.warning('You do not have permission to delete shelters');
        return;
      }
      if (!this.ids.length) {
        this.$message.warning('Please select data')
        return
      }
      this.$confirm('Are you sure you want to delete these items?', 'Confirm Delete', { type: "warning" }).then(response => {
        this.$request.delete('/room/delete/batch', { data: this.ids }).then(res => {
          if (res.code === '200') { // Indicates successful operation
            this.$message.success('Operation successful')
            this.load(1)
          } else {
            this.$message.error(res.msg) // Show error message
          }
        })
      }).catch(() => {
      })
    },
    markRoomVacant(row) { // Mark room as vacant
      if (this.user.role !== 'ADMIN') {
        this.$message.warning('You do not have permission to mark rooms as vacant');
        return;
      }
      this.$confirm('Has the pet been adopted and is the room now vacant?', 'Confirm Vacancy', { type: 'info' }).then(() => {
        const updatedRoom = { ...row, status: 'Vacant', animal: '' }; // Update the room's status and clear the animal
        this.$request({
          url: `/room/update`, // Use the correct endpoint
          method: 'PUT',
          data: updatedRoom // Pass the updated room object
        }).then(res => {
          if (res.code === '200') { // Successful update
            this.$message.success('Room marked as vacant')
            this.load(this.pageNum) // Reload the current page
          } else {
            this.$message.error(res.msg) // Error message
          }
        }).catch(error => {
          console.error('Error updating room:', error);
          this.$message.error('Failed to update room status');
        });
      }).catch(() => {
      })
    },
    load(pageNum) { // Load data with pagination
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/room/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() { // Reset search criteria
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) { // Handle page change
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>
.inline-form-items {
  display: flex;
  gap: 20px; /* Space between form items */
  align-items: center; /* Align items vertically centered */
}

.action-buttons {
  display: flex; /* Use flexbox for inline layout */
  gap: 10px; /* Add spacing between buttons */
  justify-content: center; /* Center buttons within the div */
}

.action-buttons .el-button {
  margin: 0; /* Ensure no extra margin on buttons */
}
/* Add your styles here */
</style>
