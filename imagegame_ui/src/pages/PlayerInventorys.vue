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
            <playerInventory-table
            v-if="playerInventorys && playerInventorys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:playerInventorys="playerInventorys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-player-inventorys="getAllPlayerInventorys"
             >

            </playerInventory-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PlayerInventoryTable from "@/components/PlayerInventoryTable";
import PlayerInventoryService from "../services/PlayerInventoryService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PlayerInventoryTable,
  },
  data() {
    return {
      playerInventorys: [],
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
    async getAllPlayerInventorys(sortBy='playerInventoryId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PlayerInventoryService.getAllPlayerInventorys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.playerInventorys.length) {
					this.playerInventorys = response.data.playerInventorys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching playerInventorys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching playerInventory details:", error);
      }
    },
  },
  mounted() {
    this.getAllPlayerInventorys();
  },
  created() {
    this.$root.$on('searchQueryForPlayerInventorysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPlayerInventorys();
    })
  }
};
</script>
<style></style>
