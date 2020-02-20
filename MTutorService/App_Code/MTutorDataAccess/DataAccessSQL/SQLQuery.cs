using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for SQLQuery
/// </summary>
namespace MTutorDataAccess
{
    public class SQLQuery
    {
        public string is_valid_reference_code = "SELECT * FROM ADVMGT_ADVERTISEMENT WHERE ADV_REF_CODE=@refCode";
        public string authentication = "SELECT * FROM MT_USER_ACCOUNT WHERE USER_NAME=@userName AND PASSWORD=@passWord";
        public string get_user_profile = @"SELECT UPROFILE.*,UACCNT.USER_NAME,UACCNT.PASSWORD,UACCNT.SECURITY_QUESTION,UACCNT.ANSWER,
                                        (SELECT COUNT(*) FROM MT_RECENT_ACTIVITY WHERE USER_ID='0001'AND REGISTERED_DATE BETWEEN (GETDATE()- 14)AND GETDATE()) AS ACTIVITY_COUNT, 
                                        (SELECT COUNT(*) FROM MT_RECENT_VIEWS WHERE USER_ID='0001' AND REGISTERED_DATE BETWEEN (GETDATE()- 14)AND GETDATE()) AS VIEWS_COUNT  FROM 
                                         MT_USER_PROFILE UPROFILE LEFT JOIN MT_USER_ACCOUNT UACCNT ON UPROFILE.USER_ID=UACCNT.USER_ID 
                                         WHERE UPROFILE.USER_ID=@userID";
        public string register_new_user = "INSERT INTO MT_USER_PROFILE VALUES (@userID,@firstName,@lastName,@emailID,@address,@mobileNo,@position,@workPlace,@facebook,@google,@linkedIn,@skype,@twitter,@profileImage)";
        public string create_new_user_account = "INSERT INTO MT_USER_ACCOUNT VALUES(@userID,@userName,@password,@seqQuestion,@answer,GETDATE(),@activeFlg)";
        public string get_max_memberID = "SELECT MAX(USER_ID) FROM MT_USER_PROFILE";
     
    }
}