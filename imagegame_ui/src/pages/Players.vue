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
            <player-table
            v-if="players && players.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:players="players"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-players="getAllPlayers"
             >

            </player-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PlayerTable from "@/components/PlayerTable";
import PlayerService from "../services/PlayerService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PlayerTable,
  },
  data() {
    return {
      players: [],
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
    async getAllPlayers(sortBy='playerId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PlayerService.getAllPlayers(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.players.length) {
					this.players = response.data.players;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching players:", error);
        }
        
      } catch (error) {
        console.error("Error fetching player details:", error);
      }
    },
  },
  mounted() {
    this.getAllPlayers();
  },
  created() {
    this.$root.$on('searchQueryForPlayersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPlayers();
    })
  }
};
</script>
<style></style>
