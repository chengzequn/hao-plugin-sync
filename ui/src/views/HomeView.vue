<template>
  <div class="container">
    <h1>文章同步</h1>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="type">选择类型:</label>
        <div class="radio-group">
          <label for="type1">
            <input type="radio" id="type1" value="1" v-model="postVO.type" /> CSDN
          </label>
          <label for="type2">
            <input type="radio" id="type2" value="2" v-model="postVO.type" /> 简书
          </label>
          <label for="type3">
            <input type="radio" id="type3" value="3" v-model="postVO.type" /> 知乎
          </label>
          <label for="type4">
            <input type="radio" id="type4" value="4" v-model="postVO.type" /> 微信公众号
          </label>
          <label for="type5">
            <input type="radio" id="type5" value="5" v-model="postVO.type" /> 博客园
          </label>
          <label for="type6">
            <input type="radio" id="type6" value="6" v-model="postVO.type" /> 阿里云
          </label>
          <label for="type7">
            <input type="radio" id="type7" value="7" v-model="postVO.type" /> 掘金
          </label>
          <label for="type8">
            <input type="radio" id="type8" value="8" v-model="postVO.type" /> 开源中国
          </label>
          <label for="type9">
            <input type="radio" id="type9" value="9" v-model="postVO.type" /> 华为云
          </label>
          <label for="type10">
            <input type="radio" id="type10" value="10" v-model="postVO.type" /> 腾讯云
          </label>
        </div>
      </div>

      <div class="form-group">
        <label for="url">输入 URL:</label>
        <input type="text" id="url" v-model="postVO.url" placeholder="请输入 URL" />
      </div>

      <button type="submit">提交</button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import axios from 'axios';

export default defineComponent({
  name: 'HomeView',
  setup() {
    const postVO = ref({
      type: 1,
      url: ''
    });

    const handleSubmit = async () => {
      try {
        console.log('提交的数据:', postVO.value);
        const response = await axios.post('/api/submit', postVO.value, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        console.log('服务器响应:', response.data);
      } catch (error) {
        console.error('提交失败:', error);
      }
    };

    return {
      postVO,
      handleSubmit
    };
  }
});
</script>

<style scoped>
/* 美化容器和表单 */
.container {
  max-width: 100%;
  width: 100%;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

/* 表单标题 */
h1 {
  text-align: center;
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
  width: 100%;
}

/* 表单样式 */
form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: stretch;
}

/* 表单组样式 */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

/* 单选按钮样式 */
.radio-group {
  display: flex;
  gap: 20px;
  align-items: center;
}

.radio-group label {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #333;
}

input[type="radio"] {
  margin-right: 8px;
}

/* 输入框样式 */
input[type="text"] {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  color: #333;
  outline: none;
  transition: border-color 0.3s ease;
  width: 100%; /* 调整输入框宽度 */
}

input[type="text"]:focus {
  border-color: #0066cc;
  box-shadow: 0 0 5px rgba(0, 102, 204, 0.2);
}

/* 提交按钮样式 */
button {
  padding: 12px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: 100%; /* 调整按钮宽度 */
}

button:hover {
  background-color: #0056b3;
}

button:focus {
  outline: none;
}
</style>
