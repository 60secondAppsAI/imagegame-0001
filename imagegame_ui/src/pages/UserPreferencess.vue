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
            <userPreferences-table
            v-if="userPreferencess && userPreferencess.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:userPreferencess="userPreferencess"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-user-preferencess="getAllUserPreferencess"
             >

            </userPreferences-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import UserPreferencesTable from "@/components/UserPreferencesTable";
import UserPreferencesService from "../services/UserPreferencesService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    UserPreferencesTable,
  },
  data() {
    return {
      userPreferencess: [],
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
    async getAllUserPreferencess(sortBy='userPreferencesId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await UserPreferencesService.getAllUserPreferencess(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.userPreferencess.length) {
					this.userPreferencess = response.data.userPreferencess;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching userPreferencess:", error);
        }
        
      } catch (error) {
        console.error("Error fetching userPreferences details:", error);
      }
    },
  },
  mounted() {
    this.getAllUserPreferencess();
  },
  created() {
    this.$root.$on('searchQueryForUserPreferencessChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllUserPreferencess();
    })
  }
};
</script>
<style></style>
