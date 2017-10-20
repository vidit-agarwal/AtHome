package com.devbrain.athome.parser;


import com.devbrain.athome.modal.Banners;
import com.devbrain.athome.modal.Category;
import com.devbrain.athome.modal.InitResponseWrapper;
import com.devbrain.athome.modal.OnlineApps;
import com.devbrain.athome.rest.STATUS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mukesh Jha on 6/22/2016.
 */
public class JSONParser
{
    public static String getStatusCode(String jsonString)
    {
        JSONObject jsonObject = null;
        String status = null;

        try
        {
            jsonObject = new JSONObject(jsonString);
            if(jsonObject != null && jsonObject.has(ParserConstants.STATUS))
            {
                status = jsonObject.getString(ParserConstants.STATUS);
            }
        }
        catch (JSONException jse)
        {
            jse.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            jsonObject = null;
        }

        return status;
    }

    public static STATUS getCode(String jsonString)
    {
        JSONObject jsonObject = null;
        String status = "UNKNOWN";

        try
        {
            jsonObject = new JSONObject(jsonString);
            if(jsonObject != null && (jsonObject.has(ParserConstants.STATUS) | jsonObject.has(ParserConstants.STATUS_CAPS)))
            {
                status = jsonObject.has(ParserConstants.STATUS) ? jsonObject.getString(ParserConstants.STATUS) : jsonObject.getString(ParserConstants.STATUS_CAPS);

                if("0".equalsIgnoreCase(status))
                {
                    status = "FAIL";
                }
                else if("1".equalsIgnoreCase(status))
                {
                    status = "SUCCESS";
                }
                else if("2".equalsIgnoreCase(status))
                {
                    status = "USER_NOT_FOUND";
                }
                else if("3".equalsIgnoreCase(status))
                {
                    status = "LIST_EMPTY";
                }
                else if("4".equalsIgnoreCase(status))
                {
                    status = "DATA_MISSING";
                }
            }
        }
        catch (JSONException jse)
        {
            jse.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            jsonObject = null;
        }

        return STATUS.valueOf(status);
    }

    public static String getPackageName(JSONObject json)
    {
        try {
            return json.getString("prdpkg");
        }
        catch (JSONException e)
        {e.printStackTrace();}
        catch (Exception e)
        {e.printStackTrace();}

        return "";
    }

