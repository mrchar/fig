import {defineConfig} from 'vitepress'
import {withMermaid} from "vitepress-plugin-mermaid"
import tailwindcss from '@tailwindcss/vite'


// https://vitepress.dev/reference/site-config
export default withMermaid(defineConfig({
    title: "无花果",
    description: "无花果低代码平台",
    themeConfig: {
        // https://vitepress.dev/reference/default-theme-config
        nav: [
            {text: '首页', link: '/'},
            {text: '用户手册', link: '/user-guide'},
            {text: '开发文档', link: '/development'}
        ],

        sidebar: [
            {text: '需求文档', link: '/requirement'},
            {text: '产品设计', link: '/design'},
            {text: '用户手册', link: '/user-guide'},
            {text: '开发文档', link: '/development'}
        ],

        socialLinks: [
            {icon: 'github', link: 'https://github.com/mrchar/fig'}
        ]
    },
    vite: {
        plugins: [tailwindcss()]
    }
}))
