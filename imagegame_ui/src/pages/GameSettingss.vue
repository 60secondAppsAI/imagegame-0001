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
            <gameSettings-table
            v-if="gameSettingss && gameSettingss.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:gameSettingss="gameSettingss"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-game-settingss="getAllGameSettingss"
             >

            </gameSettings-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GameSettingsTable from "@/components/GameSettingsTable";
import GameSettingsService from "../services/GameSettingsService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GameSettingsTable,
  },
  data() {
    return {
      gameSettingss: [],
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
    async getAllGameSettingss(sortBy='gameSettingsId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GameSettingsService.getAllGameSettingss(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.gameSettingss.length) {
					this.gameSettingss = response.data.gameSettingss;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching gameSettingss:", error);
        }
        
      } catch (error) {
        console.error("Error fetching gameSettings details:", error);
      }
    },
  },
  mounted() {
    this.getAllGameSettingss();
  },
  created() {
    this.$root.$on('searchQueryForGameSettingssChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGameSettingss();
    })
  }
};
</script>
<style></style>
