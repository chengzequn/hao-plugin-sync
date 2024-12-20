import { definePlugin } from "@halo-dev/console-shared";
import HomeView from "./views/HomeView.vue";
import { IconPlug } from "@halo-dev/components";
import { markRaw } from "vue";

export default definePlugin({
  components: {},
  routes: [
    {
      parentName: "ToolsRoot", //父级菜单
      route: {
        path: "articleFetcher", // TodoList 的路由 path
        name: "ArticleFetcher",// 菜单标识名
        component: HomeView,
        meta: {
          title: "文章抓取器",//菜单页的浏览器 tab 标题
          searchable: true,
          menu: {
            name: "文章抓取器",// TODO 菜单显示名称
            group: "tool",// 所在组名
            icon: markRaw(IconPlug),
            priority: 0,
          },
        },
      },
    },
  ],
  extensionPoints: {},
});
