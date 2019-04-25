package cn.solwind.framework.redis;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

/**
 * Redis基类
 * 单台和集群实现均继承此类
 * 
 * @author zln
 * 
 */
public abstract class Redis {

	/*
	 * FastJson序列化实例
	 */
	private static Redis redis = new RedisStandalone(false);
	private static Redis redisCluster = new RedisCluster(false);
	
	/*
	 * Apache序列化实例
	 */
	private static Redis complexRedis = new RedisStandalone(true);
	private static Redis complexRedisCluster = new RedisCluster(true);
	
	protected final String separator = "|";
	
	protected boolean ready = false;
	
	Logger log = LogManager.getLogger();
	
	private static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 获取实例，使用fastjson对数据进行序列化
	 * 
	 * @return
	 */
	public static Redis getInstance() {
		
		/*if (FrameworkConstants.REDIS_CLUSTER != null
				&& FrameworkConstants.REDIS_CLUSTER.equals("true")) {
			return redisCluster;
		} else {
			return redis;
		}*/
		
		return redis;
	}
	
	/**
	 * 获取实例，使用apache框架对数据进行序列化
	 * 
	 * @return
	 */
	public static Redis getComplexInstance() {
		
		/*if (FrameworkConstants.REDIS_CLUSTER != null
				&& FrameworkConstants.REDIS_CLUSTER.equals("true")) {
			return complexRedisCluster;
		} else {
			return complexRedis;
		}*/
		return complexRedis;
	}

	/**
	 * 初始化redis连接池
	 * 
	 * @param ips
	 *            ip=port
	 */
	public abstract Redis init(Map<String, String> ips, Map<String, String> config);


	/**
	 * 根据KEY返回相应的节点连接
	 * 
	 * @return
	 */
	public abstract Jedis getJedis(String key);
	
	/**
	 * 模糊查询
	 * @param pattern
	 * @return
	 */
	public abstract TreeSet<String> keys(String pattern);

	/**
	 * 检查KEY是否存在
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean exists(String key);

	/**
	 * 将KEY中的值数字加1
	 * 
	 * @param key
	 * @return
	 */
	public abstract Long increament(String key);
	/**
	 * 将KEY中的值数字减1
	 * 
	 * @param key
	 * @return
	 */
	public abstract Long decr(String key);

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public abstract boolean expire(String key, int seconds);
	
	/**
	 * 设置KEY永久有效，移除过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public abstract boolean persist(String key);

	/**
	 * 删除KEY
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean del(String key);

	/**
	 * 用于设置指定 key 的值，并返回 key 旧的值
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object getSet(String key, Object value);

	/**
	 * 返回KEY所关联的对象
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object get(String key);


	/**
	 * 获取锁
	 * 
	 * @param key
	 * @param value
	 * @param seconds 超时时间（单位：秒）0：无有效期
	 * 
	 * @return
	 */
	public abstract boolean setLock(String key, Object value, int seconds);
	
	/**
	 * 获取毫秒锁
	 * 
	 * @param key
	 * @param value
	 * @param seconds 超时时间（单位：毫秒）0：无有效期
	 * 
	 * @return
	 */
	public abstract boolean setMilliLock(String key, Object value, int milliSeconds);

	/**
	 * 获取锁
	 * 
	 * @param key
	 * @param seconds
	 *            超时时间（单位：秒）0：无有效期
	 * @return
	 */
	public abstract boolean setLock(String key, int seconds);
	
	/**
	 * 获取毫秒锁
	 * 
	 * @param key
	 * @param milliSeconds
	 *            超时时间（单位：毫秒）0：无有效期
	 * @return
	 */
	public abstract boolean setMilliLock(String key, int milliSeconds);

	/**
	 * 将value关联到KEY， ex可选为指定的 key 设置其过期时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds 超时时间，小于等于0是认为无超时时间
	 * @return
	 */
	public abstract boolean set(String key, Object value, int seconds);

