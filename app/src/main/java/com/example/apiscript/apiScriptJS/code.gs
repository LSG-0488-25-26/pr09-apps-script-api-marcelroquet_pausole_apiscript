
function doGet(e) {
  const apiKey = (e.parameter.apiKey || "").trim();
  const maxPrice = e.parameter.maxPrice;

  if (apiKey !== API_KEY) throw new Error("Unauthorized: API key incorrecta.");

  const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("laptop_price1");
  const data = sheet.getDataRange().getValues();
  const headers = data[0];
  const jsonData = [];

  for (let i = 1; i < data.length; i++) {
    let rowData = {};
    for (let j = 0; j < headers.length; j++) {
      rowData[headers[j]] = data[i][j];
    }

    if (maxPrice !== undefined && maxPrice !== "") {
      let preu = parseFloat(rowData["Price"]);
      if (preu < parseFloat(maxPrice)) {
        jsonData.push(rowData);
      }
    } else {
      jsonData.push(rowData);
    }
  }
  const response = {
    status: "ok",
    type: "laptop",
    data: jsonData
  };
  return ContentService
  .createTextOutput(JSON.stringify(response))
  .setMimeType(ContentService.MimeType.JSON);
}

function doPost(e) {
  const apiKey = (e.parameter.apiKey || "").trim();

  if (apiKey !== API_KEY) throw new Error("Unauthorized: API key incorrecta.");

  try {
    const body = JSON.parse(e.postData.contents);

    const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("laptop_price1");
    const headers = sheet.getDataRange().getValues()[0];

    const newRow =[];
    for (let j = 0; j < headers.length; j++) {
      let headerName = headers[j];
      newRow.push(body[headerName] !== undefined ? body[headerName] : "");
    }

    sheet.appendRow(newRow);

    const response = { status: "ok", message: "Portàtil inserit correctament" };
    return ContentService.createTextOutput(JSON.stringify(response))
                         .setMimeType(ContentService.MimeType.JSON);

  } catch (error) {
    const errorResponse = { status: "error", message: error.message };
    return ContentService.createTextOutput(JSON.stringify(errorResponse))
                         .setMimeType(ContentService.MimeType.JSON);
  }
}


const BASE_URL = PropertiesService.getScriptProperties().getProperty("BASE_URL");
const API_KEY = PropertiesService.getScriptProperties().getProperty("API_KEY");
/**
* Funció de test de la funció doGet
*/
function testGet() {
  const mockEventData = {
    parameter: {
      apiKey: API_KEY,
      type: "laptop",
      maxPrice: 1000
    }
  };
  Logger.log(BASE_URL);
  Logger.log("Test: Obtenir DADES");
  Logger.log(doGet(mockEventData).getContent());
}