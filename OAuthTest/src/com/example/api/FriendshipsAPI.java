package com.example.api;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.RequestListener;
/**
 *  * 此类封装了关系的接口，详情见<a href=http://open.weibo.com/wiki/API%E6%96%87%E6%A1%A3_V2#.E5.85.B3.E7.B3.BB">关系接口</a>
 * @author xiaowei6@staff.sina.com.cn
 *
 */
public class FriendshipsAPI extends WeiboAPI {
	public FriendshipsAPI(Oauth2AccessToken accessToken) {
        super(accessToken);
    }

    private static final String SERVER_URL_PRIX = API_SERVER + "/friendships";

	/**
	 * 获取用户的关注列�?	 * 
	 * @param uid �?��查询的用户UID�?	 * @param count 单页返回的记录条数，默认�?0，最大不超过200�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param trim_status 返回值中user字段中的status字段�?��，false：返回完整status字段、true：status字段仅返回status_id，默认为true�?	 * @param listener
	 */
	public void friends( long uid, int count, int cursor, boolean trim_status,
			RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		params.add("cursor", cursor);
		if (trim_status) {
			params.add("trim_status", 1);
		} else {
			params.add("trim_status", 0);
		}
		request( SERVER_URL_PRIX + "/friends.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取用户的关注列�?	 * 
	 * @param screen_name �?��查询的用户昵称�?
	 * @param count 单页返回的记录条数，默认�?0，最大不超过200�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param trim_status 返回值中user字段中的status字段�?��，false：返回完整status字段、true：status字段仅返回status_id，默认为true�?	 * @param listener
	 */
	public void friends( String screen_name, int count, int cursor,
			boolean trim_status, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("screen_name", screen_name);
		params.add("count", count);
		params.add("cursor", cursor);
		if (trim_status) {
			params.add("trim_status", 0);
		} else {
			params.add("trim_status", 1);
		}
		request( SERVER_URL_PRIX + "/friends.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取两个用户之间的共同关注人列表
	 * 
	 * @param uid �?��获取共同关注关系的用户UID�?	 * @param suid �?��获取共同关注关系的用户UID，默认为当前登录用户�?	 * @param count 单页返回的记录条数，默认�?0�?	 * @param page 返回结果的页码，默认�?�?	 * @param trim_status 返回值中user字段中的status字段�?��，false：返回完整status字段、true：status字段仅返回status_id，默认为true�?	 * @param listener
	 */
	public void inCommon( long uid, long suid, int count, int page, boolean trim_status,
			RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("suid", suid);
		params.add("count", count);
		params.add("page", page);
		if (trim_status) {
			params.add("trim_status", 1);
		} else {
			params.add("trim_status", 0);
		}
		request( SERVER_URL_PRIX + "/friends/in_common.json", params, HTTPMETHOD_GET,
				listener);
	}

	/**
	 * 获取用户的双向关注列表，即互粉列�?	 * 
	 * @param uid �?��获取双向关注列表的用户UID�?	 * @param count 单页返回的记录条数，默认�?0�?	 * @param page 返回结果的页码，默认�?�?	 * @param listener
	 */
	public void bilateral( long uid, int count, int page, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		params.add("page", page);
		request( SERVER_URL_PRIX + "/friends/bilateral.json", params, HTTPMETHOD_GET,
				listener);
	}

	/**
	 * 获取用户双向关注的用户ID列表，即互粉UID列表
	 * 
	 * @param uid �?��获取双向关注列表的用户UID�?	 * @param count 单页返回的记录条数，默认�?0，最大不超过2000�?	 * @param page 返回结果的页码，默认�?�?	 * @param listener
	 */
	public void bilateralIds( long uid, int count, int page, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		params.add("page", page);
		request( SERVER_URL_PRIX + "/friends/bilateral/ids.json", params, HTTPMETHOD_GET,
				listener);
	}

	/**
	 * 获取用户关注的用户UID列表
	 * 
	 * @param uid �?��查询的用户UID�?	 * @param count 单页返回的记录条数，默认�?00，最大不超过5000�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param listener
	 */
	public void friendsIds( long uid, int count, int cursor, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		params.add("cursor", cursor);
		request( SERVER_URL_PRIX + "/friends/ids.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取用户关注的用户UID列表
	 * 
	 * @param screen_name �?��查询的用户昵称�?
	 * @param count 单页返回的记录条数，默认�?00，最大不超过5000�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param listener
	 */
	public void friendsIds( String screen_name, int count, int cursor,
			RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("screen_name", screen_name);
		params.add("count", count);
		params.add("cursor", cursor);
		request( SERVER_URL_PRIX + "/friends/ids.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取用户的粉丝列�?�?��返回5000条数�?
	 * 
	 * @param uid �?��查询的用户UID�?	 * @param count 单页返回的记录条数，默认�?0，最大不超过200�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param trim_status 返回值中user字段中的status字段�?��，false：返回完整status字段、true：status字段仅返回status_id，默认为false�?	 * @param listener
	 */
	public void followers( long uid, int count, int cursor, boolean trim_status,
			RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		params.add("cursor", cursor);
		if (trim_status) {
			params.add("trim_status", 0);
		} else {
			params.add("trim_status", 1);
		}
		request( SERVER_URL_PRIX + "/followers.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取用户的粉丝列�?�?��返回5000条数�?
	 * 
	 * @param screen_name �?��查询的用户昵称�?
	 * @param count 单页返回的记录条数，默认�?0，最大不超过200�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param trim_status 返回值中user字段中的status字段�?��，false：返回完整status字段、true：status字段仅返回status_id，默认为false�?	 * @param listener
	 */
	public void followers( String screen_name, int count, int cursor,
			boolean trim_status, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("screen_name", screen_name);
		params.add("count", count);
		params.add("cursor", cursor);
		if (trim_status) {
			params.add("trim_status", 0);
		} else {
			params.add("trim_status", 1);
		}
		request( SERVER_URL_PRIX + "/followers.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取用户粉丝的用户UID列表
	 * 
	 * @param uid �?��查询的用户UID�?	 * @param count 单页返回的记录条数，默认�?00，最大不超过5000�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param listener
	 */
	public void followersIds( long uid, int count, int cursor, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		params.add("cursor", cursor);
		request( SERVER_URL_PRIX + "/followers/ids.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取用户粉丝的用户UID列表
	 * 
	 * @param screen_name �?��查询的用户昵称�?
	 * @param count 单页返回的记录条数，默认�?00，最大不超过5000�?	 * @param cursor 返回结果的游标，下一页用返回值里的next_cursor，上�?��用previous_cursor，默认为0�?	 * @param listener
	 */
	public void followersIds( String screen_name, int count, int cursor,
			RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("screen_name", screen_name);
		params.add("count", count);
		params.add("cursor", cursor);
		request( SERVER_URL_PRIX + "/followers/ids.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取用户的活跃粉丝列�?	 * 
	 * @param uid �?��查询的用户UID�?	 * @param count 返回的记录条数，默认�?0，最大不超过200�?	 * @param listener
	 */
	public void followersActive( long uid, int count, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		request( SERVER_URL_PRIX + "/followers/active.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取当前登录用户的关注人中又关注了指定用户的用户列表
	 * 
	 * @param uid 指定的关注目标用户UID�?	 * @param count 单页返回的记录条数，默认�?0�?	 * @param page 返回结果的页码，默认�?�?	 * @param listener
	 */
	public void chainFollowers( long uid, int count, int page, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("count", count);
		params.add("page", page);
		request( SERVER_URL_PRIX + "/friends_chain/followers.json", params, HTTPMETHOD_GET,
				listener);
	}

	/**
	 * 获取两个用户之间的详细关注关系情�?	 * 
	 * @param source_id 源用户的UID�?	 * @param target_id 目标用户的UID�?	 * @param listener
	 */
	public void show( long source_id, long target_id, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("source_id", source_id);
		params.add("target_id", target_id);
		request( SERVER_URL_PRIX + "/show.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取两个用户之间的详细关注关系情�?	 * 
	 * @param source_id 源用户的UID�?	 * @param target_screen_name 目标用户的微博昵�?	 * @param listener
	 */
	public void show( long source_id, String target_screen_name, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("source_id", source_id);
		params.add("target_screen_name", target_screen_name);
		request( SERVER_URL_PRIX + "/show.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取两个用户之间的详细关注关系情�?	 * 
	 * @param source_screen_name 源用户的微博昵称�?	 * @param target_id 目标用户的UID�?	 * @param listener
	 */
	public void show( String source_screen_name, long target_id, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("source_screen_name", source_screen_name);
		params.add("target_id", target_id);
		request( SERVER_URL_PRIX + "/show.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 获取两个用户之间的详细关注关系情�?	 * 
	 * @param source_screen_name 源用户的微博昵称�?	 * @param target_screen_name 目标用户的微博昵�?	 * @param listener
	 */
	public void show( String source_screen_name, String target_screen_name,
			RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("target_screen_name", target_screen_name);
		params.add("source_screen_name", source_screen_name);
		request(SERVER_URL_PRIX + "/show.json", params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 关注�?��用户
	 * 
	 * @param uid �?��关注的用户ID�?	 * @param screen_name �?��关注的用户昵称�?
	 * @param listener
	 */
	public void create( long uid, String screen_name, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("screen_name", screen_name);
		request( SERVER_URL_PRIX + "/create.json", params, HTTPMETHOD_POST, listener);
	}

	/**
	 * 关注�?��用户
	 * 
	 * @param screen_name �?��关注的用户昵称�?
	 * @param listener
	 */
	@Deprecated
	public void create( String screen_name, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("screen_name", screen_name);
		request( SERVER_URL_PRIX + "/create.json", params, HTTPMETHOD_POST, listener);
	}

	/**
	 * 取消关注�?��用户
	 * 
	 * @param uid �?��取消关注的用户ID�?	 * @param screen_name �?��取消关注的用户昵称�?
	 * @param listener
	 */
	public void destroy(long uid, String screen_name, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("uid", uid);
		params.add("screen_name", screen_name);
		request( SERVER_URL_PRIX + "/destroy.json", params, HTTPMETHOD_POST, listener);
	}
	
	/**
	 * 取消关注�?��用户
	 * 
	 * @param screen_name �?��取消关注的用户昵称�?
	 * @param listener
	 */
	@Deprecated
	public void destroy(String screen_name, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add("screen_name", screen_name);
		request( SERVER_URL_PRIX + "/destroy.json", params, HTTPMETHOD_POST, listener);
	}
}