	/**
	 * 返回哈希表中的数量
	 * 
	 * @param key
	 * @return
	 */
	public abstract Long hLen(String key);

	/**
	 * 为哈希表中的字段赋值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public abstract boolean hSet(String key, String field, Object value);

	/**
	 * 用于同时将多个 field-value (字段-值)对设置到哈希表中
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract boolean hmSet(String key, Map<Object, Object> value);

	/**
	 * 用于返回哈希表中指定字段的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public abstract Object hGet(String key, String field);
	
	/**
	 * 用于返回哈希表中，所有的字段和值
	 * 
	 * @param key
	 * @return
	 */
	public abstract Map<Object, Object> hGetAll(String key);

	/**
	 * 用于返回哈希表中，一个或多个给定字段的值
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public abstract List<Object> hmGet(String key, Object... fields);
	
	
	/**
	 * 删除哈希表中，一个或多个给定字段的值,不存在的字段被忽略
	 * 
	 * @param key
	 * @param fields
	 * @return  返回被成功移除字段数量
	 */
	public abstract Long hDel(String key, Object... fields);
	
	
	/**
	 * EXPIREAT 的作用和EXPIRE 类似，都用于为 key 设置生存时间。
	 * 不同在于EXPIREAT 命令接受的时间参数是 UNIX 时间戳 (unix timestamp)。
	 * EXPIREAT cache 1355292000 # 这个 key 将在 2012.12.12 过期
	 * @param key
	 * @param timestamp
	 */
	public abstract boolean expireAt(String key, Long timestamp);
	/**
	 * MOVE key db
		将当前数据库的 key 移动到给定的数据库 db 当中。
	 * @param key
	 * @param db
	 */
	public abstract boolean move(String key, String db);


	/**
	 * PTTL key 这个命令类似于TTL 命令，但它以毫秒为单位返回 key 的剩余生存时间，而不是像TTL 命令那样，以秒为 单位。
	 * 
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key
	 *         的剩余生存时间。异常返回 -3。
	 */
	public abstract Long pttl(String key);

	/**
	 * RENAME key newkey 将 key 改名为 newkey 。 当 key 和 newkey 相同，或者 key
	 * 不存在时，返回一个错误。 当 newkey 已经存在时，RENAME 命令将覆盖旧值。。
	 * 
	 * @param key
	 * @param newKey
	 */
	@Deprecated
	public abstract boolean rename(String key, String newKey);

	/**
	 * 当且仅当 newkey 不存在时，将 key 改名为 newkey 。 当 key 不存在时，返回一个错误。。
	 * 
	 * @param key
	 * @param newKey
	 */
	public abstract boolean renameNx(String key, String newKey);
	
	/**
	 * TYPE key 返回 key 所储存的值的类型。
	 * 
	 * @param key
	 * @return 返回值： none (key 不存在) string (字符串) list (列表) set (集合) zset (有序集)
	 *         hash (哈希表)
	 */
	public abstract String type(String key);
	
	/**
	 * APPEND key value 如果 key 已经存在并且是一个字符串，APPEND 命令将 value 追加到 key 原来的值的末尾。 如果
	 * key 不存在，APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
	 * 
	 * @param key
	 * @return 返回值： 追加 value 之后，key 中字符串的长度。
	 */
	public abstract long append(String key, String value);

	/**
	 * GETRANGE key start end 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定
	 * (包括 start 和 end 在内)。 负数偏移量表示从字符串最后开始计数，-1 表示最后一个字符，-2 表示倒数第二个，以此类推。
	 * 
	 * @param key
	 */
	public abstract String getRange(String key, long start, long end);

	/**
	 * INCRBY key increment 将 key 所储存的值加上增量 increment 。 如果 key 不存在，那么 key
	 * 的值会先被初始化为 0 ，然后再执行INCRBY 命令。
	 * 
	 * @param key
	 */
	public abstract Long incrBy(String key, long increment);

