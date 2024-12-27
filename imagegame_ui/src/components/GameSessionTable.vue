
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3 justify-content-end">
      <div class="col-8"></div>
       <div class="col-auto">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by BusinessUnit#, Location#, Operator#, City, State, FirstName, LastName…"
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Game Sessions</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalGameSessions = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalGameSessions">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add GameSession</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="GameSessionId" type="text" placeholder="Enter GameSessionId" v-model="gameSessionToAdd.gameSessionId"></base-input>
  <base-input label="PlayerId" type="text" placeholder="Enter PlayerId" v-model="gameSessionToAdd.playerId"></base-input>
  <base-input label="StartTime" type="text" placeholder="Enter StartTime" v-model="gameSessionToAdd.startTime"></base-input>
  <base-input label="EndTime" type="text" placeholder="Enter EndTime" v-model="gameSessionToAdd.endTime"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="gameSessions" :row-key="record => record.GameSessionId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <GameSessionPictureView :gameSessions="gameSessions" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import GameSessionService from "../services/GameSessionService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import GameSessionPictureView from './GameSessionPictureView.vue';


const gameSessionsColumns = [
  "gameSessionId",
  "year",
  "date",
  "competitionId",
  "gameSessionId"
]

export default {
  props: {
    gameSessions: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    GameSessionPictureView
  },

  data() {
    return {
      modalGameSessions: false,
        isTableView: true,

      columns: [
        {
          title: 'Game Session Id',
		dataIndex: 'gameSessionId',
          visible: true,
          scopedSlots: { customRender: 'gameSessionId' },
          sorter: true
          //sorter: (a, b) => a.gameSessionId - b.gameSessionId,
          //sorter: (a, b) => a.gameSessionId.localeCompare(b.gameSessionId),
        },
        {
          title: 'Player Id',
		dataIndex: 'playerId',
          visible: true,
          scopedSlots: { customRender: 'playerId' },
          sorter: true
          //sorter: (a, b) => a.playerId - b.playerId,
          //sorter: (a, b) => a.playerId.localeCompare(b.playerId),
        },
        {
          title: 'Start Time',
		dataIndex: 'startTime',
          visible: true,
          scopedSlots: { customRender: 'startTime' },
          sorter: true
          //sorter: (a, b) => a.startTime - b.startTime,
          //sorter: (a, b) => a.startTime.localeCompare(b.startTime),
        },
        {
          title: 'End Time',
		dataIndex: 'endTime',
          visible: true,
          scopedSlots: { customRender: 'endTime' },
          sorter: true
          //sorter: (a, b) => a.endTime - b.endTime,
          //sorter: (a, b) => a.endTime.localeCompare(b.endTime),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} gameSessions`,
      },

      gameSessions: [],
      gameSessionToAdd: {},

      gameSessionsTable: {
        columns: [...gameSessionsColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'gameSessionId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderGameSessionsTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let gameSessionsTableData = [];
      for (let i = 0; i < this.gameSessions.length; i++) {
        gameSessionsTableData.push({
          id: i,
          gameSessionId: this.gameSessions[i].gameSessionId,
          year: this.gameSessions[i].year,
          date: this.gameSessions[i].date,
          competitionId: this.gameSessions[i].competitionId,
          gameSessionId: this.gameSessions[i].gameSessionId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-game-sessions',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToImageDetail(id) {
      this.$router.push({ name: 'ImageDetail', params: { imageId: id.toString() }})
    },
    routingToKeyWordPairDetail(id) {
      this.$router.push({ name: 'KeyWordPairDetail', params: { keyWordPairId: id.toString() }})
    },
    routingToGameSessionDetail(id) {
      this.$router.push({ name: 'GameSessionDetail', params: { gameSessionId: id.toString() }})
    },
    routingToPlayerDetail(id) {
      this.$router.push({ name: 'PlayerDetail', params: { playerId: id.toString() }})
    },
    routingToPlayerStatisticsDetail(id) {
      this.$router.push({ name: 'PlayerStatisticsDetail', params: { playerStatisticsId: id.toString() }})
    },
    routingToGameHistoryDetail(id) {
      this.$router.push({ name: 'GameHistoryDetail', params: { gameHistoryId: id.toString() }})
    },
    routingToLeaderboardDetail(id) {
      this.$router.push({ name: 'LeaderboardDetail', params: { leaderboardId: id.toString() }})
    },
    routingToAchievementDetail(id) {
      this.$router.push({ name: 'AchievementDetail', params: { achievementId: id.toString() }})
    },
    routingToFeedbackDetail(id) {
      this.$router.push({ name: 'FeedbackDetail', params: { feedbackId: id.toString() }})
    },
    routingToNotificationDetail(id) {
      this.$router.push({ name: 'NotificationDetail', params: { notificationId: id.toString() }})
    },
    routingToChallengeDetail(id) {
      this.$router.push({ name: 'ChallengeDetail', params: { challengeId: id.toString() }})
    },
    routingToChallengeCompletionDetail(id) {
      this.$router.push({ name: 'ChallengeCompletionDetail', params: { challengeCompletionId: id.toString() }})
    },
    routingToBadgeDetail(id) {
      this.$router.push({ name: 'BadgeDetail', params: { badgeId: id.toString() }})
    },
    routingToUserBadgeDetail(id) {
      this.$router.push({ name: 'UserBadgeDetail', params: { userBadgeId: id.toString() }})
    },
    routingToGameSettingsDetail(id) {
      this.$router.push({ name: 'GameSettingsDetail', params: { gameSettingsId: id.toString() }})
    },
    routingToUserPreferencesDetail(id) {
      this.$router.push({ name: 'UserPreferencesDetail', params: { userPreferencesId: id.toString() }})
    },
    routingToInAppPurchaseDetail(id) {
      this.$router.push({ name: 'InAppPurchaseDetail', params: { inAppPurchaseId: id.toString() }})
    },
    routingToItemStoreDetail(id) {
      this.$router.push({ name: 'ItemStoreDetail', params: { itemStoreId: id.toString() }})
    },
    routingToPlayerInventoryDetail(id) {
      this.$router.push({ name: 'PlayerInventoryDetail', params: { playerInventoryId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForGameSessionsChanged', this.searchQuery);
		//this.renderGameSessionsTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalGameSessions = false;

      const currentDate = new Date().getTime();
      this.gameSessionToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.gameSessionToAdd);
      console.log(jsonData);
      
      const res = await GameSessionService.addGameSession(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderGameSessionsTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}
.header-search {
  width: 300px !important;
  margin-left: auto !important;
}
</style>
