package com.mtutor.servicecall;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.util.Log;

public class WebServiceCaller {
	private final String NAMESPACE = "http://tempuri.org/";
	// private final String URL = "http://10.0.2.2:50761/Service1.asmx?WSDL";
	private final String URL = "http://192.168.69.1/MTutorService/MTutorService.asmx?WSDL";
	private final String URL_MN = "http://192.168.69.1/MTutorMinningService/MTutorMinningService.asmx?WSDL";
	
	public String authenticateUser(String userName, String passWord) {
		String result = "";

		final String SOAP_ACTION = "http://tempuri.org/authenticateUser";
		final String METHOD_NAME = "authenticateUser";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userName");
		propInfo1.setValue(userName);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_passWord");
		propInfo2.setValue(passWord);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2); 

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (!response.toString().equalsIgnoreCase("error")) {
				result = response.toString();
			} else {
				result = "error";

			}
		} catch (Exception e) {

			e.printStackTrace();

		}
		return result;
	}

	public Boolean isValidReferenceCode(String refCode) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/isValidReferenceCode";
		final String METHOD_NAME = "isValidReferenceCode";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_refCode");
		propInfo1.setValue(refCode);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getMaxUserID() {

		String result = null;

		final String SOAP_ACTION = "http://tempuri.org/getMaxUserID";
		final String METHOD_NAME = "getMaxUserID";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());

			if (!response.equals("")) {
				result = response.toString();

			} else {

				result = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Boolean registerNewUser(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/registerNewUser";
		final String METHOD_NAME = "registerNewUser";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_userImage", "_userID", "_Position",
				"_workPlace", "_firstName", "_lastName", "_emailID",
				"_address", "_mobileNo", "_facebook", "_google", "_linkedin",
				"_skype", "_twitter", "_username", "_password", "_securityQ",
				"_answer" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String[] getUserprofile(String userID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserprofile";
		final String METHOD_NAME = "getUserprofile";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userid");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<ArrayList> getDocumentSearchResult(String qString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getDocumentSearchResult";
		final String METHOD_NAME = "getDocumentSearchResult";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_searchQuery");
		propInfo1.setValue(qString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public String[] getDocumentInfo(String docID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getDocumentInfo";
		final String METHOD_NAME = "getDocumentInfo";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_docID");
		propInfo1.setValue(docID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String[] getDocumentInfoQR(String qrCode) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getDocumentInfoQR";
		final String METHOD_NAME = "getDocumentInfoQR";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_qrCode");
		propInfo1.setValue(qrCode);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String[] getAurthorProfile(String aurthorID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getAurthorProfile";
		final String METHOD_NAME = "getAurthorProfile";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_aurthorID");
		propInfo1.setValue(aurthorID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String[] getDocumentRating(String docID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getDocumentRating";
		final String METHOD_NAME = "getDocumentRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_docID");
		propInfo1.setValue(docID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String[] getAttachmentCount(String docID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getAttachmentCount";
		final String METHOD_NAME = "getAttachmentCount";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_docID");
		propInfo1.setValue(docID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<ArrayList> getSuggetionList(String _qString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getSuggetionList";
		final String METHOD_NAME = "getSuggetionList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_qString");
		propInfo1.setValue(_qString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getAttachmentList(String _docID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAttachmentList";
		final String METHOD_NAME = "getAttachmentList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_docID");
		propInfo1.setValue(_docID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getUserContactList(String _userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserContactList";
		final String METHOD_NAME = "getUserContactList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getUserContactRequestList(String _userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserContactRequestList";
		final String METHOD_NAME = "getUserContactRequestList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getNetworkContactList(String _userID,String _qString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getNetworkContactList";
		final String METHOD_NAME = "getNetworkContactList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_qString");
		propInfo2.setValue(_qString);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getUserViewList(String _userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserViewList";
		final String METHOD_NAME = "getUserViewList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getUserActivityList(String _userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserActivityList";
		final String METHOD_NAME = "getUserActivityList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public String[] getUserProfileCount(String userID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserProfileCount";
		final String METHOD_NAME = "getUserProfileCount";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String[] getAttachmentInfo(String attID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getAttachmentInfo";
		final String METHOD_NAME = "getAttachmentInfo";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_attachID");
		propInfo1.setValue(attID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<ArrayList> getAttachmentSuggetionList(String _qString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAttachmentSuggetionList";
		final String METHOD_NAME = "getAttachmentSuggetionList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_qString");
		propInfo1.setValue(_qString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getAttachmentCommentList(String _attID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAttachmentCommentList";
		final String METHOD_NAME = "getAttachmentCommentList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_attachID");
		propInfo1.setValue(_attID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public String[] getUserRating(String _docID, String _userID) {
		String[] resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserRating";
		final String METHOD_NAME = "getUserRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_docID");
		propInfo1.setValue(_docID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_userID");
		propInfo2.setValue(_userID);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public Boolean saveUserRating(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveUserRating";
		final String METHOD_NAME = "saveUserRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_docID", "_userID", "_userRating" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Boolean updateUserRating(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/updateUserRating";
		final String METHOD_NAME = "updateUserRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_docID", "_userID", "_userRating" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public String[] getAttachmentRating(String attID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getAttachmentRating";
		final String METHOD_NAME = "getAttachmentRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_attID");
		propInfo1.setValue(attID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	public String[] getUserAttachmentRating(ArrayList paraList) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserAttachmentRating";
		final String METHOD_NAME = "getUserAttachmentRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_userID", "_docID", "_attID" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/* Data Mining Related functions*/
	
	public String[] getSuggetionDocumentList(String _userID, String _docID) {
		String[] resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getSuggetionDocumentList";
		final String METHOD_NAME = "getSuggetionDocumentList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_docID");
		propInfo2.setValue(_docID);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_MN);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public String[] getSuggetionAttachmentList(String _userID, String _attID) {
		String[] resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getSuggetionAttachmentList";
		final String METHOD_NAME = "getSuggetionAttachmentList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_attID");
		propInfo2.setValue(_attID);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL_MN);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	
	
	public Boolean saveAttachmentRating(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveAttachmentRating";
		final String METHOD_NAME = "saveAttachmentRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_attID", "_docID", "_userID","_usrRating" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean saveAttachmentComment(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveAttachmentComment";
		final String METHOD_NAME = "saveAttachmentComment";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_docID", "_attID", "_userID","_userComment" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean saveDocumentView(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveDocumentView";
		final String METHOD_NAME = "saveDocumentView";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_docID", "_userID" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean saveAttachmentView(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveAttachmentView";
		final String METHOD_NAME = "saveAttachmentView";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_attID", "_userID" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<ArrayList> getUserEvent(String _userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserEvent";
		final String METHOD_NAME = "getUserEvent";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getItemList(String _docQuery, String _attQuery){
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getItemList";
		final String METHOD_NAME = "getItemList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_docQuery");
		propInfo1.setValue(_docQuery);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_attQuery");
		propInfo2.setValue(_attQuery);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public Boolean updaterUserProfile(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/updaterUserProfile";
		final String METHOD_NAME = "updaterUserProfile";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_userImage", "_userID", "_Position",
				"_workPlace", "_firstName", "_lastName", "_emailID",
				"_address", "_mobileNo", "_facebook", "_google", "_linkedin",
				"_skype", "_twitter", "_username", "_password", "_securityQ",
				"_answer" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Boolean saveContactRequest(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveContactRequest";
		final String METHOD_NAME = "saveContactRequest";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_userID", "_conID" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean acceptContactRequest(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/acceptContactRequest";
		final String METHOD_NAME = "acceptContactRequest";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_userID", "_conID" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean removeContactRequest(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/removeContactRequest";
		final String METHOD_NAME = "removeContactRequest";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_userID", "_conID" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean removeContact(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/removeContact";
		final String METHOD_NAME = "removeContact";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		String[] paraNameList = { "_userID", "_conID" };
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String[] getResultSetFromResponse(SoapObject soap) {

		String[] itemArr = new String[soap.getPropertyCount()];
		for (int i = 0; i < itemArr.length; i++) {

			String item = soap.getProperty(i).toString();
			itemArr[i] = item;
		}
		return itemArr;
	}

	public ArrayList<ArrayList> getResultSetsFromResponse(SoapObject soap) {

		SoapObject rowStr = (SoapObject) soap.getProperty(0);

		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList = null;
		int totalCount = soap.getPropertyCount();

		if (totalCount > 0) {
			resultList = new ArrayList<ArrayList>();
			for (int detailCount = 0; detailCount < totalCount; detailCount++) {
				SoapObject rowSoap = (SoapObject) soap.getProperty(detailCount);
				rowList = new ArrayList<String>();
				for (int columnCount = 0; columnCount < rowSoap
						.getPropertyCount(); columnCount++) {

					rowList.add(rowSoap.getProperty(columnCount).toString());
				}

				resultList.add(rowList);
			}
		}
		return resultList;
	}

	


}
