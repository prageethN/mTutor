using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;

/// <summary>
/// Summary description for MTutorDataAccessBase
/// </summary>
namespace MTutorDataAccess
{
    public class MTutorDataAccessBase
    {
        public static SqlConnection SQLConnection;
        public static SQLQuery SQL_QUERY;
        
        public MTutorDataAccessBase()
        {
            setSQLConnection(); // Connection will be initialize at the begining of the objwct creation
            getSQLQueryObject();
            
        }

        //=================================================================================================
        public DataSet getQueryResultset(SqlCommand sqlCmd)
        {
            SqlDataAdapter sqlDataAdap;;
            DataSet dataSet;
            dataSet = new DataSet();
            sqlDataAdap = new SqlDataAdapter(sqlCmd);
            sqlDataAdap.Fill(dataSet);

            return dataSet;
        }

        public void executeSQLQuery(SqlCommand sqlCmd)
        {
            try
            {

                sqlCmd.Connection = getSQLConnection();
                sqlCmd.ExecuteNonQuery();
                SQLConnection.Close();
            }
            catch (Exception ex)
            {
                int x = 0;
            }


        } 
       
        //=================================================================================================

        public SqlParameter getSQLParameter(string parameterName, DbType parameterType, object parameterValue)
        {


            SqlParameter paramSQL = new SqlParameter();
            paramSQL.ParameterName = parameterName;
            paramSQL.DbType = parameterType;
            paramSQL.Value = parameterValue;
            return paramSQL;

        }

        public SqlCommand getSQLCommand(string sqlQuery, SqlParameter[] parameterValues)
        {
            int count;
            SqlCommand commandSQL = new SqlCommand();
            commandSQL.Connection = SQLConnection;
            commandSQL.CommandText = sqlQuery;
            for (count = 0; count < parameterValues.Length; count++)
            {
                commandSQL.Parameters.Add(parameterValues[count]);
            }
            return commandSQL;
        }
        
        
        
        //=================================================================================================
      
        /*This function will create a SQLConnection object to the specified DB 
          [ Only if a connection object is no exists, otherwise existing object will be used]
        */
        private static void setSQLConnection()
        { 
            if (SQLConnection == null)
            {
                SQLConnection = new SqlConnection();
                SQLConnection.ConnectionString = "Persist Security Info=False;User ID=prageeth;Password=prageeth;Initial Catalog=DB_MTUTOR;Server=Prageeth-PC";

            }

        }
        /*This function will return the SQLConnection object
         [ If connection object's state is not open this will set status to open]
       */
        public static SqlConnection getSQLConnection()
        { 
            setSQLConnection();
            if (SQLConnection.State != ConnectionState.Open)
            {
                SQLConnection.Open();
            }
            return SQLConnection;
        }

        public static void getSQLQueryObject(){

            if (SQL_QUERY==null) {

                SQL_QUERY = new SQLQuery();
            }
            
        }
      
    }
}