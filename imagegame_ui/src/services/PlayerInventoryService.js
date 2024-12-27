import http from "../http-common"; 

class PlayerInventoryService {
  getAllPlayerInventorys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/playerInventory/playerInventorys`, searchDTO);
  }

  get(playerInventoryId) {
    return this.getRequest(`/playerInventory/${playerInventoryId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/playerInventory?field=${matchData}`, null);
  }

  addPlayerInventory(data) {
    return http.post("/playerInventory/addPlayerInventory", data);
  }

  update(data) {
  	return http.post("/playerInventory/updatePlayerInventory", data);
  }
  
  uploadImage(data,playerInventoryId) {
  	return http.postForm("/playerInventory/uploadImage/"+playerInventoryId, data);
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

export default new PlayerInventoryService();
