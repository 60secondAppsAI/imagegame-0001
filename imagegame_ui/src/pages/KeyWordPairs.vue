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
            <keyWordPair-table
            v-if="keyWordPairs && keyWordPairs.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:keyWordPairs="keyWordPairs"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-key-word-pairs="getAllKeyWordPairs"
             >

            </keyWordPair-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import KeyWordPairTable from "@/components/KeyWordPairTable";
import KeyWordPairService from "../services/KeyWordPairService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    KeyWordPairTable,
  },
  data() {
    return {
      keyWordPairs: [],
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
    async getAllKeyWordPairs(sortBy='keyWordPairId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await KeyWordPairService.getAllKeyWordPairs(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.keyWordPairs.length) {
					this.keyWordPairs = response.data.keyWordPairs;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching keyWordPairs:", error);
        }
        
      } catch (error) {
        console.error("Error fetching keyWordPair details:", error);
      }
    },
  },
  mounted() {
    this.getAllKeyWordPairs();
  },
  created() {
    this.$root.$on('searchQueryForKeyWordPairsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllKeyWordPairs();
    })
  }
};
</script>
<style></style>
