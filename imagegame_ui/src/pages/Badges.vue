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
            <badge-table
            v-if="badges && badges.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:badges="badges"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-badges="getAllBadges"
             >

            </badge-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import BadgeTable from "@/components/BadgeTable";
import BadgeService from "../services/BadgeService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    BadgeTable,
  },
  data() {
    return {
      badges: [],
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
    async getAllBadges(sortBy='badgeId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await BadgeService.getAllBadges(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.badges.length) {
					this.badges = response.data.badges;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching badges:", error);
        }
        
      } catch (error) {
        console.error("Error fetching badge details:", error);
      }
    },
  },
  mounted() {
    this.getAllBadges();
  },
  created() {
    this.$root.$on('searchQueryForBadgesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllBadges();
    })
  }
};
</script>
<style></style>
