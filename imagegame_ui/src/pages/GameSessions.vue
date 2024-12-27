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
            <gameSession-table
            v-if="gameSessions && gameSessions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:gameSessions="gameSessions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-game-sessions="getAllGameSessions"
             >

            </gameSession-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GameSessionTable from "@/components/GameSessionTable";
import GameSessionService from "../services/GameSessionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GameSessionTable,
  },
  data() {
    return {
      gameSessions: [],
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
    async getAllGameSessions(sortBy='gameSessionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GameSessionService.getAllGameSessions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.gameSessions.length) {
					this.gameSessions = response.data.gameSessions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching gameSessions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching gameSession details:", error);
      }
    },
  },
  mounted() {
    this.getAllGameSessions();
  },
  created() {
    this.$root.$on('searchQueryForGameSessionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGameSessions();
    })
  }
};
</script>
<style></style>
