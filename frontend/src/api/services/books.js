import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/books", { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/books", book, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  edit(book) {
    return HTTP.patch(BASE_URL + "/books", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};