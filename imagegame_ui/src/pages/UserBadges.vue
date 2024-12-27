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
            <userBadge-table
            v-if="userBadges && userBadges.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:userBadges="userBadges"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-user-badges="getAllUserBadges"
             >

            </userBadge-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import UserBadgeTable from "@/components/UserBadgeTable";
import UserBadgeService from "../services/UserBadgeService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    UserBadgeTable,
  },
  data() {
    return {
      userBadges: [],
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
    async getAllUserBadges(sortBy='userBadgeId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await UserBadgeService.getAllUserBadges(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.userBadges.length) {
					this.userBadges = response.data.userBadges;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching userBadges:", error);
        }
        
      } catch (error) {
        console.error("Error fetching userBadge details:", error);
      }
    },
  },
  mounted() {
    this.getAllUserBadges();
  },
  created() {
    this.$root.$on('searchQueryForUserBadgesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllUserBadges();
    })
  }
};
</script>
<style></style>
