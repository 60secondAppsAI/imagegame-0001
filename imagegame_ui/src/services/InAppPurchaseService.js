import http from "../http-common"; 

class InAppPurchaseService {
  getAllInAppPurchases(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/inAppPurchase/inAppPurchases`, searchDTO);
  }

  get(inAppPurchaseId) {
    return this.getRequest(`/inAppPurchase/${inAppPurchaseId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/inAppPurchase?field=${matchData}`, null);
  }

  addInAppPurchase(data) {
    return http.post("/inAppPurchase/addInAppPurchase", data);
  }

  update(data) {
  	return http.post("/inAppPurchase/updateInAppPurchase", data);
  }
  
  uploadImage(data,inAppPurchaseId) {
  	return http.postForm("/inAppPurchase/uploadImage/"+inAppPurchaseId, data);
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

export default new InAppPurchaseService();
