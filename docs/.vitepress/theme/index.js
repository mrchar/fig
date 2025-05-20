import DefaultTheme from 'vitepress/theme'
import "./style.css"

const modules =
    import.meta.glob("../../components/**/*.vue", {eager: true})


/** @type {import('vitepress').Theme} */
export default {
    extends: DefaultTheme,
    enhanceApp({app}) {
        // 注册自定义全局组件
        Object.entries(modules).forEach(([path, module]) => {
            const name = path.split("/").pop().replace(/\.\w+$/, "")
            app.component(name, module.default || module)
        })
    }
}