using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MTutorDataAccess;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Runtime.InteropServices;

/// <summary>
/// Summary description for MTutorBusinessLogic
/// </summary>
namespace MTutoreBusinessLogic
{

    public class MTutorBusinessLogic : MTutorDataAccessLogic
    {
        public MTutorBusinessLogic()
        {
            //
            // TODO: Add constructor logic here
            //
        }
        public static Boolean isValidReferenceCode(string refCode)
        {
            DataTable dt = MTutorDataAccessLogic.isValidReferenceCode(refCode);
            if (dt.Rows.Count > 0)
            {
                return true;
            }
            else
            {

                return false;
            }

        }
        public static String AuthenticateUser(string _userName, string _passWord)
        {


            DataTable dt = MTutorDataAccessLogic.AuthenticateUser(_userName, _passWord);
            if (dt.Rows.Count > 0)
            {
                return dt.Rows[0][0].ToString();
            }
            else
            {
                return "error";
            }

        }
        public static string[] getUserProfile(string _userid)
        {

            DataTable dt = MTutorDataAccessLogic.getUserProfile(_userid);

            int count = 0,maxLength=dt.Columns.Count;
            string[] arrProfile = new string[maxLength];

            
            while (count < arrProfile.Length)
            {
              
                arrProfile[count] = dt.Rows[0][count].ToString();                
                count++;
            }

            return arrProfile;

        }
        public static string getMaxUserID()
        {


            DataTable dt = MTutorDataAccessLogic.getMaxUserID();
            if (dt.Rows.Count > 0)
            {
                int val = Int16.Parse(dt.Rows[0][0].ToString());
                val++;
                return val.ToString().PadLeft(4,'0');
            }
            else
            {

                return "0001";
            }

        }
        public static Boolean registerMember(String _userImage, String _userID, String _Position, String _workPlace, String _firstName, String _lastName, String _emailID, String _address, String _mobileNo, String _facebook, String _google, String _linkedin, String _skype, String _twitter, String _username, String _password, String _securityQ, String _answer)
        {

            MTutorDataAccessLogic.registerMember(_userImage, _userID, _Position, _workPlace, _firstName, _lastName, _emailID, _address, _mobileNo, _facebook, _google, _linkedin, _skype, _twitter, _username, _password, _securityQ, _answer);
            return true;
        }



       
    }
     
}


