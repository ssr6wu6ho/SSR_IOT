/// <reference types="vite/client" />
//ts识别不了vue文件，会提示找不到模块“./App.vue”。。。
declare module "*.vue" {
    import type { DefineComponent } from "vue";
    const vueComponent: DefineComponent<{}, {}, any>;
    export default vueComponent;
}