    public static List<OnlineApps> getOnlineAppsData(){
        List<OnlineApps> appLists = null;
        try{
            String strData = SharedPreferencesUtil.getInstance().getString(SharedPreferencesUtil.KEY.ONLINE_APPS);
            if(strData!=null) {
                appLists = new ArrayList<OnlineApps>();

                JSONObject objApps = new JSONObject(strData);
                JSONArray arrApps = objApps.getJSONArray(ParserConstants.APPS);
                for (int i = 0; i <arrApps.length(); i++) {
                    JSONObject objItem = (JSONObject) arrApps.get(i);
                    appLists.add(new OnlineApps(objItem.getString(ParserConstants.NAME), objItem.getInt(ParserConstants.ID), objItem.getString(ParserConstants.IMAGE_URL), objItem.getString(ParserConstants.APK_URL), objItem.getString(ParserConstants.DESCRIPTION), true));
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return appLists;
    }

    public static InitResponseWrapper parseInitResponse(String jsonString) {
        InitResponseWrapper initResponseWrapper = new InitResponseWrapper();
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        try {
            jsonObject = new JSONObject(jsonString);

//            Parsing Banners App
            if (jsonObject.has(ParserConstants.BANNERS_APP )) {

                jsonArray = jsonObject.getJSONArray(ParserConstants.BANNERS_APP );

                for (int i = 0; i < jsonArray.length(); i++) {
                    Banners banners = new Banners();

                    JSONObject jObject = jsonArray.getJSONObject(i);

                    if (jObject.has(ParserConstants.BANNER_ID_APP )) {

                        banners.setId(jObject.getString(ParserConstants.BANNER_ID_APP ));
                    }

                    if (jObject.has(ParserConstants.BANNER_NAME_APP )) {
                        banners.setRedirectUrl(jObject.getString(ParserConstants.BANNER_NAME_APP ));

                    }
                    if (jObject.has(ParserConstants.BANNER_REDIRECT_URL_APP )) {
                        banners.setRedirectUrl(jObject.getString(ParserConstants.BANNER_REDIRECT_URL_APP ));

                    }

                    if (jObject.has(ParserConstants.BANNER_IMAGE_APP )) {
                        banners.setIconUrl(jObject.getString(ParserConstants.BANNER_IMAGE_APP ));
                    }
                    initResponseWrapper.getLstBanners().add(banners);
                    banners = null;
                }
            }

            // Parsing Listing App
            if (jsonObject.has(ParserConstants.BANNERS_APP )) {
                jsonArray = jsonObject.getJSONArray(ParserConstants.BANNERS_APP );

                for (int i = 0; i < jsonArray.length(); i++) {
                    Category category = new Category();

                    JSONObject jObject = jsonArray.getJSONObject(i);

                    if (jObject.has(ParserConstants.BANNER_ID_APP )) {
                        category.setId(jObject.getString(ParserConstants.BANNER_ID_APP ));
                    }

                    if (jObject.has(ParserConstants.BANNER_NAME_APP )) {
                        category.setName(jObject.getString(ParserConstants.BANNER_NAME_APP ));
                    }

                    if (jObject.has(ParserConstants.BANNER_DESCRIPTION_APP )) {
                        category.setDescription(jObject.getString(ParserConstants.BANNER_DESCRIPTION_APP ));
                    }

                    if (jObject.has(ParserConstants.BANNER_IMAGE_APP )) {
                        category.setIconUrl(jObject.getString(ParserConstants.BANNER_IMAGE_APP ));
                    }

                    if (jObject.has(ParserConstants.BANNER_REDIRECT_URL_APP )) {
                        category.setDownloadRedirectUrl(jObject.getString(ParserConstants.BANNER_REDIRECT_URL_APP ));
                    }

                    initResponseWrapper.getLstCategories().add(category);
                    category = null;
                }
            }

            //  Parsing Banners Games
            if (jsonObject.has(ParserConstants.BANNERS_GAME )) {

                jsonArray = jsonObject.getJSONArray(ParserConstants.BANNERS_GAME );

                for (int i = 0; i < jsonArray.length(); i++) {
                    Banners banners = new Banners();

                    JSONObject jObject = jsonArray.getJSONObject(i);

                    if (jObject.has(ParserConstants.BANNER_ID_GAME )) {

                        banners.setId(jObject.getString(ParserConstants.BANNER_ID_GAME ));
                    }

                    if (jObject.has(ParserConstants.BANNER_NAME_GAME )) {
                        banners.setRedirectUrl(jObject.getString(ParserConstants.BANNER_NAME_GAME ));

                    }
                    if (jObject.has(ParserConstants.BANNER_REDIRECT_URL_GAME )) {
                        banners.setRedirectUrl(jObject.getString(ParserConstants.BANNER_REDIRECT_URL_GAME ));

                    }

                    if (jObject.has(ParserConstants.BANNER_IMAGE_GAME )) {
                        banners.setIconUrl(jObject.getString(ParserConstants.BANNER_IMAGE_GAME ));
                    }
                    initResponseWrapper.getLstBanners().add(banners);
                    banners = null;
                }
            }

            // Parsing Listing Games
            if (jsonObject.has(ParserConstants.BANNERS_GAME )) {
                jsonArray = jsonObject.getJSONArray(ParserConstants.BANNERS_GAME );

                for (int i = 0; i < jsonArray.length(); i++) {
                    Category category = new Category();

                    JSONObject jObject = jsonArray.getJSONObject(i);

                    if (jObject.has(ParserConstants.BANNER_ID_GAME)) {
                        category.setId(jObject.getString(ParserConstants.BANNER_ID_GAME));
                    }

                    if (jObject.has(ParserConstants.BANNER_NAME_GAME )) {
                        category.setName(jObject.getString(ParserConstants.BANNER_NAME_GAME ));
                    }

                    if (jObject.has(ParserConstants.BANNER_DESCRIPTION_GAME )) {
                        category.setDescription(jObject.getString(ParserConstants.BANNER_DESCRIPTION_GAME ));
                    }

                    if (jObject.has(ParserConstants.BANNER_REDIRECT_URL_GAME )) {
                        category.setDownloadRedirectUrl(jObject.getString(ParserConstants.BANNER_REDIRECT_URL_GAME ));
                    }

                    if (jObject.has(ParserConstants.BANNER_IMAGE_GAME )) {
                        category.setIconUrl(jObject.getString(ParserConstants.BANNER_IMAGE_GAME ));
                    }

                    initResponseWrapper.getLstCategories().add(category);
                    category = null;
                }
            }


//            Parsing Hot Categories
/*            if (jsonObject.has(JSONConstants.HOT_CATEGORIES)) {
                jsonArray = jsonObject.getJSONArray(JSONConstants.HOT_CATEGORIES);

                for (int i = 0; i < jsonArray.length(); i++) {
                    HotTopic hotTopic = new HotTopic();

                    JSONObject jObject = jsonArray.getJSONObject(i);

                    if (jObject.has(JSONConstants.HOT_ID)) {
                        hotTopic.setId(jObject.getString(JSONConstants.HOT_ID));
                    }

                    if (jObject.has(JSONConstants.HOT_PRODUCT_NAME)) {
                        hotTopic.setName(jObject.getString(JSONConstants.HOT_PRODUCT_NAME));
                    }

                    if (jObject.has(JSONConstants.HOT_REDIRECT_URL)) {
                        hotTopic.setRedirectUrl(jObject.getString(JSONConstants.HOT_REDIRECT_URL));
                    }

                    if (jObject.has(JSONConstants.HOT_ICON_URL)) {
                        hotTopic.setIconUrl(jObject.getString(JSONConstants.HOT_ICON_URL));
                    }

                    initResponseWrapper.getLstHotTopics().add(hotTopic);
                    hotTopic = null;
                }
            }*/
        } catch (JSONException jse) {
            jse.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jsonArray = null;
            jsonObject = null;
            jsonString = null;
        }

        return initResponseWrapper;
    }

}