	/**
	 * DECRBY key decrement 将 key 所储存的值减去减量 decrement 。 如果 key 不存在，那么 key
	 * 的值会先被初始化为 0 ，然后再执行DECRBY 操作。
	 * 
	 * @param key
	 */
	public abstract Long decrBy(String key, long decrement);
	
	/**
	 * MGET key [key ...] 返回所有 (一个或多个) 给定 key 的值。 如果给定的 key 里面，有某个 key 不存在，那么这个
	 * key 返回特殊值 nil 。因此，该命令永不失败。
	 * 
	 * @param key
	 */
	public abstract List mGet(String... key);

	/**
	 * MSET key value [key value ...] 同时设置一个或多个 key-value 对。 如果某个给定 key
	 * 已经存在，那么MSET 会用新值覆盖原来的旧值，如果这不是你所希望的效果，请考虑 使用MSETNX 命令：它只会在所有给定 key
	 * 都不存在的情况下进行设置操作。 MSET 是一个原子性 (atomic) 操作，所有给定 key 都会在同一时间内被设置，某些给定 key
	 * 被更新而另一 些给定 key 没有改变的情况，不可能发生。
	 * 
	 * @param key
	 * @param value
	 */
	public abstract boolean mSet(Object... keysvalues);

	/**
	 * SETRANGE key oﬀset value 用 value 参数覆写 (overwrite) 给定 key 所储存的字符串值，从偏移量
	 * offset 开始。 不存在的 key 当作空白字符串处理。
	 * 
	 * @param key
	 * @return 被SETRANGE 修改之后，字符串的长度。
	 */
	public abstract int setRange(String key, long offset, Object value);
	
	/**
	 * STRLEN key 返回 key 所储存的字符串值的长度。 当 key 储存的不是字符串值时，返回一个错误。
	 * 
	 * @param key
	 * @return 字符串值的长度。 当 key 不存在时，返回 0 。
	 */
	public abstract int strLen(String key);

	/**
	 * HKEYS key 返回哈希表 key 中的所有域。
	 * 
	 * @param key
	 * @return 一个包含哈希表中所有域的表。 当 key 不存在时，返回一个空表。
	 */
	public abstract Set hKeys(String key);

	/**
	 * HVALS key 返回哈希表 key 中所有域的值。
	 * 
	 * @param key
	 * @return 一个包含哈希表中所有值的表。 当 key 不存在时，返回一个空表。
	 */
	public abstract List hVals(String key);

	/**
	 * BLPOP key [key ...] timeout BLPOP 是列表的阻塞式 (blocking) 弹出原语。 它是LPOP
	 * 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被BLPOP 命令阻塞，直 到等待超时或发现可弹出元素为止。 当给定多个 key
	 * 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。超时参数设为 0 表示阻塞时间可以无限期延长
	 * 
	 * @param key
	 * @return 如果列表为空，返回一个 nil 。 否则，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元
	 *         素的值。
	 */
	public abstract List bLpop(Long timeout, String... key);

	/**
	 * BRPOP key [key ...] timeout BRPOP 是列表的阻塞式 (blocking) 弹出原语。 它是RPOP
	 * 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被BRPOP 命令阻塞，直 到等待超时或发现可弹出元素为止。 当给定多个 key
	 * 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的尾部元素。超时参数设为 0 表示阻塞时间可以无限期延长
	 * 
	 * @param key
	 * @return 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
	 *         反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元 素的值。
	 */
	public abstract List bRpop(Long timeout, String... key);

	/**
	 * BRPOPLPUSH source destination timeout BRPOPLPUSH 是RPOPLPUSH 的阻塞版本，当给定列表
	 * source 不为空时，BRPOPLPUSH 的表现 和RPOPLPUSH 一样。
	 * 
	 * @param key
	 * @return 假如在指定时间内没有任何元素被弹出，则返回null。
	 *         反之，返回被弹出元素的值
	 */
	public abstract Object bRpopLpush(Long timeout, String source, String destination);

