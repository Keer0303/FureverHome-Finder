<template>
  <div>
    <div class="card" style="padding: 15px; font-size: 20px; font-weight: bold">
      Animal Information
    </div>
    <div class="search">
      <el-input placeholder="Search By ID" style="width: 150px; margin-right: 10px;" v-model="id"></el-input>
      <el-input placeholder="Search By Name" style="width: 150px; margin-right: 10px;" v-model="name"></el-input>
      <el-input placeholder="Search By Breed" style="width: 150px; margin-right: 10px;" v-model="species"></el-input>
      <el-input placeholder="Search By Sex" style="width: 150px; margin-right: 10px;" v-model="sex"></el-input>
      <el-input placeholder="Search By Age" style="width: 150px; margin-right: 10px;" v-model="age"></el-input>
      <el-button type="info" plain style="margin-left: 10px; margin-right: 10px;" @click="load(1)">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px;" @click="reset">Reset</el-button>
    </div>

    <div class="operation" v-if="user.role !== 'USER'">
      <el-button type="primary" plain @click="handleAdd">Add New Pet</el-button>
      <el-button type="danger" plain @click="delBatch">Batch Delete</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="ID" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="img" label="Image" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.img"
                        :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="Name" show-overflow-tooltip></el-table-column>
        <el-table-column prop="species" label="Breed"></el-table-column>
        <el-table-column prop="sex" label="Sex"></el-table-column>
        <el-table-column prop="age" label="Age"></el-table-column>
        <el-table-column prop="status" label="Status"></el-table-column>
        <el-table-column prop="descr" label="Description" width="200">
          <template v-slot="scope">
            <span class="description" :title="scope.row.descr">
              {{ scope.row.descr }}
            </span>
          </template>
        </el-table-column>

        <el-table-column v-if="user.role !== 'USER'" label="Actions" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">Edit</el-button>
            <el-button plain type="danger" size="mini" @click="del(scope.row.id)">Delete</el-button>
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

    <el-dialog title="Information" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="120px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item label="Pet Image">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">Upload Image</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="Pet Name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="Pet Sex">
          <el-select v-model="form.sex" placeholder="Select Sex" style="width: 100%">
            <el-option label="Male" value="Male"></el-option>
            <el-option label="Female" value="Female"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="age" label="Pet Age">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="species" label="Pet Breed">
          <el-input v-model="form.species" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="status" label="Pet Status">
          <el-select v-model="form.status" placeholder="Select Status" style="width: 100%">
            <el-option label="Available" value="Available"></el-option>
            <el-option label="Adopted" value="Adopted"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="descr" label="Pet Description">
          <el-input type="textarea" :rows="4" v-model="form.descr" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Animal",
  data() {
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page number
      pageSize: 10,  // Items per page
      total: 0,
      name: null,
      id:null,
      species:null,
      sex:null,
      age:null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: 'Please enter pet name', trigger: 'blur'},
        ],
        id: [
          {required: true, message: 'Please enter pet ID', trigger: 'blur'},
        ],
        species: [
          {required: true, message: 'Please enter pet species', trigger: 'blur'},
        ],
        sex: [
          {required: true, message: 'Please enter pet sex', trigger: 'blur'},
        ],
        age: [
          {required: true, message: 'Please enter pet age', trigger: 'blur'},
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
      this.form = {}  // Clear form data for new entry
      this.fromVisible = true   // Open the dialog
    },
    handleEdit(row) {   // Edit data
      this.form = JSON.parse(JSON.stringify(row))  // Assign data to form, ensure deep copy
      this.fromVisible = true   // Open the dialog
    },
    save() {   // Save button logic, triggers add or update
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/animal/update' : '/animal/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // Save successful
              this.$message.success('Save successful')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // Show error message
            }
          })
        }
      })
    },
    del(id) {   // Delete single item
      this.$confirm('Are you sure you want to delete?', 'Confirm Delete', {type: "warning"}).then(response => {
        this.$request.delete('/animal/delete/' + id).then(res => {
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
    handleSelectionChange(rows) {   // Selected rows
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // Batch delete
      if (!this.ids.length) {
        this.$message.warning('Please select data')
        return
      }
      this.$confirm('Are you sure you want to delete these items?', 'Confirm Delete', {type: "warning"}).then(response => {
        this.$request.delete('/animal/delete/batch', {data: this.ids}).then(res => {
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
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum;

      // Construct the params object
      const params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.name,
        id: this.id,
        species: this.species,
        sex: this.sex,
        age: this.age,
      };

      this.$request.get('/animal/selectPage', { params })
          .then(res => {
            let data = res.data?.list || [];

            // If the user's role is 'USER', filter out 'Adopted' status on the frontend
            if (this.user.role === 'USER') {
              data = data.filter(item => item.status !== 'Adopted');
            }

            this.tableData = data;
            this.total = data.length;
          });
    },
    reset() {
      this.name = null
      this.id = null
      this.name = null
      this.species = null
      this.sex = null
      this.age = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      // Assign uploaded image link to avatar property
      this.form.img = response.data
    },
  }
}
</script>

<style scoped>
.description {
  display: block;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  max-width: 150px; /* Adjust the width as needed */
  cursor: pointer;
}

.description:hover {
  overflow: visible;
  white-space: normal;
  background-color: #f9f9f9;
  border: 1px solid #eaeaea;
  padding: 5px;
  position: absolute;
  z-index: 10;
  max-width: 300px; /* Adjust the width as needed for the hover state */
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}
</style>
