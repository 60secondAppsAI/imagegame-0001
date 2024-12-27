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
            <achievement-table
            v-if="achievements && achievements.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:achievements="achievements"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-achievements="getAllAchievements"
             >

            </achievement-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import AchievementTable from "@/components/AchievementTable";
import AchievementService from "../services/AchievementService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    AchievementTable,
  },
  data() {
    return {
      achievements: [],
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
    async getAllAchievements(sortBy='achievementId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await AchievementService.getAllAchievements(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.achievements.length) {
					this.achievements = response.data.achievements;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching achievements:", error);
        }
        
      } catch (error) {
        console.error("Error fetching achievement details:", error);
      }
    },
  },
  mounted() {
    this.getAllAchievements();
  },
  created() {
    this.$root.$on('searchQueryForAchievementsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllAchievements();
    })
  }
};
</script>
<style></style>