	/**
	 * LINDEX key index 返回列表 key 中，下标为 index 的元素。 下标 (index) 参数 start 和 stop 都以
	 * 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的 第二个元素，以此类推。 你也可以使用负数下标，以 -1
	 * 表示列表的最后一个元素，-2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 * @return 列表中下标为 index 的元素。 如果 index 参数的值不在列表的区间范围内 (out of range)，返回 nil 。
	 */
	public abstract Object lIndex(String key, long index);

	/**
	 * LINSERT key BEFORE|AFTER pivot value 将值 value 插入到列表 key 当中，位于值 pivot
	 * 之前或之后。 当 pivot 不存在于列表 key 时，不执行任何操作。
	 * 
	 * @param key
	 * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。 如果没有找到 pivot ，返回 -1 。 如果 key
	 *         不存在或为空列表，返回 0 。
	 */
	public abstract Long lInsert(String key, LIST_POSITION position, Object pivot,
			Object value);

	/**
	 * LLEN key 返回列表 key 的长度。 如果 key 不存在，则 key 被解释为一个空列表，返回 0 . 如果 key
	 * 不是列表类型，返回一个错误。
	 * 
	 * @param key
	 * @return 列表 key 的长度。
	 */
	public abstract Long lLen(String key);

	/**
	 * LPOP key 移除并返回列表 key 的头元素。
	 * 
	 * @param key
	 * @return 列表的头元素。 当 key 不存在时，返回 nil 。
	 */
	public abstract Object lPop(String key);

	/**
	 * LPUSH key value [value ...] 将一个或多个值 value 插入到列表 key 的表头 如果有多个 value
	 * 值，那么各个 value 值按从左到右的顺序依次插入到表头：比如说，对空列表 mylist 执行命令 LPUSH mylist a b c
	 * ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、LPUSH mylist b 和 LPUSH mylist c
	 * 三个命令。
	 * 
	 * @param key
	 * @return 执行LPUSH 命令后，列表的长度。
	 */
	public abstract long lPush(String key, Object... values);

	/**
	 * LRANGE key start stop 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。 下标
	 * (index) 参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的 第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素，-2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 * @return 一个列表，包含指定区间内的元素。
	 */
	public abstract List lRange(String key, int start, int stop);

	/**
	 * LREM key count value 根据参数 count 的值，移除列表中与参数 value 相等的元素。 count 的值可以是以下几种：
	 * • count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。 • count < 0 :
	 * 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。 • count = 0 : 移除表中所有与 value
	 * 相等的值。
	 * 
	 * @param key
	 * @return 被移除元素的数量。 因为不存在的 key 被视作空表 (empty list)，所以当 key 不存在时，LREM 命令总是返回
	 *         0 。
	 */
	public abstract int lRem(String key, long count, Object value);

	/**
	 * LSET key index value 将列表 key 下标为 index 的元素的值设置为 value 。 当 index
	 * 参数超出范围，或对一个空列表 ( key 不存在) 进行LSET 时，返回一个错误。
	 * 
	 * @param key
	 * @return 操作成功返回 ok ，否则返回错误信息。
	 */
	public abstract boolean lSet(String key, long index, Object value);

	/**
	 * LTRIM key start stop 对一个列表进行修剪 (trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删
	 * 除。 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。 下标 (index) 参数
	 * start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的 第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素，-2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 * @return 命令执行成功时，返回 ok 。
	 */
	public abstract boolean lTrim(String key, Long start, Long stop);

	/**
	 * RPOP key 移除并返回列表 key 的尾元素。
	 * 
	 * @param key
	 * @return 列表的尾元素。 当 key 不存在时，返回 nil 。
	 */
	public abstract Object rPop(String key);
	
