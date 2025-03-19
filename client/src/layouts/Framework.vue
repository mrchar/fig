<script setup lang="ts">
</script>

<template>
  <div class="w-screen h-screen flex flex-col">
    <div class="navbar bg-base-100 shadow-sm">
      <a class="btn btn-ghost text-xl">Fig</a>
    </div>
    <div class="h-full flex">
      <ul class="menu bg-base-200 rounded-box w-56 h-full">
        <li><a>词汇表</a></li>
        <li><a>数据格式</a></li>
        <li><a>表单设计</a></li>
        <li><a>流程设计</a></li>
        <li><a>数据分析</a></li>
      </ul>
      <div class="w-full h-full">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

