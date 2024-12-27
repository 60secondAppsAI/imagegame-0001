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
            <challengeCompletion-table
            v-if="challengeCompletions && challengeCompletions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:challengeCompletions="challengeCompletions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-challenge-completions="getAllChallengeCompletions"
             >

            </challengeCompletion-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ChallengeCompletionTable from "@/components/ChallengeCompletionTable";
import ChallengeCompletionService from "../services/ChallengeCompletionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ChallengeCompletionTable,
  },
  data() {
    return {
      challengeCompletions: [],
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
    async getAllChallengeCompletions(sortBy='challengeCompletionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ChallengeCompletionService.getAllChallengeCompletions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.challengeCompletions.length) {
					this.challengeCompletions = response.data.challengeCompletions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching challengeCompletions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching challengeCompletion details:", error);
      }
    },
  },
  mounted() {
    this.getAllChallengeCompletions();
  },
  created() {
    this.$root.$on('searchQueryForChallengeCompletionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllChallengeCompletions();
    })
  }
};
</script>
<style></style>
