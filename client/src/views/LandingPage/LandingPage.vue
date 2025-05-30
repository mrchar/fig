<script setup lang="ts">
import { Icon } from "@iconify/vue"
import ThemeToggle from "@/layouts/components/ThemeToggle.vue"

useTitle("无花果低代码：开启高效业务数字化之旅")
</script>
<template>
  <!-- 导航栏 -->
  <Navbar class="sticky top-0 z-50">
    <ul class="menu menu-horizontal px-1">
      <li><a href="#title">首页</a></li>
      <li><a href="#feature-1">功能介绍</a></li>
      <li><a>案例展示</a></li>
      <li><a>联系我们</a></li>
    </ul>
    <ThemeToggle/>
  </Navbar>

  <main>
    <!-- 英雄区域 -->
    <section
      id="title"
      class="hero scroll-mt-32 min-h-screen flex flex-col justify-center items-center space-y-8 px-4 bg-transparent dark:bg-gradient-to-br dark:from-slate-900/20 dark:via-gray-900/20 dark:to-zinc-900/20">
      <h1 class="text-5xl md:text-7xl lg:text-9xl font-bold text-center">无花果低代码</h1>
      <p class="text-xl md:text-3xl lg:text-5xl text-center">开启高效业务数字化之旅</p>
      <RouterLink to="/vocabulary/list">
        <button class="btn btn-primary btn-lg text-2xl px-12 py-4">立即体验</button>
      </RouterLink>
    </section>

    <!-- AI 全域赋能介绍 -->
    <section id="feature-1" class="scroll-mt-32 py-16 px-8 bg-transparent dark:bg-gradient-to-br dark:from-slate-900/30 dark:via-gray-900/30 dark:to-zinc-900/30">
      <div class="container mx-auto text-center">
        <h2 class="text-4xl font-bold mb-8">
          AI 全域赋能，驱动高效智能变革
        </h2>
        <p class="max-w-3xl mx-auto">无花果低代码深度嵌入 AI
          技术，实现全流程智能化革新。从业务概念提炼时提供智能分析与建议，辅助精准把握核心要点；到数据建模阶段，依据业务特性智能推荐合适结构；再到表单创建、工作流设计以及计算逻辑定义，AI
          均能依据输入信息，生成高效且贴合实际的方案。在界面设计方面，更是能结合品牌风格与用户体验偏好，打造精美界面。这一系列
          AI 驱动的功能，大幅缩短开发周期，提升整体效率，助力企业高效完成数字化转型。</p>
      </div>
    </section>

    <!-- 功能介绍区域 -->
    <section id="feature-2" class="py-16 px-8 dark:bg-gradient-to-br dark:from-slate-900/40 dark:via-gray-900/40 dark:to-zinc-900/40">
      <div class="container mx-auto">
        <h2 class="text-4xl font-bold text-center mb-12">极致体验，深度赋能</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- 卡片通用类 -->
          <div
            class="card dark:bg-gradient-to-br dark:from-slate-800/20 dark:via-gray-800/20 dark:to-zinc-800/20 backdrop-blur-md border border-gray-700 shadow-xl">
            <div class="card-body">
              <h3 class="card-title">精准抽象，构建清晰业务概念</h3>
              <p>
                无花果低代码赋予您精准抽象业务概念的能力。无论是日常的工作流程，还是特殊的业务场景，您都能快速捕捉关键信息，将复杂业务元素提炼为清晰、可操作的数字化概念。</p>
            </div>
          </div>
          <div
            class="card dark:bg-gradient-to-br dark:from-slate-800/20 dark:via-gray-800/20 dark:to-zinc-800/20 dark:backdrop-blur-md border dark:border-gray-700 shadow-xl">
            <div class="card-body">
              <h3 class="card-title">灵活建模，打造贴合业务模型</h3>
              <p>
                借助平台先进的建模技术，您能够轻松打造精准的业务数据模型。平台支持对建模粒度进行自由把控，无论是对数据的宏观分类，还是对数据记录细节的深度挖掘，都能根据需求灵活调整。</p>
            </div>
          </div>
          <div
            class="card dark:bg-gradient-to-br dark:from-slate-800/20 dark:via-gray-800/20 dark:to-zinc-800/20 backdrop-blur-md border border-gray-700 shadow-xl">
            <div class="card-body">
              <h3 class="card-title">自主定制，生成精美专属表单</h3>
              <p>
                平台支持您自主创建表单，彻底摆脱传统模板的束缚。不管是简单的数据收集，还是复杂的业务审批表单，您都能随心所欲地调整表单的样式、设置所需的信息填写项等细节。</p>
            </div>
          </div>
          <div
            class="card dark:bg-gradient-to-br dark:from-slate-800/20 dark:via-gray-800/20 dark:to-zinc-800/20 backdrop-blur-md border border-gray-700 shadow-xl">
            <div class="card-body">
              <h3 class="card-title">巧妙规划，推动顺畅表单流转</h3>
              <p>
                工作流设计是无花果低代码的一大突出优势。您可以根据业务流程的实际逻辑，轻松规划表单的流转路径，从发起申请，到多层级的审批环节，再到最后的存档，整个过程清晰明了、易于把控。</p>
            </div>
          </div>
          <div
            class="card dark:bg-gradient-to-br dark:from-slate-800/20 dark:via-gray-800/20 dark:to-zinc-800/20 backdrop-blur-md border border-gray-700 shadow-xl">
            <div class="card-body">
              <h3 class="card-title">便捷定义，实现智能计算逻辑</h3>
              <p>
                在表单与表单之间的流转进程中，无花果低代码提供强大的计算逻辑定义能力。用户可通过简洁易懂的表达式，精准设定数据流转的逻辑规则，实现数据的自动赋值。</p>
            </div>
          </div>
          <div
            class="card dark:bg-gradient-to-br dark:from-slate-800/20 dark:via-gray-800/20 dark:to-zinc-800/20 backdrop-blur-md border border-gray-700 shadow-xl">
            <div class="card-body">
              <h3 class="card-title">深度挖掘，释放巨大数据价值</h3>
              <p>
                平台助力企业全面收集业务各环节产生的数据，将分散的数据整合起来，构建起企业的核心数据资产。同时，凭借先进的数据挖掘和分析工具，深入探寻数据背后隐藏的价值。</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 页脚 -->
    <footer
      class="footer p-10 dark:bg-gradient-to-br dark:from-slate-800/30 dark:via-gray-800/30 dark:to-zinc-800/30 backdrop-blur-md border-t border-gray-700 text-base-content flex justify-between">
      <div>
        <span class="footer-title">导航</span>
        <a class="link link-hover">首页</a>
        <a class="link link-hover">功能介绍</a>
        <a class="link link-hover">案例展示</a>
        <a class="link link-hover">联系我们</a>
      </div>
      <div>
        <span class="footer-title">联系我们</span>
        <a class="link link-hover">邮箱：info@example.com</a>
        <a class="link link-hover">电话：+123456789</a>
      </div>
      <div>
        <span class="footer-title">关注我们</span>
        <div class="grid grid-flow-col gap-4">
          <a>
            <Icon icon="fa:weixin"/>
          </a>
          <a>
            <Icon icon="fa:github"/>
          </a>
        </div>
      </div>
    </footer>
  </main>
</template>

<style scoped>

</style>