	/**
	 * RPOPLPUSH source destination 命令RPOPLPUSH 在一个原子时间内，执行以下两个动作： • 将列表 source
	 * 中的最后一个元素 (尾元素) 弹出，并返回给客户端。 • 将 source 弹出的元素插入到列表 destination ，作为
	 * destination 列表的的头元素。 举个例子，你有两个列表 source 和 destination ，source 列表有元素 a, b,
	 * c ，destination 列表有元 素 x, y, z ，执行 RPOPLPUSH source destination 之后，source
	 * 列表包含元素 a, b ，destination 列 表包含元素 c, x, y, z ，并且元素 c 会被返回给客户端。 如果 source
	 * 不存在，值 nil 被返回，并且不执行其他动作。 如果 source 和 destination
	 * 相同，则列表中的表尾元素被移动到表头，并返回该元素，可以把这种特殊 情况视作列表的旋转 (rotation) 操作。
	 * 
	 * @param key
	 * @return 被弹出的元素。
	 */
	public abstract Object rPopLpush(String source, String destination);

	/**
	 * RPUSH key value [value ...] 将一个或多个值 value 插入到列表 key 的表尾 (最右边)。 如果有多个
	 * value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist a b c
	 * ，得出的结果列表为 a b c ，等同于执行命令 RPUSH mylist a 、RPUSH mylist b 、RPUSH mylist c 。
	 * 如果 key 不存在，一个空列表会被创建并执行RPUSH 操作。
	 * 
	 * @param key
	 * @return 执行RPUSH 操作后，表的长度。
	 */
	public abstract Long rPush(String key, Object... values);
	
	
	/**
	 * SORT key [BY pattern] [LIMIT oﬀset count] [GET pattern [GET pattern ...]]
	 * [ASC | DESC] [ALPHA] [STORE destination]
	 * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。 ALPHA
	 * 修饰符对字符串进行排序 使用 LIMIT 修饰符限制返回结果 BY 使用外部 key 进行排序
	 * 
	 * @param key
	 * @param sortingParameters
	 * @return 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。
	 */
	public abstract List sort(String key, SortingParams sortingParameters);
	
	/**
	 * SORT key [BY pattern] [LIMIT oﬀset count] [GET pattern [GET pattern ...]]
	 * [ASC | DESC] [ALPHA] [STORE destination] 
	 * 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
	 * ALPHA 修饰符对字符串进行排序 使用 LIMIT 修饰符限制返回结果 BY 使用外部 key 进行排序
	 * 
	 * @param key
	 * @param sortingParameters
	 * @param dstkey
	 *            默认情况下，SORT 操作只是简单地返回排序结果，并不进行任何保存操作。 通过给 dstkey 选项指定一个 key
	 *            参数，可以将排序结果保存到给定的键上。 如果被指定的 key 已存在，那么原有的值将被排序结果覆盖。
	 * @return 返回保存给定列表、集合、有序集合 key 中经过排序的元素数量。
	 */
	public abstract Long sort(String key, SortingParams sortingParameters, String dstkey);
	
	
	/**
	 * 生成json串,基础数据类型不转换成json,转换为String
	 * 
	 * @param value
	 *            所有单个对象和List<Object>,Map<String,String>,不支持Map<String,Object>
	 * @return  
	 */
	public String json(Object value) {
		if (value == null || value.equals("null")) {
			return "";
		}
		if (value instanceof List) {
			List list = (List) value;
			return String.format("%s<%s>%s%s", value.getClass().getName(), list
					.get(0).getClass().getName(), separator,
					JSON.toJSONString(value));
		} else if (value instanceof Integer || value instanceof Float
				|| value instanceof Double || value instanceof Long
				|| value instanceof String || value instanceof BigDecimal
				|| value instanceof StringBuffer || value instanceof Short) {
			// FIXME 基础数据类型不转换成json,转换为String
			return String.format("%s|%s", value.getClass().getName(),
					value.toString());
		} else if (value instanceof Date) {
			// FIXME 日期转换为 yyyy-MM-dd HH:mm:ss
			String dateToDateString = DateFormatUtils.format((Date) value,
					TIMEF_FORMAT);
			return String.format("%s|%s", value.getClass().getName(),
					dateToDateString);
		} else if (value instanceof Timestamp) {
			// FIXME 日期转换为 yyyy-MM-dd HH:mm:ss
			Timestamp tp = (Timestamp) value;
			String dateToDateString = DateFormatUtils.format(new Date(tp
					.getTime()), TIMEF_FORMAT);
			return String.format("%s|%s", Date.class.getName(),
					dateToDateString);
		} else {
			return String.format("%s|%s", value.getClass().getName(),
					JSON.toJSONString(value));
		}
	}

