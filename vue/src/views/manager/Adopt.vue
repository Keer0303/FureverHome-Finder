<template>
  <div>
    <div class="search">
      <!-- 搜索框 -->
      <el-input v-model="searchQuery" placeholder="Search By Animal Name" style="width: 200px"  @input="handleSearch"></el-input>
      <ul v-if="filteredAnimalData.length > 0" class="search-results">
        <li v-for="item in filteredAnimalData" :key="item.id" @click="selectAnimal(item.id)">
          {{ item.name }}
        </li>
      </ul>

      <!-- 收养人搜索框 -->
      <el-input v-model="adopterQuery" placeholder="Search By Adopter" style="width: 200px; margin-left: 5px" @input="handleAdopterSearch" v-if="user.role === 'ADMIN'"></el-input>
      <ul v-if="filteredAdopterData.length > 0" class="search-results">
        <li v-for="item in filteredAdopterData" :key="item.id" @click="selectAdopter(item.id)">
          {{ item.name }}
        </li>
      </ul>

      <!-- 状态选择框 -->
      <el-select v-model="status" placeholder="Search By Status" style="width: 200px; margin-left: 5px">
        <el-option label="Adopted" value="Adopted"></el-option>
        <el-option label="In Process" value="In Process"></el-option>
<!--        <el-option label="GiveUp Adoption" value="GiveUp Adoption"></el-option>-->
      </el-select>

      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="ID" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="animalImg" label="Image" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.animalImg"
                        :src="scope.row.animalImg" :preview-src-list="[scope.row.animalImg]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="animalName" label="Name" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="Adopter" show-overflow-tooltip></el-table-column>
        <el-table-column prop="time" label="Adopt Time"></el-table-column>
        <el-table-column prop="status" label="Adoption Status"></el-table-column>
        <el-table-column label="Actions" width="180" align="center">
          <template v-slot="scope">
            <el-button v-if="user.role !== 'ADMIN'" plain type="danger" size="mini" :disabled="scope.row.status === 'GiveUp Adoption'" @click="GiveUp(scope.row)">Give Up Adoption</el-button>
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
  </div>
</template>

<script>
export default {
  name: "Adopt",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      animalId: null,
      adopterId: null,
      status: null,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      ids: [],
      animalData: [],
      adopterData: [],
      searchQuery: '',
      adopterQuery: ''  // 添加收养人搜索输入框的绑定值
    }
  },
  created() {
    this.load(1);
    this.loadAnimal();
    this.loadAdopter();
  },
  computed: {
    filteredAnimalData() {
      if (!this.searchQuery) {
        return [];
      }
      return this.animalData.filter(item =>
          item.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    filteredAdopterData() {
      if (!this.adopterQuery) {
        return [];
      }
      return this.adopterData.filter(item =>
          item.name.toLowerCase().includes(this.adopterQuery.toLowerCase())
      );
    }
  },
  methods: {
    loadAnimal() {
      this.$request.get('/adopt/selectAnimal').then(res => {
        if (res.code === '200') {
          this.animalData = res.data;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error('Request failed', error);
        this.$message.error('Failed to load animal data.');
      });
    },
    loadAdopter() {
      this.$request.get('/adopt/selectAdopter').then(res => {
        if (res.code === '200') {
          this.adopterData = res.data;
          console.log(this.adopterData); // 打印 adopterData 以确认数据正确加载
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error('Request failed', error);
        this.$message.error('Failed to load adopter data.');
      });
    },
    save() {
      this.$request({
        url: this.form.id ? '/adopt/update' : '/adopt/add',
        method: this.form.id ? 'PUT' : 'POST',
        data: this.form
      }).then(res => {
        if (res.code === '200') {
          this.$message.success('Saved successfully');
          this.load(1);
          this.fromVisible = false;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    GiveUp(row) {
      this.$confirm('Are you sure you want to give up the adoption? Giving up the adoption means you will no longer be the potential adopter for this animal. If you have any concerns or need assistance, please contact our support team.', 'Confirm', {type: "warning"}).then(response => {
        this.form = JSON.parse(JSON.stringify(row));
        this.form.status = 'GiveUp Adoption';
        this.save();
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id);
    },
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('Please select data');
        return;
      }
      this.$confirm('Are you sure you want to delete these data?', 'Confirm Delete', {type: "warning"}).then(response => {
        this.$request.delete('/adopt/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {
            this.$message.success('Operation successful');
            this.load(1);
          } else {
            this.$message.error(res.msg);
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum;
      this.$request.get('/adopt/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          status: this.status,
          animalId: this.animalId,
          userId: this.adopterId
        }
      }).then(res => {
        this.tableData = res.data?.list;
        this.total = res.data?.total;
      })
    },
    reset() {
      this.status = null;
      this.animalId = null;
      this.adopterId = null;
      this.searchQuery = '';
      this.adopterQuery = '';
      this.load(1);
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum);
    },
    handleSearch() {
      // 在这里可以处理额外的搜索逻辑，例如记录搜索关键词或其他操作
      // 当前示例中没有具体操作，因为过滤逻辑在 computed 属性中
    },
    handleAdopterSearch() {
      // 这里处理额外的收养人搜索逻辑
    },
    selectAnimal(id) {
      this.animalId = id;
      this.searchQuery = this.animalData.find(item => item.id === id).name;
    },
    selectAdopter(id) {
      this.adopterId = id;
      this.adopterQuery = this.adopterData.find(item => item.id === id).name;
    }
  }
}
</script>

<style scoped>
.search-results {
  list-style-type: none;
  padding: 0;
  margin: 0;
  border: 1px solid #dcdcdc;
  max-height: 150px;
  overflow-y: auto;
  background: white;
}

.search-results li {
  padding: 8px;
  cursor: pointer;
}

.search-results li:hover {
  background: #f0f0f0;
}
</style>
