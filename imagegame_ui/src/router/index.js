import Vue from "vue";
import VueRouter from "vue-router";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import Images from  '@/pages/Images.vue';
import ImageDetail from  '@/pages/ImageDetail.vue';
import KeyWordPairs from  '@/pages/KeyWordPairs.vue';
import KeyWordPairDetail from  '@/pages/KeyWordPairDetail.vue';
import GameSessions from  '@/pages/GameSessions.vue';
import GameSessionDetail from  '@/pages/GameSessionDetail.vue';
import Players from  '@/pages/Players.vue';
import PlayerDetail from  '@/pages/PlayerDetail.vue';
import PlayerStatisticss from  '@/pages/PlayerStatisticss.vue';
import PlayerStatisticsDetail from  '@/pages/PlayerStatisticsDetail.vue';
import GameHistorys from  '@/pages/GameHistorys.vue';
import GameHistoryDetail from  '@/pages/GameHistoryDetail.vue';
import Leaderboards from  '@/pages/Leaderboards.vue';
import LeaderboardDetail from  '@/pages/LeaderboardDetail.vue';
import Achievements from  '@/pages/Achievements.vue';
import AchievementDetail from  '@/pages/AchievementDetail.vue';
import Feedbacks from  '@/pages/Feedbacks.vue';
import FeedbackDetail from  '@/pages/FeedbackDetail.vue';
import Notifications from  '@/pages/Notifications.vue';
import NotificationDetail from  '@/pages/NotificationDetail.vue';
import Challenges from  '@/pages/Challenges.vue';
import ChallengeDetail from  '@/pages/ChallengeDetail.vue';
import ChallengeCompletions from  '@/pages/ChallengeCompletions.vue';
import ChallengeCompletionDetail from  '@/pages/ChallengeCompletionDetail.vue';
import Badges from  '@/pages/Badges.vue';
import BadgeDetail from  '@/pages/BadgeDetail.vue';
import UserBadges from  '@/pages/UserBadges.vue';
import UserBadgeDetail from  '@/pages/UserBadgeDetail.vue';
import GameSettingss from  '@/pages/GameSettingss.vue';
import GameSettingsDetail from  '@/pages/GameSettingsDetail.vue';
import UserPreferencess from  '@/pages/UserPreferencess.vue';
import UserPreferencesDetail from  '@/pages/UserPreferencesDetail.vue';
import InAppPurchases from  '@/pages/InAppPurchases.vue';
import InAppPurchaseDetail from  '@/pages/InAppPurchaseDetail.vue';
import ItemStores from  '@/pages/ItemStores.vue';
import ItemStoreDetail from  '@/pages/ItemStoreDetail.vue';
import PlayerInventorys from  '@/pages/PlayerInventorys.vue';
import PlayerInventoryDetail from  '@/pages/PlayerInventoryDetail.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../views/HomeView.vue"),
			redirect: '/images',
																			  },
  {
    path: "/pricing",
    name: "PricingView",
    component: () => import("../views/PricingView.vue"),
  },
  {
    path: "/arts-gallery",
    name: "ArtsGalleryView",
    component: () => import("../views/ArtsGalleryView.vue"),
  },
  {
    path: "/checkout/:id",
    name: "CheckoutView",
    component: () => import("../views/CheckoutView.vue"),
  },
  {
    path: "/stripe-checkout",
    name: "StripeCheckoutView",
    component: () => import("../views/StripeCheckoutView.vue"),
  },
	{
		path: '/images',
		name: 'Images',
		layout: DefaultLayout,
		component: Images,
	},
	{
	    path: '/image/:imageId', 
	    name: 'ImageDetail',
		layout: DefaultLayout,
	    component: ImageDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/keyWordPairs',
		name: 'KeyWordPairs',
		layout: DefaultLayout,
		component: KeyWordPairs,
	},
	{
	    path: '/keyWordPair/:keyWordPairId', 
	    name: 'KeyWordPairDetail',
		layout: DefaultLayout,
	    component: KeyWordPairDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/gameSessions',
		name: 'GameSessions',
		layout: DefaultLayout,
		component: GameSessions,
	},
	{
	    path: '/gameSession/:gameSessionId', 
	    name: 'GameSessionDetail',
		layout: DefaultLayout,
	    component: GameSessionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/players',
		name: 'Players',
		layout: DefaultLayout,
		component: Players,
	},
	{
	    path: '/player/:playerId', 
	    name: 'PlayerDetail',
		layout: DefaultLayout,
	    component: PlayerDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/playerStatisticss',
		name: 'PlayerStatisticss',
		layout: DefaultLayout,
		component: PlayerStatisticss,
	},
	{
	    path: '/playerStatistics/:playerStatisticsId', 
	    name: 'PlayerStatisticsDetail',
		layout: DefaultLayout,
	    component: PlayerStatisticsDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/gameHistorys',
		name: 'GameHistorys',
		layout: DefaultLayout,
		component: GameHistorys,
	},
	{
	    path: '/gameHistory/:gameHistoryId', 
	    name: 'GameHistoryDetail',
		layout: DefaultLayout,
	    component: GameHistoryDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/leaderboards',
		name: 'Leaderboards',
		layout: DefaultLayout,
		component: Leaderboards,
	},
	{
	    path: '/leaderboard/:leaderboardId', 
	    name: 'LeaderboardDetail',
		layout: DefaultLayout,
	    component: LeaderboardDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/achievements',
		name: 'Achievements',
		layout: DefaultLayout,
		component: Achievements,
	},
	{
	    path: '/achievement/:achievementId', 
	    name: 'AchievementDetail',
		layout: DefaultLayout,
	    component: AchievementDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/feedbacks',
		name: 'Feedbacks',
		layout: DefaultLayout,
		component: Feedbacks,
	},
	{
	    path: '/feedback/:feedbackId', 
	    name: 'FeedbackDetail',
		layout: DefaultLayout,
	    component: FeedbackDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/notifications',
		name: 'Notifications',
		layout: DefaultLayout,
		component: Notifications,
	},
	{
	    path: '/notification/:notificationId', 
	    name: 'NotificationDetail',
		layout: DefaultLayout,
	    component: NotificationDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/challenges',
		name: 'Challenges',
		layout: DefaultLayout,
		component: Challenges,
	},
	{
	    path: '/challenge/:challengeId', 
	    name: 'ChallengeDetail',
		layout: DefaultLayout,
	    component: ChallengeDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/challengeCompletions',
		name: 'ChallengeCompletions',
		layout: DefaultLayout,
		component: ChallengeCompletions,
	},
	{
	    path: '/challengeCompletion/:challengeCompletionId', 
	    name: 'ChallengeCompletionDetail',
		layout: DefaultLayout,
	    component: ChallengeCompletionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/badges',
		name: 'Badges',
		layout: DefaultLayout,
		component: Badges,
	},
	{
	    path: '/badge/:badgeId', 
	    name: 'BadgeDetail',
		layout: DefaultLayout,
	    component: BadgeDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/userBadges',
		name: 'UserBadges',
		layout: DefaultLayout,
		component: UserBadges,
	},
	{
	    path: '/userBadge/:userBadgeId', 
	    name: 'UserBadgeDetail',
		layout: DefaultLayout,
	    component: UserBadgeDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/gameSettingss',
		name: 'GameSettingss',
		layout: DefaultLayout,
		component: GameSettingss,
	},
	{
	    path: '/gameSettings/:gameSettingsId', 
	    name: 'GameSettingsDetail',
		layout: DefaultLayout,
	    component: GameSettingsDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/userPreferencess',
		name: 'UserPreferencess',
		layout: DefaultLayout,
		component: UserPreferencess,
	},
	{
	    path: '/userPreferences/:userPreferencesId', 
	    name: 'UserPreferencesDetail',
		layout: DefaultLayout,
	    component: UserPreferencesDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/inAppPurchases',
		name: 'InAppPurchases',
		layout: DefaultLayout,
		component: InAppPurchases,
	},
	{
	    path: '/inAppPurchase/:inAppPurchaseId', 
	    name: 'InAppPurchaseDetail',
		layout: DefaultLayout,
	    component: InAppPurchaseDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/itemStores',
		name: 'ItemStores',
		layout: DefaultLayout,
		component: ItemStores,
	},
	{
	    path: '/itemStore/:itemStoreId', 
	    name: 'ItemStoreDetail',
		layout: DefaultLayout,
	    component: ItemStoreDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/playerInventorys',
		name: 'PlayerInventorys',
		layout: DefaultLayout,
		component: PlayerInventorys,
	},
	{
	    path: '/playerInventory/:playerInventoryId', 
	    name: 'PlayerInventoryDetail',
		layout: DefaultLayout,
	    component: PlayerInventoryDetail,
	    props: true // Pass route params as props to the component
  	},
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes,
});

export default router;
