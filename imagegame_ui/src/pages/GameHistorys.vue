<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <gameHistory-table
            v-if="gameHistorys && gameHistorys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:gameHistorys="gameHistorys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-game-historys="getAllGameHistorys"
             >

            </gameHistory-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GameHistoryTable from "@/components/GameHistoryTable";
import GameHistoryService from "../services/GameHistoryService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GameHistoryTable,
  },
  data() {
    return {
      gameHistorys: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllGameHistorys(sortBy='gameHistoryId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GameHistoryService.getAllGameHistorys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.gameHistorys.length) {
					this.gameHistorys = response.data.gameHistorys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching gameHistorys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching gameHistory details:", error);
      }
    },
  },
  mounted() {
    this.getAllGameHistorys();
  },
  created() {
    this.$root.$on('searchQueryForGameHistorysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGameHistorys();
    })
  }
};
</script>
<style></style>
