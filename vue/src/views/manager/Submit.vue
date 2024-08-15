<template>
  <div>
    <div class="search">
      <el-input placeholder="Search By Description" style="width: 200px" v-model="name"></el-input>
      <el-select v-model="status" placeholder="Search By Status" style="width: 200px; margin-left: 5px">
        <el-option label="Pending" value="Pending"></el-option>
        <el-option label="Processed" value="Processed"></el-option>
      </el-select>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="operation" v-if="user.role === 'USER'">
      <el-button type="primary" plain @click="handleAdd">Report</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe>
        <el-table-column prop="id" label="ID" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="img" label="Report Image" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px" v-if="scope.row.img"
                        :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="Description" show-overflow-tooltip></el-table-column>
        <el-table-column prop="time" label="Report Time"></el-table-column>
        <el-table-column prop="status" label="Status"></el-table-column>

        <el-table-column label="Operations" width="180" align="center" v-if="user.role === 'ADMIN'">
          <template v-slot="scope">
            <div style="display: flex; gap: 4px;">
              <el-button plain type="primary" :disabled="scope.row.status === 'Processed'" @click="deal(scope.row, 'Processed')" size="mini">Process</el-button>
              <el-button plain type="danger" size="mini" @click="del(scope.row.id)">Delete</el-button>
            </div>
          </template>
        </el-table-column>

      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog title="Please report real and effective information, thank you for your cooperation!" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="img" label="Upload Img">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">Upload Image</el-button>
          </el-upload>
          <span style="color: #999; font-size: 12px; margin-left: 10px;">Image should be less than 2MB</span>
        </el-form-item>
        <el-form-item prop="name" label="Description">
          <el-input type="textarea" :rows="4" v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
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
  name: "Submit",
  data() {
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page number
      pageSize: 10,  // Number of items per page
      total: 0,
      name: null,
      status: null,
      formVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: 'Please enter the description', trigger: 'blur'},
        ],
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleAdd() {   // Add new data
      this.form = {
        status: 'Pending'
      }  // Clear data when adding new data
      this.formVisible = true   // Open modal
    },
    deal(row, status) {
      let data = JSON.parse(JSON.stringify(row))
      data.status = status
      this.$request.put('/submit/update', data).then(res => {
        if (res.code === '200') {
          this.$message.success('Operation successful')
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    save() {   // Logic triggered by save button, it will trigger add or update
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/submit/update' : '/submit/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // Successfully saved
              this.$message.success('Save successful')
              this.load(1)
              this.formVisible = false
            } else {
              this.$message.error(res.msg)  // Show error message
            }
          })
        }
      })
    },
    del(id) {   // Delete a record
      this.$confirm('Are you sure you want to delete this record? It might never be processed…', 'Warning', {type: "warning"}).then(response => {
        this.$request.delete('/submit/delete/' + id).then(res => {
          if (res.code === '200') {   // Operation successful
            this.$message.success('Operation successful')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // Show error message
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // Pagination query
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/submit/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          status: this.status,
        }
      }).then(res => {
        this.tableData = res.data?.list.map(item => {
          return {
            ...item,
            time: new Date(item.time).toLocaleString('sv-SE', { timeZone: 'UTC' })
          }
        });
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.status = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.img = response.data
    },
  }
}
</script>

<style scoped>

</style>
