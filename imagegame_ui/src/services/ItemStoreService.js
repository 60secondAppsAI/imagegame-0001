import http from "../http-common"; 

class ItemStoreService {
  getAllItemStores(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/itemStore/itemStores`, searchDTO);
  }

  get(itemStoreId) {
    return this.getRequest(`/itemStore/${itemStoreId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/itemStore?field=${matchData}`, null);
  }

  addItemStore(data) {
    return http.post("/itemStore/addItemStore", data);
  }

  update(data) {
  	return http.post("/itemStore/updateItemStore", data);
  }
  
  uploadImage(data,itemStoreId) {
  	return http.postForm("/itemStore/uploadImage/"+itemStoreId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new ItemStoreService();
