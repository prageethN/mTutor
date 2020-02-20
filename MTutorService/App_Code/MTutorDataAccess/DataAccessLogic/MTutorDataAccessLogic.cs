using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;

/// <summary>
/// Summary description for MTutorDataAccessLogic
/// </summary>
namespace MTutorDataAccess
{
    public class MTutorDataAccessLogic : MTutorDataAccessBase
    {
        static  MTutorDataAccessBase BaseClass;

       public static DataTable isValidReferenceCode(string refCode)
        {

            BaseClass = new MTutorDataAccessBase();

            string sSQL = SQL_QUERY.is_valid_reference_code;
            SqlParameter[] sqlParams = new SqlParameter[1];
            SqlCommand sqlCmnd;
            sqlParams[0] = BaseClass.getSQLParameter("@refCode", DbType.String, refCode);
            sqlCmnd = BaseClass.getSQLCommand(sSQL, sqlParams);
            return BaseClass.getQueryResultset(sqlCmnd).Tables[0];
        }
       public static DataTable AuthenticateUser(string _userName, string _passWord)
       {
           BaseClass = new MTutorDataAccessBase();

           string sSQL = SQL_QUERY.authentication;

           SqlParameter[] arrParams = new SqlParameter[2];
           SqlCommand cmd;
           arrParams[0] = BaseClass.getSQLParameter("@userName", DbType.String, _userName);
           arrParams[1] = BaseClass.getSQLParameter("@passWord", DbType.String, _passWord);

           cmd = BaseClass.getSQLCommand(sSQL, arrParams);
           return BaseClass.getQueryResultset(cmd).Tables[0];
       }
       public static DataTable getUserProfile(string _userid)
       {
           BaseClass = new MTutorDataAccessBase();

           string sSQL = SQL_QUERY.get_user_profile;

           SqlParameter[] arrParams = new SqlParameter[1];
           SqlCommand cmd;
           arrParams[0] = BaseClass.getSQLParameter("@userID", DbType.String, _userid);
           cmd = BaseClass.getSQLCommand(sSQL, arrParams);
           return BaseClass.getQueryResultset(cmd).Tables[0];
       }
       public static DataTable getMaxUserID()
       {
           BaseClass = new MTutorDataAccessBase();

           string sSQL = SQL_QUERY.get_max_memberID;

           SqlParameter[] arrParams = new SqlParameter[0];
           SqlCommand cmd;
           //arrParams[0] = _dbHelper.getParameter("@id", DbType.Int64, EmpID);
           cmd = BaseClass.getSQLCommand(sSQL, arrParams);
           return BaseClass.getQueryResultset(cmd).Tables[0];
       }
       public static void registerMember(String _profileImageURL,String _userID, String _Position, String _workPlace, String _firstName, String _lastName, String _emailID, String _address, String _mobileNo, String _facebook, String _google, String _linkedin, String _skype, String _twitter, String _username, String _password, String _securityQ, String _answer)
       {
           BaseClass = new MTutorDataAccessBase();

           string sSQL_1 = SQL_QUERY.register_new_user;
           string sSQL_2 = SQL_QUERY.create_new_user_account;
         
           SqlParameter[] arrParams = new SqlParameter[14];
           SqlParameter[] arrParams2 = new SqlParameter[6];
           SqlCommand cmd1,cmd2;
       

           arrParams[0] = BaseClass.getSQLParameter("@userID", DbType.String, _userID);
           arrParams[1] = BaseClass.getSQLParameter("@firstName", DbType.String, _firstName);
           arrParams[2] = BaseClass.getSQLParameter("@lastName", DbType.String, _lastName);
           arrParams[3] = BaseClass.getSQLParameter("@position", DbType.String, _Position);
           arrParams[4] = BaseClass.getSQLParameter("@workPlace", DbType.String, _workPlace);
           arrParams[5] = BaseClass.getSQLParameter("@emailID", DbType.String, _emailID);
           arrParams[6] = BaseClass.getSQLParameter("@address", DbType.String, _address);
           arrParams[7] = BaseClass.getSQLParameter("@mobileNo", DbType.String, _mobileNo);
           arrParams[8] = BaseClass.getSQLParameter("@facebook", DbType.String, _facebook);
           arrParams[9] = BaseClass.getSQLParameter("@google", DbType.String, _google);
           arrParams[10] = BaseClass.getSQLParameter("@linkedIn", DbType.String, _linkedin);
           arrParams[11] = BaseClass.getSQLParameter("@skype", DbType.String, _skype);
           arrParams[12] = BaseClass.getSQLParameter("@twitter", DbType.String, _twitter);
           arrParams[13] = BaseClass.getSQLParameter("@profileImage", DbType.String, _profileImageURL);
           cmd1 = BaseClass.getSQLCommand(sSQL_1, arrParams);
           BaseClass.executeSQLQuery(cmd1);

           arrParams2[0] = BaseClass.getSQLParameter("@userID", DbType.String, _userID);
           arrParams2[1] = BaseClass.getSQLParameter("@userName", DbType.String, _username);
           arrParams2[2] = BaseClass.getSQLParameter("@password", DbType.String, _password);
           arrParams2[3] = BaseClass.getSQLParameter("@seqQuestion", DbType.Int32, _securityQ);
           arrParams2[4] = BaseClass.getSQLParameter("@answer", DbType.String, _answer);
           arrParams2[5] = BaseClass.getSQLParameter("@activeFlg", DbType.Int32, "1");
           cmd2 = BaseClass.getSQLCommand(sSQL_2, arrParams2);
           BaseClass.executeSQLQuery(cmd2);

       }

     
    }
   
}