	/**
	 * 解析对象
	 * 
	 * @param data
	 *         fastjon序列化:格式为：type|json,基础数据类型统一返回为String
	 *         apache序列化:byte[]
	 * @return type
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Object parseObject(Object data) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		if(isComplexObj()) {
			// 复杂对象，使用apache序列化，存储在redis中的为byte[]
			if(data == null) {
				return null;
			}
			return SerializationUtils.deserialize((byte[])data);
		}
		else {
			// 非复杂对象，使用fastjson序列化，存储在redis中的为json string，进行解析
			String json = (String)data;
			if (json == null || json.equals("null") || json.equals("nil")) {
				return null;
			}
			if (json.indexOf(separator) < 0) {
				// 没有指定类型，原样返回
				return json;
			}

			String prefix = StringUtils.substringBefore(json, separator);
			String subType = StringUtils.substringBetween(prefix, "<", ">");
			if (subType != null) {
				return JSON.parseArray(StringUtils.substringAfter(json, separator),
						Class.forName(subType));
			} else {
				if (prefix.indexOf("Integer") >= 0 || prefix.indexOf("Short") >= 0
						|| prefix.indexOf("Float") >= 0
						|| prefix.indexOf("Double") >= 0
						|| prefix.indexOf("Long") >= 0
						|| prefix.indexOf("Float") >= 0
						|| prefix.indexOf("String") >= 0
						|| prefix.indexOf("StringBuffer") >= 0
						|| prefix.indexOf("BigDecimal") >= 0) {
					// FIXME 基础数据类型统一返回为String
					return StringUtils.substringAfter(json, separator);
				} else if (prefix.indexOf("Date") >= 0) {
					// FIXME 日期转换为 yyyy-MM-dd HH:mm:ss
					Date date = null;
					try {
						date = DateUtils.parseDate(StringUtils.substringAfter(json, separator), TIMEF_FORMAT);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date;
				} else if (prefix.indexOf("Timestamp") >= 0) {
					// FIXME 日期转换为 yyyy-MM-dd HH:mm:ss
					Date date = null;
					try {
						date = DateUtils.parseDate(StringUtils.substringAfter(json, separator), TIMEF_FORMAT);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date;
				} else {
					return JSON.parseObject(
							StringUtils.substringAfter(json, separator),
							Class.forName(prefix));
				}
			}
		}
		
	}

	public boolean isOk(String value) {
		if (value != null && value.equals("OK")) {
			return true;
		} else if (value != null && value.equalsIgnoreCase("nil")) {
			return false;
		} else if (value != null && value.equalsIgnoreCase("(nil)")) {
			return false;
		} else {
			return false;
		}
	}
	
	public boolean isNull(String value) {
		if (value != null && value.equalsIgnoreCase("nil")) {
			return true;
		} else if (value != null && value.equalsIgnoreCase("(nil)")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOk(long value) {
		if (value == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOk(List<Object> value) {
		if (value == null)
			return false;
		for (Object val : value) {
			if (!val.toString().equals("OK") && !val.toString().equals("1")) {
				return false;
			}
		}
		return true;

	}

	public boolean isReady() {
		return ready;
	}

	public boolean isNotReady() {
		return !ready;
	}
	
	protected abstract boolean isComplexObj();

}
