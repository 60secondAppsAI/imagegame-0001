
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

              <h5 class="card-title">Item Stores</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalItemStores = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalItemStores">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add ItemStore</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="ItemStoreId" type="text" placeholder="Enter ItemStoreId" v-model="itemStoreToAdd.itemStoreId"></base-input>
  <base-input label="ItemName" type="text" placeholder="Enter ItemName" v-model="itemStoreToAdd.itemName"></base-input>
  <base-input label="ItemPrice" type="text" placeholder="Enter ItemPrice" v-model="itemStoreToAdd.itemPrice"></base-input>
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
            <a-table :columns="columns" :data-source="itemStores" :row-key="record => record.ItemStoreId" :pagination="pagination"
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
            <ItemStorePictureView :itemStores="itemStores" />
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
import ItemStoreService from "../services/ItemStoreService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import ItemStorePictureView from './ItemStorePictureView.vue';


const itemStoresColumns = [
  "itemStoreId",
  "year",
  "date",
  "competitionId",
  "itemStoreId"
]

export default {
  props: {
    itemStores: {
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
    ItemStorePictureView
  },

  data() {
    return {
      modalItemStores: false,
        isTableView: true,

      columns: [
        {
          title: 'Item Store Id',
		dataIndex: 'itemStoreId',
          visible: true,
          scopedSlots: { customRender: 'itemStoreId' },
          sorter: true
          //sorter: (a, b) => a.itemStoreId - b.itemStoreId,
          //sorter: (a, b) => a.itemStoreId.localeCompare(b.itemStoreId),
        },
        {
          title: 'Item Name',
		dataIndex: 'itemName',
          visible: true,
          scopedSlots: { customRender: 'itemName' },
          sorter: true
          //sorter: (a, b) => a.itemName - b.itemName,
          //sorter: (a, b) => a.itemName.localeCompare(b.itemName),
        },
        {
          title: 'Item Price',
		dataIndex: 'itemPrice',
          visible: true,
          scopedSlots: { customRender: 'itemPrice' },
          sorter: true
          //sorter: (a, b) => a.itemPrice - b.itemPrice,
          //sorter: (a, b) => a.itemPrice.localeCompare(b.itemPrice),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} itemStores`,
      },

      itemStores: [],
      itemStoreToAdd: {},

      itemStoresTable: {
        columns: [...itemStoresColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'itemStoreId',           // Column to sort by
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

    async renderItemStoresTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let itemStoresTableData = [];
      for (let i = 0; i < this.itemStores.length; i++) {
        itemStoresTableData.push({
          id: i,
          itemStoreId: this.itemStores[i].itemStoreId,
          year: this.itemStores[i].year,
          date: this.itemStores[i].date,
          competitionId: this.itemStores[i].competitionId,
          itemStoreId: this.itemStores[i].itemStoreId,
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

	  this.$emit('get-all-item-stores',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
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
    	this.$root.$emit('searchQueryForItemStoresChanged', this.searchQuery);
		//this.renderItemStoresTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalItemStores = false;

      const currentDate = new Date().getTime();
      this.itemStoreToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.itemStoreToAdd);
      console.log(jsonData);
      
      const res = await ItemStoreService.addItemStore(jsonData);

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
    this.renderItemStoresTable();
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
