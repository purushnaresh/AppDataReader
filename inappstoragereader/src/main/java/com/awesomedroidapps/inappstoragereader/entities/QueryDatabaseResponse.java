package com.awesomedroidapps.inappstoragereader.entities;

import com.awesomedroidapps.inappstoragereader.DatabaseQueryCommandType;
import com.awesomedroidapps.inappstoragereader.QueryStatus;

/**
 * Created by anshul on 6/3/17.
 * A class for encapsulating the response of a database query.
 */

public class QueryDatabaseResponse {

  private QueryStatus queryStatus;
  private TableDataResponse tableDataResponse;
  private String errorMessage;
  private String successMessage;
  private DatabaseQueryCommandType databaseQueryCommandType;
  private long affectedRows = -1;

  public QueryStatus getQueryStatus() {
    return queryStatus;
  }

  public void setQueryStatus(QueryStatus queryStatus) {
    this.queryStatus = queryStatus;
  }

  public TableDataResponse getTableDataResponse() {
    return tableDataResponse;
  }

  public void setTableDataResponse(
      TableDataResponse tableDataResponse) {
    this.tableDataResponse = tableDataResponse;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getSuccessMessage() {
    return successMessage;
  }

  public void setSuccessMessage(String successMessage) {
    this.successMessage = successMessage;
  }

  public DatabaseQueryCommandType getDatabaseQueryCommandType() {
    return databaseQueryCommandType;
  }

  public void setDatabaseQueryCommandType(
      DatabaseQueryCommandType databaseQueryCommandType) {
    this.databaseQueryCommandType = databaseQueryCommandType;
  }

  public long getAffectedRows() {
    return affectedRows;
  }

  public void setAffectedRows(long affectedRows) {
    this.affectedRows = affectedRows;
  }
}
