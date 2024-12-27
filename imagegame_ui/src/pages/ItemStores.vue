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
            <itemStore-table
            v-if="itemStores && itemStores.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:itemStores="itemStores"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-item-stores="getAllItemStores"
             >

            </itemStore-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ItemStoreTable from "@/components/ItemStoreTable";
import ItemStoreService from "../services/ItemStoreService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ItemStoreTable,
  },
  data() {
    return {
      itemStores: [],
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
    async getAllItemStores(sortBy='itemStoreId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ItemStoreService.getAllItemStores(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.itemStores.length) {
					this.itemStores = response.data.itemStores;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching itemStores:", error);
        }
        
      } catch (error) {
        console.error("Error fetching itemStore details:", error);
      }
    },
  },
  mounted() {
    this.getAllItemStores();
  },
  created() {
    this.$root.$on('searchQueryForItemStoresChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllItemStores();
    })
  }
};
</script>
<style></style>
