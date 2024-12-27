<template>
  <a-layout style="min-height: 100vh">
    <!-- Header -->
    <a-layout-header style="background: #fff; padding: 0">
      <app-header />
    </a-layout-header>

    <!-- Main layout with sider and content -->
    <a-layout>
      <!-- Collapsible Sider with Controlled Open Keys for Nested Menu -->
      <a-layout-sider width="200px" collapsible style="background: #fff">
        <a-menu
          mode="inline"
          :openKeys="openKeys"
          @openChange="onOpenChange"
          style="height: 100%; border-right: 0"
        >
<!--          <a-sub-menu key="sub1" title="Menu 1">
            <a-menu-item key="1">Option 1</a-menu-item>
            <a-menu-item key="2">Option 2</a-menu-item>
            <a-sub-menu key="sub1-1" title="Nested Menu">
              <a-menu-item key="3">Option 3</a-menu-item>
              <a-menu-item key="4">Option 4</a-menu-item>
            </a-sub-menu>
          </a-sub-menu>
          <a-sub-menu key="sub2" title="Menu 2">
            <a-menu-item key="5">Option 5</a-menu-item>
            <a-menu-item key="6">Option 6</a-menu-item>
          </a-sub-menu>
-->>

	 
			
				<a-menu-item>
					<router-link to="/images">
						<span class="label">Images</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/keyWordPairs">
						<span class="label">Key Word Pairs</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/gameSessions">
						<span class="label">Game Sessions</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/players">
						<span class="label">Players</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/playerStatisticss">
						<span class="label">Player Statisticss</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/gameHistorys">
						<span class="label">Game Historys</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/leaderboards">
						<span class="label">Leaderboards</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/achievements">
						<span class="label">Achievements</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/feedbacks">
						<span class="label">Feedbacks</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/notifications">
						<span class="label">Notifications</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/challenges">
						<span class="label">Challenges</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/challengeCompletions">
						<span class="label">Challenge Completions</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/badges">
						<span class="label">Badges</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/userBadges">
						<span class="label">User Badges</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/gameSettingss">
						<span class="label">Game Settingss</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/userPreferencess">
						<span class="label">User Preferencess</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/inAppPurchases">
						<span class="label">In App Purchases</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/itemStores">
						<span class="label">Item Stores</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/playerInventorys">
						<span class="label">Player Inventorys</span>
					</router-link>
				</a-menu-item>
		
	
        </a-menu>
      </a-layout-sider>
      
      <!-- Content area -->
      <a-layout-content style="padding: 16px">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import { ref } from "vue";
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "DefaultLayout",
  components: {
    AppHeader,
  },
  setup() {
    const openKeys = ref(["sub1"]); // default open submenu
    const onOpenChange = (keys) => {
      // Open only the last clicked submenu, for single open behavior
      openKeys.value = keys.length ? [keys[keys.length - 1]] : [];
    };

    return {
      openKeys,
      onOpenChange,
    };
  },
};
</script>
<style>
.ant-layout {
    margin-top: 20px;
}
</style>

