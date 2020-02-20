using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using MTutoreBusinessLogic;

[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]

public class MTutorService : System.Web.Services.WebService
{
    public MTutorService()
    {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }

    [WebMethod]
    public Boolean isValidReferenceCode(string _refCode)
    {
        return MTutorBusinessLogic.isValidReferenceCode(_refCode);
    }
    [WebMethod]
    public String authenticateUser(string _userName, string _passWord)
    {
        return MTutorBusinessLogic.AuthenticateUser(_userName, _passWord);
    }
    [WebMethod]
    public String[] getUserprofile(String _userid)             
    {
        return MTutorBusinessLogic.getUserProfile(_userid);
    }
    [WebMethod]
    public String getMaxUserID()
    {
        return MTutorBusinessLogic.getMaxUserID();
    }
   
    [WebMethod]
    public Boolean registerNewUser(String _userImage, String _userID, String _Position, String _workPlace, String _firstName, String _lastName, String _emailID, String _address, String _mobileNo, String _facebook, String _google, String _linkedin, String _skype, String _twitter, String _username, String _password, String _securityQ, String _answer)
    {
        string profileImageURL=storeProfileImage(_userImage, _userID);
        //string profileImageURL="www.mtutor.com/img.jpg";
        return MTutorBusinessLogic.registerMember(profileImageURL, _userID, _Position, _workPlace, _firstName, _lastName, _emailID, _address, _mobileNo, _facebook, _google, _linkedin, _skype, _twitter, _username, _password, _securityQ, _answer);
    
    }

  
    String storeProfileImage(String _userImage, String _userID)
        {
            string profileImgURL = "";
            if (_userImage!="") { 

            string sSavePath = "App_Resources/";
            System.Text.UTF8Encoding encoder = new System.Text.UTF8Encoding();
            System.Text.Decoder utf8Decode = encoder.GetDecoder();
            byte[] ret = Convert.FromBase64String(_userImage);

            // Save the stream to disk
            System.IO.FileStream newFile = new System.IO.FileStream(Server.MapPath(sSavePath + _userID + ".jpg"), System.IO.FileMode.Create);
            newFile.Write(ret, 0, ret.Length);
            newFile.Close();
            profileImgURL = "http://192.168.69.1/MTutorService/App_Resources/" + _userID + ".jpg";
            }
            return profileImgURL;

        }
}