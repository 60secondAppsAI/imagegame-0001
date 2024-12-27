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
            <inAppPurchase-table
            v-if="inAppPurchases && inAppPurchases.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:inAppPurchases="inAppPurchases"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-in-app-purchases="getAllInAppPurchases"
             >

            </inAppPurchase-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import InAppPurchaseTable from "@/components/InAppPurchaseTable";
import InAppPurchaseService from "../services/InAppPurchaseService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    InAppPurchaseTable,
  },
  data() {
    return {
      inAppPurchases: [],
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
    async getAllInAppPurchases(sortBy='inAppPurchaseId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await InAppPurchaseService.getAllInAppPurchases(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.inAppPurchases.length) {
					this.inAppPurchases = response.data.inAppPurchases;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching inAppPurchases:", error);
        }
        
      } catch (error) {
        console.error("Error fetching inAppPurchase details:", error);
      }
    },
  },
  mounted() {
    this.getAllInAppPurchases();
  },
  created() {
    this.$root.$on('searchQueryForInAppPurchasesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllInAppPurchases();
    })
  }
};
</script>
<style></style>
